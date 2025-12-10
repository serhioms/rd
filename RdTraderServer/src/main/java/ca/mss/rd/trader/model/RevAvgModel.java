package ca.mss.rd.trader.model;

import java.util.Date;

import ca.mss.rd.stat.AverageMooving;
import ca.mss.rd.stat.MiniMaxLocal;
import ca.mss.rd.trader.model.indicator.IndicatorRevAvg;
import ca.mss.rd.trader.model.indicator.ProfitSurface;
import ca.mss.rd.trader.server.wkf.ContextProcessQuote;
import ca.mss.rd.util.Logger;
import ca.mss.rd.util.UtilDateTime;
import ca.mss.rd.util.map.SmartMap;

public class RevAvgModel {

	private static final boolean ORDER_CLOSE = false;
	private static final boolean ORDER_OPEN = true;

	public static final double PIP = 10000.0;

	private final ContextProcessQuote context;
	
	private double tradeCounter;
	private int headerCnt;
	public double totalProfit;

	
	volatile private ProfitSurface profitSurface;

	public class RevAvgData {
		public final MiniMaxLocal mmlocal = new MiniMaxLocal();
		public final TradeModel<TradeOrderReport> tmodel = new TradeModel<TradeOrderReport>(){

			@Override
			public TradeOrderReport newTradeOrderReport(double avgSpread) {
				return new TradeOrderReport(this, avgSpread);
			}
			
		};
		public final String key;
		public RevAvgData(String key) {
			this.key = key;
		}
	}
	
	@SuppressWarnings("serial")
	final private SmartMap<String, RevAvgData> data = new SmartMap<String, RevAvgData>(){
		@Override
		public RevAvgData valueFactory(Object key) {
			return new RevAvgData(key.toString());
		}
		
	};
	
	public RevAvgModel(ContextProcessQuote context){
		this.context = context;
	}

	public void clean() {
		data.clear();
		tradeCounter = 1.0;
		headerCnt = 0;
		totalProfit = 0.0;
	}

	public void stop() {
		for( RevAvgData d: data.values()){
			if( d.tmodel.isBuyOrderOpened(1.0) ){
				totals(d.tmodel.closeBuyOrder(1.0), ORDER_CLOSE, Logger.TRADE_CLOSE, d.key, d);
			}
			if( d.tmodel.isSellOrderOpened(1.0) ){
				totals(d.tmodel.closeSellOrder(1.0), ORDER_CLOSE, Logger.TRADE_CLOSE, d.key, d);
			}
		}
		if( Logger.TRADE_CLOSE_FINAL.isOn ){
			Logger.TRADE_CLOSE_FINAL.printf("%s", data.values().iterator().next().tmodel.reportHeader);
			for( RevAvgData d: data.values()){
				Logger.TRADE_CLOSE_FINAL.printf("%s", d.tmodel.reportBody);
			}
		}
	}
	
	public void process(AverageMooving slow, AverageMooving fast, ProfitSurface profitSurface) {
		
		this.profitSurface = profitSurface;
		
		String slowfast = String.format("%d/%d", slow.getWindowSize(), fast.getWindowSize());
		RevAvgData radata = data.get(slowfast);
		
		TradeModel<TradeOrderReport> tmodel = radata.tmodel;
		MiniMaxLocal mmlocal = radata.mmlocal;
		
		Date time = context.quote.time24;
		double bid = context.quote.bid;
		double ask = context.quote.ask;
		double mid = (bid + ask) * 0.5;
		double spread = ask - bid;
		
		double favg = fast.getAverage();
		double savg = slow.getAverage();
		
		double davg = (favg - savg)*PIP;
		
		mmlocal.addValue(davg, time);

		String now = UtilDateTime.now(time);
		
		assert( Logger.REVAVG_VERBOSE.isOn ? Logger.REVAVG_VERBOSE.printf("%s {delta=%5.2f pip} <= {fast=%.6f, slow=%.6f} <= {mid=%.6f, sprd=%5.2f pip} <={bid=%.6f, ask=%.6f}", now, davg, favg, savg, mid, spread*PIP, bid, ask): true);

		if( mmlocal.isMax || mmlocal.isMin ){
			assert( Logger.REVAVG_MINIMAX.isOn ? Logger.REVAVG_MINIMAX.printf("%3d) %s {delta=%5.2f pip} <= %s was %s", mmlocal.minimaxCounter, now, mmlocal.getExtremum(), (mmlocal.isMax?"mAX":"mIN"), UtilDateTime.now(mmlocal.getExtremumTime()) ): true);

			tmodel.updatePNL(context.quote, MarketTrend.NA);

			if( mmlocal.isMax && favg > savg){
				if( tmodel.isBuyOrderOpened() ){
					for(TradeOrderReport order : tmodel.closeBuyOrder("Close before open SBB on MAX when fast > slow") ){
						totals(order, ORDER_CLOSE, Logger.TRADE_CLOSE, slowfast, radata);
					}
				}
				if( IndicatorRevAvg.IS_OPEN_COUPLE_TRADES ) tradeCounter += 1.0;
				if( !tmodel.isSellOrderOpened(tradeCounter) ){
					totals(tmodel.openSellOrder(tradeCounter, context.quote, context.spread.bl_fltr.getBLValue()), ORDER_OPEN, Logger.TRADE_OPEN, slowfast, radata);
				}
			} else if( mmlocal.isMin && favg < savg){
				if( tmodel.isSellOrderOpened() ){
					for(TradeOrderReport order : tmodel.closeSellOrder("Close before open BSB on MIN when fast < slow") ){
						totals(order, ORDER_CLOSE, Logger.TRADE_CLOSE, slowfast, radata);
					}
				}
				if( IndicatorRevAvg.IS_OPEN_COUPLE_TRADES ) tradeCounter += 1.0;
				if( !tmodel.isBuyOrderOpened(tradeCounter) ){
					totals(tmodel.openBuyOrder(tradeCounter, context.quote, context.spread.bl_fltr.getBLValue()), ORDER_OPEN, Logger.TRADE_OPEN, slowfast, radata);
				}
			}
		}
		
		if( Logger.REVAVG.isOn ) 
			if( radata.tmodel.getTotalProfit() != 0.0 ) 
				Logger.REVAVG.printf("%s done profit = %.2f", slowfast, radata.tmodel.getTotalProfit());
		
	}

	public void totals(TradeOrderReport order, boolean isOpen, Logger log, String key, RevAvgData radata){
		if( order != null ){

			String[] slowfast = key.split("[/,]");
			int slow = Integer.parseInt(slowfast[0]);
			int fast = Integer.parseInt(slowfast[1]);
			
			String body = order.reportBody(isOpen, IndicatorRevAvg.IS_OPEN_COUPLE_TRADES?"MANY TRADES":"ONE TRADE", slow, fast);
			
			if( log.isOn ){
				if( headerCnt++ % 10 == 0 ){ 
					log.printf("%s", order.reportHeader());
				}
				log.printf("%s", body);
			}
			
			if( !isOpen ){
				totalProfit = radata.tmodel.getTotalProfit();
				profitSurface.addPoint(slow, fast, radata.tmodel.getTotalProfit());
			}
		}
	}

}
