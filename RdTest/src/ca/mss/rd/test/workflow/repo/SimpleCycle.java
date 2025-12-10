package ca.mss.rd.test.workflow.repo;



 import ca.mss.rd.workflow.def.WkfActivity;
import ca.mss.rd.workflow.def.WkfActivityState;
import ca.mss.rd.workflow.def.WkfCondition;
import ca.mss.rd.workflow.def.WkfContext;
import ca.mss.rd.workflow.def.WkfContextFactory;
import ca.mss.rd.workflow.def.WkfData;
import ca.mss.rd.workflow.def.WkfTransition;
import ca.mss.rd.workflow.hard.HardFlow;
import ca.mss.rd.workflow.impl.WkfActivityFactory;
import ca.mss.rd.workflow.impl.WkfTransitionFactory;

import org.apache.log4j.Logger;

final public class SimpleCycle extends HardFlow implements WkfData, WkfContext, WkfContextFactory {
	
	public static final String module = SimpleCycle.class.getName();
	private static org.apache.log4j.Logger logger = ca.mss.rd.util.Debug.getLogger(module);
	
	public static int CYCLE_LIMIT = 10;

	private int counter = 0;

	/**
	 * 
	 */
	public SimpleCycle() {
		this.setWkfContextFactory(this);
	}

	/* (non-Javadoc)
	 * @see ca.mss.workflow.def.WkfContext#getData()
	 */
	@Override
	public WkfData getData() {
		return this;
	}

	/* (non-Javadoc)
	 * @see ca.mss.workflow.def.WkfContextFactory#createNewContext()
	 */
	@Override
	final public WkfContext createNewContext() {
		return this;
	}

	/* (non-Javadoc)
	 * @see ca.mss.workflow.def.WkfContext#evaluate(ca.mss.workflow.def.WkfTransition)
	 */
	@Override
	final public boolean evaluate(WkfTransition transition) {
		
		switch((TestCondition )transition.getCondition()){
		case StopperCondition:
			boolean result = (++counter==CYCLE_LIMIT);
			if( result )
				logger.info("Evaluate stop cycle #"+counter);
			else
				logger.info("Evaluate continue cycle #"+counter);
			return result;
		}
		throw new RuntimeException("No expression for transition "+transition);
	}

	/* (non-Javadoc)
	 * @see ca.mss.workflow.def.WkfContext#run(ca.mss.workflow.def.WkfActivity)
	 */
	@Override
	final public void run(WkfActivity activity) {
		switch((TestActivity )activity){
		case Worker:
			logger.info("Hello, Worker!");
			return;
		}
		throw new RuntimeException("No implementations for activity "+activity);
	}

	/* (non-Javadoc)
	 * @see ca.mss.workflow.Workflow#getSplitTransitions(ca.mss.workflow.WkfActivity)
	 */
	@Override
	final public WkfTransition[] getTransitions(WkfActivity activity) {
		switch((TestActivity )activity){
		case Start:
			return new WkfTransition[]{WkfTransitionFactory.Goto(TestActivity.Splitter)};
		case Splitter:
			return new WkfTransition[]{
					WkfTransitionFactory.Goto(TestActivity.Worker),
					WkfTransitionFactory.Goto(TestActivity.Stopper)
					};
		case Worker:
			return new WkfTransition[]{WkfTransitionFactory.Goto(TestActivity.Stop)};
		case Stopper:
			return new WkfTransition[]{
					WkfTransitionFactory.ConditionalGoto(TestActivity.Stop, TestCondition.StopperCondition),
					WkfTransitionFactory.Goto(TestActivity.Splitter)
					};
		default:
			return null; // End of Process 
		}
	}

	/* (non-Javadoc)
	 * @see ca.mss.workflow.impl.WorkflowImpl#getActivities()
	 */
	@Override
	final public TestActivity[] getActivities() {
		return TestActivity.values();
	}

	public enum TestActivity implements WkfActivity {
		Start (WkfActivityFactory.Start()),
		Splitter (WkfActivityFactory.Root().AutoAndSplit().Multiple()),
		Stopper (WkfActivityFactory.Root().Multiple()),
		Worker (WkfActivityFactory.Tool().Multiple()),
		Stop (WkfActivityFactory.Stop());

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
		final public WkfActivityState getState() {
			return state;
		}
	}

	public enum TestCondition implements WkfCondition {
		StopperCondition
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	final public String toString() {
		return "SimpleCycle[CYCLE_LIMIT="+CYCLE_LIMIT+"]";
	}

}
