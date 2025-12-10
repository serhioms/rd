package ca.mss.rd.job;


public class JobPoolSequential<P extends IJob> extends JobPoolParallel<P>{
	
	static final public int JOB_DEFAULT_SEQUENTIAL_EXECUTOR_SIZE = 1;

	public JobPoolSequential() {
		this("JobPoolSequential");
	}

	public JobPoolSequential(String ident) {
		super(ident, JOB_DEFAULT_SEQUENTIAL_EXECUTOR_SIZE);
	}

	public JobPoolSequential(String ident, int sequentialQueueSize) {
		super(String.format("%s:%d", ident, sequentialQueueSize), sequentialQueueSize, JOB_DEFAULT_SEQUENTIAL_EXECUTOR_SIZE);
	}
	
	public JobPoolSequential(String ident, int sequentialQueueSize, int priority) {
		super(String.format("%s:%d", ident, priority), sequentialQueueSize, JOB_DEFAULT_SEQUENTIAL_EXECUTOR_SIZE, priority);
	}


	@Override
	public void setExeSize(int exeSize) {
		throw new RuntimeException(String.format("Sequential executor job pool size predefined as %d", JOB_DEFAULT_SEQUENTIAL_EXECUTOR_SIZE));
	}

}
