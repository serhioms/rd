/**
 * 
 */
package ca.mss.rd.test.workflow.repo;

import ca.mss.rd.workflow.def.WkfActivity;
import ca.mss.rd.workflow.def.WkfActivityState;
import ca.mss.rd.workflow.def.WkfTransition;
import ca.mss.rd.workflow.hard.HardFlow;
import ca.mss.rd.workflow.impl.WkfActivityFactory;
import ca.mss.rd.workflow.impl.WkfTransitionFactory;

/**
 * @author smoskov
 *
 */
final public class HelloWorld extends HardFlow {
	
	public static final String module = HelloWorld.class.getName();
	
	/* (non-Javadoc)
	 * @see ca.mss.workflow.Workflow#getSplitTransitions(ca.mss.workflow.WkfActivity)
	 */
	@Override
	final public WkfTransition[] getTransitions(WkfActivity activity) {
		switch((HelloWorldActivity )activity){
		case Start:
			return new WkfTransition[]{WkfTransitionFactory.Goto(HelloWorldActivity.HelloWorld)};
		case HelloWorld:
			return new WkfTransition[]{WkfTransitionFactory.Goto(HelloWorldActivity.Stop)};
		case Stop:
			return  new WkfTransition[]{}; // End of Workflow
		default:
			return null; // End of Process 
		}
	}

	/* (non-Javadoc)
	 * @see ca.mss.workflow.impl.WorkflowImpl#getActivities()
	 */
	@Override
	final public HelloWorldActivity[] getActivities() {
		return HelloWorldActivity.values();
	}

	public enum HelloWorldActivity implements WkfActivity {
		Start (WkfActivityFactory.Start().Tool()),
		HelloWorld (WkfActivityFactory.Tool()),
		Stop (WkfActivityFactory.Stop().Tool());

		private WkfActivityState state;

		/**
		 * @param state
		 */
		private HelloWorldActivity(WkfActivityState state) {
			this.state = state;
		}

		/* (non-Javadoc)
		 * @see ca.mss.workflow.WkfActivity#getState()
		 */
		@Override
		final public WkfActivityState getState() {
			return state;
		}
	}

}
