package ca.mss.rd.test.runnable;

import ca.mss.rd.util.Logger;
import ca.mss.rd.util.runnable.RdRunnable;

public class RunnableDemo extends RdRunnable {

	int n;
	long sleep;
	
	public RunnableDemo(String id, boolean isDaemon, int n, long sleep) {
		super((isDaemon? "Deamon": "Immortal")+id, isDaemon);
		this.n = n;
		this.sleep = sleep;
	}

	@Override
	public void runThreadHandler() throws InterruptedException {
		Logger.DEBUG.printf("Running %s", getName());
		
		for(int i=1; i<=n; i++) {
			sleep(sleep);
			Logger.DEBUG.printf("Hello(%d) from thread %s", i, getName());
		}
		
		Logger.DEBUG.printf("Thread %s is exiting", getName());
	}
	
	@Override
	public void interruptionHandler(Throwable t){
		if( Logger.ERROR.isOn ) Logger.ERROR.printf("Thread %s interrupted", getName());
		super.interruptionHandler(t);
	}


}
