package ca.mss.rd.stat;

public class Delta {

	private double prev, delta;
	
	public Delta() {
		clear();
	}

	public void clear(){
		delta = 0;
		prev = Double.POSITIVE_INFINITY;
	}
	
	public void addValue(double d){
		if( prev == Double.POSITIVE_INFINITY ){
			prev = d;
		}
		
		delta = Math.abs(d - prev);
		
		prev = d;
	}

	public final double getValue(){
		return delta;
	}

}