/**
 * 
 */
package ca.mss.rd.workflow.dynamic;

import java.util.Map;

import ca.mss.rd.workflow.def.WkfContextFactory;
import ca.mss.rd.workflow.reader.WkfReader;

/**
 * @author smoskov
 *
 */
abstract public class DynaContextFactory implements WkfContextFactory {

	private WkfReader wkfReader;
	private Map<String, Object> initialData;
	
	/**
	 * 
	 */
	public DynaContextFactory() {
	}

	/**
	 * @param wkfReader
	 */
	public DynaContextFactory(WkfReader wkfReader) {
		setWkfReader(wkfReader);
	}
	
	/**
	 * @return the wkfReader
	 */
	/* (non-Javadoc)
	 * @see ca.mss.workflow.dynamic.DynaFlow#getWkfReader()
	 */
	final public WkfReader getWkfReader() {
		return wkfReader;
	}

	/**
	 * @param wkfReader the wkfReader to set
	 */
	final public void setWkfReader(WkfReader wkfReader) {
		this.wkfReader = wkfReader;
	}

	/**
	 * @return the initialData
	 */
	public final Map<String, Object> getInitialData() {
		if( initialData == null ){
			// TODO: Should not be ZERO here but couple process data instead
			setInitialData(getWkfReader().getInitialData(0)); 
		}
		return initialData;
	}

	/**
	 * @param initialData the initialData to set
	 */
	public final void setInitialData(Map<String, Object> initialData) {
		this.initialData = initialData;
	}

	
}
