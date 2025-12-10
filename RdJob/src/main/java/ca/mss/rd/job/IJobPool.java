package ca.mss.rd.job;


public interface IJobPool<J extends IJob> {

	public void queueJob(J job);

	public boolean isBusy();
	public boolean isRunning();
	public int size();

	public void startPool();
	public void shutdownPool(J jobShutdown);

	public int getQueueSize();
	public void setQueueSize(int queueSize);

	public int getExeSize();
	public void setExeSize(int exeSize);

	public void setDeamon(boolean isDeamon);

}
