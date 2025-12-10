package ca.mss.rd.trader.model;

import java.util.Date;

import ca.mss.rd.parser.impl.GenericRecord;
import ca.mss.rd.trader.src.oanda.FxOandaFile;
import ca.mss.rd.util.UtilDateTime;

public class Quote {
	
	public long quoteCounter;

	public Date time24, timeCNT;
	public double bid, ask;
	public String symbol;

	static private int scounter = 0;
	
	final public static Quote populate(GenericRecord record, String symbol) {
		Quote quote = new Quote();
		
		quote.quoteCounter = ++scounter;
		quote.time24 = UtilDateTime.parse(record.getDate()+" "+record.getTime(), FxOandaFile.ROW_DATE_TIME_FORMATTER);
		quote.timeCNT = UtilDateTime.setYear(UtilDateTime.parse(record.getDate()+" "+record.getTime(), FxOandaFile.ROW_DATE_TIME_FORMATTER), scounter);
		quote.ask = Double.parseDouble(record.getValue(symbol + "Ask"));
		quote.bid = Double.parseDouble(record.getValue(symbol + "Bid"));
		quote.symbol = symbol;
		
		return quote;
	}
	
	public void clear(){
		quoteCounter = 0;
		scounter = 0;
		bid = ask = 0.0;
	}

	public Quote() {
	}
	
	public Quote(String symbol) {
		this.symbol = symbol;
	}
	
}