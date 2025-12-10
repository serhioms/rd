/**
 * 
 */
package ca.mss.rd.trade.calypso.workflow;

import ca.mss.rd.trade.calypso.refdata.EventClass;


/**
 * @author moskovsk
 *
 */
public enum WkfEventClass {

	PSEventMessage(EventClass.PSEventMessage),
	PSEventTrade(EventClass.PSEventTrade),
	PSEventTransfer(EventClass.PSEventTransfer);
	
	public EventClass eventClass;

	/**
	 * @param eventClass
	 */
	private WkfEventClass(EventClass eventClass) {
		this.eventClass = eventClass;
	}
	
}


