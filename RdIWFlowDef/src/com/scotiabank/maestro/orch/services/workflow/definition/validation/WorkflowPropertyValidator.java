package com.scotiabank.maestro.orch.services.workflow.definition.validation;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.codehaus.plexus.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;

import ca.mss.rd.flow.IActivity;
import ca.mss.rd.flow.ITraverse;
import ca.mss.rd.flow.IWorkflow;
import ca.mss.rd.flow.prop.RdExec;
import ca.mss.rd.flow.prop.RdProp;
import ca.mss.rd.flow.prop.UtilProp;
import ca.mss.rd.flow.sp.SPActivity;
import ca.mss.rd.flow.sp.SPContext;
import ca.mss.rd.flow.sp.SPFlowProp;
import ca.mss.rd.flow.sp.SPParallel;
import ca.mss.rd.flow.sp.SPSequential;
import ca.mss.rd.flow.sp.SPStage;
import ca.mss.rd.flow.sp.SPStart;
import ca.mss.rd.flow.sp.SPSubflow;
import ca.mss.rd.util.Logger;
import ca.mss.rd.wkfdef.iwutil.IWHelper;

import com.scotiabank.maestro.msg.workflow.WorkflowValidationException;
import com.scotiabank.maestro.msg.workflow.condition.WorkflowValidationMessageVO;
import com.scotiabank.maestro.msg.workflow.condition.WorkflowValidationVO;
import com.scotiabank.maestro.orch.api.workflow.PropertyScopeTypeEnum;
import com.scotiabank.maestro.orch.services.workflow.definition.IWorkflowBeanValidator;
import com.scotiabank.maestro.orch.services.workflow.definition.IWorkflowPropertyValidator;
import com.scotiabank.maestro.orch.services.workflow.property.StageOutputPropertyScopeHelper;
import com.scotiabank.maestro.utils.token.TokenHelper;


public class WorkflowPropertyValidator implements IWorkflowBeanValidator<SPFlowProp,SPContext>, IWorkflowPropertyValidator<RdProp> {
	
	private static final String OUTPUT_PROPERTY_NAME_FORMAT_REGEX = "^[a-zA-Z][a-zA-Z0-9\\-\\.\\_]*$";
	private static final String PROPERTY_NAME_FORMAT_REGEX = "^#?[a-zA-Z][a-zA-Z0-9\\-\\.\\_]*$";

	@Autowired
	private StageOutputPropertyScopeHelper stageOutputPropertyScopeHelper;


	@Override
	public WorkflowValidationVO validate(IWorkflow<SPFlowProp,SPContext> defn) throws WorkflowValidationException {
		
		if (defn==null) {
			throw new WorkflowValidationException("Workflow is null");			
		}
		
		WorkflowValidationVO result = new WorkflowValidationVO();

		Map<String, RdProp> envProp = defn.getContext().getEnvProp();
		result.countGlob += envProp.size();

		defn.travers(new ITraverse<SPFlowProp,SPContext>(){
			public void visitor(IActivity<SPFlowProp,SPContext> activity, IWorkflow<SPFlowProp,SPContext> wkf) {
				validateActivity(activity, wkf, result, wkf.getContext().getExprContext(), envProp);
			}
		});
		
		return result;
	}
	
	protected void validateActivity(IActivity<SPFlowProp,SPContext> act, IWorkflow<SPFlowProp,SPContext> wkf, 
			WorkflowValidationVO result, Map<String, RdProp> wkfProp, Map<String, RdProp> envProp) throws WorkflowValidationException {

		if( "par.CTR-WORKFLOWS".equals(act.getName()) ){
			System.currentTimeMillis();
		}

		result.countActivity++;
		
		if (act.getClass().isAssignableFrom(SPStage.class)) {
			
			validateStage(wkfProp, (SPStage )act, result, envProp);
			
		} else if (act.getClass().isAssignableFrom(SPSubflow.class)) {

			validateSubflow(wkfProp, (SPSubflow)act, result, envProp);

		} else if (act.getClass().isAssignableFrom(SPSequential.class)) {
			
			validateSequential(wkfProp, (SPSequential)act, result, envProp);	
			
		} else if (act.getClass().isAssignableFrom(SPParallel.class)) {
			
			validateParallel(wkfProp, (SPParallel)act, result, envProp);
			
		} else if (act.getClass().isAssignableFrom(SPStart.class)) {
			
			result.countStart++;
			
			wkfProp.clear(); // clean properties before populate start one
			
			validateWkfProperty(IWHelper.<RdProp>getWkfPropGeneral(wkf), envProp, result, wkfProp, PropertyScopeTypeEnum.GENERAL);
			validateWkfProperty(IWHelper.<RdProp>getWkfPropInput(wkf), envProp, result, wkfProp, PropertyScopeTypeEnum.INPUT);
			validateWkfProperty(IWHelper.<RdProp>getWkfPropOutput(wkf), envProp, result, wkfProp, PropertyScopeTypeEnum.OUTPUT);
			
		} else {
			throw new WorkflowValidationException("No validation configured for node type: " + act.getClass().getName());
		}
		
		assert( Logger.WORKFLOW_VALIDATION.isOn ? Logger.WORKFLOW_VALIDATION.printf("%s %s", act, wkfProp): true);
	}


