package ca.mss.rd.trade.parser.oanda;

import java.util.HashMap;
import java.util.Map;

import ca.mss.rd.parser.impl.DefaultRow;
import ca.mss.rd.parser.impl.GenericRecord;
import ca.mss.rd.parser.impl.ParserRow;
import ca.mss.rd.util.Logger;

public class OandaParserNewRow extends DefaultRow implements ParserRow {

	final static public String module = OandaParserNewRow.class.getName();
	final static public long serialVersionUID = module.hashCode();

	static private String map(Map<String, Integer> map, String name, int index) {
		map.put(name, index);
		return name;
	}

	static private Map<String, Integer> DATA_RECORD_MAPPER = new HashMap<String, Integer>();

	static private int index = 0;
	static private String[] DATA_RECORD_HEADER = new String[] { 
			map(DATA_RECORD_MAPPER, "Same", index++),
			map(DATA_RECORD_MAPPER, "Date", index++), 
			map(DATA_RECORD_MAPPER, "Time", index++),
			map(DATA_RECORD_MAPPER, "Reqstd", index++), 
			map(DATA_RECORD_MAPPER, "Durtn", index++),
			map(DATA_RECORD_MAPPER, "EURUSD", index++), map(DATA_RECORD_MAPPER, "EURUSDBid", index++),
			map(DATA_RECORD_MAPPER, "EURUSDAsk", index++), map(DATA_RECORD_MAPPER, "EURUSD1", index++),
			map(DATA_RECORD_MAPPER, "EURUSD2", index++), map(DATA_RECORD_MAPPER, "EURUSDSpread", index++),
			map(DATA_RECORD_MAPPER, "EURUSD3", index++), map(DATA_RECORD_MAPPER, "EURUSD4", index++),
			map(DATA_RECORD_MAPPER, "GBPUSD", index++), map(DATA_RECORD_MAPPER, "GBPUSDBid", index++),
			map(DATA_RECORD_MAPPER, "GBPUSDAsk", index++), map(DATA_RECORD_MAPPER, "GBPUSD1", index++),
			map(DATA_RECORD_MAPPER, "GBPUSD2", index++), map(DATA_RECORD_MAPPER, "GBPUSDSpread", index++),
			map(DATA_RECORD_MAPPER, "GBPUSD3", index++), map(DATA_RECORD_MAPPER, "GBPUSD4", index++),
			map(DATA_RECORD_MAPPER, "USDCHF", index++), map(DATA_RECORD_MAPPER, "USDCHFBid", index++),
			map(DATA_RECORD_MAPPER, "USDCHFAsk", index++), map(DATA_RECORD_MAPPER, "USDCHF1", index++),
			map(DATA_RECORD_MAPPER, "USDCHF2", index++), map(DATA_RECORD_MAPPER, "USDCHFSpread", index++),
			map(DATA_RECORD_MAPPER, "USDCHF3", index++), map(DATA_RECORD_MAPPER, "USDCHF4", index++),
			map(DATA_RECORD_MAPPER, "USDJPY", index++), map(DATA_RECORD_MAPPER, "USDJPYBid", index++),
			map(DATA_RECORD_MAPPER, "USDJPYAsk", index++), map(DATA_RECORD_MAPPER, "USDJPY1", index++),
			map(DATA_RECORD_MAPPER, "USDJPY2", index++), map(DATA_RECORD_MAPPER, "USDJPYSpread", index++),
			map(DATA_RECORD_MAPPER, "USDJPY3", index++), map(DATA_RECORD_MAPPER, "USDJPY4", index++),
			map(DATA_RECORD_MAPPER, "AUDUSD", index++), map(DATA_RECORD_MAPPER, "AUDUSDBid", index++),
			map(DATA_RECORD_MAPPER, "AUDUSDAsk", index++), map(DATA_RECORD_MAPPER, "AUDUSD1", index++),
			map(DATA_RECORD_MAPPER, "AUDUSD2", index++), map(DATA_RECORD_MAPPER, "AUDUSDSpread", index++),
			map(DATA_RECORD_MAPPER, "AUDUSD3", index++), map(DATA_RECORD_MAPPER, "AUDUSD4", index++),
			map(DATA_RECORD_MAPPER, "USDCAD", index++), map(DATA_RECORD_MAPPER, "USDCADBid", index++),
			map(DATA_RECORD_MAPPER, "USDCADAsk", index++), map(DATA_RECORD_MAPPER, "USDCAD1", index++),
			map(DATA_RECORD_MAPPER, "USDCAD2", index++), map(DATA_RECORD_MAPPER, "USDCADSpread", index++),
			map(DATA_RECORD_MAPPER, "USDCAD3", index++), map(DATA_RECORD_MAPPER, "USDCAD4", index++),
			map(DATA_RECORD_MAPPER, "XAUUSD", index++), map(DATA_RECORD_MAPPER, "XAUUSDBid", index++),
			map(DATA_RECORD_MAPPER, "XAUUSDAsk", index++), map(DATA_RECORD_MAPPER, "XAUUSD1", index++),
			map(DATA_RECORD_MAPPER, "XAUUSD2", index++), map(DATA_RECORD_MAPPER, "XAUUSDSpread", index++),
			map(DATA_RECORD_MAPPER, "XAUUSD3", index++), map(DATA_RECORD_MAPPER, "XAUUSD4", index++),
			map(DATA_RECORD_MAPPER, "EURJPY", index++), map(DATA_RECORD_MAPPER, "EURJPYBid", index++),
			map(DATA_RECORD_MAPPER, "EURJPYAsk", index++), map(DATA_RECORD_MAPPER, "EURJPY1", index++),
			map(DATA_RECORD_MAPPER, "EURJPY2", index++), map(DATA_RECORD_MAPPER, "EURJPYSpread", index++),
			map(DATA_RECORD_MAPPER, "EURJPY3", index++), map(DATA_RECORD_MAPPER, "EURJPY4", index++),
			map(DATA_RECORD_MAPPER, "EURGBP", index++), map(DATA_RECORD_MAPPER, "EURGBPBid", index++),
			map(DATA_RECORD_MAPPER, "EURGBPAsk", index++), map(DATA_RECORD_MAPPER, "EURGBP1", index++),
			map(DATA_RECORD_MAPPER, "EURGBP2", index++), map(DATA_RECORD_MAPPER, "EURGBPSpread", index++),
			map(DATA_RECORD_MAPPER, "EURGBP3", index++), map(DATA_RECORD_MAPPER, "EURGBP4", index++),
			map(DATA_RECORD_MAPPER, "EURCHF", index++), map(DATA_RECORD_MAPPER, "EURCHFBid", index++),
			map(DATA_RECORD_MAPPER, "EURCHFAsk", index++), map(DATA_RECORD_MAPPER, "EURCHF1", index++),
			map(DATA_RECORD_MAPPER, "EURCHF2", index++), map(DATA_RECORD_MAPPER, "EURCHFSpread", index++),
			map(DATA_RECORD_MAPPER, "EURCHF3", index++), map(DATA_RECORD_MAPPER, "EURCHF4", index++),
			map(DATA_RECORD_MAPPER, "USDCNY", index++), map(DATA_RECORD_MAPPER, "USDCNYBid", index++),
			map(DATA_RECORD_MAPPER, "USDCNYAsk", index++), map(DATA_RECORD_MAPPER, "USDCNY1", index++),
			map(DATA_RECORD_MAPPER, "USDCNY2", index++), map(DATA_RECORD_MAPPER, "USDCNYSpread", index++),
			map(DATA_RECORD_MAPPER, "USDCNY3", index++), map(DATA_RECORD_MAPPER, "USDCNY4", index++),
			map(DATA_RECORD_MAPPER, "EURSEK", index++), map(DATA_RECORD_MAPPER, "EURSEKBid", index++),
			map(DATA_RECORD_MAPPER, "EURSEKAsk", index++), map(DATA_RECORD_MAPPER, "EURSEK1", index++),
			map(DATA_RECORD_MAPPER, "EURSEK2", index++), map(DATA_RECORD_MAPPER, "EURSEKSpread", index++),
			map(DATA_RECORD_MAPPER, "EURSEK3", index++), map(DATA_RECORD_MAPPER, "EURSEK4", index++),
			map(DATA_RECORD_MAPPER, "XAGUSD", index++), map(DATA_RECORD_MAPPER, "XAGUSDBid", index++),
			map(DATA_RECORD_MAPPER, "XAGUSDAsk", index++), map(DATA_RECORD_MAPPER, "XAGUSD1", index++),
			map(DATA_RECORD_MAPPER, "XAGUSD2", index++), map(DATA_RECORD_MAPPER, "XAGUSDSpread", index++),
			map(DATA_RECORD_MAPPER, "XAGUSD3", index++), map(DATA_RECORD_MAPPER, "XAGUSD4", index++),
			map(DATA_RECORD_MAPPER, "USDDKK", index++), map(DATA_RECORD_MAPPER, "USDDKKBid", index++),
			map(DATA_RECORD_MAPPER, "USDDKKAsk", index++), map(DATA_RECORD_MAPPER, "USDDKK1", index++),
			map(DATA_RECORD_MAPPER, "USDDKK2", index++), map(DATA_RECORD_MAPPER, "USDDKKSpread", index++),
			map(DATA_RECORD_MAPPER, "USDDKK3", index++), map(DATA_RECORD_MAPPER, "USDDKK4", index++),
			map(DATA_RECORD_MAPPER, "NZDUSD", index++), map(DATA_RECORD_MAPPER, "NZDUSDBid", index++),
			map(DATA_RECORD_MAPPER, "NZDUSDAsk", index++), map(DATA_RECORD_MAPPER, "NZDUSD1", index++),
			map(DATA_RECORD_MAPPER, "NZDUSD2", index++), map(DATA_RECORD_MAPPER, "NZDUSDSpread", index++),
			map(DATA_RECORD_MAPPER, "NZDUSD3", index++), map(DATA_RECORD_MAPPER, "NZDUSD4", index++),
			map(DATA_RECORD_MAPPER, "CHFJPY", index++), map(DATA_RECORD_MAPPER, "CHFJPYBid", index++),
			map(DATA_RECORD_MAPPER, "CHFJPYAsk", index++), map(DATA_RECORD_MAPPER, "CHFJPY1", index++),
			map(DATA_RECORD_MAPPER, "CHFJPY2", index++), map(DATA_RECORD_MAPPER, "CHFJPYSpread", index++),
			map(DATA_RECORD_MAPPER, "CHFJPY3", index++), map(DATA_RECORD_MAPPER, "CHFJPY4", index++),
			map(DATA_RECORD_MAPPER, "GBPCHF", index++), map(DATA_RECORD_MAPPER, "GBPCHFBid", index++),
			map(DATA_RECORD_MAPPER, "GBPCHFAsk", index++), map(DATA_RECORD_MAPPER, "GBPCHF1", index++),
			map(DATA_RECORD_MAPPER, "GBPCHF2", index++), map(DATA_RECORD_MAPPER, "GBPCHFSpread", index++),
			map(DATA_RECORD_MAPPER, "GBPCHF3", index++), map(DATA_RECORD_MAPPER, "GBPCHF4", index++),
			map(DATA_RECORD_MAPPER, "NZDJPY", index++), map(DATA_RECORD_MAPPER, "NZDJPYBid", index++),
			map(DATA_RECORD_MAPPER, "NZDJPYAsk", index++), map(DATA_RECORD_MAPPER, "NZDJPY1", index++),
			map(DATA_RECORD_MAPPER, "NZDJPY2", index++), map(DATA_RECORD_MAPPER, "NZDJPYSpread", index++),
			map(DATA_RECORD_MAPPER, "NZDJPY3", index++), map(DATA_RECORD_MAPPER, "NZDJPY4", index++),
			map(DATA_RECORD_MAPPER, "EURNZD", index++), map(DATA_RECORD_MAPPER, "EURNZDBid", index++),
			map(DATA_RECORD_MAPPER, "EURNZDAsk", index++), map(DATA_RECORD_MAPPER, "EURNZD1", index++),
			map(DATA_RECORD_MAPPER, "EURNZD2", index++), map(DATA_RECORD_MAPPER, "EURNZDSpread", index++),
			map(DATA_RECORD_MAPPER, "EURNZD3", index++), map(DATA_RECORD_MAPPER, "EURNZD4", index++),
			map(DATA_RECORD_MAPPER, "CADJPY", index++), map(DATA_RECORD_MAPPER, "CADJPYBid", index++),
			map(DATA_RECORD_MAPPER, "CADJPYAsk", index++), map(DATA_RECORD_MAPPER, "CADJPY1", index++),
			map(DATA_RECORD_MAPPER, "CADJPY2", index++), map(DATA_RECORD_MAPPER, "CADJPYSpread", index++),
			map(DATA_RECORD_MAPPER, "CADJPY3", index++), map(DATA_RECORD_MAPPER, "CADJPY4", index++),
			map(DATA_RECORD_MAPPER, "EURCAD", index++), map(DATA_RECORD_MAPPER, "EURCADBid", index++),
			map(DATA_RECORD_MAPPER, "EURCADAsk", index++), map(DATA_RECORD_MAPPER, "EURCAD1", index++),
			map(DATA_RECORD_MAPPER, "EURCAD2", index++), map(DATA_RECORD_MAPPER, "EURCADSpread", index++),
			map(DATA_RECORD_MAPPER, "EURCAD3", index++), map(DATA_RECORD_MAPPER, "EURCAD4", index++),
			map(DATA_RECORD_MAPPER, "US30USD", index++), map(DATA_RECORD_MAPPER, "US30USDBid", index++),
			map(DATA_RECORD_MAPPER, "US30USDAsk", index++), map(DATA_RECORD_MAPPER, "US30USD1", index++),
			map(DATA_RECORD_MAPPER, "US30USD2", index++), map(DATA_RECORD_MAPPER, "US30USDSpread", index++),
			map(DATA_RECORD_MAPPER, "US30USD3", index++), map(DATA_RECORD_MAPPER, "US30USD4", index++),
			map(DATA_RECORD_MAPPER, "SPX500USD", index++), map(DATA_RECORD_MAPPER, "SPX500USDBid", index++),
			map(DATA_RECORD_MAPPER, "SPX500USDAsk", index++), map(DATA_RECORD_MAPPER, "SPX500USD1", index++),
			map(DATA_RECORD_MAPPER, "SPX500USD2", index++), map(DATA_RECORD_MAPPER, "SPX500USDSpread", index++),
			map(DATA_RECORD_MAPPER, "SPX500USD3", index++), map(DATA_RECORD_MAPPER, "SPX500USD4", index++),
			map(DATA_RECORD_MAPPER, "BCOUSD", index++), map(DATA_RECORD_MAPPER, "BCOUSDBid", index++),
			map(DATA_RECORD_MAPPER, "BCOUSDAsk", index++), map(DATA_RECORD_MAPPER, "BCOUSD1", index++),
			map(DATA_RECORD_MAPPER, "BCOUSD2", index++), map(DATA_RECORD_MAPPER, "BCOUSDSpread", index++),
			map(DATA_RECORD_MAPPER, "BCOUSD3", index++), map(DATA_RECORD_MAPPER, "BCOUSD4", index++),
			map(DATA_RECORD_MAPPER, "WTICOUSD", index++), map(DATA_RECORD_MAPPER, "WTICOUSDBid", index++),
			map(DATA_RECORD_MAPPER, "WTICOUSDAsk", index++), map(DATA_RECORD_MAPPER, "WTICOUSD1", index++),
			map(DATA_RECORD_MAPPER, "WTICOUSD2", index++), map(DATA_RECORD_MAPPER, "WTICOUSDSpread", index++),
			map(DATA_RECORD_MAPPER, "WTICOUSD3", index++), map(DATA_RECORD_MAPPER, "WTICOUSD4", index++),
			map(DATA_RECORD_MAPPER, "DE30EUR", index++), map(DATA_RECORD_MAPPER, "DE30EURBid", index++),
			map(DATA_RECORD_MAPPER, "DE30EURAsk", index++), map(DATA_RECORD_MAPPER, "DE30EUR1", index++),
			map(DATA_RECORD_MAPPER, "DE30EUR2", index++), map(DATA_RECORD_MAPPER, "DE30EURSpread", index++),
			map(DATA_RECORD_MAPPER, "DE30EUR3", index++), map(DATA_RECORD_MAPPER, "DE30EUR4", index++),
			map(DATA_RECORD_MAPPER, "UK100GBP", index++), map(DATA_RECORD_MAPPER, "UK100GBPBid", index++),
			map(DATA_RECORD_MAPPER, "UK100GBPAsk", index++), map(DATA_RECORD_MAPPER, "UK100GBP1", index++),
			map(DATA_RECORD_MAPPER, "UK100GBP2", index++), map(DATA_RECORD_MAPPER, "UK100GBPSpread", index++),
			map(DATA_RECORD_MAPPER, "UK100GBP3", index++), map(DATA_RECORD_MAPPER, "UK100GBP4", index++),
			map(DATA_RECORD_MAPPER, "JP225USD", index++), map(DATA_RECORD_MAPPER, "JP225USDBid", index++),
			map(DATA_RECORD_MAPPER, "JP225USDAsk", index++), map(DATA_RECORD_MAPPER, "JP225USD1", index++),
			map(DATA_RECORD_MAPPER, "JP225USD2", index++), map(DATA_RECORD_MAPPER, "JP225USDSpread", index++),
			map(DATA_RECORD_MAPPER, "JP225USD3", index++), map(DATA_RECORD_MAPPER, "JP225USD4", index++),
			map(DATA_RECORD_MAPPER, "HK33HKD", index++), map(DATA_RECORD_MAPPER, "HK33HKDBid", index++),
			map(DATA_RECORD_MAPPER, "HK33HKDAsk", index++), map(DATA_RECORD_MAPPER, "HK33HKD1", index++),
			map(DATA_RECORD_MAPPER, "HK33HKD2", index++), map(DATA_RECORD_MAPPER, "HK33HKDSpread", index++),
			map(DATA_RECORD_MAPPER, "HK33HKD3", index++), map(DATA_RECORD_MAPPER, "HK33HKD4", index++),
			map(DATA_RECORD_MAPPER, "NAS100USD", index++), map(DATA_RECORD_MAPPER, "NAS100USDBid", index++),
			map(DATA_RECORD_MAPPER, "NAS100USDAsk", index++), map(DATA_RECORD_MAPPER, "NAS100USD1", index++),
			map(DATA_RECORD_MAPPER, "NAS100USD2", index++), map(DATA_RECORD_MAPPER, "NAS100USDSpread", index++),
			map(DATA_RECORD_MAPPER, "NAS100USD3", index++), map(DATA_RECORD_MAPPER, "NAS100USD4", index++),
			map(DATA_RECORD_MAPPER, "XCUUSD", index++), map(DATA_RECORD_MAPPER, "XCUUSDBid", index++),
			map(DATA_RECORD_MAPPER, "XCUUSDAsk", index++), map(DATA_RECORD_MAPPER, "XCUUSD1", index++),
			map(DATA_RECORD_MAPPER, "XCUUSD2", index++), map(DATA_RECORD_MAPPER, "XCUUSDSpread", index++),
			map(DATA_RECORD_MAPPER, "XCUUSD3", index++), map(DATA_RECORD_MAPPER, "XCUUSD4", index++),
			map(DATA_RECORD_MAPPER, "GBPJPY", index++), map(DATA_RECORD_MAPPER, "GBPJPYBid", index++),
			map(DATA_RECORD_MAPPER, "GBPJPYAsk", index++), map(DATA_RECORD_MAPPER, "GBPJPY1", index++),
			map(DATA_RECORD_MAPPER, "GBPJPY2", index++), map(DATA_RECORD_MAPPER, "GBPJPYSpread", index++),
			map(DATA_RECORD_MAPPER, "GBPJPY3", index++), map(DATA_RECORD_MAPPER, "GBPJPY4", index++),
			map(DATA_RECORD_MAPPER, "AUDJPY", index++), map(DATA_RECORD_MAPPER, "AUDJPYBid", index++),
			map(DATA_RECORD_MAPPER, "AUDJPYAsk", index++), map(DATA_RECORD_MAPPER, "AUDJPY1", index++),
			map(DATA_RECORD_MAPPER, "AUDJPY2", index++), map(DATA_RECORD_MAPPER, "AUDJPYSpread", index++),
			map(DATA_RECORD_MAPPER, "AUDJPY3", index++), map(DATA_RECORD_MAPPER, "AUDJPY4", index++),
			map(DATA_RECORD_MAPPER, "AUDCAD", index++), map(DATA_RECORD_MAPPER, "AUDCADBid", index++),
			map(DATA_RECORD_MAPPER, "AUDCADAsk", index++), map(DATA_RECORD_MAPPER, "AUDCAD1", index++),
			map(DATA_RECORD_MAPPER, "AUDCAD2", index++), map(DATA_RECORD_MAPPER, "AUDCADSpread", index++),
			map(DATA_RECORD_MAPPER, "AUDCAD3", index++), map(DATA_RECORD_MAPPER, "AUDCAD4", index++),
			map(DATA_RECORD_MAPPER, "USB05YUSD", index++), map(DATA_RECORD_MAPPER, "USB05YUSDBid", index++),
			map(DATA_RECORD_MAPPER, "USB05YUSDAsk", index++), map(DATA_RECORD_MAPPER, "USB05YUSD1", index++),
			map(DATA_RECORD_MAPPER, "USB05YUSD2", index++), map(DATA_RECORD_MAPPER, "USB05YUSDSpread", index++),
			map(DATA_RECORD_MAPPER, "USB05YUSD3", index++), map(DATA_RECORD_MAPPER, "USB05YUSD4", index++),
			map(DATA_RECORD_MAPPER, "USB10YUSD", index++), map(DATA_RECORD_MAPPER, "USB10YUSDBid", index++),
			map(DATA_RECORD_MAPPER, "USB10YUSDAsk", index++), map(DATA_RECORD_MAPPER, "USB10YUSD1", index++),
			map(DATA_RECORD_MAPPER, "USB10YUSD2", index++), map(DATA_RECORD_MAPPER, "USB10YUSDSpread", index++),
			map(DATA_RECORD_MAPPER, "USB10YUSD3", index++), map(DATA_RECORD_MAPPER, "USB10YUSD4", index++),
			map(DATA_RECORD_MAPPER, "EU50EUR", index++), map(DATA_RECORD_MAPPER, "EU50EURBid", index++),
			map(DATA_RECORD_MAPPER, "EU50EURAsk", index++), map(DATA_RECORD_MAPPER, "EU50EUR1", index++),
			map(DATA_RECORD_MAPPER, "EU50EUR2", index++), map(DATA_RECORD_MAPPER, "EU50EURSpread", index++),
			map(DATA_RECORD_MAPPER, "EU50EUR3", index++), map(DATA_RECORD_MAPPER, "EU50EUR4", index++),
			map(DATA_RECORD_MAPPER, "DE10YBEUR", index++), map(DATA_RECORD_MAPPER, "DE10YBEURBid", index++),
			map(DATA_RECORD_MAPPER, "DE10YBEURAsk", index++), map(DATA_RECORD_MAPPER, "DE10YBEUR1", index++),
			map(DATA_RECORD_MAPPER, "DE10YBEUR2", index++), map(DATA_RECORD_MAPPER, "DE10YBEURSpread", index++),
			map(DATA_RECORD_MAPPER, "DE10YBEUR3", index++), map(DATA_RECORD_MAPPER, "DE10YBEUR4", index++),
			map(DATA_RECORD_MAPPER, "UK10YBGBP", index++), map(DATA_RECORD_MAPPER, "UK10YBGBPBid", index++),
			map(DATA_RECORD_MAPPER, "UK10YBGBPAsk", index++), map(DATA_RECORD_MAPPER, "UK10YBGBP1", index++),
			map(DATA_RECORD_MAPPER, "UK10YBGBP2", index++), map(DATA_RECORD_MAPPER, "UK10YBGBPSpread", index++),
			map(DATA_RECORD_MAPPER, "UK10YBGBP3", index++), map(DATA_RECORD_MAPPER, "UK10YBGBP4", index++),
			map(DATA_RECORD_MAPPER, "NATGASUSD", index++), map(DATA_RECORD_MAPPER, "NATGASUSDBid", index++),
			map(DATA_RECORD_MAPPER, "NATGASUSDAsk", index++), map(DATA_RECORD_MAPPER, "NATGASUSD1", index++),
			map(DATA_RECORD_MAPPER, "NATGASUSD2", index++), map(DATA_RECORD_MAPPER, "NATGASUSDSpread", index++),
			map(DATA_RECORD_MAPPER, "NATGASUSD3", index++), map(DATA_RECORD_MAPPER, "NATGASUSD4", index++),
			map(DATA_RECORD_MAPPER, "WHEATUSD", index++), map(DATA_RECORD_MAPPER, "WHEATUSDBid", index++),
			map(DATA_RECORD_MAPPER, "WHEATUSDAsk", index++), map(DATA_RECORD_MAPPER, "WHEATUSD1", index++),
			map(DATA_RECORD_MAPPER, "WHEATUSD2", index++), map(DATA_RECORD_MAPPER, "WHEATUSDSpread", index++),
			map(DATA_RECORD_MAPPER, "WHEATUSD3", index++), map(DATA_RECORD_MAPPER, "WHEATUSD4", index++),
			map(DATA_RECORD_MAPPER, "SUGARUSD", index++), map(DATA_RECORD_MAPPER, "SUGARUSDBid", index++),
			map(DATA_RECORD_MAPPER, "SUGARUSDAsk", index++), map(DATA_RECORD_MAPPER, "SUGARUSD1", index++),
			map(DATA_RECORD_MAPPER, "SUGARUSD2", index++), map(DATA_RECORD_MAPPER, "SUGARUSDSpread", index++),
			map(DATA_RECORD_MAPPER, "SUGARUSD3", index++), map(DATA_RECORD_MAPPER, "SUGARUSD4", index++),
			map(DATA_RECORD_MAPPER, "XPTUSD", index++), map(DATA_RECORD_MAPPER, "XPTUSDBid", index++),
			map(DATA_RECORD_MAPPER, "XPTUSDAsk", index++), map(DATA_RECORD_MAPPER, "XPTUSD1", index++),
			map(DATA_RECORD_MAPPER, "XPTUSD2", index++), map(DATA_RECORD_MAPPER, "XPTUSDSpread", index++),
			map(DATA_RECORD_MAPPER, "XPTUSD3", index++), map(DATA_RECORD_MAPPER, "XPTUSD4", index++),
			map(DATA_RECORD_MAPPER, "XPDUSD", index++), map(DATA_RECORD_MAPPER, "XPDUSDBid", index++),
			map(DATA_RECORD_MAPPER, "XPDUSDAsk", index++), map(DATA_RECORD_MAPPER, "XPDUSD1", index++),
			map(DATA_RECORD_MAPPER, "XPDUSD2", index++), map(DATA_RECORD_MAPPER, "XPDUSDSpread", index++),
			map(DATA_RECORD_MAPPER, "XPDUSD3", index++), map(DATA_RECORD_MAPPER, "XPDUSD4", index++),
			map(DATA_RECORD_MAPPER, "AU200AUD", index++), map(DATA_RECORD_MAPPER, "AU200AUDBid", index++),
			map(DATA_RECORD_MAPPER, "AU200AUDAsk", index++), map(DATA_RECORD_MAPPER, "AU200AUD1", index++),
			map(DATA_RECORD_MAPPER, "AU200AUD2", index++), map(DATA_RECORD_MAPPER, "AU200AUDSpread", index++),
			map(DATA_RECORD_MAPPER, "AU200AUD3", index++), map(DATA_RECORD_MAPPER, "AU200AUD4", index++),
			map(DATA_RECORD_MAPPER, "SG30SGD", index++), map(DATA_RECORD_MAPPER, "SG30SGDBid", index++),
			map(DATA_RECORD_MAPPER, "SG30SGDAsk", index++), map(DATA_RECORD_MAPPER, "SG30SGD1", index++),
			map(DATA_RECORD_MAPPER, "SG30SGD2", index++), map(DATA_RECORD_MAPPER, "SG30SGDSpread", index++),
			map(DATA_RECORD_MAPPER, "SG30SGD3", index++), map(DATA_RECORD_MAPPER, "SG30SGD4", index++) };

