package ca.mss.rd.stat;

public class ZeroCrosser {

	private double val = 0.0D, old = 0.0D;
	
	public ZeroCrosser() {
	}

	public void clear(){
		val = old = 0.0D;
	}
	
	public void addValue(double d){
		if( val != 0.0D ){
			old = val;
		}
		val = d;
	}

	public boolean isCrossedZero(){
		return old*val < 0.0D;
	}
}
