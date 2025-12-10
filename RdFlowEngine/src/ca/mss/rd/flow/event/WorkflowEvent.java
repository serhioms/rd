package ca.mss.rd.flow.event;

import java.util.EventObject;

public class WorkflowEvent extends EventObject {

	private static final long serialVersionUID = WorkflowEvent.class.getName().hashCode();

	static final public WorkflowEvent FlowQueueEvent = new WorkflowEvent(FlowEvents.FlowQueue);
	static final public WorkflowEvent FlowStartEvent = new WorkflowEvent(FlowEvents.FlowStart);
	static final public WorkflowEvent FlowSuspendEvent = new WorkflowEvent(FlowEvents.FlowSuspend);
	static final public WorkflowEvent FlowResumeEvent = new WorkflowEvent(FlowEvents.FlowResume);
	static final public WorkflowEvent FlowAbortEvent = new WorkflowEvent(FlowEvents.FlowAbort);
	static final public WorkflowEvent FlowTerminateEvent = new WorkflowEvent(FlowEvents.FlowTerminate);
	static final public WorkflowEvent FlowEndEvent = new WorkflowEvent(FlowEvents.FlowEnd);
	static final public WorkflowEvent ActivityQueueEvent = new WorkflowEvent(FlowEvents.ActivityQueue);
	static final public WorkflowEvent ActivityStartEvent = new WorkflowEvent(FlowEvents.ActivityStart);
	static final public WorkflowEvent ActivityFailedEvent = new WorkflowEvent(FlowEvents.ActivityFailed);
	static final public WorkflowEvent ActivityRetryEvent = new WorkflowEvent(FlowEvents.ActivityRetry);
	static final public WorkflowEvent ActivityEndEvent = new WorkflowEvent(FlowEvents.ActivityEnd);
	static final public WorkflowEvent ToolQueueEvent = new WorkflowEvent(FlowEvents.ToolQueue);
	static final public WorkflowEvent ToolStartEvent = new WorkflowEvent(FlowEvents.ToolStart);
	static final public WorkflowEvent ToolEndEvent = new WorkflowEvent(FlowEvents.ToolEnd);

	
	static public enum FlowEvents {
		FlowQueue, FlowStart, FlowSuspend, FlowResume, FlowAbort, FlowTerminate, FlowEnd, 
		ActivityQueue, ActivityStart, ActivityFailed, ActivityRetry, ActivityEnd, 
		ToolQueue, ToolStart, ToolEnd;
	}

	
	public final FlowEvents source;
	
	public WorkflowEvent(FlowEvents source) {
		super(source);
		this.source = source;
	}
}