	private String[][] DATA_RECORD = new String[][] { null, DATA_RECORD_HEADER };
	final public Record record = new Record();

	public class Record implements GenericRecord {
		public String getSame() {
			return DATA_RECORD[0][0];
		}

		public String getDate() {
			return DATA_RECORD[0][1];
		}

		public String getTime() {
			return DATA_RECORD[0][2];
		}

		public String getReqstd() {
			return DATA_RECORD[0][3];
		}

		public String getDurtn() {
			return DATA_RECORD[0][4];
		}

		public String getEurusd() {
			return DATA_RECORD[0][5];
		}

		public String getEurusdbid() {
			return DATA_RECORD[0][6];
		}

		public String getEurusdask() {
			return DATA_RECORD[0][7];
		}

		public String getEurusd1() {
			return DATA_RECORD[0][8];
		}

		public String getEurusd2() {
			return DATA_RECORD[0][9];
		}

		public String getEurusdspread() {
			return DATA_RECORD[0][10];
		}

		public String getEurusd3() {
			return DATA_RECORD[0][11];
		}

		public String getEurusd4() {
			return DATA_RECORD[0][12];
		}

		public String getGbpusd() {
			return DATA_RECORD[0][13];
		}

		public String getGbpusdbid() {
			return DATA_RECORD[0][14];
		}

