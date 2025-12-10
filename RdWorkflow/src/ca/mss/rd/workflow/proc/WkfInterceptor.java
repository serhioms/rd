/**
 * 
 */
package ca.mss.rd.workflow.proc;

import java.util.Set;

import ca.mss.rd.workflow.def.WkfActivity;

/**
 * @author smoskov
 *
 */
public interface WkfInterceptor {

	public void run(WkfActivity a);
	public void exception(WkfActivity a, Exception e);

	public void intransition(Set<WkfActivity> aset);
	
	public boolean manualStart(WkfActivity a);
	public boolean manualFinish(WkfActivity a);

}
