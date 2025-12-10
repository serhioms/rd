package ca.mss.rd.finance;

import java.util.Date;

import ca.mss.rd.finance.impl.MoneyFixedAmount;
import ca.mss.rd.util.UtilDateTime;
import ca.mss.rd.util.UtilFormat;



public enum CashFlowEvents {

	SALARY ("Salary"),
	PURCHASE ("Purchase"),
	PAYMENT ("Payment"),
	REMINDER ("Reminder");

	final String title;
	
	private CashFlowEvents(String title) {
		this.title = title;
	}

	public class CashFlowEvent {
		
		final public CashFlowEvents event;
		public String descr;
		public Money amount;
		public Date onDate;

		private CashFlowEvent(CashFlowEvents event, Money amount, String descr) {
			this.event = event;
			this.amount = amount;
			this.descr = descr;
			
		}

		@Override
		public String toString() {
			return title+" "+amount.currency()+UtilFormat.format(amount.amount(this))+" - "+descr;
		}
	}

	public class OneTimeEvent extends CashFlowEvent {
		
		private OneTimeEvent(CashFlowEvents event, Date onDate, Money amount, String descr) {
			super(event, amount, descr);
			super.onDate = onDate;
			
		}

		@Override
		public String toString() {
			return UtilDateTime.format(onDate, "MMM dd, yyyy")+" "+super.toString();
		}
	}

	public class RecurrentEvent extends CashFlowEvent {
		
		final public CalendarSequence calendar;

		private RecurrentEvent(CashFlowEvents event, CalendarSequence calendar, Money amount, String descr) {
			super(event, amount, descr);
			this.calendar = calendar;
		}

		@Override
		public String toString() {
			return UtilDateTime.format(onDate, "MMM dd, yyyy")+" "+super.toString();
		}
	}

	final public OneTimeEvent onDate(Date onDate, Money amount, String descr){
		return new OneTimeEvent(this, onDate, amount, descr);
	}
	
	final public OneTimeEvent onDate(Date onDate, double amount, String descr){
		return new OneTimeEvent(this, onDate, new MoneyFixedAmount(amount), descr);
	}
	
	final public OneTimeEvent onDate(Date onDate, double amount){
		return onDate(onDate, amount, null);
	}
	
	final public RecurrentEvent onSchedule(CalendarSequence calendar, Money amount, String descr){
		return new RecurrentEvent(this, calendar, amount, descr);
	}
	
	final public RecurrentEvent onSchedule(CalendarSequence calendar, double amount, String descr){
		return new RecurrentEvent(this, calendar, new MoneyFixedAmount(amount), descr);
	}
	
}


