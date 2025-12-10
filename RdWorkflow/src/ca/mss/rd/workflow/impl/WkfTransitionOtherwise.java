/**
 * 
 */
package ca.mss.rd.workflow.impl;

import ca.mss.rd.workflow.def.WkfActivity;
import ca.mss.rd.workflow.def.WkfTransition;

/**
 * @author smoskov
 *
 */
public class WkfTransitionOtherwise extends WkfTransitionGoto implements WkfTransition {

	public WkfTransitionOtherwise(WkfActivity activity){
		super(activity);
	}

	/* (non-Javadoc)
	 * @see ca.mss.workflow.impl.WkfTransitionGoto#isOtherwise()
	 */
	@Override
	public boolean isOtherwise() {
		return true;
	}

	
	
}
