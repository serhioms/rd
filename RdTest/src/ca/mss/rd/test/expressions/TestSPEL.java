package ca.mss.rd.test.expressions;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import ca.mss.rd.util.Logger;

public class TestSPEL {

	@Test
	public void evalSpelExpr1() {

		String expr = "'Hello World'";
		String expected = "Hello World";
		
		ExpressionParser parser = new SpelExpressionParser();
		Expression exp = parser.parseExpression(expr);
		
		String result = (String) exp.getValue();

		Logger.TEST.printf("Eval1: %s", result);
		
		
		assertTrue(String.format("Expected [%s] but got [%s] for [%s]", expected, result, expr), expected.equals(result));

	}

	@Test
	public void evalSpelExpr2() {

		String expr = "#BUSINESS_DATE";
		String expected = "8/12/2016";
		
		ExpressionParser parser = new SpelExpressionParser();
		Expression exp = parser.parseExpression(expr);
		
		EvaluationContext context = new StandardEvaluationContext();
		context.setVariable("BUSINESS_DATE", "8/12/2016");
		
		String result = (String) exp.getValue(context);

		Logger.TEST.printf("Eval1: %s", result);
		
		
		assertTrue(String.format("Expected [%s] but got [%s] for [%s]", expected, result, expr), expected.equals(result));

	}

	@Test
	public void evalSpelExpr3() {

		String expr = "#SKIP_GATHER_STATS = TRUE";
		
		ExpressionParser parser = new SpelExpressionParser();
		Expression exp = parser.parseExpression(expr);
		
		EvaluationContext context = new StandardEvaluationContext();
		context.setVariable("#SKIP_GATHER_STATS", "TRUE");
		
		Boolean result = exp.getValue(context, Boolean.class);

		Logger.TEST.printf("Eval1: %s", result);
		
		
		assertTrue(String.format("Expected [%s] but got [%s] for [%s]", true, result, expr), result);

	}

	@Test
	public void evalSpelExpr4() {

		String expr = "#SKIP-GATHER-STATS = TRUE";
		
		ExpressionParser parser = new SpelExpressionParser();
		Expression exp = parser.parseExpression(expr);
		
		EvaluationContext context = new StandardEvaluationContext();
		context.setVariable("#SKIP-GATHER-STATS", "TRUE");
		
		Boolean result = exp.getValue(context, Boolean.class);

		Logger.TEST.printf("Eval1: %s", result);
		
		
		assertTrue(String.format("Expected [%s] but got [%s] for [%s]", true, result, expr), result);

	}

}
