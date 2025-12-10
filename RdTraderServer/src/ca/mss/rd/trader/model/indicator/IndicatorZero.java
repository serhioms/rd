package ca.mss.rd.trader.model.indicator;

import ca.mss.rd.chart.point.DrawPoint;
import ca.mss.rd.trader.server.wkf.ContextProcessQuote;
import ca.mss.rd.util.color.MyColor;

public class IndicatorZero extends IndicatorDefault {

	/*
	 * Constants
	 */
	final private ContextProcessQuote context;
	final public String color;
	
	private String[] traceName;
	private String[] chartName;
	private DrawPoint[] drawPoint;

	public IndicatorZero(ContextProcessQuote context, String color) {
		super("Zero");
		
		this.context = context;
		this.color = color;

		init();
		
		populate(context.charts, chartName);
		populate(context.traces, traceName);
		populate(context.points, drawPoint);
		
		clean();
	}

	private void init() {
		chartName = new String[]{
				context.symbol+"Trend"
		};
		
		traceName = new String[]{
				 context.symbol+"-TrendZero "
		};
		
		drawPoint = new DrawPoint[] { 
				  new DrawPoint(context.symbol, chartName[0], traceName[0], MyColor.getColor(color).darker().darker())
		};
	}

	@Override
	public void draw() {
		drawPoint[0].val = 0;
		drawPoint[0].time = context.time;
		drawPoint[0].time24 = context.quote.time24;

		context.drawPoint(drawPoint);
	}

	@Override
	public void clean() {
	}

	@Override
	public void process() {
	}

}
