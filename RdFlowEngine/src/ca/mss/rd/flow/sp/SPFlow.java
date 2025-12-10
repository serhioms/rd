package ca.mss.rd.flow.sp;

import ca.mss.rd.flow.prop.RdProp;
import ca.mss.rd.flow.simple.DefaultWorkflow;

public class SPFlow extends DefaultWorkflow<SPFlowProp,SPContext> {

	public SPFlow(SPContext context, SPActivity start) {
		super(context);
		workflow(this, start); 
	}

	@Override
	public String getName() {
		return String.format("wkf.%s", super.getName());
	}

	@Override
	public String toString() {
		return getName();
	}

	
}
