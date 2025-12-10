package ca.mss.rd.trader.view.tabs.recoder;

import java.awt.Color;
import java.awt.GridLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.Date;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ca.mss.rd.chart.manager.RdChartManager;
import ca.mss.rd.chart.point.DrawPoint;
import ca.mss.rd.parser.impl.GenericRecord;
import ca.mss.rd.swing.RdPanel;
import ca.mss.rd.swing.RdTabModel;
import ca.mss.rd.swing.RdWidgets;
import ca.mss.rd.trader.FxTConfig;
import ca.mss.rd.trader.src.oanda.FxOandaFile;
import ca.mss.rd.trader.src.oanda.QuoteEvent;
import ca.mss.rd.trader.view.tabs.lab.LabButtonsPanel;
import ca.mss.rd.trader.view.widgets.ChartsPanel;
import ca.mss.rd.trader.view.widgets.FilesPanel;
import ca.mss.rd.trader.view.widgets.LatencySliderPanel;
import ca.mss.rd.trader.view.widgets.MaxPointSliderPanel;
import ca.mss.rd.trader.view.widgets.QuotesCheckPanel;
import ca.mss.rd.trader.view.widgets.SchedullerPanel;
import ca.mss.rd.trader.view.widgets.WidgetEvent;
import ca.mss.rd.util.UtilDateTime;
import ca.mss.rd.util.UtilMisc;
import ca.mss.rd.util.UtilProperty;
import ca.mss.rd.util.UtilReflection;
import ca.mss.rd.util.map.SmartMap;
import ca.mss.rd.util.runnable.RdExecutors;
import ca.mss.rd.util.runnable.RdRunnable;

public class RecorderPanel extends RdPanel implements PropertyChangeListener {

	private static final long serialVersionUID = RecorderPanel.class.hashCode();
	final static public String module = RecorderPanel.class.getSimpleName();
	final static public org.apache.log4j.Logger logger = ca.mss.rd.util.Debug.getLogger(module);
	
	static public String DEFAULT_CHART_MANAGER_CLASS = "ca.mss.rd.chart.manager.chart2d.RdChart2dMan";

	static public int CHART_MAX_DRAW_THREADS = 5;
	static public int CHART_HEIGHT = 160;
	static public int CHART_PER_ROW = 2;

	final static private ExecutorService executor = RdExecutors.newExecutorsPool(CHART_MAX_DRAW_THREADS, "DrawChart", true, RdRunnable.NORM_PRIORITY);
	
	static {
		UtilProperty.readConstants(RecorderPanel.class);
	}

	
	private ChartsPanel panelChart;
	private RecoderButtonsPanel panelButtons;
	private LatencySliderPanel panelRequestLatency;
	private MaxPointSliderPanel panelMaxPoint;
	private FilesPanel panelFiles;
	private QuotesCheckPanel panelQuotes;
	private SchedullerPanel panelScheduller;

	private final RecorderController controller;

	private final JLabel message;
	private final RdChartManager chartman;

	private int quotes = FxTConfig.FOREX_QUOTE.size();
	private int chartHeight = CHART_HEIGHT;
	private int chartsPerRow = CHART_PER_ROW;
	
	@SuppressWarnings("serial")
	private SmartMap<String, DrawPoint> drawPointPool = new SmartMap<String, DrawPoint>(){
		
		@Override
		public DrawPoint valueFactory(Object key) {
			return new DrawPoint(key.toString(), key.toString(), key.toString(), Color.RED);
		}
	};
	
	private long downloadedRecordCounter;

	public RecorderPanel(RdTabModel model) {
		this.message = new JLabel();
		this.controller = new RecorderController(model);
		controller.addPropertyChangeListener(this);

		this.chartman = (RdChartManager )UtilReflection.instantiateObject(DEFAULT_CHART_MANAGER_CLASS);

		init();
	}

	@Override
	public void init() {
		
		message.setVerticalTextPosition(JLabel.TOP);
		message.setHorizontalTextPosition(JLabel.LEFT);
		showMessage("Message panel...");

		panelButtons = new RecoderButtonsPanel();
		panelButtons.addPropertyChangeListener(this);
		panelButtons.addPropertyChangeListener(controller);
		
		panelRequestLatency = new LatencySliderPanel(0, 60, 1, 10);
		panelRequestLatency.addPropertyChangeListener(this);
		panelRequestLatency.addPropertyChangeListener(controller);
		panelRequestLatency.init(3);

		panelMaxPoint = new MaxPointSliderPanel(0, 1000, 10, 100);
		panelMaxPoint.addPropertyChangeListener(this);
		panelMaxPoint.addPropertyChangeListener(controller);
		panelMaxPoint.init(1000);
		
		panelChart = new ChartsPanel(quotes/chartsPerRow, chartsPerRow, chartHeight); 
		panelChart.addPropertyChangeListener(this);
		panelChart.addPropertyChangeListener(controller);
		
		panelQuotes = new QuotesCheckPanel();
		panelQuotes.addPropertyChangeListener(this);
		panelQuotes.addPropertyChangeListener(controller);

		JPanel panelWest = RdWidgets.createPanelBoxY(new JComponent[]{
				panelFiles= new FilesPanel(), 
				panelButtons});
		
		JComponent panelCenter = RdWidgets.createPanelBoxY(new JComponent[]{
				RdWidgets.createPaneScroll(panelChart),
				RdWidgets.createPanelBorder(new JComponent[]{message})
			});
		
		JPanel panelSliders = RdWidgets.createPanelGrid(2, 1, new JComponent[]{panelRequestLatency, panelMaxPoint});
		
		panelScheduller = new SchedullerPanel(SchedullerPanel.SCHEDULER_STYLE_2); 
		panelScheduller.addPropertyChangeListener(this);
		panelScheduller.addPropertyChangeListener(controller);
		
		JPanel southPanel = RdWidgets.createPanelBorder(null, null, panelSliders, panelScheduller, null); 
		
		setLayout(new GridLayout());
		
		add(RdWidgets.createPanelBorder(null, panelWest, panelCenter, panelQuotes, southPanel));
	}

