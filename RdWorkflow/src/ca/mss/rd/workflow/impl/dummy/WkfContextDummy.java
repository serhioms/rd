/**
 * 
 */
package ca.mss.rd.workflow.impl.dummy;

import ca.mss.rd.workflow.def.WkfActivity;
import ca.mss.rd.workflow.def.WkfContext;
import ca.mss.rd.workflow.def.WkfData;
import ca.mss.rd.workflow.def.WkfTransition;

/**
 * @author smoskov
 *
 */
public class WkfContextDummy implements WkfContext {

	/* (non-Javadoc)
	 * @see ca.mss.workflow.def.WkfContext#evaluate(ca.mss.workflow.def.WkfTransition)
	 */
	@Override
	public boolean evaluate(WkfTransition transition) {
		return true;
	}

	/* (non-Javadoc)
	 * @see ca.mss.workflow.def.WkfContext#run(ca.mss.workflow.def.WkfActivity)
	 */
	@Override
	public void run(WkfActivity activity) {
	}

	/* (non-Javadoc)
	 * @see ca.mss.workflow.def.WkfContext#getData()
	 */
	@Override
	public WkfData getData() {
		return null;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Dummy Context";
	}

}
