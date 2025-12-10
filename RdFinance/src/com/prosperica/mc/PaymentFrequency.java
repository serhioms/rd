package com.prosperica.mc;

public enum PaymentFrequency {

	MONTHLY("Monthly", "Mo", 12, 12, 1, 0, 1),
	SEMI_MONTHLY("Semi-monthly", "sMo", 24, 24, 2, 15, 0),
	BI_WEEKLY("Bi-weekly", "BiW", 26, 26, 2, 14, 0),
	WEEKLY("Weekly", "Wi", 52, 52, 4, 7, 0),
	ACCELERATED_BI_WEEKLY("Accelerated-bi-weekly", "AccBiW", 26, 12, 2, 14, 0),
	ACCELERATED_WEEKLY("Accelerated-weekly", "AccWi", 52, 12, 4, 7, 0);
	
	
	final public int paymentsPerYear;
	final public int paymentsPerYearEffective;
	final public int paymentsPerMonth;
	final public int periodDays;
	final public int periodMonth;
	final public String title;
	final public String shortTitle;
	
	private PaymentFrequency(String title, String shortTitle, int paymentsPerYear, int paymentsPerYearEffective, int paymentsPerMonth, int periodDays, int periodMonth){
		this.title = title;
		this.shortTitle = shortTitle;
		this.paymentsPerYear = paymentsPerYear;
		this.paymentsPerYearEffective = paymentsPerYearEffective;
		this.paymentsPerMonth = paymentsPerMonth;
		this.periodDays = periodDays;
		this.periodMonth = periodMonth;
	}

	public static PaymentFrequency getFrequencyByName(String name) {
		final PaymentFrequency[] frc = values();
		for(PaymentFrequency fr : frc) {
			if( fr.getTitle().equalsIgnoreCase(name) ) {
				return fr;
			}
		}
		
		return null;
	}

	public String getTitle() {
		return title;
	}
	
	public int getPaymentsPerYear() {
		return paymentsPerYear;
	}

	/* (non-Javadoc)
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {
		return title;
	}
}


