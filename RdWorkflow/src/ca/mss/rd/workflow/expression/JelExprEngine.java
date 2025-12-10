package ca.mss.rd.workflow.expression;

import java.util.Map;

import ca.mss.rd.tools.expression.JelUtil;
import ca.mss.rd.workflow.dynamic.expression.WkfExpressionEngine;

public class JelExprEngine implements WkfExpressionEngine {

	@Override
	public boolean evaluate(String expression, Map context) {
    	return JelUtil.evaluateBool(expression, context);
	}

}
