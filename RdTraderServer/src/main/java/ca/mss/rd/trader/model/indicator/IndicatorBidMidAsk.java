package ca.mss.rd.trader.model.indicator;

import ca.mss.rd.chart.point.DrawPoint;
import ca.mss.rd.trader.server.wkf.ContextProcessQuote;
import ca.mss.rd.util.color.MyColor;


public class IndicatorBidMidAsk extends IndicatorDefault {

	final private ContextProcessQuote context;

	private String[] chartName;
	private String[] traceName;
	private DrawPoint[] drawPoint;

	public IndicatorBidMidAsk(ContextProcessQuote context) {
		super("BidMidAsk");
		this.context = context;
		init();
		populate(context.charts, chartName);
		populate(context.traces, traceName);
		populate(context.points, drawPoint);
		clean();
	}

	private void init() {
		chartName = new String[]{context.symbol};
		
		traceName = new String[]{
				context.symbol+"-MID", 
				context.symbol+"-BID", 
				context.symbol+"-ASK"
			};
		
		drawPoint = new DrawPoint[] { 
				 new DrawPoint(context.symbol, chartName[0], traceName[0], MyColor.getColor("yellow").darker().darker())
				,new DrawPoint(context.symbol, chartName[0], traceName[1], MyColor.getColor("green").darker().darker())
				,new DrawPoint(context.symbol, chartName[0], traceName[2], MyColor.getColor("green").darker().darker())
			};
	}

	@Override
	public void clean() {
		clean(drawPoint);
	}

	@Override
	public void draw() {
		
		drawPoint[0].val = context.mid;
		drawPoint[0].time = context.time;
		drawPoint[0].time24 = context.quote.time24;

		drawPoint[1].val = context.quote.bid;
		drawPoint[1].time = context.time;
		drawPoint[1].time24 = context.quote.time24;

		drawPoint[2].val = context.quote.ask;
		drawPoint[2].time = context.time;
		drawPoint[2].time24 = context.quote.time24;

		context.drawPoint(drawPoint);
	}

	@Override
	public void process() {
		context.time = context.quote.time24.getTime();
		context.mid = (context.quote.bid + context.quote.ask) * 0.5;
		context.val = context.mid;
	}
}
