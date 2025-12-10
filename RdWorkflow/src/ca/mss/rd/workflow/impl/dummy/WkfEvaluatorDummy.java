/**
 * 
 */
package ca.mss.rd.workflow.impl.dummy;

import ca.mss.rd.workflow.def.WkfData;
import ca.mss.rd.workflow.def.WkfEvaluator;
import ca.mss.rd.workflow.def.WkfTransition;

/**
 * @author smoskov
 *
 */
public class WkfEvaluatorDummy implements WkfEvaluator {

	/* (non-Javadoc)
	 * @see ca.mss.workflow.def.WkfEvaluator#evaluate(ca.mss.workflow.def.WkfTransition, ca.mss.workflow.def.WkfData)
	 */
	@Override
	public boolean evaluate(WkfTransition transition, WkfData data) {
		return true;
	}

}
