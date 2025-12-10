package ca.mss.rd.trader.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import ca.mss.rd.stat.AverageMooving;
import ca.mss.rd.stat.MiniMaxLocal;
import ca.mss.rd.trader.model.cube.MaximumHistory;
import ca.mss.rd.trader.model.cube.RevAvgCubeFinder.MACube2.MAPoint;
import ca.mss.rd.trader.model.indicator.IndicatorRevAvg;
import ca.mss.rd.trader.server.wkf.ContextProcessQuote;
import ca.mss.rd.util.Logger;
import ca.mss.rd.util.UtilDateTime;
import ca.mss.rd.util.map.SmartMap;

public class RevAvgCubeModel {

	private static final boolean ORDER_CLOSE = false;
	private static final boolean ORDER_OPEN = true;

	public static final double PIP = 10000.0;

	private final ContextProcessQuote context;
	
	private double tradeCounter;
	private int headerCnt;

	final private Set<Integer> avcach = new HashSet<Integer>();
	
	@SuppressWarnings("serial")
	final private SmartMap<String, AverageMooving> macach = new SmartMap<String, AverageMooving>(){
		@Override
		public AverageMooving valueFactory(Object key) {
			return new AverageMooving((Integer )key);
		}
		
	};
	
	@SuppressWarnings("serial")
	final private SmartMap<String, TradeModelMM> tmcach = new SmartMap<String, TradeModelMM>(){
		@Override
		public TradeModelMM valueFactory(Object key) {
			return new TradeModelMM(key.toString());
		}
		
	};

	public RevAvgCubeModel(ContextProcessQuote context){
		this.context = context;
	}

	public void clean() {
		tmcach.clear();
		macach.clear();
		avcach.clear();
		tradeCounter = 1.0;
		headerCnt = 0;
	}

	public void finalizeSimulate(MaximumHistory maxHistory) {
		for( TradeModelMM radata: tmcach.values()){

			MAPoint pnt = radata.pnt;
			
			if( !radata.key.equals(pnt.getKey()) )
				throw new RuntimeException(String.format("xexe keys must be same but not: %s <> %s",radata.key , pnt.getKey()));
			
			TradeModel<TradeOrderReport> tmodel = radata.tmodel;
			
			tmodel.updatePNL(context.quote, MarketTrend.NA);

			if( tmodel.isBuyOrderOpened() ){
				for(TradeOrderReport order : tmodel.closeBuyOrder("Close EOD") ){
					maxHistory.generateCloseSignal(totals(order, ORDER_CLOSE, Logger.TRADE_CLOSE, radata.key, radata), pnt);
					maxHistory.check(radata.tmodel.getTotalProfit(), pnt);
				}
			}

			if( tmodel.isSellOrderOpened() ){
				for(TradeOrderReport order : tmodel.closeSellOrder("Close EOD") ){
					maxHistory.generateCloseSignal(totals(order, ORDER_CLOSE, Logger.TRADE_CLOSE, radata.key, radata), pnt);
					maxHistory.check(radata.tmodel.getTotalProfit(), pnt);
				}
			}
		}
		
		if( Logger.TRADE_CLOSE_FINAL.isOn ){
			int i=0;
			for( TradeModelMM d: tmcach.values()){
				if( i++ % 10 == 0 ){
					Logger.TRADE_CLOSE_FINAL.printf("%s", d.tmodel.reportHeader);
				}
				Logger.TRADE_CLOSE_FINAL.printf("%s", d.tmodel.reportBody);
			}
		}
	}
	
	public void preSimulate(){
		avcach.clear();
	}
	
