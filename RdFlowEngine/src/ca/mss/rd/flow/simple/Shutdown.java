package ca.mss.rd.flow.simple;

import ca.mss.rd.flow.ActivityBlock;
import ca.mss.rd.flow.ActivityType;
import ca.mss.rd.flow.ActivityVisitor;
import ca.mss.rd.flow.IActivity;
import ca.mss.rd.flow.IContext;
import ca.mss.rd.flow.IFlowManager;
import ca.mss.rd.flow.ITool;
import ca.mss.rd.flow.ITraverse;
import ca.mss.rd.flow.IWorkflow;
import ca.mss.rd.job.AbstractJob;


@SuppressWarnings("unchecked") 
public class Shutdown<PROP, CONTEXT extends IContext<PROP>>  extends AbstractJob implements IWorkflow<PROP,CONTEXT>, IActivity<PROP,CONTEXT>, ITool {

	public Shutdown() {
		super(AbstractJob.SHUTDOWN);
	}

	@Override
	public String getExtCode() {
		return null;
	}

	@Override
	public void setExtCode(String code) {
	}

	@Override
	public String getDescription() {
		return null;
	}

	@Override
	public void setDescription(String description) {
	}

	@Override
	public void calcConcurencyFactors() {
	}

	@Override
	public int getWCF() {
		return 0;
	}

	@Override
	public int getACF() {
		return 0;
	}

	@Override
	public int getTCF() {
		return 0;
	}

	@Override
	public boolean isRunning() {
		return false;
	}

	@Override
	public int getActivityCounter() {
		return 0;
	}

	@Override
	public void travers(ITraverse<PROP,CONTEXT> t) {
	}

	@Override
	public int calcActivityCounter(int length) {
		return 0;
	}

	@Override
	public boolean isSync() {
		return false;
	}

	@Override
	public String getPath() {
		return null;
	}

	@Override
	public void visitor(ActivityVisitor visitor, IWorkflow<PROP,CONTEXT> wkf) {
	}

	@Override
	public void task() {
	}

	@Override
	public void executeJob() {
		
		
	}

	@Override
	public ActivityType getType() {
		
		return null;
	}

	@Override
	public void setType(ActivityType type) {
		
		
	}

	@Override
	public boolean isSubflow() {
		
		return false;
	}

	@Override
	public ITool[] getActivityTools() {
		
		return null;
	}

	@Override
	public void setActivityTools(ITool[] tools) {
		
		
	}

	@Override
	public void setActivityTool(ITool t) {
		
		
	}

	@Override
	public boolean inSubflow() {
		
		return false;
	}

	@Override
	public boolean isBlock() {
		
		return false;
	}

	@Override
	public void setBlock(ActivityBlock activityBlock) {
		
		
	}

	@Override
	public boolean isDisable() {
		
		return false;
	}

	@Override
	public void setDisable(boolean isDisable) {
		
		
	}

	@Override
	public boolean isActivitySkip() {
		
		return false;
	}

	@Override
	public boolean evaluateSkipCondition() {
		
		return false;
	}

	@Override
	public void setSkipCondition(String condition) {
		
		
	}

	@Override
	public String getSkipCondition() {
		
		return null;
	}

	@Override
	public boolean isActivityFail() {
		
		return false;
	}

	@Override
	public boolean evaluateFailCondition() {
		
		return false;
	}

	@Override
	public void setFailCondition(String condition) {
		
		
	}

	@Override
	public String getFailCondition() {
		
		return null;
	}

	@Override
	public String getFailConditionMessage() {
		
		return null;
	}

	@Override
	public void setFailConditionMessage(String message) {
		
		
	}

	@Override
	public int getMaxRetryIfFail() {
		
		return 0;
	}

	@Override
	public long getDelayBetweenRetry() {
		
		return 0;
	}

	@Override
	public long getMaxRetryDuration() {
		
		return 0;
	}

	@Override
	public void setMaxRetryIfFail(int maxRetryIfFail) {
		
		
	}

	@Override
	public void setDelayBetweenRetry(long delayBetweenRetry) {
		
		
	}

	@Override
	public void setMaxRetryDuration(long maxRetryDuration) {
		
		
	}

	@Override
	public void finalizeActivity(ITool tool) {
		
		
	}

	@Override
	public void finalizeActivity(ITool tool, boolean isFireActivityEnd) {
		
		
	}

	@Override
	public int decrementToolCounter() {
		
		return 0;
	}

	@Override
	public int decrementSetCounter() {
		
		return 0;
	}

	@Override
	public String getCode() {
		
		return null;
	}

	@Override
	public void setCode(String code) {
		
		
	}

	@Override
	public void setManager(IFlowManager wkfman) {
		
		
	}

	@Override
	public String getPathId() {
		
		return null;
	}

	@Override
	public String getPathName() {
		
		return null;
	}

	@Override
	public int getLevel() {
		return 0;
	}

	@Override
	public void executeTool() {
	}

	@Override
	public IActivity<PROP,CONTEXT>[] getNextActivities() {
		return null;
	}

	@Override
	public void setNextActivities(IActivity<PROP,CONTEXT>... activities) {
	}

	@Override
	public void setActivitySet(IActivity<PROP,CONTEXT>[] set) {
	}

	@Override
	public IActivity<PROP,CONTEXT>[] getActivitySet() {
		return null;
	}

	@Override
	public void setSubWkf(IWorkflow<PROP,CONTEXT> wkf) {
	}

	@Override
	public void setWorkflow(IWorkflow<PROP,CONTEXT> wkf) {
	}

	@Override
	public IFlowManager getManager() {
		return null;
	}

	@Override
	public void setContext(CONTEXT ctx) {
	}

	@Override
	public IActivity<PROP,CONTEXT>[] getStartActivities() {
		return null;
	}

	@Override
	public void setStartActivity(IActivity<PROP,CONTEXT> startActivity) {
	}

	@Override
	public IActivity<PROP,CONTEXT> getParentActivity() {
		return null;
	}

	@Override
	public void setParentActivity(IActivity<PROP,CONTEXT> a) {
	}

	@Override
	public CONTEXT getContext() {
		return null;
	}

	@Override
	public IWorkflow<PROP,CONTEXT> workflow(IWorkflow<PROP,CONTEXT> wkf, IActivity<PROP,CONTEXT> a) {
		return null;
	}

	@Override
	public IActivity<PROP,CONTEXT> setNonBlock(IActivity<PROP,CONTEXT> a) {
		return null;
	}

	@Override
	public IActivity<PROP,CONTEXT> parallelSubWkf(IActivity<PROP,CONTEXT>... aset) {
		return null;
	}

	@Override
	public IActivity<PROP,CONTEXT> sequentialSubWkf(IActivity<PROP,CONTEXT>... aset) {
		return null;
	}

	@Override
	public IActivity<PROP,CONTEXT> parallelSet(IActivity<PROP,CONTEXT>... aset) {
		return null;
	}

	@Override
	public IActivity<PROP,CONTEXT> sequentialSet(IActivity<PROP,CONTEXT>... aset) {
		return null;
	}

	@Override
	public IWorkflow<PROP,CONTEXT> getSubWkf() {
		return null;
	}

	
}

