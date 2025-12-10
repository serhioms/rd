/**
 * 
 */
package ca.mss.rd.workflow.impl;

/**
 * @author smoskov
 *
 */
public class WkfActivityFactory {
	
	private static WkfStateInst instantiate(){
		return new WkfStateInst();
	}
	
	public static WkfStateInst Root(){
		return instantiate().Root(); 
	}
	
	public static WkfStateInst Start(){
		return instantiate().Root().Start(); 
	}
	
	public static WkfStateInst Stop(){
		return instantiate().Root().Stop(); 
	}

	public static WkfStateInst NoImplementation(){
		return instantiate().Root().NoImplementation(); 
	}

	public static WkfStateInst Tool(){
		return instantiate().Root().Tool(); 
	}

	public static WkfStateInst LoopWhileActivity(){
		return instantiate().Root().LoopWhile(); 
	}

	public static WkfStateInst LoopRepeatUntilActivity(){
		return instantiate().Root().LoopRepeatUntil(); 
	}

	public static WkfStateInst SubflowSyncrActivity(){
		return instantiate().Root().SubflowSyncr(); 
	}

	public static WkfStateInst SubflowAsyncrActivity(){
		return instantiate().Root().SubflowAsyncr(); 
	}

}
