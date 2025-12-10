/**
 * 
 */
package ca.mss.rd.trade.calypso.boprocess.mom;

import ca.mss.rd.trade.calypso.refdata.LegalEntitiy;

/**
 * @author moskovsk
 *
 */
public class Receiver extends LegalEntitiy {

	final static public String className = Receiver.class.getName();
	final static public long serialVersionUID = className.hashCode();

	/**
	 * 
	 */
	public Receiver(String shortName) {
		super(shortName);
	}

	
}


