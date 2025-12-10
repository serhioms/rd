package ca.mss.rd.trader.view.widgets;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.beans.PropertyChangeEvent;

import javax.swing.JPanel;

import ca.mss.rd.chart.manager.RdChartManager;
import ca.mss.rd.swing.RdPanel;
import ca.mss.rd.util.runnable.RdRunnable;

public class ChartsPanel extends RdPanel {

	final static public String module = ChartsPanel.class.getName();
	final public static long serialVersionUID = module.hashCode();
	final static public org.apache.log4j.Logger logger = ca.mss.rd.util.Debug.getLogger(module);

	final static public String EVENT_CHART_PANEL_CREATED = "EventChartPanelCreated";

	private int quotes, chartsPerRow, chartHeight;
	
	public ChartsPanel(int quotes, int chartsPerRow, int chartHeight) {
		this.quotes = quotes;
		this.chartsPerRow = chartsPerRow;
		this.chartHeight = chartHeight;
		
		init();
	}

	@Override
	public void init() {
		setLayout(new GridLayout(quotes/chartsPerRow+1, chartsPerRow));
		setPreferredSize(new Dimension(getWidth(), quotes*chartHeight/chartsPerRow));
	}


	public void manage(final RdChartManager chartman, final String chartName,  final boolean isShow){
		
		JPanel chartpanel = chartman.getChartPanel(chartName);

		if( chartpanel == null ){
			// Create chart is too long to make it sequentially...
			new RdRunnable(chartName) {
				@Override
			    public void runThreadHandler() throws InterruptedException{
					// better synchronize on chartman object here rather then synchronize createChart method inside the class
					synchronized( chartman ){
						JPanel chartpanel = chartman.createChartPanel(chartName, ChartsPanel.this);
						add(chartpanel);
						ChartsPanel.this.firePropertyChange(EVENT_CHART_PANEL_CREATED, "", chartName);
					}
				}
			}.startThread();
			return;
		} else if( isShow ){
			if( chartpanel.getParent() == null )
				add(chartpanel);
		} else if( chartpanel.getParent() != null ){
				remove(chartpanel);
		}
		
		revalidate();
		repaint();
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		final String eventName = evt.getPropertyName();
		@SuppressWarnings("unused")
		final String sourceClass = evt.getSource().getClass().getSimpleName();
		final Object oldval = evt.getOldValue();
		final Object newval = evt.getNewValue();

		if( RdChartManager.EVENT_CHART_NEAREST_X.equals(eventName) ){
			this.firePropertyChange(eventName, oldval, newval);
		}
	}

}
