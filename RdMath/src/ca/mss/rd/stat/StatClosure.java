package ca.mss.rd.stat;

abstract public class StatClosure {

	abstract public double getStDev();
	abstract public double getAverage();
	
	public double getLeft(double sdw){
		return getLeft(getAverage(), getStDev(), sdw);
	}
	
	public double getLeft(double stdev, double sdw){
		return getLeft(getAverage(), stdev, sdw);
	}
	
	public double getLeft(double avg, double stdev, double sdw){
		return avg - sdw*stdev;
	}
	public double getRight(double sdw){
		return getRight(getAverage(), getStDev(), sdw);
	}
	
	public double getRight(double stdev, double sdw){
		return getRight(getAverage(), stdev, sdw);
	}
	
	public double getRight(double avg, double stdev, double sdw){
		return avg + sdw*stdev;
	}
	
	public boolean nearto(double d, double sdw){
		return (d >= getLeft(sdw)) && (d <= getRight(sdw));
	}
	
	public boolean nearto(double d, double stdev, double sdw){
		return (d >= getLeft(stdev, sdw)) && (d <= getRight(stdev, sdw));
	}
	
	public boolean nearto(double d, double avg, double stdev, double sdw){
		return (d >= getLeft(avg, stdev, sdw)) && (d <= getRight(avg, stdev, sdw));
	}


	public boolean below(double d, double stdev, double sdw){
		return (d <= getRight(stdev, sdw));
	}
	
}
