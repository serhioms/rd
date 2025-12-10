package ca.mss.rd.job;

import java.util.Calendar;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import ca.mss.rd.util.Logger;
import ca.mss.rd.util.UtilDateTime;

abstract public class JobPoolAbstract<J extends IJob> implements IJobPool<J> {
	
	static public String LOG_FORMAT = "%30s on %s";
	
	static public long JOB_POOL_QUEUE_CLEAR_CHECK_DELAY_MLS = 10L;
	static public int JOB_POOL_QUEUE_CLEAR_CHECK_ATTEMPTS = 100;

	final protected String ident;

	protected int priority = Thread.NORM_PRIORITY;
	protected boolean isDeamon = true;

	final private ThreadFactory threadFactory = new ThreadFactory() {
        private ThreadFactory threadFactory = Executors.defaultThreadFactory();
		@Override
		public Thread newThread(Runnable r) {
			Thread  thread = threadFactory.newThread(r);
			
			String[] names = thread.getName().split("-");
			
			thread.setName(String.format("%s[%d]-%s", ident, priority, names[names.length-1]));
			thread.setPriority(priority);
			thread.setDaemon(isDeamon);
					
			return thread;
		}
	};
	
	public JobPoolAbstract() {
		this("JobPool");
	}

	public JobPoolAbstract(String ident) {
		this.ident = ident;
	}

	public JobPoolAbstract(String ident, int priority) {
		this.ident = ident;
		this.priority = priority;
	}

	protected ThreadFactory getThreadFactory(){
        return threadFactory;
	}
	
	@Override
	public void setDeamon(boolean isDeamon) {
		this.isDeamon = isDeamon;
	}

	final protected void startExecutor(final ExecutorService executor, final BlockingQueue<J> queue){
		assert( Logger.JOB_POOL.isOn ? Logger.JOB_POOL.printf(LOG_FORMAT, String.format("%s executors start", ident), UtilDateTime.rightnow()): true);
		executor.execute(new Thread() {
			@Override
			public void run() {
				try {
					for(J job; true; job=null) {
						
						job = queue.take();
						
						if( job.isShutdown() ){
							break;
						}

						try {
							assert( Logger.JOB_POOL_VERBOSE.isOn ? Logger.JOB_POOL_VERBOSE.printf("%s[%d].executeJob", job.getName(), job.getId()): true);
							job.executeJob();
						} catch(Throwable t){
							if( Logger.ERROR.isOn ) Logger.ERROR.printf(t, "%s can not start %s[id=%d] due to unexpected reason - %s", job.getName(), ident, job.getId(), t.getMessage());
						}
					}
				} catch (InterruptedException e) {
					if( Logger.ERROR.isOn ) Logger.ERROR.printf(e, "Interrupted %s on %tc", ident, Calendar.getInstance());
				} catch (Throwable t) {
					if( Logger.ERROR.isOn ) Logger.ERROR.printf(t, "Terminated %s on %tc", ident, Calendar.getInstance());
				} finally {
					if( Logger.JOB_POOL.isOn ) Logger.JOB_POOL.printf("Shutdown job pool [%s] on %tc", ident, Calendar.getInstance());
				}
			}
		});
	}
	
	final protected void shutdown(final ExecutorService executor, final int size, final BlockingQueue<J> queue, final J jobShutdown){
		
		if( queue != null ){
			assert( Logger.JOB_POOL.isOn ? Logger.JOB_POOL.printf(LOG_FORMAT, String.format("%s queue shutdown", ident), UtilDateTime.rightnow()): true);
			
			while( queue.size() > 0 ){
				try {
					Thread.sleep(JOB_POOL_QUEUE_CLEAR_CHECK_DELAY_MLS);
				} catch (InterruptedException e) {
					if( Logger.ERROR.isOn ) Logger.ERROR.printf((e));
				}
			}
			
			for(int i=0; i<size; i++){
				assert( Logger.JOB_POOL.isOn ? Logger.JOB_POOL.printf("Job [%s] run", jobShutdown.getName()): true);
				queue.add( jobShutdown );
			}

		}
		
		if( executor != null ){
			assert( Logger.JOB_POOL.isOn ? Logger.JOB_POOL.printf(LOG_FORMAT,String.format("%s executors shutdown", ident), UtilDateTime.rightnow()): true);
			
			executor.shutdown();
			
			try {
				for(int n=0; n<JOB_POOL_QUEUE_CLEAR_CHECK_ATTEMPTS && !executor.isTerminated(); n++){
					executor.awaitTermination(JOB_POOL_QUEUE_CLEAR_CHECK_DELAY_MLS, TimeUnit.MILLISECONDS);
				}
				
				executor.shutdownNow();
			} catch (InterruptedException e) {
				if( Logger.ERROR.isOn ) Logger.ERROR.printf((e));
			}
		}
	}
	
}
