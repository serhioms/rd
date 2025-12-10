package ca.mss.rd.trader.src.random;

import java.math.BigDecimal;

import ca.mss.rd.trade.forex.FXBridge2BD;
import ca.mss.rd.trade.forex.FXIterator;
import ca.mss.rd.trade.forex.FXQuote;
import ca.mss.rd.util.UtilProperty;

@Deprecated
public class ForexSourceRandomFile extends FXBridge2BD implements FXIterator {


	final static public String module = ForexSourceRandomFile.class.getName();
	final static public long serialVersionUID = module.hashCode();

	static public String EXTERNAL_FILE = "/Users/smoskovskiy/workspace/rd/RdTraderConsole/data/20131024=162528=0=ForexSourceRandom.txt";

	static { 
		UtilProperty.readConstants(ForexSourceRandomFile.class); 
	}

	private RandomForexParser<RandomForexParserRow> parser;
	private RandomForexParserRow row;
	private String bid, ask;
	private FXQuote quote;
	
	public ForexSourceRandomFile(FXQuote quote) {
		this.quote = quote;
	}
	
	@Override
	final public FXQuote getNextQuotes(){
		if( parser != null ){
			row = parser.next();
			quote.bid = new BigDecimal(bid=row.record.getBid());
			quote.ask = new BigDecimal(ask=row.record.getAsk());
		} else {
			row = null;
		}
		return quote;
	}

	@Override
	public void reinitialize() {
		parser = new RandomForexParser<RandomForexParserRow>(EXTERNAL_FILE);
		parser.readHeader();
	}

	@Override
	final public boolean hasNextQuotes(){
		return parser.hasNext();
	}

	@Override
	public String toString() {
		return "RandomFile";
	}

	@Override
	public String getHeader() {
		return "ASK\tBID";
	}

	@Override
	public String getRow() {
		return ask+"\t"+bid;
	}
}
