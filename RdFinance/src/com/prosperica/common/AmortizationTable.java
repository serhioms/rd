package com.prosperica.common;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import ca.mss.rd.util.UtilDateTime;
import ca.mss.rd.util.UtilMisc;

import com.prosperica.cashflow.CashFlowRow;
import com.prosperica.mc.AmortizationType;
import com.prosperica.mc.util.UtilMortgageCalendar;


public class AmortizationTable {
	
	final static public String className = AmortizationTable.class.getName();
	final static public long serialVersionUID = className.hashCode();

	final static public int DEFAULT_COLUMN_WIDTH = 11;

	final static public double MINIMUM_PAYMENT = 10.0;

	final static public int C_NOP = 						0;
	final static public int C_BALANCE_IN_DATE = 			1;
	final static public int C_BALANCE_IN = 					2;
	final static public int C_BALANCE_IN_ANNUAL = 			3;
	final static public int C_INTEREST = 					4;
	final static public int C_INTEREST_PRC = 				5;
	final static public int C_INTEREST_ANNUAL_TOTAL = 		6;
	final static public int C_INTEREST_ANNUAL_PRC =			7;
	final static public int C_PRINCIPAL = 					8;
	final static public int C_PRINCIPAL_PRC = 				9;
	final static public int C_PAYMENT_DATE = 				10;
	final static public int C_PAYMENT = 					11;
	final static public int C_PAYMENT_MO = 					12;
	
	final static public int C_PAYMENT_EXTRA = 				13;
	final static public int C_PAYMENT_EXTRA_MO = 			14;
	final static public int C_PAYMENT_EXTRA_YEAR = 			15;
	
	final static public int C_PAYMENT_EXTRA_PRC = 			16;
	final static public int C_PAYMENT_EXTRA_PRC_MO = 		17;
	final static public int C_PAYMENT_EXTRA_PRC_YEAR = 		18;
	
	final static public int C_FULL_PRINCIPAL = 				19;
	final static public int C_FULL_PRINCIPAL_PRC = 			20;
	final static public int C_PAYMENT_FULL = 				21;
	
	final static public int C_TOTAL_FULL_PAYMENT = 			22;
	final static public int C_TOTAL_EXTRA_PAYMENT = 		23;
	final static public int C_TOTAL_EXTRA_PRC = 			24;
	final static public int C_TOTAL_PAYMENT = 				25;
	final static public int C_TOTAL_INTEREST = 				26;
	final static public int C_TOTAL_INTEREST_PRC = 			27;
	final static public int C_TOTAL_INTEREST_2PRINCIPAL_PRC = 28;
	
	final static public int C_TOTAL_PRINCIPAL = 			29;
	final static public int C_TOTAL_PRINCIPAL_PRC = 		30;
	
	final static public int C_BALANCE_OUT = 				31;
	final static public int C_DATE_OUT = 					32;
	final static public int C_DATE_OUT_YEAR = 				33;
	final static public int C_DATE_OUT_MONTH = 				34;
	final static public int C_DATE_OUT_MONTH_YEAR = 		35;
	final static public int C_DATE_OUT_MONTH_DAY = 			36;
	final static public int C_DATE_OUT_QUOTER = 			37;
	final static public int C_TERM = 						38;

	final static public int C_PAY_IN_MO = 					39;
	final static public int C_PAYMENT_DATE_PREV =			40;
	final static public int C_PAYMENT_DATE_NEXT =			41;

	final static public String[] defTitle = new String[]{
		"NOP",
		"InDay",
		"InBalance,$",
		"InBalanceAnnual,$",
		"Interest",
		"Interest,%",
		"AnnualInterest,$",
		"AnnualInterest,%",
		"Principal",
		"Principal,%",
		"Payday",
		"Payment",
		"PaymentMo",
		
		"ExtraPayment",
		"ExtraMo",
		"ExtraYear",
		
		"Extra,%",
		"ExtraMo,%",
		"ExtraYe,%",
		
		"FullPrincipal,$",
		"FullPrincipal,%",
		"FullPayment,$",
		
		"TotalFullPayment,$",
		"TotalExtraPayment,$",
		"TotalExtraPayment,%",
		"TotalPayment,$",
		"TotalInterest,$",
		"TotalInterest,%",
		"TotalIntr2Principal,%",
		
		"TotalPrincipal,$",
		"TotalPrincipal,%",
		
		"OutBalance,$",
		"OutDate",
		"OutYear",
		"OutMonth",
		"OutMonthYear",
		"OutMonthDay",
		"OutQuoter",
		"Term",
		"PaymentNumber",
		"Prev",
		"Next"
		};

