package ng.ca.mss.rd.trader;

import ca.mss.rd.flow.IActivity;
import ca.mss.rd.flow.IContext;
import ca.mss.rd.flow.ITool;
import ca.mss.rd.flow.IWorkflow;
import ca.mss.rd.flow.event.WorkflowEvent;
import ca.mss.rd.flow.event.WorkflowListener;
import ca.mss.rd.util.Logger;

public class WorkflowLogger<PROP,CONTEXT extends IContext<PROP>> implements WorkflowListener<PROP,CONTEXT> {

	public WorkflowLogger() {
	}

	@Override
	public void stateChange(WorkflowEvent event, IWorkflow<PROP,CONTEXT> wkf) throws Throwable {
		assert( Logger.WORKFLOW_VERBOSE.isOn ? Logger.WORKFLOW_VERBOSE.printf("%s[%d].%s", wkf.getName(), wkf.getId(), event.source): true);
	}

	@Override
	public void stateChange(WorkflowEvent event, IActivity<PROP,CONTEXT> act) throws Throwable {
		assert( Logger.ACTIVITY_VERBOSE.isOn ? Logger.ACTIVITY_VERBOSE.printf("%s[%d].%s", act.getName(), act.getId(), event.source): true);
	}

	@Override
	public void stateChange(WorkflowEvent event, ITool tool) throws Throwable {
		assert( Logger.TOOL_VERBOSE.isOn ? Logger.TOOL_VERBOSE.printf("%s[%d].%s", tool.getName(), tool.getId(), event.source): true);
	}

	
	
}
