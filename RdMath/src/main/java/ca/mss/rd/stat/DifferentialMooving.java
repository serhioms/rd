package ca.mss.rd.stat;

public class DifferentialMooving extends AverageMooving {

	private double differential = 0.0D, average = 0.0D;
	
	public DifferentialMooving() {
		super();
	}

	public DifferentialMooving(int windowSize) {
		super(windowSize);
	}

	public void clear(){
		super.clear();
		average = differential = 0.0D;
	}
	
	public void addValue(double d){
		if( super.getSize() > 0 ){
			average = super.getAverage();
			differential = d - average;
		}
		super.addValue(d);
	}
	
	public double getDifferential(double multiplicator){
		return differential*multiplicator;
	}
	
	public double getAverage(){
		return average;
	}

}
