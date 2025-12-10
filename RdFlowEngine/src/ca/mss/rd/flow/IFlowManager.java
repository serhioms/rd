package ca.mss.rd.flow;

import ca.mss.rd.flow.event.WorkflowListener;


public interface IFlowManager {

	public void	startup();
	public void	shutdown();

	public void	queue(IWorkflow<?,?> wkf);
	public void	executeNotify(IWorkflow<?,?> wkf);
	public void	suspend(IWorkflow<?,?> wkfw);
	public void	resume(IWorkflow<?,?> wkfw);
	public void	abort(IWorkflow<?,?> wkfw);
	public void	terminate(IWorkflow<?,?> wkfw);
	public void	endNotify(IWorkflow<?,?> wkf);

	public void	queue(IActivity<?,?> act);
	public void	executeNotify(IActivity<?,?> act);
	public void	failedNotify(IActivity<?,?> act);
	public void	retryNotify(IActivity<?,?> act);
	public void	endNotify(IActivity<?,?> act);
	
	public void	queue(ITool tool);
	public void	startNotify(ITool tool);
	public void	endNotify(ITool tool);

	public boolean isRunning();
	public String getState();
	
	public void addListener(WorkflowListener<?,?> l);
	public void removeListener(WorkflowListener<?,?> l);

}
