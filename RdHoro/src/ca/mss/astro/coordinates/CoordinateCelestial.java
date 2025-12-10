/**
 * 
 */
package ca.mss.astro.coordinates;

/**
 * @author moskovsk
 *
 */
public class CoordinateCelestial implements Coordinate {

	final static public long CELESTIAL_RADIUS_KM = Long.MAX_VALUE;

	final static public double DECLINATION_ZERO = 0.0D;
	final static public double DECLINATION_SOUTH = -90.0D;
	final static public double DECLINATION_NORTH = +90.0D;
	
	final static public double RIGHT_ASCENSION = 0.0D;

	final static Coordinate.Terminology[] types = new Coordinate.Terminology[]{
		Coordinate.Terminology.Celestial, 
		Coordinate.Terminology.Equatorial};
	
	final static public Coordinate.Terminology getType(){
		return types[0];
	}
	
	final static public Coordinate.Terminology[] getTypeSynonym(){
		return types;
	}
	
}


