package ca.mss.rd.trader.deprecated.beans;

import java.util.ArrayList;
import java.util.List;

import ca.mss.rd.table.RdDBO;
import ca.mss.rd.table.RdTable;

public class CurrencyPair {

	final static public RdDBO<List<CurrencyPair>> dbo = new RdDBO<List<CurrencyPair>>(new RdTable<List<CurrencyPair>>());
	
	public enum CurrencyPairOanda {
		EURUSD,
		GBPUSD,
		USDCHF,
		USDJPY,
		AUDUSD,
		USDCAD,
		XAUUSD,
		EURJPY,
		EURGBP,
		EURCHF,
		USDCNY,
		EURSEK,
		XAGUSD,
		USDDKK,
		NZDUSD,
		CHFJPY,
		GBPCHF,
		NZDJPY,
		EURNZD,
		CADJPY,
		EURCAD,
		US30USD,
		SPX500USD,
		BCOUSD,
		WTICOUSD,
		DE30EUR,
		UK100GBP,
		JP225USD,
		HK33HKD,
		NAS100USD,
		XCUUSD,
		GBPJPY,
		AUDJPY;
	}
	
	final public String pair;
	
	public CurrencyPair(String pair) {
		super();
		this.pair = pair;
	}

	static {
		CurrencyPairOanda[] pairs = CurrencyPairOanda.values();
		
		List<CurrencyPair> list = new ArrayList<CurrencyPair>();
		
		for(int i=0; i<pairs.length; i++){
			list.add(new CurrencyPair(pairs[i].toString()));
		}
		dbo.table.add(Floor.FloorEnum.Oanda.toString(), list);

	}
}
