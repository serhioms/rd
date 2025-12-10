package ca.mss.rd.flow.simple;

import java.util.concurrent.atomic.AtomicInteger;

import ca.mss.rd.flow.ActivityBlock;
import ca.mss.rd.flow.ActivityType;
import ca.mss.rd.flow.ActivityVisitor;
import ca.mss.rd.flow.IActivity;
import ca.mss.rd.flow.IContext;
import ca.mss.rd.flow.IFlowManager;
import ca.mss.rd.flow.ITool;
import ca.mss.rd.flow.IWorkflow;
import ca.mss.rd.job.AbstractJob;

abstract public class DefaultActivity<PROP,CONTEXT extends IContext<PROP>> extends AbstractJob implements IActivity<PROP,CONTEXT> {

	static public final String NAME = "DefActivity";

	static public final boolean FIRE_ACTIVITY_END = true;

	static public final boolean SKIP_ACTIVITY_END = false;
	static public final ITool SKIP_ACTIVITY_TOOL = null;
	
	static public final boolean ACTIVITY_RETRIED = false;
	static public final boolean ACTIVITY_RETRY_FAILED_FINALIZE_FLOW = true;

	/* TODO: Stupid constructors */
	
	public DefaultActivity(String name) {
		super(name);
	}

	public DefaultActivity(long id) {
		this(id, ActivityType.Tool);
	}

	public DefaultActivity(long id, ActivityType type) {
		super(id);
		this.type = type;
	}

	public DefaultActivity(ActivityType type) {
		super();
		this.type = type;
	}

	public DefaultActivity(ActivityType type, ActivityBlock block) {
		super();
		this.type = type;
		this.block = block;
	}

	public DefaultActivity(long id, DefaultWorkflow<PROP,CONTEXT> subwkf) {
		super(id);
		this.subwkf = subwkf;
		this.type = ActivityType.SubFlow;
	}

	/* default interface */
	private String code;
	
	private ActivityType type;
	private ActivityBlock block = ActivityBlock.FullBlock;

	private IFlowManager wkfman;
	protected IWorkflow<PROP,CONTEXT> wkf;
	private IWorkflow<PROP,CONTEXT> subwkf;
	private IActivity<PROP,CONTEXT> parentActivity;
	
	private AtomicInteger toolCounter = new AtomicInteger(0);
	private AtomicInteger setCounter = new AtomicInteger(0);
	
	private IActivity<PROP,CONTEXT>[] nextActivities = null;
	private ITool[] tools = null;
	private IActivity<PROP,CONTEXT>[] activitySet = null;
	
	protected CONTEXT context;

	/* getters/setters */
	
	
	@Override
	public String getCode() {
		return code;
	}

	@Override
	public int getLevel() {
		throw new RuntimeException(String.format("Level not implemented for %s", getName()));
	}

	@Override
	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public boolean inSubflow(){
		return wkf.getParentActivity() != null;
	}
	
	@Override
	public final void setBlock(ActivityBlock block) {
		this.block = block;
	}

	@Override
	public String getPathId(){
		return String.format("%s.%d", wkf.getPathId(), getId());
	}
	
	@Override
	public String getPathName(){
		return String.format("%s.%s", wkf.getPathName(), getName());
	}
	
	@Override
	public ITool[] getActivityTools() {
		return tools;
	}

	@Override
	public void setActivityTools(ITool[] tools) {
		this.tools = tools;
	}

	@Override
	public void setActivityTool(ITool t) {
		setActivityTools(new ITool[]{t});
	}
	
	@Override
	public final void setType(ActivityType type) {
		this.type = type;
	}

	@Override
	final public ActivityType getType() {
		return type;
	}

	@Override
	public final boolean isSubflow() {
		return subwkf != null;
	}

	protected final void setWorkflow(DefaultWorkflow<PROP,CONTEXT> wkf) {
		this.wkf = wkf;
	}

