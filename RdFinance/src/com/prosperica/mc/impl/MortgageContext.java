package com.prosperica.mc.impl;

import java.util.Date;

import com.prosperica.common.AmortizationTable;
import com.prosperica.common.CommonContext;
import com.prosperica.common.CommonSettings;
import com.prosperica.common.CompoundType;
import com.prosperica.common.Period;
import com.prosperica.common.RateScheduler;
import com.prosperica.finance.FinancialFunctions;
import com.prosperica.mc.ExtraPaymentFrequency;
import com.prosperica.mc.ExtraPaymentOrder;
import com.prosperica.mc.PaymentFrequency;

public class MortgageContext extends CommonContext {
	
	final static public String className = MortgageContext.class.getName();
	final static public long serialVersionUID = className.hashCode();

	final public CompoundType compoundType = CompoundType.CANADIAN_MORTGAGE;

	final public PaymentFrequency[] paymentType;
	final public double[] paymentAmount;
	final public double[] paymentRate;
	final public double[] amortizationRate;

	public MortgageContext(String descr, double annualRate, double principal, Period amortization, Date startDate, Period term, PaymentFrequency paymentFrequency) {
		this(descr, annualRate, principal, amortization, startDate, term, paymentFrequency, 
			0, null, null, 
			0, 0, false);
	}

	public MortgageContext(String descr, double annualRate, double principal, Period amortization, Date startDate, Period term, 
			PaymentFrequency paymentFrequency, 
			int extraPayment, ExtraPaymentFrequency extraFrequency, ExtraPaymentOrder extraOrder) {
		this(descr, annualRate, principal, amortization, startDate, term, paymentFrequency, 
			extraPayment, extraFrequency, extraOrder, 
			0, 0, false);
	}

	public MortgageContext(String descr, double annualRate, double principal, Period amortization, Date startDate, Period term, 
			PaymentFrequency paymentFrequency, 
			int extraPayment, ExtraPaymentFrequency extraFrequency, ExtraPaymentOrder extraOrder,
			int maxExtraMonth, int maxExtraYear, boolean minimizeMoPayments) {
		super(descr, annualRate, principal, amortization, startDate, term, paymentFrequency, 
				extraPayment, extraFrequency, extraOrder, 
				maxExtraMonth, maxExtraYear, minimizeMoPayments);
		this.paymentType = PaymentFrequency.values();
		this.paymentAmount = new double[paymentType.length];
		this.paymentRate = new double[paymentType.length];
		this.amortizationRate = new double[paymentType.length];
	}

	protected MortgageContext(String descr, PaymentFrequency paymentFrequency, ExtraPaymentFrequency extraFrequency, RateScheduler rates) {
		super(descr, paymentFrequency, extraFrequency, rates);
		this.paymentType = PaymentFrequency.values();
		this.paymentAmount = new double[paymentType.length];
		this.paymentRate = new double[paymentType.length];
		this.amortizationRate = new double[paymentType.length];
	}

	protected MortgageContext(String descr, PaymentFrequency paymentFrequency, ExtraPaymentFrequency extraFrequency, RateScheduler rates, Date dueDate) {
		super(descr, paymentFrequency, extraFrequency, rates, dueDate);
		this.paymentType = PaymentFrequency.values();
		this.paymentAmount = new double[paymentType.length];
		this.paymentRate = new double[paymentType.length];
		this.amortizationRate = new double[paymentType.length];
	}

	protected MortgageContext(MortgageContext parent, double principal, Date startDate, double fixedPayment, int fixedPaymentStartsNOP) {
		super(parent, principal, startDate, fixedPayment, fixedPaymentStartsNOP);
		this.paymentType = parent.paymentType;
		this.paymentAmount = parent.paymentAmount;
		this.paymentRate = parent.paymentRate;
		this.amortizationRate = parent.amortizationRate;
	}

	protected MortgageContext(MortgageContext parent, double principal, Date startDate, Date promoDueDate, double fixedPayment, int fixedPaymentStartsNOP) {
		super(parent, principal, startDate, promoDueDate, fixedPayment, fixedPaymentStartsNOP);
		this.paymentType = parent.paymentType;
		this.paymentAmount = parent.paymentAmount;
		this.paymentRate = parent.paymentRate;
		this.amortizationRate = parent.amortizationRate;
	}


