package ca.mss.rd.trader.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ca.mss.rd.stat.MiniMaxZZ;
import ca.mss.rd.util.UtilDateTime;

public class SimpleTradeModel {

	// Do not open order if spread is out of the right zone
	public static boolean ABNORMAL_SPREAD_FILTER_ON = false;

	// Reduce risk of unexpected trading moves
	public static boolean DO_CHECK_STOP_LOSS = false;
	// Better to have it as high as possible
	public static double STOP_LOSS_LIMIT = -5.0; 

	public static boolean DO_CHECK_GAIN_LIMIT = false;
	// Increase profit if between [0.1,0.25] due to eliminated very small trades
	public static double MIN_GAIN_LIMIT = 0.25;

	// Reduce profit due to reduce time of trades
	public static long START_OPEN_FROM_SEC = 0;

	// Reduce loses due to reduce EOD forced order maturing
	public static long FINISH_OPEN_TILL_SEC = 3600*23;

	final public Map<Double, Order> openOrders;
	final public List<Order> closeOrders;
	
	public SimpleTradeModel() {
		this.openOrders = new HashMap<Double, Order>();
		this.closeOrders = new ArrayList<Order>();
	}

	static public class Order {

		public double openPrice, closePrice;
		public Date openTime, closeTime;
		public double closePnl;
		public MiniMaxZZ pnl = new MiniMaxZZ();

		private boolean isBuy;

		public boolean isBuy() {
			return isBuy;
		}

		public void buy(Quote quote) {
			isBuy = true;
			openPrice = quote.ask;
			openTime = quote.time24;
			updatePNL(quote);
		}
		
		public String report(String str) {
			return String.format("%s\t%4s\t%3.6f\t%s\t%4s\t%3.6f\t%3.6f%s", 
					UtilDateTime.now(openTime), getOpenType(), openPrice, 
					UtilDateTime.now(closeTime), getCloseType(), closePrice,
					closePnl, str);
		}

		private String getCloseType() {
			return isBuy?"Sell":"Buy";
		}

		public String getOpenType() {
			return isBuy?"Buy":"Sell";
		}

		public void sell(Quote quote) {
			isBuy = false;
			openPrice = quote.bid;
			openTime = quote.time24;
			updatePNL(quote);
		}

		public void updatePNL(Quote quote) {
			closeTime = quote.time24;
			if( isBuy ){
				closePrice = quote.bid;
				closePnl = closePrice - openPrice;
			} else {
				closePrice = quote.ask;
				closePnl = openPrice - closePrice;
			}
			pnl.addValue(closePnl);
		}

		public boolean checkStopLoss() {
			return false;
		}

		public boolean checkGainProfit() {
			return false;
		}
	}

	public void clean() {
		openOrders.clear();
		closeOrders.clear();
	}
	
	public Order openBuyOrder(double pipe, Quote quote) {
		Order order = new Order();
		order.buy(quote);
		openOrders.put(pipe, order);
		return order;
	}

	public Order openSellOrder(double pipe, Quote quote) {
		Order order = new Order();
		order.sell(quote);
		openOrders.put(pipe, order);
		return order;
	}

	public Order closeOrder(double pipe) {
		Order order = openOrders.remove(pipe);
		if( order != null ){
			closeOrders.add(order);
		}
		return order;
	}

	public Collection<Order> closeOrders() {
		Collection<Order> values = openOrders.values();
		closeOrders.addAll(values);
		return values;
	}

	public void updatePNL(Quote quote) {
		for(Order order: openOrders.values()){
			order.updatePNL(quote);
		}
	}
	
	public Collection<Order> checkStopLossGainProfit() {
		for(Order order: openOrders.values()){
			if( DO_CHECK_STOP_LOSS && order.checkStopLoss() ){
				closeOrders.add(order);
				openOrders.remove(order);
			}
			if( DO_CHECK_GAIN_LIMIT && order.checkGainProfit() ){
				closeOrders.add(order);
				openOrders.remove(order);
			}
		}
		return null;
	}

}
