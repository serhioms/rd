package ca.mss.rd.loto.generator;

import java.util.HashMap;
import java.util.Map;

public class LetoMax7DrawHistory extends LetoDrawHistory {

	private static final String TITLE = "Max7";
	private static final double MIN_PRIZE_MATCH = 3.0;

	@Override
	public String getDrawTitle() {
		return TITLE;
	}

	@Override
	protected double getMinPrizeMatch() {
		return MIN_PRIZE_MATCH;
	}

	@Override
	protected Map<String,String> getFileMap(){
		Map<String,String> map = new HashMap<String,String>();
		map.put("March 18, 2016", "1=5x2=23");
		map.put("March 11, 2016", "3=5x2=80");
		return map;
	}
	
	public static void main(String[] args) {
		new LetoMax7DrawHistory().check(false);
	}

	
	@Override
	protected void populateHistory() {
		add("March 25, 2016",	"01 06 15 32 42 45 46 44");
		add("March 18, 2016",	"6 8 15 24 26 39 44 40");
		add("March 11, 2016",	"9 17 27 30 31 34 42 21");
		add("March 7, 2016",	"04 14 24 41 42 44 48 26");
		add("Feb 12, 2016",	"06 09 22 23 36 38 47 12");
		add("Feb 4, 2016",	"02 11 20 22 33 40 43 4");
		add("Feb 12, 2016",	"19 20 26 31 34 39 48 18");
		add("Feb 5, 2016",	"06 07 08 09 17 23 30 18");
		add("Jan 29, 2016",	"05 11 14 17 28 41 47 46");
		add("Jan 22, 2016",	"20 21 22 34 42 43 45 1");
		add("Jan 15, 2016",	"07 26 27 31 32 33 42 45");
		add("Jan 8, 2016",	"13 15 21 24 25 36 48 10");
		add("Jan 1, 2016",	"06 10 13 14 25 41 46 8");
//		add(", 2016", "");
	}
}
