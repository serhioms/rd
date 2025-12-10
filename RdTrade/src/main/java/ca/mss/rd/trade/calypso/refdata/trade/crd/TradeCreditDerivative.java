/**
 * 
 */
package ca.mss.rd.trade.calypso.refdata.trade.crd;

import ca.mss.rd.trade.calypso.environment.PropertyDefault;
import ca.mss.rd.trade.calypso.refdata.trade.Trade;

/**
 * @author moskovsk
 *
 * In addition, credit derivatives trades require issuers. Defining issuers is described below. 
 * 
 */
public class TradeCreditDerivative extends Trade {

	final static public String className = TradeCreditDerivative.class.getName();
	final static public long serialVersionUID = className.hashCode();

	public Issuer[] issuers;
	
	/**
	 * 
	 */
	public TradeCreditDerivative() {
		properties.add(PropertyDefault.CASH_SETTLEMENT_TRADE);
		properties.add(PropertyDefault.DIFFERENT_RESET_DT_PER_CPN);
	}
	
}