	protected void validateWkfProperty(List<RdProp> wkfProp, Map<String, RdProp> envProp, WorkflowValidationVO result, Map<String, RdProp> bothProp, PropertyScopeTypeEnum scope) throws WorkflowValidationException {

		switch( scope ){
		case GLOBAL:
			break;
		case GENERAL:
			result.countGen++;
			break;
		case INPUT:
			result.countIn++;
			break;
		case OUTPUT:
			result.countOut++;
			break;
		}
		
		for(RdProp prop: wkfProp){
			
			if(!bothProp.containsKey(prop.getName())) {
				validateWorkflowPropertyNotInEnvironmentalConfig(prop.getName(), envProp, result);
			}				
			
			if (scope.equals(PropertyScopeTypeEnum.OUTPUT)) {
				validateOutputPropertyNameFormat(prop.getName(), result);
			} else {			
				validatePropertyNameFormat(prop.getName(), result);
			}
			
			validatePropertyName(prop.getName(), bothProp, result);
			
			bothProp.put(prop.getName(), prop);
		
			List<String> invalidTokenVariables = TokenHelper.getInvalidTokenVariables(prop.getValue());
			if (!invalidTokenVariables.isEmpty()) {
				result.add(new WorkflowValidationMessageVO(String.format("%s property name[%s] has an invalid values[%s] in terms of syntax", scope.getDescriptor(), prop.getName(), prop.getValue())));
			}
		}
	}
	
	protected void validateWorkflowPropertyNotInEnvironmentalConfig(String propertyName, Map<String, RdProp> envProp, WorkflowValidationVO result) {
		if(envProp.keySet().contains(propertyName)) {			
			String message = String.format("[%s] is declared in both workflow properties and environmental configuration.  Only one allowed.", propertyName);
			WorkflowValidationMessageVO messageVO = new WorkflowValidationMessageVO(message);
			result.add(messageVO);
		}		
	}
	
	protected void validateOutputPropertyNameFormat(String propertyName, WorkflowValidationVO messageList)	throws WorkflowValidationException {
		if (!propertyName.matches(OUTPUT_PROPERTY_NAME_FORMAT_REGEX)) {
			String message = String.format("[%s] does not match an acceptable output property name format.", propertyName);
			WorkflowValidationMessageVO messageVO = new WorkflowValidationMessageVO(message);
			messageList.add(messageVO);
		}
	}

	protected void validatePropertyNameFormat(String propertyName, WorkflowValidationVO messageList)	throws WorkflowValidationException {
		if (!propertyName.matches(PROPERTY_NAME_FORMAT_REGEX)) {
			String message = String.format("[%s] does not match an acceptable property name format.", propertyName);
			WorkflowValidationMessageVO messageVO = new WorkflowValidationMessageVO(message);
			messageList.add(messageVO);
		}
	}

	protected void validatePropertyName(String propertyName, Map<String, RdProp> properties, WorkflowValidationVO messageList)
			throws WorkflowValidationException {
		if (properties.keySet().contains(propertyName) && !getStageOutputPropertyScopeHelper().isStagePropertyGlobalScope(propertyName)) {
			String message = String.format("[%s] conflicts with another declaration.", propertyName);
			WorkflowValidationMessageVO messageVO = new WorkflowValidationMessageVO(message);
			messageList.add(messageVO);
		}
	}

	private void validateSequential(Map<String, RdProp> resultProps, SPSequential sequence, WorkflowValidationVO result, Map<String, RdProp> envProp) throws WorkflowValidationException {

		result.countSeq++; 
		
		if( !sequence.isDisable() ){
			if( sequence.getSkipCondition() != null) {
				result.countSkip++;
				
				validateExpression(sequence.getSkipCondition(), resultProps, result, sequence, envProp);
			}
			if( sequence.getFailCondition() !=null ) {				
				result.countFail++;
				
				validateExpression(sequence.getFailCondition(),resultProps, result, sequence, envProp);
				
				if( sequence.getFailConditionMessage() !=null ) {
					validateExpression(sequence.getFailConditionMessage(),resultProps, result, sequence, envProp);
				}
			}
		} else {
			result.countDis++;
		}
	}

