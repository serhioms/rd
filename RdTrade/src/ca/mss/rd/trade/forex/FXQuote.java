package ca.mss.rd.trade.forex;

import java.math.BigDecimal;
import javax.xml.crypto.Data;
import ca.mss.rd.trade.forex.FX.CurrencyPair;

public class FXQuote {

	final public CurrencyPair pair;

	public Data date;
	public BigDecimal bid, bidln;
	public BigDecimal ask, askln;
	public BigDecimal spread;

	public FXQuote(CurrencyPair pair) {
		this.pair = pair;
		this.bid = null;
		this.ask = null;
	}

}
