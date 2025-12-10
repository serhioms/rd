package ca.mss.rd.chart.manager;

import java.awt.Color;
import java.beans.PropertyChangeListener;
import java.util.Set;

import javax.swing.JPanel;

import ca.mss.rd.chart.point.DrawPoint;

public interface RdChartManager {
	
	static public String EVENT_CHART_NEAREST_X = "ChartmanNearestX";
	
	public void setMaxPoints(int max);
	public int getMaxPoints();

	public JPanel createChartPanel(String chartName);
	public JPanel createChartPanel(String chartName,PropertyChangeListener listener);

	public JPanel getChartPanel(String chartName);

	public void createTrace(String chartName, String traceName, String quoteName, Color color);
	public void restoreTrace(String traceName);
	public void removeTrace(String traceName);
	public Set<String> getTraces(String chartName);

	public void drawRegularPoint(DrawPoint dp);
	public void drawSpecialPoint(DrawPoint dp);
	
	public void removeAllPoints(String quoteName);

	public void setTimingStart();
	public void setTimingFinish();
}
