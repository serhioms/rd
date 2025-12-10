package ca.mss.rd.test.workflow.repo;

import ca.mss.rd.workflow.def.WkfActivity;
import ca.mss.rd.workflow.def.WkfActivityState;
import ca.mss.rd.workflow.def.WkfTransition;
import ca.mss.rd.workflow.hard.HardFlow;
import ca.mss.rd.workflow.impl.WkfActivityFactory;
import ca.mss.rd.workflow.impl.WkfTransitionFactory;

public class SplitJoinAnd2 extends HardFlow {
	
	public static final String module = SplitJoinAnd2.class.getName();

	/* (non-Javadoc)
	 * @see ca.mss.workflow.Workflow#getSplitTransitions(ca.mss.workflow.WkfActivity)
	 */
	@Override
	public WkfTransition[] getTransitions(WkfActivity activity) {
		switch((TestActivity )activity){
		case Start:
			return new WkfTransition[]{WkfTransitionFactory.Goto(TestActivity.AndSplitBegin)};
		case AndSplitBegin:
			return new WkfTransition[]{
					WkfTransitionFactory.Goto(TestActivity.SplitOne),
					WkfTransitionFactory.Goto(TestActivity.SplitTwo)
					};
		case SplitOne:
			return new WkfTransition[]{WkfTransitionFactory.Goto(TestActivity.Three)};
		case Three:
			return new WkfTransition[]{WkfTransitionFactory.Goto(TestActivity.Four)};
		case Four:
			return new WkfTransition[]{WkfTransitionFactory.Goto(TestActivity.AndJoinEnd)};
		case SplitTwo:
			return new WkfTransition[]{WkfTransitionFactory.Goto(TestActivity.AndJoinEnd)};
		case AndJoinEnd:
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
		AndSplitBegin (WkfActivityFactory.Tool().AutoAndSplit()),
		SplitOne (WkfActivityFactory.Tool()),
		SplitTwo (WkfActivityFactory.Tool()),
		Three (WkfActivityFactory.Tool()),
		Four (WkfActivityFactory.Tool()),
		AndJoinEnd (WkfActivityFactory.Tool().AutoAndJoin()),
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
