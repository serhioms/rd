package com.prosperica.common;

import java.util.Date;

import ca.mss.rd.util.UtilDateTime;

import com.prosperica.common.RateScheduler.CCRate;
import com.prosperica.mc.ExtraPaymentFrequency;
import com.prosperica.mc.ExtraPaymentOrder;
import com.prosperica.mc.PaymentFrequency;

public abstract class CommonContext {
	
	final public String descr;
	/*final*/ public double principal;
	final public Period amortization, term;
	/*final*/ public double annualRate;
	final public Date startDate; 

	final public PaymentFrequency paymentFrequency;

	/*final*/ public int extraPayment;
	final public ExtraPaymentFrequency extraFrequency;
	final public ExtraPaymentOrder extraOrder; 

	final public int maxExtraMonth;
	final public int maxExtraYear;

	final public boolean minimizeMoPayments;

	public int termNumberOfPayments, totalNumberOfPayments;
	
	public AmortizationTable amortizationTable;

	final public RateScheduler rates;
	final public double fixedPayment;
	final public int fixedPaymentStartsNOP;


	public CommonContext(String descr, double annualRate, double principal, Period amortization, Date startDate, Period term, 
			PaymentFrequency paymentFrequency, 
			int extraPayment, ExtraPaymentFrequency extraFrequency, ExtraPaymentOrder extraOrder,
			int maxExtraMonth, int maxExtraYear, boolean minimizeMoPayments) {
		
		this.descr = descr;
		this.principal = principal;
		this.amortization = amortization;
		this.annualRate = annualRate;
		this.paymentFrequency = paymentFrequency;

		this.startDate = startDate;
		this.term = term;
		
		this.extraPayment = extraPayment;
		this.extraFrequency = extraFrequency;
		this.extraOrder = extraOrder;

		this.maxExtraMonth = maxExtraMonth;
		this.maxExtraYear = maxExtraYear;

		this.minimizeMoPayments = minimizeMoPayments;
		
		this.rates = RateScheduler.rates(annualRate);
		this.fixedPayment = 0.0;
		this.fixedPaymentStartsNOP = 0;
	}
	
	public CommonContext(String descr, PaymentFrequency paymentFrequency, ExtraPaymentFrequency extraFrequency, RateScheduler rates, Date startDate) {
		this.descr = descr;
		
		this.principal = CommonSettings.ZERO;
		this.amortization = new Period();
		this.annualRate = CommonSettings.ZERO;
		this.paymentFrequency = paymentFrequency;

		this.startDate = startDate;
		this.term = new Period();
		
		this.extraPayment = CommonSettings.ZERO_EXTRA;
		this.extraFrequency = extraFrequency;
		this.extraOrder = ExtraPaymentOrder.AFTER_PAYMENTS;

		this.maxExtraMonth = CommonSettings.PERCENT_100;
		this.maxExtraYear = CommonSettings.PERCENT_100;

		this.minimizeMoPayments = false;

		this.rates = rates;
		this.fixedPayment = 0;
		this.fixedPaymentStartsNOP= 0; 
	}
	
	public CommonContext(String descr, PaymentFrequency paymentFrequency, ExtraPaymentFrequency extraFrequency, RateScheduler rates) {
		this.descr = descr;
		
		this.principal = CommonSettings.ZERO;
		this.amortization = new Period();
		this.annualRate = CommonSettings.ZERO;
		this.paymentFrequency = paymentFrequency;

		this.startDate = null;
		this.term = new Period();
		
		this.extraPayment = CommonSettings.ZERO_EXTRA;
		this.extraFrequency = extraFrequency;
		this.extraOrder = ExtraPaymentOrder.AFTER_PAYMENTS;

		this.maxExtraMonth = CommonSettings.PERCENT_100;
		this.maxExtraYear = CommonSettings.PERCENT_100;

		this.minimizeMoPayments = false;

		this.rates = rates;
		this.fixedPayment = 0;
		this.fixedPaymentStartsNOP= 0; 
	}
	
