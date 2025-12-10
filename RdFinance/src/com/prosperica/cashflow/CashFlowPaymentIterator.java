package com.prosperica.cashflow;

import java.util.Date;
import java.util.Iterator;

import ca.mss.rd.util.UtilDateTime;

import com.prosperica.common.AmortizationTable;
import com.prosperica.common.AmortizationTableRow;
import com.prosperica.common.CommonContext;
import com.prosperica.mc.AmortizationType;


public class CashFlowPaymentIterator implements Iterator<CashFlowRow> {
	final static public String className = CashFlowPaymentIterator.class.getName();
	final static public long serialVersionUID = className.hashCode();

	final public AmortizationTable[] debts;
	
	private Iterator<AmortizationTableRow>[] dbIter;
	private CashFlowRow[] dbCach;
	
	/**
	 * @param debts
	 */
	public CashFlowPaymentIterator(CommonContext[] context, Date from, int year, int term) {
		super();

		this.debts = new AmortizationTable[context.length];
		this.dbCach = new CashFlowRow[context.length];
		this.dbIter = new Iterator[context.length];

		for(int i=0; i<context.length; i++){
			debts[i] = context[i].computate();
			dbIter[i] = debts[i].getIterator(AmortizationType.BY_PAYMENT, from, year, term);
		}
	}

	/* (non-Javadoc)
	 * @see java.util.Iterator#remove()
	 */
	public void remove() {
	}

	/* (non-Javadoc)
	 * @see java.util.Iterator#hasNext()
	 */
	public boolean hasNext() {
		for(int i=0; i<debts.length; i++){
			if( dbCach[i] != null )
				return true;
			else if( dbIter[i].hasNext() )
				return true;
		}
		return false;
	}
	
	/* (non-Javadoc)
	 * @see java.util.Iterator#next()
	 */
	public CashFlowRow next() {
		int mindate = -1;
		for(int i=0; i<debts.length; i++){
			if( dbCach[i] == null && dbIter[i].hasNext() ){
				dbCach[i] = dbIter[i].next();
			}
			if( dbCach[i] != null ){
				if( mindate == -1 ){
					mindate = i;
				} else if( UtilDateTime.before(dbCach[mindate].getPayDay(), dbCach[i].getPayDay()) ){
					mindate = i;
				}
			}
		}
		if( mindate > -1 ){
			CashFlowRow minRow = dbCach[mindate];
			dbCach[mindate] = null;
			return minRow;
		}
		return null;
	}

	
}


