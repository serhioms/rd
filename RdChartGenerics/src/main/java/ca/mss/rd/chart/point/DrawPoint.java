package ca.mss.rd.chart.point;

import java.awt.Color;
import java.util.Date;

public class DrawPoint {
	
	final public String quoteName;
	final public String chartName;
	final public String traceName;
	final public Color color;
	
	public double val, preval;
	public long time;
	public Date time24;
	
	public DrawPoint(String quoteName,  String chartName, String traceName, Color color){
		this.quoteName = quoteName;
		this.chartName = chartName;
		this.traceName = traceName;
		this.color = color;
	}

}

