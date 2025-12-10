package com.scotiabank.maestro.msg.workflow.condition;

import static java.lang.String.format;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.expression.Expression;
import org.springframework.expression.ParseException;
import org.springframework.expression.spel.SpelEvaluationException;
import org.springframework.expression.spel.SpelParseException;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import ca.mss.rd.flow.IActivity;
import ca.mss.rd.util.Logger;

import com.scotiabank.maestro.utils.token.TokenHelper;

public class ConditionEvaluator implements IConditionEvaluator {	
	
	public ConditionEvaluator() {		
	}

	private void validateTokenVariables(IActivity<?,?> activity, String originalCondition, WorkflowValidationVO result) {
		List<String> invalidTokenVariables = TokenHelper.getInvalidTokenVariables(originalCondition);
		if (!CollectionUtils.isEmpty(invalidTokenVariables)) {
			String message = format("The workflow condition contains invalid variables [%s].", StringUtils.join(invalidTokenVariables, ","));
			
			if (activity == null) {
				result.add(new WorkflowValidationMessageVO(message));
			} else {
				result.add(new WorkflowValidationMessageVO(activity.getCode(), activity.getName(), message));				
			}
		}
	}

	/**
	 * Validate raw condition.  
	 * Ex:  
	 * 	'${abc}' == 'x'
	 * 	${abc} == 10
	 * 
	 */
	@Override
	public void validate(IActivity<?,?> activity, String originalCondition, WorkflowValidationVO result)  {
		try {
			validateTokenVariables(activity, originalCondition, result);
			String substitutedCondition = TokenHelper.applyTokensWithDefaultValue(originalCondition, TokenHelper.DEFAULT_REPLACE_VALUE);
			eval(substitutedCondition);
		} catch (Exception x) {
			String message = "Invalid condition: " + originalCondition;
			if (activity == null) {
				result.add(new WorkflowValidationMessageVO(message));
			} else {
				result.add(new WorkflowValidationMessageVO(activity.getCode(), activity.getName(), message));				
			}
		}
	}
	
	protected Expression parse(String s) throws ParseException {		
		return new SpelExpressionParser().parseExpression(s);
	}

	@Override
	public boolean eval(String s) {
		StandardEvaluationContext ctx = new StandardEvaluationContext();
		try {
			Expression expr = parse(s);
	
			Boolean value = expr.getValue(ctx, Boolean.class);
			return ((value != null) ? value : false);
		} catch(ParseException x) {
			if( Logger.ERROR.isOn ) Logger.ERROR.printf("Spring SPEL ParseException:" + x.getMessage() + " ParsedString:" + s, x);
			throw x;
		}
	}
	
	@Override
	public boolean evalWithDefaultValue(String substitutedCondition, boolean defaultValue) {
		boolean returnValue = defaultValue;
		
		try {
			returnValue = eval(substitutedCondition);
		} catch(SpelEvaluationException exception) {
			if( Logger.ERROR.isOn ) Logger.ERROR.printf("Unable to evaluate condition [{}].  Defaulting to {}.", substitutedCondition, defaultValue, exception);
		} catch(SpelParseException exception) {
			if( Logger.ERROR.isOn ) Logger.ERROR.printf("Unable to parse condition [{}].  Defaulting to {}.", substitutedCondition, defaultValue, exception);			
		}
		return returnValue;
	}
	
}
