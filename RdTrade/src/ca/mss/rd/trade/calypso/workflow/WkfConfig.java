/**
 * 
 */
package ca.mss.rd.trade.calypso.workflow;

import ca.mss.rd.trade.calypso.boprocess.mom.MessageType;
import ca.mss.rd.trade.calypso.refdata.trade.ProcessingOrganization;
import ca.mss.rd.trade.calypso.refdata.trade.TradeAction;
import ca.mss.rd.trade.calypso.refdata.trade.TradeStatus;
import ca.mss.rd.trade.calypso.refdata.trade.product.ProductType;
import ca.mss.rd.trade.value.ValueBoolean;

/**
 * @author moskovsk
 *
 */
public class WkfConfig {

	final static public String className = WkfConfig.class.getName();
	final static public long serialVersionUID = className.hashCode();

	public ProcessingOrganization ProcessingOrg; // null means ALL 
	
	public WkfEventClass eventClass;
	public MessageType subtype;
	public ProductType product;
	
	public TradeStatus origStatus, resultStatus; 
	public TradeAction action; 
	
	public ValueBoolean differentUser, createTask, useSTP, useKickOffCutOff, logCommented, prefferedAction, updateOnly, generateIntermediaryEvent, needsManualAuthorization;
	
	private WkfRuleTransfer[] ruleTransfer;

	/**
	 * 
	 */
	public WkfConfig(int id, TradeStatus origStatus, TradeAction action, TradeStatus resultStatus, 
			boolean differentUser, boolean useSTP, boolean logCommented,
			MessageType subtype, ProductType productType, WkfRuleTransfer[] ruleTransfer, ProcessingOrganization procOrg,
			boolean useKickOffCutOff, boolean createTask, String comment,String filter, boolean prefferedAction,
			int confKoCo, boolean updateOnly, boolean generateIntermediaryEvent, boolean needsManualAuthorization){
		this.differentUser = new ValueBoolean(false);
		this.createTask = new ValueBoolean(false);
		this.useSTP = new ValueBoolean(true);
		this.useKickOffCutOff = new ValueBoolean(true);
		this.logCommented = new ValueBoolean(false);
		this.prefferedAction = new ValueBoolean(false);
		this.updateOnly = new ValueBoolean(false);
	}
	

}


