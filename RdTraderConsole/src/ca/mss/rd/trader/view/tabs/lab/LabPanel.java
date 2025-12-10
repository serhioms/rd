package ca.mss.rd.trader.view.tabs.lab;

import java.awt.GridLayout;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.HashMap;
import java.util.Set;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ca.mss.rd.chart.manager.RdChartManager;
import ca.mss.rd.chart.point.DrawPoint;
import ca.mss.rd.parser.impl.GenericRecord;
import ca.mss.rd.swing.RdPanel;
import ca.mss.rd.swing.RdTabModel;
import ca.mss.rd.swing.RdWidgets;
import ca.mss.rd.trader.server.Trader;
import ca.mss.rd.trader.src.oanda.QuoteEvent;
import ca.mss.rd.trader.view.widgets.ChartsPanel;
import ca.mss.rd.trader.view.widgets.FilesPanel;
import ca.mss.rd.trader.view.widgets.LatencyRadioPanel;
import ca.mss.rd.trader.view.widgets.MaxPointRadioPanel;
import ca.mss.rd.trader.view.widgets.QuotesCheckPanel;
import ca.mss.rd.trader.view.widgets.QuotesRadioPanel;
import ca.mss.rd.trader.view.widgets.TracesPanel;
import ca.mss.rd.trader.view.widgets.WidgetEvent;
import ca.mss.rd.util.UtilMisc;
import ca.mss.rd.util.UtilProperty;
import ca.mss.rd.util.UtilReflection;
import ca.mss.rd.util.UtilString;

public class LabPanel extends RdPanel implements PropertyChangeListener {

	private static final long serialVersionUID = LabPanel.class.hashCode();
	final static public String module = LabPanel.class.getSimpleName();
	final static public org.apache.log4j.Logger logger = ca.mss.rd.util.Debug.getLogger(module);
	
	static public String DEFAULT_CHART_MANAGER_CLASS = "ca.mss.rd.chart.manager.chart2d.RdChart2dMan";
	
	static public int DRAW_POINT_IF_MAXPOINT_LESS_THEN_THIS = 10000;
	static public long SHOW_MESSAGE_IF_LATENCY_GE_THEN_THIS = 250L;
	static public long SHOW_MESSAGE_IF_REMINDER_OF_THIS_DIV_LATENCY_IS_ZERO = 1000L;
	
	static public int DEFAULT_LATENCY = 7;
	static public int DEFAULT_MAX_POINT = 1;
	static public String DEFAULT_STOP_TIME = "0";
	static public HashMap<String,String> DEFAULT_INDICATORS = new HashMap<String,String>();

	static public String DEFAULT_QUOTE = "EURUSD";
	
	static {
		UtilProperty.readConstants(LabPanel.class);
	}

	private int quotes = 5;
	private int chartHeight = 145*2+50;
	private int chartsPerRow = 1;
	
	private long latency;
	//private int maxPoint;
	
	private ChartsPanel panelChart;
	private LabButtonsPanel panelButtons;
	private LatencyRadioPanel panelLatency;
	private MaxPointRadioPanel panelMaxPoint;
	private FilesPanel panelFiles;
	private TracesPanel panelTraces;
	private QuotesRadioPanel panelQuotes;

	private final LabController controller;

	private final JLabel message;
	private final RdChartManager chartman;

	private String quoteName = DEFAULT_QUOTE;
	
	private long msgCounter;

	public LabPanel(RdTabModel model) {
		this.message = new JLabel();
		this.controller = new LabController(model);
		
		controller.addPropertyChangeListener(this);
		this.addPropertyChangeListener(controller);

		panelTraces = new TracesPanel();
		panelTraces.addPropertyChangeListener(controller);
		panelTraces.addPropertyChangeListener(this);

		this.chartman = (RdChartManager )UtilReflection.instantiateObject(DEFAULT_CHART_MANAGER_CLASS);

		init();
	}

