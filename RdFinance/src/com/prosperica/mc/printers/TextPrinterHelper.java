package com.prosperica.mc.printers;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import ca.mss.rd.util.UtilDateTime;
import ca.mss.rd.util.UtilFormat;
import ca.mss.rd.util.UtilMath;
import ca.mss.rd.util.UtilMisc;
import ca.mss.rd.util.UtilString;

import com.prosperica.cashflow.CashFlowPaymentIterator;
import com.prosperica.cashflow.CashFlowRow;
import com.prosperica.common.AmortizationTable;
import com.prosperica.common.CommonContext;
import com.prosperica.common.Period;
import com.prosperica.mc.AmortizationType;

public class TextPrinterHelper {
	
	final static public String className = TextPrinterHelper.class.getName();
	final static public long serialVersionUID = className.hashCode();

	final static public int TABLE_COLUMN_WIDTH = 25;
	final static public String COLUMN_SPLITTER = "";
	
	
	public static void printCashFlow(PrintStream print, CommonContext[] context, Date from, int year, int term) {
		print.println("CASH FLOW"+(context.length > 1? " FROM "+UtilDateTime.formatMDY(from): " - "+context[0].getLabel()));
		
		print.println(UtilString.alignRight("Date",13)+
				UtilString.alignRight("MMYYYY",7)+
				UtilString.alignRight("Balance,$",12)+
				UtilString.alignRight("Payment,$",11)+
				UtilString.alignRight("Interest,$",11)+
				UtilString.alignRight("TTL Int,$",12)+
				UtilString.alignRight("TTL Int,%",10)+
				" Description");

		int count = 0;
		Date lastPayDay = null;
		double balance = 0.0;
		double payment = 0.0;
		double interest = 0.0;
		
		for(CashFlowPaymentIterator iter = new CashFlowPaymentIterator(context, from, year, term); iter.hasNext(); ){
			CashFlowRow row = iter.next();
			
			if( lastPayDay == null ) {
				lastPayDay = row.getPayDay();
			} else if( !UtilDateTime.isSameMonth(lastPayDay, row.getPayDay()) ){
				/* print month totals */
				if( count > 1 ){
					print.print(UtilString.alignLeft("Month totals:", 20));
					print.print(UtilString.alignRight(UtilFormat.format(balance), 12));
					print.print(UtilString.alignRight(UtilFormat.format(payment), 11));
					print.print(UtilString.alignRight(UtilFormat.format(interest), 11));
					print.println();
					print.println();
				}

				count = 0;
				balance = 0.0;
				payment = 0.0;
				interest = 0.0;
				lastPayDay = row.getPayDay();
			}
			
			if( !row.getDescription().contains("Mortgage")){
				balance += row.getBalance();
				interest += row.getInterest();
			}
			payment += row.getPayment();
			count++;
			
			if( row.getInterest() > 0.0 || row.getDescription().contains("Mortgage") )
				print.print(UtilString.alignRight(UtilDateTime.formatMDY(row.getPayDay()), 13));
			else
				print.print(UtilString.alignRight(UtilDateTime.format(row.getPayDay(), "MMM")+" X, "+UtilDateTime.format(row.getPayDay(), "yyyy"), 13));
			print.print(UtilString.alignRight(UtilDateTime.format(row.getPayDay(), "yyyyMM"), 7));
			print.print(UtilString.alignRight(UtilFormat.format(row.getBalance()), 12));
			print.print(UtilString.alignRight(UtilFormat.format(row.getPayment()), 11));
			print.print(UtilString.alignRight(UtilFormat.format(row.getInterest()), 11));
			print.print(UtilString.alignRight(UtilFormat.format(row.getInterestTotal()), 12));
			print.print(UtilString.alignRight(UtilFormat.format(row.getInterestPrc()), 10));
			print.print(" "+row.getDescription());
			print.println();
		}
	}

	
	public static void printInput(PrintStream print, CommonContext context) {
		printInput(print, new CommonContext[]{context}, "");
	}
	
	public static void printInput(PrintStream print, CommonContext[] context) {
		printInput(print, context, "");
	}
	
