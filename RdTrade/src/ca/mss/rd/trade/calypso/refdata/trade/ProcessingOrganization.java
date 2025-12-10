/**
 * 
 */
package ca.mss.rd.trade.calypso.refdata.trade;

import ca.mss.rd.trade.calypso.refdata.LegalEntitiy;

/**
 * @author moskovsk
 *
 * At a minimum, any trade requires a processing organization to identify your organization, 
 * and a counterparty to indentify the other side of the deal.
 * 
 */
public class ProcessingOrganization extends LegalEntitiy {
	
	final static public String className = ProcessingOrganization.class.getName();
	final static public long serialVersionUID = className.hashCode();

	public TradingBook tradingBook;

	/**
	 * @param shortName
	 */
	private ProcessingOrganization(String shortName) {
		super(shortName);
	}
	
	
}


