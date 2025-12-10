/**
 * 
 */
package ca.mss.rd.workflow.impl;

import ca.mss.rd.workflow.def.WkfCondition;

/**
 * @author mss
 *
 */
public class WkfConditionImpl implements WkfCondition {

	private String expression;
	
	public WkfConditionImpl() {
	}
	
	public WkfConditionImpl(String expression) {
		setExpression(expression);
	}
	
	/**
	 * @return the expression
	 */
	public String getExpression() {
		return expression;
	}
	/**
	 * @param expression the expression to set
	 */
	public void setExpression(String expression) {
		this.expression = expression;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return expression;
	}

	
}
