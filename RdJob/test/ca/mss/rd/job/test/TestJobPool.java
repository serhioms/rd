package ca.mss.rd.job.test;

import static org.junit.Assert.fail;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import ca.mss.rd.job.JobPoolParallel;
import ca.mss.rd.job.JobPoolSequential;
import ca.mss.rd.util.Logger;

public class TestJobPool {

	private String msg = "";

	static JobPoolParallel<MockJob> jobp;
	static JobPoolSequential<MockJob> jobs;

	@BeforeClass
	static public void setUpBeforeClass() throws Exception {
		jobs = new JobPoolSequential<MockJob>();
		jobs.startPool();

		jobp = new JobPoolParallel<MockJob>();
		jobp.startPool();
	}
	
	@AfterClass
	static public void tiarDownAfterClass() throws Exception {
		jobs.shutdownPool(new MockJob(MockJob.SHUTDOWN));
		jobp.shutdownPool(new MockJob(MockJob.SHUTDOWN));
	}
	
	@Test
	public void RunParallelSuccessful() {
		try {
			for (int i = 0, size = jobp.getQueueSize(); i < size; i++) {
				jobp.queueJob(new MockJob());
			}
		} catch (Throwable t) {
			Logger.ERROR.printf(msg=String.format("RunParallelSuccessful: Failed run parallel job due to %s", t.getMessage()), t);
			fail(msg);
		}
	}

	@Test
	public void RunParallelFailure() {
		try {
			for (int i = -10, size=jobp.getQueueSize(); i < size; i++) {
				jobp.queueJob(new MockJob());
			}
			fail(msg = "Must be failed due to parallel queue full");
		} catch (Throwable t) {
			Logger.ERROR.printf(msg = String.format("RunParallelFailure: %s", t.getMessage()));
		}
	}

	@Test
	public void RunSequentialSuccess() {
		try {
			for (int i = 0, size = jobs.getQueueSize(); i < size; i++) {
				jobs.queueJob(new MockJob());
			}
		} catch (IllegalStateException t) {
			Logger.ERROR.printf(msg = String.format("RunSequentialSuccess: Failed run sequential job due to %s", t.getMessage()), t);
			fail(msg);
		}
	}

	@Test
	public void RunSequentialFailure() {
		try {
			for (int i = -10, size = jobs.getQueueSize(); i < size; i++) {
				if( jobs.isBusy() )
					Thread.sleep(10L);
				jobs.queueJob(new MockJob());
			}
			fail(msg = "Must be failed due to sequential queue full");
		} catch (Throwable t) {
			Logger.ERROR.printf(msg = String.format("RunSequentialFailure: %s",t.getMessage()));
		}
	}

	@Test
	public void RunAllJobs() {
		JobPoolParallel<MockJob> jobp = new JobPoolParallel<MockJob>();
		JobPoolSequential<MockJob> jobs = new JobPoolSequential<MockJob>();
		
		jobp.startPool();
		jobs.startPool();

		long sequentialId = 0; 
		long parallelId = 0; 
		
		try {
			for (int size = jobs.getQueueSize(), i=-size; i < size; i++) {
				if( jobp.isBusy() )
					Thread.sleep(10L);
				jobp.queueJob(new MockJob(++parallelId));
				
				if( jobs.isBusy() )
					Thread.sleep(10L);
				jobs.queueJob(new MockJob(++sequentialId));
			}
		} catch (Throwable t) {
			Logger.ERROR.printf(msg = String.format("RunFakedJobs: %s",t.getMessage()));
			fail(msg);
		} finally {
			jobs.shutdownPool(new MockJob(MockJob.SHUTDOWN));
			jobp.shutdownPool(new MockJob(MockJob.SHUTDOWN));
		}
	}

}
