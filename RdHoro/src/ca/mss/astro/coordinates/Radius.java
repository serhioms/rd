/**
 * 
 */
package ca.mss.astro.coordinates;

/**
 * @author moskovsk
 *
 */
public enum Radius {

	Earth(ca.mss.astro.coordinates.Earth.EARTH_MEAN_RADIUS_KM),
	Celestial(CoordinateCelestial.CELESTIAL_RADIUS_KM)
	;

	public long km;

	/**
	 * @param m
	 */
	private Radius(long km) {
		this.km = km;
	}
	
	
}


