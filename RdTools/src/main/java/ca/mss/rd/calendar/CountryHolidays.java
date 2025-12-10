package ca.mss.rd.calendar;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import ca.mss.rd.calendar.impl.Holiday;
import ca.mss.rd.calendar.impl.Holidays;
import ca.mss.rd.calendar.impl.Proviance;
import ca.mss.rd.util.UtilDateTime;
import ca.mss.rd.util.UtilMisc;


public class CountryHolidays {
	
	final static public String NA = "N/A";

	public enum CANADA implements Proviance {
		BC("BC"),
		AB("AB"),
		SK("SK"),
		MB("MB"),
		ON("ON"),
		QC("QC"),
		NB("NB"),
		NS("NS"),
		PE("PE"),
		NL("NL"),
		YT("YT"),
		NT("NT"),
		NU("NU");
		
		// "BC","AB","SK","MB","ON","QC","NB","NS","PE","NL","YT","NT","NU"
		final public String title;
		
		private CANADA(String title) {
			this.title = title;
		}
		
		final public String getProvianceTitle(){
			return title;
		}
		
		final public String getCountryTitle(){
			return "Canada";
		}
		
		final static public String[] holidayTitle = new String[]{
			"New Year's Day",
			"Islander Day",
			"Louis Riel Day",
			"Family Day", 
			"Valentine's Day", 
			"St. Patrick's Day",
			"Good Friday", 
			"Easter Monday", 
			"Mother's Day", 
			"Victoria Day",
			"Father's Day", 
			"St. Jean Baptiste Day", 
			"Canada Day",
			"Civic Holiday", 
			"Labour Day", 
			"Thanksgiving",  
			"Halloween", 
			"Remembrance Day", 
			"Christmas Day", 
			"Boxing Day"
		};
		
		final public static Map<String, Holiday> HOLYDAYS = toHolidayMap(
				"1/1", Holiday.unconditional("New Year's Day", UtilMisc.toSet(Holiday.ALL_PROVIANCES)),
				"PE-2/18", Holiday.unconditional("Islander Day", UtilMisc.toSet("PE")),
				"MB-2/18", Holiday.unconditional("Louis Riel Day", UtilMisc.toSet("MB")),
				"2/Mon3", Holiday.unconditional("Family Day", UtilMisc.toSet("BC","AB","SK","ON")),
				"2/14", Holiday.unconditional("Valentine's Day", UtilMisc.toSet(Holiday.ALL_PROVIANCES, Holiday.NON_OFFICIAL_HOLIDAY)), 
				"3/17", Holiday.unconditional("St. Patrick's Day", UtilMisc.toSet(Holiday.ALL_PROVIANCES, Holiday.NON_OFFICIAL_HOLIDAY)),
				"Fri", Holiday.conditional("Good Friday", UtilMisc.toSet("BC","AB","SK","MB","ON","NB","NS","PE","NL","YT","NT","NU")).beforeEasterSunday(),
				"QC-Mon", Holiday.conditional("Easter Monday", UtilMisc.toSet("QC")).afterEasterSunday(), 
				"5/12", Holiday.unconditional("Mother's Day", UtilMisc.toSet(Holiday.ALL_PROVIANCES, Holiday.NON_OFFICIAL_HOLIDAY)),  
				"5/Mon", Holiday.conditional("Victoria Day", UtilMisc.toSet("BC","AB","SK","MB","ON","QC","YT","NT","NU")).beforeDayOfMonth(25),
				"6/16", Holiday.unconditional("Father's Day", UtilMisc.toSet(Holiday.ALL_PROVIANCES, Holiday.NON_OFFICIAL_HOLIDAY)),  
				"QC-6/Sn4", Holiday.unconditional(_tmp_title_="St. Jean Baptiste Day", UtilMisc.toSet("QC")), 
				"QC-6/Sn5", Holiday.unconditional(_tmp_title_, UtilMisc.toSet("QC")),
				"7/1",  Holiday.unconditional("Canada Day"),
				"8/Mon1", Holiday.unconditional("Civic Holiday", UtilMisc.toSet("BC","SK","MB","ON","NB","NU")), 
				"9/Mon1", Holiday.unconditional("Labour Day"), 
				"10/Mon2", Holiday.unconditional("Thanksgiving", UtilMisc.toSet("BC","AB","SK","MB","ON","QC","YT","NT","NU")), 
				"10/31", Holiday.unconditional("Halloween", UtilMisc.toSet(Holiday.ALL_PROVIANCES, Holiday.NON_OFFICIAL_HOLIDAY)), 
				"11/11", Holiday.unconditional("Remembrance Day", UtilMisc.toSet("BC","AB","SK","NB","PE","YT","NT","NU")), 
				"12/25", Holiday.unconditional("Christmas Day", UtilMisc.toSet(Holiday.ALL_PROVIANCES)), 
				"12/26", Holiday.unconditional("Boxing Day", UtilMisc.toSet("ON"))
				);

