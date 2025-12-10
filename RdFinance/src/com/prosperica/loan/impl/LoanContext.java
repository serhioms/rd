package com.prosperica.loan.impl;

import java.util.Date;

import ca.mss.rd.util.UtilDateTime;

import com.prosperica.common.CompoundType;
import com.prosperica.common.Period;
import com.prosperica.common.RateScheduler;
import com.prosperica.mc.ExtraPaymentFrequency;
import com.prosperica.mc.ExtraPaymentOrder;
import com.prosperica.mc.PaymentFrequency;
import com.prosperica.mc.impl.MortgageContext;
import com.prosperica.mc.util.UtilMortgageCalendar;

public class LoanContext extends MortgageContext {
	
	final static public String className = LoanContext.class.getName();
	final static public long serialVersionUID = className.hashCode();

	final public CompoundType compoundType = CompoundType.CANADIAN_LOAN;

	public LoanContext(String descr, double annualRate, double principal, Period amortization, Date startDate){
		this(descr, annualRate, principal, amortization, startDate,
			0, null, null, 
			0, 0, false);
	}

	public LoanContext(String descr, double annualRate, double principal, Period amortization, Date startDate,
			int extraPayment, ExtraPaymentFrequency extraFrequency, ExtraPaymentOrder extraOrder) {
		this(descr, annualRate, principal, amortization, startDate,  
			extraPayment, extraFrequency, extraOrder, 
			0, 0, false);
	}

	public LoanContext(String descr, double annualRate, double principal, Period amortization, Date startDate,
			int extraPayment, ExtraPaymentFrequency extraFrequency, ExtraPaymentOrder extraOrder,
			int maxExtraMonth, int maxExtraYear, boolean minimizeMoPayments) {
		super(descr, annualRate, principal, amortization, startDate, amortization, PaymentFrequency.MONTHLY, 
				extraPayment, extraFrequency, extraOrder, 
				maxExtraMonth, maxExtraYear, minimizeMoPayments);
	}

	/* (non-Javadoc)
	 * @see com.prosperica.mc.impl.MortgageContext#getPaymentTypeLength()
	 */
	@Override
	public int getPaymentTypeLength() {
		return 1;
	}

	@Override
	public CompoundType getCompoundType() {
		return compoundType;
	}

	public LoanContext(String descr, double annualRate, Date dueDate) {
		this(descr, RateScheduler.rates(annualRate), dueDate);
	}

	public LoanContext(String descr, RateScheduler rates, Date dueDate) {
		super(descr, PaymentFrequency.MONTHLY, ExtraPaymentFrequency.MONTHLY, rates, dueDate);
	}

	protected LoanContext(LoanContext parent, double principal, Date startDate, double fixedPayment, int fixedPaymentStartsNOP) {
		super(parent, principal, startDate, fixedPayment, fixedPaymentStartsNOP);
	}

	protected LoanContext(LoanContext parent, double principal, Date startDate, Date promoDueDate, double fixedPayment, int fixedPaymentStartsNOP) {
		super(parent, principal, startDate, promoDueDate, fixedPayment, fixedPaymentStartsNOP);
	}


	final public LoanContext reminder(Date reminderDate, double principal, double fixedPayment, int fixedPaymentStartsNOP){
		setAccumulator(getAccumulator().add(new LoanContext(this, principal, startDate, fixedPayment, fixedPaymentStartsNOP)));		
		return this;
	}

}


