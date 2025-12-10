package ca.mss.rd.trader.src.deprecated;

import ca.mss.rd.math.FXSourceIterator;
import ca.mss.rd.parser.impl.Getter;
import ca.mss.rd.trade.parser.oanda.OandaParser;
import ca.mss.rd.trade.parser.oanda.OandaParserRow;
import ca.mss.rd.util.UtilProperty;

@Deprecated
public class FXSourceOandaFile implements FXSourceIterator {


	final static public String module = FXSourceOandaFile.class.getName();
	final static public long serialVersionUID = module.hashCode();

	static public String OANDA_FILE = "D:/workspace/2012/rd/RdTraderConsole/data/20131023=180413=1250=ForexSourceOanda.txt";

	static { 
		UtilProperty.readConstants(FXSourceOandaFile.class); 
	}

	private OandaParser<OandaParserRow> parser;
	
	public FXSourceOandaFile() {
		reinitialize();
	}

	public void reinitialize() {
		parser = new OandaParser<OandaParserRow>(OANDA_FILE);
		parser.readHeader();	
	}

	@Override
	final public boolean hasNext(){
		return parser.hasNext();
	}

	@Override
	public Getter next() {
		return parser.next().record;
	}

	@Override
	public void remove() {
	}

	@Override
	public String toString() {
		return "OandaFile";
	}

}
