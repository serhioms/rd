package ca.mss.rd.trader.model;


public class TrendModel implements TrendModelInt {

	public static final boolean COMPARABLE_TREND = true;
	
	public MarketTrend marketTrend;
	public double profitBSB, sumProfitBSB; 
	public double profitSBB, sumProfitSBB; 
	public double sumProfit; 
	public boolean isBSB; 
	public double ttlProfitSBB, ttlProfitBSB; 
	private int trendCounter;
	
	public TrendModel() {
		init();
		clear();
	}


	/* (non-Javadoc)
	 * @see ca.mss.rd.trader.model.TradeModelInt#init()
	 */
	@Override
	public void init() {
		profitBSB = sumProfitBSB = profitSBB = sumProfitSBB = sumProfit = 0.0; 
		marketTrend = MarketTrend.Flat;
	}


	/* (non-Javadoc)
	 * @see ca.mss.rd.trader.model.TradeModelInt#clear()
	 */
	@Override
	public void clear() {
		ttlProfitSBB = ttlProfitBSB = 0.0;
		marketTrend = MarketTrend.NA;
	}

	/* (non-Javadoc)
	 * @see ca.mss.rd.trader.model.TradeModelInt#addProfitBSB(double)
	 */
	@Override
	public void addProfitBSB(double profit) {
		trendCounter++;
		isBSB = true;
		profitBSB = profit;
		sumProfitBSB += profit;
		ttlProfitBSB += profit;
		marketTrend = sumProfitBSB>sumProfitSBB? MarketTrend.Bool: sumProfitBSB<sumProfitSBB? MarketTrend.Bear: MarketTrend.Flat;
		sumProfit = sumProfitBSB>=sumProfitSBB?sumProfitBSB - sumProfitSBB: sumProfitSBB-sumProfitBSB;
	}

	/* (non-Javadoc)
	 * @see ca.mss.rd.trader.model.TradeModelInt#addProfitSBB(double)
	 */
	@Override
	public void addProfitSBB(double profit) {
		trendCounter++;
		isBSB = false;
		profitSBB = profit;
		sumProfitSBB += profit;
		ttlProfitSBB += profit;
		marketTrend = sumProfitBSB>sumProfitSBB? MarketTrend.Bool: sumProfitBSB<sumProfitSBB? MarketTrend.Bear: MarketTrend.Flat;
		sumProfit = sumProfitBSB>=sumProfitSBB?sumProfitBSB - sumProfitSBB: sumProfitSBB-sumProfitBSB;
	}


	/* (non-Javadoc)
	 * @see ca.mss.rd.trader.model.TradeModelInt#report()
	 */
	@Override
	public String report(){
		return String.format("profit = %4.2f[%6.2f,%-6.2f]%s", getProfit(), sumProfitBSB, sumProfitSBB, String.format(" => %4s[%4.2f]", marketTrend, sumProfit));

	}

	/* (non-Javadoc)
	 * @see ca.mss.rd.trader.model.TradeModelInt#getProfit()
	 */
	@Override
	public double getProfit() {
		return isBSB? profitBSB: profitSBB;
	}

	@Override
	public double getProfitBSB() {
		return profitBSB;
	}


	@Override
	public double getProfitSBB() {
		return profitSBB;
	}


	/* (non-Javadoc)
	 * @see ca.mss.rd.trader.model.TradeModelInt#getProfitTotal()
	 */
	@Override
	public double getProfitTotal() {
		return ttlProfitSBB + ttlProfitBSB;
	}


	@Override
	public MarketTrend getMarketTrend() {
		return marketTrend;
	}


	@Override
	public boolean isBSB() {
		return isBSB;
	}


	@Override
	public int getTrendCounter() {
		return trendCounter;
	}

	
	
}
