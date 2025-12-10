package ca.mss.rd.trader.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ca.mss.rd.util.Logger;

abstract public class TradeModel<TOR extends TradeOrderReport> {

	public static final String END_OF_THE_DAY = "EOD";
	public static final String BEAR_COMING = "BEAR";
	public static final String BOOL_COMING = "BOOL";
	public static final String T_P = "T/P";
	public static final String S_L = "S/L";


	abstract public TOR newTradeOrderReport(double avgSpread);

	// Close orders ASAP if spread is getting huge
	public static boolean CLOSE_WHEN_SPREAD_HUGE = false;

	// Open orders when spread is tiny
	public static boolean OPEN_WHEN_SPREAD_TINY = true;

	// Reduce risk of unexpected trading moves
	public static boolean DO_CHECK_STOP_LOSS = true;
	// Approximately is double the profit pipe minus trend pipe
	public static double STOP_LOSS_LIMIT = -3.0; // (0.3-1.3)*2.0

	public static boolean DO_CHECK_TOP_PROFIT = false;
	// Approximately is the trend pipe plus profit pipe
	public static double GAIN_PROFIT_LIMIT = 2.0; //1.3+0.3;

	final public Map<Double, TOR> openBuyOrders;
	final public Map<Double, TOR> openSellOrders;
	final public List<TOR> allCloseOrders;
	final public List<TOR> closeOrders; // Due to change trend


	private double totalProfit;
	private double currentProfit;
	private boolean isHeaderReported;

	String reportHeader;
	String reportBody;
	
	
	public TradeModel() {
		this.openBuyOrders = new HashMap<Double, TOR>();
		this.openSellOrders = new HashMap<Double, TOR>();
		this.allCloseOrders = new ArrayList<TOR>();
		this.closeOrders = new ArrayList<TOR>();
	}

	public void clear() {
		openBuyOrders.clear();
		openSellOrders.clear();
		allCloseOrders.clear();
		closeOrders.clear();
		currentProfit = totalProfit = 0.0;
		isHeaderReported = false;
	}
	
	
	public double getTotalProfit() {
		return totalProfit;
	}

	public void addTotalProfit(double totalProfit) {
		this.totalProfit += totalProfit;
	}

	public double getCurrentProfit() {
		return currentProfit;
	}

	public TOR openBuyOrder(double pipe, Quote quote, double avgSpread) {
		TOR order = newTradeOrderReport(avgSpread);
		order.buy(quote);
		openBuyOrders.put(pipe, order);
		return order;
	}

	public boolean isBuyOrderOpened(double pipe) {
		return openBuyOrders.containsKey(pipe);
	}

	public boolean isBuyOrderOpened() {
		return openBuyOrders.size() > 0;
	}

	public TOR openSellOrder(double pipe, Quote quote, double avgSpread) {
		TOR order = newTradeOrderReport(avgSpread);
		order.sell(quote);
		openSellOrders.put(pipe, order);
		return order;
	}

	public boolean isSellOrderOpened(double pipe) {
		return openSellOrders.containsKey(pipe);
	}

	public boolean isSellOrderOpened() {
		return openSellOrders.size() > 0;
	}

	public boolean isAnyOrderOpened() {
		return openSellOrders.size() > 0 || openBuyOrders.size() > 0;
	}

	public TOR closeBuyOrder(double pipe) {
		TOR order = openBuyOrders.remove(pipe);
		if( order != null ){
			allCloseOrders.add(order);
		}
		return order;
	}

	public Collection<TOR> closeBuyOrder(String reason) {
		List<TOR> list = new ArrayList<TOR>();

		Collection<TOR> buy = openBuyOrders.values();
		
		allCloseOrders.addAll(buy);
		closeOrders.addAll(buy);
		
		for(TOR order: buy){
			order.setCloseReason(reason);
			list.add(order);
		}

		openBuyOrders.clear();
		
		return list;
	}

	public TOR closeSellOrder(double pipe) {
		TOR order = openSellOrders.remove(pipe);
		if( order != null ){
			allCloseOrders.add(order);
		}
		return order;
	}

	public Collection<TOR> closeSellOrder(String reason) {
		List<TOR> list = new ArrayList<TOR>();

		Collection<TOR> sell = openSellOrders.values();
		
		allCloseOrders.addAll(sell);
		closeOrders.addAll(sell);

		for(TOR order: sell){
			order.setCloseReason(reason);
			list.add(order);
		}
		
		openSellOrders.clear();

		return list;
	}

	public void closeOrders(String reason) {
		closeBuyOrder(reason);
		closeSellOrder(reason);
	}

	public void updatePNL(Quote quote, MarketTrend marketTrend) {
		currentProfit = 0.0;
		for(TOR order: openBuyOrders.values()){
			order.updatePNL(quote);
			currentProfit += order.getPnl(); 
		}
		for(TOR order: openSellOrders.values()){
			order.updatePNL(quote);
			currentProfit += order.getPnl(); 
		}
	}
	
	public List<TOR> checkStopLoss(String reason) {
		List<TOR> list = new ArrayList<TOR>();
		
		for(TOR order: openBuyOrders.values()){
			if( order.checkStopLoss(STOP_LOSS_LIMIT) ){
				order.setCloseReason(reason);
				list.add(order);
			}
		}
		
		for(TOR order: openSellOrders.values()){
			if( order.checkStopLoss(STOP_LOSS_LIMIT) ){
				order.setCloseReason(reason);
				list.add(order);
			}
		}
		
		if( list.size() > 0 ){
			openBuyOrders.values().removeAll(list);
			openSellOrders.values().removeAll(list);
			allCloseOrders.addAll(list);
		}
		
		return list;
	}

	public List<TOR> checkTopProfit(String reason, boolean isBool, boolean isBear) {
		List<TOR> list = new ArrayList<TOR>();
		
		for(TOR order: openBuyOrders.values()){
			if( order.checkGainProfit(GAIN_PROFIT_LIMIT) &&  isBool ){
				order.setCloseReason(String.format("%s(%s)",reason, GAIN_PROFIT_LIMIT));
				list.add(order);
			}
		}
		
		for(TOR order: openSellOrders.values()){
			if( order.checkGainProfit(GAIN_PROFIT_LIMIT) && isBear ){
				order.setCloseReason(String.format("%s(%s)", reason, GAIN_PROFIT_LIMIT));
				list.add(order);
			}
		}
		
		if( list.size() > 0 ){
			openBuyOrders.values().removeAll(list);
			openSellOrders.values().removeAll(list);
			allCloseOrders.addAll(list);
		}
		
		return list;
	}


	public boolean closeAndReport(Collection<TOR> lst, String comment) {
		boolean isClosed = false;
		for(TOR order: lst ){
			isClosed = true;
			
			if( !isHeaderReported ){
				isHeaderReported = true;
				Logger.TRADE_CLOSE.printf(order.reportHeader());
				
			}
			Logger.TRADE_CLOSE.printf(order.reportBody(false, comment));
		}
		return isClosed;
	}

}
