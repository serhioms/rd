package ca.mss.rd.stat;

public class LogarifmSimple extends StdDevPopulationSimple {

	private double prev, log;
	
	public LogarifmSimple() {
		super();
	}

	public void clear(){
		super.clear();
		prev = Double.POSITIVE_INFINITY;
		log = Double.POSITIVE_INFINITY;
	}
	
	public void addValue(double d){
		
		if( prev == Double.POSITIVE_INFINITY ){
			prev = d;
		}
		
		log = Math.log(d/prev);
		
		prev = d;

		super.addValue(log);
	}
	
	public double getLog(){
		return log;
	}
	
	public double getAverageExp(){
		return Math.exp(getAverage())*prev;
	}
	
	public double getStDevExp(){
		return Math.expm1(getStDev())*prev;
	}
}
