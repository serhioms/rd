/**
 * 
 */
package ca.mss.astro.coordinates;

/**
 * @author moskovsk
 *
 */
public class CoordinateEarth implements Coordinate {

	final static public Equator equator = new EquatorEarth();
	
	
	final static Coordinate.Terminology[] types = new Coordinate.Terminology[]{
		Coordinate.Terminology.Earth};
			
	final static public Coordinate.Terminology getType(){
		return types[0];
	}
	
	final static public Coordinate.Terminology[] getTypeSynonym(){
		return types;
	}
	
}


