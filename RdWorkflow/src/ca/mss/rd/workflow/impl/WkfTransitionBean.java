/**
 * 
 */
package ca.mss.rd.workflow.impl;

import ca.mss.rd.workflow.def.WkfActivity;

/**
 * @author smoskov
 *
 */
public abstract class WkfTransitionBean {

	private String id;
	private WkfActivity beginActivity;
	private WkfActivity endActivity;
	
	/**
	 * @param endActivity the endActivity to set
	 */
	public void setEndActivity(WkfActivity endActivity) {
		this.endActivity = endActivity;
	}

	/**
	 * @return the activity
	 */
	public WkfActivity getEndActivity() {
		return endActivity;
	}

	/**
	 * @param activity the activity to set
	 */
	public void setBeginActivity(WkfActivity activity) {
		this.beginActivity = activity;
	}

	/**
	 * @return the beginActivity
	 */
	public WkfActivity getBeginActivity() {
		return beginActivity;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		if( id == null ){
			setId("FR_"+getBeginActivity()+"_TO_"+getEndActivity());
		}
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return getId();
	}

	
}
