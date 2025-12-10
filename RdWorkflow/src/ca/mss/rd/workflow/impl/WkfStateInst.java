/**
 * 
 */
package ca.mss.rd.workflow.impl;

import ca.mss.rd.workflow.def.WkfActivityImplementation;
import ca.mss.rd.workflow.def.WkfActivityInstantiation;
import ca.mss.rd.workflow.def.WkfActivityMode;

/**
 * @author smoskov
 *
 */
public class WkfStateInst extends WkfStateImpl {


	public WkfStateInst Priority(int priority) {
		setPriority(priority);
		return this; 
	}
	
	public WkfStateInst Once() {
		setInstantiation(WkfActivityInstantiation.Once);
		return this;
	}
	public WkfStateInst Multiple() {
		setInstantiation(WkfActivityInstantiation.Multiple);
		return this;
	}
	
	public WkfStateInst JoinOnStart(WkfActivityMode joinOnStart) {
		setJoinOnStart(joinOnStart);
		return this; 
	}
	
	public WkfStateInst AutoXorJoin() {
		setJoinOnStart(WkfActivityMode.AutoXor);
		return this;
	}
	
	public WkfStateInst AutoAndJoin() {
		setJoinOnStart(WkfActivityMode.AutoAnd);
		return this;
	}
	
	public WkfStateInst ManualAndJoin() {
		setJoinOnStart(WkfActivityMode.ManualAnd);
		return this;
	}
	
	public WkfStateInst ManualXorJoin() {
		setJoinOnStart(WkfActivityMode.ManualXor);
		return this;
	}
	
	public WkfStateInst SplitOnFinish(WkfActivityMode splitOnFinish) {
		setSplitOnFinish(splitOnFinish);
		return this; 
	}
	public WkfStateInst AutoXorSplit() {
		setSplitOnFinish(WkfActivityMode.AutoXor);
		return this; 
	}
	
	public WkfStateInst AutoAndSplit() {
		setSplitOnFinish(WkfActivityMode.AutoAnd);
		return this; 
	}

	public WkfStateInst ManualAndSplit() {
		setSplitOnFinish(WkfActivityMode.ManualAnd);
		return this; 
	}

	public WkfStateInst ManualXorSplit() {
		setSplitOnFinish(WkfActivityMode.ManualXor);
		return this; 

	}
	
	public WkfStateInst Root() {
		setImplementation(WkfActivityImplementation.Root);
		return this; 

	}
	
	public WkfStateInst Tool() {
		setImplementation(WkfActivityImplementation.Tool);
		return this; 
	}
	
	public WkfStateInst LoopRepeatUntil() {
		setImplementation(WkfActivityImplementation.LoopRepeatUntil);
		return this;
	}
	
	public WkfStateInst LoopWhile() {
		setImplementation(WkfActivityImplementation.LoopWhile);
		return this;
	}
	
	public WkfStateInst NoImplementation() {
		setImplementation(WkfActivityImplementation.NoImplementation);
		return this;
	}
	
	public WkfStateInst SubflowAsyncr() {
		setImplementation(WkfActivityImplementation.SubFlowAsyncr);
		return this;
	}
	
	public WkfStateInst SubflowSyncr() {
		setImplementation(WkfActivityImplementation.SubFlowSyncr);
		return this;
	}

	public WkfStateInst Stop() {
		setStop(true);
		return this;
	}
	
	public WkfStateInst Start() {
		setCanStart(true);
		return this;
	}

}
