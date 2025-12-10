package ca.mss.rd.flow;

import ca.mss.rd.job.IJob;

public interface IWorkflow<PROP, CONTEXT extends IContext<PROP>> extends IJob {

	public String getExtCode();
	public void setExtCode(String code);
	public String getDescription();
	public void setDescription(String description);


	public void setManager(IFlowManager wkfman);

	public IActivity<PROP,CONTEXT>[] getStartActivities();
	void setStartActivity(IActivity<PROP,CONTEXT> startActivity);
	
	public IActivity<PROP,CONTEXT> getParentActivity();
	
	public String getPathId();
	public String getPathName();
	
	// Concurrency factors
	public void calcConcurencyFactors();
	public int getWCF();
	public int getACF();
	public int getTCF();

	// for convenience ?
	public boolean isRunning();
	public int getActivityCounter();

	// for clear, init, finish, etc
	void travers(ITraverse<PROP,CONTEXT> t);

	// Generalization...
	public void setParentActivity(IActivity<PROP,CONTEXT> a);
	public int calcActivityCounter(int length);
	public CONTEXT getContext();

	public IWorkflow<PROP,CONTEXT> workflow(IWorkflow<PROP,CONTEXT> wkf, IActivity<PROP,CONTEXT> a);
	public IActivity<PROP,CONTEXT> setNonBlock(IActivity<PROP,CONTEXT> a);

	public IActivity<PROP,CONTEXT> parallelSubWkf(@SuppressWarnings("unchecked") IActivity<PROP,CONTEXT>... aset);
	public IActivity<PROP,CONTEXT> sequentialSubWkf(@SuppressWarnings("unchecked") IActivity<PROP,CONTEXT>... aset);
	public IActivity<PROP,CONTEXT> parallelSet(@SuppressWarnings("unchecked") IActivity<PROP,CONTEXT>... aset);
	public IActivity<PROP,CONTEXT> sequentialSet(@SuppressWarnings("unchecked") IActivity<PROP,CONTEXT>... aset);

}