	private void validateParallel(Map<String, RdProp> resultProps, SPParallel parallel, WorkflowValidationVO result, Map<String, RdProp> envProp) throws WorkflowValidationException {

		result.countPar++; 

		if( !parallel.isDisable() ){
			if(parallel.getSkipCondition()!=null) {
				result.countSkip++;
				
				validateExpression(parallel.getSkipCondition(), resultProps, result, parallel, envProp);
			}
			
			if( parallel.getFailCondition() !=null ) {				
				result.countPar++;
				
				validateExpression(parallel.getFailCondition(), resultProps, result, parallel, envProp);
				
				if( parallel.getFailConditionMessage() !=null ) {
					validateExpression(parallel.getFailConditionMessage(), resultProps, result, parallel, envProp);
				}
			}
		} else {
			result.countDis++;
		}
	}
	

	protected void validateReferenceInputs(SPSubflow reference, Map<String, RdProp> envProp, WorkflowValidationVO result) {
		List<RdProp> inputProperties = reference.getContext().getWkfProp().getSubPropInput().get(reference.getCode());
		if (inputProperties != null) {
			for( RdProp inputParameter : inputProperties) {
				String inputParameterName = inputParameter.getName();
				if (envProp.keySet().contains(inputParameterName)) {
					WorkflowValidationMessageVO message = createWorkflowValidationMessage(reference, String.format("[%s] declared as both property and environmental configuration.", inputParameterName));
					result.add(message);
				}
			}
		}
	}
	
	
	protected void validateSubflow(Map<String, RdProp> resultProps, SPSubflow subflow, WorkflowValidationVO result, Map<String, RdProp> envProp) throws WorkflowValidationException {
		
		result.countSubflow++; 
		
		Map<String,RdProp> outputProperties = new HashMap<String,RdProp>();

		validateReferenceInputs(subflow, envProp, result);

		List<RdProp> inputProp = subflow.getContext().getWkfProp().getSubPropInput().get(subflow.getCode());
		List<RdProp> outputProp = subflow.getContext().getWkfProp().getSubPropOutput().get(subflow.getCode());

		if( outputProp != null ){	
			for (RdProp outputProperty : outputProp) {
				result.countOut++;
				
				String outputPropertyName = outputProperty.getName();
				validatePropertyNameFormat(getStageOutputPropertyScopeHelper().resolveStagePropertyKey(subflow.getCode(), outputPropertyName),result);
				validatePropertyName(getStageOutputPropertyScopeHelper().resolveStagePropertyKey(subflow.getCode(), outputPropertyName),resultProps, result);
				
				if( outputProperty.getTarget() != null ){
					outputPropertyName = outputProperty.getTarget();
					validatePropertyNameFormat(getStageOutputPropertyScopeHelper().resolveStagePropertyKey(subflow.getCode(), outputPropertyName),result);
					validatePropertyName(getStageOutputPropertyScopeHelper().resolveStagePropertyKey(subflow.getCode(), outputPropertyName),resultProps, result);
					
					outputProperties.put(outputPropertyName, UtilProp.toProp(outputPropertyName, "", PropertyScopeTypeEnum.GENERAL));
				} else {
					throw new RuntimeException(String.format("There is no mapping for output ptoperty %s", outputPropertyName));
				}
				
			}
			
			resultProps.putAll(appendStageName(subflow.getCode(), outputProperties));
		}
										
		if( !subflow.isDisable() ) {
			//validate that input properties have been defined already
			if( inputProp != null ){
				for (RdProp inputProperty : inputProp) {
					result.countIn++;
					
					String inputPropertyName = inputProperty.getName();
					String inputPropertyValue = inputProperty.getValue();
					validatePropertyNameFormat(subflow.getCode() + "." + inputPropertyName, result);
					//validate that input property expressions are valid
					validatePropertyName(subflow.getCode() + "." + inputPropertyName, resultProps, result);
					if (inputPropertyValue != null) {
						result.countIn++;
						validateExpression(inputPropertyValue, resultProps, result, subflow, envProp);
					}
				}
			}
			
			//validate the skip condition
			if( subflow.getSkipCondition() !=null ) {
				result.countSkip++;
				
				validateExpression(subflow.getSkipCondition(),resultProps, result, subflow, envProp);
			}
			
			
			if( subflow.getFailCondition() !=null ) {				
				result.countFail++;
				
				validateExpression(subflow.getFailCondition(), resultProps, result, subflow, envProp);
				
				if( subflow.getFailConditionMessage() !=null ) {
					validateExpression(subflow.getFailConditionMessage(), resultProps, result, subflow, envProp);
				}
			}
		} else {
			result.countDis++;
		}
	}