	public static void printInput(PrintStream print,  CommonContext[] mc, String header) {
		print.println(mc[0].getLabel().toUpperCase()+(mc.length==1? "": " "+header));
		print.println();
		printPrincipal(print, mc);
		printAnnualRate(print, mc);
		printAmortization(print, mc);
		printStartDate(print, mc);
		printYearTerm(print, mc);
		printExtraPayment(print, mc);
		printExtraFrequency(print, mc);
		printExtraOrder(print, mc);
	}
	
	public static void printPayments(PrintStream print, CommonContext context) {
		if( context.getPaymentTypeLength() == 1 )
			if( context.getPayment(0) == 0.0 )
				return;
		
		print.println();
		print.println("REGULAR PAYMENTS");
		for(int i=0, max=context.getPaymentTypeLength(); i<max; i++){
			print.printf("%21s = $%,.2f\n", context.getPaymentType(i).title, context.getPayment(i));
		}
	}

	public static void printPayments(PrintStream print, CommonContext[] context) {
		print.println();
		print.println("PAYMENTS");
		for(int i=0; i<context.length; i++ )
			print.printf("%21s (%10s) = $%,.2f\n", context[i].paymentFrequency.title,  UtilDateTime.formatYMD(context[i].startDate), context[i].getPayment());
	}

	public static void printAmortization(PrintStream print, CommonContext context, AmortizationTable ma, AmortizationType at) {
		printAmortization(print, context, ma, at, 0, 0);
	}
	
	public static void printAmortization(PrintStream print, CommonContext context, AmortizationTable ma, AmortizationType at, int[] cols) {
		printAmortization(print, context, ma, at, 0, 0, cols);
	}
	
	public static void printAmortization(PrintStream print, CommonContext context, 
			AmortizationTable ma, AmortizationType at, int year, int term, int[] cols) {
	
		System.out.println();
		System.out.println("AMORTIZATION TABLE "+at.toString().toUpperCase()+" "+printPaymentFrequency(null, new CommonContext[]{context})+"["+at.title+"]"+printExtraPayment(null, new CommonContext[]{context})+printExtraFrequency(null, new CommonContext[]{context})+printExtraOrder(null, new CommonContext[]{context}));
		
		Iterator<String[]> iter = ma.getIteratorStar(at, year, term);
	
		/* Table Header */
		for(int i=0; i<cols.length; i++){
			int c = cols[i];
			System.out.printf("%"+(AmortizationTable.getColumnWidth()+1)+"s|", 
						UtilString.alignRight(AmortizationTable.getColumnTitle(c)+" ", AmortizationTable.getColumnWidth()));
		}
		System.out.println();
		
		/* Table Body */
		while( iter.hasNext() ){
			String[] row = iter.next();
			for(int i=0; i<cols.length; i++){
				int c = cols[i];
				System.out.printf("%"+AmortizationTable.getColumnWidth()+"s |", row[c]);
			}
			System.out.println();
		}
	}

	public static void printAmortization(PrintStream print, CommonContext context, 
			AmortizationTable ma, AmortizationType at, int year, int term) {
	
		System.out.println();
		System.out.println("AMORTIZATION TABLE "+at.toString().toUpperCase()+" "+printPaymentFrequency(null, new CommonContext[]{context})+"["+at.title+"]"+printExtraPayment(null, new CommonContext[]{context})+printExtraFrequency(null, new CommonContext[]{context})+printExtraOrder(null, new CommonContext[]{context}));
		
		Iterator<String[]> iter = ma.getIteratorStar(at, year, term);
	
		/* Table Header */
		for(int c=0; c<AmortizationTable.defTitle.length; c++){
			System.out.printf("%"+(AmortizationTable.getColumnWidth()+1)+"s|", 
						UtilString.alignRight(AmortizationTable.getColumnTitle(c)+" ", AmortizationTable.getColumnWidth()));
		}
		System.out.println();
		
		/* Table Body */
		while( iter.hasNext() ){
			String[] row = iter.next();
			for(int c=0; c<AmortizationTable.defTitle.length; c++){
				System.out.printf("%"+AmortizationTable.getColumnWidth()+"s |", row[c]);
			}
			System.out.println();
		}
	}