		public String getGbpusdask() {
			return DATA_RECORD[0][15];
		}

		public String getGbpusd1() {
			return DATA_RECORD[0][16];
		}

		public String getGbpusd2() {
			return DATA_RECORD[0][17];
		}

		public String getGbpusdspread() {
			return DATA_RECORD[0][18];
		}

		public String getGbpusd3() {
			return DATA_RECORD[0][19];
		}

		public String getGbpusd4() {
			return DATA_RECORD[0][20];
		}

		public String getUsdchf() {
			return DATA_RECORD[0][21];
		}

		public String getUsdchfbid() {
			return DATA_RECORD[0][22];
		}

		public String getUsdchfask() {
			return DATA_RECORD[0][23];
		}

		public String getUsdchf1() {
			return DATA_RECORD[0][24];
		}

		public String getUsdchf2() {
			return DATA_RECORD[0][25];
		}

		public String getUsdchfspread() {
			return DATA_RECORD[0][26];
		}

		public String getUsdchf3() {
			return DATA_RECORD[0][27];
		}

		public String getUsdchf4() {
			return DATA_RECORD[0][28];
		}

		public String getUsdjpy() {
			return DATA_RECORD[0][29];
		}

		public String getUsdjpybid() {
			return DATA_RECORD[0][30];
		}

