package ca.mss.rd.trader.deprecated.tabs;

import info.monitorenter.gui.chart.Chart2D;
import info.monitorenter.gui.chart.ITrace2D;
import info.monitorenter.gui.chart.TracePoint2D;
import info.monitorenter.gui.chart.controls.LayoutFactory;
import info.monitorenter.gui.chart.views.ChartPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.swing.BoxLayout;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import ca.mss.rd.jchart2d.man.RdChartManager;
import ca.mss.rd.jchart2d.util.UtilJChart2D;
import ca.mss.rd.swing.RdTabModel;
import ca.mss.rd.trader.deprecated.controller.ChartController;
import ca.mss.rd.trader.deprecated.model.AvailableData;
import ca.mss.rd.trader.deprecated.model.SelectedData;
import ca.mss.rd.trader.deprecated.model.indicator.Indicator;
import ca.mss.rd.trader.deprecated.panels.AmountPointsPanel;
import ca.mss.rd.trader.deprecated.panels.ButtonPanel;
import ca.mss.rd.trader.deprecated.panels.LatencyTimePanel;
import ca.mss.rd.trader.model.Point;
import ca.mss.rd.trader.util.MyColor;


@Deprecated
public class LabPanel extends ButtonPanel implements PropertyChangeListener {

	private static final long serialVersionUID = LabPanel.class.hashCode();
	final static public String module = LabPanel.class.getSimpleName();
	final static public org.apache.log4j.Logger logger = ca.mss.rd.util.Debug.getLogger(module);
	

	final private ChartController ctrl;
	final private RdChartManager chartman;

	private String dataSource;
	
	private Map<String, Chart2D> chart2D = new TreeMap<String, Chart2D>();
	private JPanel graphPanel;

	public LabPanel(RdTabModel model) {
		super();
		
		ctrl = new ChartController();
		chartman = new RdChartManager();
		this.addPropertyChangeListener(ctrl);
		ctrl.addPropertyChangeListener(this);

		init();
	}

	
	public final int getGraphAmount() {
		int amnt = 0;
		
		if( getDataSource() != null ){
			Set<String> charts = new HashSet<String>();
	
			for(Iterator<String> iter=SelectedData.INSTANCE.selectedQuotes.get(getDataSource()).iterator(); iter.hasNext(); ){
				String quote = iter.next();
				if( !charts.contains(quote) ){
					amnt += 1;
					charts.add(quote);
				}
			}
			
			for(Iterator<List<Indicator>> iter=ctrl.activeIndicators.values().iterator(); iter.hasNext(); ){
				List<Indicator> lind = iter.next();
				for(int i=0, sizei=lind.size(); i<sizei; i++)
					for(int j=0, sizej=lind.get(i).size(); j<sizej; j++)
						if( lind.get(i).isVisible(j))
							if( !charts.contains(lind.get(i).getChartName(j)) ){
								amnt += 1;
								charts.add(lind.get(i).getChartName(j));
							}
			}
		}
		return amnt;
	}


	private int hor = 1;
	private int ver = 1;
	private int height = 160;

	private void init() {
		
		graphPanel = new JPanel();

		latencyPanel = new LatencyTimePanel(new PropertyChangeListener[]{ctrl});
		amountPanel = new AmountPointsPanel(new PropertyChangeListener[]{ctrl});
		
		int ttlg = getGraphAmount();
		
		// defaults
		height = 160;
		hor = 2;
		ver = ttlg/hor + ttlg%hor;
		
		if( ttlg == 0){
			hor = 1;
			ver = 1;
			height = 3*height;
		} else if( ttlg == 1){
			hor = 1;
			ver = 1;
			height = 3*height;
		} else if( ttlg == 2){
			hor = 1;
			ver = 2;
			height = 3*height/2;
		} else if( ttlg <= 3) {
			hor = 1;
			ver = 3;
		} else if( ttlg <= 2*2) {
			hor = 2;
			ver = 2;
		} else if(ttlg <= 3*2) {
			hor = 2;
			ver = 3;
		} else if(ttlg <= 3*3 ) {
			hor = 3;
			ver = 3;
		} else if(ttlg <= 4*4) {
			hor = 4;
			ver = 4;
		} else {
			hor = 4;
			ver = ttlg/hor+1;
		}

		graphPanel.setLayout(new GridLayout(ver, hor));
		graphPanel.setPreferredSize(new Dimension(this.getWidth(), 160*ver));


		JScrollPane scroll = new JScrollPane();
		scroll.setPreferredSize(new Dimension(500, 500));
		scroll.setViewportView(graphPanel);	

		this.setLayout(new BorderLayout());
		this.add(scroll);
		
		initControlButtonPanel();

		/*
		 * Add controls to the south
		 */
		JPanel southPanel = new JPanel();
		southPanel.setLayout(new BoxLayout(southPanel, 1));
		southPanel.add(amountPanel);
		southPanel.add(latencyPanel);

		this.add(southPanel);
	}

	int graphCount = 0;
    private void addChart(String chartName, String traceName,  Color color, int maxSize){
    	
		Chart2D chart = chart2D.get(chartName);

		if( chart == null ){
			chart = UtilJChart2D.create2DChart(chartName);
			chart2D.put(chartName, chart);

			ChartPanel chartpanel = new ChartPanel(chart);
			graphPanel.add(chartpanel);
			graphPanel.addPropertyChangeListener(chartpanel);

			/*
			 * Add grapth menu
			 */
			LayoutFactory factory = LayoutFactory.getInstance();
			
			JMenuBar menu = factory.createChartMenuBar(chartpanel, false);
			menu.getMenu(0).setText(chart.getName());

		}
		
		// TODO: too long menu
//		if( app.getJMenuBar() == null )
//			app.setJMenuBar(menu);
//		else
//			app.getJMenuBar().add(menu);
		
		// TODO: can be associated with any chart
//        snapshotBtn = new JButton(Chart2DActionSaveImageSingleton.getInstance(chart2D[1], "Save image"));
//        snapshotBtn.setBackground(Color.WHITE);
		
		chartman.addTrace(chart, UtilJChart2D.createTrace2DLtd(traceName, color, maxSize));
		
		++graphCount;
		
		System.out.println("Added ["+graphCount+"]["+chartName+"]");
    }
    
    

