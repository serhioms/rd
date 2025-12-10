package ca.mss.rd.loto;

import ca.mss.rd.loto.canada.model.WinningPrize;
import ca.mss.rd.util.UtilProperty;


public class LotoConfig {

	final static public String module = LotoConfig.class.getSimpleName();
	final static public long serialVersionUID = module.hashCode();

	static public String ROW_DELIMITER = "\t";
	
	static public String LOTO_CANADA_WINNING_NUMBERS_DOWNLOAD_URL = "http://www.olg.ca/lotteries/viewPrizeShares.do";
	static public String LOTO_CANADA_WINNING_NUMBERS_FILE = "data/winning_numbers.txt";
	static public String LOTO_CANADA_WINNING_NUMBERS_DOWNLOAD_FILE = "download/winning_numbers.html";
	

	static public String LOTO_CANADA_DOWNLOAD_PRIZE_URL = "http://www.olg.ca/lotteries/viewPrizeShares.do";
	static public String LOTO_CANADA_PRIZE_DOWNLOAD_FILE_EXPR = "download/winning_prize=%s.html";
	static public String LOTO_CANADA_PRIZE_PARSED_FILE_EXPR = "data/winning_prize=%s.txt";
	
	
	static {
		UtilProperty.readConstants(WinningPrize.class);
	}

	final static public String getCanadaPrizeDownloadFile(String lotoName){
		return String.format("%s%s", LOTO_CANADA_PRIZE_DOWNLOAD_FILE_EXPR,lotoName);
	}

	final static public String getCanadaPrizeFile(String lotoName){
		return String.format("%s%s", LOTO_CANADA_PRIZE_PARSED_FILE_EXPR, lotoName);
	}
	
}