		public String getUsdjpyask() {
			return DATA_RECORD[0][31];
		}

		public String getUsdjpy1() {
			return DATA_RECORD[0][32];
		}

		public String getUsdjpy2() {
			return DATA_RECORD[0][33];
		}

		public String getUsdjpyspread() {
			return DATA_RECORD[0][34];
		}

		public String getUsdjpy3() {
			return DATA_RECORD[0][35];
		}

		public String getUsdjpy4() {
			return DATA_RECORD[0][36];
		}

		public String getAudusd() {
			return DATA_RECORD[0][37];
		}

		public String getAudusdbid() {
			return DATA_RECORD[0][38];
		}

		public String getAudusdask() {
			return DATA_RECORD[0][39];
		}

		public String getAudusd1() {
			return DATA_RECORD[0][40];
		}

		public String getAudusd2() {
			return DATA_RECORD[0][41];
		}

		public String getAudusdspread() {
			return DATA_RECORD[0][42];
		}

		public String getAudusd3() {
			return DATA_RECORD[0][43];
		}

		public String getAudusd4() {
			return DATA_RECORD[0][44];
		}

		public String getUsdcad() {
			return DATA_RECORD[0][45];
		}

		public String getUsdcadbid() {
			return DATA_RECORD[0][46];
		}

