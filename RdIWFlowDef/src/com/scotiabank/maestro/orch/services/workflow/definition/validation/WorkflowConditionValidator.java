package com.scotiabank.maestro.orch.services.workflow.definition.validation;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;

import ca.mss.rd.flow.IActivity;
import ca.mss.rd.flow.IContext;
import ca.mss.rd.flow.ITraverse;
import ca.mss.rd.flow.IWorkflow;

import com.scotiabank.maestro.msg.workflow.WorkflowValidationException;
import com.scotiabank.maestro.msg.workflow.condition.IConditionEvaluator;
import com.scotiabank.maestro.msg.workflow.condition.WorkflowValidationVO;
import com.scotiabank.maestro.orch.services.workflow.definition.IWorkflowBeanValidator;

public class WorkflowConditionValidator<PROP, CONTEXT extends IContext<PROP>> implements IWorkflowBeanValidator<PROP, CONTEXT>{
	
	@Autowired
	private IConditionEvaluator evaluator;
	
	public WorkflowConditionValidator() {
		
	}
	
	/**
	 * Validate the entire definition.
	 */
	@Override
	public WorkflowValidationVO validate(IWorkflow<PROP,CONTEXT> defn){
		if( defn == null) {
			throw new WorkflowValidationException("Workflow definition is null");
		}
		
		WorkflowValidationVO result = new WorkflowValidationVO();
		
		defn.travers(new ITraverse<PROP,CONTEXT>(){
			public void visitor(IActivity<PROP,CONTEXT> activity, IWorkflow<PROP,CONTEXT> wkf) {
				validateActivityCondition(activity, result);
			}
		});
		
		return result;
	}
	
	public void validateActivityCondition(IActivity<PROP,CONTEXT> activity, WorkflowValidationVO result) {
		
		result.countActivity++;
		
		String failCondition = activity.getFailCondition();
		if (StringUtils.isNotBlank(failCondition)) {
			result.countFail++;
			evaluator.validate(activity, failCondition, result);
		}
		
		String skipCondition = activity.getSkipCondition();
		if (StringUtils.isNotBlank(skipCondition)) {
			result.countSkip++;
			evaluator.validate(activity, skipCondition, result);
		}
	}

	
	public IConditionEvaluator getEvaluator() {
		return evaluator;
	}
	
	@Required
	public void setEvaluator(IConditionEvaluator evaluator) {
		this.evaluator = evaluator;
	}
}
