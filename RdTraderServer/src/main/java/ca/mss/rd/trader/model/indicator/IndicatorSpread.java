package ca.mss.rd.trader.model.indicator;

import ca.mss.rd.chart.point.DrawPoint;
import ca.mss.rd.trader.server.wkf.ContextProcessQuote;
import ca.mss.rd.util.color.MyColor;


public class IndicatorSpread extends IndicatorDefault {

	final static public String module = IndicatorSpread.class.getSimpleName();

	final private ContextProcessQuote context;

	private String[] traceName;
	private String[] chartName;
	private DrawPoint[] drawPoint;
	
	public IndicatorSpread(ContextProcessQuote context) {
		super("Spread");
		this.context = context;
		init();
		populate(context.charts, chartName);
		populate(context.traces, traceName);
		populate(context.points, drawPoint);
		clean();
	}

	private void init() {

		chartName = new String[]{
				context.symbol+"_SPREAD"
			};
		
		traceName = new String[]{
				context.symbol+"-SPRD"
				,context.symbol+"-SPRD-AVG_MVNG" 
				,context.symbol+"-SPRD-AVG-Up" 
				,context.symbol+"-SPRD-AVG_FLTR"
				,context.symbol+"-SPRD-AVGF-Dn"
			};
		
		drawPoint = new DrawPoint[] { 
					 new DrawPoint(context.symbol, chartName[0], traceName[0], MyColor.getColor("green").darker().darker())
					,new DrawPoint(context.symbol, chartName[0], traceName[1], MyColor.getColor("blue"))
					,new DrawPoint(context.symbol, chartName[0], traceName[2], MyColor.getColor("white"))
					,new DrawPoint(context.symbol, chartName[0], traceName[3], MyColor.getColor("red"))
					,new DrawPoint(context.symbol, chartName[0], traceName[4], MyColor.getColor("white"))
				};
	}

	@Override
	public void clean() {
		clean(drawPoint);
	}

	@Override
	public void draw() {
		drawPoint[0].val = context.spread.val;
		drawPoint[0].time = context.time;
		drawPoint[0].time24 = context.quote.time24;

		drawPoint[1].val = context.spread.md_mvng.getAverage();
		drawPoint[1].time = context.time;
		drawPoint[1].time24 = context.quote.time24;

		drawPoint[2].val = context.spread.getRight();
		drawPoint[2].time = context.time;
		drawPoint[2].time24 = context.quote.time24;

		//drawPoint[3].val = context.spread.ma_fltr.getAverage();
		drawPoint[3].val = context.spread.bl_fltr.getBLValue();
		drawPoint[3].time = context.time;
		drawPoint[3].time24 = context.quote.time24;

		drawPoint[4].val = context.spread.getLeft(); 
		drawPoint[4].time = context.time;
		drawPoint[4].time24 = context.quote.time24;

		context.drawPoint(drawPoint);
	}

	@Override
	public void process() {
		context.time = context.quote.time24.getTime();
		context.spread.calc(context.quote.ask, context.quote.bid);
	}

}
