/**
 * 
 */
package ca.mss.rd.test.workflow.interceptors;

import java.util.Set;

import ca.mss.rd.workflow.def.WkfActivity;
import ca.mss.rd.workflow.def.WkfContextFactory;
import ca.mss.rd.workflow.def.WkfTransition;
import ca.mss.rd.workflow.def.Workflow;

/**
 * @author smoskov
 *
 */
public class JUnitWorkflow implements Workflow {

	private Workflow flow;
	
	/**
	 * 
	 */
	public JUnitWorkflow(Workflow flow) {
		this.flow = flow;
	}

	/* (non-Javadoc)
	 * @see ca.mss.workflow.def.Workflow#getStartActivities()
	 */
	@Override
	public Set<WkfActivity> getStartActivities() {
		return flow.getStartActivities();
	}

	/* (non-Javadoc)
	 * @see ca.mss.workflow.def.Workflow#getWorkflow(ca.mss.workflow.def.WkfActivity)
	 */
	@Override
	public WkfTransition[] getTransitions(WkfActivity activity) {
		return flow.getTransitions(activity);
	}

	/* (non-Javadoc)
	 * @see ca.mss.workflow.def.Workflow#getSplitTransitions(ca.mss.workflow.def.WkfActivity)
	 */
	@Override
	public WkfTransition[] getSplitTransitions(WkfActivity activity) {
		return flow.getSplitTransitions(activity);
	}

	/* (non-Javadoc)
	 * @see ca.mss.workflow.def.Workflow#getJoinTransitions(ca.mss.workflow.def.WkfActivity)
	 */
	@Override
	public WkfTransition[] getJoinTransitions(WkfActivity activity) {
		return flow.getJoinTransitions(activity);
	}

	/* (non-Javadoc)
	 * @see ca.mss.workflow.def.Workflow#getWkfContextFactory()
	 */
	@Override
	public WkfContextFactory getWkfContextFactory() {
		return new JUnitContextFactory(flow.getWkfContextFactory());
	}

}
