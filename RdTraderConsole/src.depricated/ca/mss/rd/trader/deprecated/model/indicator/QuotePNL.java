package ca.mss.rd.trader.deprecated.model.indicator;

import java.math.BigDecimal;
import java.util.Map;

import ca.mss.rd.trade.forex.FX;
import ca.mss.rd.trade.forex.FX.Currency;
import ca.mss.rd.trade.forex.FX.CurrencyPair;
import ca.mss.rd.trader.deprecated.collector.BidAskMidQuote;
import ca.mss.rd.trader.model.Point;

import ca.mss.rd.util.UtilProperty;

public class QuotePNL  extends DefaultIndicator {

	public static final long serialVersionUID = QuoteSlicer.class.hashCode();
	final static public String module = QuoteSlicer.class.getSimpleName();
	final static public org.apache.log4j.Logger logger = ca.mss.rd.util.Debug.getLogger(module);

	static public Currency PNL_CURRECY = Currency.CAD;
	static public CurrencyPair PNL_CURRENCY_PAIR = CurrencyPair.EUR_USD;
	static public String PNL_QHRATE_SELL = "1.03292"; 
	static public String PNL_QHRATE_BUY  = "1.03300"; 
	static public String PNL_LEVERAGE = "10000"; 
	
	static { 
		UtilProperty.readConstants(QuotePNL.class);
	}
	
	static private int SIZE = 0;
	final static public int PNL_SUM = SIZE++;
	final static public int PNL_TTL = SIZE++;
	final static public int PNL_BSB = SIZE++;
	final static public int PNL_SBB = SIZE++;

	final static public String[] TITLE = new String[]{"SUM", "TTL", "BSB", "SBB"};
	
	private Point[] point = new Point[]{new Point(1),new Point(1),new Point(1),new Point(1)};

	// PNL calculations 
	private FX bsbFx, sbbFx; 
	public boolean isBSB, isSBB;
	
	private double ask, bid;
	private BigDecimal bsb;
	private BigDecimal sbb;
	private BigDecimal sum;
	private BigDecimal total;
	private BigDecimal bsbProfit;
	private BigDecimal sbbProfit;
	
	public QuotePNL(Map<String,String> param) {
		super(param);

		// PNL calculations
		bsbFx = new FX(PNL_CURRECY, PNL_CURRENCY_PAIR, new BigDecimal(PNL_LEVERAGE, FX.LEVERAGE));
		sbbFx = new FX(PNL_CURRECY, PNL_CURRENCY_PAIR, new BigDecimal(PNL_LEVERAGE, FX.LEVERAGE));
		isBSB = false;
		isSBB = false;
		
		start();
	}

	@Override
	public void start() {
		total = new BigDecimal(0.0);
		bsbProfit = new BigDecimal(0.0);
		sbbProfit = new BigDecimal(0.0);
		bsb = new BigDecimal(0.0);
		sbb = new BigDecimal(0.0);
		sum = new BigDecimal(0.0);
	}

	@Override
	public void process(Point p){
		
		ask = p.val[BidAskMidQuote.ASK];
		bid = p.val[BidAskMidQuote.BID];
		
		sum = total;
		if( isBSB ){
			if( bsbFx.isNotBought() ){
				doBuy();
			}
			bsb = bsbFx.sellBack(new BigDecimal(bid, FX.PRICE), new BigDecimal(PNL_QHRATE_SELL, FX.PRICE)).getPNL();
			sum = sum.add(bsb);
			bsbProfit = bsb;
		}
		if( isSBB ){
			if( sbbFx.isNotSold() ){
				doSell();
			}
			sbb = sbbFx.buyBack(new BigDecimal(ask, FX.PRICE), new BigDecimal(PNL_QHRATE_BUY, FX.PRICE)).getPNL();
			sum = sum.add(sbb);
			sbbProfit = sbb;
		}
		
		point[PNL_SUM].setCurr(sum.doubleValue());
		point[PNL_SUM].time = p.time;

		point[PNL_TTL].setCurr(total.doubleValue());
		point[PNL_TTL].time = p.time;

		point[PNL_BSB].setCurr(bsb.doubleValue());
		point[PNL_BSB].time = p.time;

		point[PNL_SBB].setCurr(sbb.doubleValue());
		point[PNL_SBB].time = p.time;
	}
	
	@Override
	public boolean isChange(int i) {
		return true;
	}

	@Override
	public Point getPoint(int i) {
		return point[i];
	}

	@Override
	public int size() {
		return QuotePNL.TITLE.length;
	}

	@Override
	public String getTitle(int i) {
		return QuotePNL.TITLE[i];
	}

	@Override
	public int getIndex(String title) {
		for(int i=0; i<QuotePNL.TITLE.length; i++)
			if( title.equals(QuotePNL.TITLE[i]))
				return i;
		return Integer.parseInt(title); // Expect # here
	}

	

	/*
	 * PNL methods
	 */
	
	public void doBuy() {
    	if( ask > 0.0 )
    		bsbFx.doBuy(new BigDecimal("10000", FX.UNITS), new BigDecimal( ask, FX.PRICE));
    	isBSB = true;
    }

    public void doSell() {
    	if( bid > 0.0 )
    		sbbFx.doSell(new BigDecimal("10000", FX.UNITS), new BigDecimal( bid, FX.PRICE));
		isSBB = true;
    }

    public void sellBack() {
		isBSB = false;
		total = total.add(bsbProfit);
		bsbProfit = new BigDecimal(0.0);
    }
	
    public void buyBack() {
		isSBB = false;
		total = total.add(sbbProfit);
		sbbProfit = new BigDecimal(0.0);
    }

	public final boolean isBSB() {
		return isBSB;
	}

	public final boolean isSBB() {
		return isSBB;
	}

}
