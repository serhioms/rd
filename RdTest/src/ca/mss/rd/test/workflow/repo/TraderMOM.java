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
public class TraderMOM extends HardFlow {
	
	public static final String module = TraderMOM.class.getName();

	public enum TraderMOMActivity implements WkfActivity {
		Quote (WkfActivityFactory.Start().Tool().AutoAndSplit()), 
		Indicator (WkfActivityFactory.Tool().AutoAndSplit()),
		PnL (WkfActivityFactory.Tool().AutoAndSplit()),
		Robot (WkfActivityFactory.Tool().AutoAndJoin()),
		Graph (WkfActivityFactory.Stop().Tool().AutoAndJoin());

		private WkfActivityState state;

		private TraderMOMActivity(WkfActivityState state) {
			this.state = state;
		}

		@Override
		public WkfActivityState getState() {
			return state;
		}
	}

	@Override
	public WkfActivity[] getActivities() {
		return TraderMOMActivity.values();
	}


	@Override
	public WkfTransition[] getTransitions(WkfActivity activity) {
		switch((TraderMOMActivity )activity){
		case Quote:
			return new WkfTransition[]{
					WkfTransitionFactory.Goto(TraderMOMActivity.Graph), 
					WkfTransitionFactory.Goto(TraderMOMActivity.Indicator), 
					WkfTransitionFactory.Goto(TraderMOMActivity.PnL)};
		case Indicator:
			return new WkfTransition[]{
					WkfTransitionFactory.Goto(TraderMOMActivity.Graph), 
					WkfTransitionFactory.Goto(TraderMOMActivity.Robot)};
		case PnL:
			return new WkfTransition[]{
					WkfTransitionFactory.Goto(TraderMOMActivity.Graph), 
					WkfTransitionFactory.Goto(TraderMOMActivity.Robot)};
		case Robot:
			return new WkfTransition[]{
					WkfTransitionFactory.Goto(TraderMOMActivity.Graph)};
		default:
			return null; // accidental stop by default 
		}
	}

}