	public double simulateTrading(MAPoint pnt, MaximumHistory maxHistory) {

		int fast=pnt.getFast(), slow=pnt.getSlow();

		Date time = context.quote.time24;
		
		double bid = context.quote.bid;
		double ask = context.quote.ask;
		double mid = (bid + ask) * 0.5;
		double spread = ask - bid;
		
		AverageMooving fam = macach.get(fast);
		AverageMooving sam = macach.get(slow);
		
		if( !avcach.contains(fast) ){
			fam.addValue(mid);
			avcach.add(fast);
		}
		
		if( !avcach.contains(slow) ){
			sam.addValue(mid);
			avcach.add(slow);
		}

		if( !sam.isFull() || !fam.isFull() ){
			return 0.0;
		}

		double favg = fam.getAverage();
		double savg = sam.getAverage();
		double davg = (favg - savg)*PIP;
		
		String pkey = pnt.getKey();
		TradeModelMM radata = tmcach.get(pkey);
		TradeModel<TradeOrderReport> tmodel = radata.tmodel;
		MiniMaxLocal mmlocal = radata.mmlocal;

		mmlocal.addValue(davg, time);

		if( Logger.REVAVG_MODEL_QUOTE.isOn ){
			String now = UtilDateTime.now(time);
			Logger.REVAVG_MODEL_QUOTE.printf("%s {delta=%5.2f pip} <= {fast=%.6f, slow=%.6f} <= {mid=%.6f, sprd=%5.2f pip} <={bid=%.6f, ask=%.6f}", now, davg, favg, savg, mid, spread*PIP, bid, ask);
		}

		if( mmlocal.isMax || mmlocal.isMin ){

			if( Logger.REVAVG_MODEL_MINIMAX.isOn ){
				String now = UtilDateTime.now(time);
				Logger.REVAVG_MODEL_MINIMAX.printf("%3d) %s {delta=%5.2f pip} <= %s was %s", mmlocal.minimaxCounter, now, mmlocal.getExtremum(), (mmlocal.isMax?"mAX":"mIN"), UtilDateTime.now(mmlocal.getExtremumTime()) );
			}

			tmodel.updatePNL(context.quote, MarketTrend.NA);

			if( mmlocal.isMax && favg > savg){
				if( tmodel.isBuyOrderOpened() ){
					for(TradeOrderReport order : tmodel.closeBuyOrder("Close before open SBB on MAX when fast > slow") ){
						maxHistory.generateCloseSignal(totals(order, ORDER_CLOSE, Logger.TRADE_CLOSE, pkey, radata), pnt);
					}
				}
				if( IndicatorRevAvg.IS_OPEN_COUPLE_TRADES ) tradeCounter += 1.0;
				if( !tmodel.isSellOrderOpened(tradeCounter) ){
					if( radata.pnt == null )
						radata.pnt = pnt.clone();
					maxHistory.generateOpenSignal(totals(tmodel.openSellOrder(tradeCounter, context.quote, context.spread.bl_fltr.getBLValue()), ORDER_OPEN, Logger.TRADE_OPEN, pkey, radata), pnt);
				}
			} else if( mmlocal.isMin && favg < savg){
				if( tmodel.isSellOrderOpened() ){
					for(TradeOrderReport order : tmodel.closeSellOrder("Close before open BSB on MIN when fast < slow") ){
						maxHistory.generateCloseSignal(totals(order, ORDER_CLOSE, Logger.TRADE_CLOSE, pkey, radata), pnt);
					}
				}
				if( IndicatorRevAvg.IS_OPEN_COUPLE_TRADES ) tradeCounter += 1.0;
				if( !tmodel.isBuyOrderOpened(tradeCounter) ){
					if( radata.pnt == null )
						radata.pnt = pnt.clone();
					maxHistory.generateOpenSignal(totals(tmodel.openBuyOrder(tradeCounter, context.quote, context.spread.bl_fltr.getBLValue()), ORDER_OPEN, Logger.TRADE_OPEN, pkey, radata), pnt);
				}
			}
		}

		return radata.tmodel.getTotalProfit();
	}

	private TradeOrderReport totals(TradeOrderReport order, boolean isOpen, Logger log, String key, TradeModelMM radata){
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
		}
		return order;
	}

	
	/*
	 * Model data types
	 */
	
	static public class TradeModelMM {
		
		private final MiniMaxLocal mmlocal = new MiniMaxLocal();
		private final TradeModel<TradeOrderReport> tmodel = new TradeModel<TradeOrderReport>(){
			@Override
			public TradeOrderReport newTradeOrderReport(double avgSpread) {
				return new TradeOrderReport(this, avgSpread);
			}
			
		};
		private final String key;
		
		private MAPoint pnt;
		
		public TradeModelMM(String key) {
			this.key = key;
		}
		
		public MAPoint getPoint() {
			
			pnt.set(new int[]{});
			return pnt;
		}
	}
	

}