	public final void setContext(CONTEXT context) {
		if( context == null ){
			throw new RuntimeException("Context is NULL!");
		}
		this.context = context;
	}

	@Override
	public final void setManager(IFlowManager wkfman) {
		this.wkfman = wkfman;
	}

	@Override
	public IFlowManager getManager() {
		return wkfman;
	}

	@Override
	public void setSubWkf(IWorkflow<PROP,CONTEXT> subwkf) {
		this.subwkf = subwkf;
		this.type = ActivityType.SubFlow;
	}

	@Override
	public IWorkflow<PROP,CONTEXT> getSubWkf() {
		return subwkf;
	}

	@Override
	public final IActivity<PROP,CONTEXT>[] getStartActivities() {
		return subwkf.getStartActivities();
	}
	
	@Override
	public IActivity<PROP,CONTEXT>[] getActivitySet() {
		return activitySet;
	}

	@Override
	public final void setActivitySet(IActivity<PROP,CONTEXT>[] activitySet) {
		if( this.activitySet != null ){
			throw new RuntimeException(String.format("Can not assign set %d for %s cause it is already initialized by %d", activitySet.length, getName(), this.activitySet.length));
		}
		this.activitySet = activitySet;
	}

	@SuppressWarnings("unchecked")
	@Override
	public IActivity<PROP,CONTEXT>[] getNextActivities() {
		if( nextActivities != null )
			return nextActivities;
		else
			return IActivity.NO_ACTIVITY;
	}

	@SuppressWarnings("unchecked")
	@Override
	public final void setNextActivities(IActivity<PROP,CONTEXT>... nextActivities) {
		if( this.nextActivities != null ){
			throw new RuntimeException(String.format("Can not assign next %d for %s cause it is already initialized by %d", activitySet.length, getName(), this.activitySet.length));
		}
		this.nextActivities = nextActivities;
	}

	/* 
	 * Must be overridden for handling activity failure in future implementations 
	 */
	protected boolean failedActivityHandler(){
		return ACTIVITY_RETRY_FAILED_FINALIZE_FLOW;
	}
	
	
	/* Actual implementation */
	
	protected void executeActivity() {

		wkfman.executeNotify(this);

		switch( type ){
		case None:
			finalizeActivity( null ); // No implementation - no tools
		case Route:
			finalizeActivity( null ); // No implementation - no tools
			break;
			
		case Task:
			task();					  // Implementation inside activity	
			finalizeActivity( null ); // No implementation - no tools
			break;
			
		case ActivitySet:
			IActivity<PROP,CONTEXT>[] set = getActivitySet();
			if( set != null ){
				
				setCounter.set( set.length ); 			// Activity set counter for catch last one on zero
				wkf.calcActivityCounter( set.length );	// Same story for flow...
				
				for(int i=0; i < set.length; i++) {
					if( set[i].isDisable() ){
						setCounter.decrementAndGet();	// Decrement due to some of counted - disabled
						wkf.calcActivityCounter(-1);
					} else {
						set[i].setManager(wkfman);
						set[i].setWorkflow(wkf);
						set[i].setContext(findoutContext());
						set[i].setParentActivity(this);
						wkfman.queue( set[i] );
					}
				}
			}
			break;
			
		case Tool:
			ITool[] tools = getActivityTools();
			if( tools.length > 0 ){
				
				toolCounter.set(tools.length); // Tools counter for catch last one on zero
				
				for(int i=0; i < tools.length; i++) {
					wkfman.queue( tools[i] );
				}
			} else {
				// No tools... Activity must be finalized.
				finalizeActivity( null );
			}
			break;
			
		case SubFlow:
			if( subwkf == null ){
				throw new RuntimeException(String.format("Subflow not defined for %s[id=%s]", getName(), getId()));
			}
			
			switch(block){
			case FullBlock:
				subwkf.setParentActivity(this);
				break;
			case NonBlock:
				break;
			}
			
			wkfman.queue(subwkf);
			break;
			
		default:
			throw new RuntimeException(String.format("Sorry, %s not implemented yet.", type));
		}
	}

