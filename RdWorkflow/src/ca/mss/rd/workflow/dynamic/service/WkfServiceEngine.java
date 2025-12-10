/**
 * 
 */
package ca.mss.rd.workflow.dynamic.service;

import ca.mss.rd.workflow.def.WkfData;
import ca.mss.rd.workflow.def.WkfTool;

/**
 * @author smoskov
 *
 */
public interface WkfServiceEngine {

	public void runProcedure(WkfProcedureDelegator delegateObject, WkfTool tool, WkfData data);
	
}
