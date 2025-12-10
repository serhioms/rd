package ca.mss.rd.workflow.dynamic;

import ca.mss.rd.workflow.def.WkfActivity;
import ca.mss.rd.workflow.def.WkfContext;
import ca.mss.rd.workflow.def.WkfTool;
import ca.mss.rd.workflow.def.WkfTransition;
import ca.mss.rd.workflow.dynamic.expression.WkfExpressionEngine;
import ca.mss.rd.workflow.dynamic.service.WkfProcedureDelegator;
import ca.mss.rd.workflow.dynamic.service.WkfServiceEngine;
import ca.mss.rd.workflow.impl.WkfConditionImpl;

public class DynaContext implements WkfContext {

	final public static String module = DynaContext.class.getName();
	static final long serialVersionUID = module.hashCode();
	
	private DynaData data;
	private WkfProcedureDelegator procedureDelegator;
	
	private WkfServiceEngine serviceEngine;
	private WkfExpressionEngine expressionEngine;
	
	/**
	 * @return the data
	 */
	final public DynaData getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	final public void setData(DynaData data) {
		this.data = data;
	}

	
	/**
	 * @return the service
	 */
	final public WkfServiceEngine getServiceEngine() {
		return serviceEngine;
	}

	/**
	 * @param service the service to set
	 */
	final public void setServiceEngine(WkfServiceEngine service) {
		this.serviceEngine = service;
	}

	/**
	 * @return the expressionEngine
	 */
	public final WkfExpressionEngine getExpressionEngine() {
		return expressionEngine;
	}


	/**
	 * @param expressionEngine the expressionEngine to set
	 */
	public final void setExpressionEngine(WkfExpressionEngine expressionEngine) {
		this.expressionEngine = expressionEngine;
	}

	/**
	 * @return the procedureDelegator
	 */
	public final WkfProcedureDelegator getProcedureDelegator() {
		return procedureDelegator;
	}

	/**
	 * @param procedureDelegator the procedureDelegator to set
	 */
	public final void setProcedureDelegator(WkfProcedureDelegator procedureDelegator) {
		this.procedureDelegator = procedureDelegator;
	}

	/* (non-Javadoc)
	 * @see ca.mss.workflow.def.WkfEvaluator#evaluate(ca.mss.workflow.def.WkfTransition)
	 */
	@Override
	public boolean evaluate(WkfTransition transition) {
		WkfConditionImpl condition = (WkfConditionImpl )transition.getCondition();
        return getExpressionEngine().evaluate(condition.getExpression(), getData());
	}
	
	
	/* (non-Javadoc)
	 * @see ca.mss.workflow.def.WkfActivityTool#run(ca.mss.workflow.def.WkfActivity)
	 */
	@Override
	public void run(WkfActivity activity) {
		WkfTool[] tools = activity.getState().getTools();
		for(int i=0; i<tools.length; i++){
			WkfTool tool = tools[i];
			switch( tool.getType() ){
			case Procedure:
				getServiceEngine().runProcedure(getProcedureDelegator(), tool, getData());
				continue;
			}
			throw new RuntimeException("Unknown tool type [type="+tool.getType()+"][tool="+tool+"]");
		}
	} 

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "DynaContext [data="+data+"]";
	}
	
}