	public static void printAmortization(PrintStream print, CommonContext[] context, 
			AmortizationTable ma, AmortizationType at, int year, int term) {
	
		System.out.println();
		System.out.println("AMORTIZATION TABLE "+at.toString().toUpperCase()+" "+printPaymentFrequency(null, context)+"["+at.title+"]"+printExtraPayment(null, context)+printExtraFrequency(null, context)+printExtraOrder(null, context));
		
		Iterator<String[]> iter = ma.getIteratorStar(at, year, term);
	
		/* Table Header */
		for(int c=0; c<AmortizationTable.defTitle.length; c++){
			System.out.printf("%"+(AmortizationTable.getColumnWidth()+1)+"s|", 
						UtilString.alignRight(AmortizationTable.getColumnTitle(c)+" ", AmortizationTable.getColumnWidth()));
		}
		System.out.println();
		
		/* Table Body */
		while( iter.hasNext() ){
			String[] row = iter.next();
			for(int c=0; c<AmortizationTable.defTitle.length; c++){
				System.out.printf("%"+AmortizationTable.getColumnWidth()+"s |", row[c]);
			}
			System.out.println();
		}
	}

	
	public static void printAmortizationCompareByNOP(PrintStream print, CommonContext[] mc, 
			AmortizationTable[] ma, AmortizationType at, int nop) {

		System.out.println();
		System.out.println("AMORTIZATION COMPARE RESULTS "+printPaymentFrequency(null, mc)+"["+at.title+"][NOP="+nop+"]"+printExtraPayment(null, mc)+printExtraFrequency(null, mc)+printExtraOrder(null, mc));

		ArrayList<Iterator<String[]>> iterators = getIterators(ma, at, 0, 0);

		String[][] columns = new String[iterators.size()][];
		String[] val = null;
		do {
			for(int i=0, mi=iterators.size(); i<mi; i++ ){
				if( iterators.get(i).hasNext() )
					val = columns[i] = iterators.get(i).next();
				else
					return;
			}
		}while( val != null && Integer.parseInt(val[AmortizationTable.C_NOP]) != nop );

		boolean[] isVisible = new boolean[columns[0].length];
		for(int j=0, mj=columns[0].length; j<mj; j++){
			for(int i=1; i<columns.length; i++ ){
				if( !columns[i][j].equals(columns[0][j]) ){
					isVisible[j] = true;
					break;
				}
			}
		}

		boolean[] isDuplicated = new boolean[columns[0].length];
		for(int i=0, mi=columns[0].length-1; i<mi; i++){
			for(int j=i+1; j<columns[0].length; j++){
				if( !isDuplicated[j] ){
					int duplicationCount = 0;
					for( int k=0; k<columns.length; k++ ){
						if( columns[k][i].equals(columns[k][j]) ){
							duplicationCount++;
						}
					}
					if( duplicationCount == columns.length){
						isDuplicated[j] = true;
					}
				}
			}
		}

		/* Print comparision header */
		System.out.printf("%"+TABLE_COLUMN_WIDTH+"s |", "Mortgage");
		for(int j=0; j<ma[0].defTitle.length; j++){
			if( isVisible[j] && !isDuplicated[j] ){
				System.out.printf("%"+(ma[0].getColumnWidth()+1)+"s|", UtilString.alignRight(ma[0].defTitle[j]+" ", ma[0].getColumnWidth()));
			}
		}
		System.out.println();
		

		/* Print comparision table*/
		for(int i=0; i<columns.length; i++ ){
			System.out.printf("%"+TABLE_COLUMN_WIDTH+"s |", printLabel(null, mc, i));
			for(int j=0; j<columns[i].length; j++ ){
				if( isVisible[j] && !isDuplicated[j] ){
					System.out.printf("%"+ma[0].getColumnWidth()+"s |", columns[i][j]);
				}
			}
			System.out.println();
		}
	}

	enum AmortizationSummaryType {
		YEAR_AMORTIZATION("Year"),
		TERM_AMORTIZATION("Term"),
		FULL_AMORTIZATION("Full");
		
		final public String descr;
		
		AmortizationSummaryType(String descr){
			this.descr = descr;
		}

		@Override
		public String toString() {
			return descr+" Amortization Summary";
		}

		public String toString(int par) {
			switch( this ){
			case YEAR_AMORTIZATION:
				return "Amortization Summary" + (par==0?"":" [year="+par+"]");
			case TERM_AMORTIZATION:
				return "Amortization Summary" + (par==0?"":" [term="+par+"]");
			case FULL_AMORTIZATION:
				return descr+" Amortization Summary";
			default:
				return toString();
			}
		}
		
	}
	
