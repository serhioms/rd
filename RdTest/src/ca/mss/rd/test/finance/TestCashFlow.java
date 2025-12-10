package ca.mss.rd.test.finance;

import java.util.Iterator;

import ca.mss.rd.finance.CalendarSequence;
import ca.mss.rd.finance.CashFlow;
import ca.mss.rd.finance.CashFlowEvents;
import ca.mss.rd.finance.CashFlowEvents.CashFlowEvent;
import ca.mss.rd.finance.EventFrequency;
import ca.mss.rd.finance.impl.MoneyRate;
import ca.mss.rd.util.UtilDateTime;

import com.prosperica.common.Def;

public abstract class TestCashFlow {

	final static public String className = TestCashFlow.class.getName();

	public static void main(String[] args) {
		test3();
		test2();
		test1();
	}
	
	public static void test3() {
		System.out.println("\n\n\n====== test3");

		CashFlow cashFlow = new CashFlow(CashFlowEvents.SALARY.onSchedule(
				new CalendarSequence(
					UtilDateTime.parse("1/1/2013", "MM/dd/yyyy"),
					EventFrequency.SEMI_MONTHLY),
					new MoneyRate(Def.HOUR_RATE_CAD(75.0)), "Zarplata-papa"));
		
		@SuppressWarnings("unused")
		Iterator<CashFlowEvent> iter = cashFlow.iterator(false?null:UtilDateTime.parse("1/1/2013", "MM/dd/yyyy"));
		for(int i=0; i<25; i++){
			CashFlowEvent event = iter.next();
			System.out.println(event.toString());
		}
	}

	public static void test2() {
		System.out.println("\n\n\n====== test2");
		
		CashFlow cashFlow = new CashFlow(CashFlowEvents.PAYMENT.onSchedule(
				new CalendarSequence(
					UtilDateTime.parse("4/17/2012", "MM/dd/yyyy"),
					EventFrequency.MONTHLY),
				Def.CAD(330.0), "Car Insurance"));
		
		cashFlow.add(CashFlowEvents.PAYMENT.onDate(UtilDateTime.parse("7/16/2012", "MM/dd/yyyy"), Def.CAD(360.0)));
		
		@SuppressWarnings("unused")
		Iterator<CashFlowEvent> iter = cashFlow.iterator(false?null:UtilDateTime.parse("1/1/2012", "MM/dd/yyyy"));
		for(int i=0; i<10; i++){
			CashFlowEvent event = iter.next();
			System.out.println(event.toString());
		}
	}

	public static void test1() {
		System.out.println("\n\n\n====== test1");
		
		CashFlow cashFlow = new CashFlow();
		
		cashFlow.add(CashFlowEvents.REMINDER.onDate(UtilDateTime.parse("9/21/2012", "MM/dd/yyyy"), Def.CAD(750.0), "CC Balance"));
		cashFlow.add(CashFlowEvents.PURCHASE.onDate(UtilDateTime.parse("9/28/2012", "MM/dd/yyyy"), Def.CAD(100.0), "Roku player"));
		cashFlow.add(CashFlowEvents.REMINDER.onDate(UtilDateTime.parse("9/30/2012", "MM/dd/yyyy"), Def.CAD(750.0), "CL Balance"));
		cashFlow.add(CashFlowEvents.REMINDER.onDate(UtilDateTime.parse("9/26/2012", "MM/dd/yyyy"), Def.CAD(750.0), "Loan Balance"));
		cashFlow.add(CashFlowEvents.PAYMENT.onDate(UtilDateTime.parse("9/15/2012", "MM/dd/yyyy"), Def.CAD(100.0), "Embridge"));

		for(@SuppressWarnings("unused")
		Iterator<CashFlowEvent> iter = cashFlow.iterator(false?null:UtilDateTime.parse("9/27/2012", "MM/dd/yyyy")); iter.hasNext();){
			CashFlowEvent event = iter.next();
			System.out.println(event.toString());
		}
	}
}
/*

Apr 17, 2012 Payment CAD330.00 - Car Insurance
May 17, 2012 Payment CAD330.00 - Car Insurance
Jun 17, 2012 Payment CAD330.00 - Car Insurance
Jul 17, 2012 Payment CAD330.00 - Car Insurance
Aug 17, 2012 Payment CAD330.00 - Car Insurance


Sep 15, 2012 Payment CAD100.00 - Embridge
Sep 21, 2012 Reminder CAD750.00 - CC Balance
Sep 26, 2012 Reminder CAD750.00 - Loan Balance
Sep 28, 2012 Purchase CAD100.00 - Roku player
Sep 30, 2012 Reminder CAD750.00 - CL Balance

Sep 28, 2012 Purchase CAD100.00 - Roku player
Sep 30, 2012 Reminder CAD750.00 - CL Balance

*/


