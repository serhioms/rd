/**
 * 
 */
package ca.mss.rd.workflow.proc.inthread;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;



 import ca.mss.rd.workflow.def.WkfActivity;
import ca.mss.rd.workflow.def.WkfContext;
import ca.mss.rd.workflow.def.WkfTransition;
import ca.mss.rd.workflow.def.Workflow;
import ca.mss.rd.workflow.proc.WkfInterceptor;
import ca.mss.rd.workflow.proc.WkfProcessor;
import ca.mss.rd.workflow.proc.interceptor.dummy.WkfInterceptorDummy;

import org.apache.log4j.Logger;

/**
 * @author smoskov
 *
 */
public class WkfProcessorImpl implements WkfProcessor {

	public static final String module = WkfProcessorImpl.class.getName();
	private static org.apache.log4j.Logger logger = ca.mss.rd.util.Debug.getLogger(module);


	private Workflow workflow;
	private WkfContext wkfContext;
	private WkfInterceptor wkfInterceptor = new WkfInterceptorDummy();
	
	// Manually started activities stack here 
	private Set<WkfActivity> ready = new HashSet<WkfActivity>(); 
	// Running activities means doing activity implementation procedures 
	private Set<WkfActivity> run = new HashSet<WkfActivity>(); 
	// Failure while performing any of activity implementations
	private Set<WkfActivity> error = new HashSet<WkfActivity>(); 
	// Manually finished activities stack here
	private Set<WkfActivity> done = new HashSet<WkfActivity>(); 
	// Activity with failed transitions  
	private Set<WkfActivity> trans = new HashSet<WkfActivity>(); 
	// Activity with was passed done state but not getting ready or later  
	private Set<WkfActivity> completed = new HashSet<WkfActivity>(); 
	private Set<WkfActivity> completedOnce = new HashSet<WkfActivity>(); 
	// True transition cache  
	private Set<WkfTransition> trueTansitions = new HashSet<WkfTransition>();

	
	
	/**
	 * @return the wkfInterceptor
	 */
	public WkfInterceptor getWkfInterceptor() {
		return wkfInterceptor;
	}

	/**
	 * @param wkfInterceptor the wkfInterceptor to set
	 */
	public void setWkfInterceptor(WkfInterceptor wkfInterceptor) {
		this.wkfInterceptor = wkfInterceptor;
	}
		/**
	 * @return the workflow
	 */
	public Workflow getWorkflow() {
		return workflow;
	}
	
	/**
	 * @param workflow the workflow to set
	 */
	public void setWorkflow(Workflow workflow) {
		this.workflow = workflow;
	}

	/**
	 * @return the wkfContext
	 */
	/* (non-Javadoc)
	 * @see ca.mss.workflow.proc.WkfProcessor#getWkfContext()
	 */
	public WkfContext getWkfContext() {
		return wkfContext;
	}

	/**
	 * @param wkfContext the wkfContext to set
	 */
	public void setWkfContext(WkfContext wkfContext) {
		this.wkfContext = wkfContext;
	}

	public void startWorkflow(Workflow workflow) {
		this.workflow = workflow;
		clearStates();
		
		// Start workflow from any of available to start activities
		Set<WkfActivity> start = workflow.getStartActivities();
		if( !start.isEmpty() ){
			readyAdd(start.iterator().next()); // start from first available
		} else {
			throw new RuntimeException("Can not start workflow. No start activity found.");
		}
		
		setWkfContext(workflow.getWkfContextFactory().createNewContext());

		resumeWorkflow();
	}
	
