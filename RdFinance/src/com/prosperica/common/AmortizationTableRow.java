package com.prosperica.common;

import java.util.Date;

import ca.mss.rd.util.UtilDateTime;

import com.prosperica.cashflow.CashFlowRow;
import com.prosperica.mc.ExtraPaymentOrder;


public class AmortizationTableRow implements CashFlowRow {
	
	final static public String className = AmortizationTableRow.class.getName();
	final static public long serialVersionUID = className.hashCode();

	public int nop;
	public Date balanceInDate;
	public double balanceIn;
	public double balanceInAnnual;
	public double interest;
	public double interestPrc;
	public double interestAnnualTotal;
	public double interestAnnualPrc;
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
	public double totalInterestPrc2Principle;
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
	public double currentRatePrc;
	
	CommonContext context;
	
	public AmortizationTableRow(CommonContext context) {
		this.context = context;
	}

	final public boolean ifProcessedExtrasBefore(){
		extraPayment = 0.0;
		if( context.extraPayment > 0.0 && !(context.minimizeMoPayments  && payInMo > context.paymentFrequency.paymentsPerMonth)){
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
		if( context.extraPayment > 0.0 && !(context.minimizeMoPayments  && payInMo > context.paymentFrequency.paymentsPerMonth)){
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

	/* (non-Javadoc)
	 * @see com.prosperica.cashflow.CashFlowRow#getPayDay()
	 */
	public Date getPayDay() {
		return payday;
	}

	/* (non-Javadoc)
	 * @see com.prosperica.cashflow.CashFlowRow#getPayment()
	 */
	public double getPayment() {
		return fullPayment;
	}

	/* (non-Javadoc)
	 * @see com.prosperica.cashflow.CashFlowRow#getDescription()
	 */
	public String getDescription() {
		return context.getLabel()+" ("+currentRatePrc+"%/y)";
	}

	public double getInterest() {
		return context.getCompoundType() == CompoundType.CANADIAN_MORTGAGE? CommonSettings.ZERO: interest;
	}


	public double getBalance() {
		return balanceIn;
	}


	public double getInterestTotal() {
		return totalInterest;
	}


	public double getInterestPrc() {
		return totalInterestPrc2Principle;
	}

	
}


