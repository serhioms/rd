/**
 * 
 */
package ca.mss.rd.trade.calypso.boprocess.mom;

import ca.mss.rd.trade.value.Value;



/**
 * @author moskovsk
 *
 */
public class Advice {

	final static public String className = Advice.class.getName();
	final static public long serialVersionUID = className.hashCode();

	public Value value;
	
	/**
	 * @param adviceValue
	 */
	public Advice(Value value) {
		this.value = value;
	}



	final public void populate(int val){
		value.populate(val);
	}

}