	/*
	 * (non-Javadoc)
	 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
	 */
	@Override
	public void propertyChange(PropertyChangeEvent evt) {

		final String eventName = evt.getPropertyName();
		//final String sourceClass = evt.getSource().getClass().getSimpleName();
		final Object oldval = evt.getOldValue();
		final Object newval = evt.getNewValue();

		if( RecorderController.EVENT_DRAW_CHART.equals(eventName) ){
			//logger.warn("%s: Process event [event=%s][from=%s][old=%s][new=%s]", module, eventName, sourceClass, oldval, newval);
			drawChartHandler((GenericRecord )newval);
		} else if( RecoderButtonsPanel.EVENT_RECODER.equals(eventName)) {
			//logger.warn("%s: Process event [event=%s][from=%s][old=%s][new=%s]", module, eventName, sourceClass, oldval, newval);
			if( ((Boolean )newval).booleanValue() ){
				panelRequestLatency.slider.setEnabled(false);
			} else {
				panelRequestLatency.slider.setEnabled(true);
			}
		} else if( QuoteEvent.EVENT_RECORD.equals(eventName)) {
			final GenericRecord record = (GenericRecord )newval;
			String row = record.getRow();
			if( row.isEmpty() )
				showMessage(String.format("%6d) Nothing downloaded %s", downloadedRecordCounter++, UtilDateTime.formatDate(UtilDateTime.now())));
			else
				showMessage(String.format("%6d) "+UtilMisc.toString(row.substring(0, Math.min(50, row.length())).split("\t"), "  |  ", ""), downloadedRecordCounter++));
		} else if( QuoteEvent.EVENT_HEADER.equals(eventName)) {
			final String[] header = (String[] )newval;
			panelQuotes.update(controller.quotesSource.getQuotes(header));
			downloadedRecordCounter = 1;
		} else if( RecorderController.EVENT_NEW_FILE.equals(eventName)) {
			panelFiles.navigateFile(new File((String )newval));
		} else if (RecorderController.EVENT_RUN_BUTTON.equals(eventName)) {
			if( !panelButtons.run.isSelected() ){
				panelButtons.run.doClick();
            	firePropertyChange(RecoderButtonsPanel.EVENT_RUN, false, true);
			}
		} else if (WidgetEvent.EVENT_MAXPOINT.equals(eventName)) {
			if( oldval != null ){
				chartman.setMaxPoints(((Integer )newval).intValue());
			}
		} else if (QuotesCheckPanel.EVENT_QUOTE_CHOOSER.equals(eventName)) {
			final String[] showChart = ((String )newval).split(":"); // new=GBPUSD:true
			panelChart.manage(chartman, showChart[0], Boolean.parseBoolean(showChart[1]));
		} else if (LabButtonsPanel.EVENT_CLEAR.equals(eventName)) {
			//logger.warn("%s: Process event [event=%s][from=%s][old=%s][new=%s]", module, eventName, sourceClass, oldval, newval);
			chartman.removeAllPoints(null);
		} else if (ChartsPanel.EVENT_CHART_PANEL_CREATED.equals(eventName)) {
			chartPanelHandler((String )newval);
		} else {
			//logger.warn(String.format("%s: Unhandled event [event=%s][from=%s][old=%s][new=%s]", module, eventName, sourceClass, oldval, newval));
		}
	}

	private final void showMessage(String... args){
		String text = "";
		for(int i=0; i<args.length; i++)
			text += args[i];
		message.setText(String.format("%-200s", text));
	}
	
	private final void chartPanelHandler(String chartName){
		chartman.createTrace(chartName, chartName, chartName, Color.RED);
		this.revalidate();
		this.repaint();
	}
	
	private final void drawChartHandler(final GenericRecord record){
    	for(Iterator<String> iter=controller.visibleCharts.iterator(); iter.hasNext(); ){
    		final String chartName = iter.next(); 
    		executor.execute(
				new RdRunnable("DrawChart") {
					@Override
				    public void runThreadHandler() throws InterruptedException{
			    		String bid = record.getValue(chartName+"Bid");
			    		String ask = record.getValue(chartName+"Ask");
			    		
			    		Date time24 = UtilDateTime.parse(record.getDate()+" "+record.getTime(), FxOandaFile.ROW_DATE_TIME_FORMATTER);

			    		if( bid == null || ask == null ){
			    			logger.debug(String.format("There is no quotes for [%s] - skipped draw on chart", chartName));
			    		} else {
				    		DrawPoint drawPoint = drawPointPool.get(chartName);
				    		drawPoint.time = time24.getTime();
				    		drawPoint.val = (Double.parseDouble(bid)+Double.parseDouble(ask))*0.5;
				    		chartman.drawRegularPoint(drawPoint);
			    		}
					}
				});
    	}
	}
	
}
