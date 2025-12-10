/**
 * 
 */
package ca.mss.rd.workflow.def;

import java.util.Map;

/**
 * @author smoskov
 *
 */
public interface WkfTransitionEvaluator {

	public boolean evaluate(WkfActivity target, WkfTransitionExpression expr, Map<String, Object> data);
	
}
