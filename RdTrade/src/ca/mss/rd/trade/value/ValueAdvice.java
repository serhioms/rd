/**
 * 
 */
package ca.mss.rd.trade.value;

import ca.mss.rd.trade.calypso.refdata.Time;


/**
 * @author moskovsk
 *
 */
public class ValueAdvice extends Value {

	final static public String className = ValueAdvice.class.getName();
	final static public long serialVersionUID = className.hashCode();

	/**
	 * @param value
	 */
	public ValueAdvice(String value) {
		super(value);
	}

	/**
	 * @param value
	 */
	private ValueAdvice(int value) {
		super(value);
	}

	/**
	 * @param value
	 */
	private ValueAdvice(Time value) {
		super(value);
	}

}


