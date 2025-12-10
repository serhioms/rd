package ca.mss.rd.stat;


public class StdDevMooving2 extends AverageMooving1 {

	protected double sum2;

	public StdDevMooving2() {
		super();
		clear();
	}

	public StdDevMooving2(int depth) {
		super(depth);
		clear();
	}

	public void clear(){
		super.clear();
		sum2 = 0.0D;
	}

	public final void setDepth(int depth){
		super.depth = depth;
		for(int i=values.size(); i>depth; i--){
			double v = values.poll();
			sum -= v;
			sum2 -= v*v;
		}
	}

	public void addValue(double val){
		if( values.size() < depth ){
			sum += val;
			sum2 += val*val;
		} else {
			double v = values.poll();
			sum += val - v;
			sum2 += val*val - v*v;
		}
		values.add(val);
	}
	
	public final double getAverage2(){
		return sum2/values.size();
	}
	
	public double getStdDev(){
		double avg=getAverage(), avg2=getAverage2();
		return Math.sqrt(Math.abs(avg2-avg*avg));
	}
	
}
