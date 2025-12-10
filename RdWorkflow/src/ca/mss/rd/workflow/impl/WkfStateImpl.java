/**
 * 
 */
package ca.mss.rd.workflow.impl;

import ca.mss.rd.workflow.def.WkfActivityImplementation;
import ca.mss.rd.workflow.def.WkfActivityInstantiation;
import ca.mss.rd.workflow.def.WkfActivityMode;
import ca.mss.rd.workflow.def.WkfActivityState;

/**
 * @author smoskov
 *
 */
public class WkfStateImpl extends WkfStateBean implements WkfActivityState {

	private String ident;
	
	@Override
	public final String getIdent() {
		return ident;
	}

	@Override
	public final void setIdent(String ident) {
		this.ident = ident;
	}

	/* (non-Javadoc)
	 * @see ca.mss.workflow.WkfActivity#canStart()
	 */
	@Override
	public boolean canStart() {
		return canStart;
	}

	/* (non-Javadoc)
	 * @see ca.mss.workflow.WkfActivityImpl#isInstantiateOnce()
	 */
	@Override
	public boolean isInstantiateOnce() {
		return instantiation == WkfActivityInstantiation.Once;
	}

	/* (non-Javadoc)
	 * @see ca.mss.workflow.WkfActivityImpl#isInstantiateMultiple()
	 */
	@Override
	public boolean isInstantiateMultiple() {
		return instantiation == WkfActivityInstantiation.Multiple;
	}

	/* (non-Javadoc)
	 * @see ca.mss.workflow.WkfActivityImpl#isAutoStart()
	 */
	@Override
	public boolean isAutoStart() {
		return joinOnStart == WkfActivityMode.AutoXor || 
			joinOnStart == WkfActivityMode.AutoAnd;
	}

	/* (non-Javadoc)
	 * @see ca.mss.workflow.WkfActivityImpl#isManualStart()
	 */
	@Override
	public boolean isManualStart() {
		return joinOnStart == WkfActivityMode.ManualXor || 
			joinOnStart == WkfActivityMode.ManualAnd;
	}

	/* (non-Javadoc)
	 * @see ca.mss.workflow.WkfActivityImpl#isAutoFinish()
	 */
	@Override
	public boolean isAutoFinish() {
		return splitOnFinish == WkfActivityMode.AutoXor || 
			splitOnFinish == WkfActivityMode.AutoAnd;
	}

	/* (non-Javadoc)
	 * @see ca.mss.workflow.WkfActivityImpl#isManualFinish()
	 */
	@Override
	public boolean isManualFinish() {
		return splitOnFinish == WkfActivityMode.ManualXor || 
			splitOnFinish == WkfActivityMode.ManualAnd;
	}

	/* (non-Javadoc)
	 * @see ca.mss.workflow.WkfActivityImpl#isBlockBegin()
	 */
	@Override
	public boolean isBlockBegin() {
		// TODO Workflow block begin
		return false;
	}

	/* (non-Javadoc)
	 * @see ca.mss.workflow.WkfActivityImpl#isBlockEnd()
	 */
	@Override
	public boolean isBlockEnd() {
		// TODO Workflow block end
		return false;
	}

	/* (non-Javadoc)
	 * @see ca.mss.workflow.WkfActivityImpl#isJoinXor()
	 */
	@Override
	public boolean isJoinXor() {
		return joinOnStart == WkfActivityMode.AutoXor || 
			joinOnStart == WkfActivityMode.ManualXor;
	}

	/* (non-Javadoc)
	 * @see ca.mss.workflow.WkfActivityImpl#isJoinAnd()
	 */
	@Override
	public boolean isJoinAnd() {
		return joinOnStart == WkfActivityMode.AutoAnd || 
			joinOnStart == WkfActivityMode.ManualAnd;
	}

	/* (non-Javadoc)
	 * @see ca.mss.workflow.WkfActivityImpl#isSplitXor()
	 */
	@Override
	public boolean isSplitXor() {
		return splitOnFinish == WkfActivityMode.AutoXor || 
			splitOnFinish == WkfActivityMode.ManualXor;
	}

	/* (non-Javadoc)
	 * @see ca.mss.workflow.WkfActivityImpl#isSplitAnd()
	 */
	@Override
	public boolean isSplitAnd() {
		return splitOnFinish == WkfActivityMode.AutoAnd || 
			splitOnFinish == WkfActivityMode.ManualAnd;
	}

	/* (non-Javadoc)
	 * @see ca.mss.workflow.WkfActivityImpl#isRoot()
	 */
	@Override
	public boolean isRoot() {
		return implementation == WkfActivityImplementation.Root;
	}

	/* (non-Javadoc)
	 * @see ca.mss.workflow.WkfActivityImpl#isNoImplementation()
	 */
	@Override
	public boolean isNoImplementation() {
		return implementation == WkfActivityImplementation.NoImplementation;
	}

	/* (non-Javadoc)
	 * @see ca.mss.workflow.WkfActivityImpl#isTool()
	 */
	@Override
	public boolean isTool() {
		return implementation == WkfActivityImplementation.Tool;
	}

	/* (non-Javadoc)
	 * @see ca.mss.workflow.WkfActivityImpl#isLoop()
	 */
	@Override
	public boolean isLoop() {
		return implementation == WkfActivityImplementation.LoopRepeatUntil ||
		   implementation == WkfActivityImplementation.LoopWhile;
	}

	/* (non-Javadoc)
	 * @see ca.mss.workflow.WkfActivityImpl#isLoopWhile()
	 */
	@Override
	public boolean isLoopWhile() {
		return implementation == WkfActivityImplementation.LoopWhile;
	}

	/* (non-Javadoc)
	 * @see ca.mss.workflow.WkfActivityImpl#isLoopRepeatUntil()
	 */
	@Override
	public boolean isLoopRepeatUntil() {
		return implementation == WkfActivityImplementation.LoopRepeatUntil;
	}

	/* (non-Javadoc)
	 * @see ca.mss.workflow.WkfActivityImpl#isSubFlow()
	 */
	@Override
	public boolean isSubFlow() {
		return implementation == WkfActivityImplementation.SubFlowAsyncr ||
		   implementation == WkfActivityImplementation.SubFlowSyncr;
	}

	/* (non-Javadoc)
	 * @see ca.mss.workflow.WkfActivityImpl#isSubFlowSynch()
	 */
	@Override
	public boolean isSubFlowSynch() {
		return implementation == WkfActivityImplementation.SubFlowSyncr;
	}

	/* (non-Javadoc)
	 * @see ca.mss.workflow.WkfActivityImpl#isSubFlowAsynchr()
	 */
	@Override
	public boolean isSubFlowAsynchr() {
		return implementation == WkfActivityImplementation.SubFlowAsyncr;
	}

	@Override
	public String toString() {
		return ident+"[start="+joinOnStart+"][=finish"+splitOnFinish+"]";
	}

	
}