	public void resumeWorkflow() {

		Set<WkfActivity> temp = new HashSet<WkfActivity>(); 
		Set<WkfActivity> retemp = new HashSet<WkfActivity>(); 
	
		boolean doStep = true;
		while( doStep && !isWorkflowFinished() ){
			doStep = false;

			if( !ready.isEmpty() ){
				// Check ready activities
				temp.clear();
				for(Iterator<WkfActivity> iready=ready.iterator(); iready.hasNext(); ){
					WkfActivity readyActivity = iready.next();
					// Manually started activities just stack here 
					if( readyActivity.getState().isAutoStart() ){
						// ready transit to run
						temp.add(readyActivity);
						run.add(readyActivity); 
						doStep = true;
					} else {
						doStep = doStep || wkfInterceptor.manualStart(readyActivity);
					}
				}
				ready.removeAll(temp);
			}
			
			if( !run.isEmpty() ){
				// Perform run activities and transit them to error or done state
				temp.clear();
				for(Iterator<WkfActivity> irun=run.iterator(); irun.hasNext(); ){
					WkfActivity runActivity = irun.next();

					wkfInterceptor.run(runActivity);

					doStep = true;
					
					try {
						switch( runActivity.getState().getImplementation() ){
						case Root:
							// root activities simply transit to done state
							temp.add(runActivity);
							doneAdd(runActivity);
							break;
						case NoImplementation:
							// Workflow logic are implemented vie manual start/completed
							temp.add(runActivity);
							doneAdd(runActivity);
							break;
						case Tool:
							// Invoke application
							temp.add(runActivity);
							wkfContext.run(runActivity);
							doneAdd(runActivity);
							break;
						case Loop:
						case LoopWhile:
							// TODO: internal workflow loop
							break;
						case LoopRepeatUntil:
							// TODO: internal workflow loop
							break;
						case SubFlow:
						case SubFlowSyncr:
							// TODO: internal workflow synchronous sub-flow
							break;
						case SubFlowAsyncr:
							// TODO: internal workflow asynchronous sub-flow
							break;
						}
					} catch(Exception e){
						run.remove(runActivity);
						error.add(runActivity);
						wkfInterceptor.exception(runActivity, e);
					}
				}
				run.removeAll(temp);
			}
			
			if( !done.isEmpty() ){
				// Process done activities to x-wait or ready state
				temp.clear();
				retemp.clear();
				for(Iterator<WkfActivity> idone=done.iterator(); idone.hasNext(); ){
					WkfActivity doneActivity = idone.next();
					// Manually finished activities stack here 
					if( doneActivity.getState().isAutoFinish() ){
						temp.add(doneActivity);
						if( !doSplit(doneActivity, retemp) ){
							trans.add(doneActivity);
						}
						// Check for completed activities one time
						readyAddAll(retemp);
						doStep = true;
					} else {
						doStep = doStep || wkfInterceptor.manualFinish(doneActivity);
					}
				}
				done.removeAll(temp);
			}

			if( !trans.isEmpty() ){
				// Check activities in transition state  
				temp.clear();
				for(Iterator<WkfActivity> itrans=trans.iterator(); itrans.hasNext(); ){
					WkfActivity transActivity = itrans.next();
					if( doSplit(transActivity, ready) ){
						temp.add(transActivity); // all target activities came to ready state inside
						doStep = true;
					}
				}
				trans.removeAll(temp);
			}
			
		}
		wkfInterceptor.intransition(trans);
	}

	/**
	 * @return
	 */
	public boolean isWorkflowFinished(){
		return ready.isEmpty() && run.isEmpty() && error.isEmpty() && done.isEmpty() && trans.isEmpty();
	}
	

	/*
	 * Private implementation 
	 * 
	 */
	
	private boolean checkJoin(WkfActivity target){
		if( target.getState().isJoinXor() )
			// TODO: probably here state/transition priorities can be involved
			// joint XOR is always true if XOR split is true and it is already true and finished
			return true; 
		else if( target.getState().isJoinAnd() )
			// XOR to AND transit if all AND transitions are true for target
 			return checkAndJoinTransition(target);
		else
			throw new RuntimeException("Unexpected join quantor ["+target.getState()+"]");
	}

