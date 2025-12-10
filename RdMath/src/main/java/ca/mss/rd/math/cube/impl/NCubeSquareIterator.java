package ca.mss.rd.math.cube.impl;

import ca.mss.rd.math.cube.NCube;

public class NCubeSquareIterator<Point extends NCube<Point,Region>.NPoint,Region extends NCube<Point,Region>.NRegion> implements NCubeIterator<Point, Region> {

	final private Point left, right, step, size;
	final private Point point;
	
	private boolean hasNext;
	private int count, index;
	private boolean isBreakable;
	
	public double[] surf;

	
	@Override
	public void remove() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public double[] getSurf() {
		return surf;
	}

	@Override
	public Point getLeft() {
		return left;
	}

	@Override
	public int getLength() {
		return surf.length;
	}

	@Override
	public Point getRight() {
		return right;
	}

	@Override
	public Point getStep() {
		return step;
	}

	@Override
	public Point getSize() {
		return size;
	}

	@Override
	public boolean isBreakable() {
		return isBreakable;
	}

	@Override
	public void addSurf(double d) {
		surf[index] += d;
	}

	@Override
	public int getIndex() {
		return index;
	}

	@Override
	public double getSurfValue() {
		return surf[index];
	}

	@Override
	public void setSurfValue(double d) {
		surf[index] = d;
	}

	@Override
	public boolean hasNext() {
		return hasNext;
	}

	@Override
	public Point next() {
		if( count++ == 0 ){
			point.set(left);
			if( surf.length == 1 ){
				hasNext = false;
			}
		} else {
			if( !(hasNext = count < surf.length) ){
				count = 0;
			}
			for(int i=0; i<point.dim.length; i++) {
				point.dim[i] += step.dim[i];
				if( point.dim[i] < right.dim[i] ){
					if( (point.dim[i]-left.dim[i]) >= size.dim[i]*step.dim[i] && hasNext ){
						point.dim[i] = left.dim[i];
					} else {
						break;
					}
				} else {
					point.dim[i] = left.dim[i];
				}
			}
		}
		index = calcIndex(point);
		return point;
	}

	public int calcIndex(Point pnt){
		int idx = 0;
		if( surf.length > 1 ){
			idx = (pnt.dim[0]-left.dim[0])/step.dim[0];
			for(int i=1; i<pnt.dim.length; i++){
				idx = (pnt.dim[i]-left.dim[i])/step.dim[i]+size.dim[i]*idx;
			}
		}
		return idx;
	}
	
	
	public NCubeSquareIterator(Point left, Point right, Point step, Point size, Point point, int breaks) {
		this.left = left;
		this.right = right;
		this.step = step;
		this.size = size;
		this.point = point;
		if( breaks == 0 ){
			this.isBreakable = false;
			this.surf = new double[1];
		} else {
			this.isBreakable = true;
			int lsize = 1;
			for(int i=0; i<step.dim.length; i++){
				step.dim[i] = (right.dim[i] - left.dim[i] + breaks/2)/breaks;
				size.dim[i] = breaks;
				isBreakable &= step.dim[i] > 1;
				lsize *= size.dim[i];
			}
			if( !isBreakable ){
				lsize = 1;
				for(int i=0; i<step.dim.length; i++ ){
					step.dim[i] = 1;
					size.dim[i] = right.dim[i] - left.dim[i];
					lsize *= size.dim[i];
				}
			}
			this.surf = new double[lsize];
		}
		clear();
	}

	public void clear() {
		this.hasNext = true;
		this.count = 0;
	}
}
