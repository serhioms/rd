/**
 * 
 */
package ca.mss.rd.workflow.impl;

import ca.mss.rd.workflow.def.WkfActivity;
import ca.mss.rd.workflow.def.WkfCondition;
import ca.mss.rd.workflow.def.WkfTransition;

/**
 * @author smoskov
 *
 */
public class WkfTransitionFactory {
	
	public static WkfTransition Goto(WkfActivity activity){
		return new WkfTransitionGoto(activity); 
	}

	public static WkfTransition ConditionalGoto(WkfActivity activity, WkfCondition condition){
		return new WkfTransitionConditional(activity, condition); 
	}

	public static WkfTransition ConditionalGoto(WkfActivity activity, String expression){
		return new WkfTransitionConditional(activity, expression); 
	}

	public static WkfTransition Otherwise(WkfActivity activity){
		return new WkfTransitionOtherwise(activity); 
	}

}
