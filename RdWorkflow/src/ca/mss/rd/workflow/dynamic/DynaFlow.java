/**
 * 
 */
package ca.mss.rd.workflow.dynamic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import ca.mss.rd.workflow.def.WkfActivity;
import ca.mss.rd.workflow.def.WkfActivityImplementation;
import ca.mss.rd.workflow.def.WkfActivityState;
import ca.mss.rd.workflow.def.WkfTransition;
import ca.mss.rd.workflow.def.WkfTransitionType;
import ca.mss.rd.workflow.hard.HardFlow;
import ca.mss.rd.workflow.impl.WkfActivityImpl;
import ca.mss.rd.workflow.impl.WkfStateBean;
import ca.mss.rd.workflow.impl.WkfStateImpl;
import ca.mss.rd.workflow.impl.WkfTransitionFactory;
import ca.mss.rd.workflow.reader.WkfReader;

/**
 * @author smoskov
 *
 */
abstract public class DynaFlow extends HardFlow {

	public static final String module = DynaFlow.class.getName();

	private WkfActivity[] activities; 
	private Map<WkfActivity, WkfTransition[]> transitions;
	
	abstract public WkfReader getWkfReader();
	
	/**
	 * 
	 */
	public DynaFlow() {
		buildWorkflow();
	}

	/* (non-Javadoc)
	 * @see ca.mss.workflow.Workflow#getTransitions(ca.mss.workflow.WkfActivity)
	 */
	@Override
	final public WkfTransition[] getTransitions(WkfActivity activity) {
		return transitions.get(activity); 
	}
	
	/* (non-Javadoc)
	 * @see ca.mss.workflow.impl.WorkflowImpl#getActivities()
	 */
	@Override
	final public WkfActivity[] getActivities() {
		return activities;
	}

	/*
	 *  Once Dyna workflow should be built
	 */
	public void buildWorkflow() {
		WkfReader wkfReader = getWkfReader();
		if( wkfReader != null ){
			// Let's assume just one process in a file
			int process = 0;
			{
				// Populate activities to list
				List<WkfActivity> alist = new ArrayList<WkfActivity>();
				{
					Map<String, WkfActivity> amap = new HashMap<String, WkfActivity>();
					{
						for(int activity=0, max3=wkfReader.getActivitySize(process); activity <max3; activity++){
							
							WkfStateBean state = new WkfStateImpl();
							
							state.setId(wkfReader.getActivityId(process, activity));
							state.setCanStart(wkfReader.getActivityCanStart(process, activity));
							state.setInstantiation(wkfReader.getActivityInstantiation(process, activity));
							state.setJoinOnStart(wkfReader.getActivityStartMode(process, activity));
							state.setPriority(wkfReader.getActivityPriority(process, activity));
							state.setSplitOnFinish(wkfReader.getActivityFinishMode(process, activity));
							state.setStop(false);
							WkfActivityImplementation activityImplementation = wkfReader.getActivityImplementation(process, activity);
							state.setImplementation(activityImplementation);
							if( activityImplementation == WkfActivityImplementation.Tool ){
								state.setTools(wkfReader.getActivityTool(process, activity));
							}
				
							WkfActivityImpl a = new WkfActivityImpl((WkfActivityState )state);
							a.setId(wkfReader.getActivityId(process, activity));
							a.setName(wkfReader.getActivityName(process, activity));
							a.setDescr(wkfReader.getActivityDesc(process, activity));
							
							alist.add(a);
							
							// Store activities by ID
							amap.put(wkfReader.getActivityId(process, activity), a);
						}
					
						// Populate activities to array
						activities = new WkfActivity[alist.size()];
						{
							alist.toArray(activities);
						}
					
						// Populate transitions to lists
						Map<WkfActivity, List<WkfTransition>> tmap = new HashMap<WkfActivity, List<WkfTransition>>();
						{
							for(int transition=0, max4=wkfReader.getTransitionSize(process); transition <max4; transition++){
								
								// Get from activity
								String frId = wkfReader.getTransitionFrom(process, transition);
								WkfActivity frActivity = amap.get(frId);
								
								// Get to activity
								String toId = wkfReader.getTransitionTo(process, transition);
								WkfActivity toActivity = amap.get(toId);
								
								// Get transitions from transition list
								List<WkfTransition> tlist = tmap.get(frActivity);
								
								if( tlist == null ){
									tlist = new ArrayList<WkfTransition>();
									tmap.put(frActivity, tlist);
								}
								
								// Create transition
								WkfTransition splitTransition = null;
								WkfTransitionType type = wkfReader.getTransitionType(process, transition);
								switch( type ){
								case Goto:
									splitTransition = WkfTransitionFactory.Goto(toActivity);
									break;
								case Condition:
									splitTransition = WkfTransitionFactory.ConditionalGoto(toActivity, wkfReader.getTransitionExpression(process, transition));
									break;
								case Otherwise:
									splitTransition = WkfTransitionFactory.Otherwise(toActivity);
									break;
								default:
									throw new RuntimeException("Unknown transition type ["+type+"]");
								}
								
								tlist.add(splitTransition);
							}		
						
							// Populate transitions to array
							transitions = new HashMap<WkfActivity, WkfTransition[]>();
							{
								Iterator<Entry<WkfActivity, List<WkfTransition>>> titer = tmap.entrySet().iterator();
								while( titer.hasNext() ){
									Entry<WkfActivity, List<WkfTransition>> tent = titer.next();
									WkfActivity activity = tent.getKey();
									List<WkfTransition> tlist = tent.getValue();
									
									// Populate each transition list to array
									WkfTransition[] t = new WkfTransition[tlist.size()];
									tlist.toArray(t);
									tlist.clear();
									
									transitions.put(activity, t);
								}
							}
						}
						tmap.clear();
						// Initial Data
						// TODO: Shoud be done in separate layer
						// getWkfContextFactory().setInitialData(wkfReader.getInitialData(process));
					}
					amap.clear();
				}
				alist.clear();
			}
			// build hard flow at the end
			super.buildWorkflow();
		}
	}
	
}
