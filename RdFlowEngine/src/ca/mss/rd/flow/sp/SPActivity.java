package ca.mss.rd.flow.sp;

import java.util.ArrayList;
import java.util.List;

import ca.mss.rd.flow.ActivityType;
import ca.mss.rd.flow.simple.RetriableActivity;

abstract public class SPActivity extends RetriableActivity<SPFlowProp, SPContext> {

	protected List<SPActivity> set = new ArrayList<SPActivity>(0);
	
	private int level;
	
	public SPActivity(SPContext context, ActivityType type, int level) {
		super(type);
		this.context = context;
		this.level = level;
	}

	public void add(SPSubflow subflow){
		set.add(subflow);
	}

	public void add(SPStage stage){
		set.add(stage);
	}

	public void add(SPParallel parallel){
		set.add(parallel);
	}

	public void add(SPSequential sequential){
		set.add(sequential);
	}

	public void finalizeSet(){
		SPActivity[] arr = new SPActivity[set.size()]; 
		set.toArray(arr);
		setNextActivities(arr);

		set.clear();
		set = null;
	}
	
	@Override
	public int getLevel() {
		return level;
	}
}
