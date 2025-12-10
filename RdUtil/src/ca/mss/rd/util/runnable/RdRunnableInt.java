package ca.mss.rd.util.runnable;



public interface RdRunnableInt extends Runnable {

	public void sleep(long sleep) throws InterruptedException;
	
	public String getName();
	
	public boolean isDeamon();
	public boolean isRunning();
	
	public void joinThread();
	public void interruptThread();
	public RdRunnableInt startThread();

	public void lock() throws InterruptedException;
	public void release() throws InterruptedException;

}