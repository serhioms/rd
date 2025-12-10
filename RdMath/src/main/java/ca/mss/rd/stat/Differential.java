package ca.mss.rd.stat;

public class Differential {

	private double prev = Double.NaN, differencial;
	
	public Differential() {
	}

	public void clear(){
		prev = Double.NaN;
	}
	
	public void addValue(double d){
		if( prev == Double.NaN ){
			prev = d;
		}
		differencial = d - prev;
		prev = d;
	}

	public double getDifferential(double factor){
		return differencial*factor;
	}
}
