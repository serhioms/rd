package ca.mss.rd.trade.forex;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import ca.mss.rd.util.Logger;



public class FX {

	final static public String module = FX.class.getSimpleName();
	final static public long serialVersionUID = module.hashCode();

	public enum Currency {
		USD("USD", "$"),
		EUR("EUR", "\u20ac"),
		AUD("AUD", "$"),
		CAD("CAD", "CAD"),
		GBP("GBP", "\u00a3"),
		CHF("CHF", "\u20A3"),
		NZD("NZD", "$"),
		JPY("JPY", "\u00a5");
		
		final public String acronim;
		final public String symbol;

		private Currency(String acronim, String symbol) {
			this.acronim = acronim;
			this.symbol = symbol;
		}

		@Override
		public String toString() {
			return symbol;
		}
	}
	
	public enum PairRank {
		Major,
		Cross;
	}
	
	public enum CurrencyPair {
		EUR_USD(PairRank.Major, Currency.EUR, Currency.USD, "Euro-Dollar", "ED"),
		USD_JPY(PairRank.Major, Currency.USD, Currency.JPY, "Dollar-Yen", "DY"),
		GBP_USD(PairRank.Major, Currency.GBP, Currency.USD, "Sterling-Dollar", "Sterling"), // Cable  
		USD_CHF(PairRank.Major, Currency.USD, Currency.CHF, "Dollar-Swiss", "Swissy"),
		USD_CAD(PairRank.Major, Currency.USD, Currency.CAD, "UAS-Canada Dollar", "Loonie"),
		AUD_USD(PairRank.Major, Currency.AUD, Currency.USD, "Australian-dollar", "OZ"), // Aussie 
		NZD_USD(PairRank.Major, Currency.NZD, Currency.USD, "New Zelland-dollar", "Kiwi"),
		EUR_CHF(PairRank.Cross, Currency.EUR, Currency.CHF, "Euro-Swiss", "ESW"),
		EUR_GBP(PairRank.Cross, Currency.EUR, Currency.GBP, "Euro-Sterling", "EST"),
		EUR_JPY(PairRank.Cross, Currency.EUR, Currency.JPY, "Euro-yen", "EY"),
		GBP_JPY(PairRank.Cross, Currency.GBP, Currency.JPY, "Sterling-yen", "SY"),
		AUD_JPY(PairRank.Cross, Currency.AUD, Currency.JPY, "Aussie-yen", "AY"),
		GBP_CHF(PairRank.Cross, Currency.GBP, Currency.CHF, "Sterling-Swiss", "SSW"),
		CAD_USD(PairRank.Cross, Currency.CAD, Currency.USD, "Canada-USA Dollar", "CUD"),
		NZD_JPY(PairRank.Cross, Currency.NZD, Currency.JPY, "Kiwi-yen", "KY");
		
		final public Currency baseCcy;
		final public Currency quoteCcy;  // counter 
		final public String acronim;
		final public String longName;
		final public String nickName;
		final PairRank rank;

		private CurrencyPair(PairRank rank, Currency baseCcy, Currency quoteCcy, String longName, String nickName) {
			this.rank = rank;
			this.baseCcy = baseCcy;
			this.quoteCcy = quoteCcy;
			this.longName = longName;
			this.nickName = nickName;
			this.acronim = baseCcy.acronim+"/"+quoteCcy.acronim;
		}

		@Override
		public String toString() {
			return super.toString().replace('_', '/');
		}
		
		
	}
	
	public enum Trade {
		BuySellBack(true,false),
		SellBuyBack(false,true);
		
		final public boolean isLong, isShort;

		private Trade(boolean isLong, boolean isShort) {
			this.isLong = isLong;
			this.isShort = isShort;
		}
	}

	final public CurrencyPair pair;		// currency pair base/quote
	final public BigDecimal leverage;	// trade multiplicator 1:100, 1:1'000, 1:10'000
	public Trade trade;					// BSB or SBB
	public BigDecimal units;			// units, trade size, transaction size in base currency: standard=100'000, mini=10'000, micro=1'000
	public BigDecimal open;				// bid or ask
	public BigDecimal close;			// ask or bid 
	
	
	public FX(CurrencyPair pair, BigDecimal leverage) {
		this.pair = pair;
		this.leverage = leverage;
	}

