package ca.mss.rd.flow.expr.jel;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;

import ca.mss.rd.flow.expr.Solver;
import ca.mss.rd.flow.prop.DefProp;
import ca.mss.rd.flow.prop.RdProp;
import ca.mss.rd.util.Logger;

import com.scotiabank.maestro.msg.workflow.condition.IConditionEvaluator;
import com.scotiabank.maestro.utils.token.TokenHelper;

public class SpelSolver implements Solver<String, RdProp> {

	private static final String VALID_TOKEN_VARIABLE_REGEX = "\\$\\{[\\#?a-zA-Z0-9_\\-\\.]+}";
	private static final Pattern VALID_TOKEN_VARIABLE_PATTERN = Pattern.compile(VALID_TOKEN_VARIABLE_REGEX);

	public final Map<String, RdProp> exprContext = new HashMap<String, RdProp>(0);
	
	@Autowired
	private IConditionEvaluator evaluator;
	
	public SpelSolver() {
	}

	
	@Override
	public Map<String, RdProp> getExprContext() {
		return exprContext;
	}


	@Override
	public void setExprContext(Map<String, RdProp> exprContext) {
		this.exprContext.clear();
		this.exprContext.putAll(exprContext);
	}

	@Override
	public final String evalExpression(String name, String exprValue) {
		if( isExpression(exprValue) ){
			String value = TokenHelper.applyTokens(exprValue, exprContext);
			exprContext.put(name, new DefProp(name, value));
			assert(Logger.WORKFLOW_SOLVER.printf("%s=%s", name, value));
			return value;
		} else {
			exprContext.put(name, new DefProp(name, exprValue));
			assert(Logger.WORKFLOW_SOLVER.printf("%s=%s", name, exprValue));
			return exprValue;
		}
	}
	
	
	
	@Override
	public boolean evalCondition(String exprValue) {
		return getEvaluator().eval(TokenHelper.applyTokens(exprValue, exprContext));
	}


	@Override
	public final boolean isExpression(String exprValue) {
		if( exprValue == null || exprValue.isEmpty() ) {
			return false;
		} else {
			return VALID_TOKEN_VARIABLE_PATTERN.matcher(exprValue).find();
		}
	}

	@Override
	public final String detokenizedExpression(String expr) {
		return expr;
	}


	public IConditionEvaluator getEvaluator() {
		return evaluator;
	}
	
	@Required
	public void setEvaluator(IConditionEvaluator evaluator) {
		this.evaluator = evaluator;
	}

}
