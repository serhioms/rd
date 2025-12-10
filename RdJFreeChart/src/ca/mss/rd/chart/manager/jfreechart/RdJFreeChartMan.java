package ca.mss.rd.chart.manager.jfreechart;

import java.awt.Color;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.swing.JComponent;
import javax.swing.JPanel;

import org.jfree.chart.JFreeChart;
import org.jfree.data.time.Millisecond;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

import ca.mss.rd.chart.jfreechart.util.UtilJFreeChart;
import ca.mss.rd.chart.manager.RdChartManager;
import ca.mss.rd.chart.point.DrawPoint;
import ca.mss.rd.swing.RdWidgets;

public class RdJFreeChartMan implements RdChartManager {

	private final Map<String, TimeSeries> traceByTrace = new HashMap<String, TimeSeries>();
	private final Map<String, Set<TimeSeries>> traceByQuote = new HashMap<String, Set<TimeSeries>>();
	private final Map<String, TimeSeriesCollection> chartByTrace = new HashMap<String, TimeSeriesCollection>();
	private final Map<String, JPanel> panelByChart = new TreeMap<String, JPanel>();
	private final Map<String, TimeSeriesCollection> collectionByChart = new TreeMap<String, TimeSeriesCollection>();

	private int maxPoints;

	public RdJFreeChartMan() {
	}
	
	@Override
	public final int getMaxPoints() {
		return maxPoints;
	}

	@Override
	public final void setMaxPoints(int maxPoints) {
		this.maxPoints = maxPoints;
		for (Iterator<TimeSeries> iter = traceByTrace.values().iterator(); iter.hasNext();) {
			TimeSeries trace = iter.next();
			if (trace instanceof TimeSeries) {
				((TimeSeries) trace).setMaximumItemCount(maxPoints);
			}
		}
	}

	@Override
	public final JPanel getChartPanel(String chartName) {
		return panelByChart.get(chartName);
	}

	@Override
	public final JPanel createChartPanel(String chartName) {
		return createChartPanel(chartName, null);
	}
	
	@Override
	public final JPanel createChartPanel(String chartName,PropertyChangeListener listener) {

		JPanel chartpanel = panelByChart.get(chartName);
		if( chartpanel == null ){

			TimeSeriesCollection timeseriescollection = new TimeSeriesCollection();
			JFreeChart chart = UtilJFreeChart.createChart(chartName, timeseriescollection);

			chartpanel = RdWidgets.createPanelBorder(new JComponent[]{UtilJFreeChart.createChartPanel(chart)});
			panelByChart.put(chartName, chartpanel);
			collectionByChart.put(chartName, timeseriescollection);
		}
		return chartpanel;
	}

	@Override
	public final void createTrace(String chartName, String traceName, String quoteName, Color color) {
		
		TimeSeries trace = UtilJFreeChart.createTrace(traceName, getMaxPoints());
		
		TimeSeriesCollection chart = collectionByChart.get(chartName);

		chart.addSeries(trace);

		// Associations
		traceByTrace.put(traceName, trace);
		chartByTrace.put(traceName, chart);

		Set<TimeSeries> list = traceByQuote.get(quoteName);
		if( list == null ){
			list = new HashSet<TimeSeries>();
			traceByQuote.put(quoteName, list);
		}
		list.add(trace);
	}
	
	@Override
	public final void removeTrace(String traceName) {
		if (chartByTrace.containsKey(traceName))
			if (traceByTrace.containsKey(traceName)){
				chartByTrace.get(traceName).removeSeries(traceByTrace.get(traceName));
			}
	}

	@Override
	public final void restoreTrace(String traceName) {
		if (chartByTrace.containsKey(traceName))
			if (traceByTrace.containsKey(traceName))
				chartByTrace.get(traceName).addSeries(traceByTrace.get(traceName));
	}

	@Override
	public final void removeAllPoints(String quoteName) {
		if( quoteName == null ){
			for (Iterator<TimeSeries> iter = traceByTrace.values().iterator(); iter.hasNext();) {
				iter.next().clear();
			}
		} else {
			for (Iterator<Map.Entry<String,Set<TimeSeries>>> iter1 = traceByQuote.entrySet().iterator(); iter1.hasNext();) {
				Map.Entry<String,Set<TimeSeries>> entry = iter1.next();
				if( quoteName.equals(entry.getKey()) ){
					for(Iterator<TimeSeries> iter=entry.getValue().iterator(); iter.hasNext(); ){
						iter.next().clear();
					}
				}
			}
		}
	}

	@Override
	public void drawRegularPoint(DrawPoint dp){
		TimeSeries trace = getTrace(dp.traceName);
		if( trace == null ){
			createTrace(dp.chartName, dp.traceName, dp.quoteName, dp.color);
			trace = getTrace(dp.traceName);
		}
		if( trace != null ){
			//logger.debug(String.format("%s:2: %5.0f = %f", dp.traceName, dp.t, dp.y[j]));
   			trace.add(new Millisecond(dp.time24), dp.val);
		}
	}

	@Override
	public Set<String> getTraces(String chartName) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * 
	 * Couple convinient privates
	 */
	private final TimeSeries getTrace(String traceName) {
		if (traceByTrace.containsKey(traceName)) {
			return traceByTrace.get(traceName);
		} else
			return null;
	}

	@Override
	public void drawSpecialPoint(DrawPoint dp) {
		// TODO Auto-generated method stub
		
	}
}
