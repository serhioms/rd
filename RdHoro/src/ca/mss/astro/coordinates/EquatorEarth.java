/**
 * 
 */
package ca.mss.astro.coordinates;

/**
 * @author moskovsk
 *
 */
public class EquatorEarth implements Equator {

	final static public Point northPole = Point.NorthPole; 
	final static public Point southPole = Point.SouthPole; 
	final static public Point center = Point.EarthCenter; 

	final static public Axis rotationAxis = new Axis(new Point[]{northPole, southPole, center}); 
	
	/* (non-Javadoc)
	 * @see ca.mss.astro.coordinates.Surface#getCenter()
	 */
	@Override
	public Point getCenter() {
		return center;
	}

	/* (non-Javadoc)
	 * @see ca.mss.astro.coordinates.Surface#getAxis()
	 */
	@Override
	public Axis getAxis() {
		return rotationAxis;
	}

	/* (non-Javadoc)
	 * @see ca.mss.astro.coordinates.Circle#getRadius()
	 */
	@Override
	public Radius getRadius() {
		return Radius.Earth;
	}

	
	
}


