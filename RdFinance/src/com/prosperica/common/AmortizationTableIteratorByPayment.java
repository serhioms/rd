package com.prosperica.common;

import java.util.Date;
import java.util.Iterator;

import ca.mss.rd.util.UtilDateTime;
import ca.mss.rd.util.UtilMath;

import com.prosperica.excel.ExcelFunctions;
import com.prosperica.mc.util.UtilMortgageCalendar;


public class AmortizationTableIteratorByPayment implements Iterator<AmortizationTableRow> {
	
	final static public String className = AmortizationTableIteratorByPayment.class.getName();
	final static public long serialVersionUID = className.hashCode();

	protected double balance = 0.0;
	private int gapDays = 0;

	private int order;
	private AmortizationTableRow row;

	public AmortizationTableIteratorByPayment(CommonContext context) {
		this.row = new AmortizationTableRow(context);
		this.balance = row.context.principal;
		this.order = 1;
	}

	/* (non-Javadoc)
	 * @see java.util.Iterator#hasNext()
	 */
	public boolean hasNext() {
		return balance > ExcelFunctions.ZERO;
	}

	/* (non-Javadoc)
	 * @see java.util.Iterator#next()
	 */
	public AmortizationTableRow next() {

		if( row.nop == 0 ){
			row.payday = row.context.getStartDate();
			row.paydayNext = UtilMortgageCalendar.getNextPayday(1, row.context.paymentFrequency, row.payday);
		} else {
			row.paydayPrev = row.payday;
			row.payday = row.paydayNext;
			row.paydayNext = UtilMortgageCalendar.getNextPayday(1, row.context.paymentFrequency, row.payday);
		}
		
		row.nop = order++;

		
		/* Check if payment depends on NOP */
		if( row.context.fixedPaymentStartsNOP > 0 && row.nop >= row.context.fixedPaymentStartsNOP ){
			row.context.setPayment(row.context.fixedPayment);
		}

		/* check for mortgage changes */
		if( row.context.getHistory().hasNext() ){
			CommonContext newContext =  row.context.getHistory().getNext();
			if( UtilDateTime.beforeEqual(row.payday, newContext.startDate) ){
				row.context.getHistory().increment();

				/* initialize context */
				newContext.computate();
				
				/* reset context */
				row.context = newContext;
				
				/* next mortgage amortization starts here */
				row.payday = row.context.startDate;
				row.paydayNext = UtilMortgageCalendar.getNextPayday(1, row.context.paymentFrequency, row.payday);
				this.gapDays = UtilDateTime.getDays(UtilMortgageCalendar.getNextPayday(1, row.context.paymentFrequency, row.paydayPrev), row.payday);
			}
		}
		
		
		
		row.balanceIn = balance;
		row.balanceInDate = row.payday;

		row.interest = ExcelFunctions.round(row.balanceIn*row.context.getAmortizationRate(), CommonSettings.SCALE);
		row.principal = ExcelFunctions.round(row.context.getPayment() - row.interest, CommonSettings.SCALE);

		row.currentRatePrc = ExcelFunctions.round(row.context.getAmortizationRate()*row.context.paymentFrequency.paymentsPerYear*100.0D, 2);
		
		row.interest = ExcelFunctions.round(row.balanceIn*row.context.getAmortizationRate(), CommonSettings.SCALE);
		row.principal = ExcelFunctions.round(row.context.getPayment() - row.interest, CommonSettings.SCALE);
		
		if( this.gapDays != 0 ){
			row.interest = ExcelFunctions.round(row.interest*(row.context.paymentFrequency.periodDays+this.gapDays)/row.context.paymentFrequency.periodDays, CommonSettings.SCALE);
			row.principal = ExcelFunctions.round(row.principal*(row.context.paymentFrequency.periodDays+this.gapDays)/row.context.paymentFrequency.periodDays, CommonSettings.SCALE);
			this.gapDays = 0;
		}

		row.balanceOutDate = row.payday;
		row.balanceOutYear = Integer.parseInt(UtilDateTime.format(row.payday, "yyyy"));
		row.balanceOutMonth = UtilDateTime.format(row.payday, "MMM");
		row.balanceOutMonthDay = UtilDateTime.format(row.payday, "MMM d");
		row.balanceOutMonthYear = UtilDateTime.format(row.payday, "MMM yyyy");
		row.balanceOutQuoter = UtilDateTime.getQuoter(row.payday)+"/"+UtilDateTime.format(row.payday, "yy");
		row.balanceOut = UtilMath.round(row.balanceIn - row.principal, CommonSettings.SCALE);
		row.balanceTerm = row.context.termNumberOfPayments>0?(row.nop-1)/row.context.termNumberOfPayments+1:1;

		if( !UtilDateTime.isSameMonth(row.payday, row.paydayPrev) ){
			row.payInMo = calculateNumberOfPaymentsInMonth(row.payday, row);
		}
		
		if( row.balanceOut <= AmortizationTable.MINIMUM_PAYMENT ){
			row.principal += row.balanceOut;
			row.balanceOut = 0.0;
		}
		
		row.payment = UtilMath.round(row.interest + row.principal, CommonSettings.SCALE);
		if( UtilDateTime.isSameMonth(row.payday, row.paydayPrev)){
			row.paymentMo += row.payment;
		} else {
			row.paymentMo = row.payment;
		}

		row.totalInterest = ExcelFunctions.round(row.totalInterest+row.interest, CommonSettings.SCALE);
		row.totalPrincipal = ExcelFunctions.round(row.totalPrincipal+row.principal, CommonSettings.SCALE);
		row.totalPayment = ExcelFunctions.round(row.totalPayment+row.payment, CommonSettings.SCALE);
		
		row.principalPrc = row.payment>0? ExcelFunctions.round(row.principal*100.0/row.payment, CommonSettings.SCALE): 0.0;
		row.interestPrc = row.payment>0? ExcelFunctions.round(row.interest*100.0/row.payment, CommonSettings.SCALE): 0.0;
		row.totalPrincipalPrc = row.totalPayment>0? ExcelFunctions.round(row.totalPrincipal*100.0/row.totalPayment, CommonSettings.SCALE): 0.0;
		row.totalInterestPrc = row.totalPayment>0? ExcelFunctions.round(row.totalInterest*100.0/row.totalPayment, CommonSettings.SCALE): 0.0;
		row.totalInterestPrc2Principle = row.context.principal>0? ExcelFunctions.round(row.totalInterest*100.0/row.context.principal, CommonSettings.SCALE): 0.0;

		/* next iteration */
		balance = row.balanceOut;
		row.nextBalanceOutYear = Integer.parseInt(UtilDateTime.format(row.paydayNext, "yyyy"));
		row.nextBalanceTerm = row.context.termNumberOfPayments>0? (order-1)/row.context.termNumberOfPayments+1: 1;

		/* annual intrest calculation */
		if( !UtilDateTime.isSameYear(row.payday, row.paydayPrev)){
			row.balanceInAnnual = row.balanceIn; 
			row.interestAnnualTotal = 0;
		}
		row.interestAnnualTotal += row.interest;
		row.interestAnnualPrc = ExcelFunctions.round(row.interestAnnualTotal*100.0/row.balanceInAnnual, CommonSettings.SCALE);

		return row;
	}

	/* (non-Javadoc)
	 * @see java.util.Iterator#remove()
	 */
	public void remove() {
		/* nothing */
	}
	
	final private int calculateNumberOfPaymentsInMonth(Date payday, AmortizationTableRow row){
		int payInMo = 1;
		
		for(Date next=row.paydayNext;  
				UtilDateTime.isSameMonth(payday, next); 
				next=UtilMortgageCalendar.getNextPayday(1, row.context.paymentFrequency, next) ){
				payInMo++;
			}
			
		for(Date prev=row.paydayPrev;  
				UtilDateTime.isSameMonth(payday, prev); 
				prev=UtilMortgageCalendar.getNextPayday(-1, row.context.paymentFrequency, prev) ){
				payInMo++;
			}
			
		return payInMo;
	}


}


