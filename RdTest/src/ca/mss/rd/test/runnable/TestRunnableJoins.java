package ca.mss.rd.test.runnable;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ca.mss.rd.util.Logger;

public class TestRunnableJoins  {

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
	public void TestThreadJoins() {
		Logger.DEBUG.printf("**** Test thread joins ***");
		
		new RunnableDemo("#1", false, 5, 10L).startThread();
		new RunnableDemo("#2", false, 5, 10L).startThread();
		
		new RunnableDemo("#3", false, 5, 10L).startThread().joinThread();
		
		new RunnableDemo("#4", false, 5, 10L).startThread();
	}
	
}
