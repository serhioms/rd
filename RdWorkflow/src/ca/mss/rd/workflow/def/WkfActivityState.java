/**
 * 
 */
package ca.mss.rd.workflow.def;



/**
 * @author smoskov
 *
 */
public interface WkfActivityState {
	
	public String getIdent();
	public void setIdent(String ident);
	
	public String getId();

	public boolean canStart();
	public WkfActivityImplementation getImplementation();
	
	
	public boolean isStop();
	
	public int getPriority();
	
	public boolean isInstantiateOnce();
	public boolean isInstantiateMultiple();
	
	public boolean isAutoStart();
	public boolean isManualStart();
	
	public boolean isAutoFinish();
	public boolean isManualFinish();

	public boolean isBlockBegin();
	public boolean isBlockEnd();

	public boolean isJoinXor();
	public boolean isJoinAnd();
	
	public boolean isSplitXor();
	public boolean isSplitAnd();

	public boolean isNoImplementation();
	public boolean isTool();		
	public boolean isSubFlow();			
	public boolean isRoot();			
	public boolean isLoop();
	
	public boolean isLoopWhile();			
	public boolean isLoopRepeatUntil();			
	
	public boolean isSubFlowSynch();			
	public boolean isSubFlowAsynchr();
	
	public WkfTool[] getTools();
}
