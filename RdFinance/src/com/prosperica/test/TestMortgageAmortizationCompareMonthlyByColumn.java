package com.prosperica.test;

import com.prosperica.common.AmortizationTable;
import com.prosperica.mc.AmortizationType;
import com.prosperica.mc.printers.TextPrinterHelper;

public class TestMortgageAmortizationCompareMonthlyByColumn extends TestMortgagePayment {

	final static public String className = TestMortgageAmortizationCompareMonthlyByColumn.class.getName();
	final static public long serialVersionUID = className.hashCode();

	public static void main(String[] args) {
		
		TextPrinterHelper.printMortgageCompareByColumn(System.out, mca, AmortizationType.BY_MONTH, 
				new int[]{AmortizationTable.C_PAYMENT_FULL, AmortizationTable.C_BALANCE_OUT},
				new int[]{10, 11},
				" ",
				0, 0
		);
		
	}
	
}


