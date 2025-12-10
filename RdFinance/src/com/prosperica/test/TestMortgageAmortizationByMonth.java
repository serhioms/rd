package com.prosperica.test;

import com.prosperica.common.AmortizationTable;
import com.prosperica.mc.AmortizationType;
import com.prosperica.mc.impl.MortgageContext;
import com.prosperica.mc.printers.TextPrinterHelper;

public class TestMortgageAmortizationByMonth extends TestMortgagePayment {

	final static public String className = TestMortgageAmortizationByMonth.class.getName();
	final static public long serialVersionUID = className.hashCode();

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		MortgageContext mc = mca[2];
		AmortizationTable ma = mc.computate();
		
		TextPrinterHelper.printInput(System.out, mc);
		TextPrinterHelper.printPayments(System.out, mc);
		
		TextPrinterHelper.printAmortization(System.out, mc, ma, AmortizationType.BY_MONTH);
		
	}

}


