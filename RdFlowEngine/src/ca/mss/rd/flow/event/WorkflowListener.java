package ca.mss.rd.flow.event;

import java.util.EventListener;

import ca.mss.rd.flow.IActivity;
import ca.mss.rd.flow.IContext;
import ca.mss.rd.flow.ITool;
import ca.mss.rd.flow.IWorkflow;

public interface WorkflowListener<PROP, CONTEXT extends IContext<PROP>> extends EventListener {

	public void stateChange(WorkflowEvent event, IWorkflow<PROP,CONTEXT> wkf) throws Throwable;

	public void stateChange(WorkflowEvent event, IActivity<PROP,CONTEXT> act) throws Throwable;

	public void stateChange(WorkflowEvent event, ITool tool) throws Throwable;
	
	
}
