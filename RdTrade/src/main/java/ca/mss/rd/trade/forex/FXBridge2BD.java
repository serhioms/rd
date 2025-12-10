package ca.mss.rd.trade.forex;

import java.math.BigDecimal;
import java.util.Map;

import ca.mss.rd.download.RdDownload;
import ca.mss.rd.math.BDIterator;
import ca.mss.rd.http.UtilHttp.RequestType;

abstract public class FXBridge2BD extends RdDownload implements FXIterator,BDIterator {

	protected FXBridge2BD() {
		super(null);
	}

	protected FXBridge2BD(String requestURL, Map<String, String> requestParameters, RequestType requestType) {
		super(requestURL, requestParameters, requestType);
	}

	protected FXBridge2BD(String requestURL) {
		super(requestURL);
	}

	private BigDecimal[] result = new BigDecimal[]{null};
	@Override
	public boolean hasNext() {
		return hasNextQuotes();
	}

	final public static BigDecimal BD_ONE_TWO = new BigDecimal("0.5");
	
	@Override
	public BigDecimal[] next() {
		FXQuote q;
		try {
			q = getNextQuotes();
			result[0] = BD_ONE_TWO.multiply(q.ask.add(q.bid));
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		return result;
	}

	@Override
	public void remove() {}
}
