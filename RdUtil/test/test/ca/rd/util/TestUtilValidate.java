package test.ca.rd.util;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ca.mss.rd.util.Logger;
import ca.mss.rd.util.UtilValidate;

public class TestUtilValidate {

	@Before
	public void setUp() throws Exception {
		Logger.TEST.isOn = true;
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void numberFormat(){
		assertTrue(!UtilValidate.isValidNumber("-+."));
		assertTrue(!UtilValidate.isValidNumber("-."));
		assertTrue(!UtilValidate.isValidNumber("+."));
		assertTrue(!UtilValidate.isValidNumber("+-."));
		assertTrue(!UtilValidate.isValidNumber("."));
		assertTrue(UtilValidate.isValidNumber("12345.346346"));
		assertTrue(!UtilValidate.isValidNumber("12345.3463 46"));
		assertTrue(!UtilValidate.isValidNumber("12345..346346"));
		assertTrue(UtilValidate.isValidNumber("-12345.7"));
		assertTrue(!UtilValidate.isValidNumber("-12345..7"));
		assertTrue(UtilValidate.isValidNumber("+12345.7"));
		assertTrue(!UtilValidate.isValidNumber("+-12345.7"));
		assertTrue(!UtilValidate.isValidNumber("-+12345.7"));
		assertTrue(UtilValidate.isValidNumber("1234567890"));
		assertTrue(UtilValidate.isValidNumber("-1234567890"));
		assertTrue(UtilValidate.isValidNumber("+1234567890"));
		assertTrue(!UtilValidate.isValidNumber("+-1234567890"));
		assertTrue(!UtilValidate.isValidNumber("-+1234567890"));
		assertTrue(UtilValidate.isValidNumber("1234567890."));
		assertTrue(UtilValidate.isValidNumber(" 1234567890. "));
		assertTrue(!UtilValidate.isValidNumber(" 12345 67890. "));
		assertTrue(!UtilValidate.isValidNumber("1234567890.."));
		assertTrue(UtilValidate.isValidNumber("-1234567890."));
		assertTrue(UtilValidate.isValidNumber("+1234567890."));
		assertTrue(!UtilValidate.isValidNumber("+-1234567890."));
		assertTrue(!UtilValidate.isValidNumber("-+1234567890."));
	}

}
