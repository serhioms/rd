package ca.mss.rd.stat;

import java.math.BigDecimal;

import ca.mss.rd.math.NewBigDecimal;



public class StdDevMoovingSample extends AverageMooving {

	static public int DEFAULT_DEPTH = 2;

	protected BigDecimal avg2, stdev;
	
	public StdDevMoovingSample() {
		this(DEFAULT_DEPTH);
	}

	public StdDevMoovingSample(int depth) {
		super(depth);

	}

	public void clear(){
		super.clear();
		stdev = NewBigDecimal.getInstance(0.0D);
		avg2 = NewBigDecimal.getInstance(0.0D);
	}

	public void setWindowSize(final int windowSize){
		super.setWindowSize(windowSize);
		clear();
	}

	public void addValue(final double d){
		super.addValue(d);

		if( ma.size == 1 || ma.depth == 1 ){
			stdev = NewBigDecimal.getInstance(0.0D);
		} else {
			BigDecimal sum = NewBigDecimal.getInstance(0.0D);
			for(int i=0,max=ma.size; i<max; i++){
				final BigDecimal da = ma.val[i].subtract(avg);
				sum = sum.add(da.multiply(da));
			}
	
			stdev = NewBigDecimal.getInstance(Math.sqrt(sum.divide(ma.sized.subtract(NewBigDecimal.ONE), NewBigDecimal.MATH_CONTEXT).doubleValue()));
		}
	}
	
	@Override
	public final double getStDev(){
		return stdev.doubleValue();
	}
}