	@Override
	public void init() {
		
		message.setVerticalTextPosition(JLabel.TOP);
		message.setHorizontalTextPosition(JLabel.LEFT);
		showMessage("Message panel...");

		panelButtons = new LabButtonsPanel();
		panelButtons.addPropertyChangeListener(controller);
		panelButtons.addPropertyChangeListener(this);
		panelButtons.stop.setText(DEFAULT_STOP_TIME);
		panelButtons.stop.addFocusListener(new FocusListener() {
			private String oldval;
            @Override
			public void focusGained(FocusEvent event) {
            	oldval = panelButtons.stop.getText();
			}

			@Override
			public void focusLost(FocusEvent event) {
            	String newval = panelButtons.stop.getText();
            	if( !newval.equals(oldval) ){
            		DEFAULT_STOP_TIME = newval;
            		UtilProperty.writeConstants(LabPanel.class);
            	}
            }
        });
		
		panelLatency = new LatencyRadioPanel(new String[]{"0","1","5","10","25","50","75","100","250","500","750","1s","3s","15s","1m","15m","1h","1d","1w","1mo"});
		panelLatency.addPropertyChangeListener(controller);
		panelLatency.addPropertyChangeListener(this);
		panelLatency.init(DEFAULT_LATENCY);

		panelMaxPoint = new MaxPointRadioPanel(new String[]{"25", "50","100","250","500","750","1K","2K","5K","10K","25K","50K","100K","500K","1M"});
		panelMaxPoint.addPropertyChangeListener(controller);
		panelMaxPoint.addPropertyChangeListener(this);
		panelMaxPoint.init(DEFAULT_MAX_POINT);
		
		panelChart = new ChartsPanel(quotes/chartsPerRow, chartsPerRow, chartHeight); 
		panelChart.addPropertyChangeListener(this);
		panelChart.addPropertyChangeListener(controller);
		
		panelQuotes = new QuotesRadioPanel();
		panelQuotes.addPropertyChangeListener(this);
		panelQuotes.addPropertyChangeListener(controller);
		panelQuotes.setSelected(quoteName);
		
		panelFiles = new FilesPanel();
		
		JPanel panelWest = RdWidgets.createPanelBoxY(new JComponent[]{
				RdWidgets.createTabPane(
						new String[]{"Traces", "Files"},  
						new JComponent[] { panelTraces, panelFiles} 
				)
				,panelButtons});
		
		JComponent panelCenter = RdWidgets.createPanelBoxY(new JComponent[]{
				RdWidgets.createPaneScroll(panelChart),
				RdWidgets.createPanelBorder(new JComponent[]{message})
			});
		
		JPanel panelSliders = RdWidgets.createPanelGrid(2, 1, new JComponent[]{panelLatency, panelMaxPoint});
		
		JPanel southPanel = RdWidgets.createPanelFlow(panelSliders); 
		
		setLayout(new GridLayout());
		
		add(RdWidgets.createPanelBorder(null, panelWest, panelCenter, panelQuotes, southPanel));
	}

