package ca.mss.rd.test.calendar;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import ca.mss.rd.calendar.CountryHolidays;
import ca.mss.rd.calendar.CountryHolidays.CANADA;
import ca.mss.rd.calendar.impl.Holiday;
import ca.mss.rd.calendar.impl.Holidays;
import ca.mss.rd.finance.CalendarSequence;
import ca.mss.rd.finance.EventFrequency;
import ca.mss.rd.util.Timing;
import ca.mss.rd.util.UtilDateTime;
import ca.mss.rd.util.UtilString;

public class TestCalendar {

	final static public String className = TestCalendar.class.getName();

	
	public static void main3(String[] args) {
		
		CalendarSequence cal = new CalendarSequence( UtilDateTime.parse("9/19/2012", "MM/dd/yyyy"), EventFrequency.WEEKLY);
		
		@SuppressWarnings("unused")
		Iterator<Date> iter=cal.iterator(false? null: UtilDateTime.parse("10/03/2012", "MM/dd/yyyy"));
		for(int i=0; i<100; i++){
			System.out.println(UtilDateTime.format(iter.next(), "yyyy-MM-dd"));
		}

	}
	public static void main(String[] args) {
		Timing time = new Timing();
		
		CANADA selectedProviance = CountryHolidays.CANADA.ON;
		int selectedYear = 2013;
		
		Map<String,String> selectedYearHolidays = new HashMap<String,String>();
		
		Holidays holidays;
		
		for(Date date=UtilDateTime.getYearStart(selectedYear); UtilDateTime.getYear(date)==selectedYear; date=UtilDateTime.addDate(date, 1, 0, 0)){

			if( (holidays=selectedProviance.getHoliday(date)) != null && (holidays.contains(selectedProviance.title) || holidays.contains(Holiday.ALL_PROVIANCES)) ){
				selectedYearHolidays.put(selectedProviance.title+"-"+holidays.getTitle(), UtilDateTime.format(date, "MMM d, EEEE"));
			}

		}
		
		System.out.print(UtilString.alignLeft("Holiday for "+selectedProviance.title, 25));
		System.out.print(UtilString.alignLeft("Date in "+selectedYear, 17));
		CANADA[] proviance = CANADA.values();
		for(int i=0; i<proviance.length; i++){
			System.out.print(UtilString.alignCenter(proviance[i].title, 3));
		}
		
		System.out.println();
		
		for(int i=0; i<CountryHolidays.CANADA.holidayTitle.length; i++ ){
			System.out.print(UtilString.alignLeft(CountryHolidays.CANADA.holidayTitle[i], 25));
			System.out.print(UtilString.alignLeft(CountryHolidays.selectYearHoliday(selectedYearHolidays, selectedProviance, CountryHolidays.CANADA.holidayTitle[i]), 17));
			Set<String> holydays = CountryHolidays.getHolydayProviances(CountryHolidays.CANADA.holidayTitle[i], CountryHolidays.CANADA.HOLYDAYS);
			if( holydays.contains( Holiday.NON_OFFICIAL_HOLIDAY ) )
				System.out.print(Holiday.NON_OFFICIAL_HOLIDAY);
			else
				for(int j=0; j<proviance.length; j++){
					if( holydays.contains( Holiday.ALL_PROVIANCES ) || holydays.contains(proviance[j].title) )
						System.out.print(UtilString.alignCenter("*", 3));
					else
						System.out.print("   ");
				}
			System.out.println();
		}
		
		System.out.println(time.total());
	}

	
	public static void main1(String[] args) {
		Timing time = new Timing();
		
		CANADA proviance = CountryHolidays.CANADA.ON;
		
		Holidays holiday;
		for(Date date=UtilDateTime.getYearStart(2013); UtilDateTime.getYear(date)==2013; date=UtilDateTime.addDate(date, 1, 0, 0)){
			if( !(holiday=proviance.getHoliday(date)).holiday.isEmpty() ){
				System.out.println(UtilDateTime.formatDMDY(date)+" - "+holiday.getTitle());
			}
		}
		
		System.out.println(time.total());
	}

}












/*
2013 Statuory Holidays


Tue Jan 1, 2013 - New Year's Day
Thu Feb 14, 2013 - Valentine's Day
Mon Feb 18, 2013 - Family Day
Sun Mar 17, 2013 - St. Patrick's Day
Fri Mar 29, 2013 - Good Friday
Sun May 12, 2013 - Mother's Day
Mon May 20, 2013 - Victoria Day
Sun Jun 16, 2013 - Father's Day
Mon Aug 5, 2013 - Civic Holiday
Mon Oct 14, 2013 - Thanksgiving
Thu Oct 31, 2013 - Halloween
Wed Dec 25, 2013 - Christmas Day
Thu Dec 26, 2013 - Boxing Day
62 mls



2012/09/19
2012/09/26
2012/10/03
2012/10/10
2012/10/17

2012-10-03
2012-10-10
2012-10-17
2012-10-24
2012-10-31

*/