		public String getUsdcadask() {
			return DATA_RECORD[0][47];
		}

		public String getUsdcad1() {
			return DATA_RECORD[0][48];
		}

		public String getUsdcad2() {
			return DATA_RECORD[0][49];
		}

		public String getUsdcadspread() {
			return DATA_RECORD[0][50];
		}

		public String getUsdcad3() {
			return DATA_RECORD[0][51];
		}

		public String getUsdcad4() {
			return DATA_RECORD[0][52];
		}

		public String getXauusd() {
			return DATA_RECORD[0][53];
		}

		public String getXauusdbid() {
			return DATA_RECORD[0][54];
		}

		public String getXauusdask() {
			return DATA_RECORD[0][55];
		}

		public String getXauusd1() {
			return DATA_RECORD[0][56];
		}

		public String getXauusd2() {
			return DATA_RECORD[0][57];
		}

		public String getXauusdspread() {
			return DATA_RECORD[0][58];
		}

		public String getXauusd3() {
			return DATA_RECORD[0][59];
		}

		public String getXauusd4() {
			return DATA_RECORD[0][60];
		}

		public String getEurjpy() {
			return DATA_RECORD[0][61];
		}

		public String getEurjpybid() {
			return DATA_RECORD[0][62];
		}

		public String getEurjpyask() {
			return DATA_RECORD[0][63];
		}

		public String getEurjpy1() {
			return DATA_RECORD[0][64];
		}

		public String getEurjpy2() {
			return DATA_RECORD[0][65];
		}

		public String getEurjpyspread() {
			return DATA_RECORD[0][66];
		}

		public String getEurjpy3() {
			return DATA_RECORD[0][67];
		}

		public String getEurjpy4() {
			return DATA_RECORD[0][68];
		}

		public String getEurgbp() {
			return DATA_RECORD[0][69];
		}

		public String getEurgbpbid() {
			return DATA_RECORD[0][70];
		}

		public String getEurgbpask() {
			return DATA_RECORD[0][71];
		}

		public String getEurgbp1() {
			return DATA_RECORD[0][72];
		}

		public String getEurgbp2() {
			return DATA_RECORD[0][73];
		}

		public String getEurgbpspread() {
			return DATA_RECORD[0][74];
		}

		public String getEurgbp3() {
			return DATA_RECORD[0][75];
		}

		public String getEurgbp4() {
			return DATA_RECORD[0][76];
		}

		public String getEurchf() {
			return DATA_RECORD[0][77];
		}

		public String getEurchfbid() {
			return DATA_RECORD[0][78];
		}

		public String getEurchfask() {
			return DATA_RECORD[0][79];
		}

		public String getEurchf1() {
			return DATA_RECORD[0][80];
		}

		public String getEurchf2() {
			return DATA_RECORD[0][81];
		}

		public String getEurchfspread() {
			return DATA_RECORD[0][82];
		}

		public String getEurchf3() {
			return DATA_RECORD[0][83];
		}

		public String getEurchf4() {
			return DATA_RECORD[0][84];
		}

		public String getUsdcny() {
			return DATA_RECORD[0][85];
		}

		public String getUsdcnybid() {
			return DATA_RECORD[0][86];
		}

		public String getUsdcnyask() {
			return DATA_RECORD[0][87];
		}

		public String getUsdcny1() {
			return DATA_RECORD[0][88];
		}

		public String getUsdcny2() {
			return DATA_RECORD[0][89];
		}

		public String getUsdcnyspread() {
			return DATA_RECORD[0][90];
		}

		public String getUsdcny3() {
			return DATA_RECORD[0][91];
		}

		public String getUsdcny4() {
			return DATA_RECORD[0][92];
		}

		public String getEursek() {
			return DATA_RECORD[0][93];
		}

		public String getEursekbid() {
			return DATA_RECORD[0][94];
		}

		public String getEursekask() {
			return DATA_RECORD[0][95];
		}

		public String getEursek1() {
			return DATA_RECORD[0][96];
		}

		public String getEursek2() {
			return DATA_RECORD[0][97];
		}

		public String getEursekspread() {
			return DATA_RECORD[0][98];
		}

		public String getEursek3() {
			return DATA_RECORD[0][99];
		}

		public String getEursek4() {
			return DATA_RECORD[0][100];
		}

		public String getXagusd() {
			return DATA_RECORD[0][101];
		}

		public String getXagusdbid() {
			return DATA_RECORD[0][102];
		}

		public String getXagusdask() {
			return DATA_RECORD[0][103];
		}

		public String getXagusd1() {
			return DATA_RECORD[0][104];
		}

		public String getXagusd2() {
			return DATA_RECORD[0][105];
		}

		public String getXagusdspread() {
			return DATA_RECORD[0][106];
		}

