package ca.mss.rd.flow.sp.basic;

import ca.mss.rd.flow.ActivityType;
import ca.mss.rd.flow.IContext;
import ca.mss.rd.flow.simple.RetriableActivity;

public class SPRootActivity<PROP,CONTEXT extends IContext<PROP>> extends RetriableActivity<PROP,CONTEXT> {
	
	public SPRootActivity() {
		super(ActivityType.Route);
		setName("Route");
	}
	
}