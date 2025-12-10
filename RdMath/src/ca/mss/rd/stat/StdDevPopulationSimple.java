package ca.mss.rd.stat;

public class StdDevPopulationSimple extends AverageSimple {

	final protected AverageSimple avg2;
	protected double stdev, stdevmin;
	
	public StdDevPopulationSimple() {
		avg2 = new AverageSimple();
	}

	public void clear(){
		super.clear();
		stdev = 0.0D;
		avg2.clear();
	}

	public void addValue(final double d){
		super.addValue(d);
		
		avg2.addValue(d*d);
		stdev = super.getAverage();
		stdev = Math.sqrt(Math.abs(avg2.getAverage()-stdev*stdev));
	}

	@Override
	final public double getStDev() {
		return stdev;
	}

	final public double getStdDev(double k) {
		return stdev*k;
	}

}
