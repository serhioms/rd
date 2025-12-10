package ca.mss.rd.loto.generator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ca.mss.rd.util.UtilMisc;

abstract public class LetoPersistence {

	abstract protected void populateHistory();
	abstract protected LetoDraw newdraw(String date, int[] draw);
	
	private List<LetoDraw> letoDrawHistory = null;

	final protected void add(String date, int... draw) {
		letoDrawHistory.add(newdraw(date, draw));
	}

	final protected void add(String date, String draw) {
		letoDrawHistory.add(newdraw(date, UtilMisc.toInt(draw.split(" "))));
	}

	final public List<LetoDraw> getHistory() {
		if( letoDrawHistory == null ){
			letoDrawHistory = new ArrayList<LetoDraw>();
			populateHistory();
		}
		return letoDrawHistory;
	}

	final public int[] getHistoryPool(int max) {
		if( letoDrawHistory == null ){
			letoDrawHistory = new ArrayList<LetoDraw>();
			populateHistory();
		}
		
		Set<Integer> set = new HashSet<Integer>();
		for(int k=0, maxk=letoDrawHistory.size(); k<maxk && set.size() < max; k++){
			int[] draw = letoDrawHistory.get(k).draw;
			for(int i=0; i<draw.length && set.size() < max; i++){
				set.add(draw[i]);
			}
		}
		
		int[] hp = new int[set.size()];
		int k=0;
		for(Integer i: set){
			hp[k++] = i.intValue();
		}
		
		return hp;
	}

}
