package ca.mss.rd.workflow.dynamic.expression;

import java.util.Map;

public interface WkfExpressionEngine {

    public boolean evaluate(String expression, Map<String,Object> context);

}
