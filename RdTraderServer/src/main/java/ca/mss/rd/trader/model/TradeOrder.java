package ca.mss.rd.trader.model;


public class TradeOrder extends MovingPNL {

	final public double avgSpread;

	public String closeReason, openReason;
	
	private int id = 0;

	public TradeOrder(double avgSpread) {
		this.avgSpread = avgSpread;
	}

	public double getPnl() {
		return closePnl/avgSpread;
	}

	public double getPnlMax() {
		return pnl.max/avgSpread;
	}

	public double getPnlMin() {
		return pnl.min/avgSpread;
	}

	public boolean checkStopLoss(double limit) {
		return getPnl() < limit;
	}

	public boolean checkGainProfit(double limit) {
		return getPnl() > limit;
	}

	public void setCloseReason(String reason) {
		this.closeReason = reason; 
	}

	public void setOpenReason(String reason) {
		this.openReason = reason; 
	}

	public void setId(int id) {
		this.id  = id;
	}

	public int getId() {
		return id;
	}
	
}
