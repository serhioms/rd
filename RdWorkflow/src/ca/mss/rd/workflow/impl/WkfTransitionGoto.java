/**
 * 
 */
package ca.mss.rd.workflow.impl;

import ca.mss.rd.workflow.def.WkfActivity;
import ca.mss.rd.workflow.def.WkfCondition;
import ca.mss.rd.workflow.def.WkfContext;
import ca.mss.rd.workflow.def.WkfTransition;

/**
 * @author smoskov
 *
 */
public class WkfTransitionGoto extends WkfTransitionBean implements WkfTransition {

	public WkfTransitionGoto(){
	}

	public WkfTransitionGoto(WkfActivity activity) {
		setEndActivity(activity);
	}

	/* (non-Javadoc)
	 * @see ca.mss.workflow.WkfTransition#isConditional()
	 */
	@Override
	public boolean isConditional() {
		return false;
	}

	/* (non-Javadoc)
	 * @see ca.mss.workflow.WkfTransition#isOtherwise()
	 */
	@Override
	public boolean isOtherwise() {
		return false;
	}

	/* (non-Javadoc)
	 * @see ca.mss.workflow.WkfTransition#getIdentifier()
	 */
	@Override
	public WkfCondition getCondition() {
		return null;
	}

	/* (non-Javadoc)
	 * @see ca.mss.workflow.def.WkfTransition#evaluate(ca.mss.workflow.def.WkfData)
	 */
	@Override
	public boolean evaluate(WkfContext data) {
		return true;
	}

	
}