		public String getXagusd3() {
			return DATA_RECORD[0][107];
		}

		public String getXagusd4() {
			return DATA_RECORD[0][108];
		}

		public String getUsddkk() {
			return DATA_RECORD[0][109];
		}

		public String getUsddkkbid() {
			return DATA_RECORD[0][110];
		}

		public String getUsddkkask() {
			return DATA_RECORD[0][111];
		}

		public String getUsddkk1() {
			return DATA_RECORD[0][112];
		}

		public String getUsddkk2() {
			return DATA_RECORD[0][113];
		}

		public String getUsddkkspread() {
			return DATA_RECORD[0][114];
		}

		public String getUsddkk3() {
			return DATA_RECORD[0][115];
		}

		public String getUsddkk4() {
			return DATA_RECORD[0][116];
		}

		public String getNzdusd() {
			return DATA_RECORD[0][117];
		}

		public String getNzdusdbid() {
			return DATA_RECORD[0][118];
		}

		public String getNzdusdask() {
			return DATA_RECORD[0][119];
		}

		public String getNzdusd1() {
			return DATA_RECORD[0][120];
		}

		public String getNzdusd2() {
			return DATA_RECORD[0][121];
		}

		public String getNzdusdspread() {
			return DATA_RECORD[0][122];
		}

		public String getNzdusd3() {
			return DATA_RECORD[0][123];
		}

		public String getNzdusd4() {
			return DATA_RECORD[0][124];
		}

		public String getChfjpy() {
			return DATA_RECORD[0][125];
		}

		public String getChfjpybid() {
			return DATA_RECORD[0][126];
		}

		public String getChfjpyask() {
			return DATA_RECORD[0][127];
		}

		public String getChfjpy1() {
			return DATA_RECORD[0][128];
		}

		public String getChfjpy2() {
			return DATA_RECORD[0][129];
		}

		public String getChfjpyspread() {
			return DATA_RECORD[0][130];
		}

		public String getChfjpy3() {
			return DATA_RECORD[0][131];
		}

		public String getChfjpy4() {
			return DATA_RECORD[0][132];
		}

		public String getGbpchf() {
			return DATA_RECORD[0][133];
		}

		public String getGbpchfbid() {
			return DATA_RECORD[0][134];
		}

		public String getGbpchfask() {
			return DATA_RECORD[0][135];
		}

		public String getGbpchf1() {
			return DATA_RECORD[0][136];
		}

		public String getGbpchf2() {
			return DATA_RECORD[0][137];
		}

		public String getGbpchfspread() {
			return DATA_RECORD[0][138];
		}

		public String getGbpchf3() {
			return DATA_RECORD[0][139];
		}

		public String getGbpchf4() {
			return DATA_RECORD[0][140];
		}

		public String getNzdjpy() {
			return DATA_RECORD[0][141];
		}

		public String getNzdjpybid() {
			return DATA_RECORD[0][142];
		}

		public String getNzdjpyask() {
			return DATA_RECORD[0][143];
		}

		public String getNzdjpy1() {
			return DATA_RECORD[0][144];
		}

		public String getNzdjpy2() {
			return DATA_RECORD[0][145];
		}

		public String getNzdjpyspread() {
			return DATA_RECORD[0][146];
		}

		public String getNzdjpy3() {
			return DATA_RECORD[0][147];
		}

		public String getNzdjpy4() {
			return DATA_RECORD[0][148];
		}

		public String getEurnzd() {
			return DATA_RECORD[0][149];
		}

		public String getEurnzdbid() {
			return DATA_RECORD[0][150];
		}

		public String getEurnzdask() {
			return DATA_RECORD[0][151];
		}

		public String getEurnzd1() {
			return DATA_RECORD[0][152];
		}

		public String getEurnzd2() {
			return DATA_RECORD[0][153];
		}

		public String getEurnzdspread() {
			return DATA_RECORD[0][154];
		}

		public String getEurnzd3() {
			return DATA_RECORD[0][155];
		}

		public String getEurnzd4() {
			return DATA_RECORD[0][156];
		}

		public String getCadjpy() {
			return DATA_RECORD[0][157];
		}

		public String getCadjpybid() {
			return DATA_RECORD[0][158];
		}

		public String getCadjpyask() {
			return DATA_RECORD[0][159];
		}

		public String getCadjpy1() {
			return DATA_RECORD[0][160];
		}

		public String getCadjpy2() {
			return DATA_RECORD[0][161];
		}

		public String getCadjpyspread() {
			return DATA_RECORD[0][162];
		}

		public String getCadjpy3() {
			return DATA_RECORD[0][163];
		}

		public String getCadjpy4() {
			return DATA_RECORD[0][164];
		}

		public String getEurcad() {
			return DATA_RECORD[0][165];
		}

		public String getEurcadbid() {
			return DATA_RECORD[0][166];
		}

		public String getEurcadask() {
			return DATA_RECORD[0][167];
		}

		public String getEurcad1() {
			return DATA_RECORD[0][168];
		}

		public String getEurcad2() {
			return DATA_RECORD[0][169];
		}

		public String getEurcadspread() {
			return DATA_RECORD[0][170];
		}

		public String getEurcad3() {
			return DATA_RECORD[0][171];
		}

		public String getEurcad4() {
			return DATA_RECORD[0][172];
		}

		public String getUs30usd() {
			return DATA_RECORD[0][173];
		}

		public String getUs30usdbid() {
			return DATA_RECORD[0][174];
		}

		public String getUs30usdask() {
			return DATA_RECORD[0][175];
		}

		public String getUs30usd1() {
			return DATA_RECORD[0][176];
		}

		public String getUs30usd2() {
			return DATA_RECORD[0][177];
		}

		public String getUs30usdspread() {
			return DATA_RECORD[0][178];
		}

		public String getUs30usd3() {
			return DATA_RECORD[0][179];
		}

		public String getUs30usd4() {
			return DATA_RECORD[0][180];
		}

		public String getSpx500usd() {
			return DATA_RECORD[0][181];
		}

		public String getSpx500usdbid() {
			return DATA_RECORD[0][182];
		}

		public String getSpx500usdask() {
			return DATA_RECORD[0][183];
		}

		public String getSpx500usd1() {
			return DATA_RECORD[0][184];
		}

		public String getSpx500usd2() {
			return DATA_RECORD[0][185];
		}

		public String getSpx500usdspread() {
			return DATA_RECORD[0][186];
		}

		public String getSpx500usd3() {
			return DATA_RECORD[0][187];
		}

		public String getSpx500usd4() {
			return DATA_RECORD[0][188];
		}

		public String getBcousd() {
			return DATA_RECORD[0][189];
		}

		public String getBcousdbid() {
			return DATA_RECORD[0][190];
		}

		public String getBcousdask() {
			return DATA_RECORD[0][191];
		}

		public String getBcousd1() {
			return DATA_RECORD[0][192];
		}

		public String getBcousd2() {
			return DATA_RECORD[0][193];
		}

		public String getBcousdspread() {
			return DATA_RECORD[0][194];
		}

		public String getBcousd3() {
			return DATA_RECORD[0][195];
		}

		public String getBcousd4() {
			return DATA_RECORD[0][196];
		}

		public String getWticousd() {
			return DATA_RECORD[0][197];
		}

		public String getWticousdbid() {
			return DATA_RECORD[0][198];
		}

		public String getWticousdask() {
			return DATA_RECORD[0][199];
		}

		public String getWticousd1() {
			return DATA_RECORD[0][200];
		}

		public String getWticousd2() {
			return DATA_RECORD[0][201];
		}

