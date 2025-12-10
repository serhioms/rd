package com.prosperica.cc.impl;

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

public class CreditCardContext extends MortgageContext {
	
	final static public String className = LoanContext.class.getName();
	final static public long serialVersionUID = className.hashCode();

	final public CompoundType compoundType = CompoundType.CREDIT_CARD;
	final public CCFee fee;
	
	public CreditCardContext(double annualRate, double principal, Period repaymentYears, Date startDate) {
		this("CC", annualRate, principal, repaymentYears, startDate);
	}

	public CreditCardContext(double annualRate, double principal, 
			Date startDate, int extraPayment, ExtraPaymentFrequency extraFrequency) {
		this("CC", annualRate, principal, new Period(), startDate, 
				extraPayment, extraFrequency, ExtraPaymentOrder.AFTER_PAYMENTS);
	}


	public CreditCardContext(CCFee fee, double annualRate, double principal, Period repaymentYears, Date startDate) {
		this(fee.descr, annualRate, principal, repaymentYears, startDate);
	}

	public CreditCardContext(CCFee fee, double annualRate, double principal, Date startDate, 
			int extraPayment, ExtraPaymentFrequency extraFrequency) {
		this(fee.descr, annualRate, principal, new Period(), startDate, 
				extraPayment, extraFrequency, ExtraPaymentOrder.AFTER_PAYMENTS);
	}

	private CreditCardContext(String descr, double annualRate, double principal, Period repaymentYears, Date startDate){
		this(descr, annualRate, principal, repaymentYears, startDate,
			0, null, null, 
			0, 0, false);
	}

	private CreditCardContext(String descr, double annualRate, double principal, Period amortization, Date startDate,
			int extraPayment, ExtraPaymentFrequency extraFrequency, ExtraPaymentOrder extraOrder) {
		this(descr, annualRate, principal, amortization, startDate,  
			extraPayment, extraFrequency, extraOrder, 
			0, 0, false);
	}

	private CreditCardContext(String descr, double annualRate, double principal, Period amortization, Date startDate,
			int extraPayment, ExtraPaymentFrequency extraFrequency, ExtraPaymentOrder extraOrder,
			int maxExtraMonth, int maxExtraYear, boolean minimizeMoPayments) {
		super(descr, annualRate, principal, amortization, startDate, amortization, PaymentFrequency.MONTHLY, 
				extraPayment, extraFrequency, extraOrder, 
				maxExtraMonth, maxExtraYear, minimizeMoPayments);
		this.fee = new CCFee("CC", 0, 0); 
	}
	
	
	@Override
	public CompoundType getCompoundType() {
		return compoundType;
	}

	public CreditCardContext(CCFee fee, double annualRate) {
		this(fee, RateScheduler.rates(annualRate));
	}

	public CreditCardContext(CCFee fee, RateScheduler rates) {
		super(fee.descr, PaymentFrequency.MONTHLY, ExtraPaymentFrequency.MONTHLY, rates);
		this.fee = fee; 
	}

	public CreditCardContext(CCFee fee, RateScheduler rates, Date dueDate) {
		super(fee.descr, PaymentFrequency.MONTHLY, ExtraPaymentFrequency.MONTHLY, rates, dueDate);
		this.fee = fee; 
	}

	public CreditCardContext(CreditCardContext parent, double principal, Date startDate, double fixedPayment, int fixedPaymentStartsNOP) {
		super(parent, principal, startDate, fixedPayment, fixedPaymentStartsNOP);
		this.fee = parent.fee; 
	}

	public CreditCardContext(CreditCardContext parent, double principal, Date startDate,  Date promoDueDate, double fixedPayment, int fixedPaymentStartsNOP) {
		super(parent, principal, startDate, promoDueDate, fixedPayment, fixedPaymentStartsNOP);
		this.fee = parent.fee; 
	}

	// @deprecated  
	final public CreditCardContext purchase(Date purchaseDate, double amount, double fixedPayment, int fixedPaymentStartsNOP, int dueDay){
		setAccumulator(getAccumulator().add(new CreditCardContext(this, amount, 
				UtilDateTime.setDay(UtilMortgageCalendar.getNextPayday(1, PaymentFrequency.MONTHLY, purchaseDate), dueDay),
				fixedPayment, fixedPaymentStartsNOP)));		
		return this;
	}
	
	final public CreditCardContext purchase(Date purchaseDate, double amount, double fixedPayment, int fixedPaymentStartsNOP){
		setAccumulator(getAccumulator().add(new CreditCardContext(this, amount, startDate, fixedPayment, fixedPaymentStartsNOP)));		
		return this;
	}
	
	final public CreditCardContext purchase(Date purchaseDate, RateScheduler promoRate, double amount, double fixedPayment, int fixedPaymentStartsNOP, int dueDay){
		setAccumulator(getAccumulator().add(new CreditCardContext(this, amount, 
				UtilDateTime.setDay(UtilMortgageCalendar.getNextPayday(1, PaymentFrequency.MONTHLY, purchaseDate), dueDay),
				fixedPayment, fixedPaymentStartsNOP)));		
		return this;
	}
	
	final public CreditCardContext reminder(Date reminderDate, double principal, double fixedPayment, int fixedPaymentStartsNOP){
		setAccumulator(getAccumulator().add(new CreditCardContext(this, principal, startDate, fixedPayment, fixedPaymentStartsNOP)));		
		return this;
	}

}


