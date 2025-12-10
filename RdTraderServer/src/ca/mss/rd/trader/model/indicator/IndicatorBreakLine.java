package ca.mss.rd.trader.model.indicator;

import ca.mss.rd.chart.point.DrawPoint;
import ca.mss.rd.stat.BreakLine;
import ca.mss.rd.stat.MiniMaxZZ;
import ca.mss.rd.trader.model.TrendBModel;
import ca.mss.rd.trader.model.TrendModel;
import ca.mss.rd.trader.model.TrendModelInt;
import ca.mss.rd.trader.server.wkf.ContextProcessQuote;
import ca.mss.rd.util.Logger;
import ca.mss.rd.util.color.MyColor;

public class IndicatorBreakLine extends IndicatorDefault {

	public static double MINIMAL_PROFITABLE_PIPE = 1.0;

	final public BreakLine bl = new BreakLine();
	
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

	public IndicatorBreakLine(ContextProcessQuote context, int index, double pipe, String color) {
		super("BreakLine");
		this.context = context;
		this.color = color;
		this.index = index;
		this.bl.pipe = pipe;

		if( context.bl.size() <= index){
			for(int i=context.bl.size(); i<= index; i++ ){
				context.bl.add(null);
			}
		}
		context.bl.set(index, this);
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
				context.symbol+"-BLine "+bl.pipe
		};
		
		drawPoint = new DrawPoint[] { 
				  new DrawPoint(context.symbol, chartName[0], traceName[0], MyColor.getColor(color))
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