	protected CommonContext(CommonContext parent, double principal, Date startDate, double fixedPayment, int fixedPaymentStartsNOP) {
		this.descr = parent.descr;
		
		this.principal = principal;
		this.amortization = parent.amortization;
		this.annualRate = parent.annualRate;
		this.paymentFrequency = parent.paymentFrequency;

		this.startDate = startDate;
		this.term = parent.term;
		
		this.extraPayment = parent.extraPayment;
		this.extraFrequency = parent.extraFrequency;
		this.extraOrder = parent.extraOrder;

		this.maxExtraMonth = parent.maxExtraMonth;
		this.maxExtraYear = parent.maxExtraYear;

		this.minimizeMoPayments = parent.minimizeMoPayments;

		this.rates = parent.rates;
		this.fixedPayment = fixedPayment;
		this.fixedPaymentStartsNOP = fixedPaymentStartsNOP;
	}

	
	protected CommonContext(CommonContext parent, double principal, Date startDate, Date promoDueDate, double fixedPayment, int fixedPaymentStartsNOP) {
		this.descr = parent.descr;
		
		this.principal = principal;
		this.amortization = parent.amortization;
		this.annualRate = parent.annualRate;
		this.paymentFrequency = parent.paymentFrequency;

		this.startDate = startDate;
		this.term = parent.term;
		
		this.extraPayment = parent.extraPayment;
		this.extraFrequency = parent.extraFrequency;
		this.extraOrder = parent.extraOrder;

		this.maxExtraMonth = parent.maxExtraMonth;
		this.maxExtraYear = parent.maxExtraYear;

		this.minimizeMoPayments = parent.minimizeMoPayments;

		this.rates = parent.rates;
		//this.promoDueDate = parent.promoDueDate;
		this.fixedPayment = fixedPayment;
		this.fixedPaymentStartsNOP = fixedPaymentStartsNOP;
	}

	
	/**
	 * @return the startDay
	 */
	public final Date getStartDate() {
		return startDate;
	}

	/**
	 * @return the yearTerm
	 */
	public final int getYearTerm() {
		return term.getYears();
	}


	/*
	 * Common interface
	 */
	abstract public double getAmortizationRate();
	abstract public double getPayment();
	abstract public void setPayment(double payment);
	abstract public double getPayment(int index);
	abstract public int getPaymentTypeLength();
	abstract public PaymentFrequency getPaymentType(int index);
	abstract public AmortizationTable computate();
	abstract public AmortizationTable computate(int nop);
	abstract public CompoundType getCompoundType(); 

	public String getLabel(){
		return descr;
	}

	
	/* Changes interface */
	public ContextCollection history = new ContextCollection();

	public final ContextCollection getHistory() {
		return history;
	}

	public final void setHistory(ContextCollection history) {
		this.history = history;
	}

	/* Accumulator  interface */
	public ContextCollection accumulator = new ContextCollection();

	public final ContextCollection getAccumulator() {
		return accumulator;
	}

	public final CommonContext getAccumulator(int i) {
		return accumulator.getCollection().get(i);
	}

	public final int getAccumulatorSize() {
		return accumulator.getCollection().size();
	}

	public final void setAccumulator(ContextCollection accumulator) {
		this.accumulator = accumulator;
	}

	final public String getAnnualIntrest(int nop) {
		return Double.toString(Def.RATE2YEAR_INTEREST(getAnnualRate(nop, null)));
	}

	final public double getAnnualRate(int nop, Date paymentDate) {
		int max = rates.list.size();
		if( max == 0 ){
			return annualRate;
		} else if( nop <= 0 ){	
			return rates.list.get(max-1).rate;
		} else if( paymentDate != null ){
			for(int i=0; i<max; i++ ){
				CCRate rate = rates.list.get(i);
				if( rate.nop > 0 && nop <= rate.nop ){
					return rate.rate;
				} else if( rate.tillDate != null && UtilDateTime.beforeEqual(rate.tillDate, paymentDate) ){
					return rate.rate;
				}
			}
		} else {
			for(int i=0; i<max; i++ ){
				CCRate rate = rates.list.get(i);
				if( nop <= rate.nop ){
					return rate.rate;
				}
			}
		}
		return annualRate;
	}

}


