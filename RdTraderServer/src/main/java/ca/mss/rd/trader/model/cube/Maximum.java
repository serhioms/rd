package ca.mss.rd.trader.model.cube;

import ca.mss.rd.trader.model.cube.RevAvgCubeFinder.MACube2.MAPoint;
import ca.mss.rd.util.model.SortableDef;

public class Maximum extends SortableDef<Number> {
	
	public double profit;
	public MAPoint point, left, right, step;
	public boolean isPrinted;

	public Maximum() {
		clear();
	}

	@Override
	public Number sortby() {
		return profit;
	}
	
	public void clear() {
		profit = Double.NEGATIVE_INFINITY;
	}

	public Maximum clone() {
		Maximum clone = new Maximum();
		clone.set(this);
		return clone;
	}

	public void check(double prft, MAPoint pnt) {
		if( prft > profit ){
			profit = prft;
			if( point == null ){
				point = pnt.clone();
			} else if( !point.isEquals(pnt) ){
				point.set(pnt);
				left = pnt.outRegion.left;
				right = pnt.outRegion.right;
				step = pnt.outRegion.step;
			}
		}
	}

	public boolean isEquals(Maximum max) {
		return max.profit == profit && max.point.isEquals(point);
	}

	public void set(Maximum max) {
		profit = max.profit;
		if( point == null ){
			point = max.point.clone();
		} else {
			point.set(max.point.dim);
		}
		left = max.left;
		right = max.right;
		step = max.step;
		isPrinted=true;
	}

	public void setProfit(double profit) {
		this.profit = profit;
		isPrinted=true;
	}
}
