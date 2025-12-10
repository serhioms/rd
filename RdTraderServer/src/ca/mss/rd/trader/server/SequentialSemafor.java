package ca.mss.rd.trader.server;

public interface SequentialSemafor {

	public void lock();
	public void release();
	
}
