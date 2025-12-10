package ca.mss.rd.math.cube.impl;

import ca.mss.rd.math.cube.NCube;


public class NCubeSquare<Point extends NCube<Point,Region>.NPoint,Region extends NCube<Point,Region>.NRegion> {

	final public Point[] getPoints(Point left, Point right) {
		return null;
	}

	final public Region getRegion(int[] dim) {
		return null;
	}

	final public void populate(Region reg, int[] narr) {
		for(int i=0,n=reg.left.dim.length; i<n; i++){
			reg.left.dim[i] = narr[i];
		}
		for(int i=0,n=reg.right.dim.length; i<n; i++){
			reg.right.dim[i] = narr[n+i];
		}
	}
}
