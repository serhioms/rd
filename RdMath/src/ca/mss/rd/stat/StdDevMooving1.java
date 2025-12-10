package ca.mss.rd.stat;


public class StdDevMooving1 {

	private AverageMooving1 ma;
	private AverageMooving1 ma2;
	
	public StdDevMooving1() {
		this(2);
	}

	public StdDevMooving1(int depth) {
		ma = new AverageMooving1(depth);
		ma2 = new AverageMooving1(depth);
	}

	public void clear(){
		ma.clear();
		ma2.clear();
	}
	
	public final void setDepth(int depth){
		ma.setDepth(depth);
		ma2.setDepth(depth);
	}
	
	public final int getDepth(){
		return ma.getDepth();
	}
	
	public final boolean isFull() {
		return ma.isFull();
	}

	public void addValue(double val){
		ma.addValue(val);
		ma2.addValue(val*val);
	}
	
	public double getStdDev(){
		double m = ma.getAverage();
		return Math.sqrt(ma2.getAverage()-m*m);
	}
}
