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
public class WkfTransitionConditional extends WkfTransitionGoto implements WkfTransition {

	private WkfCondition condition;

	public WkfTransitionConditional(){
		this.condition = null;
	}
	
	public WkfTransitionConditional(WkfActivity activity, WkfCondition condition){
		super(activity);
		this.condition = condition;
	}
	
	public WkfTransitionConditional(WkfActivity activity, String expression){
		this(activity, new WkfConditionImpl(expression));
	}

	/* (non-Javadoc)
	 * @see ca.mss.workflow.impl.WkfTransitionGoto#isConditional()
	 */
	@Override
	public boolean isConditional() {
		return true;
	}

	/* (non-Javadoc)
	 * @see ca.mss.workflow.impl.WkfTransitionGoto#getExpression()
	 */
	@Override
	public WkfCondition getCondition() {
		return condition;
	}

	/* (non-Javadoc)
	 * @see ca.mss.workflow.impl.WkfTransitionGoto#evaluate(ca.mss.workflow.def.WkfData)
	 */
	@Override
	public boolean evaluate(WkfContext data) {
		return data.evaluate(this);
	}
}
