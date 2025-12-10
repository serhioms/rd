package ca.mss.rd.flow;

import ca.mss.rd.job.IJob;

public interface ITool extends IJob {

	static public final ITool[] NO_TOOLS = new ITool[]{};

	final static boolean SYNC_TOOL = true;
	final static boolean ASYNC_TOOL = false;


	public void setName(String name);
	
	public boolean isSync(); // return SYNC_TOOL/ASYNC_TOOL
	
	public String getPath();
	
	public void executeTool();

}
