/**
 * 
 */
package ca.mss.rd.workflow.def;


/**
 * @author smoskov
 *
 */
public enum WkfActivityImplementation {
	NoImplementation, 
	Tool,
	SubFlow, 
	SubFlowSyncr, 
	SubFlowAsyncr, 
	Root, 
	Loop, 
	LoopWhile,
	LoopRepeatUntil;
}
