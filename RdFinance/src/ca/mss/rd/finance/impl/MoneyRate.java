package ca.mss.rd.finance.impl;

import java.util.Date;

import ca.mss.rd.calendar.CountryHolidays;
import ca.mss.rd.finance.CalendarSequence;
import ca.mss.rd.finance.CashFlowEvents.CashFlowEvent;
import ca.mss.rd.finance.CashFlowEvents.RecurrentEvent;
import ca.mss.rd.finance.Money;
import ca.mss.rd.util.UtilDateTime;

public class MoneyRate implements Money {

	final static public String className = MoneyFixedAmount.class.getName();
	
	final public double rate, hoursPerDay, taxes;
	final public String currency;

	private double getHours(Date onDate, CalendarSequence calendar) {
		//System.out.print("[period="+UtilDateTime.formatMDY(UtilDateTime.getDayStart(onDate, -1, 0, 0))+" - "+UtilDateTime.formatMDY(calendar.getPrevDay(onDate))+"]");
		double totalHours = 0.0;
		//System.out.print("[");
		for(Date date=calendar.getPrevDay(onDate); UtilDateTime.before(onDate, date); date=UtilDateTime.addDate(date, 1, 0, 0)){
			if( UtilDateTime.isMn2Fr(date) )
				if( !CountryHolidays.CANADA.ON.isHoliday(date) ){
					totalHours += hoursPerDay;
					//System.out.print(UtilDateTime.getDayOfMonth(date)+",");
				}
		}
		//System.out.print("]["+totalHours+"h]");
		return totalHours;
	}

	public double amount(CashFlowEvent event) {
		if( event instanceof RecurrentEvent ){
			RecurrentEvent revent = (RecurrentEvent )event;
			return rate*getHours(event.onDate, revent.calendar)*taxes;
		}
		return 0D;
	}

	public String currency() {
		return currency;
	}

	public MoneyRate(double rate, String currency) {
		this.rate = rate;
		this.currency = currency;
		this.hoursPerDay = 8.0;
		this.taxes = 1.13;
	}

	public MoneyRate(double rate) {
		this(rate, DEFAULT_CURRENCY);
	}

}


