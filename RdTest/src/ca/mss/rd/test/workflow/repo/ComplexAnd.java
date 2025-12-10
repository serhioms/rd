package ca.mss.rd.test.workflow.repo;

import ca.mss.rd.workflow.def.WkfActivity;
import ca.mss.rd.workflow.def.WkfActivityState;
import ca.mss.rd.workflow.def.WkfTransition;
import ca.mss.rd.workflow.hard.HardFlow;
import ca.mss.rd.workflow.impl.WkfActivityFactory;
import ca.mss.rd.workflow.impl.WkfTransitionFactory;

public class ComplexAnd extends HardFlow {
	
	public static final String module = SplitAnd.class.getName();
	
	/* (non-Javadoc)
	 * @see ca.mss.workflow.Workflow#getSplitTransitions(ca.mss.workflow.WkfActivity)
	 */
	@Override
	public WkfTransition[] getTransitions(WkfActivity activity) {
		switch((TestActivity )activity){
		case Start:
			return new WkfTransition[]{WkfTransitionFactory.Goto(TestActivity.Test1), WkfTransitionFactory.Goto(TestActivity.Test2), WkfTransitionFactory.Goto(TestActivity.Test29)};
		case Test1:
			return new WkfTransition[]{WkfTransitionFactory.Goto(TestActivity.Test4)};
		case Test2:
			return new WkfTransition[]{WkfTransitionFactory.Goto(TestActivity.Test4), WkfTransitionFactory.Goto(TestActivity.Test5)};
		case Test29:
			return new WkfTransition[]{WkfTransitionFactory.Goto(TestActivity.Test3)};
		case Test3:
			return new WkfTransition[]{WkfTransitionFactory.Goto(TestActivity.Test5)};
		case Test4:
			return new WkfTransition[]{WkfTransitionFactory.Goto(TestActivity.Stop)};
		case Test5:
			return new WkfTransition[]{WkfTransitionFactory.Goto(TestActivity.Stop)};
		case Stop:
			return new WkfTransition[]{WkfTransitionFactory.Goto(TestActivity.Start)};
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
		Start (WkfActivityFactory.Start().AutoAndSplit().Multiple()),
		Test1 (WkfActivityFactory.Root().Multiple()),
		Test2 (WkfActivityFactory.Root().Multiple()),
		Test29 (WkfActivityFactory.Root().ManualXorJoin().Multiple()),
		Test3 (WkfActivityFactory.Root().Multiple()),
		Test4 (WkfActivityFactory.Root().AutoAndJoin().Multiple()),
		Test5 (WkfActivityFactory.Root().AutoAndJoin().Multiple()),
		Stop (WkfActivityFactory.Stop().AutoAndJoin().Multiple());

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