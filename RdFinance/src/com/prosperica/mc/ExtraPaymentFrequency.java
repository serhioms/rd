package com.prosperica.mc;

import ca.mss.rd.util.UtilMisc;

public enum ExtraPaymentFrequency {

	ANNUAL("Annual", "An", 12),
	SEMI_ANNUAL("Semi-annual", "SA", 6),
	QUOTERLY("Quoterly", "Qo", 3),
	MONTHLY("Monthly", "Mo", 1);

	final public String title;
	final public String ident;
	final public int paymentsPerMonth;
	
	private ExtraPaymentFrequency(String title, String ident, int paymentsPerMonth){
		this.title = title;
		this.ident = ident;
		this.paymentsPerMonth = paymentsPerMonth;
	}

	public static ExtraPaymentFrequency getByIdent(String ident) {
		if( !UtilMisc.isEmpty(ident) )
			for(ExtraPaymentFrequency v : values())
				if( v.ident.equalsIgnoreCase(ident) )
					return v;
		return null;
	}

	public String getTitle() {
		return title;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {
		return title;
	}
}


