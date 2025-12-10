/**
 * 
 */
package ca.mss.rd.trade.calypso.environment;

import ca.mss.rd.trade.value.Value;
import ca.mss.rd.trade.value.ValueBoolean;

/**
 * @author moskovsk
 *
 */
public enum PropertyGlobal implements Property {

	
	DIFFERENT_RESET_DT_PER_CPN(new ValueBoolean(true)); /*
		Y The reset dates are based on the coupon payment frequency by default for all trades. 
		N The reset dates are based on the index tenor by default for all trades.	
	*/
	
	
	
	
	public Value value;

	/**
	 * @param value
	 */
	private PropertyGlobal(Value value) {
		this.value = value;
	}
	
}


