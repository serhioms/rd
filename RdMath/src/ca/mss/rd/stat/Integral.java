package ca.mss.rd.stat;

public class Integral extends ZeroCrosser {

	private double sum = 0.0D;
	
	public Integral() {
	}

	public void clear(){
		super.clear();
		sum = 0.0D;
	}
	
	public void addValue(double d){
		super.addValue(sum += d);
	}

	public double getIntegral(double factor){
		return sum*factor;
	}
}
