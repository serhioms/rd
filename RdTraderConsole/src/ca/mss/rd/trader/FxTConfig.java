package ca.mss.rd.trader;

import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ca.mss.rd.util.UtilMisc;
import ca.mss.rd.util.UtilProperty;

public class FxTConfig {

	/*
	 * Local folder root
	 */
    static public String DATA_ROOT = "data";

	/*
	 * Available data sources
	 */
	static private Map<String, String> DATA_SOURCE_CLASS = UtilMisc.toMapOrdered(
			"Oanda-File", "ca.mss.rd.trader.src.deprecated.FXSourceOandaFile",
			"Oanda-File2", "ca.mss.rd.trader.src.oanda.FxOandaFile",
			"Oanda-Online", "ca.mss.rd.trader.src.oanda.FxOandaOnline"
		);
	
	final static public String getDataSourceClass(String name){
		return DATA_SOURCE_CLASS.get(name);
	}

	
	/*
	 * Online  
	 */
	static public Set<String> ONLINE_QUOTES = UtilMisc.toSet(
			"Oanda.com"
		);

	/*
	 * Menu
	 */
	public static String[] MENU_ROOT = "Root".split(","); 
	
	public static Map<String,String[]> MENU_ITEMS = UtilMisc.toMapArray(
			"Root", "Lab,Recoder",
			"Recoder", UtilMisc.toString(FxTConfig.ONLINE_QUOTES.iterator(),",",""),
			"Lab", "BacktestLab"
		); 

	public static Map<String,Map<String,String>> MENU_PROPS = UtilMisc.toMapMap(
			"Root", "Label=Tabs,Description=Open new tab,Mnemonic="+KeyEvent.VK_T,
			"Lab", "Label=Trader Lab,Type=Menu,KeyEvent=1,Icon=images/demo.png,Tip=,Mnemonic="+KeyEvent.VK_L,
			"BacktestLab", "Label=Back Testing,Type=Item,Description=Trader backtesting,KeyEvent=2,Icon=images/demo.png,Tip=,Mnemonic="+KeyEvent.VK_B,
			"Recoder", "Label=Quotes Recoder,Type=Menu,KeyEvent=1,Icon=images/demo.png,Tip=,Mnemonic="+KeyEvent.VK_R
			); 
			
	/*
	 * Tabs
	 */
	public static Map<String,Map<String,String>> TAB_PROPS = UtilMisc.toMapMap(
			"BacktestLab", "Label=Back Testing,Clazz=ca.mss.rd.trader.view.tabs.lab.LabPanel,DataSource=Oanda-File2,Icon=images/demo.png,Tip=Trader laboratory,Mnemonic="+KeyEvent.VK_B,
			"Oanda.com", "Label=Oanda Recoder,Clazz=ca.mss.rd.trader.view.tabs.recoder.RecorderPanel,DataSource=Oanda-Online,Icon=images/demo.png,Tip=Record oanda.com online quotes,Mnemonic="+KeyEvent.VK_O
			); 
	
	/*
	 * Quotes
	 */
    static public List<String> FOREX_QUOTE = UtilMisc.toList(
    		"EURUSD",
			"GBPUSD",
			"USDCHF",
			"AUDUSD",
			"USDCAD",
			"EURJPY",
			"EURCHF",
			"USDCNY",
			"EURSEK",
			"XAGUSD",
			"USDDKK",
			"NZDUSD",
			"CHFJPY",
			"GBPCHF",
			"US30USD",
			"CADJPY",
			"NZDJPY",
			"BCOUSD",
			"WTICOUSD",
			"DE30EUR",
			"UK100GBP",
			"JP225USD",
			"HK33HKD",
			"XCUUSD",
			"GBPJPY",
			"AUDJPY",
			"EURNZD",
			"EURCAD",
			"SPX500USD",
			"NAS100USD",
			"USDJPY",
			"EURGBP"
	);
    
    
	static { 
		UtilProperty.readConstants(FxTConfig.class); 
	}
}