	public final void showMessage(String row){
		msgCounter++;
		
		if( row.isEmpty() )
			return;
		else if( latency > SHOW_MESSAGE_IF_LATENCY_GE_THEN_THIS );
		else if( msgCounter % (SHOW_MESSAGE_IF_REMINDER_OF_THIS_DIV_LATENCY_IS_ZERO/(latency+1)) == 0L );
		else
			return;

			
		String msg = UtilMisc.toString(row.substring(0, Math.min(50, row.length())).split("\t"), "  |  ", "");
		
		message.setText(String.format("%-200s", msg));
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		final String eventName = evt.getPropertyName();
		@SuppressWarnings("unused")
		final String sourceClass = evt.getSource().getClass().getSimpleName();
		final Object oldval = evt.getOldValue();
		final Object newval = evt.getNewValue();

		if( Trader.EVENT_CHART_DRAW.equals(eventName) ){
			if( Trader.BackOffice.ifShow() ){
				drawChartHandler((DrawPoint[] )newval);
			}
		} else if( LabController.EVENT_CHART_MARK_DRAW.equals(eventName) ){
			drawSpecialHandler((DrawPoint[] )newval);
		} else if (LabButtonsPanel.EVENT_RUN.equals(eventName)) {
			chartman.setMaxPoints(chartman.getMaxPoints());
		} else if( QuoteEvent.EVENT_RECORD.equals(eventName)) {
			showMessage(((GenericRecord )newval).getRow());
		} else if( QuoteEvent.EVENT_HEADER.equals(eventName)) {
			chartman.setTimingStart();
			panelQuotes.update(controller.quoteSource.getQuotes((String[] )newval));
		} else if( LabController.EVENT_NEW_FILE.equals(eventName)) {
			panelFiles.navigateFile((File )newval);
		} else if (WidgetEvent.EVENT_MAXPOINT.equals(eventName)) {
			if( oldval != null ){
				maxpointHandler(((Integer )newval).intValue());
			}
		} else if (WidgetEvent.EVENT_LATENCY.equals(eventName)) {
			this.latency = ((Long)newval).longValue();
			DEFAULT_LATENCY = panelLatency.getIndex(latency); 
			UtilProperty.writeConstants(LabPanel.class);
		} else if (Trader.EVENT_CHART_SHOW.equals(eventName)) {
			chartsHandler((Set<String>)newval, true);
		} else if (Trader.EVENT_CHART_HIDE.equals(eventName)) {
			chartsHandler((Set<String>)newval, false);
		} else if (QuoteEvent.EVENT_FINISH.equals(eventName)) {
			chartman.setTimingFinish();

			if( panelButtons.run.isSelected() ){
				try {
					panelButtons.run.doClick();
				}catch(Throwable t){
					// ignore
				}
			}
			
			panelQuotes.enable();
			panelFiles.enable();
		} else if (LabButtonsPanel.EVENT_CLEAR.equals(eventName)) {
			chartman.removeAllPoints(quoteName);
			msgCounter = 0L;
		} else if (QuotesCheckPanel.EVENT_QUOTE_CHOOSER.equals(eventName)) {
			final String[] showQuote = ((String )newval).split(":"); // new=GBPUSD:true
			if( "true".equalsIgnoreCase(showQuote[1]) ){
				DEFAULT_QUOTE = quoteName = showQuote[0];
				UtilProperty.writeConstants(LabPanel.class);
			}
		} else if (LabButtonsPanel.EVENT_RUN.equals(eventName)) {
			panelQuotes.freez();
			panelFiles.freez();
		} else if (Trader.EVENT_TRACE_PANEL_CREATE.equals(eventName)) {
			panelTraces.createTraces((Set<String> )newval, DEFAULT_INDICATORS.get(quoteName));
		} else if (LabController.EVENT_TRACE_HIDE.equals(eventName)) {
			panelTraces.manageSlowTraces((Set<String> )newval, false);
		} else if (LabController.EVENT_TRACE_SHOW.equals(eventName)) {
			panelTraces.manageSlowTraces((Set<String> )newval, true);
		} else if (TracesPanel.EVENT_TRACE_SELECT.equals(eventName)) {
			String[] traceVisible = ((String) newval).split(":");
			traceHandler(traceVisible[0], Boolean.parseBoolean(traceVisible[1]));
		} else if (Trader.EVENT_TRACE_CREATE.equals(eventName)) {
			createTraceHandler((DrawPoint )newval);
		} else if (ChartsPanel.EVENT_CHART_PANEL_CREATED.equals(eventName)) {
			chartPanelHandler((String )newval);
		} else if( RdChartManager.EVENT_CHART_NEAREST_X.equals(eventName) ){
			long start = UtilString.parseLong(panelButtons.start.getText());
			long stop = UtilString.parseLong(panelButtons.stop.getText());
			long nw = UtilString.parseLong(newval.toString());
			if( start == 0 ){
				panelButtons.start.setText(newval.toString());
				firePropertyChange(LabButtonsPanel.EVENT_START_COUNTER, "", newval.toString());
			} else if( nw > stop ) {
				panelButtons.stop.setText(newval.toString());
				firePropertyChange(LabButtonsPanel.EVENT_STOP_COUNTER, "", newval.toString());
			} else if( nw < start ) {
				panelButtons.start.setText(newval.toString());
				firePropertyChange(LabButtonsPanel.EVENT_START_COUNTER, "", newval.toString());
			} else {
				panelButtons.stop.setText(newval.toString());
				firePropertyChange(LabButtonsPanel.EVENT_STOP_COUNTER, "", newval.toString());
			}
		} else {
			//logger.warn(String.format("%s: Unhandled event [event=%s][from=%s][old=%s][new=%s]", module, eventName, sourceClass, oldval, newval));
		}
	}
	
	private final void drawChartHandler(final DrawPoint[] dp){
		for(int i=0; i<dp.length; i++){
			chartman.drawRegularPoint(dp[i]);
		}
	}
	
	private final void drawSpecialHandler(final DrawPoint[] dp){
		for(int i=0; i<dp.length; i++){
			chartman.drawSpecialPoint(dp[i]);
		}
	}
	
	private final void chartPanelHandler(String chartName){
		for(String traceName: chartman.getTraces(chartName)){
			if( !panelTraces.isSelected(traceName) ){
				chartman.removeTrace(traceName);
			}
		}
		this.revalidate();
		this.repaint();
	}
	
	private final void chartsHandler(Set<String> charts, boolean isShow){
		for(final String chartName: charts){
			panelChart.manage(chartman, chartName, isShow);
		}
	}
	
	private final void createTraceHandler(DrawPoint dp){
		chartman.createTrace(dp.chartName, dp.traceName, dp.quoteName, dp.color);
	}
	
	private final void traceHandler(String traceName, boolean isSelected){
		if( isSelected ){
			chartman.restoreTrace(traceName);
		} else {
			chartman.removeTrace(traceName);
		}
		panelTraces.setSelected(traceName, isSelected);
		//
		DEFAULT_INDICATORS.put(quoteName, panelTraces.getSelectedTraces());
		UtilProperty.writeConstants(LabPanel.class);
	}
	
	private final void maxpointHandler(int maxPoint){
		//this.maxPoint = maxPoint;
		
		chartman.setMaxPoints(maxPoint);
	
		DEFAULT_MAX_POINT = panelMaxPoint.getIndex(chartman.getMaxPoints()); 
		UtilProperty.writeConstants(LabPanel.class);
	}
	
}
