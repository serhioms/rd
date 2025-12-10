package ca.mss.rd.trader.model.indicator;

import ca.mss.rd.chart.point.DrawPoint;
import ca.mss.rd.stat.BreakLineMM;
import ca.mss.rd.stat.MiniMaxZZ;
import ca.mss.rd.trader.model.TrendBModel;
import ca.mss.rd.trader.model.TrendModel;
import ca.mss.rd.trader.model.TrendModelInt;
import ca.mss.rd.trader.server.wkf.ContextProcessQuote;
import ca.mss.rd.util.Logger;
import ca.mss.rd.util.color.MyColor;

public class IndicatorBreakLineMM extends IndicatorDefault {

	public static double MINIMAL_PROFITABLE_PIPE = 1.0;

	final public BreakLineMM bl = new BreakLineMM();
	
	final public TrendModelInt trend;
	
	double minBid, minAsk, maxBid, maxAsk;
	double sumBSB, sumSBB;
	MiniMaxZZ bsb = new MiniMaxZZ(), sbb = new MiniMaxZZ();


	/*
	 * Constants
	 */
	final private ContextProcessQuote context;
	final public String color;
	final public int index;
	
	private String[] traceName;
	private String[] chartName;
	private DrawPoint[] drawPoint;
	
	private double limit;

	public IndicatorBreakLineMM(ContextProcessQuote context, int index, double pipe, String color) {
		super("BreakLineMM["+pipe+"]");
		this.context = context;
		this.color = color;
		this.index = index;
		this.bl.pipe = pipe;

		if( context.blmm.size() <= index){
			for(int i=context.blmm.size(); i<= index; i++ ){
				context.blmm.add(null);
			}
		}
		context.blmm.set(index, this);
		trend = index == 0? new TrendBModel(): new TrendModel();

		init();
		
		populate(context.charts, chartName);
		populate(context.traces, traceName);
		populate(context.points, drawPoint);
		
		clean();
	}

	private void init() {
		chartName = new String[]{context.symbol};
		
		traceName = new String[]{
				context.symbol+"-BLineMM "+bl.pipe,
				context.symbol+"-BLineMM-MAX "+bl.pipe, 
				context.symbol+"-BLineMM-MIN "+bl.pipe};
		
		drawPoint = new DrawPoint[] { 
				  new DrawPoint(context.symbol, chartName[0], traceName[0], MyColor.getColor(color))
				 ,new DrawPoint(context.symbol, chartName[0], traceName[1], MyColor.getColor("red"))
				 ,new DrawPoint(context.symbol, chartName[0], traceName[2], MyColor.getColor("green"))
			};
	}

	@Override
	public void clean() {
		assert( Logger.INDICATOR.isOn ? Logger.INDICATOR.printf("BreakLineMM.clean[%d]", index): true);
		
		clean(drawPoint);
		bl.clear();
		trend.clear();
		
		minBid = minAsk = maxBid = maxAsk = Double.POSITIVE_INFINITY;
		sumBSB = sumSBB = 0.0;
		bsb.clear();
		sbb.clear();
	}

	@Override
	public void draw() {
		drawPoint[0].val = bl.getBLValue();
		drawPoint[0].time = context.time;
		drawPoint[0].time24 = context.quote.time24;
		
		drawPoint[1].val = bl.max;
		drawPoint[1].time = context.time;
		drawPoint[1].time24 = context.quote.time24;
		
		drawPoint[2].val = bl.min;
		drawPoint[2].time = context.time;
		drawPoint[2].time24 = context.quote.time24;
		
		context.drawPoint(drawPoint);
	}

	@Override
	public void process() {
		if( minBid == Double.POSITIVE_INFINITY) {
			minBid = maxBid = context.quote.bid;
			maxAsk = minAsk = context.quote.ask;	
		}
		
		limit = bl.pipe * context.spread.ma_fltr.getAverage();
		bl.addValue(context.mid, limit);
	}

	public boolean isProfitable() {
		return bl.pipe >= MINIMAL_PROFITABLE_PIPE;
	}

	public boolean isTrendable() {
		return bl.pipe < MINIMAL_PROFITABLE_PIPE;
	}
	
}