	/* Stubs activity */

	@Override
	public boolean isBlock() {
		return true;
	}

	@Override
	public int decrementToolCounter() {
		return toolCounter.decrementAndGet();
	}


	@Override
	public int decrementSetCounter() {
		return setCounter.decrementAndGet();
	}

	@Override
	public final void finalizeActivity(final ITool tool) {
		finalizeActivity(tool, FIRE_ACTIVITY_END);
	}
	
	@Override
	public final void finalizeActivity(ITool tool, boolean isFireActivityEnd) {

		// check if activity failed and hook it up
		if( evaluateFailCondition() ){
			if( failedActivityHandler() ){
				if( wkf.calcActivityCounter(-1) == 0 ){
					// last activity must finish the flow
					wkfman.endNotify(wkf);
					
					// and finalize subflow activity
					if( inSubflow() ){
						wkf.getParentActivity().finalizeActivity( null );
					}
				}
			}
		} else {

			// in case of skip/disabled/retry
			if( isFireActivityEnd ){
				wkfman.endNotify(this);
			}

			final IActivity<PROP,CONTEXT>[] nextActivity = getNextActivities();
			
			// check if activity processing not finished (some of disabled activity processed as well)
			if( wkf.calcActivityCounter(nextActivity.length-1) == 0 ){
				// last activity must finish the flow
				wkfman.endNotify(wkf);
				
				// and finalize subflow activity
				if( inSubflow() ){
					wkf.getParentActivity().finalizeActivity( null );
				}
			} 
			
			if( nextActivity.length > 0 ){
				for(int i=0; i<nextActivity.length; i++){
					
					nextActivity[i].setManager(wkfman);
					nextActivity[i].setWorkflow(wkf);
					nextActivity[i].setContext(findoutContext());

					if( parentActivity != null ){
						nextActivity[i].setParentActivity(parentActivity);
						setCounter.incrementAndGet(); // Activity set counter for catch last one on zero
					}	

					if( nextActivity[i].isDisable() ){
						nextActivity[i].finalizeActivity(SKIP_ACTIVITY_TOOL, SKIP_ACTIVITY_END);
					} else {
						wkfman.queue( nextActivity[i] );
					}
				}
			} else if( parentActivity != null ){
				// ActivitySet finalizing here same way as tool
				if( parentActivity.decrementSetCounter() == 0 ){
					parentActivity.finalizeActivity( null );
				}
			} 
		}
	}

	private CONTEXT findoutContext() {
		if( context != null )
			return context;
		
		if( wkf.getContext() != null )
			return context;

		return null;
	}

	@Override
	public CONTEXT getContext() {
		return context;
	}


	@Override
	public void setWorkflow(IWorkflow<PROP,CONTEXT> wkf) {
		this.wkf = wkf;
	}
	
	@Override
	public void setParentActivity(IActivity<PROP,CONTEXT> parentActivity) {
		if( this.parentActivity != null ){
			throw new RuntimeException(String.format("Can not assign parent %s for %s cause it is already initialized by %s", parentActivity.getName(), getName(), this.parentActivity.getName()));
		}
		this.parentActivity = parentActivity;
	}

	/*
	 * Implement Job Interface
	 */
	@Override
	public void executeJob() {
		if( evaluateSkipCondition() ){
			finalizeActivity(SKIP_ACTIVITY_TOOL, SKIP_ACTIVITY_END);
		} else {
			executeActivity();
		}
	}

	@Override
	public void visitor(ActivityVisitor visitor, IWorkflow<PROP,CONTEXT> wkf) {
		throw new RuntimeException(String.format("Visitor not implemented!"));
	}

	@Override
	public void task() {
		if( type != ActivityType.Task ){
			throw new RuntimeException(String.format("Task not implemented!"));
		} else {
			throw new RuntimeException(String.format("Must not be implemented for %s activities!", this.getClass().getSimpleName()));
		}
	}
}

