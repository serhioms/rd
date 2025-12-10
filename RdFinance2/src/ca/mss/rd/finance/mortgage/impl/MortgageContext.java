package ca.mss.rd.finance.mortgage.impl;

import java.text.NumberFormat;
import java.util.Date;

import ca.mss.rd.finance.mortgage.ExtraPaymentFrequency;
import ca.mss.rd.finance.mortgage.ExtraPaymentOrder;
import ca.mss.rd.finance.mortgage.PaymentFrequency;
import ca.mss.rd.util.UtilDateTime;



public class MortgageContext {
	final static public String className = MortgageContext.class.getName();
	final static public long serialVersionUID = className.hashCode();
	final public double principal;
	final public MortgageDuration duration;
	final public double annualRate;

	final public PaymentFrequency[] paymentType;
	final public double[] paymentAmount;
	final public double[] paymentRate;
	final public double[] amortizationRate;

	final public int extraPayment;
	final public ExtraPaymentFrequency extraFrequency;
	final public ExtraPaymentOrder extraOrder; 

	final public double maxExtraMonth;
	final public double maxExtraYear;

	final public boolean minimizeMoPayments;

	Date startDate; 
	public int termYears; 
	
	private final NumberFormat numberFormatter;

	public MortgageContext(double annualRate, double principal, MortgageDuration duration, Date startDate, int termYears) {
		this(annualRate, principal, duration, startDate, termYears, 0, null, null, 0, 0, false);
	}

	public MortgageContext(double annualRate, double principal, MortgageDuration duration, Date startDate, int termYears, 
			int extraPayment, ExtraPaymentFrequency extraFrequency, ExtraPaymentOrder extraOrder) {
		this(annualRate, principal, duration, startDate, termYears,  
			extraPayment, extraFrequency, extraOrder, 
			0, 0, false);
	}

	public MortgageContext(double annualRate, double principal, MortgageDuration duration, Date startDate, int termYears, 
			int extraPayment, ExtraPaymentFrequency extraFrequency, ExtraPaymentOrder extraOrder,
			int maxExtraMonth, int maxExtraYear, boolean minimizeMoPayments) {
		this.principal = principal;
		
		this.duration = duration;
		this.annualRate = annualRate;

		this.paymentType = PaymentFrequency.values();
		this.paymentAmount = new double[paymentType.length];
		this.paymentRate = new double[paymentType.length];
		this.amortizationRate = new double[paymentType.length];
		this.startDate = startDate;
		this.termYears = termYears;
		
		this.extraPayment = extraPayment;
		this.extraFrequency = extraFrequency;
		this.extraOrder = extraOrder;

		this.maxExtraMonth = maxExtraMonth;
		this.maxExtraYear = maxExtraYear;

		this.minimizeMoPayments = minimizeMoPayments;
		this.numberFormatter = NumberFormat.getInstance();
		this.numberFormatter.setMaximumFractionDigits(MortgageSettings.SCALE);
	}

	public final double getPayment(int index){
		return paymentAmount[index];
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

	public final String format(String s){
		return s;
	}
	
	public final String format(int n){
		return Integer.toString(n);
	}
	
	public final String format(double d){
		return numberFormatter.format(d);
	}
	
	public final String format(Date d){
		return UtilDateTime.format(d);
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
		return termYears;
	}

	public final PaymentFrequency getPaymentType(int index) {
		return paymentType[index];
	}
	
	public final int getPaymentTypeLength() {
		return paymentType.length;
	}
	
}