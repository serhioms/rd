package ca.mss.astro.coordinates;

public class Earth implements Planet {

	final static public long EARTH_MIN_RADIUS_KM =  6353L;
	final static public long EARTH_MEAN_RADIUS_KM = 6371L;
	final static public long EARTH_MAX_RADIUS_KM = 6384L;
	
	
	/* (non-Javadoc)
	 * @see ca.mss.astro.coordinates.Planet#getPlanetName()
	 */
	@Override
	public SolarSystemPlanet getPlanetName() {
		return Planet.SolarSystemPlanet.Earth;
	}

	
}


