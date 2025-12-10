package ca.mss.rd.trader.deprecated.model;

import java.util.Map;
import java.util.Set;

import ca.mss.rd.util.UtilMisc;
import ca.mss.rd.util.UtilProperty;

@Deprecated
public class AvailableData {

	static { 
		UtilProperty.readConstants(AvailableData.class); 
	}

	/*
	 * Available data sources
	 */
	static private Map<String, String> FOREX_QUOTE_SOURCE = UtilMisc.toMapOrdered(
			"Oanda-File", "ca.mss.rd.trader.src.deprecated.FXSourceOandaFile",
			"Oanda-Online", "ca.mss.rd.trader.src.oanda.FxOandaOnline",
			"Random", "ca.mss.rd.trader.src.random.ForexSourceRandom",
			"Random-File", "ca.mss.rd.trader.src.random..ForexSourceRandomFile"
		);

	final static public String getDataSourceClass(String name){
		return AvailableData.FOREX_QUOTE_SOURCE.get(name);
	}
	
	final static public Set<String> getDataSourceSet(){
    	return FOREX_QUOTE_SOURCE.keySet();
    }

}
