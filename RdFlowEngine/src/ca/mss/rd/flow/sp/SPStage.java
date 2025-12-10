package ca.mss.rd.flow.sp;

import ca.mss.rd.flow.ActivityType;
import ca.mss.rd.flow.ITool;
import ca.mss.rd.flow.prop.RdProp;
import ca.mss.rd.flow.tools.RdTool;

public class SPStage extends SPActivity {

	public SPStage(SPContext context, int level) {
		super(context, ActivityType.Tool, level);
	}

	@Override
	public String getName() {
		return String.format("stage.%s", super.getName());
	}

	@Override
	public ITool[] getActivityTools() {
		return new ITool[]{new RdTool(this)};
	}

	@Override
	public String toString() {
		return getName();
	}
	
}
