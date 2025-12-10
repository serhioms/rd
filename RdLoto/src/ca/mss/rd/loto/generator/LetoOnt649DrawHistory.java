package ca.mss.rd.loto.generator;

import java.util.HashMap;
import java.util.Map;


public class LetoOnt649DrawHistory extends LetoDrawHistory {

	private static final String ONTARIO_6_49 = "Ontario 6-49";
	private static final double MIN_PRIZE_MATCH = 2.0;

	@Override
	public String getDrawTitle() {
		return ONTARIO_6_49;
	}

	@Override
	protected double getMinPrizeMatch() {
		return MIN_PRIZE_MATCH;
	}

	@Override
	protected Map<String,String> getFileMap(){
		Map<String,String> map = new HashMap<String,String>();
		map.put("March 16, 2016", "13x2=5x3=260=22-152-101");
		return map;
	}

	public static void main(String[] args) {
		new LetoOnt649DrawHistory().check(false);
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@Override
	protected void populateHistory() {
		add("March 16, 2016", 02,8,23,30,41,48,29);
		add("              ", 03,9,10,12,14,24,32);
		add("              ", 10,19,27,28,31,36,41);
		add("              ", 9,14,18,33,42,43,46);
		add("              ", 07,12,14,26,42,44,21);
		add("              ", 01,8,22,24,39,44,35);
		add("              ", 01,14,20,25,33,38,8);
		add("              ", 06,10,11,38,47,48,49);
		add("              ", 10,13,22,28,33,46,37);
		add("              ", 03,10,13,19,40,44,37);
		add("              ", 03,21,28,29,35,40,49);
		add("              ", 01,9,27,30,31,35,2);
		add("              ", 03,05,14,35,37,42,29);
		add("              ", 03,11,13,14,30,32,49);
		add("              ", 02,11,16,22,37,49,20);
		add("              ", 04,26,28,29,40,48,13);
		add("              ", 05,20,24,31,40,43,42);
		add("              ", 07,12,21,28,31,44,8);
		add("              ", 01,9,14,20,29,43,3);
		add("              ", 01,05,06,12,25,49,8);
		add("              ", 07,8,10,13,40,46,4);
		add("              ", 02,05,16,29,34,41,3);
	}
}
