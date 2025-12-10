package ca.mss.rd.flow.tools;

import ca.mss.rd.flow.IActivity;
import ca.mss.rd.flow.prop.RdProp;
import ca.mss.rd.flow.simple.DefaultTool;
import ca.mss.rd.flow.sp.SPContext;
import ca.mss.rd.flow.sp.SPFlowProp;
import ca.mss.rd.util.Logger;

import com.scotiabank.maestro.msg.type.RdResult;

public class RdTool extends DefaultTool<SPFlowProp, SPContext> {

	public RdResult result;
	
	public RdTool(IActivity<SPFlowProp,SPContext> activity) {
		super(activity);
		setName(String.format("tool:%s", activity.getName()));
	}

	
	@Override
	public void executeTool() {
		
		// evaluate expressions for input parameters by providing context solver 
		result = getActivity().getContext().getWkfProp().getStageExec().get(getActivity().getCode()).execute(getActivity().getContext());

		// TODO: evaluate expressions for output param here 

		assert( Logger.TOOL_VERBOSE.isOn? Logger.TOOL_VERBOSE.printf("%s = %s (%s)", getName(), result.getCompletedStatus(), result.getMessage()): true);
	}
	
}
