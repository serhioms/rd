/**
 * 
 */
package ca.mss.rd.workflow.proc;

import ca.mss.rd.workflow.def.WkfContext;
import ca.mss.rd.workflow.def.Workflow;

/**
 * @author mss
 *
 */
public interface WkfProcessor {

	public void startWorkflow(Workflow workflow);
	public void resumeWorkflow();
	public boolean isWorkflowFinished();
	public void setWkfInterceptor(WkfInterceptor interceptor);
	
	public WkfContext getWkfContext();

}
