package ca.mss.rd.trader.src.deprecated;

import java.math.BigDecimal;

import ca.mss.rd.trade.forex.FXBridge2BD;
import ca.mss.rd.trade.forex.FXQuote;
import ca.mss.rd.trade.parser.oanda.OandaParser;
import ca.mss.rd.trade.parser.oanda.OandaParserRow;
import ca.mss.rd.util.UtilProperty;

@Deprecated
public class ForexSourceOandaFile extends FXBridge2BD {


	final static public String module = ForexSourceOandaFile.class.getName();
	final static public long serialVersionUID = module.hashCode();

	static public String OANDA_FILE = "D:/workspace/2012/rd/RdTraderConsole/data/20131023=180413=1250=ForexSourceOanda.txt";

	static { 
		UtilProperty.readConstants(ForexSourceOandaFile.class); 
	}

	private OandaParser<OandaParserRow> parser;
	private OandaParserRow row;
	private String bid, ask;
	private FXQuote quote;
	
	public ForexSourceOandaFile(FXQuote quote) {
		this.quote = quote;
		reinitialize();
	}
	
	@Override
	final public FXQuote getNextQuotes(){
		if( parser != null ){
			row = parser.next();
			quote.bid = new BigDecimal(bid=row.record.getEurusdbid());
			quote.ask = new BigDecimal(ask=row.record.getEurusdask());
		} else
			row = null;
		return quote;
	}
	
	@Override
    public final void interruptionHandler(Throwable t){
    	logger.debug(String.format("%s is stopped.", module));
    }
	
	@Override
	public void reinitialize() {
		parser = new OandaParser<OandaParserRow>(OANDA_FILE);
		parser.readHeader();	
	}

	@Override
	final public boolean hasNextQuotes(){
		return parser.hasNext();
	}

	@Override
	public String toString() {
		return "OandaFile";
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
