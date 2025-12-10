/**
 * 
 */
package ca.mss.astro.coordinates;

/**
 * @author moskovsk
 *
 */
public interface Planet {
	
	
	public enum SolarSystemPlanet {
		Mercury,
		Venus,
		Earth,
		Mars,
		Jupiter,
		Saturn,
		Uranus,
		Neptune
		;
	}

	
	public SolarSystemPlanet getPlanetName();
}


