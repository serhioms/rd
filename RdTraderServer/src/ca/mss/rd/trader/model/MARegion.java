package ca.mss.rd.trader.model;

public class MARegion {
	
	public int breakSize;
	public MAPoint left, right, step, center, size, extr;

	public MARegion(MAPoint left, MAPoint right, int breakSize) {
		this.breakSize = breakSize;
		this.extr = new MAPoint(0.0);
		this.left = left; 
		this.right = right;
		this.size = new MAPoint(breakSize, breakSize);
		this.step = new MAPoint((right.slow-left.slow)/breakSize, (right.fast-left.fast)/breakSize);
		this.center = new MAPoint(left.slow+this.step.slow/2, left.fast+this.step.fast/2);
	}
	public MARegion(MAPoint left, MAPoint right) {
		this.breakSize = 1;
		this.extr = new MAPoint(0.0);
		this.left = left; 
		this.right = right;
		this.size = new MAPoint(right.slow-left.slow, right.fast-left.fast);
		this.step = new MAPoint(1,1);
		this.center = new MAPoint(left.slow+this.step.slow/2, left.fast+this.step.fast/2);
	}
	public MARegion(double d) {
		this.breakSize = 0;
		this.extr = new MAPoint(0.0);
		this.left = new MAPoint(0.0); 
		this.right = new MAPoint(0.0);
		this.size = new MAPoint(0.0);
		this.step = new MAPoint(0.0);
		this.center = new MAPoint(0.0);
	}
	public boolean checkMax(MAPoint extr) {
		if( extr.profit > this.extr.profit ){
			set(extr);
			return true;
		}
		return false;
	}
	private void set(MAPoint extr) {
		this.extr.profit = extr.profit;
		this.extr.slow = extr.slow;
		this.extr.fast = extr.fast;
	}
	public int slowEnd() {
		return left.slow + step.slow*breakSize;
	}
	public int fastEnd() {
		return left.fast + step.fast*breakSize;
	}
}

