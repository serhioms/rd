/**
 * 
 */
package ca.mss.rd.workflow.def;

import java.util.Set;

/**
 * @author smoskov
 *
 */
public interface Workflow {

	public Set<WkfActivity> getStartActivities();
	public WkfTransition[] getTransitions(WkfActivity activity);
	
	public WkfTransition[] getSplitTransitions(WkfActivity activity);
	public WkfTransition[] getJoinTransitions(WkfActivity activity);
	
	public WkfContextFactory getWkfContextFactory();
	
}
