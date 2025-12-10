package ca.mss.rd.finance;

import ca.mss.rd.finance.CashFlowEvents.CashFlowEvent;


public interface Money {

	final static public String DEFAULT_CURRENCY = "$";

	public double amount(CashFlowEvent event);
	public String currency();
	
	
}


