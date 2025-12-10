/**
 * 
 */
package ca.mss.rd.workflow.impl;

import ca.mss.rd.workflow.def.WkfActivityImplementation;
import ca.mss.rd.workflow.def.WkfActivityInstantiation;
import ca.mss.rd.workflow.def.WkfActivityMode;
import ca.mss.rd.workflow.def.WkfTool;

/**
 * @author smoskov
 *
 */
public class WkfStateBean {

	// Let's to be OOP open mind
	protected String id;
	
	protected boolean canStart = false;
	protected boolean isStop = false;
	
	protected int priority = 0;
	
	protected WkfActivityInstantiation instantiation = WkfActivityInstantiation.Once;
	protected WkfActivityMode joinOnStart = WkfActivityMode.AutoXor;
	protected WkfActivityMode splitOnFinish = WkfActivityMode.AutoXor;
	protected WkfActivityImplementation implementation = WkfActivityImplementation.Root;
	
	private WkfTool[] tools;
	
	/**
	 * @return the id
	 */
	public final String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public final void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the tools
	 */
	public WkfTool[] getTools() {
		return tools;
	}
	/**
	 * @param tools the tools to set
	 */
	public void setTools(WkfTool[] tools) {
		this.tools = tools;
	}
	/**
	 * @return the canStart
	 */
	public boolean getCanStart() {
		return canStart;
	}
	/**
	 * @param canStart the canStart to set
	 */
	public void setCanStart(boolean canStart) {
		this.canStart = canStart;
	}
	/**
	 * @return the isStop
	 */
	public boolean isStop() {
		return isStop;
	}
	/**
	 * @param isStop the isStop to set
	 */
	public void setStop(boolean isStop) {
		this.isStop = isStop;
	}
	/**
	 * @return the priority
	 */
	public int getPriority() {
		return priority;
	}
	/**
	 * @param priority the priority to set
	 */
	public void setPriority(int priority) {
		this.priority = priority;
	}
	/**
	 * @return the instantiation
	 */
	public WkfActivityInstantiation getInstantiation() {
		return instantiation;
	}
	/**
	 * @param instantiation the instantiation to set
	 */
	public void setInstantiation(WkfActivityInstantiation instantiation) {
		this.instantiation = instantiation;
	}
	/**
	 * @return the joinOnStart
	 */
	public WkfActivityMode getJoinOnStart() {
		return joinOnStart;
	}
	/**
	 * @param joinOnStart the joinOnStart to set
	 */
	public void setJoinOnStart(WkfActivityMode joinOnStart) {
		this.joinOnStart = joinOnStart;
	}
	/**
	 * @return the splitOnFinish
	 */
	public WkfActivityMode getSplitOnFinish() {
		return splitOnFinish;
	}
	/**
	 * @param splitOnFinish the splitOnFinish to set
	 */
	public void setSplitOnFinish(WkfActivityMode splitOnFinish) {
		this.splitOnFinish = splitOnFinish;
	}
	/**
	 * @return the implementation
	 */
	public WkfActivityImplementation getImplementation() {
		return implementation;
	}
	/**
	 * @param implementation the implementation to set
	 */
	public void setImplementation(WkfActivityImplementation implementation) {
		this.implementation = implementation;
	}
	
}
