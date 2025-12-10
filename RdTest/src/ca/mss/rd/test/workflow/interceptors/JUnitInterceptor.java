/**
 * 
 */
package ca.mss.rd.test.workflow.interceptors;

import java.util.Iterator;
import java.util.Set;



 import ca.mss.rd.workflow.def.WkfActivity;
import ca.mss.rd.workflow.def.WkfActivityMode;
import ca.mss.rd.workflow.impl.WkfStateBean;
import ca.mss.rd.workflow.proc.WkfInterceptor;

import org.apache.log4j.Logger;

/**
 * @author smoskov
 *
 */
public class JUnitInterceptor implements WkfInterceptor {

	public static final String module = JUnitInterceptor.class.getName();
	private static org.apache.log4j.Logger logger = ca.mss.rd.util.Debug.getLogger(module);

	/* (non-Javadoc)
	 * @see ca.mss.workflow.proc.WkfInterceptor#root(ca.mss.workflow.def.WkfActivity)
	 */
	@Override
	public void run(WkfActivity a) {
		if( logger.isDebugEnabled()) logger.debug(a.getState().getImplementation()+"[activity="+a+"]");
	}

	/* (non-Javadoc)
	 * @see ca.mss.workflow.proc.WkfInterceptor#exception(ca.mss.workflow.def.WkfActivity, java.lang.Exception)
	 */
	@Override
	public void exception(WkfActivity a, Exception e) {
		logger.error("Exception[activity="+a+"]", e);
	}

	/* (non-Javadoc)
	 * @see ca.mss.workflow.proc.WkfInterceptor#intransition(java.util.Set)
	 */
	@Override
	public void intransition(Set<WkfActivity> aset) {
		if( !aset.isEmpty() ){
			Iterator<WkfActivity> aiter = aset.iterator();
			while( aiter.hasNext() ){
				if( logger.isDebugEnabled()) logger.debug("InTransition[activity="+aiter.next()+"]");
			}
		} else {
			if( logger.isDebugEnabled()) logger.debug("Workflow successfully finished!\n");
		}
		
	}

	/* (non-Javadoc)
	 * @see ca.mss.workflow.proc.WkfInterceptor#manualStart(ca.mss.workflow.def.WkfActivity)
	 */
	@Override
	public boolean manualStart(WkfActivity a) {
		if( logger.isDebugEnabled()) logger.debug("ManualStart[activity="+a+"]");
		
		// Convert manual activities to authomatic on next step
		WkfStateBean bean = (WkfStateBean )a.getState();
		switch( bean.getJoinOnStart() ){
		case ManualXor:
			bean.setJoinOnStart(WkfActivityMode.AutoXor);
			return true;
		case ManualAnd:
			bean.setJoinOnStart(WkfActivityMode.AutoAnd);
			return true;
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see ca.mss.workflow.proc.WkfInterceptor#manualFinish(ca.mss.workflow.def.WkfActivity)
	 */
	@Override
	public boolean manualFinish(WkfActivity a) {
		if( logger.isDebugEnabled()) logger.debug("ManualFinish[activity="+a+"]");
		
		// Convert manual activities to authomatic on next step
		WkfStateBean bean = (WkfStateBean )a.getState();
		switch( bean.getSplitOnFinish() ){
		case ManualXor:
			bean.setSplitOnFinish(WkfActivityMode.AutoXor);
			return true;
		case ManualAnd:
			bean.setSplitOnFinish(WkfActivityMode.AutoAnd);
			return true;
		}
		return false;
	}

	
}
