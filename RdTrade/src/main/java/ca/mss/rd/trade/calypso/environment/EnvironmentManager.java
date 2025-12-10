/**
 * 
 */
package ca.mss.rd.trade.calypso.environment;


/**
 * @author moskovsk
 *
 */
public interface EnvironmentManager {

	final static public String className = EnvironmentManager.class.getName();
	final static public long serialVersionUID = className.hashCode();


	public void add(Property ep);
	
	public void edit(Property ep);
	
	public void remove(Property ep);
	
	

}


