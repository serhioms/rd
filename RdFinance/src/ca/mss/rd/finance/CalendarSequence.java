package ca.mss.rd.finance;

import java.util.Date;

import ca.mss.rd.util.UtilDateTime;


public class CalendarSequence {

	final static public String className = CalendarSequence.class.getName();

	
	final public Date startDate;
	final public EventFrequency frequency;

	public CalendarSequence(Date startDate, EventFrequency frequency) {
		this.startDate = startDate;
		this.frequency = frequency;
	}
	

	final public CalendarSequenceIterator iterator(){
		return new CalendarSequenceIterator();
	}

	final public CalendarSequenceIterator iterator(Date fromDate){
		if( fromDate == null ){
			return iterator();
		} else {
			CalendarSequenceIterator iter = iterator();
			while( UtilDateTime.before(fromDate, iter.nextDate()) ) 
				iter.next();
			return iter;
		}
	}

	public class CalendarSequenceIterator extends FinancialIterator<Date> {
	
		private Date nextDate;
		
		private CalendarSequenceIterator() {
			this.nextDate = null;
		}

		final public Date next() {
			return nextDate = nextDate();
		}

		@Override
		public Date nextDate() {
			return nextDate==null? startDate: getNextDay(nextDate, frequency, 1);
		}

	}
	
	
	final public Date getNextDay(Date date){
		return getNextDay(date, frequency, 1);
	}
	
	final public Date getPrevDay(Date date){
		return getPrevDay(date, frequency, 1);
	}
	
	final static public Date getNextDay(Date date, EventFrequency frequency, int n){
		if( frequency == EventFrequency.SEMI_MONTHLY ){
			if( UtilDateTime.getDayOfMonth(date) < 16 ){
				if( n % 2 == 1)
					return UtilDateTime.setDay(UtilDateTime.addDate(date, 0, n/2, 0), 16);
				else
					return UtilDateTime.setDay(UtilDateTime.addDate(date, 0, (n+1)/2, 0), 1);
			} else {
				if( (n+1) % 2 == 0)
					return UtilDateTime.setDay(UtilDateTime.addDate(date, 0, (n+1)/2, 0), 1);
				else
					return UtilDateTime.setDay(UtilDateTime.addDate(date, 0, (n+1)/2, 0), 16);
			}
		} else
			return UtilDateTime.addDate(date, n*frequency.periodDays, n*frequency.periodMonth, n*frequency.periodYears);
	}

	final static public Date getPrevDay(Date date, EventFrequency frequency, int n){
		if( frequency == EventFrequency.SEMI_MONTHLY ){
			if( UtilDateTime.getDayOfMonth(date) < 16 ){
				if( n % 2 == 1)
					return UtilDateTime.setDay(UtilDateTime.addDate(date, 0, -(n+1)/2, 0), 16);
				else
					return UtilDateTime.setDay(UtilDateTime.addDate(date, 0, -(n+1)/2, 0), 1);
			} else {
				if( (n-1) % 2 == 0)
					return UtilDateTime.setDay(UtilDateTime.addDate(date, 0, -(n-1)/2, 0), 1);
				else
					return UtilDateTime.setDay(UtilDateTime.addDate(date, 0, -(n-1)/2, 0), 16);
			}
		} else
			return UtilDateTime.addDate(date, -n*frequency.periodDays, -n*frequency.periodMonth, -n*frequency.periodYears);
	}
}