	protected void validateStage(Map<String, RdProp> resultProps, SPStage stage, WorkflowValidationVO result, Map<String, RdProp> envProp) throws WorkflowValidationException {
		
		result.countStage++;
		
		RdExec<String,RdProp> stageExe = stage.getContext().getWkfProp().getStageExec().get(stage.getCode());
		
		Map<String,RdProp> outputProperties = new HashMap<String,RdProp>();
		
		for (String outputPropertyName : stageExe.getOutSet()) {
			if( outputPropertyName != null ){
				result.countOut++;
				
				validatePropertyNameFormat(getStageOutputPropertyScopeHelper().resolveStagePropertyKey(stage.getCode(), outputPropertyName), result);
				validatePropertyName(getStageOutputPropertyScopeHelper().resolveStagePropertyKey(stage.getCode(), outputPropertyName), resultProps, result);
				
				outputProperties.put(outputPropertyName, UtilProp.toProp(outputPropertyName, "", PropertyScopeTypeEnum.GENERAL));
			}
		}
		
		resultProps.putAll(appendStageName(stage.getCode(), outputProperties));			
								
		if( !stage.isDisable() ) {
			for( String inputPropertyName : stageExe.getInSet()) {
				if( inputPropertyName != null ){
					result.countIn++;
					
					validatePropertyNameFormat(stage.getCode() + "." + inputPropertyName, result);
					validatePropertyName(stage.getCode() + "." + inputPropertyName, resultProps, result);
				}
			}
			
			//validate that input property expressions are valid
			for( String inputPropertyValue : stageExe.getValSet()) {
				if( inputPropertyValue != null) {
					if( inputPropertyValue != null ){
						result.countVal++;
						
						validateExpression(inputPropertyValue, resultProps, result, stage, envProp);
					}
				}
			}
			
			//validate the skip condition
			if( stage.getSkipCondition() != null ) {
				result.countSkip++;
				
				validateExpression(stage.getSkipCondition(),resultProps, result, stage, envProp);
			}
			
			if( stage.getFailCondition() !=null ) {
				result.countFail++;
				
				validateExpression(stage.getFailCondition(), resultProps, result, stage, envProp);	
				
				if (stage.getFailConditionMessage() !=null ) {
					validateExpression(stage.getFailConditionMessage(), resultProps, result, stage, envProp);
				}
			}
		} else {
			result.countDis++;
		}
		
	}
	
	protected void validateExpression(String propertyExpression, Map<String, RdProp> resultProps, WorkflowValidationVO result, SPActivity node, Map<String, RdProp> envProp) throws WorkflowValidationException {				
		validateExpression(propertyExpression, resultProps, envProp, new HashSet<String>(), result, node);		
	}
	
	protected void validateExpression(String propertyExpression, Map<String, RdProp> resultProps, Map<String, RdProp> envProp, Set<String> visitedProperties, 
			WorkflowValidationVO result, SPActivity node) throws WorkflowValidationException {
		
		List<String> variables = TokenHelper.getValidTokenVariables(propertyExpression);
		Set<String> visitedPropertiesThis = new HashSet<String>();
		visitedPropertiesThis.addAll(visitedProperties);
		
		for (String variable : variables) {
			
			result.countVar++;

			assert( Logger.WORKFLOW_VERBOSE.isOn ? Logger.WORKFLOW_VERBOSE.printf("%d) Variable [%s] trancate", result.countVar, variable): true);
			
			
			variable = variable.substring(2,variable.length()-1);
			
			WorkflowValidationMessageVO messageExpressionExists = validateExpressionInEitherWorkflowOrConfig(variable, resultProps, envProp, node);
			if(messageExpressionExists != null) {
				result.add(messageExpressionExists);
				continue;
			}
			WorkflowValidationMessageVO messageExpressionDuplicated = validateExpressionNotInBothWorkflowAndConfig(variable, resultProps, envProp, node);
			if(messageExpressionDuplicated != null) {
				result.add(messageExpressionDuplicated);
				continue;
			}
			WorkflowValidationMessageVO messageCycleExists = validateExpressionNoCycleExists(variable, visitedProperties, node);
			if(messageCycleExists != null) {
				result.add(messageCycleExists);
				continue;
			}			
			/*
			 * Validate both the properties in the workflow and the application configuration. 
			 */
			Set<String> loopSet = new HashSet<>(visitedPropertiesThis);
			loopSet.add(variable);
			RdProp workflowPropertyVO = resultProps.get(variable);			
			
			if(workflowPropertyVO != null) {
				if (workflowPropertyVO.getScope() != null && workflowPropertyVO.getScope().equals(PropertyScopeTypeEnum.OUTPUT)) {
					result.add(createWorkflowValidationMessage(node, String.format("You cannot reference an output property (%s) within the workflow.", workflowPropertyVO.getName())));
					continue;
				}				
				
				if (StringUtils.isNotBlank(workflowPropertyVO.getValue())) {
					validateExpression(workflowPropertyVO.getValue(), resultProps, envProp, loopSet, result, node);					
				}
			}
			
			RdProp configWorkflowPropertyVO = envProp.get(variable);
			if(configWorkflowPropertyVO != null && !StringUtils.isBlank(configWorkflowPropertyVO.getValue())) {
				validateExpression(configWorkflowPropertyVO.getValue(), resultProps, envProp, loopSet, result, node);
			}
		}
	}
	
