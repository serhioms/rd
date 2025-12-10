package ca.mss.rd.test.workflow.interceptors;



 import ca.mss.rd.workflow.def.WkfContext;
import ca.mss.rd.workflow.def.Workflow;
import ca.mss.rd.workflow.proc.WkfInterceptor;
import ca.mss.rd.workflow.proc.WkfProcessor;

import org.apache.log4j.Logger;

public class JUnitProcessor implements WkfProcessor {

	public static final String module = JUnitProcessor.class.getName();
	private static org.apache.log4j.Logger logger = ca.mss.rd.util.Debug.getLogger(module);
	
	private WkfProcessor wkfProcessor;
	
	/**
	 * @param wkfProcessor
	 */
	public JUnitProcessor(WkfProcessor wkfProcessor) {
		this.wkfProcessor = wkfProcessor;
	}

	@Override
	public void startWorkflow(Workflow workflow) {
		if( logger.isDebugEnabled()) logger.debug("start [workflow="+workflow+"]");
		wkfProcessor.startWorkflow(workflow);
	}

	@Override
	public void resumeWorkflow() {
		if( logger.isDebugEnabled()) logger.debug("resume");
		wkfProcessor.resumeWorkflow();
	}

	@Override
	public boolean isWorkflowFinished() {
		boolean isWorkflowFinished = wkfProcessor.isWorkflowFinished();
		if( logger.isDebugEnabled()) logger.debug("[isWorkflowFinished="+isWorkflowFinished+"]");
		return isWorkflowFinished;
	}

	@Override
	public void setWkfInterceptor(WkfInterceptor interceptor) {
		wkfProcessor.setWkfInterceptor(interceptor);
	}

	@Override
	public WkfContext getWkfContext() {
		JUnitContext juc = (JUnitContext )wkfProcessor.getWkfContext(); 
		return juc.getWkfContext();
	}

}
