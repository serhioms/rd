package ca.mss.rd.finance;

import ca.mss.rd.excel.ExcelFunctions;
import ca.mss.rd.finance.mortgage.PaymentFrequency;
import ca.mss.rd.finance.mortgage.impl.MortgageDuration;




public class FinancialFunctions {

	final static public String className = FinancialFunctions.class.getName();
	final static public long serialVersionUID = className.hashCode();
	
	final static public double loanPaymentRate(double annualRate, int paymentFrequency){
		return annualRate/paymentFrequency;
	}
	
	final static public double mortgageCompoundRate(double annualRate, int compoundFrequency){
		return Math.pow(1+loanPaymentRate(annualRate, compoundFrequency), compoundFrequency)-1.0;
	}
	
	final static public double mortgagePaymentRate(double annualRate, int compoundFrequency, int effectiveYearPayments){
		return Math.pow(1+mortgageCompoundRate(annualRate, compoundFrequency), 1.0/effectiveYearPayments)-1.0;
	}
	
	final static public double mortgagePaymentMonthly(double paymentRate, double principal, int amortizationYears){
		return mortgagePaymentRegular(paymentRate, principal, amortizationYears, PaymentFrequency.MONTHLY.paymentsPerYear);
	}

	final static public double mortgagePaymentRegular(double paymentRate, double principal, int totalPayments, int yearPayments){
		return -ExcelFunctions.PMT(paymentRate, principal, totalPayments);
	}

	final static public double mortgagePaymentAccelerated(double paymentRate, double principal, int amortizationYears, int monthPayments){
		return mortgagePaymentMonthly(paymentRate, principal, amortizationYears)/monthPayments;
	}

	final static public double mortgagePayment(PaymentFrequency pf, double principal, double paymentRate, MortgageDuration duration, int scale){
		switch( pf ){
		case MONTHLY:
		case SEMI_MONTHLY:
		case BI_WEEKLY:
		case WEEKLY:
			return ExcelFunctions.round(FinancialFunctions.mortgagePaymentRegular(paymentRate, principal, duration.getPaymentsNo(pf), pf.paymentsPerYear), scale);
		case ACCELERATED_BI_WEEKLY:
		case ACCELERATED_WEEKLY:
			return ExcelFunctions.round(FinancialFunctions.mortgagePaymentAccelerated(paymentRate, principal, duration.getPaymentsNo(PaymentFrequency.MONTHLY), pf.paymentsPerMonth), scale);
		default:
			throw new RuntimeException("Not supported payment type ["+pf+"]");
		}
	}


}