	public static void printAmortizationSummary(PrintStream print, CommonContext[] mc, 
			AmortizationTable[] ma, AmortizationType at, AmortizationSummaryType ast, 
			int[] clmnSum, int[] wcSum,
			int parameter, String splitter) {

		System.out.println();
		System.out.println(ast.toString(parameter).toUpperCase());

		ArrayList<Iterator<String[]>> iterators = null;

		switch( ast ){
		case YEAR_AMORTIZATION:
			iterators = getIterators(ma, at, parameter, 0);
			break;
		case TERM_AMORTIZATION:
			iterators = getIterators(ma, at, 0, parameter);
			break;
		case FULL_AMORTIZATION:
			iterators = getIterators(ma, at, 0, 0);
			break;
		default:
			new RuntimeException("Unexpected AmortizationSummaryType value ["+ast+"]");
		}

		String[][] columns = new String[iterators.size()][];
		for(int i=0, mi=iterators.size(); i<mi; i++ ){
			while( iterators.get(i).hasNext() )
				columns[i] = iterators.get(i).next();
		}

		/* Print comparision header */
		System.out.printf("%"+(TABLE_COLUMN_WIDTH+shortLabel(0).length())+"s"+splitter, "Mortgage");
		for(int j=0; j<clmnSum.length; j++){
			System.out.printf("%"+wcSum[j]+"s"+splitter, UtilString.alignRight(ma[0].getColumnTitle(clmnSum[j]), wcSum[j]));
		}
		System.out.println();
		

		/* Print comparision table*/
		for(int i=0; i<columns.length; i++ ){
			System.out.printf("%s%"+TABLE_COLUMN_WIDTH+"s"+splitter, shortLabel(i), printLabel(null, mc, i));
			for(int j=0; j<clmnSum.length; j++){
				System.out.printf("%"+wcSum[j]+"s"+splitter, columns[i][clmnSum[j]]);
			}
			System.out.println();
		}
	}	
	
	public static void printSummary(PrintStream print, CommonContext[] mc, AmortizationTable[] ma){
		print.println();
		print.println("SUMMARY");
		for(int i=0; i<mc.length; i++){
			print.printf("%s", shortLabel(i)+" "+printLabel(null, mc, i));
			print.println();
		}
	}
	
	private static ArrayList<Iterator<String[]>> getIterators(AmortizationTable[] ma, AmortizationType at, int year, int term){
		ArrayList<Iterator<String[]>> iterators = new ArrayList<Iterator<String[]>>(ma.length);
		for(int i=0; i<ma.length; i++ ){
			iterators.add(i, ma[i].getIteratorStar(at, year, term));
		}
		return iterators;
	}
	
	private static String shortLabel(int index){
		return "#"+(index+1);
	}
	