		public String getWticousdspread() {
			return DATA_RECORD[0][202];
		}

		public String getWticousd3() {
			return DATA_RECORD[0][203];
		}

		public String getWticousd4() {
			return DATA_RECORD[0][204];
		}

		public String getDe30eur() {
			return DATA_RECORD[0][205];
		}

		public String getDe30eurbid() {
			return DATA_RECORD[0][206];
		}

		public String getDe30eurask() {
			return DATA_RECORD[0][207];
		}

		public String getDe30eur1() {
			return DATA_RECORD[0][208];
		}

		public String getDe30eur2() {
			return DATA_RECORD[0][209];
		}

		public String getDe30eurspread() {
			return DATA_RECORD[0][210];
		}

		public String getDe30eur3() {
			return DATA_RECORD[0][211];
		}

		public String getDe30eur4() {
			return DATA_RECORD[0][212];
		}

		public String getUk100gbp() {
			return DATA_RECORD[0][213];
		}

		public String getUk100gbpbid() {
			return DATA_RECORD[0][214];
		}

		public String getUk100gbpask() {
			return DATA_RECORD[0][215];
		}

		public String getUk100gbp1() {
			return DATA_RECORD[0][216];
		}

		public String getUk100gbp2() {
			return DATA_RECORD[0][217];
		}

		public String getUk100gbpspread() {
			return DATA_RECORD[0][218];
		}

		public String getUk100gbp3() {
			return DATA_RECORD[0][219];
		}

		public String getUk100gbp4() {
			return DATA_RECORD[0][220];
		}

		public String getJp225usd() {
			return DATA_RECORD[0][221];
		}

		public String getJp225usdbid() {
			return DATA_RECORD[0][222];
		}

		public String getJp225usdask() {
			return DATA_RECORD[0][223];
		}

		public String getJp225usd1() {
			return DATA_RECORD[0][224];
		}

		public String getJp225usd2() {
			return DATA_RECORD[0][225];
		}

		public String getJp225usdspread() {
			return DATA_RECORD[0][226];
		}

		public String getJp225usd3() {
			return DATA_RECORD[0][227];
		}

		public String getJp225usd4() {
			return DATA_RECORD[0][228];
		}

		public String getHk33hkd() {
			return DATA_RECORD[0][229];
		}

		public String getHk33hkdbid() {
			return DATA_RECORD[0][230];
		}

		public String getHk33hkdask() {
			return DATA_RECORD[0][231];
		}

		public String getHk33hkd1() {
			return DATA_RECORD[0][232];
		}

		public String getHk33hkd2() {
			return DATA_RECORD[0][233];
		}

		public String getHk33hkdspread() {
			return DATA_RECORD[0][234];
		}

		public String getHk33hkd3() {
			return DATA_RECORD[0][235];
		}

		public String getHk33hkd4() {
			return DATA_RECORD[0][236];
		}

		public String getNas100usd() {
			return DATA_RECORD[0][237];
		}

		public String getNas100usdbid() {
			return DATA_RECORD[0][238];
		}

		public String getNas100usdask() {
			return DATA_RECORD[0][239];
		}

		public String getNas100usd1() {
			return DATA_RECORD[0][240];
		}

		public String getNas100usd2() {
			return DATA_RECORD[0][241];
		}

		public String getNas100usdspread() {
			return DATA_RECORD[0][242];
		}

		public String getNas100usd3() {
			return DATA_RECORD[0][243];
		}

		public String getNas100usd4() {
			return DATA_RECORD[0][244];
		}

		public String getXcuusd() {
			return DATA_RECORD[0][245];
		}

		public String getXcuusdbid() {
			return DATA_RECORD[0][246];
		}

		public String getXcuusdask() {
			return DATA_RECORD[0][247];
		}

		public String getXcuusd1() {
			return DATA_RECORD[0][248];
		}

		public String getXcuusd2() {
			return DATA_RECORD[0][249];
		}

		public String getXcuusdspread() {
			return DATA_RECORD[0][250];
		}

		public String getXcuusd3() {
			return DATA_RECORD[0][251];
		}

		public String getXcuusd4() {
			return DATA_RECORD[0][252];
		}

		public String getGbpjpy() {
			return DATA_RECORD[0][253];
		}

		public String getGbpjpybid() {
			return DATA_RECORD[0][254];
		}

		public String getGbpjpyask() {
			return DATA_RECORD[0][255];
		}

		public String getGbpjpy1() {
			return DATA_RECORD[0][256];
		}

		public String getGbpjpy2() {
			return DATA_RECORD[0][257];
		}

		public String getGbpjpyspread() {
			return DATA_RECORD[0][258];
		}

		public String getGbpjpy3() {
			return DATA_RECORD[0][259];
		}

		public String getGbpjpy4() {
			return DATA_RECORD[0][260];
		}

		public String getAudjpy() {
			return DATA_RECORD[0][261];
		}

		public String getAudjpybid() {
			return DATA_RECORD[0][262];
		}

		public String getAudjpyask() {
			return DATA_RECORD[0][263];
		}

		public String getAudjpy1() {
			return DATA_RECORD[0][264];
		}

		public String getAudjpy2() {
			return DATA_RECORD[0][265];
		}

		public String getAudjpyspread() {
			return DATA_RECORD[0][266];
		}

		public String getAudjpy3() {
			return DATA_RECORD[0][267];
		}

		public String getAudjpy4() {
			return DATA_RECORD[0][268];
		}

		public String getAudcad() {
			return DATA_RECORD[0][269];
		}

		public String getAudcadbid() {
			return DATA_RECORD[0][270];
		}

		public String getAudcadask() {
			return DATA_RECORD[0][271];
		}

		public String getAudcad1() {
			return DATA_RECORD[0][272];
		}

		public String getAudcad2() {
			return DATA_RECORD[0][273];
		}

		public String getAudcadspread() {
			return DATA_RECORD[0][274];
		}

		public String getAudcad3() {
			return DATA_RECORD[0][275];
		}

		public String getAudcad4() {
			return DATA_RECORD[0][276];
		}

		public String getUsb05yusd() {
			return DATA_RECORD[0][277];
		}

		public String getUsb05yusdbid() {
			return DATA_RECORD[0][278];
		}

		public String getUsb05yusdask() {
			return DATA_RECORD[0][279];
		}

		public String getUsb05yusd1() {
			return DATA_RECORD[0][280];
		}

		public String getUsb05yusd2() {
			return DATA_RECORD[0][281];
		}

		public String getUsb05yusdspread() {
			return DATA_RECORD[0][282];
		}

		public String getUsb05yusd3() {
			return DATA_RECORD[0][283];
		}

		public String getUsb05yusd4() {
			return DATA_RECORD[0][284];
		}

		public String getUsb10yusd() {
			return DATA_RECORD[0][285];
		}

		public String getUsb10yusdbid() {
			return DATA_RECORD[0][286];
		}

		public String getUsb10yusdask() {
			return DATA_RECORD[0][287];
		}

		public String getUsb10yusd1() {
			return DATA_RECORD[0][288];
		}

		public String getUsb10yusd2() {
			return DATA_RECORD[0][289];
		}

		public String getUsb10yusdspread() {
			return DATA_RECORD[0][290];
		}

		public String getUsb10yusd3() {
			return DATA_RECORD[0][291];
		}

		public String getUsb10yusd4() {
			return DATA_RECORD[0][292];
		}

		public String getEu50eur() {
			return DATA_RECORD[0][293];
		}

