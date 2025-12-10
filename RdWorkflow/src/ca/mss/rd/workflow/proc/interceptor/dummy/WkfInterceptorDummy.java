/**
 * 
 */
package ca.mss.rd.workflow.proc.interceptor.dummy;

import java.util.Set;

import ca.mss.rd.workflow.def.WkfActivity;
import ca.mss.rd.workflow.def.WkfData;
import ca.mss.rd.workflow.def.WkfTransition;
import ca.mss.rd.workflow.proc.WkfInterceptor;

/**
 * @author smoskov
 *
 */
public class WkfInterceptorDummy implements WkfInterceptor {


	/* (non-Javadoc)
	 * @see ca.mss.workflow.proc.WkfInterceptor#run(ca.mss.workflow.def.WkfActivity)
	 */
	@Override
	public void run(WkfActivity a) {
	}

	/* (non-Javadoc)
	 * @see ca.mss.workflow.proc.WkfInterceptor#exception(ca.mss.workflow.def.WkfActivity, java.lang.Exception)
	 */
	@Override
	public void exception(WkfActivity a, Exception e) {
	}

	/* (non-Javadoc)
	 * @see ca.mss.workflow.proc.WkfInterceptor#intransition(java.util.Set)
	 */
	@Override
	public void intransition(Set<WkfActivity> aset) {
	}

	/* (non-Javadoc)
	 * @see ca.mss.workflow.proc.WkfInterceptor#manualStart(ca.mss.workflow.def.WkfActivity)
	 */
	@Override
	public boolean manualStart(WkfActivity a) {
		return false;
	}

	/* (non-Javadoc)
	 * @see ca.mss.workflow.proc.WkfInterceptor#manualFinish(ca.mss.workflow.def.WkfActivity)
	 */
	@Override
	public boolean manualFinish(WkfActivity a) {
		return false;
	}

}
