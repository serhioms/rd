package com.scotiabank.maestro.orch.services.workflow.definition;

import ca.mss.rd.flow.IContext;
import ca.mss.rd.flow.IWorkflow;

import com.scotiabank.maestro.msg.workflow.condition.WorkflowValidationVO;

public interface IWorkflowBeanValidator<PROP, CONTEXT extends IContext<PROP>> {
	public WorkflowValidationVO validate(IWorkflow<PROP,CONTEXT> defn);
}
