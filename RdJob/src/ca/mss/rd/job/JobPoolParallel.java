package ca.mss.rd.job;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import ca.mss.rd.util.Logger;

public class JobPoolParallel<P extends IJob> extends JobPoolAbstract<P>{
	
	static public int JOB_DEFAULT_PARALLEL_QUEUE_SIZE = 100;
	static public int JOB_DEFAULT_PARALLEL_EXECUTOR_SIZE = 10;

	final private BlockingQueue<P> queue;
	private int queueSize, exeSize;

	private ExecutorService executor;
	
	public JobPoolParallel() {
		this(JOB_DEFAULT_PARALLEL_QUEUE_SIZE, JOB_DEFAULT_PARALLEL_EXECUTOR_SIZE);
	}

	public JobPoolParallel(int parallelQueueSize, int parallelExeSize) {
		this(String.format("JobPoolParallel:%d:%d", parallelQueueSize, parallelExeSize), parallelQueueSize, parallelExeSize);
	}

	public JobPoolParallel(String ident, int parallelExeSize) {
		this(ident, JOB_DEFAULT_PARALLEL_QUEUE_SIZE, parallelExeSize);
	}

	public JobPoolParallel(String ident, int parallelQueueSize, int parallelExeSize) {
		super(ident);
		this.queueSize = parallelQueueSize;
		this.exeSize = parallelExeSize;
		this.queue = queueSize > 0? new LinkedBlockingQueue<P>(queueSize): null;
	}

	public JobPoolParallel(String ident, int parallelQueueSize, int parallelExeSize, int priority) {
		super(ident, priority);
		this.queueSize = parallelQueueSize;
		this.exeSize = parallelExeSize;
		this.queue = new LinkedBlockingQueue<P>(queueSize);
	}

	@Override
	public boolean isBusy() {
		return queue.size() ==  queueSize;
	}

	@Override
	public boolean isRunning() {
		return queue != null && !queue.isEmpty();
	}

	@Override
	public int size() {
		return queue.size();
	}

	@Override
	public void queueJob(P prop) {
		if( prop.getId() == -1 )
		assert( Logger.JOB_POOL_VERBOSE.isOn ? Logger.JOB_POOL_VERBOSE.printf("%s[%d].queueJob", prop.getName(), prop.getId()): true);
		queue.add(prop);
	}

	@Override
	public void startPool(){

		// Not a good idea... There are scenarios to populate to pool first then start the pool.
//		if( queueSize > 0 ){
//			queue.clear();
//		}
		if( exeSize > 0 ){
	        RejectedExecutionHandlerImpl rejectionHandler = new RejectedExecutionHandlerImpl();
	        
            // TODO: Take a look on pool initialization
            int WAIT_TASK_LIMIT = exeSize, CORE_POOL_SIZE = exeSize, MAXIMUM_POOL_SIZE = exeSize, KEEP_ALIVE_TIME_SEC = 360, REPORT_DELAY_SEC = 10;
            ArrayBlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<Runnable>(WAIT_TASK_LIMIT);

            //creating the ThreadPoolExecutor
            if( exeSize == 1 )
            	executor = Executors.newSingleThreadExecutor(getThreadFactory());
            else
            	executor = new ThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE_TIME_SEC, TimeUnit.SECONDS,
            		workQueue, getThreadFactory(), rejectionHandler);
            
            //start the monitoring thread
            if( Logger.THREAD.isOn ){
	            MyMonitorThread monitor = new MyMonitorThread(executor, REPORT_DELAY_SEC);
	            Thread monitorThread = new Thread(monitor);
	            monitorThread.setName(String.format("ThreadMonitor-%s", ident));
	            monitorThread.start();
            }
		}

		for (int i=0; i < exeSize; i++){
			startExecutor(executor, queue);
		}
	}

	@Override
	public void shutdownPool(P jobShutdown){
		shutdown(executor, exeSize, queue, jobShutdown);
	}
	
	@Override
	public final int getQueueSize() {
		return queueSize;
	}

	@Override
	public final void setQueueSize(int queueSize) {
		this.queueSize = queueSize;
	}

	@Override
	public final int getExeSize() {
		return exeSize;
	}

	@Override
	public void setExeSize(int exeSize) {
		this.exeSize = exeSize;
	}

	static public class RejectedExecutionHandlerImpl implements RejectedExecutionHandler {

		@Override
	    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
	        assert( Logger.THREAD.isOn? Logger.THREAD.printf("%s is rejected", r.toString()): true);
	    }
	}
	
	static public class MyMonitorThread implements Runnable {
		private ExecutorService executor;
		private int seconds;
		private boolean run = true;

		public MyMonitorThread(ExecutorService executor, int delaySec) {
			this.executor = executor;
			this.seconds = delaySec;
		}

		public void shutdown() {
			this.run = false;
		}

		@Override
	    public void run()
	    {
			if( executor instanceof ThreadPoolExecutor ){
				ThreadPoolExecutor tpexecutor = (ThreadPoolExecutor )executor;
		        while(run){
		        	
		        	assert( Logger.THREAD.isOn ? Logger.THREAD.printf("[monitor] [%d/%d] Active: %d, Completed: %d, Task: %d, isShutdown: %s, isTerminated: %s",
	                    tpexecutor.getPoolSize(),
	                    tpexecutor.getCorePoolSize(),
	                    tpexecutor.getActiveCount(),
	                    tpexecutor.getCompletedTaskCount(),
	                    tpexecutor.getTaskCount(),
	                    tpexecutor.isShutdown(),
	                    tpexecutor.isTerminated()): true);
		        	
	                try {
	                    Thread.sleep(seconds*1000);
	                } catch (InterruptedException e) {
	                	if( Logger.ERROR.isOn ) Logger.ERROR.printf(e);
	                }
		        }
			}
	    }
	}
}
