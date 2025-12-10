package ca.mss.rd.stat;

public class BreakLineMM extends BreakLine {

	private boolean prevBrokenDn, prevBrokenUp;
	public boolean isMin, isMax;
	private double locmin, locmax;
	public double max, min;
	
	public BreakLineMM() {
		super();
	}

	public void clear(){
		super.clear();
		prevBrokenDn = prevBrokenUp = isMin = isMax = false;
		locmin = Double.MAX_VALUE;
		locmax = Double.MIN_VALUE;
		min = max = Double.POSITIVE_INFINITY;
	}

	public void addValue(double d, double limit){
		super.addValue(d, limit);
		
		if( max == Double.POSITIVE_INFINITY ){
			min = max = d;
		}
		
		isMax = isMin = false;
		
		locmin = Math.min(locmin, d);
		locmax = Math.max(locmax, d);
		
		if( isBroken() ){
			if( isGoingUp ){
				isMin = prevBrokenDn;
				prevBrokenUp = true;
				prevBrokenDn = false;
			} else if( isGoingDn ){
				isMax = prevBrokenUp;
				prevBrokenDn = true;
				prevBrokenUp = false;
			}
		} else if( prevBrokenUp && isGoingDn && (d < locmax) && (locmax - d) >= limit ){
			prevBrokenDn = super.isBroken = isMax = true;
			prevBrokenUp = false;
			super.v = locmax - limit;
		} else if( prevBrokenDn && isGoingUp && (d > locmin) && (d - locmin) >= limit ){
			prevBrokenUp = super.isBroken = isMin = true;
			prevBrokenDn = false;
			super.v = locmin + limit;
		}
		
		if( isMax ){
			max = locmax;
			locmax = Double.MIN_VALUE;
			locmin = d;
		} else if( isMin ){
			min = locmin;
			locmin = Double.MAX_VALUE;
			locmax = d;
		} else {
			if( Math.abs(locmin - min) >= limit ){
				min = Math.min(locmin, min);
			}
			if( Math.abs(locmax - max) >= limit ){
				max = Math.max(locmax, max);
			}
		}
	}

	public String getIdent(){
		return isMax?"Max":isMin?"Min":isGoingUp?"UP":isGoingDn?"DN":"N/A";
	}
}
