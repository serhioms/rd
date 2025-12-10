/**
 * 
 */
package ca.mss.rd.trade.calypso.workflow;

import ca.mss.rd.trade.calypso.boprocess.mom.Advice;
import ca.mss.rd.trade.calypso.boprocess.mom.PaymentAdvice;
import ca.mss.rd.trade.value.ValueKickOffCutOff;

/**
 * @author moskovsk
 *
 */
public enum WkfRule {

	CheckKickOff(new PaymentAdvice(new ValueKickOffCutOff()));

	public Advice advice;

	/**
	 * @param advice
	 */
	private WkfRule(Advice advice) {
		this.advice = advice;
	}
	
	final public WkfRule populate(int val){
		advice.populate(val);
		return this;
	}
	
}



