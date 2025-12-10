package ca.mss.rd.trader.model.indicator;

import ca.mss.rd.chart.point.DrawPoint;
import ca.mss.rd.stat.BreakLine;
import ca.mss.rd.trader.server.wkf.ContextProcessQuote;
import ca.mss.rd.util.UtilDateTime;
import ca.mss.rd.util.color.MyColor;

public class IndicatorBreakTime extends IndicatorDefault {

	public static boolean REAL_TIME = false;

	final public BreakLine bl = new BreakLine();
	
	long startTimeUp, startTimeDn, timeUp, timeDn, timeNow; 
	
	/*
	 * Constants
	 */
	final private ContextProcessQuote context;
	final public String color;
	final public int index;
	
	private String[] traceName;
	private String[] chartName;
	private DrawPoint[] drawPoint;
	
	public IndicatorBreakTime(ContextProcessQuote context, int index, double pipe, String color) {
		super(String.format("BreakTime[%d]", index));
		
		this.context = context;
		this.color = color;
		this.index = index;
		this.bl.pipe = pipe;

		if( context.btime.size() <= index){
			for(int i=context.btime.size(); i<= index; i++ ){
				context.btime.add(null);
			}
		}
		context.btime.set(index, this);

		init();
		
		populate(context.charts, chartName);
		populate(context.traces, traceName);
		populate(context.points, drawPoint);
		
		clean();
	}

	private void init() {
		chartName = new String[]{
				 context.symbol+"BreakTime"
				,context.symbol+"Trend"
		};
		
		traceName = new String[]{
				 context.symbol+"-BTimeUP "+bl.pipe
				,context.symbol+"-BTimeDN "+bl.pipe
				,context.symbol+"-BTimeTrend "+bl.pipe
				,context.symbol+"-BTimeFor "+bl.pipe
		};
		
		drawPoint = new DrawPoint[] { 
				  new DrawPoint(context.symbol, chartName[0], traceName[0], MyColor.getColor(color))
				 ,new DrawPoint(context.symbol, chartName[0], traceName[1], MyColor.getColor(color).darker().darker())
				 ,new DrawPoint(context.symbol, chartName[1], traceName[2], MyColor.getColor(color).darker())
				 ,new DrawPoint(context.symbol, chartName[1], traceName[3], MyColor.getColor(color).darker().darker())
		};
	}

	@Override
	public void clean() {
		clean(drawPoint);
		bl.clear();
		context.quote.clear();
		startTimeUp = startTimeDn = (REAL_TIME? UtilDateTime.now().getTime(): context.quote.quoteCounter);
	}

	@Override
	public void draw() {
		drawPoint[0].val = timeUp * (REAL_TIME? 0.001: 3.0)/60.0; // min
		drawPoint[0].time = context.time;
		drawPoint[0].time24 = context.quote.time24;
		
		drawPoint[1].val = timeDn * (REAL_TIME? 0.001: 3.0)/60.0; // min
		drawPoint[1].time = context.time;
		drawPoint[1].time24 = context.quote.time24;
		
		drawPoint[2].val = context.mid > bl.getBLValue() ? bl.pipe: -bl.pipe;
		drawPoint[2].time = context.time;
		drawPoint[2].time24 = context.quote.time24;
		
		drawPoint[3].val = ((context.mid - bl.getBLValue())/context.spread.ma_fltr.getAverage())*bl.pipe;
		drawPoint[3].time = context.time;
		drawPoint[3].time24 = context.quote.time24;
		
		context.drawPoint(drawPoint);
	}

	@Override
	public void process() {
		bl.addValue(context.mid, bl.pipe * context.spread.ma_fltr.getAverage());
		
		if( bl.isBroken() ){
			if( bl.isGoingDn() ){
				startTimeDn = (REAL_TIME? UtilDateTime.now().getTime(): context.quote.quoteCounter);
			} else if( bl.isGoingUp() ){
				startTimeUp = (REAL_TIME? UtilDateTime.now().getTime(): context.quote.quoteCounter);
			} else {
				throw new RuntimeException(String.format("%s is broken unexpected way...", getName()));
			}
		} else {
			timeNow = (REAL_TIME? UtilDateTime.now().getTime(): context.quote.quoteCounter);
			timeUp = timeNow - startTimeUp;
			timeDn = timeNow - startTimeDn;
		}
	}
	
}