	public static void printAmortizationCompareByColumn(PrintStream print, CommonContext[] mc, 
			AmortizationTable[] ma, AmortizationType at,
			int[] header, int[] wh,  int[] column, int[] wc, 
			int year, int term,
			String splitter) {

		System.out.println();
		System.out.println("AMORTIZATION "+at.toString(year, term).toUpperCase());

		ArrayList<Iterator<String[]>> iter = getIterators(ma, at, year, term);

		/* Table Header */
		// Determine table column widths
		int headerWidth=splitter.length(), titleWidth=0, titleWidth2=0;
		for(int i=0; i<wh.length; i++){
			headerWidth += wh[i];
		}
		for(int i=0; i<wc.length; i++){
			titleWidth += wc[i];
			titleWidth2 += wc[i];
		}
		
		// table titles
		String spaceSplitter = "                ".substring(0, splitter.length());
		if( column.length == 1 ){
			System.out.printf("%"+headerWidth+"s"+splitter, ""); // headers
			for(int i=0; i<ma.length; i++){
				for(int c=0; c<column.length; c++){
					System.out.printf("%"+wc[c]+"s"+((i+1)<ma.length? splitter: ""), 
								UtilString.alignRight(AmortizationTable.getColumnTitle(column[c])+shortLabel(i), wc[c]));
				}
			}
			System.out.println();
		} else {
			System.out.printf("%"+headerWidth+"s"+spaceSplitter, ""); // headers
			for(int i=0; i<mc.length; i++){
				System.out.printf("%"+titleWidth2+"s"+splitter, UtilString.alignCenter(shortLabel(i), titleWidth));
			}
			System.out.println();
			
			// table column titles
			System.out.printf("%"+headerWidth+"s"+splitter, ""); // headers
			for(int i=0; i<ma.length; i++){
				for(int c=0; c<column.length; c++){
					System.out.printf("%"+wc[c]+"s"+((i+1)<ma.length? splitter: ""), 
								UtilString.alignRight(AmortizationTable.getColumnTitle(column[c]), wc[c]));
				}
			}
			System.out.println();
		}

		/* Table Body */
		do {
			// Select rows from each iterator first
			String[][] rows = new String[iter.size()][];  
			for(int i=0, mi=iter.size(); i<mi; i++){
				if( iter.get(i).hasNext() ){
					rows[i] = iter.get(i).next();
				}
			}

			// Find first not null row
			String[] notnull = null;
			for(int i=0; i<rows.length; i++){
				if( rows[i] != null ){
					notnull = rows[i];
				}
			}

			// nothing to print ?
			if( notnull == null ){
				break;
			}
			
			// Header columns
			for(int h=0; h<header.length; h++){
				System.out.printf("%"+wh[h]+"s"+splitter, UtilString.alignRight(notnull[header[h]], wh[h]));
			}
			
			// table columns
			for(int i=0; i<rows.length; i++){
				if( rows[i] != null ){
					for(int c=0; c<column.length; c++){
						System.out.printf("%"+wc[c]+"s"+((i+1)<ma.length? splitter: ""), rows[i][column[c]]);
					}
				} else
					for(int c=0; c<column.length; c++){
						System.out.printf("%"+wc[c]+"s"+((i+1)<ma.length? splitter: ""), "");
					}
			}
			System.out.println();
		}while( true );
		

	}
	
	public static void printMorthageCombineCompare(PrintStream print, CommonContext[] mc, AmortizationType at, 
			int[] clmnCmp, int[] wcCmp, 
			int[] clmnSum, int[] wcSum, 
			String splitter, int year, int term){
		
		AmortizationTable[] ma = new AmortizationTable[mc.length];
		for(int i=0; i<mc.length; i++ ){
			ma[i] = mc[i].computate();
		}
		
		int[] header = new int[]{};
		int[] wh = new int[]{};

		switch( at ){
		case FOR_WHOLE_AMORTIZATION_PERIOD:
			break;
		case BY_PAYMENT:
			for(int i=1; i<mc.length; i++ ){
				if( mc[i].paymentFrequency != mc[0].paymentFrequency )
					throw new RuntimeException("Can not compare by payments if payment frequency not the same");
			}
			header = new int[]{AmortizationTable.C_NOP, AmortizationTable.C_DATE_OUT_YEAR};
			wh = new int[]{3, 6};
			break;
		case BY_MONTH:
			if( year > 0 ){
				header = new int[]{AmortizationTable.C_NOP, AmortizationTable.C_DATE_OUT_MONTH};
				wh = new int[]{3, 3};
			} else {
				header = new int[]{AmortizationTable.C_NOP, AmortizationTable.C_DATE_OUT_MONTH_YEAR};
				wh = new int[]{3, 8};
			}
			break;
		case BY_QUOTER:
			header = new int[]{AmortizationTable.C_NOP, AmortizationTable.C_DATE_OUT_QUOTER};
			wh = new int[]{2, 4};
			break;
		case BY_TERM:
			header = new int[]{AmortizationTable.C_TERM, AmortizationTable.C_DATE_OUT_YEAR};
			wh = new int[]{1, 4};
			break;
		case BY_YEAR:
			header = new int[]{AmortizationTable.C_NOP, AmortizationTable.C_DATE_OUT_YEAR};
			wh = new int[]{2, 4};
			break;
		default:
			throw new RuntimeException("Unexpected ma type ["+at+"]");
		}
		
		TextPrinterHelper.printInput(print, mc, "COMPARATOR");

		TextPrinterHelper.printAmortizationSummary(print, mc, ma, at, AmortizationSummaryType.TERM_AMORTIZATION, clmnSum, wcSum, term, splitter);

		TextPrinterHelper.printAmortizationCompareByColumn(print, mc, ma, at, header, wh, clmnCmp, wcCmp, year, term, splitter);
		
	}

