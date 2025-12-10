package ca.mss.rd.flow.simple;

import java.util.concurrent.atomic.AtomicInteger;

import ca.mss.rd.flow.ActivityBlock;
import ca.mss.rd.flow.ActivityVisitor;
import ca.mss.rd.flow.IActivity;
import ca.mss.rd.flow.IContext;
import ca.mss.rd.flow.IFlowManager;
import ca.mss.rd.flow.ITraverse;
import ca.mss.rd.flow.IWorkflow;
import ca.mss.rd.flow.sp.basic.SPRootActivity;
import ca.mss.rd.flow.sp.basic.SPSetActivity;
import ca.mss.rd.job.AbstractJob;
import ca.mss.rd.util.Logger;
import ca.mss.rd.util.UtilDateTime;

public class DefaultWorkflow<PROP,CONTEXT extends IContext<PROP>> extends AbstractJob implements IWorkflow<PROP,CONTEXT> {
	
	static public final String NAME = "DefWorkflow";
	
	private String extCode, description;

	private IFlowManager wkfman;
	private IActivity<PROP,CONTEXT>[] startActivities;
	private IActivity<PROP,CONTEXT> parentActivity;
	private AtomicInteger activityCounter = new AtomicInteger(0);
	
	private volatile int wcf = 0, acf = 0, tcf = 0;
	
	public CONTEXT context;
	
	public DefaultWorkflow(CONTEXT context) {
		this();
		this.context = context;
	}

	public DefaultWorkflow() {
		super();
		setName(NAME);
	}

	public DefaultWorkflow(String name) {
		this();
		setName(name);
	}

	public DefaultWorkflow(long id) {
		super(id);
		setName(NAME);
	}

	public DefaultWorkflow(boolean isShutdown) {
		super(isShutdown);
		if( !isShutdown ){
			setName(NAME);
		}

	}

	@Override
	final public void setManager(IFlowManager wkfman) {
		this.wkfman = wkfman;
	}

	@Override
	public IActivity<PROP,CONTEXT>[] getStartActivities() {
		return startActivities;
	}

	@SuppressWarnings("unchecked")
	@Override
	final public void setStartActivity(IActivity<PROP,CONTEXT> startActivity) {
		this.startActivities = new IActivity[]{startActivity};
	}

	
	@Override
	final public void executeJob() {
		wkfman.executeNotify(this);
		
		IActivity<PROP,CONTEXT>[] startActivity = getStartActivities();
		
		calcActivityCounter(startActivity.length);
		
		for(int i=0; i<startActivity.length; i++ ){
			if( startActivity[i].isDisable() ){
				calcActivityCounter(-1);
			} else {
				startActivity[i].setManager(wkfman);
				startActivity[i].setWorkflow(this);
				startActivity[i].setContext(findoutContext());

				wkfman.queue( startActivity[i] );
			}
			
		}
	}

	final private CONTEXT findoutContext() {
		if( context != null )
			return context;
		
		if( parentActivity != null )
			if( parentActivity.getContext() != null )
				return parentActivity.getContext();
		
		return null;
	}

	@Override
	final public boolean isRunning() {
		return activityCounter.get() > 0;
	}

	@Override
	public int getActivityCounter() {
		return activityCounter.get();
	}

	@Override
	final public int calcActivityCounter(int length) {
		return activityCounter.addAndGet(length);
	}

	@Override
	final public void setParentActivity(IActivity<PROP,CONTEXT> parentActivity) {
		this.parentActivity = parentActivity;
	}

	@Override
	final public IActivity<PROP,CONTEXT> getParentActivity() {
		return parentActivity;
	}

	@Override
	final public String getPathId() {
		return parentActivity==null? String.format("%d", getId()): String.format("%s.%d", parentActivity.getPathId(), getId());
	}

	@Override
	final public String getPathName() {
		return parentActivity==null? String.format("%s", getName()): String.format("%s.%s", parentActivity.getPathName(), getName());
	}

	@Override
	final public int getWCF() {
		return wcf;
	}

	@Override
	final public int getACF() {
		return acf;
	}

	@Override
	final public int getTCF() {
		return tcf;
	}
	
	@Override
	final public void calcConcurencyFactors(){
		calcCF(getStartActivities());
	}

	@Override
	public CONTEXT getContext() {
		return context;
	}

	final private void calcCF(IActivity<PROP,CONTEXT>[] activitySet) {
		if( activitySet != null ){
			
			//Logger.ERROR.print("Activities [%d]", activitySet.length);
			
			for(int i=0; i<activitySet.length; i++){
				activitySet[i].setWorkflow(this);
				//assert( Logger.DEBUG.isOn ? Logger.DEBUG.printf("Activity#%-4d %s[%s]{%d}", activitySet[i].getId(), activitySet[i].getName(), activitySet[i].getType(), activitySet[i].getNextActivities().length): true);
				calcCF(activitySet[i].getNextActivities());
			}
		}
	}

