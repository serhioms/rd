package com.prosperica.test;

import com.prosperica.mc.AmortizationType;
import com.prosperica.mc.printers.TextPrinterHelper;

public class TestMortgageAmortizationCompareNOP extends TestMortgagePayment {

	final static public String className = TestMortgageAmortizationByTerm.class.getName();
	final static public long serialVersionUID = className.hashCode();

	public static void main(String[] args) {
		
		TextPrinterHelper.printMortgageCompareByNOP(System.out, mca, AmortizationType.FOR_WHOLE_AMORTIZATION_PERIOD, 1);

		TextPrinterHelper.printMortgageCompareByNOP(System.out, mca, AmortizationType.BY_TERM, 1);

		TextPrinterHelper.printMortgageCompareByNOP(System.out, mca, AmortizationType.BY_YEAR, 1);

		TextPrinterHelper.printMortgageCompareByNOP(System.out, mca, AmortizationType.BY_QUOTER, 1);
		
		TextPrinterHelper.printMortgageCompareByNOP(System.out, mca, AmortizationType.BY_MONTH, 1);
		
	}
	
}


