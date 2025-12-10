/**
 * 
 */
package ca.mss.rd.workflow.impl;

import ca.mss.rd.workflow.def.WkfTransitionExpression;

/**
 * @author mss
 *
 */
public class WkfTransitionExpressionImpl implements WkfTransitionExpression {

	private String id;
	private String expression;
	
	public WkfTransitionExpressionImpl() {
	}
	
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
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
		return id+"${"+expression+"}";
	}
	
	
	
}
