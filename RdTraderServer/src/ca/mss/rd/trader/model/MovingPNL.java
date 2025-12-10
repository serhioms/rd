package ca.mss.rd.trader.model;

import java.util.Date;

import ca.mss.rd.stat.MiniMaxZZ;
import ca.mss.rd.util.UtilDateTime;

public class MovingPNL {
	
	public String symbol;
	public double openPrice, closePrice;
	public double closePnl, openPnl;
	public Date openTime, closeTime;
	
	final protected MiniMaxZZ pnl = new MiniMaxZZ();

	public boolean isBuy, isStart;
	
	final public void buy(Quote quote) {
		isStart = true;
		isBuy = true;
		openPrice = quote.ask;
		openTime = quote.time24;
		symbol = quote.symbol;
		updatePNL(quote);
		openPnl = closePnl;
	}

	final public void sell(Quote quote) {
		isStart = true;
		isBuy = false;
		openPrice = quote.bid;
		openTime = quote.time24;
		symbol = quote.symbol;
		updatePNL(quote);
		openPnl = closePnl;
	}

	final public void updatePNL(Quote quote) {
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

	
	final public String getCloseType() {
		return isBuy?"Sell":"Buy";
	}

	final public String getOpenType() {
		return isBuy?"Buy":"Sell";
	}

	final public String report(String str) {
		return String.format("%s\t%4s\t%9.6f\t%s\t%4s\t%9.6f\t%9.6f%s", 
				UtilDateTime.now(openTime), getOpenType(), openPrice, 
				UtilDateTime.now(closeTime), getCloseType(), closePrice,
				closePnl, 
				str);
	}

	final public void clear() {
		pnl.clear();
		symbol = null;
		openPrice = closePrice = closePnl = 0.0;
		openTime = closeTime = null;
		isStart = false;
	}

	public boolean gettingBelow(double dn, double factor) {
		return isStart? (openPnl-closePnl) <= factor*dn: false;
	}

	public int belowLevel(double dn) {
		return isStart? (int)((openPnl-closePnl)*100/dn): 0;
	}

	public boolean gettingAbow(double up, double factor) {
		return isStart? (openPnl-closePnl) >= factor*up: false;
	}

	public int abowLevel(double up) {
		return isStart? (int )((openPnl-closePnl)*100.0/up): 0;
	}

}
