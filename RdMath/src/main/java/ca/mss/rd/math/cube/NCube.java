package ca.mss.rd.math.cube;

import ca.mss.rd.math.cube.impl.NCubeIterator;
import ca.mss.rd.math.cube.impl.NCubeSquare;
import ca.mss.rd.math.cube.impl.NCubeSquareIterator;

abstract public class NCube<Point extends NCube<Point,Region>.NPoint, Region extends NCube<Point,Region>.NRegion> {

	private final NCubeSquare<Point,Region> impl = new NCubeSquare<Point,Region>();
	
	public final int N;
	public final Region cubeRegion;
	public int breaks;
	
	abstract public Region newRegion();
	abstract public Point newPoint();
	
	public class NPoint {
		
		public final int[] dim;
		private Region subRegion;
		public NRegion outRegion;

		public NPoint() {
			dim = new int[N];
		}

		public boolean isEquals(NPoint point) {
			if( point == null ){
				return false;
			} else {
				for(int i=0; i<dim.length; i++){
					if( dim[i] != point.dim[i] )
						return false;
				}
			}
			return true;
		}

		
		public Region getSubRegion(Point step){
			if( subRegion == null ){
				subRegion = newRegion();
				for(int i=0; i< step.dim.length; i++){
					subRegion.left.dim[i] = dim[i] + Math.max(1, step.dim[i]/breaks);
					subRegion.right.dim[i] = dim[i] + step.dim[i];
				}
			}
			return subRegion;
		}

		public final void set(Point point){
			for(int i=0; i<dim.length; i++){
				dim[i] = point.dim[i];
			}
		}

		public Point clone() {
			Point dupl = newPoint();
			for(int i=0; i<dim.length; i++){
				dupl.dim[i] = dim[i];
			}
			return dupl;
		}

		public final void set(int[] dim){
			for(int i=0; i<dim.length; i++){
				this.dim[i] = dim[i];
			}
		}
		
		public String getCoordinates() {
			String metrics = "";
			for(int i=0; i<dim.length; i++){
				if( i==0 )
					metrics += String.format("%d", dim[i]);
				else
					metrics += String.format(", %d", dim[i]);
			}
			return metrics;
		}

		public int getDimSum() {
			int sum = 1;
			for(int i=0; i<dim.length; i++){
				sum += dim[i];
			}
			return sum;
		}

		public String getLimits(NPoint right) {
			String limits = "";
			for(int i=0; i<dim.length; i++){
				if( i==0 )
					limits += String.format("%d-%d", dim[i], right.dim[i]);
				else
					limits += String.format(",%d-%d", dim[i], right.dim[i]);
			}
			return limits;
		}

		public int getSize() {
			int size = 1;
			for(int i=0; i<dim.length; i++){
				size *= dim[i];
			}
			return size;
		}

		public String getMetrics() {
			String metrics = "";
			for(int i=0; i<dim.length; i++){
				if( i==0 )
					metrics += String.format("%d", dim[i]);
				else
					metrics += String.format("x%d", dim[i]);
			}
			return metrics;
		}

		public String getKey() {
			String key="";
			for(int i=0; i<dim.length; i++){
				if( i == 0 )
					key += String.format("%d", getDim(i));
				else
					key += String.format(",%d", getDim(i));
			}
			return key; 
		}

		public final int getDim(int i){
			return dim[i];
		}
	}

	public class NRegion {
		public final Point left = newPoint();
		public final Point right = newPoint();
		public final Point step = newPoint();
		public final Point size = newPoint();
		public final Point point = newPoint();

		public NCubeSquareIterator<Point, Region> iter;
		
		final public NCubeIterator<Point, Region> iterator() {
			if( iter == null ){
				iter = new NCubeSquareIterator<Point, Region>(left, right, step, size, point, breaks);
				point.outRegion = this;
			} else {
				iter.clear();
			}
			return iter;
		}
	}

	public NCube(int N) {
		this.N = N;
		this.cubeRegion = newRegion();
	}

	final public void populate(int[] narr) {
		impl.populate(cubeRegion, narr);
	}
	
	final public void setBreaks(int breaks) {
		this.breaks = breaks;
	}
	
	final public int getBreaks() {
		return breaks;
	}


}
