/**
 * 
 */
package ca.mss.rd.trade.calypso.boprocess.engine;

import java.util.HashSet;
import java.util.Set;

import ca.mss.rd.trade.calypso.boprocess.mom.Advice;
import ca.mss.rd.trade.calypso.boprocess.mom.PaymentAdvice;
import ca.mss.rd.trade.calypso.environment.Property;
import ca.mss.rd.trade.calypso.environment.PropertyDefault;
import ca.mss.rd.trade.calypso.workflow.Workflow;

/**
 * @author moskovsk
 * 
 * Once trades have been captured, they can automatically trigger back office processes. The following documents will 
 * help you preparing your environment for back office processing:
 * 
 *  Message configurations setup and messages processing
 *  
 */
abstract public class MessageEngine extends Engine {

	abstract public void createAdvice(Advice advice);
	abstract public Workflow getWorkflow(PaymentAdvice paymentAdvice);
	
	public Set<Property> properties = new HashSet<Property>();;

	/**
	 * 
	 */
	public MessageEngine() {
		properties.add(PropertyDefault.ADVICE_ON_SETTLEDATE);
	}
	
	
}


