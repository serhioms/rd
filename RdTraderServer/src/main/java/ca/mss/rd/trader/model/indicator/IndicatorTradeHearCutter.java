package ca.mss.rd.trader.model.indicator;

import ca.mss.rd.stat.BreakLineMM;
import ca.mss.rd.stat.MiniMax;
import ca.mss.rd.trader.model.MarketTrend;
import ca.mss.rd.trader.model.TradeModel;
import ca.mss.rd.trader.model.TradeOrderReport;
import ca.mss.rd.trader.server.wkf.ContextProcessQuote;
import ca.mss.rd.util.Logger;
import ca.mss.rd.util.UtilDateTime;

public class IndicatorTradeHearCutter extends IndicatorDefault {
	
	final private ContextProcessQuote context;
	final private TradeModel<TradeOrderReport> tmodel;

	double totalProfit;
	final BreakLineMM blBSB;
	final BreakLineMM blSBB;
	
	public IndicatorTradeHearCutter(ContextProcessQuote context) {
		super("TradeHearCutter");
		this.context = context;
		this.tmodel = new TradeModel<TradeOrderReport>(){
			public TradeOrderReport newTradeOrderReport(double avgSpread){
				return new TradeOrderReport(this, avgSpread);
			}
		};
		this.blBSB = new BreakLineMM();
		this.blSBB = new BreakLineMM();
		clean();
	}
	
	@Override
	public void process() {

		tmodel.updatePNL(context.quote, blBSB.isMax?MarketTrend.Bool: blSBB.isMax?MarketTrend.Bear: MarketTrend.NA);

		{
			TradeOrderReport order = tmodel.openBuyOrders.get(1.0);
			if( order != null ){
				blBSB.addValue(order.getPnl(), 0.1);
//				if( blBSB.isMax )
//					System.out.println("max");
//				if( blBSB.isMin )
//					System.out.println("min");
			}
			
			order = tmodel.openSellOrders.get(1.0);
			if( order != null ){
				blSBB.addValue(order.getPnl(), 0.1);
//				if( blSBB.isMax )
//					System.out.println("max");
//				if( blSBB.isMin )
//					System.out.println("min");
			}
		}

		if( context.isExtremum == true ){
			if( Logger.TREND_ANALIZER_VERBOSE.isOn ) statusReport(tmodel.openBuyOrders.get(1.0));
			if( Logger.TREND_ANALIZER_VERBOSE.isOn ) statusReport(tmodel.openSellOrders.get(1.0));
		}

		if( TradeModel.DO_CHECK_STOP_LOSS ){
			// TODO: DO_CHECK_STOP_LOSS
//			if( tmodel.closeOrdersSL.size() > 0 ){
//				for(TradeOrderReport order:  tmodel.closeOrdersSL ){
//					if( Logger.TRADE.isOn ){
//						Logger.TRADE.printf(closeReport(order));
//					}
//				}
//			}
		}
		
		if( Logger.TRADE.isOn && !Logger.TREND_ANALIZER_VERBOSE.isOn ){
			if( tmodel.closeOrders.size() > 0 ){
				statusReport(tmodel.openBuyOrders.get(1.0));
				statusReport(tmodel.openSellOrders.get(1.0));
			}
		}
		
		if( TradeModel.DO_CHECK_TOP_PROFIT ){
			// TODO: DO_CHECK_TOP_PROFIT 
//			if( tmodel.closeOrdersTP.size() > 0 ){
//				blBSB.clear();
//				for(TradeOrderReport order:  tmodel.closeOrdersTP ){
//					if( Logger.TRADE.isOn ){
//						Logger.TRADE.printf(closeReport(order));
//					}
//					if( order.isBuy )
//						isPostpondedSBB = true;
//					else
//						isPostpondedBSB = true;
//					
//				}
//			}
		}
		
		if( TradeModel.CLOSE_WHEN_SPREAD_HUGE && context.spread.isHuge() ) {
			tmodel.closeOrders("Abnorm-spread");
			for(TradeOrderReport order:  tmodel.closeOrders ){ // TOTO: must be close all trades?
				if( Logger.TRADE.isOn ){
					Logger.TRADE.printf(closeReport(order));
				}
			}
			return;
		} 

		if( context.stopTrade ){
			if( tmodel.isBuyOrderOpened(1.0) ){
				TradeOrderReport order = tmodel.closeBuyOrder(1.0);
				order.setCloseReason("Stop-BSB");
				if( Logger.TRADE.isOn ){
					if( order != null ){
						Logger.TRADE.printf(closeReport(order));
					}
				}
			}
		}
		
		if( context.stopTrade ){
			if( tmodel.isSellOrderOpened(1.0) ){
				TradeOrderReport order = tmodel.closeSellOrder(1.0);
				order.setCloseReason("Stop-SBB");
				if( Logger.TRADE.isOn ){
					if( order != null ){
						Logger.TRADE.printf(closeReport(order));
					}
				}
			}
		}

		if( context.startBSB || isPostpondedBSB ){
			if( !tmodel.isBuyOrderOpened(1.0) ){
				if( !TradeModel.OPEN_WHEN_SPREAD_TINY || (TradeModel.OPEN_WHEN_SPREAD_TINY && context.spread.isTiny()) ){
					TradeOrderReport order = tmodel.openBuyOrder(1.0, context.quote, context.spread.bl_fltr.getBLValue());
					order.setOpenReason("Start-BSB");
					if( Logger.TRADE.isOn ){
						Logger.TRADE.printf(openReport(order));
					}
					isPostpondedBSB = false;
					postBSB.clear();
				} else if( TradeModel.OPEN_WHEN_SPREAD_TINY ){
					isPostpondedBSB = true;
					postBSB.addValue(context.quote.ask);
					if( Logger.TRADE_VERBOSE.isOn ){
						Logger.TRADE_VERBOSE.printf("%s Postpond BSB [%f>%f=-%5.2f] %f[%f,%f]", UtilDateTime.now(context.quote.time24), context.spread.val, context.spread.ma_fltr.getAverage(), context.spread.val/context.spread.ma_fltr.getAverage(), context.quote.ask, postBSB.min, postBSB.max);
					}
				}
			}
		}
		
		if( (context.startSBB && !isPostpondedBSB) || isPostpondedSBB ){
			if( !tmodel.isSellOrderOpened(1.0) ){
				if( !TradeModel.OPEN_WHEN_SPREAD_TINY || (TradeModel.OPEN_WHEN_SPREAD_TINY && context.spread.isTiny()) ){
					TradeOrderReport order = tmodel.openSellOrder(1.0, context.quote, context.spread.bl_fltr.getBLValue());
					order.setOpenReason("Start-SBB");
					if( Logger.TRADE.isOn ){
						Logger.TRADE.printf(openReport(order));
					}
					isPostpondedSBB = false;
					postSBB.clear();
				} else if( TradeModel.OPEN_WHEN_SPREAD_TINY ){
					isPostpondedSBB = true;
					postSBB.addValue(context.quote.bid);
					if( Logger.TRADE_VERBOSE.isOn ){
						Logger.TRADE_VERBOSE.printf("%s Postpond SBB [%f>%f=-%5.2f] %f[%s,%s]", UtilDateTime.now(context.quote.time24), context.spread.val, context.spread.ma_fltr.getAverage(), context.spread.val/context.spread.ma_fltr.getAverage(), context.quote.bid, postSBB.min, postSBB.max);
					}
				}
			}
		}
		
	}

