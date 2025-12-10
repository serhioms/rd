package ca.mss.rd.flow.simple;

import ca.mss.rd.flow.ActivityBlock;
import ca.mss.rd.flow.ActivityType;
import ca.mss.rd.flow.IContext;

abstract public class RetriableActivity<PROP,CONTEXT extends IContext<PROP>> extends DisableEnableActivity<PROP,CONTEXT> {

	/* TODO: Stupid constructors */

	public RetriableActivity(long id) {
		super(id);
	}

	public RetriableActivity(String name) {
		super(name);
	}

	public RetriableActivity(ActivityType type) {
		super(type);
	}

	public RetriableActivity(ActivityType type, ActivityBlock block) {
		super(type, block);
	}

	public RetriableActivity(long id, DefaultWorkflow<PROP,CONTEXT> subwkf) {
		super(id, subwkf);
	}

	public RetriableActivity(long id, ActivityType type) {
		super(id, type);
	}


	/* retriable interface */
	
	private int maxRetryIfFail = 0;
	private long delayBetweenRetry = 0L;
	private long maxRetryDuration = 0L;
	private int retryCounter = 0;


	@Override
	public int getMaxRetryIfFail() {
		return maxRetryIfFail;
	}

	@Override
	public long getDelayBetweenRetry() {
		return delayBetweenRetry;
	}

	@Override
	public long getMaxRetryDuration() {
		return maxRetryDuration;
	}
	
	@Override
	public final void setMaxRetryIfFail(int maxRetryIfFail) {
		this.maxRetryIfFail = maxRetryIfFail;
	}

	@Override
	public final void setDelayBetweenRetry(long delayBetweenRetry) {
		this.delayBetweenRetry = delayBetweenRetry;
	}

	@Override
	public final void setMaxRetryDuration(long maxRetryDuration) {
		this.maxRetryDuration = maxRetryDuration;
	}
	
	/* implementation */
	
	@Override
	final public void executeJob() {
		/*
		 *  Set retry as late as possible to support lazy activity initialization.
		 *  When retry counter become zero then retry is ended.
		 */
		retryCounter = getMaxRetryIfFail();

		super.executeJob();
	}

	
	@Override
	protected boolean failedActivityHandler() {
		
		if( retryCounter-- > 0 ){

			getManager().retryNotify(this);

			/*
			 *  Retry activity means re-execute activity body trough queue not directly...
			 */
			getManager().queue(this); // not executeActivity();
			
			return ACTIVITY_RETRIED;
		} else {

			getManager().failedNotify(this);
			
			/* 
			 * No more retries... Activity finally failed... 
			 * Anyway, activity must be finalized (tools are already done)
			 */

			// TODO: Flow must be stopped run ASAP or continued running any valid stages
			return ACTIVITY_RETRY_FAILED_FINALIZE_FLOW;
		}
	}
}
