package ca.mss.rd.flow.sp;

import ca.mss.rd.flow.ActivityType;
import ca.mss.rd.flow.prop.RdProp;
import ca.mss.rd.wkfdef.iw.ExternalWorkflowReference;

public class SPSubflow extends SPActivity {

	public SPSubflow(SPContext context, ExternalWorkflowReference subDefn, SPFlow wkf, int level) {
		super(context, ActivityType.SubFlow, level);
		setSubWkf(wkf);
	}
	
	@Override
	public String getName() {
		return String.format("sub.%s", super.getName());
	}

	@Override
	public String toString() {
		return getName();
	}

}
