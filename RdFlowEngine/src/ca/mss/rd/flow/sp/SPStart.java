package ca.mss.rd.flow.sp;

import ca.mss.rd.flow.ActivityType;
import ca.mss.rd.flow.prop.RdProp;

public class SPStart extends SPActivity {

	public SPStart(SPContext context, int level) {
		super(context, ActivityType.Route, level);
	}

	@Override
	public String getName() {
		return String.format("start.%s", super.getName());
	}

	@Override
	public String toString() {
		return getName();
	}

}
