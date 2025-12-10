package com.prosperica.test;

import java.util.Date;

import ca.mss.rd.util.UtilDateTime;

import com.prosperica.common.CommonContext;
import com.prosperica.mc.printers.TextPrinterHelper;

public class CashFlow {

	final static public String className = CashFlow.class.getName();
	final static public long serialVersionUID = className.hashCode();

	static public Date FROM_DATE = UtilDateTime.parse("2/1/2013", "MM/dd/yyyy");

	static public int FILTER_TERM = 1;
	static public int FILTER_YEAR = 0;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		

		CommonContext[] context = new CommonContext[]{
				
				 Mortgage.MORTGAGES[Mortgage.IDX_HOME]
				,
				Loan.LOANS[Loan.IDX_TOYOTA]
				,
				LineOfCredit.CREDIT_LINES[LineOfCredit.IDX_CIBC_ED]
				,
				LineOfCredit.CREDIT_LINES[LineOfCredit.IDX_RBC_PAPA]
				,
				LineOfCredit.CREDIT_LINES[LineOfCredit.LC_TD_PAPA]
				,
				CreditCard.CREDIT_CARDS[CreditCard.IDX_AMEX_COSTCO]
				,
				CreditCard.CREDIT_CARDS[CreditCard.IDX_HOMEDEPO]
						,
				CreditCard.CREDIT_CARDS[CreditCard.IDX_HOMEDEPO2]
				,
				CreditCard.CREDIT_CARDS[CreditCard.IDX_LAWS]
				,
				CreditCard.CREDIT_CARDS[CreditCard.IDX_VISA_RBC_PAPA]
				,
				CreditCard.CREDIT_CARDS[CreditCard.IDX_VISA_TD_MAMA]
						
		}; 
		
		TextPrinterHelper.printCashFlow(System.out, context, FROM_DATE, FILTER_YEAR, FILTER_TERM);
		
	}

}


