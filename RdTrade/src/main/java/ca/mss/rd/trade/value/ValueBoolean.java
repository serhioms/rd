/**
 * 
 */
package ca.mss.rd.trade.value;



/**
 * @author moskovsk
 *
 */
public class ValueBoolean extends Value {

	/**
	 * @param value
	 */
	private ValueBoolean(String value) {
		super(value);
	}

	public ValueBoolean(boolean b) {
		super(b?Value.YES.value: Value.NO.value);
	}
	
}


