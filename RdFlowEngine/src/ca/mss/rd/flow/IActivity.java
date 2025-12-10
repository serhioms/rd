package ca.mss.rd.flow;

import ca.mss.rd.job.IJob;


public interface IActivity<PROP, CONTEXT extends IContext<PROP>> extends IJob {

	@SuppressWarnings("rawtypes")
	static public final IActivity[] NO_ACTIVITY = new IActivity[] {};

	// Basic Activity Features
	public long getId();

	public void setName(String name);
	public String getName();
	
	public String getPathId();
	public String getPathName();

	public ActivityType getType();
	public void setType(ActivityType type);

	public boolean isSubflow();
	public IActivity<PROP,CONTEXT>[] getStartActivities();
	
	public IActivity<PROP,CONTEXT>[] getNextActivities();
	
	@SuppressWarnings("unchecked")
	public void setNextActivities(IActivity<PROP,CONTEXT>... activities);
	
	public void setActivitySet(IActivity<PROP,CONTEXT>[] set);
	public IActivity<PROP,CONTEXT>[] getActivitySet();
	
	public ITool[] getActivityTools();
	public void setActivityTools(ITool[] tools);
	public void setActivityTool(ITool t);

	public void visitor(ActivityVisitor visitor, IWorkflow<PROP,CONTEXT> wkf);
	public void task();

	public boolean inSubflow();
	public void setSubWkf(IWorkflow<PROP,CONTEXT> wkf);
	public IWorkflow<PROP,CONTEXT> getSubWkf();


	// Activity Features
	public boolean isBlock();
	public void setBlock(ActivityBlock activityBlock);

	// Extra Activity Features
	public boolean isDisable();
	public void setDisable(boolean isDisable);
	
	public boolean isActivitySkip();
	public boolean evaluateSkipCondition();
	public void setSkipCondition(String condition);
	public String getSkipCondition();
	
	public boolean isActivityFail();
	public boolean evaluateFailCondition();
	public void setFailCondition(String condition);
	public String getFailCondition();

	public String getFailConditionMessage();
	public void setFailConditionMessage(String message);
	
	public int getMaxRetryIfFail();
	public long getDelayBetweenRetry();
	public long getMaxRetryDuration();
	public void setMaxRetryIfFail(int  maxRetryIfFail);
	public void setDelayBetweenRetry(long delayBetweenRetry);
	public void setMaxRetryDuration(long maxRetryDuration);

	// Generalization...
	public CONTEXT getContext();
	
	void setWorkflow(IWorkflow<PROP,CONTEXT> wkf);
	
	void setManager(IFlowManager wkfman);
	IFlowManager getManager();
	void setContext(CONTEXT ctx);
	
	void finalizeActivity(ITool tool);
	void finalizeActivity(ITool tool, boolean isFireActivityEnd);

	int decrementToolCounter();

	void setParentActivity(IActivity<PROP,CONTEXT> a);
	int decrementSetCounter();
	
	// one more activity identifier
	public String getCode();
	public void setCode(String code);
	
	public int getLevel();

}
