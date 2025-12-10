package com.prosperica.common;

import ca.mss.rd.util.UtilDateTime;
import ca.mss.rd.util.UtilMath;


public class Defs {

	final static public String END_OF_PAYMENTS = Integer.toString(Def.END_OF_PAYMENTS);

	final static public String NUMBER_OF_PAYMENTS(int val) {
		return Integer.toString(val);
	}
	
	final static public String YEAR_INTEREST2RATE(double val) {
		return Double.toString(UtilMath.round(val/100.0D, 6));
	}
	
	final static public String RATE2YEAR_INTEREST(double val) {
		return Double.toString(UtilMath.round(val*100.0D, 6));
	}
	
	final static public String YEAR_FEE(double val) {
		return Double.toString(val);
	}
	
	final static public String CREDIT_LIMIT(double val) {
		return Double.toString(val);
	}
	
	final static public String REMINDER_AMOUNT(double val) {
		return Double.toString(val);
	}
	
	final static public String PURCHASE_AMOUNT(double val) {
		return Double.toString(val);
	}
	
	final static public String FIXED_PAYMENT(int val) {
		return Integer.toString(val);
	}
	
	final static public String FIRST_PAYMENT_NOP(int val) {
		return Integer.toString(val);
	}
	
	final static public String DUE_DAY_OF_MONTH(int val) {
		return Integer.toString(val);
	}

	final static public String PURCHASE_DATE(String date) {
		return UtilDateTime.parse(date, "MM/dd/yyyy").toString();
	}

	final static public String REMINDER_ON_DATE(String date) {
		return UtilDateTime.parse(date, "MM/dd/yyyy").toString();
	}
	
}
