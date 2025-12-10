package ca.mss.rd.flow.simple;

import ca.mss.rd.flow.ActivityBlock;
import ca.mss.rd.flow.ActivityType;
import ca.mss.rd.flow.IContext;

abstract public class DisableEnableActivity<PROP,CONTEXT extends IContext<PROP>> extends DefaultActivity<PROP,CONTEXT> {

	/* TODO: Stupid constructors */

	public DisableEnableActivity(String name) {
		super(name);
	}

	public DisableEnableActivity(long id) {
		super(id);
	}

	public DisableEnableActivity(ActivityType type) {
		super(type);
	}

	public DisableEnableActivity(ActivityType type, ActivityBlock block) {
		super(type, block);
	}

	public DisableEnableActivity(long id, DefaultWorkflow<PROP,CONTEXT> subwkf) {
		super(id, subwkf);
	}

	public DisableEnableActivity(long id, ActivityType type) {
		super(id, type);
	}

	
	/* Disable/Enable interface */
	
	private boolean isDisable = false;
	private boolean isSkip = false;
	private String skipCondition;
	private boolean isFail = false;
	private String failCondition, failConditionMessage;

	
	@Override
	public boolean isDisable() {
		return isDisable;
	}

	@Override
	public void setDisable(boolean isDisable) {
		this.isDisable = isDisable;
	}

	@Override
	public boolean isActivitySkip() {
		return isSkip;
	}

	@Override
	public boolean evaluateSkipCondition() {
		if( skipCondition == null )
			return isSkip=false;
		else
			return isSkip=false; // TODO: Evaluate skip condition here
	}

	@Override
	public void setSkipCondition(String skipCondition) {
		this.skipCondition = skipCondition;
	}

	@Override
	public String getSkipCondition() {
		return skipCondition;
	}

	@Override
	public boolean isActivityFail() {
		return isFail;
	}

	@Override
	public boolean evaluateFailCondition() {
		if( failCondition == null )
			return isFail=false;
		else
			return isFail=false; // TODO: Evaluate skip condition here
	}

	@Override
	public void setFailCondition(String failCondition) {
		this.failCondition = failCondition;
	}

	@Override
	public String getFailCondition() {
		return failCondition;
	}

	@Override
	public String getFailConditionMessage() {
		return failConditionMessage;
	}

	@Override
	public void setFailConditionMessage(String failConditionMessage) {
		this.failConditionMessage = failConditionMessage;
	}
}