	public FX(BigDecimal leverage) {
		this(null, leverage);
	}

	final static public MathContext INTEGER = new MathContext(0);
	final static public MathContext LEVERAGE = INTEGER;
	final static public MathContext UNITS = INTEGER;
	
	final static public int PRECISION = 12;
	final static public int SCALE = 2;
	final static public RoundingMode ROUNDING_MODEL = RoundingMode.HALF_UP;
	final static public MathContext CURRENCY = new MathContext(PRECISION, ROUNDING_MODEL);
	
	final static public MathContext EXRATE = CURRENCY;
	final static public MathContext OPEN = EXRATE;
	final static public MathContext CLOSE = EXRATE;

	final static private BigDecimal ONE = new BigDecimal("1", CURRENCY);
	
	final public void doBuy(BigDecimal units, BigDecimal ask){
		this.trade = Trade.BuySellBack;
		this.units = units;
		this.open = ask;
	}

	final public void sellBack(BigDecimal bid){
		this.close = bid;
	}


	final public void doSell(BigDecimal units, BigDecimal bid){
		this.trade = Trade.SellBuyBack;
		this.units = units;
		this.open = bid;
	}

	final public void buyBack(BigDecimal ask){
		this.close = ask;
	}

	public final boolean isOpened() {
		return open != null;
	}
	

	public final boolean isClosed() {
		return close != null;
	}
	
	final public BigDecimal getMargin(){
		return units.multiply(open).setScale(SCALE, ROUNDING_MODEL);
	}

	final public BigDecimal getWithdraw(){
		return units.multiply(close).setScale(SCALE, ROUNDING_MODEL);
	}

	final public BigDecimal getProfit(){
		return (trade == Trade.BuySellBack? close.subtract(open): open.subtract(close)).multiply(units).setScale(SCALE, ROUNDING_MODEL);
	}
	
	final public BigDecimal getProfit(BigDecimal rateQuote2AccCcy){
		return getProfit().multiply(rateQuote2AccCcy).setScale(SCALE, ROUNDING_MODEL);
	}

	final public BigDecimal getDeposit(){
		return units.divide(leverage, PRECISION, ROUNDING_MODEL);
	}

	final public BigDecimal getDeposit(BigDecimal rateBase2AccCcy){
		return getDeposit().multiply(rateBase2AccCcy, FX.CURRENCY);
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		FX.test(Trade.SellBuyBack, CurrencyPair.EUR_USD, "1000", "10000", "0.9517", "0.9505", Currency.CAD, new BigDecimal(1.3393), new BigDecimal(1.2463));
		FX.test(Trade.BuySellBack, CurrencyPair.USD_JPY, "1000", "10000", "115.05", "114.45", Currency.USD);
		FX.test(Trade.BuySellBack, CurrencyPair.USD_JPY, "1000", "10000", "115.05", "114.45", Currency.CAD, new BigDecimal(1.2463), new BigDecimal(0.0104444306));
		FX.test(Trade.BuySellBack, CurrencyPair.GBP_CHF, "1000", "1000", "2.1443", "2.1452", Currency.USD, new BigDecimal(1.4657), new BigDecimal(1.1025));
		FX.test(Trade.BuySellBack, CurrencyPair.EUR_USD, "1000", "10000", "1.09829", "1.09923", Currency.CAD, new BigDecimal(1.3393), new BigDecimal(1.2463));
		FX.test(Trade.SellBuyBack, CurrencyPair.EUR_USD, "1000", "10000", "1.09829", "1.09923", Currency.CAD, new BigDecimal(1.3393), new BigDecimal(1.2463));
		FX.test(Trade.BuySellBack, CurrencyPair.EUR_USD, "1000", "10000", "1.09829", "1.09923", Currency.USD, new BigDecimal(1.09829), ONE);

		FX.test(Trade.BuySellBack, CurrencyPair.USD_CAD, "50", "10000", "1.25146", "1.25146", Currency.CAD);
		FX.test(Trade.BuySellBack, CurrencyPair.CAD_USD, "50", "10000", "0.79907", "0.79907", Currency.CAD);
	}

