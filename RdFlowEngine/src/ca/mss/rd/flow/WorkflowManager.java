package ca.mss.rd.flow;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import ca.mss.rd.flow.event.WorkflowEvent;
import ca.mss.rd.flow.event.WorkflowListener;
import ca.mss.rd.flow.simple.Shutdown;
import ca.mss.rd.job.IJobPool;
import ca.mss.rd.job.JobPoolParallel;
import ca.mss.rd.util.Logger;

public class WorkflowManager implements IFlowManager {

	final static private Shutdown SHUTDOWN = new Shutdown();
	
	public static String LOG_FORMAT = "%-24s [%tc]";

	private volatile AtomicInteger isExecuting = new AtomicInteger(0);

	@SuppressWarnings("rawtypes")
	private IJobPool<IWorkflow> jobWkf;

	@SuppressWarnings("rawtypes")
	private IJobPool<IActivity> jobActv;

	private IJobPool<ITool> jobTool;

	private List<WorkflowListener> listeners = new ArrayList<WorkflowListener>(0);

	@Override
	public String getState() {
		return String.format("jobWkf=%d, jobActv=%d, jobTool=%d", jobWkf.size(), jobActv.size(), jobTool.size());
	}

	public WorkflowManager() {
		this(10, 1, 100, 5, 1000, 10);
	}

	public WorkflowManager(int queueSize, int execSize) {
		this(queueSize, execSize, queueSize * 10, execSize * 10, queueSize * 100, execSize * 100);
	}

	@SuppressWarnings("rawtypes")
	public WorkflowManager(int qsizeWkf, int esizeWkf, int qsizeTool, int esizeTool, int qsizeActv, int esizeActv) {
		jobWkf = new JobPoolParallel<IWorkflow>("Workflow", qsizeWkf, esizeWkf);
		jobActv = new JobPoolParallel<IActivity>("Activity", qsizeTool, esizeTool);
		jobTool = new JobPoolParallel<ITool>("Tool", qsizeActv, esizeActv);
	}

	@Override
	public void startup() {
		assert(Logger.WKFMAN.isOn? Logger.WKFMAN.printf(LOG_FORMAT, "Start workflow engine", Calendar.getInstance()): true);
		jobWkf.startPool();
		jobTool.startPool();
		jobActv.startPool();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void shutdown() {
		assert(Logger.WKFMAN.isOn? Logger.WKFMAN.printf(LOG_FORMAT, "Shutdown workflow engine", Calendar.getInstance()): true);
		jobWkf.shutdownPool(SHUTDOWN);
		jobActv.shutdownPool(SHUTDOWN);
		jobTool.shutdownPool(SHUTDOWN);
	}

	@Override
	public boolean isRunning() {
		return isExecuting.get() > 0 || jobWkf.isRunning() || jobActv.isRunning() || jobTool.isRunning();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void queue(IWorkflow<?,?> flow) {
		fireEvent(WorkflowEvent.FlowQueueEvent, flow);
		flow.setManager(this);
		isExecuting.addAndGet(+1);
		jobWkf.queueJob(flow);
	}

	@Override
	public void executeNotify(IWorkflow<?,?> wkf) {
		fireEvent(WorkflowEvent.FlowStartEvent, wkf);
	}

	@Override
	public void endNotify(IWorkflow<?,?> wkf) {
		fireEvent(WorkflowEvent.FlowEndEvent, wkf);
		isExecuting.addAndGet(-1);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void suspend(IWorkflow wkf) {
		fireEvent(WorkflowEvent.FlowSuspendEvent, wkf);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void resume(IWorkflow wkf) {
		fireEvent(WorkflowEvent.FlowResumeEvent, wkf);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void abort(IWorkflow wkf) {
		fireEvent(WorkflowEvent.FlowAbortEvent, wkf);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void terminate(IWorkflow wkf) {
		fireEvent(WorkflowEvent.FlowTerminateEvent, wkf);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void queue(IActivity a) {
		fireEvent(WorkflowEvent.ActivityQueueEvent, a);
		jobActv.queueJob(a);
	}

	@Override
	public void executeNotify(IActivity<?,?> act) {
		fireEvent(WorkflowEvent.ActivityStartEvent, act);
	}

	@Override
	public void endNotify(IActivity<?,?> act) {
		fireEvent(WorkflowEvent.ActivityEndEvent, act);
	}

	@Override
	public void failedNotify(IActivity<?,?> act) {
		fireEvent(WorkflowEvent.ActivityEndEvent, act);
	}

	@Override
	public void retryNotify(IActivity<?,?> act) {
		fireEvent(WorkflowEvent.ActivityEndEvent, act);
	}

	@Override
	public void queue(ITool tool) {
		fireEvent(WorkflowEvent.ToolQueueEvent, tool);
		jobTool.queueJob(tool);
	}

	
	@Override
	public void startNotify(ITool tool) {
		fireEvent(WorkflowEvent.ToolStartEvent, tool);
	}

	@Override
	public void endNotify(ITool tool) {
		fireEvent(WorkflowEvent.ToolEndEvent, tool);
	}

	@Override
	public void addListener(WorkflowListener l) {
		listeners.add(l);
	}

	@Override
	public void removeListener(WorkflowListener l) {
		listeners.remove(l);
	}

	/* 
	 * Fire a WorkflowEvent to all registered listeners 
	 */
	protected void fireEvent(WorkflowEvent event, IWorkflow<?,?> wkf) {
		if ( !listeners.isEmpty() ){
			// create the event object to send
//			WorkflowEvent event = new WorkflowEvent(this);

			// make a copy of the listener list in case
			// anyone adds/removes listeners
//			Vector targets;
//			synchronized (this) {
//				targets = (Vector) listeners.clone();
//			}

			// walk through the listener list and
			// call the sunMoved method in each
			for(WorkflowListener listener:  listeners) {
				try {
					listener.stateChange(event, wkf);
				} catch (Throwable t) {
					if( Logger.ERROR.isOn ) Logger.ERROR.printf(t, "Workflow %s[%d] listener failed", wkf.getName(), wkf.getId());
				}
			}
		}
	}
	
	protected void fireEvent(WorkflowEvent event, IActivity<?,?> act) {
		if ( !listeners.isEmpty() ){
			// create the event object to send
//			WorkflowEvent event = new WorkflowEvent(this);

			// make a copy of the listener list in case
			// anyone adds/removes listeners
//			Vector targets;
//			synchronized (this) {
//				targets = (Vector) listeners.clone();
//			}

			// walk through the listener list and
			// call the sunMoved method in each
			for(WorkflowListener listener:  listeners) {
				try {
					listener.stateChange(event, act);
				} catch (Throwable t) {
					if( Logger.ERROR.isOn ) Logger.ERROR.printf(t, "Activity %s[%d] listener failed", act.getName(), act.getId());
				}
			}
		}
	}


	protected void fireEvent(WorkflowEvent event, ITool tool) {
		if ( !listeners.isEmpty() ){
			// create the event object to send
//			WorkflowEvent event = new WorkflowEvent(this);

			// make a copy of the listener list in case
			// anyone adds/removes listeners
//			Vector targets;
//			synchronized (this) {
//				targets = (Vector) listeners.clone();
//			}

			// walk through the listener list and
			// call the sunMoved method in each
			for(WorkflowListener listener:  listeners) {
				try {
					listener.stateChange(event, tool);
				} catch (Throwable t) {
					if( Logger.ERROR.isOn ) Logger.ERROR.printf(t, "Tool $s[%d] listener failed", tool.getName(), tool.getId());
				}
			}
		}
	}
	
}
