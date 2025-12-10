package ca.mss.rd.trader.src.random;

import java.math.BigDecimal;
import java.math.MathContext;

import ca.mss.rd.trade.forex.FXBridge2BD;
import ca.mss.rd.trade.forex.FXIterator;
import ca.mss.rd.trade.forex.FXQuote;
import ca.mss.rd.util.UtilProperty;

@Deprecated
public class ForexSourceRandom extends FXBridge2BD implements FXIterator {


	final static public String module = ForexSourceRandom.class.getName();
	final static public long serialVersionUID = module.hashCode();

	static public int EXPORT_TO_STRING_SCALE = 12;
	static private MathContext EXPORT_MATH_CONTEXT = new MathContext(EXPORT_TO_STRING_SCALE);

	static { 
		UtilProperty.readConstants(ForexSourceRandom.class); 
	}

	private BigDecimal ask, bid;
	private FXQuote quote;
	
	public ForexSourceRandom(FXQuote quote) {
		this.quote = quote;
	}
	
	@Override
	public void reinitialize() {
	}

	@Override
	final public FXQuote getNextQuotes(){
		// calculate
		final double rand = Math.random();
		final boolean add = (rand >= 0.5) ? true : false;
		if( ask == null ){
			ask =  new BigDecimal(0.0, EXPORT_MATH_CONTEXT);
		}
		ask = (add) ? ask.add(new BigDecimal(Math.random())).abs() : ask.subtract(new BigDecimal(Math.random())).abs();
		bid = ask.subtract(ask.multiply(new BigDecimal(0.2))).abs();

		// populate
		quote.ask = ask;
		quote.bid = bid;
		return quote;
	}

	@Override
	final public boolean hasNextQuotes(){
		return true;
	}

	@Override
	public String toString() {
		return "RANDOM";
	}

	@Override
	public String getHeader() {
		return "ASK\tBID";
	}

	@Override
	public String getRow() {
		return ask.round(EXPORT_MATH_CONTEXT).toPlainString()+"\t"+bid.round(EXPORT_MATH_CONTEXT).toPlainString();
	}

	private BigDecimal prevask, prevbid;

//	@Override
//	final public void populateQuoteLn(FXQuote quote){
//		populateQuote(quote);
//
//		// initialize
//		if( prevask == null && prevbid == null ){
//			prevask = ask; 
//			prevbid = bid; 
//		}
//		
//		// populate
//		quote.askln = new BigDecimal(Math.log(quote.ask.divide(prevask, EXPORT_MATH_CONTEXT).doubleValue()), EXPORT_MATH_CONTEXT);
//		quote.bidln = new BigDecimal(Math.log(quote.bid.divide(prevbid, EXPORT_MATH_CONTEXT).doubleValue()), EXPORT_MATH_CONTEXT);
//
//		// store previous point
//		prevask = ask; 
//		prevbid = bid; 
//	}

}
