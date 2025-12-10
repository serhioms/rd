package ca.mss.rd.job.test;

import ca.mss.rd.job.AbstractJob;

public class MockJob extends AbstractJob {

	public MockJob() {
		super();
	}

	public MockJob(boolean isShutdown) {
		super(isShutdown);
	}

	public MockJob(long id) {
		super(id);
	}

	public MockJob(long jobId, boolean isShutdown) {
		super(jobId, isShutdown);
	}

	@Override
	public String getName() {
		return "MockJob";
	}

	@Override
	public void executeJob() {
		// do nothing
	}

}
