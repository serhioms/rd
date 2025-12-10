package com.prosperica.common;

import java.util.Iterator;

import ca.mss.rd.util.UtilDateTime;

import com.prosperica.excel.ExcelFunctions;
import com.prosperica.mc.AmortizationType;


public class AmortizationTableIterator extends AmortizationTableIteratorFiltered {
	
	final static public String className = AmortizationTableIterator.class.getName();
	final static public long serialVersionUID = className.hashCode();

	final private AmortizationType at;

	private int order;
	private AmortizationTableRow row;
	private AmortizationTableRow payrow;

	final private Iterator<AmortizationTableRow>[] iterator;

	public AmortizationTableIterator(CommonContext context, AmortizationType at) {
		this(context, at, 0, 0);
	}

	public AmortizationTableIterator(CommonContext context, AmortizationType at, int year, int term) {
		super(context, year, term);
		this.at = at;
		this.order = 1;
		this.row = new AmortizationTableRow(context);
		if( context.getAccumulatorSize() > 0 ){
			this.iterator = new Iterator[context.getAccumulatorSize()];
			for(int i=0; i<iterator.length; i++){
				this.iterator[i] = context.getAccumulator(i).computate().getIterator(at, year, term);
			}
		} else {
			this.iterator = new Iterator[0];
		}
	}

	/* (non-Javadoc)
	 * @see com.prosperica.common.AmortizationTableIteratorFiltered#hasNext()
	 */
	@Override
	public boolean hasNext() {
		if( iterator.length == 0 ){
			return super.hasNext();
		}
		for(int i=0; i<iterator.length; i++){
			if( iterator[i].hasNext() )
				return true;
		}
		return false;
	}


	/* (non-Javadoc)
	 * @see java.util.Iterator#next()
	 */
	@Override
	public AmortizationTableRow next() {
		if( iterator.length == 0 )
			return next1();
		else
			return next2();
	}

	final private AmortizationTableRow next2() {
		row.nop = order++;

		row.balanceIn = 0.0;
		row.balanceInAnnual = 0.0;
		row.interest = 0.0;
		row.principal = 0.0;
		row.payment = 0.0;
		row.paymentMo = 0.0;
		row.extraPayment = 0.0;
		row.extraPaymentMo = 0.0;
		row.extraPaymentYear = 0.0;
		row.fullPayment = 0.0;
		row.extraPaymentMo = 0.0; 
		row.extraPaymentYear = 0.0; 
		row.totalInterest = 0.0;
		row.interestAnnualTotal = 0.0;
		row.totalPrincipal = 0.0;
		row.totalPayment = 0.0;
		row.totalExtraPayment = 0.0;
		row.totalFullPayment = 0.0;
		row.balanceOut = 0.0;

		for(int i=0; i<iterator.length; i++){
			if( iterator[i].hasNext() ){
				
				CommonContext cc = row.context.getAccumulator(i);
				/* Check if rate depends on NOP */
				double newAnnualRate;
				if( row.payday == null )
					row.payday = row.context.startDate;
				if( cc.annualRate != (newAnnualRate=cc.getAnnualRate(row.nop, row.payday)) ){
					cc.annualRate = newAnnualRate;
					cc.computate(row.nop);
				}
				
				payrow = iterator[i].next();
				
				row.paydayPrev = payrow.paydayPrev;
				row.payday = payrow.payday;
				row.paydayNext = payrow.paydayNext;

				row.balanceIn += payrow.balanceIn;
				row.balanceInAnnual += payrow.balanceInAnnual;
				row.balanceInDate = payrow.balanceInDate;
				
				row.currentRatePrc = payrow.currentRatePrc;
				
				row.interest += payrow.interest;
				row.principal += payrow.principal;
				row.payment += payrow.payment;
				
				row.extraPayment += payrow.extraPayment;
				row.extraPaymentMo += payrow.extraPaymentMo;
				row.extraPaymentYear += payrow.extraPaymentYear;
				row.fullPayment += payrow.fullPayment;

				while( isNextPaymentSamePeriod() ) {

					if( iterator[i].hasNext() )
						payrow = iterator[i].next();
					else
						break;
					
					/* For proper calculation */
					row.interest += payrow.interest;
					row.principal += payrow.principal;
					row.payment += payrow.payment;
					row.extraPayment += payrow.extraPayment;
					row.fullPayment += payrow.fullPayment;
					
				}

				row.paymentMo += payrow.paymentMo;
				row.extraPaymentMo += payrow.extraPaymentMo; 
				row.extraPaymentYear += payrow.extraPaymentYear; 

				row.extraPrcMo = payrow.extraPrcMo; // ?
				row.extraPrcYear = payrow.extraPrcYear; // ?

				row.totalInterest += payrow.totalInterest;
				row.interestAnnualTotal += payrow.interestAnnualTotal;
				row.totalPrincipal += payrow.totalPrincipal;
				row.totalPayment += payrow.totalPayment;
				row.totalExtraPayment += payrow.totalExtraPayment;
				row.totalFullPayment += payrow.totalFullPayment;
				
				row.totalExtraPrc = payrow.totalExtraPrc; // ?
				row.totalPrincipalPrc = payrow.totalPrincipalPrc; // ?
				row.totalInterestPrc = payrow.totalInterestPrc; // ?
				row.totalInterestPrc2Principle = payrow.totalInterestPrc2Principle;

				row.principalPrc = row.fullPayment>0? ExcelFunctions.round(row.principal*100.0/row.fullPayment, 2):0;
				row.interestPrc = row.fullPayment>0? ExcelFunctions.round(row.interest*100.0/row.fullPayment, 2):0;
				row.interestAnnualPrc = ExcelFunctions.round(row.interestAnnualTotal*100.0/row.balanceInAnnual, 2);
				row.extraPrc = row.fullPayment>0? ExcelFunctions.round(row.extraPayment*100.0/row.fullPayment, 2):0;
				
				row.balanceOut += payrow.balanceOut;

				row.balanceOutDate = payrow.balanceOutDate;
				row.balanceOutMonth = payrow.balanceOutMonth;
				row.balanceOutMonthYear = payrow.balanceOutMonthYear;
				row.balanceOutMonthDay = payrow.balanceOutMonthDay;
				row.balanceOutQuoter = payrow.balanceOutQuoter;
				row.balanceOutYear = payrow.balanceOutYear;
				row.nextBalanceOutYear = payrow.nextBalanceOutYear;
				row.balanceTerm = payrow.balanceTerm;
				row.nextBalanceTerm = payrow.nextBalanceTerm;

				row.fullPrincipal = row.principal + row.extraPayment; 
				row.fullPrincipalPrc = row.principalPrc + row.extraPrc; 

				row.payInMo = payrow.payInMo;
			}
		}
		return row;
	}

