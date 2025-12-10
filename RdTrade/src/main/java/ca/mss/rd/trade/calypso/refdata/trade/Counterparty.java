/**
 * 
 */
package ca.mss.rd.trade.calypso.refdata.trade;

import ca.mss.rd.trade.calypso.refdata.LegalEntitiy;

/**
 * @author moskovsk
 *
 * At a minimum, any trade requires a processing organization to identify your organization, 
 * and a counterparty to indentify the other side of the deal. These legal entities need to be associated with 
 * contact information. 
 *
 */
public class Counterparty extends LegalEntitiy {
	
	final static public String className = Counterparty.class.getName();
	final static public long serialVersionUID = className.hashCode();

	/**
	 * @param shortName
	 */
	private Counterparty(String shortName) {
		super(shortName);
	}

	
	
}