		public String getEu50eurbid() {
			return DATA_RECORD[0][294];
		}

		public String getEu50eurask() {
			return DATA_RECORD[0][295];
		}

		public String getEu50eur1() {
			return DATA_RECORD[0][296];
		}

		public String getEu50eur2() {
			return DATA_RECORD[0][297];
		}

		public String getEu50eurspread() {
			return DATA_RECORD[0][298];
		}

		public String getEu50eur3() {
			return DATA_RECORD[0][299];
		}

		public String getEu50eur4() {
			return DATA_RECORD[0][300];
		}

		public String getDe10ybeur() {
			return DATA_RECORD[0][301];
		}

		public String getDe10ybeurbid() {
			return DATA_RECORD[0][302];
		}

		public String getDe10ybeurask() {
			return DATA_RECORD[0][303];
		}

		public String getDe10ybeur1() {
			return DATA_RECORD[0][304];
		}

		public String getDe10ybeur2() {
			return DATA_RECORD[0][305];
		}

		public String getDe10ybeurspread() {
			return DATA_RECORD[0][306];
		}

		public String getDe10ybeur3() {
			return DATA_RECORD[0][307];
		}

		public String getDe10ybeur4() {
			return DATA_RECORD[0][308];
		}

		public String getUk10ybgbp() {
			return DATA_RECORD[0][309];
		}

		public String getUk10ybgbpbid() {
			return DATA_RECORD[0][310];
		}

		public String getUk10ybgbpask() {
			return DATA_RECORD[0][311];
		}

		public String getUk10ybgbp1() {
			return DATA_RECORD[0][312];
		}

		public String getUk10ybgbp2() {
			return DATA_RECORD[0][313];
		}

		public String getUk10ybgbpspread() {
			return DATA_RECORD[0][314];
		}

		public String getUk10ybgbp3() {
			return DATA_RECORD[0][315];
		}

		public String getUk10ybgbp4() {
			return DATA_RECORD[0][316];
		}

		public String getNatgasusd() {
			return DATA_RECORD[0][317];
		}

		public String getNatgasusdbid() {
			return DATA_RECORD[0][318];
		}

		public String getNatgasusdask() {
			return DATA_RECORD[0][319];
		}

		public String getNatgasusd1() {
			return DATA_RECORD[0][320];
		}

		public String getNatgasusd2() {
			return DATA_RECORD[0][321];
		}

		public String getNatgasusdspread() {
			return DATA_RECORD[0][322];
		}

		public String getNatgasusd3() {
			return DATA_RECORD[0][323];
		}

		public String getNatgasusd4() {
			return DATA_RECORD[0][324];
		}

		public String getWheatusd() {
			return DATA_RECORD[0][325];
		}

		public String getWheatusdbid() {
			return DATA_RECORD[0][326];
		}

		public String getWheatusdask() {
			return DATA_RECORD[0][327];
		}

		public String getWheatusd1() {
			return DATA_RECORD[0][328];
		}

		public String getWheatusd2() {
			return DATA_RECORD[0][329];
		}

		public String getWheatusdspread() {
			return DATA_RECORD[0][330];
		}

		public String getWheatusd3() {
			return DATA_RECORD[0][331];
		}

		public String getWheatusd4() {
			return DATA_RECORD[0][332];
		}

		public String getSugarusd() {
			return DATA_RECORD[0][333];
		}

		public String getSugarusdbid() {
			return DATA_RECORD[0][334];
		}

		public String getSugarusdask() {
			return DATA_RECORD[0][335];
		}

		public String getSugarusd1() {
			return DATA_RECORD[0][336];
		}

		public String getSugarusd2() {
			return DATA_RECORD[0][337];
		}

		public String getSugarusdspread() {
			return DATA_RECORD[0][338];
		}

		public String getSugarusd3() {
			return DATA_RECORD[0][339];
		}

		public String getSugarusd4() {
			return DATA_RECORD[0][340];
		}

		public String getXptusd() {
			return DATA_RECORD[0][341];
		}

		public String getXptusdbid() {
			return DATA_RECORD[0][342];
		}

		public String getXptusdask() {
			return DATA_RECORD[0][343];
		}

		public String getXptusd1() {
			return DATA_RECORD[0][344];
		}

		public String getXptusd2() {
			return DATA_RECORD[0][345];
		}

		public String getXptusdspread() {
			return DATA_RECORD[0][346];
		}

		public String getXptusd3() {
			return DATA_RECORD[0][347];
		}

		public String getXptusd4() {
			return DATA_RECORD[0][348];
		}

		public String getXpdusd() {
			return DATA_RECORD[0][349];
		}

		public String getXpdusdbid() {
			return DATA_RECORD[0][350];
		}

		public String getXpdusdask() {
			return DATA_RECORD[0][351];
		}

		public String getXpdusd1() {
			return DATA_RECORD[0][352];
		}

		public String getXpdusd2() {
			return DATA_RECORD[0][353];
		}

		public String getXpdusdspread() {
			return DATA_RECORD[0][354];
		}

		public String getXpdusd3() {
			return DATA_RECORD[0][355];
		}

		public String getXpdusd4() {
			return DATA_RECORD[0][356];
		}

		public String getAu200aud() {
			return DATA_RECORD[0][357];
		}

		public String getAu200audbid() {
			return DATA_RECORD[0][358];
		}

		public String getAu200audask() {
			return DATA_RECORD[0][359];
		}

		public String getAu200aud1() {
			return DATA_RECORD[0][360];
		}

		public String getAu200aud2() {
			return DATA_RECORD[0][361];
		}

		public String getAu200audspread() {
			return DATA_RECORD[0][362];
		}

		public String getAu200aud3() {
			return DATA_RECORD[0][363];
		}

		public String getAu200aud4() {
			return DATA_RECORD[0][364];
		}

		public String getSg30sgd() {
			return DATA_RECORD[0][365];
		}

		public String getSg30sgdbid() {
			return DATA_RECORD[0][366];
		}

		public String getSg30sgdask() {
			return DATA_RECORD[0][367];
		}

		public String getSg30sgd1() {
			return DATA_RECORD[0][368];
		}

		public String getSg30sgd2() {
			return DATA_RECORD[0][369];
		}

		public String getSg30sgdspread() {
			return DATA_RECORD[0][370];
		}

		public String getSg30sgd3() {
			return DATA_RECORD[0][371];
		}

		public String getSg30sgd4() {
			return DATA_RECORD[0][372];
		}

		@Override
		public String getValue(String name) {
			Integer index = DATA_RECORD_MAPPER.get(name);
			if( index == null ){
				if( Logger.ERROR.isOn ) Logger.ERROR.printf("Can not find field [%s]", name);
			} else if( DATA_RECORD[0].length <= index ){
				if( Logger.ERROR.isOn ) Logger.ERROR.printf("Wrong index [%d] for field [%s]", index, name);
			} else {
				return DATA_RECORD[0][index];
			}
			return null;
		}
		
		@Override
		public String getRow() {
			return row;
		}

		@Override
		public String[] getHeader() {
			return DATA_RECORD_HEADER;
		}
	}

	public boolean populate(String[] data) {
		this.data[0] = data;
		return true;
	}

	public OandaParserNewRow() {
		headerTitles = new HashMap<String, String[][]>();
		headerTitles.put(DefaultRow.DEFAULT_HEADER_RECORD, DATA_RECORD);

		data = DATA_RECORD;

		headerClasses.put(DefaultRow.DEFAULT_HEADER_RECORD, Record.class);
	}

}
