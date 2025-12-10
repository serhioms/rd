/**
 * 
 */
package ca.mss.rd.workflow.hard;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import ca.mss.rd.workflow.def.WkfActivity;
import ca.mss.rd.workflow.def.WkfContextFactory;
import ca.mss.rd.workflow.def.WkfTransition;
import ca.mss.rd.workflow.def.Workflow;
import ca.mss.rd.workflow.impl.WkfTransitionBean;
import ca.mss.rd.workflow.impl.dummy.WkfContextFactoryDummy;

/**
 * @author smoskov
 *
 */
public abstract class HardFlow implements Workflow {

	public static final String module = HardFlow.class.getName();

	private Set<WkfActivity> startActivities;
	
	private Map<WkfActivity, WkfTransition[]> splitTransitionMap;
	private Map<WkfActivity, WkfTransition[]> joinTransitionMap;
	
	private WkfContextFactory wkfContextFactory = new WkfContextFactoryDummy();


	/**
	 * @return List of all activities in workflow
	 */
	abstract public WkfActivity[] getActivities();
	
	/**
	 * 
	 */
	public HardFlow() {
		buildWorkflow();
	}


	/* (non-Javadoc)
	 * @see ca.mss.workflow.def.Workflow#getWkfContextFactory()
	 */
	@Override
	final public WkfContextFactory getWkfContextFactory() {
		return wkfContextFactory;
	}


	/**
	 * @param wkfContextFactory the wkfContextFactory to set
	 */
	final public void setWkfContextFactory(WkfContextFactory wkfContextFactory) {
		this.wkfContextFactory = wkfContextFactory;
	}


	/* (non-Javadoc)
	 * @see ca.mss.workflow.Workflow#getStartActivities()
	 */
	@Override
	final public Set<WkfActivity> getStartActivities() {
		return startActivities;
	}


	/* (non-Javadoc)
	 * @see ca.mss.workflow.Workflow#getSplitTransitions(ca.mss.workflow.WkfActivity)
	 */
	@Override
	final public WkfTransition[] getSplitTransitions(WkfActivity activity) {
		return splitTransitionMap.get(activity);
	}

	/* (non-Javadoc)
	 * @see ca.mss.workflow.Workflow#getJoinTransitions(ca.mss.workflow.WkfActivity)
	 */
	@Override
	final public WkfTransition[] getJoinTransitions(WkfActivity activity) {
		return joinTransitionMap.get(activity);
	}


	/**
	 *  Before start workflow it should be builded once
	 */
	public void buildWorkflow() {
		if( getActivities() != null ){
			startActivities = new HashSet<WkfActivity>();
			{
				WkfActivity[] activities = getActivities();
				{
					// Populate splitTransitionMap from workflow definition
					splitTransitionMap = new HashMap<WkfActivity, WkfTransition[]>();
					{
						for( int i=0; i<activities.length; i++){
							WkfTransition[] transition = getTransitions(activities[i]);
							splitTransitionMap.put(activities[i], transition);
							if( activities[i].getState().canStart() ){
								startActivities.add(activities[i]);
							}
						}
					}
					// Accumulate join transitions in temp
					Map<WkfActivity, Set<WkfTransition>> temp = new HashMap<WkfActivity, Set<WkfTransition>>();
					{
						for(int i=0; i<activities.length; i++){
							WkfTransition[] transition = getSplitTransitions(activities[i]);
							for(int j=0; transition != null && j<transition.length; j++){
								
								// Fulfill begin activity for all transitions
								((WkfTransitionBean )transition[j]).setBeginActivity(activities[i]);
				
								WkfActivity join = transition[j].getEndActivity();
								Set<WkfTransition> joins = temp.get(join);
								if( joins == null ){
									joins = new HashSet<WkfTransition>();
									temp.put(join, joins);
								}
								joins.add(transition[j]);
							}
						}
						
						// Populate joinTransitionMap from temp
						joinTransitionMap = new HashMap<WkfActivity, WkfTransition[]>();
						{
							for(Iterator<Entry<WkfActivity, Set<WkfTransition>>> iter = temp.entrySet().iterator(); iter.hasNext(); ){
								Entry<WkfActivity, Set<WkfTransition>> entry = iter.next();
								
								WkfTransition[] trans = new WkfTransition[entry.getValue().size()];
								entry.getValue().toArray(trans);
								entry.getValue().clear();
								
								joinTransitionMap.put(entry.getKey(), trans);
							}
						}
					}
					temp.clear();
				}
			}
		}
	}
}
