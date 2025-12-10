package com.prosperica.test;

import java.util.Date;

import ca.mss.rd.util.UtilDateTime;

import com.prosperica.common.Period;
import com.prosperica.common.Period.PeriodMeasurement;
import com.prosperica.mc.ExtraPaymentFrequency;
import com.prosperica.mc.ExtraPaymentOrder;
import com.prosperica.mc.PaymentFrequency;
import com.prosperica.mc.impl.MortgageContext;

public class TestMortgagePayment {

	final static public String className = TestMortgagePayment.class.getName();
	final static public long serialVersionUID = className.hashCode();

	/**
	 * @param args
	 */

	static public double PRINCIPAL = Double.parseDouble("504000.0");
	static public double ANUAL_RATE = Double.parseDouble("0.0309");
	static public Period AMORTIZATION_PHERIOD_YEARS = new Period(25);
	static public Date START_DATE = UtilDateTime.parse("9/19/2012", "MM/dd/yyyy");
	static public Period YEAR_TERM = new Period(5);

	static public int MAX_MONTH_EXTRAS_PRC = 20;
	static public int MAX_YEAR_EXTRAS_PRC = 20;
	
	static public boolean MINIMIZE_MO_PAYMENTS = true;
	
	static public MortgageContext[] mca = new MortgageContext[]{
		
		new MortgageContext("Test Mortgage", ANUAL_RATE, PRINCIPAL, AMORTIZATION_PHERIOD_YEARS, START_DATE, YEAR_TERM, 
					PaymentFrequency.ACCELERATED_BI_WEEKLY),
					
		new MortgageContext("Test Mortgage", ANUAL_RATE, PRINCIPAL, AMORTIZATION_PHERIOD_YEARS, START_DATE, YEAR_TERM, 
					PaymentFrequency.WEEKLY, 
					310, ExtraPaymentFrequency.MONTHLY, ExtraPaymentOrder.AFTER_PAYMENTS,
					MAX_MONTH_EXTRAS_PRC, MAX_YEAR_EXTRAS_PRC, MINIMIZE_MO_PAYMENTS),
					
		new MortgageContext("Test Mortgage", ANUAL_RATE, PRINCIPAL, AMORTIZATION_PHERIOD_YEARS, START_DATE, YEAR_TERM, 
					PaymentFrequency.ACCELERATED_WEEKLY, 
					0, ExtraPaymentFrequency.MONTHLY, ExtraPaymentOrder.AFTER_PAYMENTS),		

		new MortgageContext("Test Mortgage", 0.06, 100000, new Period(12, PeriodMeasurement.Month), START_DATE, YEAR_TERM, 
				PaymentFrequency.ACCELERATED_BI_WEEKLY),
				
	}; 


}