	final private AmortizationTableRow next1() {

		payrow = super.next();
		
		row.nop = order++;
		
		row.paydayPrev = payrow.paydayPrev;
		row.payday = payrow.payday;
		row.paydayNext = payrow.paydayNext;

		row.balanceIn = payrow.balanceIn;
		row.balanceInAnnual = payrow.balanceInAnnual;
		row.balanceInDate = payrow.balanceInDate;
		
		row.currentRatePrc = payrow.currentRatePrc;
		
		row.interest = payrow.interest;
		row.principal = payrow.principal;
		row.payment = payrow.payment;
		
		row.extraPayment = payrow.extraPayment;
		row.extraPaymentMo = payrow.extraPaymentMo;
		row.extraPaymentYear = payrow.extraPaymentYear;
		row.fullPayment = payrow.fullPayment;

		while( isNextPaymentSamePeriod() ) {

			if( super.hasNext() )
				payrow = super.next();
			else
				break;
			
			/* For proper calculation */
			row.interest += payrow.interest;
			row.principal += payrow.principal;
			row.payment += payrow.payment;
			row.extraPayment += payrow.extraPayment;
			row.extraPaymentMo += payrow.extraPaymentMo;
			row.extraPaymentYear += payrow.extraPaymentYear;
			row.fullPayment += payrow.fullPayment;
			
		}

		row.paymentMo = payrow.paymentMo;
		row.extraPaymentMo = payrow.extraPaymentMo; 
		row.extraPaymentYear = payrow.extraPaymentYear; 
		row.extraPrcMo = payrow.extraPrcMo;
		row.extraPrcYear = payrow.extraPrcYear;

		row.totalInterest = payrow.totalInterest;
		row.interestAnnualTotal = payrow.interestAnnualTotal;
		row.totalPrincipal = payrow.totalPrincipal;
		row.totalPayment = payrow.totalPayment;
		row.totalExtraPayment = payrow.totalExtraPayment;
		row.totalExtraPrc = payrow.totalExtraPrc;
		row.totalFullPayment = payrow.totalFullPayment;
		row.totalPrincipalPrc = payrow.totalPrincipalPrc;
		row.totalInterestPrc = payrow.totalInterestPrc;
		row.totalInterestPrc2Principle = payrow.totalInterestPrc2Principle;

		row.principalPrc = row.fullPayment > 0? ExcelFunctions.round(row.principal*100.0/row.fullPayment, 2): 0;
		row.interestPrc = row.fullPayment > 0? ExcelFunctions.round(row.interest*100.0/row.fullPayment, 2): 0;
		row.interestAnnualPrc = ExcelFunctions.round(row.interestAnnualTotal*100.0/row.balanceInAnnual, 2);
		row.extraPrc = row.fullPayment > 0? ExcelFunctions.round(row.extraPayment*100.0/row.fullPayment, 2): 0;
		
		row.balanceOut = payrow.balanceOut;
		row.balanceOutDate = payrow.balanceOutDate;
		
		row.balanceOutMonth = payrow.balanceOutMonth;
		row.balanceOutMonthYear = payrow.balanceOutMonthYear;
		row.balanceOutMonthDay = payrow.balanceOutMonthDay;
		row.balanceOutQuoter = payrow.balanceOutQuoter;
		row.balanceOutYear = payrow.balanceOutYear;
		row.nextBalanceOutYear = payrow.nextBalanceOutYear;
		row.balanceTerm = payrow.balanceTerm;
		row.nextBalanceTerm = payrow.nextBalanceTerm;

		row.fullPrincipal = row.principal + row.extraPayment; 
		row.fullPrincipalPrc = row.principalPrc + row.extraPrc; 

		row.payInMo = payrow.payInMo; 
		
		return row;
	}
	
	final private boolean isNextPaymentSamePeriod(){
			switch( at ){
			case BY_PAYMENT:
				return false;
			case BY_MONTH:
				return UtilDateTime.isSameMonth(payrow.paydayNext, payrow.payday);  
			case BY_QUOTER:
				return UtilDateTime.isSameQuoter(payrow.paydayNext, payrow.payday);  
			case BY_YEAR:
				return UtilDateTime.isSameYear(payrow.paydayNext, payrow.payday);  
			case BY_TERM:
				return payrow.balanceTerm == payrow.nextBalanceTerm;  
			case FOR_WHOLE_AMORTIZATION_PERIOD:
				return true;  
			default:
				throw new RuntimeException("Unexpected amortization type ["+at+"]");
			}
	}
	

}


