package ca.mss.rd.stat;

public class LogarifmMovingPopulation extends StdDevMoovingPopulation {

	private double prev, log;
	
	public LogarifmMovingPopulation() {
		super();
	}

	public LogarifmMovingPopulation(int depth) {
		super(depth);
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
}
