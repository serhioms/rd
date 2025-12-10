package ca.mss.rd.util.runnable;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class RdExecutors implements ThreadFactory {

	final static public String module = RdExecutors.class.getSimpleName();
	final static public long serialVersionUID = module.hashCode();

	
	final public Object name;
	final public boolean isDaemon;
	final public int priority;
	
	public RdExecutors(Object name, boolean isDaemon, int priority) {
		this.name = name;
		this.isDaemon = isDaemon;
		this.priority = priority;
	}

	@Override
	public Thread newThread(Runnable task) {
    	Thread thread = new Thread(task);
    	thread.setName( name.toString() );
    	thread.setDaemon( isDaemon );
    	thread.setPriority( priority );
    	return thread;
	}

	
	final static public ExecutorService newExecutorsPool(int size, Object name, boolean isDaemon, int priority){
		return Executors.newFixedThreadPool(size, new RdExecutors(name, isDaemon, priority));
	}

	
}
