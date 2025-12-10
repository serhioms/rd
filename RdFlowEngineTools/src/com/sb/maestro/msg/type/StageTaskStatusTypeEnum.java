package com.scotiabank.maestro.msg.type;


public enum StageTaskStatusTypeEnum {
	QUEUED("Task Execute Request Sent", false),
	IN_PROGRESS("Task Started and In Progress", false),
	FAILED_WILL_RETRY("Task has failed, will try again.", false),
	COMPLETED("Task Completed Successfully", true),
	FAILED("Task Completed with Error", true),
	FAILED_DUE_TO_COMM_FAILURE("Task Failed Due To Communication Failure", true),
	FAILED_USER_ABORTED("Task Failed - User Aborted", true),
	FAILED_DUE_TO_ORCHESTRATION_ERROR("Task Failed - Error within the Orchestrator", true),
	FAILED_DUE_TO_CONDITION("Task Failed - Stage's failure condition met", true),
	WARNING("Task Completed with Warning", true),
	MANUAL_RETRY("User has requested that we retry.", false),
	MANUAL_SKIP("User has requested that we skip this task.", true);
	
	private boolean isTaskComplete;
	private String desc;
	
	private StageTaskStatusTypeEnum(String desc, boolean isTaskComplete) {
		this.isTaskComplete=isTaskComplete;
		this.desc = desc;
	}
	public boolean isTaskComplete() {
		return isTaskComplete;
	}
	
	public boolean isRetry() {
		return FAILED_WILL_RETRY.equals(this);
	}
	
	public boolean isManualRetry() {
		return MANUAL_RETRY.equals(this);
	}

	public boolean inProgress() {
		return IN_PROGRESS.equals(this);
	}
	public boolean isQueued() {
		return QUEUED.equals(this);
	}
	public String getDesc() {
		return desc;
	}
	
	public boolean isTaskFailed() {
		return FAILED.equals(this) || FAILED_DUE_TO_COMM_FAILURE.equals(this) || FAILED_USER_ABORTED.equals(this)
				|| FAILED_DUE_TO_CONDITION.equals(this);
	}
	public boolean canManualRetry() {
		return FAILED.equals(this) || FAILED_DUE_TO_COMM_FAILURE.equals(this) || FAILED_DUE_TO_CONDITION.equals(this);
	}
	
	public boolean canAutoRetry() {
		return FAILED_DUE_TO_CONDITION.equals(this) || FAILED.equals(this);
	}
	
	public static StageTaskStatusTypeEnum[] getPermanentFailedStageTaskStatusTypes() {
		StageTaskStatusTypeEnum[] permanentFailedStageTaskStatusTypes = { FAILED, FAILED_DUE_TO_COMM_FAILURE, FAILED_DUE_TO_ORCHESTRATION_ERROR,
				FAILED_USER_ABORTED, FAILED_DUE_TO_CONDITION };

		return permanentFailedStageTaskStatusTypes;
	}
}
