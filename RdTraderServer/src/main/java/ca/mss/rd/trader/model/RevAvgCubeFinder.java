package ca.mss.rd.trader.model;

import ca.mss.rd.math.cube.NCube;
import ca.mss.rd.math.cube.impl.NCubeIterator;
import ca.mss.rd.util.Logger;
import ca.mss.rd.util.UtilRand;



public class RevAvgCubeFinder {

	final public MACube2 cube;
	private int npp;
	
	public RevAvgCubeFinder(CubeSpace size, int breaks) {
		cube = new MACube2();
		cube.setBreaks(breaks);
		cube.cubeRegion.left.set(size.left);
		cube.cubeRegion.right.set(size.right);
	}

	public RevAvgCubeFinder(CubeSpace size) {
		this(size, 0);
	}

	static public class CubeSpace {
		final int[] left = new int[2];
		final int[] right = new int[2];

		// Region
		public CubeSpace(int slowLeft, int slowRight, int fastLeft, int fastRight) {
			super();
			left[0] = slowLeft;
			left[1] = fastLeft;
			right[0] = slowRight;
			right[1] = fastRight;
		}

		// Point
		public CubeSpace(int slow, int fast) {
			super();
			left[0] = slow;
			left[1] = fast;
			right[0] = slow;
			right[1] = fast;
		}
	}
	
	static public class MACube2 extends NCube<MACube2.MAPoint, MACube2.MARegion> {
		
		public class MAPoint extends NCube<MAPoint,MARegion>.NPoint {
			
			public MAPoint() {
				super();
			}

			public final int getSlow(){
				return dim[0];
			}
			
			public final int getFast(){
				return dim[1];
			}
		}

		public class MARegion extends NCube<MAPoint,MARegion>.NRegion {
		}

		@Override
		public MAPoint newPoint() {
			return new MAPoint();
		}
		
		@Override
		public MARegion newRegion() {
			return new MARegion();
		}

		public MACube2() {
			super(2);
		}

	}

	public void clean() {
		npp = 0;
	}

	public int getSurfaceSize() {
		return 0;
	}

	public void process(double mid, RevAvgModel ramodel, int level) {
		NCubeIterator<MACube2.MAPoint, MACube2.MARegion> iter = cube.cubeRegion.iterator();

		int iterLength = iter.getLength();
		
		if( Logger.REVAVG_VERBOSE.isOn && iterLength > 1){
			MACube2.MAPoint step = iter.getStep(), size = iter.getSize(), left = iter.getLeft(), right = iter.getRight();
			Logger.REVAVG_VERBOSE.printf("%6d) Cube %dx%d/%d (%d-%d,%d-%d)", ++npp, step.getSlow(), step.getFast(), size.getSlow()*size.getFast(), left.getSlow(), right.getSlow(), left.getFast(), right.getFast());
		}
		
		for(int cnt=1; iter.hasNext(); cnt++){

			MACube2.MAPoint pnt = iter.next();

			double profit = UtilRand.getRandDbl(10.0*level)-.111;
			
			iter.addSurf(profit);

			if( Logger.REVAVG_VERBOSE.isOn )
				if( iterLength == 1)
					Logger.REVAVG_VERBOSE.printf("%6d) (%5d, %-5d) = %5.2f", ++npp, pnt.getSlow(), pnt.getFast(), profit);
				else
					Logger.REVAVG_VERBOSE.printf("%6s) (%5d, %-5d)[%-3d] = %5.2f", String.format("%d.%d", npp, cnt), pnt.getSlow(), pnt.getFast(), iter.getIndex(), profit);
		}
	}

	public String getSurfaceTable() {
		return "N/I";
	}

	public boolean isNewExtremum() {
		return false;
	}

	public double getExtrProfit() {
		return 0.0;
	}

	public String getExtrCoordinates() {
		return "SxF";
	}

	public String getExtrRegion() {
		return "(slow(L-R/Step):fast(L-R/Step))";
	}

	
	
}
