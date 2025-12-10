package ca.mss.rd.stat;


public class AverageSimple extends StatClosure {

	protected double avg;
	protected int size;
	
	public AverageSimple() {
	}

	public void clear(){
		avg = 0.0D;
		size = 0;
	}

	public void addValue(final double d){
		avg = (avg*size + d)/(size+1.0);
		size ++;
	}
	
	@Override
	final public double getAverage(){
		return avg;
	}
	
	public final int getSize(){
		return size;
	}

	@Override
	public double getStDev() {
		throw new RuntimeException("Standard Deviation not exists for AverageSimple");
	}
	
	
}
