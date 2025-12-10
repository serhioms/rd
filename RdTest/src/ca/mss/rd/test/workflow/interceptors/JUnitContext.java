/**
 * 
 */
package ca.mss.rd.test.workflow.interceptors;



 import ca.mss.rd.workflow.def.WkfActivity;
import ca.mss.rd.workflow.def.WkfContext;
import ca.mss.rd.workflow.def.WkfData;
import ca.mss.rd.workflow.def.WkfTransition;

import org.apache.log4j.Logger;

/**
 * @author smoskov
 *
 */
public class JUnitContext implements WkfContext {

	public static final String module = JUnitContext.class.getName();
	private static org.apache.log4j.Logger logger = ca.mss.rd.util.Debug.getLogger(module);

	private WkfContext wkfContext;
	
	
	/* (non-Javadoc)
	 * @see ca.mss.workflow.def.WkfContext#evaluate(ca.mss.workflow.def.WkfTransition)
	 */
	/**
	 * @param wkfContext
	 */
	public JUnitContext(WkfContext wkfContext) {
		this.wkfContext = wkfContext;
	}

	/**
	 * @return the wkfContext
	 */
	public final WkfContext getWkfContext() {
		return wkfContext;
	}

	/**
	 * @param wkfContext the wkfContext to set
	 */
	public final void setWkfContext(WkfContext wkfContext) {
		this.wkfContext = wkfContext;
	}

	@Override
	public boolean evaluate(WkfTransition transition) {
		boolean result = wkfContext.evaluate(transition);
		if( transition.isConditional() )
			if( logger.isDebugEnabled()) logger.debug("EVALUATE[transition="+transition+"][result="+result+"][condition="+transition.getCondition()+"]");
		else if( transition.isOtherwise() )
			if( logger.isDebugEnabled()) logger.debug("EVALUATE[transition="+transition+"][otherwise]");
		else
			if( logger.isDebugEnabled()) logger.debug("EVALUATE[transition="+transition+"]");
		return result;
	}

	/* (non-Javadoc)
	 * @see ca.mss.workflow.def.WkfContext#run(ca.mss.workflow.def.WkfActivity)
	 */
	@Override
	public void run(WkfActivity activity) {
		wkfContext.run(activity);
		if( logger.isDebugEnabled()) logger.debug("RUN[activity="+activity+"][SUCCSEED]");
	}

	
	/* (non-Javadoc)
	 * @see ca.mss.workflow.def.WkfContext#getData()
	 */
	@Override
	public WkfData getData() {
		return wkfContext.getData();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return wkfContext.toString();
	}

	
}