	protected WorkflowValidationMessageVO validateExpressionNoCycleExists(String variable, Set<String> visitedProperties, SPActivity node) {
		WorkflowValidationMessageVO returnValue;
		if (visitedProperties.contains(variable)) {
			String message = "Invalid expression: cycle detected in property name references:"+variable;
			returnValue = createWorkflowValidationMessage(node, message);
		} else {
			returnValue = null;
		}
		return returnValue;
	}

	protected WorkflowValidationMessageVO createWorkflowValidationMessage(SPActivity node, String message) {
		WorkflowValidationMessageVO returnValue;
		if (node == null) {
			returnValue = new WorkflowValidationMessageVO(message);
		} else {					
			returnValue = new WorkflowValidationMessageVO(node.getCode(), node.getName(), message, false);				
		}
		return returnValue;
	}


	protected WorkflowValidationMessageVO validateExpressionNotInBothWorkflowAndConfig(String variable, Map<String, RdProp> workflowProperties,
			Map<String, RdProp> envProp, SPActivity node) {
		
		WorkflowValidationMessageVO returnValue;
		
		if (workflowProperties.keySet().contains(variable) && envProp.keySet().contains(variable)) {
			String message = "Invalid expression: property name cannot be defined in both workflow and application configuration:"+variable;
			returnValue = createWorkflowValidationMessage(node, message);
		} else {
			returnValue = null;
		}
		
		return returnValue;
	}

	/**
	 * If null, valid.
	 */
	protected WorkflowValidationMessageVO validateExpressionInEitherWorkflowOrConfig(String variable, Map<String, RdProp> wkfProp, Map<String, RdProp> envProp, SPActivity node) {
		
		WorkflowValidationMessageVO returnValue;
		
		if (!wkfProp.keySet().contains(variable) && !envProp.keySet().contains(variable)) {
			String message = String.format("Invalid expression: %s property name not defined in workflow or application configuration %s:", variable, node.getPathName());
			returnValue = createWorkflowValidationMessage(node, message);
		} else {
			returnValue = null;
		}
		
		return returnValue;
	}


	protected Map<String,RdProp> appendStageName(String stageXmlId, Map<String, RdProp> properties) {
		Map<String,RdProp> stageNameProperties = new HashMap<String,RdProp>(properties.size());
		for(Map.Entry<String,RdProp> entry: properties.entrySet()) {
			stageNameProperties.put(getStageOutputPropertyScopeHelper().resolveStagePropertyKey(stageXmlId, entry.getKey()), entry.getValue());
		}
		return stageNameProperties;
	}

	public StageOutputPropertyScopeHelper getStageOutputPropertyScopeHelper() {
		return stageOutputPropertyScopeHelper;
	}
	
	@Required
	public void setStageOutputPropertyScopeHelper(StageOutputPropertyScopeHelper stageOutputPropertyScopeHelper) {
		this.stageOutputPropertyScopeHelper = stageOutputPropertyScopeHelper;
	}
	
	
	@Override
	public WorkflowValidationVO validatePropertiesAgainstEnvironment(List<RdProp> envProp, Map<String, RdProp> globalProp) throws WorkflowValidationException {
		WorkflowValidationVO result = new WorkflowValidationVO();
		
		for (RdProp prop: envProp) {
			validateWorkflowPropertyNotInEnvironmentalConfig(prop.getName(), globalProp, result);
		}		
		
		return result;
	}
	
	
	

}
