package ca.mss.rd.stat;


public class MiniMax {

	public double min, max;
	public long tmin, tmax;
	
	public MiniMax() {
		clear();
	}

	public String getMin(String fmt) {
		return min==Double.MAX_VALUE?"":String.format(fmt, min);
	}

	public String getMax(String fmt) {
		return max==Double.MIN_VALUE?"":String.format(fmt, max);
	}

	public void clear(){
		min = Double.MAX_VALUE;
		max = Double.MIN_VALUE;
		tmin = tmax = 0L;
	}

	public void addValue(double val){
		if( val > max ){
			max = val;
		}
		
		if( val < min ){
			min = val;
		}
	}
	
	public void addValue(double val, long time){
		if( val > max ){
			max = val;
			tmax = time;
		}
		
		if( val < min ){
			min = val;
			tmin = time;
		}
	}
	
}
