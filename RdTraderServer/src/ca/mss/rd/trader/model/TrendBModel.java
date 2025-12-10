package ca.mss.rd.trader.model;


public class TrendBModel implements TrendModelInt {

	private int trendCounter;
	private TrendModel trendModel2;
	private TrendModel trendModel3;
	
	public TrendBModel() {
		trendModel2 = new TrendModel();
		trendModel3 = new TrendModel();
		init();
		clear();
	}

	@Override
	public void init() {
		trendModel2.init();
		trendModel3.init();
	}


	@Override
	public void clear() {
		trendCounter = 0;
		trendModel2.clear();
		trendModel3.clear();
	}

	@Override
	public void addProfitBSB(double profit) {
		if( trendCounter++ == 0 ){
			trendModel2.addProfitBSB(profit);
		} else {
			trendModel2.addProfitBSB(profit);
			trendModel3.addProfitBSB(profit);
		}
	}

	@Override
	public void addProfitSBB(double profit) {
		if( trendCounter++ == 0 ){
			trendModel2.addProfitSBB(profit);
		} else {
			trendModel2.addProfitSBB(profit);
			trendModel3.addProfitSBB(profit);
		}
	}


	@Override
	public String report(){
		if( trendCounter % 2 == 0 ){
			return trendModel2.report();
		} else {
			return trendModel3.report();
		}
	}


	@Override
	public double getProfit() {
		if( trendCounter % 2 == 0 ){
			return trendModel2.getProfit();
		} else {
			return trendModel3.getProfit();
		}
	}


	@Override
	public double getProfitBSB() {
		if( trendCounter % 2 == 0 ){
			return trendModel2.getProfitBSB();
		} else {
			return trendModel3.getProfitBSB();
		}
	}


	@Override
	public double getProfitSBB() {
		if( trendCounter % 2 == 0 ){
			return trendModel2.getProfitSBB();
		} else {
			return trendModel3.getProfitSBB();
		}
	}

	@Override
	public double getProfitTotal() {
		if( trendCounter % 2 == 0 ){
			return trendModel2.getProfitTotal();
		} else {
			return trendModel3.getProfitTotal();
		}
	}

	@Override
	public MarketTrend getMarketTrend() {
		if( trendCounter % 2 == 0 ){
			return trendModel2.getMarketTrend();
		} else {
			return trendModel3.getMarketTrend();
		}
	}

	@Override
	public boolean isBSB() {
		if( trendCounter % 2 == 0 ){
			return trendModel2.isBSB();
		} else {
			return trendModel3.isBSB();
		}
	}

	@Override
	public int getTrendCounter() {
		return trendCounter;
	}
	
	
}
