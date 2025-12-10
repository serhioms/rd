package ca.mss.rd.util.runnable;

import java.util.concurrent.Semaphore;

import ca.mss.rd.util.Logger;



abstract public class RdRunnable implements RdRunnableInt {

	static public int HIFH_PRIORITY = Thread.NORM_PRIORITY + 2;
	static public int NORM_PRIORITY = Thread.NORM_PRIORITY;
	static public int LOW_PRIORITY = Thread.NORM_PRIORITY - 2;
	
	public final String name;
	public final boolean isDeamon;
	protected Thread thread;
    private int threadCounter;
    
    protected boolean isShutdown;
    
	protected final Semaphore serializer = new Semaphore(1);

    abstract public void runThreadHandler() throws InterruptedException;
	
    public void interruptionHandler(Throwable t){
    	if( Logger.ERROR.isOn ) Logger.ERROR.printf(t, "%s.interrupted", getName());
    }

	public RdRunnable() {
    	this.name = "autodeamon";
    	this.isDeamon = true;
    	this.isShutdown = false;
	}

    public RdRunnable(String name) {
    	this.name = name;
    	this.isDeamon = true;
    	this.threadCounter = 1;
    	this.isShutdown = false;
	}

    public RdRunnable(String name, boolean isDaemon) {
    	this.name = name;
    	this.isDeamon = isDaemon;
    	this.threadCounter = 1;
    	this.isShutdown = false;
	}

    public RdRunnable(boolean isDaemon) {
    	this.name = isDaemon? "Deamon": "Immortal";
    	this.isDeamon = isDaemon;
    	this.isShutdown = false;
	}

	final public Thread createThread(final int priority){
		thread = new Thread(this);
    	thread.setName( name + "["+(threadCounter++)+"]" );
    	thread.setDaemon( isDeamon );
    	thread.setPriority(priority);
    	return thread; 
    }

	final public Thread createThread(){
		thread = new Thread(this);
    	thread.setName( name + "["+(threadCounter++)+"]" );
    	thread.setDaemon( isDeamon );
    	return thread; 
    }

	@Override
	public final void sleep(final long sleep) throws InterruptedException {
		if( sleep > 0L ){
			try {
				Thread.sleep(sleep);
			} catch (InterruptedException e){
				throw e;
			}
		}
	}

	@Override
	public final void lock() throws InterruptedException {
		serializer.acquire();
		assert( Logger.THREAD.isOn ? Logger.THREAD.printf("%s.aquireLock = %d", getName(), serializer.availablePermits()): true);
	}

	@Override
	public final void release() throws InterruptedException {
		serializer.release();
		assert( Logger.THREAD.isOn ? Logger.THREAD.printf("%s.releaseLock = %d", getName(), serializer.availablePermits()): true);
	}

	@Override
	public void run() {
	    try {
    	    runThreadHandler();
	    } catch (Throwable t) {
	    	interruptionHandler(t);
	    } finally {
	    	thread = null;
	    }
	}

	@Override
	public final String getName() {
		return Thread.currentThread().getName();
	}

	@Override
	public final boolean isDeamon() {
		return isDeamon;
	}

	public final void shutDown() {
		isShutdown = true;
	}

	@Override
	public final boolean isRunning() {
		return thread != null && thread.isAlive();
	}

	@Override
	public RdRunnable startThread(){
		return startThread(NORM_PRIORITY);
	}
	
	public final RdRunnable startThread(final int priority){
		if( !isRunning() ){
			createThread(priority).start();
        } else {
        	throw new RuntimeException(String.format("Can not start thread [%s] cause it is allready running!", thread));
        }
		return this;
    }

	@Override
	public final void joinThread(){
		try {
			thread.join();
		} catch (InterruptedException e) {
			new RuntimeException(String.format("Thread %s is interrupted", name), e);
		} catch (NullPointerException e) {
			new RuntimeException(String.format("Thread %s is not started", name), e);
		}
    }


	@Override
	public final void interruptThread() {
		Thread ref = thread;
		thread = null;
		ref.interrupt();
    	//interruptionHandler(new RuntimeException(String.format("Soft interruption of thread %s", getName())));
    }
}