	final static public Map<String,String> title = UtilMisc.toMap(
			"NOP", "no",
			"InDay", "Start,D",
			"InBalance,$", "BIn,$",
			"InBalanceAnnual,$", "BAIn,$",
			"Interest", "R,$",
			"Interest,%", "R,%",
			"AnnualInterest,$", "RA,$",
			"AnnualInterest,%", "RA,%",
			"Principal", "P,$",
			"Principal,%", "P,%",
			"Payday", "Pay,D",
			"Payment", "PMT,$",
			"PaymentMo", "PMT,Mo",

			"ExtraPayment", "ExPMT,$",
			"ExtraMo", "ExPMT,Mo",
			"ExtraYear", "ExPMT,Y",
			
			"Extra,%", "ExPMT,%",
			"ExtraMo,%", "ExPMT/Mo,%",
			"ExtraYe,%", "ExPMT/Y,%",
			
			"FullPrincipal,$", "FP,$",
			"FullPrincipal,%", "FP,%",
			"FullPayment,$", "FPMT,$",
			
			"TotalFullPayment,$", "TFPMT,$",
			"TotalExtraPayment,$", "TExPMT,$",
			"TotalExtraPayment,%", "TExPMT,%",
			"TotalPayment,$", "TPMT,$",
			"TotalInterest,$", "TR,$",
			"TotalInterest,%", "TR,%",
			"TotalIntr2Principal,%", "TR,%",
			"TotalPrincipal,$", "TP,$",
			"TotalPrincipal,%", "TP,%",
			
			"OutBalance,$", "BOut,$",
			"OutDate", "Date",
			"OutYear", "Year",
			"OutMonth", "Month",
			"OutMonthYear", "Date",
			"OutMonthDay", "Date",
			"OutQuoter", "Quoter",
			"Term", "Term",
			"PaymentNumber", "PMT#",
			"Prev", "Prev",
			"Next", "Next"
	);
			
	final static public boolean[] isVisible = new boolean[defTitle.length];
	final static public int[] columnNumber = new int[defTitle.length];

	public CommonContext context;

	public AmortizationTable(CommonContext context) {
		this.context = context;
	}

	final static public int getColumnWidth(int order){
		switch( order ){
		case C_NOP:
		case C_TERM:
			return 3;
		case C_BALANCE_IN_DATE:
		case C_DATE_OUT:
		case C_PAYMENT_DATE:
			return 9;
		case C_DATE_OUT_YEAR:
			return 5;
		case C_DATE_OUT_MONTH:
			return 4;
		case C_DATE_OUT_MONTH_YEAR:
		case C_DATE_OUT_MONTH_DAY:
		case C_DATE_OUT_QUOTER:
			return 6;
		default:
			return DEFAULT_COLUMN_WIDTH;
		}
	}

	final static public int getColumnWidth(){
		return DEFAULT_COLUMN_WIDTH;
	}

	final static public String getColumnTitle(int order){
		return title.get(defTitle[order]);
	}
			
	final public Iterator<String[]> getIteratorStar(AmortizationType at){
		return getIteratorStar(at, 0, 0);
	}

	final public Iterator<String[]> getIteratorStar(AmortizationType atype, int year, int term){
		return new AmortizationTableIteratorStrarr(this, atype, year, term);
	}

	final public Iterator<AmortizationTableRow> getIterator(AmortizationType atype, Date from, int year, int term){
		AmortizationTableIterator iter = new AmortizationTableIterator(context, atype, year, term);
		if( UtilDateTime.before(from, context.startDate) ){
			for(CashFlowRow row=iter.next();  
					UtilDateTime.beforeEqual(from, UtilMortgageCalendar.getNextPayday(1, context.paymentFrequency, row.getPayDay())); 
					row=iter.next());
		}
		return iter;
	}

	final public Iterator<AmortizationTableRow> getIterator(AmortizationType atype, int year, int term){
		return new AmortizationTableIterator(context, atype, year, term);
	}

	final public Iterator<AmortizationTableRow> getIterator(AmortizationType at){
		return getIterator(at, 0, 0);
	}

	static final public void setVisible(int index, boolean b){
		AmortizationTable.isVisible[index] = b;
	}

	static final public void setColumnNumber(int index, int n){
		AmortizationTable.columnNumber[index] = n;
		AmortizationTable.isVisible[index] = true;
	}

	static final public boolean isVisible(int index){
		return AmortizationTable.isVisible[index];
	}
	
	static final public String columnLabels(int[] column){
		String labels = "";
		
		for(int i=0; i<column.length; i++){
			if( i > 0 )
				labels += ",";
			labels += getColumnTitle(column[i]);
		}
		
		return labels;
	}
	

}


