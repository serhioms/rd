package com.prosperica.common;

import ca.mss.rd.util.UtilDateTime;
import ca.mss.rd.util.UtilMath;

import com.prosperica.excel.ExcelFunctions;


public class AmortizationTableIteratorByPaymentExtra extends AmortizationTableIteratorByPayment {
	
	final static public String className = AmortizationTableIteratorByPayment.class.getName();
	final static public long serialVersionUID = className.hashCode();

	private AmortizationTableRow row;
	
	public AmortizationTableIteratorByPaymentExtra(CommonContext context) {
		super(context);
	}

	/* (non-Javadoc)
	 * @see java.util.Iterator#next()
	 */
	public AmortizationTableRow next() {

		row = super.next();

		if( !UtilDateTime.isSameMonth(row.payday, row.paydayPrev)){
			row.extraPaymentMo = 0;
			row.extraPrcMo = 0;
		}
		if( !UtilDateTime.isSameYear(row.payday, row.paydayPrev)){
			row.extraPaymentYear = 0;
			row.extraPrcYear = 0;
		}

		boolean ifProcessedExtrasBefore = false;
		boolean ifProcessedExtrasAfter = false;
		
		/* check for extra payments before and after payment */
		if( row.ifProcessedExtrasBefore() ){
			ifProcessedExtrasBefore = true;
			if( row.balanceOut >= row.extraPayment ){
				row.balanceIn = UtilMath.round(row.balanceIn-row.extraPayment, CommonSettings.SCALE);
	
				/* revert */ 
				row.totalInterest -= row.interest;
				row.totalPrincipal -= row.principal;
				row.totalPayment -= row.payment;
	
				row.interest = ExcelFunctions.round(row.balanceIn*row.context.getAmortizationRate(), CommonSettings.SCALE);;
				row.principal = ExcelFunctions.round(row.context.getPayment() - row.interest, CommonSettings.SCALE);
				row.balanceOut = UtilMath.round(row.balanceIn - row.principal, CommonSettings.SCALE);
				
				// Goto #1
			} else {
				row.extraPayment = 0.0; // no balance for paying in to
				row.fullPayment = row.payment;
				row.totalFullPayment += row.fullPayment;
				return row;
			}
		} else if( row.ifProcessedExtrasAfter() ){
			ifProcessedExtrasAfter = true;
			if( row.balanceOut >= row.extraPayment ){
				// Goto #2
			} else {
				row.extraPayment = 0.0; // no balance for paying in to
				row.fullPayment = row.payment;
				row.totalFullPayment += row.fullPayment;
				return row;
			}
		} else {
			/* No extras */
			row.fullPayment = row.payment;
			row.totalFullPayment += row.fullPayment;
			return row;
		}

		/* Calculate month/year extras */
		if( row.paydayPrev == null )
			row.extraPaymentMo = row.extraPayment;
		else if( UtilDateTime.isSameMonth(row.payday, row.paydayPrev))
			row.extraPaymentMo += row.extraPayment;
		else
			row.extraPaymentMo = row.extraPayment;

		if( row.paydayPrev == null )
			row.extraPaymentYear += row.extraPayment;
		else if( UtilDateTime.isSameYear(row.payday, row.paydayPrev))
			row.extraPaymentYear += row.extraPayment;
		else
			row.extraPaymentYear = row.extraPayment;

		/* Calculate year % an check year % limitation */
		row.extraPrcYear = ExcelFunctions.round(row.extraPaymentYear*100.0/row.context.principal, 2);
		if( row.extraPrcYear > 0 && row.context.maxExtraYear > 0.0 && row.extraPrcYear > row.context.maxExtraYear ){
			double deltaPrc = row.extraPrcYear - row.context.maxExtraYear;
			double deltaPayment = Math.min(row.extraPayment, ExcelFunctions.round(deltaPrc*row.context.principal/100.0, 2));
			
			/* extra delta revert */
			row.extraPayment -= deltaPayment;
			
			if( UtilDateTime.isSameMonth(row.payday, row.paydayPrev)){
				row.extraPaymentMo -= deltaPayment;
			}

			if( UtilDateTime.isSameYear(row.payday, row.paydayPrev)){
				row.extraPaymentYear -= deltaPayment;
			}

			if( ifProcessedExtrasBefore ){
					row.balanceIn += deltaPayment;
		
					/* revert */ 
					row.totalInterest -= row.interest;
					row.totalPrincipal -= row.principal;
					row.totalPayment -= row.payment;
		
					row.interest = ExcelFunctions.round(row.balanceIn*row.context.getAmortizationRate(), CommonSettings.SCALE);;
					row.principal = ExcelFunctions.round(row.context.getPayment() - row.interest, CommonSettings.SCALE);
					row.balanceOut = UtilMath.round(row.balanceIn - row.principal, CommonSettings.SCALE);
					
					// Goto #1
			}
		}
			
		/* Calculate month % an check month % limitation */
		row.extraPrcMo = ExcelFunctions.round(row.extraPaymentMo*100.0/row.paymentMo, 2);
		if( row.extraPrcMo > 0 && row.context.maxExtraMonth > 0.0 && row.extraPrcMo > row.context.maxExtraMonth ){
			double deltaPrc = row.extraPrcMo - row.context.maxExtraMonth;
			double deltaPayment =  Math.min(row.extraPayment, ExcelFunctions.round(deltaPrc*row.paymentMo/100.0, 2));
			
			/* extra delta revert */
			row.extraPayment -= deltaPayment;
			
			if( UtilDateTime.isSameMonth(row.payday, row.paydayPrev)){
				row.extraPaymentMo -= deltaPayment;
			}

			if( UtilDateTime.isSameYear(row.payday, row.paydayPrev)){
				row.extraPaymentYear -= deltaPayment;
			}

			if( ifProcessedExtrasBefore ){
					row.balanceIn += deltaPayment;
		
					/* revert */ 
					row.totalInterest -= row.interest;
					row.totalPrincipal -= row.principal;
					row.totalPayment -= row.payment;
		
					row.interest = ExcelFunctions.round(row.balanceIn*row.context.getAmortizationRate(), CommonSettings.SCALE);;
					row.principal = ExcelFunctions.round(row.context.getPayment() - row.interest, CommonSettings.SCALE);
					row.balanceOut = UtilMath.round(row.balanceIn - row.principal, CommonSettings.SCALE);
					
					// Goto #1
			}
		}
		
		/* Goto here */
		if( ifProcessedExtrasBefore ){
			// label #1
			if( row.balanceOut <= AmortizationTable.MINIMUM_PAYMENT ){
				row.principal += row.balanceOut;
				row.balanceOut = 0.0;
			}
			
			row.payment = UtilMath.round(row.interest + row.principal, CommonSettings.SCALE);
			
			/* apply */ 
			row.totalInterest = ExcelFunctions.round(row.totalInterest+row.interest, CommonSettings.SCALE);
			row.totalPrincipal = ExcelFunctions.round(row.totalPrincipal+row.principal, CommonSettings.SCALE);
			row.totalPayment = ExcelFunctions.round(row.totalPayment+row.payment, CommonSettings.SCALE);
		} else if( ifProcessedExtrasAfter ){
			if( row.balanceOut >= row.extraPayment ){
				// label #2
				row.balanceOut = UtilMath.round(row.balanceOut-row.extraPayment, CommonSettings.SCALE);
			}
		}	
		
		/* All % limits are passed */
		row.extraPrcMo = ExcelFunctions.round(row.extraPaymentMo*100.0/row.paymentMo, 2);
		row.extraPrcYear = ExcelFunctions.round(row.extraPaymentYear*100.0/row.context.principal, 2);
		
		row.totalExtraPayment += row.extraPayment;
		row.fullPayment = UtilMath.round(row.payment + row.extraPayment, CommonSettings.SCALE);
		row.totalFullPayment += row.fullPayment;

		row.principalPrc = ExcelFunctions.round(row.principal*100.0/row.fullPayment, 2);
		row.interestPrc = ExcelFunctions.round(row.interest*100.0/row.fullPayment, 2);
		row.extraPrc = ExcelFunctions.round(row.extraPayment*100.0/row.fullPayment, 2);
		
		row.totalPrincipalPrc = ExcelFunctions.round(row.totalPrincipal*100.0/row.totalFullPayment, 2);
		row.totalInterestPrc = ExcelFunctions.round(row.totalInterest*100.0/row.totalFullPayment, 2);
		row.totalInterestPrc2Principle = ExcelFunctions.round(row.totalInterest*100.0/row.context.principal, 2);
		row.totalExtraPrc = ExcelFunctions.round(row.totalExtraPayment*100.0/row.totalFullPayment, 2);

		row.fullPrincipal = row.principal + row.extraPayment; 
		row.fullPrincipalPrc = row.principalPrc + row.extraPrc; 

		/* update out balance */
		balance = row.balanceOut;

		return row;
	}
	
}


