package ca.mss.rd.finance;

public enum EventFrequency {

	ANNUALLY("Annually", "An", 0, 0, 1),
	SEMI_ANNUALLY("Semi-annually", "SemiAn", 0, 6, 0),
	MONTHLY("Monthly", "Mo", 0, 1, 0),
	SEMI_MONTHLY("Semi-monthly", "SemiMo", 15, 0, 0),
	BI_WEEKLY("Bi-weekly", "BiW", 14, 0, 0),
	WEEKLY("Weekly", "Wi", 7, 0, 0);
	
	
	final public String title;
	final public String shortTitle;
	final public int periodDays;
	final public int periodMonth;
	final public int periodYears;
	
	private EventFrequency(String title, String shortTitle, int periodDays, int periodMonth, int periodYears){
		this.title = title;
		this.shortTitle = shortTitle;
		this.periodDays = periodDays;
		this.periodMonth = periodMonth;
		this.periodYears = periodYears;
	}

	@Override
	public String toString() {
		return title;
	}
}