	private boolean checkAndJoinTransition(WkfActivity target){
		WkfTransition[] transition = workflow.getJoinTransitions(target);
		if( transition != null ){
			for(int i=0; i<transition.length; i++ ){
				WkfActivity begin = transition[i].getBeginActivity();
				
				if( !isActivityCompleted(begin) )
					return false;
				else if( transition[i].isConditional() )
					if( !trueTansitions.contains(transition[i]) )
						if( !transition[i].evaluate(wkfContext) )
							return false;
						else if( begin.getState().isInstantiateOnce() )
							trueTansitions.add(transition[i]);
			}
		}
		return true;
	}

	private boolean doSplit(WkfActivity begin, Set<WkfActivity> next){
		if( begin.getState().isStop() )
			return true;
		else if( begin.getState().isSplitXor() )
			return doXorSplit(begin, next);
		else if( begin.getState().isSplitAnd() )
			return doAndSplit(begin, next);
		else
			throw new RuntimeException("Unexpected split state ["+begin.getState()+"]");
	}
	
	private boolean doXorSplit(WkfActivity begin, Set<WkfActivity> next){

		boolean XOR = false;
		int maxPriority = Integer.MIN_VALUE;
		WkfActivity joinActivity = null;
		
		WkfTransition[] transition = workflow.getSplitTransitions(begin);
		
		if( transition == null || transition.length == 0 )
			return true; // No splits from begin activity treats as end of process 
		
		WkfTransition otherwise = null;
		WkfActivity otherwiseActivity = null;
		
		boolean beginStateIsInstantiateOnce = begin.getState().isInstantiateOnce();
		
		for(int i=0; transition != null && i<transition.length; i++ ){
			if( transition[i].isConditional() )
				if( !trueTansitions.contains(transition[i]) )
					if( !transition[i].evaluate(wkfContext) )
						continue;
					else if( beginStateIsInstantiateOnce )
						trueTansitions.add(transition[i]);
				
			WkfActivity target = transition[i].getEndActivity();

			if( checkJoin(target) ){
				if( transition[i].isOtherwise() ){
					otherwise = transition[i];
					otherwiseActivity = target;
				} else if( target.getState().getPriority() > maxPriority ){
					XOR = true;
					maxPriority = target.getState().getPriority();
					joinActivity = target;
				}
			}
		}
		
		if( XOR ){
			next.add(joinActivity);
		} else if( otherwise != null ){
			next.add(otherwiseActivity);
			return true;
		}
		
		return XOR;
	}
	
	private boolean doAndSplit(WkfActivity begin, Set<WkfActivity> next){

		WkfTransition[] transition = workflow.getSplitTransitions(begin);
		for(int i=0; transition != null && i<transition.length; i++ ){
			if( transition[i].isConditional() )
				if( !trueTansitions.contains(transition[i]) )
					if( transition[i].evaluate(wkfContext) )
						trueTansitions.add(transition[i]);
					else
						continue;
			WkfActivity target = transition[i].getEndActivity();
			if( checkJoin(target) )
				next.add(target);
		}
		return !next.isEmpty();
	}

	// Completed activities life cycle
	private void readyAddAll(Set<WkfActivity> aset){
		aset.removeAll(completedOnce);
		ready.addAll(aset); 
		completed.removeAll(aset);
	}

	private void readyAdd(WkfActivity activity){
		// Prevent activity to be instantiated twice
		if( activity.getState().isInstantiateOnce())
			if( completedOnce.contains(activity) )
				return;
		ready.add(activity); 
		completed.remove(activity);
	}

	private void doneAdd(WkfActivity activity){
		done.add(activity); 
		if( activity.getState().isInstantiateOnce())
			completedOnce.add(activity);
		else
			completed.add(activity);
	}

	private boolean isActivityCompleted(WkfActivity activity){
		if( activity.getState().isInstantiateOnce())
			return completedOnce.contains(activity);
		else
			return completed.contains(activity);
	}
	
	private void clearStates(){
		// Cleaning activity states
		ready.clear(); 
		run.clear(); 
		error.clear(); 
		done.clear();
		trans.clear();
		completed.clear();
		completedOnce.clear();
		// Cleaning transition evaluating cache
		trueTansitions.clear();
	}
}
