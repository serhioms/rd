package com.prosperica.test;

import ca.mss.rd.util.UtilDateTime;

import com.prosperica.common.AmortizationTable;
import com.prosperica.common.Period;
import com.prosperica.mc.AmortizationType;
import com.prosperica.mc.PaymentFrequency;
import com.prosperica.mc.impl.MortgageContext;
import com.prosperica.mc.printers.TextPrinterHelper;

public class TestMortgageAgregation2 {

	final static public String className = TestMortgageAgregation2.class.getName();
	final static public long serialVersionUID = className.hashCode();

	static public int FILTER_TERM = 1;
	static public int FILTER_YEAR = 0;

	static public int MAX_MONTH_EXTRAS_PRC = 20;
	static public int MAX_YEAR_EXTRAS_PRC = 20;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		MortgageContext mc = new MortgageContext("Test Mortgage", 
				Double.parseDouble("0.0309"), 
				Double.parseDouble("504000.0"), 
				new Period(25), 
				UtilDateTime.parse("9/19/2012", "MM/dd/yyyy"), 
				new Period(5), 
				PaymentFrequency.ACCELERATED_BI_WEEKLY)
		.changePaymentFrequency(UtilDateTime.parse("2/1/2013", "MM/dd/yyyy"), PaymentFrequency.ACCELERATED_WEEKLY)
		.changeRate(UtilDateTime.parse("1/12/2014", "MM/dd/yyyy"), Double.parseDouble("0.03"));
		
		AmortizationTable ma = mc.computate();
		
		TextPrinterHelper.printInput(System.out, mc);
		TextPrinterHelper.printPayments(System.out, mc);
		
		TextPrinterHelper.printAmortization(System.out, mc, ma, AmortizationType.BY_PAYMENT, FILTER_YEAR, FILTER_TERM);

	}

}