	boolean isPostpondedBSB, isPostpondedSBB;
	MiniMax postBSB = new MiniMax(),postSBB = new MiniMax(); 

	public String openReport(TradeOrderReport order){
		return String.format("%s", order.report(String.format("\t%15s = %5.2f %s%s", order.openReason, order.closePnl/context.spread.ma_fltr.getAverage(), order.isBuy?postBSB.getMin("[%s,"): postSBB.getMin("[%s,"), order.isBuy?postBSB.getMax("%s]"): postSBB.getMax("%s]"))));
	}
	
	public void statusReport(TradeOrderReport order){
		if( order != null ){
			double profit = order.closePnl/context.spread.ma_fltr.getAverage();
			Logger.TRADE.printf("%s Total profit = %6.2f", order.report(String.format("\t%15s = %5.2f [%4.2f,%4.2f]", "status", profit, order.getPnlMin(), order.getPnlMax())), totalProfit);
		}
	}
	
	
	public String closeReport(TradeOrderReport order){
		double profit = order.closePnl/context.spread.ma_fltr.getAverage();
		totalProfit += profit; 
		return String.format("%s Total profit = %6.2f", order.report(String.format("\t%15s = %5.2f [%4.2f,%4.2f]", order.closeReason, profit,  order.getPnlMin(), order.getPnlMax())), totalProfit);
	}
	
	
	@Override
	public void draw() {
	}
	
	@Override
	public void clean() {
		Logger.INDICATOR.printf("TradeHearCutter.clean");
		tmodel.clear();
		totalProfit = 0;
		blBSB.clear();
		blSBB.clear();
	}

}