	public final double getPayment(PaymentFrequency pt){
		return paymentAmount[pt.ordinal()];
	}
	
	public final double getPaymentRate(PaymentFrequency pt){
		return paymentRate[pt.ordinal()];
	}

	public final double getAmortizationRate(PaymentFrequency pt){
		return amortizationRate[pt.ordinal()];
	}

	@Override
	public final PaymentFrequency getPaymentType(int index) {
		return paymentType[index];
	}
	
	@Override
	public final double getPayment(int index){
		return paymentAmount[index];
	}

	@Override
	public int getPaymentTypeLength() {
		return paymentType.length;
	}
	
	@Override
	public final double getAmortizationRate(){
		return getAmortizationRate(this.paymentFrequency);
	}

	@Override
	public final double getPayment(){
		return getPayment(this.paymentFrequency);
	}

	@Override
	public final void setPayment(double payment){
		 paymentAmount[this.paymentFrequency.ordinal()] = payment;
	}

	@Override
	public CompoundType getCompoundType() {
		return compoundType;
	}

	@Override
	final public AmortizationTable computate(){
		for(int i=0; i<paymentType.length; i++){
			amortizationRate[i] = FinancialFunctions.mortgagePaymentRate(annualRate, getCompoundType().conversionPeriods, paymentType[i].paymentsPerYear);
			paymentRate[i] = FinancialFunctions.mortgagePaymentRate(annualRate, getCompoundType().conversionPeriods, paymentType[i].paymentsPerYearEffective);
			paymentAmount[i] = FinancialFunctions.mortgagePayment(paymentType[i], principal, paymentRate[i], amortization, CommonSettings.SCALE);
			termNumberOfPayments = term.getTotalPayments(paymentFrequency);
			totalNumberOfPayments =amortization.getTotalPayments(paymentFrequency);
		}
		amortizationTable = new AmortizationTable(this);
		return amortizationTable;
	}

	@Override
	public AmortizationTable computate(int nop){
		for(int i=0; i<paymentType.length; i++){
			amortizationRate[i] = FinancialFunctions.mortgagePaymentRate(annualRate, getCompoundType().conversionPeriods, paymentType[i].paymentsPerYear);
			if( fixedPayment > 0 && nop >= fixedPaymentStartsNOP ){
				paymentRate[i] = 0.0; // TODO: calculate fixed payment rate here
				paymentAmount[i] = fixedPayment;
			} else {
				paymentRate[i] = FinancialFunctions.mortgagePaymentRate(annualRate, getCompoundType().conversionPeriods, paymentType[i].paymentsPerYearEffective);
				paymentAmount[i] = FinancialFunctions.mortgagePayment(paymentType[i], principal, paymentRate[i], amortization, CommonSettings.SCALE);
			}
			termNumberOfPayments = term.getTotalPayments(paymentFrequency);
			totalNumberOfPayments =amortization.getTotalPayments(paymentFrequency);
		}
		amortizationTable = new AmortizationTable(this);
		return amortizationTable;
	}


	final public MortgageContext changePaymentFrequency(Date startDate, PaymentFrequency paymentFrequency){
		CommonContext prev = getHistory().getLast(this);
		
		MortgageContext next = new MortgageContext(prev.descr, prev.annualRate, prev.principal, prev.amortization, 
				startDate, prev.term, 
				paymentFrequency, 
				prev.extraPayment, prev.extraFrequency, prev.extraOrder, 
				prev.maxExtraMonth, prev.maxExtraYear, prev.minimizeMoPayments);
		
		next.setHistory(getHistory().add(next));
		return this;
	}
	
	final public MortgageContext changeRate(Date startDate, double annualRate){
		CommonContext prev = getHistory().getLast(this);
		
		MortgageContext next = new MortgageContext(prev.descr,
				annualRate, prev.principal, prev.amortization, 
				startDate, prev.term, 
				prev.paymentFrequency, 
				prev.extraPayment, prev.extraFrequency, prev.extraOrder, 
				prev.maxExtraMonth, prev.maxExtraYear, prev.minimizeMoPayments);
		
		next.setHistory(getHistory().add(next));		
		return this;
	}
	
}

