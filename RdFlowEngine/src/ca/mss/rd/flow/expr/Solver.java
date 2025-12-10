package ca.mss.rd.flow.expr;

import java.util.Map;

public interface Solver<VALUE, PROP> {

	public boolean isExpression(String exprValue);

	public String detokenizedExpression(String expr);

	public VALUE evalExpression(String name, String expr);
	public boolean evalCondition(String expr);
	
	public Map<String, PROP> getExprContext();
	public void setExprContext(Map<String, PROP> exprContext);


}
