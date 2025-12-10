package com.prosperica.common;




public class AmortizationTableIteratorFiltered extends AmortizationTableIteratorByPaymentExtra {
	
	final static public String className = AmortizationTableIteratorFiltered.class.getName();
	final static public long serialVersionUID = className.hashCode();

	final private int year;
	final private int term;

	private AmortizationTableRow row;

	public AmortizationTableIteratorFiltered(CommonContext context, int year, int term) {
		super(context);
		this.year = year;
		this.term = term;
	}

	/* (non-Javadoc)
	 * @see com.prosperica.mc.impl.MortgageAmortizationIteratorByPayment#hasNext()
	 */
	@Override
	public boolean hasNext() {
		if( super.hasNext() ){
			if( row == null )
				return true;
			else if( year > 0 && row.nextBalanceOutYear > year )
				return false;
			else if( term > 0 && row.nextBalanceTerm > term )
				return false;
			else
				return true;
		} else
			return false;
	}

	/* (non-Javadoc)
	 * @see java.util.Iterator#next()
	 */
	@Override
	public AmortizationTableRow next() {
		while( true ){
			row = super.next();
			if( year > 0 && row.balanceOutYear < year )
				continue;
			else if( term > 0  && row.balanceTerm < term )
				continue;
			else
				return row;
		}
	}

}


