package ca.mss.rd.test.workflow.repo;

import ca.mss.rd.workflow.def.WkfActivity;
import ca.mss.rd.workflow.def.WkfActivityState;
import ca.mss.rd.workflow.def.WkfTransition;
import ca.mss.rd.workflow.hard.HardFlow;
import ca.mss.rd.workflow.impl.WkfActivityFactory;
import ca.mss.rd.workflow.impl.WkfTransitionFactory;

public class SplitXor extends HardFlow {
	
	public static final String module = SplitXor.class.getName();

	/* (non-Javadoc)
	 * @see ca.mss.workflow.Workflow#getSplitTransitions(ca.mss.workflow.WkfActivity)
	 */
	@Override
	public WkfTransition[] getTransitions(WkfActivity activity) {
		switch((TestActivity )activity){
		case Start:
			return new WkfTransition[]{WkfTransitionFactory.Goto(TestActivity.XorSplitBegin)};
		case XorSplitBegin:
			return new WkfTransition[]{
					WkfTransitionFactory.Goto(TestActivity.SplitOne),
					WkfTransitionFactory.Goto(TestActivity.SplitTwo)
					};
		case SplitOne:
			return new WkfTransition[]{WkfTransitionFactory.Goto(TestActivity.Stop)};
		case SplitTwo:
			return new WkfTransition[]{WkfTransitionFactory.Goto(TestActivity.Stop)};
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
	public TestActivity[] getActivities() {
		return TestActivity.values();
	}

	public enum TestActivity implements WkfActivity {
		Start (WkfActivityFactory.Start().Tool()),
		XorSplitBegin (WkfActivityFactory.Tool().AutoXorSplit()),
		SplitOne (WkfActivityFactory.Tool()),
		SplitTwo (WkfActivityFactory.Tool().Priority(100)),
		Stop (WkfActivityFactory.Stop().Tool());

		private WkfActivityState state;

		/**
		 * @param state
		 */
		private TestActivity(WkfActivityState state) {
			this.state = state;
		}

		/* (non-Javadoc)
		 * @see ca.mss.workflow.WkfActivity#getState()
		 */
		@Override
		public WkfActivityState getState() {
			return state;
		}
	}

}
