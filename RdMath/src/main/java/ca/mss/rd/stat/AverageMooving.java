package ca.mss.rd.stat;

import java.math.BigDecimal;

import ca.mss.rd.math.NewBigDecimal;


public class AverageMooving extends StatClosure {

	static public int DEFAULT_DEPTH = 2;

	protected MovingArray ma;
	protected BigDecimal avg;
	
	public AverageMooving() {
		this(DEFAULT_DEPTH);
	}

	public AverageMooving(int windowSize) {
		ma = new MovingArray(windowSize);
		avg = NewBigDecimal.getInstance(0.0D);
		clear();
	}

	final public boolean isFull() {
		return ma.isFull();
	}

	final public int getSize() {
		return ma.size;
	}

	public void clear(){
		ma.clear();
		avg = NewBigDecimal.getInstance(0.0D);
	}

	public void setWindowSize(final int windowSize){
		ma.setWindowSize(windowSize);
		clear();
	}

	public int getWindowSize(){
		return ma.getWindowSize();
	}

	public void addValue(double d){
		BigDecimal b = NewBigDecimal.getInstance(d);
		if( ma.size < ma.depth ){
			// avg = (avg*ma.sized + d)/(ma.sized+1.0);
			avg = avg.multiply(ma.sized).add(b).divide(ma.sized.add(NewBigDecimal.ONE), NewBigDecimal.MATH_CONTEXT);
		} else {
			// avg = avg + (d-ma.poll())/ma.sized;			
			avg = avg.add(b.subtract(ma.poll()).divide(ma.sized, NewBigDecimal.MATH_CONTEXT));
		}
		ma.addValue(d);
	}
	
	@Override
	public double getAverage(){
		return avg.doubleValue();
	}

	@Override
	public double getStDev() {
		throw new RuntimeException("Standard Deviation not exists for AverageSimple");
	}
}
