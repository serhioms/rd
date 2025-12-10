package ca.mss.rd.util.runnable;

import java.util.Date;

import ca.mss.rd.util.UtilDateTime;

abstract public class RdRunnableSecundomer extends RdRunnableInfinite {

	private Date start, timeon, nexttimeon;
	private long delay;
	private int thresholdSec;
	private long thresholdMls;
	
	private Date workStart, workEnd;
	private long workDuration;

	public RdRunnableSecundomer() {
		super(true);
	}

	public RdRunnableSecundomer(boolean isDaemon) {
		super(isDaemon);
	}

	public RdRunnableSecundomer(String name, boolean isDaemon) {
		super(name, isDaemon);
	}

	public RdRunnableSecundomer(String name) {
		super(name);
	}

	/*
	 * Instead of work() you must use secundomer() method
	 */
	abstract public void secundomer() throws InterruptedException;
	
	/*
	 * Useful to override
	 */
	public void postSecundomer(){}

	
	@Override
	public void startThreadHandler() throws InterruptedException {
		nexttimeon = null;
	}

	public Date getStartDate() {
		return UtilDateTime.now();
	}

	final private void sleepBefore() throws InterruptedException {
		start = getStartDate();
		timeon = UtilDateTime.setMillisecond(start, 0);

		if( thresholdSec > 0 ){
			if( nexttimeon == null ){
				final int second = UtilDateTime.getSecond(timeon);
				timeon = UtilDateTime.setSecond(timeon, second + thresholdSec - (second % thresholdSec));
			} else {
				timeon = nexttimeon; 
			}
			nexttimeon = UtilDateTime.addTime(timeon, 0, 0, thresholdSec);
			delay = Math.max(UtilDateTime.substruct(timeon, UtilDateTime.now()), 0L);
		} else if( thresholdMls > 0L ){
			delay = thresholdMls;
		}

		sleep(delay);
	}

	@Override
	final public void runThreadHandler() throws InterruptedException {
		sleepBefore();
		workStart = UtilDateTime.now();
		{
			secundomer();
		}
		workEnd = UtilDateTime.now();
		workDuration = UtilDateTime.substruct(workEnd, workStart);
		
		postSecundomer();
	}

	/*
	 * Getters/Setters
	 */
	
	public final int getThresholdSec() {
		return thresholdSec;
	}

	public final void setThresholdSec(int thresholdSec) {
		this.thresholdSec = thresholdSec;
	}
	
	public final long getThresholdMls() {
		return thresholdMls;
	}

	public final void setThresholdMls(long thresholdMls) {
		this.thresholdMls = thresholdMls;
	}

	public final Date getStart() {
		return start;
	}

	public final Date getTimeon() {
		return timeon;
	}

	public final Date getNextTimeon() {
		return nexttimeon;
	}

	public final long getDelay() {
		return delay;
	}

	public final Date getWorkStart() {
		return workStart;
	}

	public final Date getWorkEnd() {
		return workEnd;
	}

	public final long getWorkDuration() {
		return workDuration;
	}
	
}
