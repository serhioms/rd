package ca.mss.rd.flow.sp;

import ca.mss.rd.flow.ActivityType;
import ca.mss.rd.flow.prop.RdProp;

public class SPSequential extends SPActivity {

	public SPSequential(SPContext context, int level) {
		super(context, ActivityType.Route, level);
	}

	@Override
	public String getName() {
		return String.format("seq.%s", super.getName());
	}

	@SuppressWarnings("unchecked")
	public void finalizeSet(){
		for(int i=1, size=set.size(); i<size; i++){
			set.get(i-1).setNextActivities(set.get(i));
		}
		setNextActivities(set.get(0));
		
		set.clear();
		set = null;
	}

	@Override
	public String toString() {
		return getName();
	}
}
