package ng.ca.mss.rd.trader;

import java.util.concurrent.atomic.AtomicInteger;

import ca.mss.rd.flow.IActivity;
import ca.mss.rd.flow.IContext;
import ca.mss.rd.flow.ITool;
import ca.mss.rd.flow.IWorkflow;
import ca.mss.rd.flow.event.WorkflowEvent;
import ca.mss.rd.flow.event.WorkflowListener;
import ca.mss.rd.flow.sp.SPFlowProp;
import ca.mss.rd.flow.sp.SPStage;
import ca.mss.rd.util.Logger;
import ca.mss.rd.util.map.SmartMap;

public class WorkflowStat<CONTEXT extends IContext<SPFlowProp>> implements WorkflowListener<SPFlowProp,CONTEXT> {

	public final AtomicInteger activityCounter = new AtomicInteger(0);

	public final SmartMap<String, AtomicInteger> activities = new SmartMap<String, AtomicInteger>(){
		@Override
		public AtomicInteger valueFactory(Object key) {
			return new AtomicInteger(0);
		}
	};

	public final SmartMap<String, AtomicInteger> stages = new SmartMap<String, AtomicInteger>(){
		@Override
		public AtomicInteger valueFactory(Object key) {
			return new AtomicInteger(0);
		}
	};

	public WorkflowStat() {
	}

	@SuppressWarnings("incomplete-switch")
	@Override
	public void stateChange(WorkflowEvent event, IWorkflow<SPFlowProp,CONTEXT> wkf) throws Throwable {
		assert( Logger.WORKFLOW_VERBOSE.isOn ? Logger.WORKFLOW_VERBOSE.printf("%s[%d].%s", wkf.getName(), wkf.getId(), event.source): true);
		
		switch( event.source ){
		case FlowEnd:
			break;
		}
	}

	@SuppressWarnings("incomplete-switch")
	@Override
	public void stateChange(WorkflowEvent event, IActivity<SPFlowProp,CONTEXT> act) throws Throwable {
		switch( event.source ){
		case ActivityEnd:
			activityCounter.incrementAndGet();
			activities.get(act.getClass().getSimpleName()).incrementAndGet();
			if( act instanceof SPStage ){
				stages.get(act.getContext().getWkfProp().getStageExec().get(act.getCode()).getClass().getSimpleName()).incrementAndGet();
			}
			break;
		}
	}

	@Override
	public void stateChange(WorkflowEvent event, ITool tool) throws Throwable {
		assert( Logger.TOOL_VERBOSE.isOn ? Logger.TOOL_VERBOSE.printf("%s[%d].%s", tool.getName(), tool.getId(), event.source): true);
	}

	
	
}
