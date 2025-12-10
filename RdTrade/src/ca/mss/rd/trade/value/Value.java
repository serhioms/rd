/**
 * 
 */
package ca.mss.rd.trade.value;

import ca.mss.rd.trade.calypso.refdata.Time;

/**
 * @author moskovsk
 *
 */
public class Value {

	final static public String className = Value.class.getName();
	final static public long serialVersionUID = className.hashCode();

	final static private String[] VALUE_YES = new String[]{"yes", "true", "1"};  
	final static private String[] VALUE_NO = new String[]{"no", "false", "0", "null"};  

	final static public Value YES = new Value(VALUE_YES[0]);  
	final static public Value NO = new Value(VALUE_NO[0]);  
	
	
	public String value;
	
	/**
	 * @param value
	 */
	public Value(String value) {
		populate(value);
	}

	public Value(int value) {
		populate(value);
	}

	public Value(Time value) {
		this.value = value.name();
	}

	final public boolean yes(){
		for(int i=0; i<VALUE_YES.length; i++)
			if( VALUE_YES[i].equalsIgnoreCase(value) )
				return true;
		return false; 
	}
	
	final public boolean no(){
		for(int i=0; i<VALUE_NO.length; i++)
			if( VALUE_NO[i].equalsIgnoreCase(value) )
				return true;
		return false; 
	}
	
	final public void populate(int value){
		this.value = Integer.toString(value);
	}
	
	final public void populate(String value){
		this.value = value;
	}
	
}


