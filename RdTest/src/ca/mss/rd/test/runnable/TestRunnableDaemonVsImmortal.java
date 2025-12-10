package ca.mss.rd.test.runnable;

import ca.mss.rd.util.Logger;

public class TestRunnableDaemonVsImmortal  {


	static public void main(String[] args ) throws Exception {
		Logger.DEBUG.printf("\n\n\n\tMain Thread started");
		
		Logger.DEBUG.printf("*** Test Daemon vs Immortal threads ***");
		
		new RunnableDemo("", false, 5, 100L).startThread();
		new RunnableDemo("", true, 10, 100L).startThread();
		
		Thread.sleep(250L);
		
		Logger.DEBUG.printf("\tMain Thread ended");
	}
	
	
	
}
