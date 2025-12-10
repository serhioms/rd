package ca.mss.rd.stat;

import java.util.Date;



public class MiniMaxLocal {

	public static final int MINIMAX_DEPTH = 3;

	private final double[] vals;
	private final Date[] times;
	private int idx;
	public int minimaxCounter;

	public boolean isMax, isMin;
	
	public MiniMaxLocal() {
		vals = new double[MINIMAX_DEPTH];
		times = new Date[MINIMAX_DEPTH];
		clear();
	}

	public void clear(){
		minimaxCounter = idx = 0;
		isMax = isMin = false;
	}

	public void addValue(double val){
		if( idx == MINIMAX_DEPTH ){
			// Poll list
			vals[0] = vals[1];
			vals[1] = vals[2];
			vals[2] = val;
			
			// calculate local extremums
			isMax = vals[0] < vals[1] && vals[1] > vals[2];
			isMin = vals[0] > vals[1] && vals[1] < vals[2];
		} else {
			vals[idx++] = val;
		}
		if( isMax || isMin ){
			minimaxCounter++;
		}
	}

	public double getExtremum() {
		return vals[1];
	}

	public Date getExtremumTime() {
		return times[1];
	}

	public void addValue(double val, Date time) {
		if( idx == MINIMAX_DEPTH ){
			// Poll list
			times[0] = times[1];
			times[1] = times[2];
			times[2] = time;
		} else {
			times[idx] = time;
		}
		addValue(val);
	}
	
}
