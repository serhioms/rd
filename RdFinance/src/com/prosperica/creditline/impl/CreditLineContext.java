package com.prosperica.creditline.impl;

import java.util.Date;

import ca.mss.rd.util.UtilDateTime;

import com.prosperica.common.CompoundType;
import com.prosperica.common.Period;
import com.prosperica.common.RateScheduler;
import com.prosperica.loan.impl.LoanContext;
import com.prosperica.mc.ExtraPaymentFrequency;
import com.prosperica.mc.ExtraPaymentOrder;
import com.prosperica.mc.PaymentFrequency;
import com.prosperica.mc.impl.MortgageContext;
import com.prosperica.mc.util.UtilMortgageCalendar;

public class CreditLineContext extends MortgageContext {
	
	final static public String className = LoanContext.class.getName();
	final static public long serialVersionUID = className.hashCode();

	final public CompoundType compoundType = CompoundType.CREDIT_LINE;

	public CreditLineContext(String descr, double annualRate, double principal, Period repaymentYears, Date startDate){
		this(descr, annualRate, principal, repaymentYears, startDate,
			0, null, null, 
			0, 0, false);
	}

	public CreditLineContext(String descr, double annualRate, double principal, Date startDate,
			int extraPayment, ExtraPaymentFrequency extraFrequency) {
		this(descr, annualRate, principal, new Period(), startDate,  
			extraPayment, extraFrequency, ExtraPaymentOrder.AFTER_PAYMENTS, 
			0, 0, false);
	}

	protected CreditLineContext(String descr, double annualRate, double principal, Period amortization, Date startDate,
			int extraPayment, ExtraPaymentFrequency extraFrequency, ExtraPaymentOrder extraOrder) {
		this(descr, annualRate, principal, amortization, startDate,  
			extraPayment, extraFrequency, extraOrder, 
			0, 0, false);
	}

	protected CreditLineContext(String descr, double annualRate, double principal, Period amortization, Date startDate,
			int extraPayment, ExtraPaymentFrequency extraFrequency, ExtraPaymentOrder extraOrder,
			int maxExtraMonth, int maxExtraYear, boolean minimizeMoPayments) {
		super(descr, annualRate, principal, amortization, startDate, amortization, PaymentFrequency.MONTHLY, 
				extraPayment, extraFrequency, extraOrder, 
				maxExtraMonth, maxExtraYear, minimizeMoPayments);
	}

	@Override
	public CompoundType getCompoundType() {
		return compoundType;
	}

	
	public CreditLineContext(String descr, double annualRate, Date startDate) {
		this(descr, RateScheduler.rates(annualRate), startDate);
	}

	public CreditLineContext(String descr, RateScheduler rates, Date startDate) {
		super(descr, PaymentFrequency.MONTHLY, ExtraPaymentFrequency.MONTHLY, rates, startDate);
	}
	
	public CreditLineContext(CreditLineContext parent, double principal, Date startDate, double fixedPayment, int fixedPaymentStartsNOP) {
		super(parent, principal, startDate, fixedPayment, fixedPaymentStartsNOP);
	}

	final public CreditLineContext reminder(Date reminderDate, double principal, double fixedPayment, int fixedPaymentStartsNOP){
		setAccumulator(getAccumulator().add(new CreditLineContext(this, principal, startDate, fixedPayment, fixedPaymentStartsNOP)));		
		return this;
	}

}


