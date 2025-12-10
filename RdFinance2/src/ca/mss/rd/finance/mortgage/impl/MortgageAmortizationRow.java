package ca.mss.rd.finance.mortgage.impl;

import java.util.Date;

import ca.mss.rd.finance.mortgage.ExtraPaymentOrder;
import ca.mss.rd.util.UtilDateTime;



public class MortgageAmortizationRow {
	
	final static public String className = MortgageAmortizationRow.class.getName();
	final static public long serialVersionUID = className.hashCode();

	final public MortgageAmortization amortization;
	final public MortgageContext context;
	
	public int nop;
	public Date balanceInDate;
	public double balanceIn;
	public double interest;
	public double interestPrc;
	public double principal;
	public double principalPrc;
	public Date payday;
	public double payment;
	public double paymentMo;
	public double extraPayment;
	public double extraPaymentMo;
	public double extraPaymentYear;
	public double extraPrc;
	public double extraPrcMo;
	public double extraPrcYear;
	public double fullPayment;
	public double fullPrincipal;
	public double fullPrincipalPrc;
	public double totalFullPayment;
	public double totalExtraPayment;
	public double totalExtraPrc;
	public double totalPayment;
	public double totalInterest;
	public double totalInterestPrc;
	public double totalPrincipal;
	public double totalPrincipalPrc;
	public double balanceOut;
	public Date balanceOutDate;
	public int balanceOutYear;
	public int nextBalanceOutYear; // For hasNext filtering
	public String balanceOutMonth;
	public String balanceOutMonthYear;
	public String balanceOutMonthDay;
	public String balanceOutMonthDayYear;
	public String balanceOutQuoter;
	public int balanceTerm;
	public int nextBalanceTerm;  // For hasNext filtering

	public int payInMo;
	public Date paydayPrev;
	public Date paydayNext;

	public MortgageAmortizationRow(MortgageAmortization amortization) {
		this.amortization = amortization;
		this.context = amortization.context;
	}

	final public boolean ifProcessedExtrasBefore(){
		extraPayment = 0.0;
		if( context.extraPayment > 0.0 && !(context.minimizeMoPayments  && payInMo > amortization.paymentFrequency.paymentsPerMonth)){
			if( context.extraOrder == ExtraPaymentOrder.BEFORE_PAYMENTS ){
				if( nop == 1 || !UtilDateTime.isSameMonth(payday, paydayPrev) ){
					switch( context.extraFrequency ){
					case ANNUAL:
						extraPayment = (UtilDateTime.getMonth(payday)-1) == 0? context.extraPayment: 0.0; 
						break;
					case SEMI_ANNUAL:
						extraPayment = (UtilDateTime.getMonth(payday)-1) % 6 == 0? context.extraPayment: 0.0; 
						break;
					case QUOTERLY:
						extraPayment = (UtilDateTime.getMonth(payday)-1) % 3 == 0? context.extraPayment: 0.0; 
						break;
					case MONTHLY:
						extraPayment = context.extraPayment; 
						break;
					}
					return true;
				}
			}
		}
		return false;
	}

	final public boolean ifProcessedExtrasAfter(){
		extraPayment = 0.0;
		if( context.extraPayment > 0.0 && !(context.minimizeMoPayments  && payInMo > amortization.paymentFrequency.paymentsPerMonth)){
			if( context.extraOrder == ExtraPaymentOrder.AFTER_PAYMENTS ){
				if( !UtilDateTime.isSameMonth(payday, paydayNext) ){
					switch( context.extraFrequency ){
					case ANNUAL:
						extraPayment = UtilDateTime.getMonth(payday) == 12? context.extraPayment: 0.0; 
						break;
					case SEMI_ANNUAL:
						extraPayment = UtilDateTime.getMonth(payday) % 6 == 0? context.extraPayment: 0.0; 
						break;
					case QUOTERLY:
						extraPayment = UtilDateTime.getMonth(payday) % 3 == 0? context.extraPayment: 0.0; 
						break;
					case MONTHLY:
						extraPayment =  context.extraPayment; 
						break;
					}
					return true;
				}
			}
		}
		return false;
	}

}


