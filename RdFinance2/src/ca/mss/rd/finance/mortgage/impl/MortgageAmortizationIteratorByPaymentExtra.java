package ca.mss.rd.finance.mortgage.impl;

import ca.mss.rd.excel.ExcelFunctions;
import ca.mss.rd.util.UtilDateTime;
import ca.mss.rd.util.UtilMath;



public class MortgageAmortizationIteratorByPaymentExtra extends MortgageAmortizationIteratorByPayment {
	
	final static public String className = MortgageAmortizationIteratorByPayment.class.getName();
	final static public long serialVersionUID = className.hashCode();

	private MortgageAmortizationRow row;
	
	public MortgageAmortizationIteratorByPaymentExtra(MortgageAmortization amortization) {
		super(amortization);
	}

	/* (non-Javadoc)
	 * @see java.util.Iterator#next()
	 */
	public MortgageAmortizationRow next() {

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
				row.balanceIn = UtilMath.round(row.balanceIn-row.extraPayment, MortgageSettings.SCALE);
	
				/* revert */ 
				row.totalInterest  = ExcelFunctions.round(row.totalInterest-row.interest,2);
				row.totalPrincipal  = ExcelFunctions.round(row.totalPrincipal-row.principal,2);
				row.totalPayment  = ExcelFunctions.round(row.totalPayment-row.payment,2);
				row.paymentMo  = ExcelFunctions.round(row.paymentMo-row.payment,2);
	
				row.interest = ExcelFunctions.round(row.balanceIn*context.getAmortizationRate(amortization.paymentFrequency), MortgageSettings.SCALE);;
				row.principal = ExcelFunctions.round(context.getPayment(amortization.paymentFrequency) - row.interest, MortgageSettings.SCALE);
				row.balanceOut = UtilMath.round(row.balanceIn - row.principal, MortgageSettings.SCALE);
				
				// Goto #1
			} else {
				row.extraPayment = row.balanceIn; // pay full balance
				row.balanceIn = UtilMath.round(Math.max(0.0, row.balanceIn-row.extraPayment), MortgageSettings.SCALE);
				
				/* revert */ 
				row.totalInterest  = ExcelFunctions.round(row.totalInterest-row.interest,2);
				row.totalPrincipal  = ExcelFunctions.round(row.totalPrincipal-row.principal,2);
				row.totalPayment  = ExcelFunctions.round(row.totalPayment-row.payment,2);
				row.paymentMo  = ExcelFunctions.round(row.paymentMo-row.payment,2);
	
				row.interest = ExcelFunctions.round(row.balanceIn*context.getAmortizationRate(amortization.paymentFrequency), MortgageSettings.SCALE);;
				row.principal = ExcelFunctions.round(context.getPayment(amortization.paymentFrequency) - row.interest, MortgageSettings.SCALE);
				row.balanceOut = UtilMath.round(row.balanceIn - row.principal, MortgageSettings.SCALE);
			}
		} else if( row.ifProcessedExtrasAfter() ){
			ifProcessedExtrasAfter = true;
			if( row.balanceOut < row.extraPayment ){
				row.extraPayment = row.balanceOut; // pay full balance
			}
		} else {
			/* No extras */
			row.fullPayment = row.payment;
			row.totalFullPayment  = ExcelFunctions.round(row.totalFullPayment+row.fullPayment,2);
			return row;
		}

		/* Calculate month/year extras */
		if( row.paydayPrev == null || UtilDateTime.isSameMonth(row.payday, row.paydayPrev)){
			row.extraPaymentMo  = ExcelFunctions.round(row.extraPaymentMo+row.extraPayment,2);
		}
		if( row.paydayPrev == null || UtilDateTime.isSameYear(row.payday, row.paydayPrev)){
			row.extraPaymentYear  = ExcelFunctions.round(row.extraPaymentYear+row.extraPayment,2);
		}

		/* Calculate year % an check year % limitation */
		row.extraPrcYear = ExcelFunctions.round(row.extraPaymentYear*100.0/context.principal, 2);
		if( row.extraPrcYear > 0 && context.maxExtraYear > 0.0 && row.extraPrcYear > context.maxExtraYear ){
			double deltaPrc = row.extraPrcYear - context.maxExtraYear;
			double deltaPayment = Math.min(row.extraPayment, ExcelFunctions.round(deltaPrc*context.principal/100.0, 2));
			
			/* extra delta revert */
			row.extraPayment  = ExcelFunctions.round(row.extraPayment-deltaPayment,2);
			
			if( UtilDateTime.isSameMonth(row.payday, row.paydayPrev)){
				row.extraPaymentMo  = ExcelFunctions.round(row.extraPaymentMo-deltaPayment,2);
			}

			if( UtilDateTime.isSameYear(row.payday, row.paydayPrev)){
				row.extraPaymentYear  = ExcelFunctions.round(row.extraPaymentYear-deltaPayment,2);
			}

			if( ifProcessedExtrasBefore ){
					row.balanceIn  = ExcelFunctions.round(row.balanceIn+deltaPayment,2);
		
					/* revert */ 
					row.totalInterest  = ExcelFunctions.round(row.totalInterest-row.interest,2);
					row.totalPrincipal  = ExcelFunctions.round(row.totalPrincipal-row.principal,2);
					row.totalPayment  = ExcelFunctions.round(row.totalPayment-row.payment,2);
		
					row.interest = ExcelFunctions.round(row.balanceIn*context.getAmortizationRate(amortization.paymentFrequency), MortgageSettings.SCALE);;
					row.principal = ExcelFunctions.round(context.getPayment(amortization.paymentFrequency) - row.interest, MortgageSettings.SCALE);
					row.balanceOut = ExcelFunctions.round(row.balanceIn - row.principal, 2);
					
					// Goto #1
			}
		}
			
		/* Calculate month % an check month % limitation */
		if( row.paymentMo > 0.0 ){
			row.extraPrcMo = ExcelFunctions.round(row.extraPaymentMo*100.0/row.paymentMo, 2);
			if( row.extraPrcMo > 0 && context.maxExtraMonth > 0.0 && row.extraPrcMo > context.maxExtraMonth ){
				double deltaPrc = row.extraPrcMo - context.maxExtraMonth;
				double deltaPayment =  Math.min(row.extraPayment, ExcelFunctions.round(deltaPrc*row.paymentMo/100.0, 2));
				
				/* extra delta revert */
				row.extraPayment  = ExcelFunctions.round(row.extraPayment-deltaPayment,2);
				
				if( UtilDateTime.isSameMonth(row.payday, row.paydayPrev)){
					row.extraPaymentMo  = ExcelFunctions.round(row.extraPaymentMo-deltaPayment,2);
				}
	
				if( UtilDateTime.isSameYear(row.payday, row.paydayPrev)){
					row.extraPaymentYear  = ExcelFunctions.round(row.extraPaymentYear-deltaPayment,2);
				}
	
				if( ifProcessedExtrasBefore ){
						row.balanceIn  = ExcelFunctions.round(row.balanceIn+deltaPayment,2);
			
						/* revert */ 
						row.totalInterest  = ExcelFunctions.round(row.totalInterest-row.interest,2);
						row.totalPrincipal  = ExcelFunctions.round(row.totalPrincipal-row.principal,2);
						row.totalPayment  = ExcelFunctions.round(row.totalPayment-row.payment,2);
			
						row.interest = ExcelFunctions.round(row.balanceIn*context.getAmortizationRate(amortization.paymentFrequency), MortgageSettings.SCALE);;
						row.principal = ExcelFunctions.round(context.getPayment(amortization.paymentFrequency) - row.interest, MortgageSettings.SCALE);
						row.balanceOut = ExcelFunctions.round(row.balanceIn - row.principal, 2);
						
						// Goto #1
				}
			}
		} else {
			row.extraPrcMo = 100;
		}
		
		/* Goto here */
		if( ifProcessedExtrasBefore ){
			// label #1
			if( row.balanceOut <= MortgageAmortization.MINIMUM_PAYMENT ){
				row.principal = ExcelFunctions.round(row.principal+row.balanceOut,2);
				row.balanceOut = 0.0;
			}
			
			row.payment =  ExcelFunctions.round(row.interest + row.principal, 2);
			
			/* apply */ 
			row.totalInterest = ExcelFunctions.round(row.totalInterest+row.interest,2);
			row.totalPrincipal  = ExcelFunctions.round(row.totalPrincipal+row.principal,2);
			row.totalPayment  = ExcelFunctions.round(row.totalPayment+row.payment,2);
		} else if( ifProcessedExtrasAfter ){
			if( row.balanceOut >= row.extraPayment ){
				// label #2
				row.balanceOut =  ExcelFunctions.round(row.balanceOut-row.extraPayment, 2);
			}
		}	
		
		/* All % limits are passed */
		row.extraPrcMo = row.paymentMo>0? ExcelFunctions.round(row.extraPaymentMo*100.0/row.paymentMo, 2): 100;
		row.extraPrcYear = ExcelFunctions.round(row.extraPaymentYear*100.0/context.principal, 2);
		
		row.totalExtraPayment  = ExcelFunctions.round(row.totalExtraPayment+row.extraPayment,2);
		row.fullPayment = ExcelFunctions.round(row.payment + row.extraPayment, 2);
		row.totalFullPayment  = ExcelFunctions.round(row.totalFullPayment+row.fullPayment,2);

		row.principalPrc = ExcelFunctions.round(row.principal*100.0/row.fullPayment, 2);
		row.interestPrc = ExcelFunctions.round(row.interest*100.0/row.fullPayment, 2);
		row.extraPrc = ExcelFunctions.round(row.extraPayment*100.0/row.fullPayment, 2);
		
		row.totalPrincipalPrc = ExcelFunctions.round(row.totalPrincipal*100.0/row.totalFullPayment, 2);
		row.totalInterestPrc = ExcelFunctions.round(row.totalInterest*100.0/row.totalFullPayment, 2);
		row.totalExtraPrc = ExcelFunctions.round(row.totalExtraPayment*100.0/row.totalFullPayment, 2);

		row.fullPrincipal =  ExcelFunctions.round(row.principal + row.extraPayment, 2); 
		row.fullPrincipalPrc =  ExcelFunctions.round(row.principalPrc + row.extraPrc, 2); 
		
		/* update out balance */
		balance = row.balanceOut;

		return row;
	}
	
}


