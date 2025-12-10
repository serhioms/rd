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
public class BatchLoader extends HardFlow {
	
	public static final String module = BatchLoader.class.getName();

	public enum BatchLoaderActivity implements WkfActivity {
		Preprocessor (WkfActivityFactory.Start().Tool()),
		Validator (WkfActivityFactory.Tool()),
		Splitter (WkfActivityFactory.Tool()),
		Parser (WkfActivityFactory.Tool()),
		Transformer (WkfActivityFactory.Tool()),
		Loader (WkfActivityFactory.Stop().Tool());

		private WkfActivityState state;

		/**
		 * @param state
		 */
		private BatchLoaderActivity(WkfActivityState state) {
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

	/* (non-Javadoc)
	 * @see ca.mss.workflow.impl.WorkflowImpl#getActivities()
	 */
	@Override
	public WkfActivity[] getActivities() {
		return BatchLoaderActivity.values();
	}


	/* (non-Javadoc)
	 * @see ca.mss.workflow.Workflow#getSplitTransitions(ca.mss.workflow.WkfActivity)
	 */
	@Override
	public WkfTransition[] getTransitions(WkfActivity activity) {
		switch((BatchLoaderActivity )activity){
		case Preprocessor:
			return new WkfTransition[]{WkfTransitionFactory.Goto(BatchLoaderActivity.Validator)};
		case Validator:
			return new WkfTransition[]{WkfTransitionFactory.Goto(BatchLoaderActivity.Splitter)};
		case Splitter:
			return new WkfTransition[]{WkfTransitionFactory.Goto(BatchLoaderActivity.Parser)};
		case Parser:
			return new WkfTransition[]{WkfTransitionFactory.Goto(BatchLoaderActivity.Transformer)};
		case Transformer:
			return new WkfTransition[]{WkfTransitionFactory.Goto(BatchLoaderActivity.Loader)};
		default:
			return null; // accidental stop by default 
		}
	}

}
