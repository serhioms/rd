package ca.mss.rd.finance.mortgage.impl;

import java.util.Date;
import java.util.Iterator;

import ca.mss.rd.excel.ExcelFunctions;
import ca.mss.rd.finance.mortgage.util.UtilMortgageCalendar;
import ca.mss.rd.util.UtilDateTime;
import ca.mss.rd.util.UtilMath;



public class MortgageAmortizationIteratorByPayment implements Iterator<MortgageAmortizationRow> {
	
	final static public String className = MortgageAmortizationIteratorByPayment.class.getName();
	final static public long serialVersionUID = className.hashCode();

	final public MortgageAmortization amortization;
	final public MortgageContext context;

	protected double balance = 0.0;

	private int order;
	private MortgageAmortizationRow row;

	public MortgageAmortizationIteratorByPayment(MortgageAmortization amortization) {
		this.amortization = amortization;
		this.context = amortization.context;
		this.row = new MortgageAmortizationRow(amortization);
		this.balance = amortization.context.principal;
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
	public MortgageAmortizationRow next() {

		if( row.nop == 0 ){
			row.payday = amortization.context.getStartDate();
			row.paydayNext = UtilMortgageCalendar.getNextPayday(1, amortization.paymentFrequency, row.payday);
		} else {
			row.paydayPrev = row.payday;
			row.payday = row.paydayNext;
			row.paydayNext = UtilMortgageCalendar.getNextPayday(1, amortization.paymentFrequency, row.payday);
		}

		row.nop = order++;
		
		row.balanceIn = balance;
		row.balanceInDate = row.payday;

		row.interest = ExcelFunctions.round(row.balanceIn*amortization.context.getAmortizationRate(amortization.paymentFrequency), MortgageSettings.SCALE);;
		row.principal = ExcelFunctions.round(amortization.context.getPayment(amortization.paymentFrequency) - row.interest, MortgageSettings.SCALE);

		row.balanceOutDate = row.payday;
		row.balanceOutYear = Integer.parseInt(UtilDateTime.format(row.payday, "yyyy"));
		row.balanceOutMonth = UtilDateTime.format(row.payday, "MMM");
		row.balanceOutMonthDay = UtilDateTime.format(row.payday, "MMM d");
		row.balanceOutMonthYear = UtilDateTime.format(row.payday, "MMM yyyy");
		row.balanceOutQuoter = UtilDateTime.getQuoter(row.payday)+"/"+UtilDateTime.format(row.payday, "yy");
		row.balanceOut = UtilMath.round(row.balanceIn - row.principal, MortgageSettings.SCALE);
		row.balanceTerm = (row.nop-1)/amortization.termNumberOfPayments+1;

		if( !UtilDateTime.isSameMonth(row.payday, row.paydayPrev) ){
			row.payInMo = calculateNumberOfPaymentsInMonth(row.payday, row);
		}
		
		if( row.balanceOut <= MortgageAmortization.MINIMUM_PAYMENT ){
			row.principal = ExcelFunctions.round(row.principal+row.balanceOut,2);
			row.balanceOut = 0.0;
		}
		
		row.payment = UtilMath.round(row.interest + row.principal, MortgageSettings.SCALE);
		if( UtilDateTime.isSameMonth(row.payday, row.paydayPrev)){
			row.paymentMo = ExcelFunctions.round(row.paymentMo+row.payment,2);
		} else {
			row.paymentMo = row.payment;
		}

		row.totalInterest = ExcelFunctions.round(row.totalInterest+row.interest,2);
		row.totalPrincipal = ExcelFunctions.round(row.totalPrincipal+row.principal,2);
		row.totalPayment = ExcelFunctions.round(row.totalPayment+row.payment,2);
		
		row.principalPrc = ExcelFunctions.round(row.principal*100.0/row.payment, 2);
		row.interestPrc = ExcelFunctions.round(row.interest*100.0/row.payment, 2);
		row.totalPrincipalPrc = ExcelFunctions.round(row.totalPrincipal*100.0/row.totalPayment, 2);
		row.totalInterestPrc = ExcelFunctions.round(row.totalInterest*100.0/row.totalPayment, 2);

		/* next iteration */
		balance = row.balanceOut;
		row.nextBalanceOutYear = Integer.parseInt(UtilDateTime.format(row.paydayNext, "yyyy"));
		row.nextBalanceTerm = (order-1)/amortization.termNumberOfPayments+1;

		return row;
	}

	/* (non-Javadoc)
	 * @see java.util.Iterator#remove()
	 */
	public void remove() {
		/* nothing */
	}
	
	final private int calculateNumberOfPaymentsInMonth(Date payday, MortgageAmortizationRow row){
		int payInMo = 1;
		
		for(Date next=row.paydayNext;  
				UtilDateTime.isSameMonth(payday, next); 
				next=UtilMortgageCalendar.getNextPayday(1, amortization.paymentFrequency, next) ){
				payInMo++;
			}
			
		for(Date prev=row.paydayPrev;  
				UtilDateTime.isSameMonth(payday, prev); 
				prev=UtilMortgageCalendar.getNextPayday(-1, amortization.paymentFrequency, prev) ){
				payInMo++;
			}
			
		return payInMo;
	}
}