	@Override
	public void travers(ITraverse<PROP,CONTEXT> t){
		travers(t, this, getStartActivities());
	}

	public void travers(ITraverse<PROP,CONTEXT> t, IWorkflow<PROP,CONTEXT> wkf, IActivity<PROP,CONTEXT>[] arr){
		if( arr != null && arr.length > 0 ){
			for(IActivity<PROP,CONTEXT> activity: arr ){
				
				activity.setWorkflow(wkf);
				
				if( activity.isSubflow() ){
					
					IWorkflow<PROP, CONTEXT> subWkf = activity.getSubWkf();
					subWkf.setParentActivity(activity);

					// this activity belongs parent flow
					t.visitor(activity, wkf);
					// all next activities belongs subflow
					travers(t, subWkf, activity.getStartActivities());
				} else {
					t.visitor(activity, wkf);
				}
				travers(t, wkf, activity.getActivitySet());
				travers(t, wkf, activity.getNextActivities());
			}
		}
	}

	/*
	 * Useful methods
	 */

	@Override
	public IWorkflow<PROP,CONTEXT> workflow(IWorkflow<PROP,CONTEXT> wkf, IActivity<PROP,CONTEXT> start) {
		wkf.setStartActivity(start);
		return wkf;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public IActivity<PROP,CONTEXT> sequentialSet(IActivity<PROP,CONTEXT>... activitySet) {
		
		SPSetActivity<PROP,CONTEXT> set = new SPSetActivity<PROP,CONTEXT>();

		if( activitySet.length > 0 ){
			set.setName("SequentialSet");
			for(int i=1; i<activitySet.length; i++){
				activitySet[i-1].setNextActivities(new IActivity[]{activitySet[i]});
			}
	
			set.setActivitySet( new IActivity[]{activitySet[0]} );
		}
		
		return set; 
	}


	@SuppressWarnings("unchecked")
	@Override
	public IActivity<PROP,CONTEXT> parallelSet(IActivity<PROP,CONTEXT>... activitySet) {
		
		SPSetActivity<PROP,CONTEXT> set = new SPSetActivity<PROP,CONTEXT>();
		
		if( activitySet.length > 0 ){
			set.setName("ParallelSet");
			set.setActivitySet( activitySet );
		}
		
		return set; 
	}

	@SuppressWarnings("unchecked")
	@Override
	public IActivity<PROP,CONTEXT> sequentialSubWkf(IActivity<PROP,CONTEXT>... activitySet) {

		SPRootActivity<PROP,CONTEXT> root = new SPRootActivity<PROP,CONTEXT>();
		
		root.setName("RootSequentialSubWkf");
		root.setSubWkf(workflow(new DefaultWorkflow<PROP,CONTEXT>("SequentialSubWkf"), sequentialSet(activitySet)));
		
		return root; 
	}

	@SuppressWarnings("unchecked")
	@Override
	public IActivity<PROP,CONTEXT> parallelSubWkf(IActivity<PROP,CONTEXT>... activitySet) {
		
		SPRootActivity<PROP,CONTEXT> root = new SPRootActivity<PROP,CONTEXT>();
		
		root.setName("RootParallelSubWkf");

		root.setSubWkf(workflow(new DefaultWorkflow<PROP,CONTEXT>("ParallelSubWkf"), parallelSet(activitySet)));

		return root;
	}

	@Override
	public IActivity<PROP,CONTEXT> setNonBlock(IActivity<PROP,CONTEXT> a) {
		a.setBlock(ActivityBlock.NonBlock);
		return a;
	}

	@Override
	public String getExtCode() {
		return extCode;
	}

	@Override
	public void setExtCode(String extCode) {
		this.extCode = extCode;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public void setDescription(String description) {
		this.description = description;
	}

	
	public void clearFlow() {
		assert( Logger.WORKFLOW_VERBOSE.isOn ? Logger.WORKFLOW_VERBOSE.printf("%s[%d].clearFlow on %s", getName(), getId(), UtilDateTime.rightnow()): true);
		
		context.clear();
		
		travers(new ITraverse<PROP,CONTEXT>() {
			@Override
			public void visitor(IActivity<PROP,CONTEXT> activity, IWorkflow<PROP,CONTEXT> wkf) {
				activity.visitor(ActivityVisitor.clean, DefaultWorkflow.this);
			}
		});
	}

	public void stopFlow() {
		assert( Logger.WORKFLOW_VERBOSE.isOn ? Logger.WORKFLOW_VERBOSE.printf("%s[%d].stopFlow on %s", getName(), getId(), UtilDateTime.rightnow()): true);
		travers(new ITraverse<PROP,CONTEXT>() {
			@Override
			public void visitor(IActivity<PROP,CONTEXT> activity, IWorkflow<PROP,CONTEXT> wkf) {
				activity.visitor(ActivityVisitor.stop, DefaultWorkflow.this);
			}
		});
	}


}