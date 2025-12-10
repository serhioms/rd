package com.prosperica.test;

import com.prosperica.common.AmortizationTable;
import com.prosperica.mc.AmortizationType;
import com.prosperica.mc.impl.MortgageContext;
import com.prosperica.mc.printers.TextPrinterHelper;

public class TestMortgageAmortizationByWholeAmortization extends TestMortgagePayment {

	final static public String className = TestMortgageAmortizationByWholeAmortization.class.getName();
	final static public long serialVersionUID = className.hashCode();

	public static void main(String[] args) {
		
		MortgageContext context = mca[2];
		AmortizationTable ma = context.computate();
		
		TextPrinterHelper.printInput(System.out, context);
		TextPrinterHelper.printPayments(System.out, context);
		
		TextPrinterHelper.printAmortization(System.out, context, ma, AmortizationType.FOR_WHOLE_AMORTIZATION_PERIOD);
		
	}
	

}


