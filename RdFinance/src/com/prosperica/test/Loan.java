package com.prosperica.test;

import ca.mss.rd.util.UtilDateTime;

import com.prosperica.common.AmortizationTable;
import com.prosperica.common.Def;
import com.prosperica.common.Period;
import com.prosperica.loan.impl.LoanContext;
import com.prosperica.mc.AmortizationType;
import com.prosperica.mc.ExtraPaymentFrequency;
import com.prosperica.mc.ExtraPaymentOrder;
import com.prosperica.mc.printers.TextPrinterHelper;

public class Loan {

	final static public String className = Loan.class.getName();
	final static public long serialVersionUID = className.hashCode();

	/**
	 * @param args
	 */
	
	static public int TEST_LN_INDEX = 3;

	static public int IDX_TOYOTA = 3;
	
	static public LoanContext[] LOANS = new LoanContext[]{
		
		 new LoanContext("Test Loan", 0.1199, 3000.0, new Period(8), UtilDateTime.parse("1/1/2013", "MM/dd/yyyy"))
					
		,new LoanContext("Test Loan", 0.1199, 3000.0, new Period(8), UtilDateTime.parse("1/1/2013", "MM/dd/yyyy"), 
					310, ExtraPaymentFrequency.MONTHLY, ExtraPaymentOrder.AFTER_PAYMENTS,
					20, 20, true)
					
		,new LoanContext("Test Loan", 0.1199, 3000.0, new Period(8), UtilDateTime.parse("1/1/2013", "MM/dd/yyyy"), 
					0, ExtraPaymentFrequency.MONTHLY, ExtraPaymentOrder.AFTER_PAYMENTS)
		
		,new LoanContext("TOYOTA LOAN", 0.0749, Def.FIRST_DUE_DATE("2/22/2013"))
 			.reminder(Def.REMINDER_ON_DATE("1/22/2013"), 
 				Def.REMINDER_AMOUNT(  5188.27  ), 
 				Def.FIXED_PAYMENT( 450.12 ), 
 				Def.FIRST_PAYMENT_NOP(1))
		
	}; 


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		LoanContext lc = LOANS[TEST_LN_INDEX];
		AmortizationTable at = lc.computate();
		
		TextPrinterHelper.printInput(System.out, lc);
		TextPrinterHelper.printPayments(System.out, lc);
		
		TextPrinterHelper.printAmortization(System.out, lc, at, AmortizationType.BY_MONTH, new int[]{
				 AmortizationTable.C_NOP
				,AmortizationTable.C_PAYMENT_DATE
				,AmortizationTable.C_BALANCE_IN
				,AmortizationTable.C_PAYMENT_FULL
				,AmortizationTable.C_INTEREST
				,AmortizationTable.C_TOTAL_INTEREST
				,AmortizationTable.C_TOTAL_INTEREST_2PRINCIPAL_PRC
		});
		
	}
}


