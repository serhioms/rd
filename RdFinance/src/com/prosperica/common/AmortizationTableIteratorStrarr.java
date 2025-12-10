package com.prosperica.common;

import java.util.Iterator;

import ca.mss.rd.util.UtilFormat;

import com.prosperica.mc.AmortizationType;


public class AmortizationTableIteratorStrarr implements Iterator<String[]> {
	
	final static public String className = AmortizationTableIteratorStrarr.class.getName();
	final static public long serialVersionUID = className.hashCode();

	final private Iterator<AmortizationTableRow> iterator;
	
	private String[] row = new String[AmortizationTable.defTitle.length];

	public AmortizationTableIteratorStrarr(AmortizationTable atable, AmortizationType atype) {
		this(atable, atype, 0, 0);
	}

	public AmortizationTableIteratorStrarr(AmortizationTable atable, AmortizationType atype, int year, int term) {
		this.iterator = atable.getIterator(atype, year, term);
	}

	/* (non-Javadoc)
	 * @see java.util.Iterator#hasNext()
	 */
	public boolean hasNext() {
		return iterator.hasNext();
	}

	/* (non-Javadoc)
	 * @see java.util.Iterator#next()
	 */
	final public String[] next() {
	
		AmortizationTableRow obj = iterator.next();

		row[AmortizationTable.C_NOP] = Integer.toString(obj.nop);
		row[AmortizationTable.C_BALANCE_IN_DATE] = UtilFormat.format(obj.balanceInDate);
		row[AmortizationTable.C_BALANCE_IN] = UtilFormat.format(obj.balanceIn);
		row[AmortizationTable.C_BALANCE_IN_ANNUAL] = UtilFormat.format(obj.balanceInAnnual);
		row[AmortizationTable.C_INTEREST] = UtilFormat.format(obj.interest);
		row[AmortizationTable.C_INTEREST_PRC] = UtilFormat.format(obj.interestPrc);
		row[AmortizationTable.C_INTEREST_ANNUAL_TOTAL] = UtilFormat.format(obj.interestAnnualTotal);
		row[AmortizationTable.C_INTEREST_ANNUAL_PRC] = UtilFormat.format(obj.interestAnnualPrc);
		row[AmortizationTable.C_PRINCIPAL] = UtilFormat.format(obj.principal);
		row[AmortizationTable.C_PRINCIPAL_PRC] = UtilFormat.format(obj.principalPrc);
		row[AmortizationTable.C_PAYMENT_DATE] = UtilFormat.format(obj.payday);
		
		row[AmortizationTable.C_PAYMENT] = UtilFormat.format(obj.payment);
		row[AmortizationTable.C_PAYMENT_MO] = UtilFormat.format(obj.paymentMo);
		
		row[AmortizationTable.C_PAYMENT_EXTRA] = UtilFormat.format(obj.extraPayment);
		row[AmortizationTable.C_PAYMENT_EXTRA_MO] = UtilFormat.format(obj.extraPaymentMo);
		row[AmortizationTable.C_PAYMENT_EXTRA_YEAR] = UtilFormat.format(obj.extraPaymentYear);
		row[AmortizationTable.C_PAYMENT_EXTRA_PRC] = UtilFormat.format(obj.extraPrc);
		row[AmortizationTable.C_PAYMENT_EXTRA_PRC_MO] = UtilFormat.format(obj.extraPrcMo);
		row[AmortizationTable.C_PAYMENT_EXTRA_PRC_YEAR] = UtilFormat.format(obj.extraPrcYear);
		
		row[AmortizationTable.C_FULL_PRINCIPAL] = UtilFormat.format(obj.fullPrincipal);
		row[AmortizationTable.C_FULL_PRINCIPAL_PRC] = UtilFormat.format(obj.fullPrincipalPrc);
		row[AmortizationTable.C_PAYMENT_FULL] = UtilFormat.format(obj.fullPayment);

		row[AmortizationTable.C_TOTAL_EXTRA_PAYMENT] = UtilFormat.format(obj.totalExtraPayment);
		row[AmortizationTable.C_TOTAL_FULL_PAYMENT] = UtilFormat.format(obj.totalFullPayment);
		row[AmortizationTable.C_TOTAL_EXTRA_PRC] = UtilFormat.format(obj.totalExtraPrc);
		row[AmortizationTable.C_TOTAL_PAYMENT] = UtilFormat.format(obj.totalPayment);
		row[AmortizationTable.C_TOTAL_INTEREST] = UtilFormat.format(obj.totalInterest);
		row[AmortizationTable.C_TOTAL_INTEREST_PRC] = UtilFormat.format(obj.totalInterestPrc);
		row[AmortizationTable.C_TOTAL_INTEREST_2PRINCIPAL_PRC] = UtilFormat.format(obj.totalInterestPrc2Principle);
		row[AmortizationTable.C_TOTAL_PRINCIPAL] = UtilFormat.format(obj.totalPrincipal);
		row[AmortizationTable.C_TOTAL_PRINCIPAL_PRC] = UtilFormat.format(obj.totalPrincipalPrc);

		row[AmortizationTable.C_BALANCE_OUT] = UtilFormat.format(obj.balanceOut);
		row[AmortizationTable.C_DATE_OUT] = UtilFormat.format(obj.balanceOutDate);
		row[AmortizationTable.C_DATE_OUT_YEAR] = UtilFormat.format(obj.balanceOutYear);
		row[AmortizationTable.C_DATE_OUT_MONTH] = UtilFormat.format(obj.balanceOutMonth);
		row[AmortizationTable.C_DATE_OUT_MONTH_YEAR] = UtilFormat.format(obj.balanceOutMonthYear);
		row[AmortizationTable.C_DATE_OUT_MONTH_DAY] = UtilFormat.format(obj.balanceOutMonthDay);
		row[AmortizationTable.C_DATE_OUT_QUOTER] = UtilFormat.format(obj.balanceOutQuoter);
		row[AmortizationTable.C_TERM] = UtilFormat.format(obj.balanceTerm);

		row[AmortizationTable.C_PAY_IN_MO] = UtilFormat.format(obj.payInMo);
		if( obj.paydayPrev != null )
			row[AmortizationTable.C_PAYMENT_DATE_PREV] = UtilFormat.format(obj.paydayPrev);
		row[AmortizationTable.C_PAYMENT_DATE_NEXT] = UtilFormat.format(obj.paydayNext);
		
		return row;
	}

	/* (non-Javadoc)
	 * @see java.util.Iterator#remove()
	 */
	final public void remove() {
		/* nothing */
	}

}


