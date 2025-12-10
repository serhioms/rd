package com.scotiabank.maestro.msg.workflow.condition;

import ca.mss.rd.flow.IActivity;

import com.scotiabank.maestro.msg.workflow.WorkflowValidationException;


/**
 * Evaluates conditions.
 *
 */
public interface IConditionEvaluator {

	/**
	 * Evaluate condition
	 * 
	 * @param substitutedCondition The condition as a string with the ${abc} type variables with actual values..
	 * @return
	 */
	public boolean eval(String substitutedCondition);
	
	/**
	 * Validate the condition (as a string).  Can contain the ${abc} syntax in the string.
	 * @param messages
	 * @param s
	 * @throws WorkflowValidationException
	 */
	public void validate(IActivity<?,?> activity, String originalCondition, WorkflowValidationVO result);

	/**
	 * Evaluate the condition (values applied) and if there is a exception blow up, return default value.
	 * 
	 * @param substitutedCondition
	 * @param defaultValue
	 * @return
	 */
	public boolean evalWithDefaultValue(String substitutedCondition, boolean defaultValue);
}
