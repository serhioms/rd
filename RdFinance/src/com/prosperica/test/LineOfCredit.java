package com.prosperica.test;

import java.util.Date;

import ca.mss.rd.util.UtilDateTime;

import com.prosperica.common.AmortizationTable;
import com.prosperica.common.Def;
import com.prosperica.common.Period;
import com.prosperica.common.Period.PeriodMeasurement;
import com.prosperica.creditline.impl.CreditLineContext;
import com.prosperica.mc.AmortizationType;
import com.prosperica.mc.ExtraPaymentFrequency;
import com.prosperica.mc.printers.TextPrinterHelper;

public class LineOfCredit {

	final static public String className = LineOfCredit.class.getName();
	final static public long serialVersionUID = className.hashCode();

	/**
	 * @param args
	 */

	static public double PRINCIPAL = Double.parseDouble("3000.0");
	static public double ANUAL_RATE = Double.parseDouble("0.1199");
	static public Date PAY_DATE = UtilDateTime.parse("1/1/2013", "MM/dd/yyyy");
	
	static public int TEST_CL_INDEX = 5;

	static public int IDX_CIBC_ED = 3;
	static public int LC_TD_PAPA = 4;
	static public int IDX_RBC_PAPA = 5;
	
	static public CreditLineContext[] CREDIT_LINES = new CreditLineContext[]{
		
		 new CreditLineContext("Test", ANUAL_RATE, PRINCIPAL, new Period(3), PAY_DATE)

		,new CreditLineContext("Test", ANUAL_RATE, PRINCIPAL, PAY_DATE, 100, ExtraPaymentFrequency.MONTHLY)
		
		,new CreditLineContext("Ed's CIBC Line  of credit", 0.05, 14972.05,
				new Period(12, PeriodMeasurement.Year), UtilDateTime.parse("1/2/2013", "MM/dd/yyyy")) /* 58.09 */

		,new CreditLineContext("CIBC Line Ed", 0.05, Def.FIRST_DUE_DATE("2/2/2013"))
 		.reminder(Def.REMINDER_ON_DATE("1/22/2013"), 
 				Def.REMINDER_AMOUNT( 14985.81 ), 
 				Def.FIXED_PAYMENT( 370 ), 
 				Def.FIRST_PAYMENT_NOP(1))
 
		,new CreditLineContext("TD Line", 0.125, Def.FIRST_DUE_DATE("2/21/2013"))
		 .reminder(Def.REMINDER_ON_DATE("1/23/2013"), 
 				Def.REMINDER_AMOUNT( 5590.15 ), 
 				Def.FIXED_PAYMENT( 350 ), 
 				Def.FIRST_PAYMENT_NOP(1))
 
		,new CreditLineContext("RBC Line", 0.125, Def.FIRST_DUE_DATE("2/26/2013"))
		 .reminder(Def.REMINDER_ON_DATE("1/23/2013"), 
 				Def.REMINDER_AMOUNT( 24815.07 ), 
 				Def.FIXED_PAYMENT(1600), 
 				Def.FIRST_PAYMENT_NOP(1))
	}; 


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		

		CreditLineContext clc = CREDIT_LINES[TEST_CL_INDEX];
		AmortizationTable cca = clc.computate();
		
		TextPrinterHelper.printInput(System.out, clc);
		TextPrinterHelper.printPayments(System.out, clc);
		
		TextPrinterHelper.printAmortization(System.out, clc, cca, AmortizationType.BY_MONTH, new int[]{
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


