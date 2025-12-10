/**
 * 
 */
package ca.mss.rd.trade.calypso.refdata.trade.crd;

import ca.mss.rd.trade.calypso.refdata.LegalEntitiy;

/**
 * @author moskovsk
 *
 */
public class Issuer extends LegalEntitiy {

	final static public String className = Issuer.class.getName();
	final static public long serialVersionUID = className.hashCode();

	/**
	 * @param shortName
	 */
	private Issuer(String shortName) {
		super(shortName);
	}
	
	
}


