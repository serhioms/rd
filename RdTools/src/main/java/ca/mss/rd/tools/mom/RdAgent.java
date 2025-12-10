package ca.mss.rd.tools.mom;

import java.util.Map;

import ca.mss.rd.util.UtilRand;
import ca.mss.rd.util.runnable.RdRunnable;

public class RdAgent extends RdRunnable {

	private boolean isAsync = false; 
	protected Map<String, Object> context;
	
	public RdAgent(String name) {
		super(name);
	}

	public final boolean isAsync() {
		return isAsync;
	}

	public final void setAsync(boolean isAsync) {
		this.isAsync = isAsync;
	}

	public void start(Map<String, Object> context){
		this.context = context;
		startThread();
	}
	
	@Override
	public RdRunnable startThread() {
		if( isAsync )
			super.startThread();
		else
			runThreadHandler();
		return this;
	}

	@Override
	public void runThreadHandler() {
		long sleep = UtilRand.getRandLong(1000L);
		System.out.println("Run ["+name+"]["+context+"]");
		try { Thread.sleep(sleep); } catch (InterruptedException e) {}
		context.put(name, ""+(sleep/1000.0));
	}

}
