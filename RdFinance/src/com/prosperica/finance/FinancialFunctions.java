package com.prosperica.finance;

import ca.mss.rd.util.UtilMath;

import com.prosperica.common.Period;
import com.prosperica.excel.ExcelFunctions;
import com.prosperica.mc.PaymentFrequency;



public class FinancialFunctions {

	final static public String className = FinancialFunctions.class.getName();
	final static public long serialVersionUID = className.hashCode();
	
	final static public double loanPaymentRate(double annualRate, int paymentsPerYear){
		return annualRate/paymentsPerYear;
	}
	
	final static public double mortgageCompoundRate(double annualRate, int compoundFrequency){
		return Math.pow(1+loanPaymentRate(annualRate, compoundFrequency), compoundFrequency)-1.0;
	}
	
	final static public double mortgagePaymentRate(double annualRate, int compoundFrequency, int effectiveYearPayments){
		return Math.pow(1+mortgageCompoundRate(annualRate, compoundFrequency), 1.0/effectiveYearPayments)-1.0;
	}
	
	final static public double mortgagePaymentMonthly(double paymentRate, double principal, int totalPayments){
		return mortgagePaymentRegular(paymentRate, principal, totalPayments);
	}

	final static public double mortgagePaymentRegular(double paymentRate, double principal, int totalPayments){
		return -ExcelFunctions.PMT(paymentRate, principal, totalPayments);
	}

	final static public double loanPayment(double paymentRate, double principal, int numberOfPayments){
		return -ExcelFunctions.PMT(paymentRate, principal, numberOfPayments);
	}

	final static public double creditCardPayment(double paymentRate, double principal, int scale){
		return UtilMath.round(paymentRate*principal, scale);
	}

	final static public double mortgagePaymentAccelerated(double paymentRate, double principal, int totalPayments, int monthPayments){
		return mortgagePaymentMonthly(paymentRate, principal, totalPayments)/monthPayments;
	}

	final static public double mortgagePayment(PaymentFrequency pf, double principal, double paymentRate, Period amortization, int scale){
		switch( pf ){
		case MONTHLY:
		case SEMI_MONTHLY:
		case BI_WEEKLY:
		case WEEKLY:
			return ExcelFunctions.round(mortgagePaymentRegular(paymentRate, principal, amortization.getTotalPayments(pf)), scale);
		case ACCELERATED_BI_WEEKLY:
		case ACCELERATED_WEEKLY:
			return ExcelFunctions.round(mortgagePaymentAccelerated(paymentRate, principal, amortization.getTotalPayments(PaymentFrequency.MONTHLY), pf.paymentsPerMonth), scale);
		default:
			throw new RuntimeException("Not supported payment type ["+pf+"]");
		}
	}

	final static public double loanPayment(PaymentFrequency pf, double principal, double paymentRate, int periodYears, int scale){
		return ExcelFunctions.round(loanPayment(paymentRate, principal, periodYears*pf.paymentsPerYear), scale);
	}


}
