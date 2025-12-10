package com.scotiabank.maestro.orch.services.workflow.definition;

import java.util.List;
import java.util.Map;

import ca.mss.rd.flow.prop.RdProp;

import com.scotiabank.maestro.msg.workflow.WorkflowValidationException;
import com.scotiabank.maestro.msg.workflow.condition.WorkflowValidationVO;

/**
 * Validates workflow properties against environmental configuration.
 */
public interface IWorkflowPropertyValidator<PROP extends RdProp> {
	
	public WorkflowValidationVO validatePropertiesAgainstEnvironment(List<PROP> props, Map<String, RdProp> globalProp) throws WorkflowValidationException;
	
}
