package ca.mss.rd.stat;

import java.math.BigDecimal;

import ca.mss.rd.math.NewBigDecimal;



public class StdDevMoovingPopulation extends AverageMooving {

	static public int DEFAULT_DEPTH = 2;

	protected BigDecimal avg2, stdev;
	
	public StdDevMoovingPopulation() {
		this(DEFAULT_DEPTH);
	}

	public StdDevMoovingPopulation(int depth) {
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
		BigDecimal b = NewBigDecimal.getInstance(d);
		
		if( ma.size < ma.depth ){
			avg2 = (avg2.multiply(ma.sized).add(b.multiply(b))).divide(ma.sized.add(NewBigDecimal.ONE), NewBigDecimal.MATH_CONTEXT);
		} else {
			final BigDecimal p = ma.poll();
			avg2 = avg2.add(b.multiply(b).subtract(p.multiply(p)).divide(ma.sized, NewBigDecimal.MATH_CONTEXT));
		}
		
		super.addValue(d);

		stdev = NewBigDecimal.getInstance(Math.sqrt(avg2.subtract(avg.multiply(avg)).abs().doubleValue()));
	}
	
	@Override
	final public double getStDev(){
		return stdev.doubleValue();
	}

}