    public final String getDataSource() {
		return dataSource;
	}

	@Override
    public void setDataSource(String dataSource){
    	this.dataSource = dataSource;
		ctrl.setDataSource(dataSource);
		ctrl.setLatencyDelay(latencyPanel.getLatencyDelay());
		ctrl.addQuote(SelectedData.INSTANCE.selectedQuotes.get(getDataSource()));
    }

    @Override
	public Set<String> getDataSources() {
    	return AvailableData.getDataSourceSet();
	}

	/*
	 * Button action handlers
	 * 
	 */
	public void revertHandler(){
		if (ctrl.isSBB()) {
			buyBack();
		} else {
			doSell();
		}
		if( ctrl.isBSB() ) {
			sellBack();
		} else {
			doBuy();
		}
    }
	
    public void startHandler(String command){
		if ("Start".equals(command)) {
            startStopBtn.setText("Stop");
        	ctrl.start();
		} else {
            startStopBtn.setText("Start");
            ctrl.stop();
		}

		startStopBtn.invalidate();
    	startStopBtn.repaint();
    }
	
    public void sbbHandler(String command) {
		if ("sbb".equals(command)) {
			doSell();
		} else {
			buyBack();
		}
    }
	
	
    public void bsbHandler(String command) {
		if ("bsb".equals(command)) {
			doBuy();
		} else {
			sellBack();
		}
    }

	
    public void recordHandler(String command) {
        if( "Record".equals(command)) {
            startRecord();
            recordBtn.setText("Stop");
        } else {
            stopRecord();
            recordBtn.setText("Record");
        }
        recordBtn.invalidate();
        recordBtn.repaint();
    }
	
	public void clearHandler() {
		startHandler("Stop");
		chartman.removeAllPoints(null);
		ctrl.restart();
	}

	/*
	 * Private interface
	 */

    private void doSell() {
		pcs.firePropertyChange("doSell", null, "default");
        sellbuyBtn.setText("SBB");
        sellbuyBtn.invalidate();
        sellbuyBtn.repaint();
        setReverseColor();
    }

    private void buyBack() {
		pcs.firePropertyChange("buyBack", null, "default");
        sellbuyBtn.setText("sbb");
        sellbuyBtn.invalidate();
        sellbuyBtn.repaint();
        setReverseColor();
    }
    
    private void doBuy() {
		pcs.firePropertyChange("doBuy", null, "default");
        buysellBtn.setText("BSB");
        buysellBtn.invalidate();
        buysellBtn.repaint();
        setReverseColor();
    }

    private void sellBack() {
		pcs.firePropertyChange("sellBack", null, "default");
		buysellBtn.setText("bsb");
        buysellBtn.invalidate();
        buysellBtn.repaint();
        setReverseColor();
    }

    private void setReverseColor() {
        if( ctrl.isSBB() && !ctrl.isBSB() ){
        	revertBtn.setForeground(MyColor.COLOR_SBB);
	        revertBtn.invalidate();
	        revertBtn.repaint();
        } else  if( ctrl.isBSB() &&!ctrl.isSBB() ){
        	revertBtn.setForeground(MyColor.COLOR_BSB);
	        revertBtn.invalidate();
	        revertBtn.repaint();
        } else {
        	revertBtn.setForeground(Color.BLACK);
	        revertBtn.invalidate();
	        revertBtn.repaint();
	        }
    }

    private void startRecord() {
		pcs.firePropertyChange("startRecord", null, "default");
    }

    private void stopRecord() {
		pcs.firePropertyChange("stopRecord", null, "default");
    }

	/*
	 * (non-Javadoc)
	 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
	 */
	@Override
	public void propertyChange(PropertyChangeEvent evt) {

		final String name = evt.getPropertyName();
		final ITrace2D trace = chartman.getTrace(name);

		if( trace != null  ){
			Point point = (Point )evt.getNewValue();
			trace.addPoint(new TracePoint2D(point.time, point.getPrev()));
			trace.addPoint(new TracePoint2D(point.time, point.getCurr()));
		} else if ("maxPointAmount".equals(name)) {
			chartman.setMaxPoints((Integer )evt.getNewValue());
		} else if( "threadStop".equals(name)){
			startHandler("Stop");
		} else if( "addQuote".equals(name)){
			String chartName = (String )evt.getNewValue();
			addChart(chartName, chartName, MyColor.getColor("white"), amountPanel.getMaxSize());
		} else if( "addIndicator".equals(name)){
			Indicator indicator = (Indicator )evt.getNewValue();
			for(int i=0, sizei=indicator.size(); i<sizei; i++)
				if( indicator.isVisible(i) )
					addChart(indicator.getChartName(i), indicator.getTraceName(i), indicator.getColor(i), amountPanel.getMaxSize());
		} else {
			logger.warn(module+": unhandled event [event="+name+"][from="+evt.getSource().getClass().getSimpleName()+"][value="+evt.getNewValue()+"]");
		}
	}
	
	/*
     * Property change support
     */
    private PropertyChangeSupport pcs;
    
    final public void addPropertyChangeListener(PropertyChangeListener listener) {
    	if( pcs == null ){
    		pcs = new PropertyChangeSupport(this);
    	}
    	pcs.addPropertyChangeListener(listener);
    }

}
