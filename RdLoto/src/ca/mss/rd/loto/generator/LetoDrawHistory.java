package ca.mss.rd.loto.generator;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import ca.mss.rd.util.Logger;
import ca.mss.rd.util.UtilMisc;
import ca.mss.rd.util.UtilSort;
import ca.mss.rd.util.io.UtilTextFile;

abstract public class LetoDrawHistory extends LetoPersistence {

	abstract protected double getMinPrizeMatch();
	abstract protected String getDrawTitle();
	abstract protected Map<String,String> getFileMap();

	final public LetoDraw getTop() {
		return getHistory().get(0);
	}

	@Override
	protected LetoDraw newdraw(String date, int[] draw) {
		return new LetoDraw(this, date, draw);
	}
	
	public void check(boolean showAll) {
		Map<Double, Integer> ttlTMatch = new HashMap<Double, Integer>();
		Map<Double, Integer> ttlPMatch = new HashMap<Double, Integer>();
		
		LetoDraw top = getTop();
		
		Logger.LETO_DRAW.printf("%s draw on %s {%s}", getDrawTitle(), top.date, toString(top.draw));

		// pools
		Logger.LETO_DRAW.printf("Pools:");

		int[][] pool = top.getPool();
		for (int k=0; k<pool.length; k++) {
			Map<Double, Integer> poolMatch = matchPool(ttlPMatch, top.draw, pool[k]);
			Logger.LETO_DRAW.printf("%3d) {%s} = %s", k+1, toString(pool[k]), poolMatch.toString());
		}
		Logger.LETO_DRAW.printf("Totals: %s", UtilSort.sortNumber(ttlPMatch).toString());
		
		// tickets
		Logger.LETO_DRAW.printf("Tickets:");

		int[][] ticket = top.getTicket();
		for (int k=0; k<ticket.length; k++) {
			Map<Double, Integer> ticketMatch = matchTicket(ttlTMatch, top.draw, ticket[k]);
			if( ticketMatch.size() > 0 )
				Logger.LETO_DRAW.printf("%3d) {%s} = %s", k+1, toString(ticket[k]), ticketMatch.toString());
			else if( showAll )
				Logger.LETO_DRAW.printf("%3d) {%s}", k+1, toString(ticket[k]));
		}
		
		Logger.LETO_DRAW.printf("Totals: {%s} = %s", UtilMisc.toSortedString(top.draw, "%-2d"), UtilSort.sortNumber(ttlTMatch).toString());
		Logger.LETO_DRAW.printf("Pool totals: %s", UtilSort.sortNumber(ttlPMatch).toString());
	}
	
	public void matchHistory(Map<Double, Integer> matcher, int[] ticket) {
		for(LetoDraw ld: getHistory()){
			matchTicket(matcher, ld.draw, ticket);
		}
	}
	
	public Map<Double, Integer> matchPool(Map<Double, Integer> totalMatch, int[] draw, int[] pool) {
		return matcher(totalMatch, draw, UtilMisc.toSet(pool), 0.0);
	}

	public Map<Double, Integer> matchTicket(Map<Double, Integer> totalMatch, int[] draw, int[] ticket) {
		return matcher(totalMatch, draw, UtilMisc.toSet(ticket), getMinPrizeMatch());
	}

	private Map<Double, Integer> matcher(Map<Double, Integer> totalMatch, int[] draw, Set<Integer> ticket, double min) {
		Map<Double, Integer> match = new HashMap<Double, Integer>();

		double sum = ticket.contains(draw[draw.length-1])?0.5:0;
		
		for(int i=0,maxi=draw.length-1; i<maxi; i++){
			if( ticket.contains(draw[i]) ){
				sum += 1.0;
			}
		}
		if( sum >= min ){
			if( !totalMatch.containsKey(sum) ){
				totalMatch.put(sum, 1);
			} else {
				totalMatch.put(sum, totalMatch.get(sum)+1);
			}
			if( !match.containsKey(sum) ){
				match.put(sum, 1);
			} else {
				match.put(sum, match.get(sum)+1);
			}
		}
		
		return match;
	}
	
	public int optimalMatch(Map<Double, Integer> match) {
		int result = 0; 
		Integer[] opt = new Integer[]{match.get(4.0), match.get(4.5)};
		for(int i=0; i<opt.length; i++){
			if( opt[i] != null ){
				result += opt[i].intValue(); 
			}
		}
		return result;
	}


	// Persistence
	public int[][] readTicket(String drawTitle, String date) {
		return UtilTextFile.readTableInt(String.format("data/generate/%s/%s/ticket=%s.txt", drawTitle, date, getFileMap().get(date)));
	}

	public int[][] readPool(String drawTitle, String date) {
		return UtilTextFile.readTableInt(String.format("data/generate/%s/%s/pool=%s.txt", drawTitle, date, getFileMap().get(date)));
	}

	
	// Just for convenience
	final protected String toString(int[] a){
		return UtilMisc.toString(a, "%-2d");
	}
	
}
