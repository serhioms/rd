package ca.mss.rd.trader.model.indicator;

import ca.mss.rd.chart.point.DrawPoint;
import ca.mss.rd.stat.AverageMooving;
import ca.mss.rd.stat.BreakLine;
import ca.mss.rd.trader.server.wkf.ContextProcessQuote;
import ca.mss.rd.util.color.MyColor;

public class IndicatorBLTrend extends IndicatorDefault {

	public static boolean REAL_TIME = false;

	final public BreakLine bl = new BreakLine();
	final public AverageMooving avg = new AverageMooving(10000); 
	
	private double trend, forecast, avgforecast;
	
	/*
	 * Constants
	 */
	final private ContextProcessQuote context;
	final public String color;
	final public int index;
	
	private String[] traceName;
	private String[] chartName;
	private DrawPoint[] drawPoint;

	private boolean isStop;
	
	public IndicatorBLTrend(ContextProcessQuote context, int index, double pipe, String color) {
		super(String.format("BLTrend[%d]", index));
		
		this.context = context;
		this.color = color;
		this.index = index;
		this.bl.pipe = pipe;

		if( context.blt.size() <= index){
			for(int i=context.blt.size(); i<= index; i++ ){
				context.blt.add(null);
			}
		}
		context.blt.set(index, this);

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
				 context.symbol+"-BLTrend "+bl.pipe
				,context.symbol+"-BLTrendFor "+bl.pipe
				,context.symbol+"-BLTrendForAvg "+bl.pipe
		};
		
		drawPoint = new DrawPoint[] { 
				  new DrawPoint(context.symbol, chartName[0], traceName[0], MyColor.getColor(color))
					 ,new DrawPoint(context.symbol, chartName[0], traceName[1], MyColor.getColor(color).darker())
					 ,new DrawPoint(context.symbol, chartName[0], traceName[2], MyColor.getColor(color).darker().darker())
		};
	}

	@Override
	public void clean() {
		clean(drawPoint);
		bl.clear();
		context.quote.clear();
		trend = forecast = 0.0;
		avg.clear();
	}

	@Override
	public void draw() {
		drawPoint[0].val = trend;
		drawPoint[0].time = context.time;
		drawPoint[0].time24 = context.quote.time24;
		
		drawPoint[1].val = forecast;
		drawPoint[1].time = context.time;
		drawPoint[1].time24 = context.quote.time24;
		
		drawPoint[2].val = avgforecast;
		drawPoint[2].time = context.time;
		drawPoint[2].time24 = context.quote.time24;
		
		context.drawPoint(drawPoint);
		
		// 
		if( bl.isBroken() ){
			System.out.println(String.format("%2.2f %s = %f", bl.pipe, bl.isGoingUp()?"UP":bl.isGoingDn()?"DN":"", avg.getAverage()));
			avg.clear();
			if( bl.pipe >= 2.0 ){
				isStop = true;
			}
		}
	}

	@Override
	public void process() {
		
		if( isStop ){
			isStop = false;
			System.out.print("");
		}
		
		double avgSpread = context.spread.ma_fltr.getAverage();
		double limit = bl.pipe * avgSpread;
		
		bl.addValue(context.mid, limit);
		
		if( bl.delta > 0 ){
			trend = bl.pipe; // limit/avgSpread
			forecast = bl.delta/avgSpread;
		} else if( bl.delta < 0 ){
			trend = -bl.pipe; // limit/avgSpread
			forecast = bl.delta/avgSpread;
		} else {
			// previous trend and forecast
		}
		
		avg.addValue(forecast);

		avgforecast = avg.getAverage();
	}

}
