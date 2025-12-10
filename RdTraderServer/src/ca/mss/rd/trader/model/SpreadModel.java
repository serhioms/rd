package ca.mss.rd.trader.model;


public class SpreadModel extends FilteredMovingAvgModel {

	static public int MOOVING_AVERAGE_DEPTH_MVNG = 5*60/3; 
	static public int MOOVING_AVERAGE_DEPTH_FILTR = 15*60/3; 

	static public double CHANEL_UP = 10.0; 
	static public double CHANEL_DN = 3.0; 
	
	static public double PIPE = 4.0; 
	
	public SpreadModel() {
		super(MOOVING_AVERAGE_DEPTH_MVNG, MOOVING_AVERAGE_DEPTH_FILTR, CHANEL_UP, CHANEL_DN, PIPE); 
	}

	final public void calc(double ask, double bid) {
		super.calc(ask - bid);
	}
	
}
