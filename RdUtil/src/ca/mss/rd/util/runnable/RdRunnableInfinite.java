package ca.mss.rd.util.runnable;

abstract public class RdRunnableInfinite extends RdRunnable {

 	public RdRunnableInfinite(boolean isDaemon) {
		super(isDaemon);
	}

	public RdRunnableInfinite(String name, boolean isDaemon) {
		super(name, isDaemon);
	}

	public RdRunnableInfinite(String name) {
		super(name);
	}
	
	@Override
	public void run() {
	    try {
	    	startThreadHandler();
	    	for(; thread != null; delayThreadHandler() ){
	    		runThreadHandler();
	    	}
	    } catch (Throwable t) {
    		interruptionHandler(t);
	    } finally {
	    	thread = null;
	    	endThreadHandler();
	    }
	}

	/*
	 * Useful to override
	 */
	public void startThreadHandler() throws InterruptedException {}
	public void endThreadHandler(){}
	public void delayThreadHandler() throws InterruptedException {}
	
}