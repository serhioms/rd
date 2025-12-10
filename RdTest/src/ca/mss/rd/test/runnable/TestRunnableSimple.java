package ca.mss.rd.test.runnable;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ca.mss.rd.util.Logger;
import ca.mss.rd.util.runnable.RdRunnable;

public class TestRunnableSimple  {


	@Before
	public void begin() throws Exception {
		Logger.DEBUG.printf("\n\n\n\tMain Thread started");
	}

	@After
	public void end() throws Exception {
		Logger.DEBUG.printf("\tMain Thread ended");
	}

	
	
	
	@Test
	public void TestThreadSimple() throws Exception {
		Logger.DEBUG.printf("*** Test Simple thread ***");
		RdRunnable run = new RunnableDemo("Simple", false, 10, 100L).startThread();
		
		Thread.sleep(250L);

		run.interruptThread();
	}
	
	
	
}
