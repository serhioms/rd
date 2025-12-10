package ca.mss.rd.trader.model;

public interface TrendModelInt {

	public void init();

	public void clear();

	public void addProfitBSB(double profit);

	public void addProfitSBB(double profit);

	public String report();

	public double getProfit();
	public double getProfitBSB();
	public double getProfitSBB();

	public double getProfitTotal();

	public MarketTrend getMarketTrend();

	public boolean isBSB();

	public int getTrendCounter();

}