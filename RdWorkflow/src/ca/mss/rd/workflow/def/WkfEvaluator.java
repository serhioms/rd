/**
 * 
 */
package ca.mss.rd.workflow.def;


/**
 * @author smoskov
 *
 */
public interface WkfEvaluator {

	public boolean evaluate(WkfTransition transition, WkfData data);
	
}