	public static void printMortgageCompareByColumn(PrintStream print, CommonContext[] mc, AmortizationType at, 
			int[] clmnCmp, int[] wcCmp, 
			String splitter, int year, int term){
		
		AmortizationTable[] ma = new AmortizationTable[mc.length];
		for(int i=0; i<mc.length; i++ ){
			ma[i] = mc[i].computate();
		}
		
		int[] header = new int[]{};
		int[] wh = new int[]{};

		switch( at ){
		case FOR_WHOLE_AMORTIZATION_PERIOD:
			break;
		case BY_PAYMENT:
			for(int i=1; i<mc.length; i++ ){
				if( mc[i].paymentFrequency != mc[0].paymentFrequency )
					throw new RuntimeException("Can not compare by payments if payment frequency not the same");
			}
			header = new int[]{AmortizationTable.C_NOP, AmortizationTable.C_DATE_OUT_YEAR};
			wh = new int[]{3, 6};
			break;
		case BY_MONTH:
			if( year > 0 ){
				header = new int[]{AmortizationTable.C_NOP, AmortizationTable.C_DATE_OUT_MONTH};
				wh = new int[]{3, 3};
			} else {
				header = new int[]{AmortizationTable.C_NOP, AmortizationTable.C_DATE_OUT_MONTH_YEAR};
				wh = new int[]{3, 8};
			}
			break;
		case BY_QUOTER:
			header = new int[]{AmortizationTable.C_NOP, AmortizationTable.C_DATE_OUT_QUOTER};
			wh = new int[]{2, 4};
			break;
		case BY_TERM:
			header = new int[]{AmortizationTable.C_TERM, AmortizationTable.C_DATE_OUT_YEAR};
			wh = new int[]{1, 4};
			break;
		case BY_YEAR:
			header = new int[]{AmortizationTable.C_NOP, AmortizationTable.C_DATE_OUT_YEAR};
			wh = new int[]{2, 4};
			break;
		default:
			throw new RuntimeException("Unexpected ma type ["+at+"]");
		}
		
		TextPrinterHelper.printInput(print, mc, "COMPARATOR");

		TextPrinterHelper.printSummary(print, mc, ma);

		TextPrinterHelper.printAmortizationCompareByColumn(print, mc, ma, at, header, wh, clmnCmp, wcCmp, year, term, splitter);
		
	}

	public static void printMortgageCompareByNOP(PrintStream print, CommonContext[] mc, AmortizationType at, int nop){
		AmortizationTable[] ma = new AmortizationTable[mc.length];
		for(int i=0; i<mc.length; i++ ){
			ma[i] = mc[i].computate();
		}
		
		TextPrinterHelper.printInput(print, mc, "COMPARATOR");

		TextPrinterHelper.printAmortizationCompareByNOP(print, mc, ma, at, nop);
		
	}
	
	public static String printLabel(PrintStream print, CommonContext[] mc, int index){
		String label = "";
		
		CommonContext[] mcindex = new CommonContext[]{mc[index]};
		
		if( UtilMisc.isEmpty(printPaymentFrequency(null, mc))){
			label += printPaymentFrequency(null, mcindex); 
		}
		
		label += "$"+mc[index].getPayment(); 
		
		if( UtilMisc.isEmpty(printPrincipal(null, mc))){
			label += printPrincipal(null, mcindex); 
		}
		
		if( UtilMisc.isEmpty(printAnnualRate(null, mc))){
			label += printAnnualRate(null, mcindex); 
		}
		
		if( UtilMisc.isEmpty(printAmortization(null, mc))){
			label += printAmortization(null, mcindex); 
		}
		
		if( UtilMisc.isEmpty(printStartDate(null, mc))){
			label += printStartDate(null, mcindex); 
		}
		
		if( UtilMisc.isEmpty(printYearTerm(null, mc))){
			label += printYearTerm(null, mcindex); 
		}
		
		if( UtilMisc.isEmpty(printExtraPayment(null, mc))){
			label += printExtraPayment(null, mcindex); 
		}
		
		if( UtilMisc.isEmpty(printExtraFrequency(null, mc))){
			label += printExtraFrequency(null, mcindex); 
		}
		/*
		if( UtilMisc.isEmpty(printExtraOrder(null, mc))){
			label += printExtraOrder(null, mcindex); 
		}*/
		
		return label;
	}
	
