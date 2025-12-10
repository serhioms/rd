package ca.mss.rd.finance.mortgage.impl;

import ca.mss.rd.excel.ExcelFunctions;
import ca.mss.rd.finance.mortgage.AmortizationType;
import ca.mss.rd.util.UtilDateTime;



public class MortgageAmortizationIterator extends MortgageAmortizationIteratorFiltered {
	
	final static public String className = MortgageAmortizationIterator.class.getName();
	final static public long serialVersionUID = className.hashCode();

	final private AmortizationType at;

	private int order;
	private MortgageAmortizationRow row;
	private MortgageAmortizationRow payrow;

	public MortgageAmortizationIterator(MortgageAmortization amortization, AmortizationType at) {
		this(amortization, at, 0, 0);
	}

	public MortgageAmortizationIterator(MortgageAmortization amortization, AmortizationType at, int year, int term) {
		super(amortization, year, term);
		this.at = at;
		this.order = 1;
		this.row = new MortgageAmortizationRow(amortization);
	}

	/* (non-Javadoc)
	 * @see java.util.Iterator#next()
	 */
	public MortgageAmortizationRow next() {

		payrow = super.next();
		
		row.nop = order++;
		
		row.paydayPrev = payrow.paydayPrev;
		row.payday = payrow.payday;
		row.paydayNext = payrow.paydayNext;

		row.balanceIn = payrow.balanceIn;
		row.balanceInDate = payrow.balanceInDate;
		
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
			row.interest = ExcelFunctions.round(row.interest + payrow.interest, 2);
			row.principal =  ExcelFunctions.round(row.principal + payrow.principal, 2);
			row.payment =  ExcelFunctions.round(row.payment + payrow.payment, 2);
			row.extraPayment =  ExcelFunctions.round(row.extraPayment + payrow.extraPayment, 2);
			row.fullPayment =  ExcelFunctions.round(row.fullPayment + payrow.fullPayment, 2);
			
		}

		row.paymentMo = payrow.paymentMo;
		row.extraPaymentMo = payrow.extraPaymentMo; 
		row.extraPaymentYear = payrow.extraPaymentYear; 
		row.extraPrcMo = payrow.extraPrcMo;
		row.extraPrcYear = payrow.extraPrcYear;

		row.principalPrc = ExcelFunctions.round(row.principal*100.0/row.fullPayment, 2);
		row.interestPrc = ExcelFunctions.round(row.interest*100.0/row.fullPayment, 2);
		row.extraPrc = ExcelFunctions.round(row.extraPayment*100.0/row.fullPayment, 2);
		
		row.totalInterest = payrow.totalInterest;
		row.totalPrincipal = payrow.totalPrincipal;
		row.totalPayment = payrow.totalPayment;
		row.totalExtraPayment = payrow.totalExtraPayment;
		row.totalExtraPrc = payrow.totalExtraPrc;
		row.totalFullPayment = payrow.totalFullPayment;
		row.totalPrincipalPrc = payrow.totalPrincipalPrc;
		row.totalInterestPrc = payrow.totalInterestPrc;

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

		row.fullPrincipal =  ExcelFunctions.round(row.principal + row.extraPayment, 2); 
		row.fullPrincipalPrc =  ExcelFunctions.round(row.principalPrc + row.extraPrc, 2); 

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
			case BY_AMORTIZATION:
				return true;  
			default:
				throw new RuntimeException("Unexpected amortization type ["+at+"]");
			}
	}
	

}