		final public Holidays getHoliday(Date date) {
			return CountryHolidays.getHoliday(date, HOLYDAYS, this);
		}

		final public boolean isHoliday(Date date) {
			return !CountryHolidays.getHoliday(date, HOLYDAYS, this).holiday.isEmpty();
		}
	};
	
		
	final static public Set<String> getHolydayProviances(String title, Map<String, Holiday> holidays){
		Iterator<Entry<String, Holiday>>  iter = holidays.entrySet().iterator();
		while( iter.hasNext() ){
			Entry<String, Holiday> ent = iter.next();
			if( title.equals( ent.getValue().title ) ){
				return ent.getValue().proviances;
			}
		}
		return Holiday.ZERO_SET_OF_HOLIDAY_PROVIANCES;
	}
	
	
	final static public Holidays getHoliday(Date date, Map<String, Holiday> holydays, Proviance proviance) {
		
		Holidays holidays = new Holidays();  
		
		/* ON- */
		String provTitle = proviance.getProvianceTitle()+"-";
		
		final Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		
		final int month = cal.get(Calendar.MONTH)+1;
		final int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
		
		/* 1/1 and ON-12/26, Canada Day */
		Holiday holiday;
		if( (holiday=holydays.get(provTitle+month+"/"+dayOfMonth)) != null && (holiday=holiday.check(date, proviance)) != null)
			holidays.holiday.add(holiday);
		if( (holiday=holydays.get(month+"/"+dayOfMonth)) != null && (holiday=holiday.check(date, proviance)) != null)
			holidays.holiday.add(holiday);
			
		final int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
		final String dayInWeek = UtilDateTime.getDay(dayOfWeek);
		final String monthDayInWeek = month+"/"+dayInWeek;
		final int dayOfWeekCounter = UtilDateTime.getDayOfWeekCounter(dayOfMonth);
		
		/* ON-2/Mon3, 9/Mon1, Family Day, etc */
		if( (holiday=holydays.get(provTitle+monthDayInWeek+dayOfWeekCounter)) != null && (holiday=holiday.check(date, proviance)) != null)
			holidays.holiday.add(holiday);
		if( (holiday=holydays.get(monthDayInWeek+dayOfWeekCounter)) != null && (holiday=holiday.check(date, proviance)) != null)
			holidays.holiday.add(holiday);

		/* Victoria Day */
		if( (holiday=holydays.get(provTitle+monthDayInWeek)) != null && (holiday=holiday.check(date, proviance)) != null)
			holidays.holiday.add(holiday);
		if( (holiday=holydays.get(monthDayInWeek)) != null && (holiday=holiday.check(date, proviance)) != null)
			holidays.holiday.add(holiday);
		
		/* Good Friday, Easter Monday */
		if( (holiday=holydays.get(provTitle+dayInWeek)) != null && (holiday=holiday.check(date, proviance)) != null)
			holidays.holiday.add(holiday);
		if( (holiday=holydays.get(dayInWeek)) != null && (holiday=holiday.check(date, proviance)) != null)
			holidays.holiday.add(holiday);

		return holidays;
	}

	/* FOR PEACE */
	private static String _tmp_title_;
	
	public static String selectYearHoliday(Map<String,String> selectedYearHolidays, CANADA selectedProviance, String holidayTitle){
		String selectYearHoliday;
		if( (selectYearHoliday=selectedYearHolidays.get(selectedProviance.title+"-"+holidayTitle)) != null )
			return selectYearHoliday;
		return ""; //throw new RuntimeException("Can not find holiday date for "+holidayTitle);
	}
	
	static public Map<String, Holiday> toHolidayMap(Object... args) {
		Map<String, Holiday> map = new HashMap<String, Holiday>();

		for (int i = 0; i < args.length; i += 2) {
			map.put(args[i + 0].toString(), (Holiday )args[i + 1]);
		}

		return map;
	}

	
}

