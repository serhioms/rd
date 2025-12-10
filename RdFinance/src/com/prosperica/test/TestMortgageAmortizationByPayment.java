package com.prosperica.test;

import com.prosperica.common.AmortizationTable;
import com.prosperica.mc.AmortizationType;
import com.prosperica.mc.impl.MortgageContext;
import com.prosperica.mc.printers.TextPrinterHelper;

public class TestMortgageAmortizationByPayment extends TestMortgagePayment {

	final static public String className = TestMortgageAmortizationByPayment.class.getName();
	final static public long serialVersionUID = className.hashCode();

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		MortgageContext mc = mca[3];
		AmortizationTable ma = mc.computate();
		
		TextPrinterHelper.printInput(System.out, mc);
		TextPrinterHelper.printPayments(System.out, mc);
		
		TextPrinterHelper.printAmortization(System.out, mc, ma, AmortizationType.BY_PAYMENT, 0, 0);

	}

}


