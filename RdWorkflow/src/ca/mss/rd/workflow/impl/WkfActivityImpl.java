/**
 * 
 */
package ca.mss.rd.workflow.impl;

import ca.mss.rd.workflow.def.WkfActivity;
import ca.mss.rd.workflow.def.WkfActivityState;

/**
 * @author mss
 *
 */
public class WkfActivityImpl extends WkfActivityBean implements WkfActivity {

	private WkfActivityState state;

	public WkfActivityImpl(WkfActivityState state) {
		setState(state);
	}

	/**
	 * @return the state
	 */
	public WkfActivityState getState() {
		return state;
	}
	/**
	 * @param state the state to set
	 */
	public void setState(WkfActivityState state) {
		this.state = state;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return getId();
	}

	
}
