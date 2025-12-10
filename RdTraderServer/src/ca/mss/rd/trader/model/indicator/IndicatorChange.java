package ca.mss.rd.trader.model.indicator;

import ca.mss.rd.chart.point.DrawPoint;
import ca.mss.rd.stat.Change;
import ca.mss.rd.trader.server.wkf.ContextProcessQuote;
import ca.mss.rd.util.color.MyColor;

public class IndicatorChange extends IndicatorDefault {

	final private ContextProcessQuote context;
	final private Change ch;
	final private String color;
	
	private DrawPoint[] drawPoint;
	private String[] traceName;
	private String[] chartName;

	public IndicatorChange(ContextProcessQuote context, String color) {
		super("Change");
		this.context = context;
		this.color = color;
		this.ch = new Change();
		
		init();
		populate(context.charts, chartName);
		populate(context.traces, traceName);
		populate(context.points, drawPoint);
	}

	final private void init() {
		chartName = new String[]{context.symbol};
		
		traceName = new String[]{context.symbol+"-CH"};
		
		drawPoint = new DrawPoint[] { 
				 new DrawPoint(context.symbol, chartName[0], traceName[0], MyColor.getColor(color))
				};
	}

	@Override
	final public void draw() {
		if( ch.isChange() ){
			drawPoint[0].val = ch.getValue();
			drawPoint[0].time = context.time;
			drawPoint[0].time24 = context.quote.time24;
			
			context.drawPoint(drawPoint);
		}
	}

	@Override
	public void clean() {
		clean(drawPoint);
		ch.clean();
	}

	@Override
	public void process() {
		ch.addValue(context.val);
	}

}