	public static String printPaymentFrequency(PrintStream print, CommonContext[] mc){
		for(int i=0; i<mc.length; i++ ){
			if( mc[i].paymentFrequency != null && mc[i].paymentFrequency != mc[0].paymentFrequency )
				return "";
		}
		if( print == null) 
			return mc[0].paymentFrequency.shortTitle;
		else
			print.println("       Frequency = "+mc[0].paymentFrequency);
		return "";
	}
	
	public static String printPrincipal(PrintStream print, CommonContext[] mc){
		for(int i=0; i<mc.length; i++ ){
			if( mc[0].principal == 0 || mc[i].principal != mc[0].principal )
				return "";
		}
		if( print == null) 
			return "$"+mc[0].principal;
		else
			print.println("      Principal = $"+mc[0].principal);
		return "";
	}
	
	public static String printAnnualRate(PrintStream print, CommonContext[] mc){
		for(int i=0; i<mc.length; i++ ){
			if( mc[0].annualRate == 0.0 || mc[i].annualRate != mc[0].annualRate )
				return "";
		}
		if( print == null) 
			return (mc[0].annualRate*100)+"%";
		else
			print.println("  Interest Rate = "+UtilMath.round(mc[0].annualRate*100, 4)+"%");
		return "";
	}
	
	public static String printAmortization(PrintStream print, CommonContext[] mc){
		for(int i=0; i<mc.length; i++ ){
			if( !mc[i].amortization.equals(mc[0].amortization) )
				return "";
		}
		if( print == null) 
			return "/"+mc[0].amortization+"y";
		else if( mc[0].amortization.amortization > 0 && !mc[0].amortization.equalsYear(mc[0].getYearTerm()) )
			print.println("   Amortization = "+mc[0].amortization);
		return "";
	}
	
	public static String printStartDate(PrintStream print, CommonContext[] mc){
		for(int i=0; i<mc.length; i++ ){
			if( mc[0].getStartDate() == null || mc[i].getStartDate() != mc[0].getStartDate() )
				return "";
		}
		if( print == null) 
			return ""+mc[0].getStartDate();
		else
			print.println("     Start Date = "+UtilDateTime.formatYMD(mc[0].getStartDate()));
		return "";
	}
	
	public static String printYearTerm(PrintStream print, CommonContext[] mc){
		for(int i=0; i<mc.length; i++ ){
			if( mc[i].getYearTerm() != mc[0].getYearTerm() )
				return "";
		}
		if( print == null) 
			return mc[0].getYearTerm()+"y";
		else if( mc[0].getYearTerm() > 0 && mc[0].getYearTerm() != Period.UNLIMITED_AMORTIZATION_PERIOD)
			print.println("           Term = "+mc[0].getYearTerm()+" years");
		return "";
	}
	
	public static String printExtraPayment(PrintStream print, CommonContext[] mc){
		for(int i=0; i<mc.length; i++ ){
			if( mc[i].extraPayment != mc[0].extraPayment )
				return "";
		}
		if( print == null ){
			if( mc[0].extraPayment > 0.0 )
				return "+$"+mc[0].extraPayment;
		} else if( mc[0].extraPayment > 0 )
			print.println("  Extra Payment = "+mc[0].extraPayment+" $");
		return "";
	}
	
	public static String printExtraFrequency(PrintStream print, CommonContext[] mc){
		for(int i=0; i<mc.length; i++ ){
			if( mc[i].extraFrequency != mc[0].extraFrequency )
				return "";
		}
		if( print == null ){
			if( mc[0].extraFrequency != null )
				return "/"+mc[0].extraFrequency.ident;
		} else if( mc[0].extraFrequency != null && mc[0].extraFrequency.paymentsPerMonth != mc[0].paymentFrequency.paymentsPerMonth)
			print.println("Extra Frequency = "+mc[0].extraFrequency);
		return "";
	}
	
	public static String printExtraOrder(PrintStream print, CommonContext[] mc){
		/*
		for(int i=0; i<mc.length; i++ ){
			if( mc[i].extraOrder != mc[0].extraOrder )
				return "";
		}
		if( print == null ){
			if( mc[0].extraOrder != null )
				return mc[0].extraOrder.ident;
		} else if( mc[0].extraOrder != null )
			print.println("    Extra Order = "+mc[0].extraOrder);
		*/
		return "";
	}

	
	
	
	
	
	
	
	
	
	