	static final public void test(Trade trade, CurrencyPair pair, String leverage, String units, String open, String close, Currency accCcy){
		test(trade, pair, leverage, units, open, close, accCcy, getMarginRate(pair, close, accCcy), getProfitRate(pair, close, accCcy));
	}
	
	static final public BigDecimal getProfitRate(CurrencyPair pair, String close, Currency accCcy){
		if( pair.quoteCcy == accCcy){
			return ONE;
		} else if( pair.acronim.equals(pair.quoteCcy.acronim+"/"+accCcy.acronim)){
			return new BigDecimal(close, CURRENCY);
		} else if( pair.acronim.equals(accCcy.acronim+"/"+pair.quoteCcy.acronim)){
			return ONE.divide(new BigDecimal(close, FX.EXRATE), PRECISION, ROUNDING_MODEL);
		} else {
			throw new RuntimeException(String.format("Need %s exchange rate for PNL exchange to %s", pair.quoteCcy.acronim+"/"+accCcy.acronim, accCcy));
		}
	}
	
	static final public BigDecimal getMarginRate(CurrencyPair pair, String open, Currency accCcy){
		if( pair.baseCcy == accCcy){
			return ONE;
		} else if( pair.acronim.equals(pair.baseCcy.acronim+"/"+accCcy.acronim)){
			return new BigDecimal(open, CURRENCY);
		} else if( pair.acronim.equals(accCcy.acronim+"/"+pair.baseCcy.acronim)){
			return ONE.divide(new BigDecimal(open, FX.EXRATE), PRECISION, ROUNDING_MODEL);
		} else {
			throw new RuntimeException(String.format("Need %s exchange rate for deposit exchange to %s", pair.baseCcy.acronim+"/"+accCcy.acronim, accCcy));
		}
	}

	static final public void test(Trade trade, CurrencyPair pair, String leverage, String units, String open, String close, 
			Currency accCcy, BigDecimal depositRate, BigDecimal profitRate){
		
		FX fx = new FX(pair, new BigDecimal(leverage, FX.LEVERAGE));

		if( trade == Trade.BuySellBack ){
			fx.doBuy(new BigDecimal(units, FX.UNITS), new BigDecimal(open, FX.OPEN));
			fx.sellBack(new BigDecimal(close, FX.CLOSE));
		} else if( trade == Trade.SellBuyBack ){
			fx.doSell(new BigDecimal(units, FX.UNITS), new BigDecimal(open, FX.OPEN));
			fx.buyBack(new BigDecimal(close, FX.CLOSE));
		} else {
			throw new RuntimeException(String.format("Unexpected trade %s", trade));
		}
		
		
		if( Logger.TEST.isOn ){ 
				
			Logger.TEST.printf("%s %4.0f units of %s leverage %4.0f (Account currency %s)",fx.trade, fx.units, fx.pair, fx.leverage, accCcy);
	
			Logger.TEST.printf("open:  %-7s %4.0f%s/%-8s %4.2f%s",
					"Borrow", fx.units, fx.pair.baseCcy, 
					"Margine", fx.getMargin(), fx.pair.quoteCcy.symbol);
	
			Logger.TEST.printf("close: %-7s %4.0f%s/%-8s %4.2f%s",
					"Return", fx.units, fx.pair.baseCcy,  
					"Withdraw", fx.getWithdraw(), fx.pair.quoteCcy.symbol);
	
			Logger.TEST.printf("deposit: %4.2f%s/%4.2f%s",
					fx.getDeposit(), fx.pair.baseCcy.symbol, 
					fx.getDeposit(depositRate), accCcy);
			
			Logger.TEST.printf("profit: %4.2f%s/%4.2f%s",
					fx.getProfit(), fx.pair.quoteCcy.symbol, 
					fx.getProfit(profitRate), accCcy.symbol);
	 
			Logger.TEST.printf("\n");
		}
	}
	
	
}