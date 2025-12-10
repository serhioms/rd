package ng.ca.mss.rd.trader;

import ca.mss.rd.flow.IActivity;
import ca.mss.rd.flow.ITool;
import ca.mss.rd.flow.IWorkflow;
import ca.mss.rd.flow.event.WorkflowEvent;
import ca.mss.rd.flow.event.WorkflowListener;
import ca.mss.rd.flow.sp.SPContext;
import ca.mss.rd.flow.sp.SPFlowProp;
import ca.mss.rd.flow.sp.SPStage;
import ca.mss.rd.flow.sp.SPStart;
import ca.mss.rd.flow.sp.SPSubflow;
import ca.mss.rd.util.Logger;
import ca.mss.rd.util.UtilString;

public class WorkflowPlan implements WorkflowListener<SPFlowProp,SPContext> {

	private int maxLevel;
	
	public WorkflowPlan(int maxLevel) {
		this.maxLevel = maxLevel;
		Logger.ACTIVITY.isOn = true;
	}

	@Override
	public void stateChange(WorkflowEvent event, IWorkflow<SPFlowProp,SPContext> wkf) throws Throwable {
	}

	@SuppressWarnings("incomplete-switch")
	@Override
	public void stateChange(WorkflowEvent event, IActivity<SPFlowProp,SPContext> act) throws Throwable {
		switch( event.source ){
		case ActivityEnd:
			int level = act.getLevel();
			
			if( level < maxLevel ){
				String props = "";
				
				if( act instanceof SPStart ){
					props = act.getContext().getWkfProp().getWkfPropGeneral().toString()+act.getContext().getWkfProp().getWkfPropInput().toString()+act.getContext().getWkfProp().getWkfPropOutput().toString();
				} else if( act instanceof SPSubflow ){
					props = act.getContext().getWkfProp().getSubPropInput().toString()+act.getContext().getWkfProp().getSubPropOutput().toString();
				} else if( act instanceof SPStage ){
					Object stageProps = act.getContext().getWkfProp().getStageExec().get(act.getCode());
					if( stageProps != null ){
						props = stageProps.toString();
					}
				}
					
				assert( Logger.ACTIVITY.isOn ? Logger.ACTIVITY.printf("%-90s|%s|%-20s|%-20s|%s", String.format("%s%s%s", UtilString.space(level), UtilString.space(level), act.getName()), act.isDisable()?"OFF":"ON ", UtilString.substring(act.getSkipCondition(), 17, "..."), UtilString.substring(act.getFailCondition(), 17, "..."), props): true);
			}
		}
	}

	@Override
	public void stateChange(WorkflowEvent event, ITool tool) throws Throwable {
	}
}
