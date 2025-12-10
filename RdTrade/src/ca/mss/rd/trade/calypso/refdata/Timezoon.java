/**
 * 
 */
package ca.mss.rd.trade.calypso.refdata;

/**
 * @author moskovsk
 *
 */
public class Timezoon {

	final static public String className = Timezoon.class.getName();
	final static public long serialVersionUID = className.hashCode();

	public String name = "Greenwich";

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return name;
	}
	
}


