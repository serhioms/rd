package ca.mss.rd.trader.model.indicator;

import ca.mss.rd.chart.point.DrawPoint;
import ca.mss.rd.trader.model.MarketTrend;
import ca.mss.rd.trader.model.TradeModel;
import ca.mss.rd.trader.server.wkf.ContextProcessQuote;
import ca.mss.rd.util.color.MyColor;


public class IndicatorTrade extends IndicatorDefault {

	final static public String module = IndicatorTrade.class.getSimpleName();

	final private ContextProcessQuote context;

	private String[] traceName;
	private String[] chartName;
	private DrawPoint[] drawPoint;
	
	public IndicatorTrade(ContextProcessQuote context) {
		super("Trade");

		this.context = context;
		init();
		populate(context.charts, chartName);
		populate(context.traces, traceName);
		populate(context.points, drawPoint);
		clean();
	}

	private void init() {

		chartName = new String[]{
				context.symbol+"_PROFIT"
			};
		
		traceName = new String[]{
				 context.symbol+"-TTL"
				,context.symbol+"-CUR"
			};
		
		drawPoint = new DrawPoint[] { 
				  new DrawPoint(context.symbol, chartName[0], traceName[0], MyColor.getColor("green").darker().darker())
				 ,new DrawPoint(context.symbol, chartName[0], traceName[1], MyColor.getColor("magenta").darker().darker())
			};
	}

	@Override
	public void clean() {
		clean(drawPoint);
	}

	@Override
	public void draw() {
		int i=0;
		
		drawPoint[i].val = context.tm.getTotalProfit();
		drawPoint[i].time = context.time;
		drawPoint[i++].time24 = context.quote.time24;
		
		drawPoint[i].val = context.tm.getCurrentProfit();
		drawPoint[i].time = context.time;
		drawPoint[i++].time24 = context.quote.time24;
		
		context.drawPoint(drawPoint);
	}

	@Override
	public void process() {
		
		if( context.tm.isAnyOrderOpened() ){
			context.tm.updatePNL(context.quote, context.marketTrend);
			
			if( TradeModel.DO_CHECK_STOP_LOSS ){
				if( context.tm.closeAndReport(context.tm.checkStopLoss(TradeModel.S_L), TradeModel.S_L) ){
					// TODO: Change trend on S/L - not so simple. Change trend based on integral 
//					context.marketTrend = MarketTrend.NA;
//					if( context.marketTrend == MarketTrend.Bool )
//						context.marketTrend = MarketTrend.Bear;
//					else if( context.marketTrend == MarketTrend.Bear )
//						context.marketTrend = MarketTrend.Bool;
				}
			}
			
			if( TradeModel.DO_CHECK_TOP_PROFIT ){
				context.tm.closeAndReport(context.tm.checkTopProfit(TradeModel.T_P, context.marketTrend == MarketTrend.Bool, context.marketTrend == MarketTrend.Bear ), TradeModel.T_P);
			}
			
			
		} 

		if( context.marketTrend == MarketTrend.Bool && !context.tm.isBuyOrderOpened() ){
			context.startBSB = true;
			if( context.tm.isSellOrderOpened() ){
				context.tm.closeAndReport(context.tm.closeSellOrder(TradeModel.BOOL_COMING), TradeModel.BOOL_COMING);
			}
		}
		
		if( context.marketTrend == MarketTrend.Bear && !context.tm.isSellOrderOpened() ){
			context.startSBB = true;
			if( context.tm.isBuyOrderOpened() ){
				context.tm.closeAndReport(context.tm.closeBuyOrder(TradeModel.BEAR_COMING), TradeModel.BEAR_COMING);
			}
		}
		
		if( context.startBSB ){
			if( !TradeModel.OPEN_WHEN_SPREAD_TINY || context.spread.isTiny() ){
				context.tm.openBuyOrder(1.0, context.quote, context.spread.bl_fltr.getBLValue());
				context.startBSB = false;
			}
		}

		if( context.startSBB ){
			if( !TradeModel.OPEN_WHEN_SPREAD_TINY || context.spread.isTiny() ){
				context.tm.openSellOrder(1.0, context.quote, context.spread.bl_fltr.getBLValue());
				context.startSBB = false;
			}
		}
	}

	@Override
	public void stop() {
		context.tm.closeAndReport(context.tm.closeSellOrder(TradeModel.END_OF_THE_DAY), TradeModel.END_OF_THE_DAY);
		context.startSBB = false;
		
		context.tm.closeAndReport(context.tm.closeBuyOrder(TradeModel.END_OF_THE_DAY), TradeModel.END_OF_THE_DAY);
		context.startBSB = false;
	}
	
}
