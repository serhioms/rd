package ca.mss.rd.workflow.hard;

import ca.mss.rd.workflow.def.WkfActivity;
import ca.mss.rd.workflow.def.WkfContext;
import ca.mss.rd.workflow.def.WkfTransition;
import ca.mss.rd.workflow.dynamic.DynaContext;
import ca.mss.rd.workflow.dynamic.DynaData;

abstract public class HardContext extends DynaContext implements WkfContext {

	final public static String module = HardContext.class.getName();
	static final long serialVersionUID = module.hashCode();
	
	abstract public boolean evaluateTransition(WkfTransition transition, DynaData data);
	abstract public void runActivity(WkfActivity activity, DynaData data);
	
	
	/* (non-Javadoc)
	 * @see ca.mss.workflow.def.WkfEvaluator#evaluate(ca.mss.workflow.def.WkfTransition)
	 */
	@Override
	final public boolean evaluate(WkfTransition transition) {
        return super.evaluate(transition);
	}
	
	
	/* (non-Javadoc)
	 * @see ca.mss.workflow.def.WkfActivityTool#run(ca.mss.workflow.def.WkfActivity)
	 */
	@Override
	final public void run(WkfActivity activity) {
		runActivity(activity, getData());
	} 

	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "HardContext [data="+getData()+"]";
	}
	
}
