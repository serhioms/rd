package ca.mss.rd.test.runnable;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ca.mss.rd.util.Logger;
import ca.mss.rd.util.runnable.RdRunnable;

public class TestRunnableInterruption {

	@Before
	public void begin() throws Exception {
		Logger.DEBUG.printf("\n\n\n\tMain Thread started");
	}

	@After
	public void end() throws Exception {
		Thread.sleep(300L);
		Logger.DEBUG.printf("\tMain Thread ended");
	}

	
	
	
	@Test
	public void TestInterrupted() throws Exception {
		Logger.DEBUG.printf("*** Test interrupt and continue thread ***");
		
		RdRunnable rd = new RunnableSleepwalk("Feniks", false, 10, 100L).startThread();
		
		Thread.sleep(250L);
		
		rd.interruptThread();
	}
	
	
	
	static public class RunnableSleepwalk extends RunnableDemo {

		public RunnableSleepwalk(String id, boolean isDaemon, int n, long sleep) {
			super(id, isDaemon, n, sleep);
		}

		@Override
		public void interruptionHandler(Throwable t){
			super.interruptionHandler(t);
			startThread();
		}
	
	}

}
