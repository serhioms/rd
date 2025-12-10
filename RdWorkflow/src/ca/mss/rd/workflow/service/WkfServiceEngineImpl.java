/**
 * 
 */
package ca.mss.rd.workflow.service;

import java.util.Map;

import ca.mss.rd.workflow.def.WkfData;
import ca.mss.rd.workflow.def.WkfTool;
import ca.mss.rd.workflow.dynamic.service.WkfProcedureDelegator;
import ca.mss.rd.workflow.dynamic.service.WkfServiceEngine;

/**
 * @author smoskov
 *
 */
public class WkfServiceEngineImpl implements WkfServiceEngine {

	public static final String module = WkfServiceEngineImpl.class.getName();

	/* (non-Javadoc)
	 * @see ca.mss.workflow.dynamic.service.WkfServiceEngine#runProcedure(ca.mss.workflow.dynamic.service.WkfProcedureDelegator, ca.mss.workflow.def.WkfTool, ca.mss.workflow.def.WkfData)
	 */
	@Override
	public void runProcedure(WkfProcedureDelegator delegateObject, WkfTool tool, WkfData data) {
		ServiceHelper.invoke(delegateObject, tool.getDefinition().get("serviceName").value, (Map )data);
	}

	
}
