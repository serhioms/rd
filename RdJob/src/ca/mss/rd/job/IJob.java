package ca.mss.rd.job;

public interface IJob {

	static public final String SHUTDOWN = "Shutdown";

	public boolean isShutdown();
	
	public long getId();
	public void setId(long id);
	
	public String getName();
	public void setName(String name);

	public void executeJob();
}
