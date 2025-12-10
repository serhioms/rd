package ca.mss.rd.finance.impl;

import ca.mss.rd.finance.CashFlowEvents.CashFlowEvent;
import ca.mss.rd.finance.Money;

public class MoneyFixedAmount implements Money {

	final static public String className = MoneyFixedAmount.class.getName();
	
	final public double amount;
	final public String currency;

	public double amount(CashFlowEvent event) {
		return amount;
	}

	public String currency() {
		return currency;
	}

	public MoneyFixedAmount(double amount, String currency) {
		this.amount = amount;
		this.currency = currency;
	}

	public MoneyFixedAmount(double amount) {
		this(amount, DEFAULT_CURRENCY);
	}

}