	public static void printTable(PrintStream print, CommonContext context, AmortizationTable ma,
				AmortizationType at, int[] columns, int term, String year) {

		Iterator<String[]> iter = ma.getIteratorStar(at);

		/* Table Header */
		for(int c=0; c<columns.length; c++){
			System.out.printf("%"+(AmortizationTable.getColumnWidth(columns[c])+1)+"s"+COLUMN_SPLITTER, 
						UtilString.alignRight(AmortizationTable.getColumnTitle(columns[c])+" ", AmortizationTable.getColumnWidth(columns[c])));
		}
		System.out.println();
		
		/* Table Body */
		while( iter.hasNext() ){
			String[] row = iter.next();
			if( year != null && !year.equals(row[AmortizationTable.C_DATE_OUT_YEAR]) ){
				continue;
			} else if( Integer.parseInt(row[AmortizationTable.C_TERM]) > term ){
				continue;
			}
			for(int c=0; c<columns.length; c++){
				System.out.printf("%"+AmortizationTable.getColumnWidth(columns[c])+"s "+COLUMN_SPLITTER, row[columns[c]]);
			}
			System.out.println();
		}
	}
		
	
	
	
	
	
	
	/* TAB: Table, RADIO: Amortization */
	public static void printTableTabAmortization(PrintStream print, AmortizationTable ma, CommonContext context, 
			String title, int term) {

		print.println();
		print.println(title);
		
		int[] columns = new int[]{
				AmortizationTable.C_NOP,
				AmortizationTable.C_DATE_OUT_YEAR,
				AmortizationTable.C_TOTAL_INTEREST,
				AmortizationTable.C_TOTAL_PRINCIPAL,
				AmortizationTable.C_TOTAL_FULL_PAYMENT,
				AmortizationTable.C_BALANCE_OUT
		};
		
		TextPrinterHelper.printTable(print, context, ma, AmortizationType.BY_YEAR, columns,
				term, null);
	}
	

	public static void printTableTabQuoter(PrintStream print, AmortizationTable ma, CommonContext context, 
			String title, int term, int year) {

		print.println();
		print.println(title);
		
		int[] columns = new int[]{
				AmortizationTable.C_NOP,
				AmortizationTable.C_DATE_OUT_QUOTER,
				AmortizationTable.C_TOTAL_INTEREST,
				AmortizationTable.C_TOTAL_PRINCIPAL,
				AmortizationTable.C_TOTAL_FULL_PAYMENT,
				AmortizationTable.C_BALANCE_OUT
		};
		
		TextPrinterHelper.printTable(print, context, ma, AmortizationType.BY_QUOTER, columns, 
				term, Integer.toString(year));
	}

	public static void printTableTabMonth(PrintStream print, AmortizationTable ma, CommonContext context, 
			String title, int term, int year) {

		print.println();
		print.println(title);
		
		int[] columns = new int[]{
				AmortizationTable.C_NOP,
				AmortizationTable.C_DATE_OUT_MONTH,
				AmortizationTable.C_TOTAL_INTEREST,
				AmortizationTable.C_TOTAL_PRINCIPAL,
				AmortizationTable.C_TOTAL_FULL_PAYMENT,
				AmortizationTable.C_BALANCE_OUT
		};
		
		TextPrinterHelper.printTable(print, context, ma, AmortizationType.BY_MONTH, columns,
				term, Integer.toString(year));
	}

	public static void printTableTabPayment(PrintStream print, AmortizationTable ma, CommonContext context, 
			String title, int term, int year) {

		print.println();
		print.println(title);
		
		int[] columns = new int[]{
				AmortizationTable.C_NOP,
				AmortizationTable.C_DATE_OUT_MONTH_DAY,
				AmortizationTable.C_TOTAL_INTEREST,
				AmortizationTable.C_TOTAL_PRINCIPAL,
				AmortizationTable.C_TOTAL_FULL_PAYMENT,
				AmortizationTable.C_BALANCE_OUT
		};
		
		TextPrinterHelper.printTable(print, context, ma, AmortizationType.BY_MONTH, columns, 
				term, Integer.toString(year));
	}


}


