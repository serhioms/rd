package com.prosperica.common;

import java.util.Date;

import ca.mss.rd.util.UtilDateTime;
import ca.mss.rd.util.UtilMath;


public class Def {

	final static public int END_OF_PAYMENTS = Integer.MAX_VALUE;

	final static public int NUMBER_OF_PAYMENTS(int val) {
		return val;
	}
	
	final static public double INTEREST_ZERO = 0.0D;
	
	final static public double YEAR_INTEREST2RATE(double val) {
		return UtilMath.round(val/100.0D, 6);
	}
	
	final static public double RATE2YEAR_INTEREST(double val) {
		return UtilMath.round(val*100.0D, 6);
	}
	
	final static public double YEAR_FEE(double val) {
		return val;
	}
	
	final static public double CREDIT_LIMIT(double val) {
		return val;
	}
	
	final static public double REMINDER_AMOUNT(double val) {
		return val;
	}
	
	final static public double PURCHASE_AMOUNT(double val) {
		return val;
	}
	
	final static public double FIXED_PAYMENT(double val) {
		return val;
	}
	
	final static public int FIRST_PAYMENT_NOP(int val) {
		return val;
	}
	
	final static public int DUE_DAY_OF_MONTH(int val) {
		return val;
	}

	final static public Date PURCHASE_DATE(String date) {
		return UtilDateTime.parse(date, "MM/dd/yyyy");
	}

	final static public Date PROMOTION_END_DATE(String date) {
		return UtilDateTime.parse(date, "MM/dd/yyyy");
	}

	final static public Date REMINDER_ON_DATE(String date) {
		return UtilDateTime.parse(date, "MM/dd/yyyy");
	}
	
	final static public Date FIRST_DUE_DATE(String date) {
		return UtilDateTime.parse(date, "MM/dd/yyyy");
	}
	
	final static public double CAD(double val) {
		return val;
	}
	
	final static public double HOUR_RATE_CAD(double val) {
		return val;
	}
	
}
