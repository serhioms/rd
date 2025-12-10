package ca.mss.rd.trader.model.indicator;

import ca.mss.rd.chart.point.DrawPoint;
import ca.mss.rd.trader.model.PNLModel;
import ca.mss.rd.trader.server.wkf.ContextProcessQuote;
import ca.mss.rd.util.color.MyColor;


public class IndicatorPNL extends IndicatorDefault {

	final static public String module = IndicatorPNL.class.getSimpleName();

	final private ContextProcessQuote context;

	private String[] traceName;
	private String[] chartName;
	private DrawPoint[] drawPoint;
	
	public IndicatorPNL(ContextProcessQuote context) {
		super("PNL");
		this.context = context;
		init();
		populate(context.charts, chartName);
		populate(context.traces, traceName);
		populate(context.points, drawPoint);
		clean();
	}

	private void init() {

		chartName = new String[]{
				context.symbol+"_PNL"
			};
		
		traceName = new String[]{
				 context.symbol+"-PNL-Sum"
				,context.symbol+"-PNL-Avg"+PNLModel.PNL_MA_LEVEL
				,context.symbol+"-PNL-Up" 
				,context.symbol+"-PNL-Dn"
				,context.symbol+"-PNL-Avg"+PNLModel.PNL_MA_DEPTH
				,context.symbol+"-PNL-Dif"
				,context.symbol+"-PNL-Dif"+PNLModel.PNL_MA_DEPTH
				,context.symbol+"-PNL"
				,context.symbol+"-PNL-Bool"
				,context.symbol+"-PNL-Bear"
			};
		
		drawPoint = new DrawPoint[] { 
				 	 new DrawPoint(context.symbol, chartName[0], traceName[0], MyColor.getColor("magenta"))
				 	,new DrawPoint(context.symbol, chartName[0], traceName[0], MyColor.getColor("magenta"))
				 	,new DrawPoint(context.symbol, chartName[0], traceName[1], MyColor.getColor("magenta"))
				 	,new DrawPoint(context.symbol, chartName[0], traceName[1], MyColor.getColor("magenta"))
					,new DrawPoint(context.symbol, chartName[0], traceName[2], MyColor.getColor("white"))
					,new DrawPoint(context.symbol, chartName[0], traceName[3], MyColor.getColor("white"))
				 	,new DrawPoint(context.symbol, chartName[0], traceName[4], MyColor.getColor("magenta"))
				 	,new DrawPoint(context.symbol, chartName[0], traceName[5], MyColor.getColor("deepcyan"))
				 	,new DrawPoint(context.symbol, chartName[0], traceName[6], MyColor.getColor("cyan"))
				 	,new DrawPoint(context.symbol, chartName[0], traceName[7], MyColor.getColor("green").darker())
				 	,new DrawPoint(context.symbol, chartName[0], traceName[8], MyColor.getColor("green").darker())
				 	,new DrawPoint(context.symbol, chartName[0], traceName[9], MyColor.getColor("green").darker())
				};
	}

	@Override
	public void clean() {
		clean(drawPoint);
	}

	@Override
	public void draw() {
		int i=0;
		drawPoint[i].val = context.pnl.sum;
		drawPoint[i].time = context.time;
		drawPoint[i++].time24 = context.quote.time24;

		drawPoint[i].val = 0.0;
		drawPoint[i].time = context.time;
		drawPoint[i++].time24 = context.quote.time24;

		drawPoint[i].val = context.pnl.avgN;
		drawPoint[i].time = context.time;
		drawPoint[i++].time24 = context.quote.time24;

		drawPoint[i].val = 0.0;
		drawPoint[i].time = context.time;
		drawPoint[i++].time24 = context.quote.time24;

		drawPoint[i].val = context.pnl.up;
		drawPoint[i].time = context.time;
		drawPoint[i++].time24 = context.quote.time24;

		drawPoint[i].val = context.pnl.dn;
		drawPoint[i].time = context.time;
		drawPoint[i++].time24 = context.quote.time24;

		drawPoint[i].val = context.pnl.avg1;
		drawPoint[i].time = context.time;
		drawPoint[i++].time24 = context.quote.time24;

		drawPoint[i].val = context.pnl.diff;
		drawPoint[i].time = context.time;
		drawPoint[i++].time24 = context.quote.time24;

		drawPoint[i].val = context.pnl.diff2;
		drawPoint[i].time = context.time;
		drawPoint[i++].time24 = context.quote.time24;

		drawPoint[i].val = context.pnl.val;
		drawPoint[i].time = context.time;
		drawPoint[i++].time24 = context.quote.time24;

		drawPoint[i].val = context.pnl.bool;
		drawPoint[i].time = context.time;
		drawPoint[i++].time24 = context.quote.time24;

		drawPoint[i].val = context.pnl.bear;
		drawPoint[i].time = context.time;
		drawPoint[i++].time24 = context.quote.time24;
		
		context.drawPoint(drawPoint);
	}

	@Override
	public void process() {
		context.pnl.calc(context.quote);
	}

}
