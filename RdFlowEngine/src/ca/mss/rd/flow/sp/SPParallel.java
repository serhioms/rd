package ca.mss.rd.flow.sp;

import ca.mss.rd.flow.ActivityType;
import ca.mss.rd.flow.prop.RdProp;

public class SPParallel extends SPActivity {

	public SPParallel(SPContext context, int level) {
		super(context, ActivityType.ActivitySet, level);
	}


	@Override
	public String getName() {
		return String.format("par.%s", super.getName());
	}


	public void finalizeSet(){
		SPActivity[] arr = new SPActivity[super.set.size()]; 
		super.set.toArray(arr);
		setActivitySet(arr);

		super.set.clear();
		super.set = null;
	}


	@Override
	public String toString() {
		return getName();
	}
	
}
