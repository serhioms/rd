package ca.mss.rd.test.expressions;

import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import ca.mss.rd.tools.expression.JelUtil;
import ca.mss.rd.util.Logger;

public class TestJEL {

	@Test
	public void evalSimpleExpression1() {

		Map<String, Object> properties = new HashMap<String, Object>();

		properties.put("s1", "proba");
		properties.put("s2", "proba");

		assertTrue(String.format("s1 == s2, where s1=s2='proba'"), JelUtil.evaluateBool("s1.equals(s2) && s2.equals(\"proba\")", properties));
	}

	@Test
	public void evalSimpleExpression2() {

		Map<String, Object> properties = new HashMap<String, Object>();

		properties.put("s_1", "proba");
		properties.put("s_2", "proba");

		assertTrue(String.format("s_1 == s_2, where s_1=s_2='proba'"), JelUtil.evaluateBool("s_1.equals(s_2) && s_2.equals(\"proba\")", properties));
	}

	@Test
	public void evalSimpleExpression3() {

		Map<String, Object> properties = new HashMap<String, Object>();

		properties.put("s_1", "proba");
		properties.put("s_2", "proba");

		assertTrue(String.format("s_1 == s_2, where s_1=s_2='proba'"), JelUtil.evaluateBool("s_1 == s_2 && s_2 == \"proba\"", properties));
	}

	@Test
	public void detokinizeVar1() {
		String expected = "#BUSINESS-DATE";
		String expr = "${" + expected + "}";
		String result = JelUtil.detokenizedExpression(expr);

		Logger.TEST.printf("Detokinized1: %s", result);

		assertTrue(String.format("expected [%s] for [%s] but got [%s] in fact", expected, expr, result), result.equals(expected));
	}

	@Test
	public void detokinizeVar2() {
		String expected = "BUSINESS-DATE.VALUE";
		String expr = "${" + expected + "}";
		String result = JelUtil.detokenizedExpression(expr);

		Logger.TEST.printf("Detokinized2: %s", result);

		assertTrue(String.format("expected [%s] for [%s] but got [%s] in fact", expected, expr, result), result.equals(expected));
	}

	@Test
	public void evalSimpleExpression4() {

		String expr = "SKIP_GATHER_STATS == \"TRUE\"";

		Map<String, Object> properties = new HashMap<String, Object>();

		properties.put("SKIP_GATHER_STATS", "TRUE");
		boolean result = JelUtil.evaluateBool(expr, properties);

		Logger.TEST.printf("Evaluate4: %s", result);

		assertTrue(String.format("Expression [%s] evaluated as [%b] but should be [%b]", expr, result, !result), result);
	}
};
