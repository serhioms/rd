package com.prosperica.test;

import java.util.Date;

import ca.mss.rd.util.UtilDateTime;

import com.prosperica.cc.impl.CreditCardContext;
import com.prosperica.cc.impl.CCFee;
import com.prosperica.common.AmortizationTable;
import com.prosperica.common.Def;
import com.prosperica.common.Defs;
import com.prosperica.common.Period;
import com.prosperica.common.Period.PeriodMeasurement;
import com.prosperica.common.RateScheduler;
import com.prosperica.mc.AmortizationType;
import com.prosperica.mc.ExtraPaymentFrequency;
import com.prosperica.mc.printers.TextPrinterHelper;

public class CreditCard {

	final static public String className = CreditCard.class.getName();
	final static public long serialVersionUID = className.hashCode();

	/**
	 * @param args
	 */

	static public double PRINCIPAL = Double.parseDouble("3000.0");
	static public double ANUAL_RATE = Double.parseDouble("0.1199");
	static public Date PAY_DATE = UtilDateTime.parse("1/1/2013", "MM/dd/yyyy");
	
	static public int TEST_CC_INDEX = 5;

	static public int IDX_LAWS = 3;
	static public int IDX_HOMEDEPO = 4;
	static public int IDX_HOMEDEPO2 = 5;
	static public int IDX_VISA_RBC_PAPA = 6;
	static public int IDX_VISA_TD_MAMA = 7;
	static public int IDX_AMEX_COSTCO = 8;
	
	static public CreditCardContext[] CREDIT_CARDS = new CreditCardContext[]{
		
		 new CreditCardContext(new CCFee("Test", 0, 0), ANUAL_RATE, PRINCIPAL, new Period(3), PAY_DATE)

		,new CreditCardContext(new CCFee("Test", 0, 0), ANUAL_RATE, PRINCIPAL, PAY_DATE, 100, ExtraPaymentFrequency.MONTHLY)
		
		,new CreditCardContext(new CCFee("American Express", 3000.0, 0.0), 0.0299/* 6mo, then 0.1975; cash 0.2199; 2 missed 1 time 0.2399; 1 missed 2 times 0.2699*/, 2109.53,
			new Period(6, PeriodMeasurement.Payment), UtilDateTime.parse("1/31/2013", "MM/dd/yyyy"))

		,new CreditCardContext(new CCFee("Laws CC (6mo/NI)",  Def.CREDIT_LIMIT(1500.0), Def.YEAR_FEE(0.0)),
				RateScheduler.rates(
						Defs.NUMBER_OF_PAYMENTS(6), Defs.YEAR_INTEREST2RATE(0.0),
							  Defs.END_OF_PAYMENTS, Defs.YEAR_INTEREST2RATE(28.8)),
		 		Def.FIRST_DUE_DATE("10/3/2012"))
		 		.purchase(
		 				Def.PURCHASE_DATE("9/24/2012"), 
		 				Def.PURCHASE_AMOUNT(1246.69), 
		 				Def.FIXED_PAYMENT(200), 
		 				Def.FIRST_PAYMENT_NOP(5))
		 				
		 				
		 				
		 				
		 				
		,new CreditCardContext(new CCFee("Home Depo (1y/NI)",  Def.CREDIT_LIMIT(4500.0), Def.YEAR_FEE(0.0)), 
					 RateScheduler.rates(Def.PROMOTION_END_DATE("9/17/2013"), Def.YEAR_INTEREST2RATE(0.0), Def.YEAR_INTEREST2RATE(19.75)),
		 			 Def.FIRST_DUE_DATE("2/28/2013"))
			 		.purchase(
			 				Def.PURCHASE_DATE("9/17/2012"), 
			 				Def.PURCHASE_AMOUNT( 2203.84-500.0-83.84 ), 
			 				Def.FIXED_PAYMENT(200), 
			 				Def.FIRST_PAYMENT_NOP(5), 
			 				Def.DUE_DAY_OF_MONTH(28))
			 				
		,new CreditCardContext(new CCFee("Home Depo (1y/NI)",  Def.CREDIT_LIMIT(4500.0), Def.YEAR_FEE(0.0)), 
					 RateScheduler.rates(Def.PROMOTION_END_DATE("5/8/2014"), Def.YEAR_INTEREST2RATE(0.0), Def.YEAR_INTEREST2RATE(19.75)),
		 			 Def.FIRST_DUE_DATE("1/28/2014"))
			 		.purchase(
			 				Def.PURCHASE_DATE("11/6/2012"), 
			 				Def.PURCHASE_AMOUNT( 2203.84 ),
			 				Def.FIXED_PAYMENT( 200 ), 
			 				Def.FIRST_PAYMENT_NOP(1))

			 				
			 				
			 				
		,new CreditCardContext(new CCFee("RBC VISA PAPA",  Def.CREDIT_LIMIT(5800.0), Def.YEAR_FEE(0.0)), 
				 RateScheduler.rates(Def.YEAR_INTEREST2RATE(11.99)),
				 Def.FIRST_DUE_DATE("2/4/2013"))
		 		.reminder(
		 				Def.REMINDER_ON_DATE("2/1/2013 "), 
		 				Def.REMINDER_AMOUNT( 4248.59 ),  
		 				Def.FIXED_PAYMENT( 240 ), 
		 				Def.FIRST_PAYMENT_NOP(1))
		 				
		,new CreditCardContext(new CCFee("TD VISA MAMA",  Def.CREDIT_LIMIT(4500.0), Def.YEAR_FEE(0.0)), 
				 RateScheduler.rates(Def.YEAR_INTEREST2RATE(10.75)),
	 			 Def.FIRST_DUE_DATE("2/8/2013"))
		 		.reminder(
		 				Def.REMINDER_ON_DATE("1/24/2013"), 
		 				Def.REMINDER_AMOUNT(  1536.64 ),
		 				Def.FIXED_PAYMENT( 90 ), 
		 				Def.FIRST_PAYMENT_NOP(1))

		,new CreditCardContext(new CCFee("COSTCO AMEX",  Def.CREDIT_LIMIT(3000.0), Def.YEAR_FEE(0.0)), 
				RateScheduler.rates(
						Defs.NUMBER_OF_PAYMENTS(5), Defs.YEAR_INTEREST2RATE( 2.99 ), /* one time only :( */
							  Defs.END_OF_PAYMENTS, Defs.YEAR_INTEREST2RATE( 19.75 )),
	 			Def.FIRST_DUE_DATE("2/28/2013")) /* each 30-th except Feb 2013 - March 2 2013 */
		 		.purchase(
		 				Def.PURCHASE_DATE("12/28/2012"), 
		 				Def.PURCHASE_AMOUNT( 2109.53 - 64.0 ), /* 1-st payment was Jan 24, 2013*/
		 				Def.FIXED_PAYMENT( 150 ), 
		 				Def.FIRST_PAYMENT_NOP(1))

	}; 


	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		

		CreditCardContext ccc = CREDIT_CARDS[TEST_CC_INDEX];
		AmortizationTable cca = ccc.computate();
		
		TextPrinterHelper.printInput(System.out, ccc);
		TextPrinterHelper.printPayments(System.out, ccc);
		
		TextPrinterHelper.printAmortization(System.out, ccc, cca, AmortizationType.BY_MONTH, new int[]{
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


