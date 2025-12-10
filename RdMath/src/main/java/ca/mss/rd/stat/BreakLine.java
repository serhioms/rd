package ca.mss.rd.stat;

public class BreakLine {

	public double pipe, delta, absdelta;

	protected double v, limit;
	protected boolean isBroken, isGoingUp, isGoingDn;
	
	public BreakLine() {
		super();
		clear();
	}

	public void clear(){
		isGoingUp = isGoingDn = isBroken = false;
		v = Double.POSITIVE_INFINITY;
		delta = 0.0;
	}
	
	public void addValue(double d, double limit){
		this.limit = limit;

		if( v == Double.POSITIVE_INFINITY ){
			v = d;
		}
		
		isGoingUp = d > v; 
		isGoingDn = d < v;

		delta = d - v;
		
		if( isGoingUp ){
			isBroken = (absdelta=(d - v)) >= limit;
			if( isBroken )
				v = d;
		} else if( isGoingDn ){
			isBroken = (absdelta=(v - d)) >= limit;
			if( isBroken )
				v = d;
		} else {
			absdelta = 0.0;
			isBroken = false;
		}
	}

	public final double getBLValue(){
		return v;
	}

	public boolean isBroken() {
		return isBroken;
	}
	
	public boolean isGoingUp() {
		return isGoingUp;
	}
	
	public boolean isGoingDn() {
		return isGoingDn;
	}
	
	public final double getTop(double d){
		double level=v;
		for(; d>=level; level += limit);
		return (level-d)*100.0/limit;
	}

	public final double getBottom(double d){
		double level=v;
		for(; d<=level; level -= limit);
		return (d-level)*100.0/limit;
	}
}
