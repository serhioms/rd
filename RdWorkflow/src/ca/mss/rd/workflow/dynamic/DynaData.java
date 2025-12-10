/**
 * 
 */
package ca.mss.rd.workflow.dynamic;

import java.util.HashMap;
import java.util.Map;

import ca.mss.rd.workflow.def.WkfData;

/**
 * @author mss
 *
 */
public class DynaData extends HashMap<String, Object> implements WkfData {

	/**
	 * 
	 */
	private static final long serialVersionUID = DynaData.class.hashCode();

	/**
	 * 
	 */
	public DynaData() {
		super();
	}

	/**
	 * @param capacity
	 * @param loadFactor
	 */
	public DynaData(int capacity, float loadFactor) {
		super(capacity, loadFactor);
	}

	/**
	 * @param capacity
	 */
	public DynaData(int capacity) {
		super(capacity);
	}

	/**
	 * @param map
	 */
	public DynaData(Map<? extends String, ? extends Object> map) {
		super(map);
	}

	
}
