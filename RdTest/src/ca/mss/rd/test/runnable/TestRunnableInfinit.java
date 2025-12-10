package ca.mss.rd.test.runnable;

import ca.mss.rd.util.Logger;
import ca.mss.rd.util.UtilDateTime;
import ca.mss.rd.util.UtilRand;
import ca.mss.rd.util.runnable.RdRunnableSecundomer;

public class TestRunnableInfinit {

	final static private int SECUNDOMER_THRESHOULD_SEC = 1;

	static public String TIME_FORMATTER = "mm:ss.SSS";

	static public void main(String[] args) throws Exception {
		Logger.DEBUG.printf("\n\n\n\tMain Thread started");
		Logger.DEBUG.printf("*** Test interrupt and continue thread ***");

		new RunnableDemoInfinit("Infinit", false, 150L).startThread();

		Logger.DEBUG.printf("\tMain Thread ended");
	}

	static public class RunnableDemoInfinit extends RdRunnableSecundomer {

		private long sleep;
		private int counter = 0;

		public RunnableDemoInfinit(String id, boolean isDaemon, long sleep) {
			super((isDaemon ? "Deamon" : "Immortal") + id, isDaemon);
			this.sleep = sleep;
			setThresholdSec(SECUNDOMER_THRESHOULD_SEC);
		}

		@Override
		public void secundomer() throws InterruptedException {
			sleep(sleep + UtilRand.getRandLong(sleep));
		}

		
		@Override
		public void endThreadHandler() {
			Logger.DEBUG.printf("Secundomer [start=" 
					+ UtilDateTime.format(getStart(), TIME_FORMATTER) + "][timeon="
					+ UtilDateTime.format(getTimeon(), TIME_FORMATTER) + "][before=" 
					+ getDelay() + "][startWork="
					+ UtilDateTime.format(getWorkStart(), TIME_FORMATTER) + "][duration=" 
					+ getWorkDuration() + " mls][endWork="
					+ UtilDateTime.format(getWorkEnd(), TIME_FORMATTER) + "]");
			if (counter++ == 10) interruptThread();
		}

		@Override
		public void interruptionHandler(Throwable t) {
			if( Logger.ERROR.isOn ) Logger.ERROR.printf("Thread %s interrupted", getName());
			super.interruptionHandler(t);
		}

	}

}
