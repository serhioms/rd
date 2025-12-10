package com.scotiabank.maestro.orch.services.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import ca.mss.rd.flow.prop.RdProp;
import ca.mss.rd.util.Logger;

import com.scotiabank.maestro.utils.token.TokenHelper;

public class ConfigServiceImpl {
	
	protected static class CircularDependencyException extends Exception {

		private static final long serialVersionUID = 5244941592346380359L;
		
		private List<String> namesProcessed;
		
		public CircularDependencyException(List<String> namesProcessed) {
			this.namesProcessed = namesProcessed;
		}
	}
	
	private static final int MAX_RECURSIONS = 200;
	
	public List<String> validateConfigWorkflowProperties(Map<String, RdProp> map) {		
		List<String> errorMessages = new ArrayList<>();
		// Validate all names unique.
		if(errorMessages.isEmpty()) {
			validateConfigWorkflowPropertiesValidNameFormat(map, errorMessages);
		}
		if(errorMessages.isEmpty()) {
			validateConfigWorkflowPropertiesInvalidTokens(map, errorMessages);
		}		 
		if(errorMessages.isEmpty()) {			
			validateConfigWorkflowPropertiesNoDeclaredKeys(map, errorMessages);
		}
		if(errorMessages.isEmpty()) {
			validateConfigWorkflowPropertiesCircularDependencies(map, errorMessages);
		}		
		return errorMessages;
	}
	
	
	protected void validateConfigWorkflowPropertiesValidNameFormat(Map<String, RdProp> map, List<String> errorMessages) {
		for (String propertyName : map.keySet()) {
			if(!isValidPropertyNameFormat(propertyName)) {
				String errorMessage = String.format("Property name is in invalid format [%s]", propertyName);
				errorMessages.add(errorMessage);
			}
		}
	}
	
	protected boolean isValidPropertyNameFormat(String propertyName) {
		String regex = "^[a-zA-Z][a-zA-Z0-9\\.\\_]*$";		
		return propertyName.matches(regex );
	}

	protected void validateConfigWorkflowPropertiesInvalidTokens(Map<String, RdProp> configWorkflowPropertyVOs, List<String> errorMessages) {
		List<RdProp> valuesContainingInvalidTokenVariables = findInvalidTokenValues(configWorkflowPropertyVOs);					
		if(!valuesContainingInvalidTokenVariables.isEmpty()) {			
			for (RdProp vo : valuesContainingInvalidTokenVariables) {
				String errorMessage = String.format("Property contains invalid variable syntax [name=%s][value=%s]", vo.getName(), vo.getValue());
				errorMessages.add(errorMessage);
			}			
		}
	}

	/**
	 * Find syntax errors in variable references (ex: ${{x})
	 * @param map
	 * @return
	 */
	protected List<RdProp> findInvalidTokenValues(Map<String, RdProp> map) {
		List<RdProp> invalidProps = new ArrayList<>();
		
		for(RdProp prop : map.values()) {			
			String propertyValue = prop.getValue();
			if( propertyValue == null )
				propertyValue = prop.getTarget();
			
			List<String> invalidTokenVariables = TokenHelper.getInvalidTokenVariables(propertyValue);
			if(!invalidTokenVariables.isEmpty()) {
				invalidProps.add(prop);
			}
		}
		return invalidProps;
	}

	protected void validateConfigWorkflowPropertiesNoDeclaredKeys(Map<String, RdProp> prop, List<String> errorMessages) {
		List<RdProp> vosWithoutDeclaredKeys = findValuesWithoutDeclaredKeys(prop);
		if(!vosWithoutDeclaredKeys.isEmpty()) {
			for (RdProp vo : vosWithoutDeclaredKeys) {
				String errorMessage = String.format("Value references property name not declared [name=%s][value=%s]", vo.getName(), vo.getValue());
				errorMessages.add(errorMessage);
			}
		}
	}
	
	protected List<RdProp> findValuesWithoutDeclaredKeys(Map<String, RdProp> prop) {
		List<RdProp> returnValue = new ArrayList<>();
		for (RdProp vo : prop.values()) {
			String propertyName = vo.getName();
			String propertyValue = vo.getValue();
			List<String> validTokenVariables = TokenHelper.getValidTokenVariables(propertyValue);
			for (String validTokenVariable : validTokenVariables) {				
				String variableWithoutDollarBraces = TokenHelper.getRawTokenVariable(validTokenVariable);				
				if(propertyName.equals(variableWithoutDollarBraces) || !prop.containsKey(variableWithoutDollarBraces)) {
					returnValue.add(vo);
				}
			}
		}
		
		return returnValue;
	}

	protected void validateConfigWorkflowPropertiesCircularDependencies(Map<String, RdProp> prop,
			List<String> errorMessages) {
		Map<String, RdProp> mapConfigWorkflowPropertyVOs = prop;
		List<String> namesProcessedCircularDependency = findCircularReferences(mapConfigWorkflowPropertyVOs);
		if(!namesProcessedCircularDependency.isEmpty()) {
			StringBuilder sb = new StringBuilder();
			sb.append("Circular dependency among properties:  ");
			for (String propertyName : namesProcessedCircularDependency) {
				sb.append("[");
				sb.append(propertyName);
				sb.append("]");					
			}
			errorMessages.add(sb.toString());
		}
	}
	
	protected List<String> findCircularReferences(Map<String, RdProp> prop) {
		List<String> namesProcessed = new ArrayList<>();
		
		Set<Entry<String, RdProp>> entrySet = prop.entrySet();
		
		for (Entry<String, RdProp> entry : entrySet) {
			String propertyNameKey = entry.getKey();
			RdProp propertyVOValue = entry.getValue();
			String propertyValue = propertyVOValue.getValue();
			try {
				checkCircularDependency(prop, propertyNameKey, propertyValue, new ArrayList<String>(), 0);
			} catch (CircularDependencyException e) {
				Logger.WORKFLOW_VALIDATION.printf("Circular depdency found.", e);
				namesProcessed = e.namesProcessed;
			}			
		}
		return namesProcessed;
	}

	protected void checkCircularDependency(Map<String, RdProp> prop, String propertyNameKey,
			String propertyValue, List<String> namesProcessed, int recursionLevel) throws CircularDependencyException {
		if(recursionLevel > MAX_RECURSIONS) {
			throw new IllegalArgumentException("Maximum recursion levels exceeded");
		}
		namesProcessed.add(propertyNameKey);
		List<String> validTokenVariables = TokenHelper.getValidTokenVariables(propertyValue);
		if (!validTokenVariables.isEmpty()) {			
			for (String validTokenVariable : validTokenVariables) {
				// ${x} -> x
				String variableAsKey = TokenHelper.getRawTokenVariable(validTokenVariable);
				if(namesProcessed.contains(variableAsKey)) {
					throw new CircularDependencyException(namesProcessed); 
				}				 
				RdProp configWorkflowPropertyVO = prop.get(variableAsKey);
				
				String voPropertyValue = configWorkflowPropertyVO.getValue();
				checkCircularDependency(prop, variableAsKey, voPropertyValue, namesProcessed, recursionLevel + 1);
			}			
		}
	}



	
}
