/**
 * 
 */
package ca.mss.rd.trade.calypso.boprocess.mom;

import ca.mss.rd.trade.value.Value;

/**
 * @author moskovsk
 *
 */
public class PaymentAdvice extends Advice {

	final static public String className = PaymentAdvice.class.getName();
	final static public long serialVersionUID = className.hashCode();

	/**
	 * 
	 */
	public PaymentAdvice(Value value) {
		super(value);
	}

	
}


