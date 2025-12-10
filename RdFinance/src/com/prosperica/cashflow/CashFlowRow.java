package com.prosperica.cashflow;

import java.util.Date;


public interface CashFlowRow {

	public Date getPayDay();
	public double getBalance();
	public double getPayment();
	public double getInterest();
	public double getInterestTotal();
	public double getInterestPrc();
	public String getDescription();
	
}


