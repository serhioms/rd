/**
 * 
 */
package ca.mss.rd.workflow.impl.dummy;

import ca.mss.rd.workflow.def.WkfContext;
import ca.mss.rd.workflow.def.WkfContextFactory;

/**
 * @author smoskov
 *
 */
public class WkfContextFactoryDummy implements WkfContextFactory {

	private WkfContext wkfContextDummy = new WkfContextDummy();
	/* (non-Javadoc)
	 * @see ca.mss.workflow.def.WkfContextFactory#createNewContext()
	 */
	@Override
	public WkfContext createNewContext() {
		return wkfContextDummy;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Dummy Factory";
	}
}
