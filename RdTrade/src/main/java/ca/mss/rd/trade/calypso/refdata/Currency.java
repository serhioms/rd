/**
 * 
 */
package ca.mss.rd.trade.calypso.refdata;


/**
 * @author moskovsk
 *
 * A currency is identified by its currency code throughout the system.
 * 
 */
public class Currency {

	final static public String className = Currency.class.getName();
	final static public long serialVersionUID = className.hashCode();

	
	public CurrencyCode code;
	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return code.name();
	}


	public enum CurrencyCode {
		ANY,
		USD,
		EUR;
	}
}


