package ca.mss.rd.workflow.expression;

import java.util.Map;


import ca.mss.rd.tools.expression.BshUtil;
import ca.mss.rd.workflow.dynamic.expression.WkfExpressionEngine;

public class BshExprEngine implements WkfExpressionEngine {

	@Override
	public boolean evaluate(String expression, Map<String,Object> context) {
		boolean result = true;
		
    	Object obj = BshUtil.eval(expression, context);

    	if (obj == null)
        	result = false;
        else if (obj instanceof Number)
        	result = (((Number) obj).doubleValue() == 0) ? false : true;
        else if (obj instanceof Boolean)
        	result = ((Boolean) obj).booleanValue();
        else
        	result = obj.toString().equalsIgnoreCase("true") ? true: false;
    	
    	return result;
	}

}
