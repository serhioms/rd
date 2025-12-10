package ca.mss.rd.trader.deprecated.model.indicator;

import java.awt.Color;

import ca.mss.rd.trader.model.Point;

public interface Indicator {
	
	public void start();

	public void process(Point point);

	public int size();
	public Point getPoint(int i);
	public boolean isChange(int i);

	public boolean isVisible(int i);

	public String[] getInEvent();
	public String getOutEvent(int i);
	public String getChartName(int i);
	public String getTraceName(int i);
	public Color getColor(int i);

	public String getIdent();
}
