package ca.mss.rd.util;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.xml.datatype.XMLGregorianCalendar;

import com.sun.org.apache.xerces.internal.jaxp.datatype.XMLGregorianCalendarImpl;

/**
 * @author moskovsk
 * 
 */
public class UtilDateTime {

	final public static String className = UtilDateTime.class.getName();
	final public static long serialVersionUID = className.hashCode();

	public static String RIGHT_NOW_FORMAT = "kk:mm:ss.SSS";
	public static String NOW_FORMAT = "kk:mm:ss";
	public static String DATE_FORMAT_DEFAULT = "z MMM dd, yyyy kk:mm:ss";
	public static String TIME_FORMAT_DEFAULT = "kk:mm:ss";

	final public static String DATE_FORMAT_YYYYMMDD = "yyyy/MM/dd";
	final public static String DATE_FORMAT_MMDDYYY = "MMM d, yyyy";
	final public static String DATE_FORMAT_DMMDDYYY = "E MMM d, yyyy";

	public static final Date parse(String date, String format) {
		try {
			SimpleDateFormat df = new SimpleDateFormat(format);
			return df.parse(date);
		} catch (Exception e) {
			throw new RuntimeException("Can not parse date [" + date + "] as [" + format + "]");
		}
	}

	public static final Date parse(String date) {
		return UtilDateTime.parse(date, DATE_FORMAT_DEFAULT);
	}

	public static final String formatDate(Date date) {
		return format(date, DATE_FORMAT_DEFAULT);
	}

	public static final String formatTime(Date date) {
		return format(date, TIME_FORMAT_DEFAULT);
	}

	public static final String format(Date date, String format) {
		if( date == null )
			throw new RuntimeException("Can not format [null] date.");
		SimpleDateFormat df = new SimpleDateFormat(format);
		return df.format(date);
	}

	public static final String format(String date, String format) {
		SimpleDateFormat df = new SimpleDateFormat(format);
		return df.format(parse(date, DATE_FORMAT_YYYYMMDD));
	}

	public static final String formatYMD(Date date) {
		return format(date, DATE_FORMAT_YYYYMMDD);
	}

	public static final String formatMDY(Date date) {
		return format(date, DATE_FORMAT_MMDDYYY);
	}

	public static final String formatDMDY(Date date) {
		return format(date, DATE_FORMAT_DMMDDYYY);
	}

	public static final Date setDay(Date date, int day) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH, day);
		return cal.getTime();
	}

	public static final Date setYear(Date date, int year) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.YEAR, year);
		return cal.getTime();
	}

	public static final Date setTime(Date date, int hour, int mm) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, hour);
		cal.set(Calendar.MINUTE, mm);
		return cal.getTime();
	}

	public static final Date setSecond(Date date, int v) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.SECOND, v);
		return cal.getTime();
	}

	public static final Date setMillisecond(Date date, int v) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.MILLISECOND, v);
		return cal.getTime();
	}

	public static final Date addDate(Date day, int daysLater, int monthsLater, int yearsLater) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(day);
		cal.add(Calendar.YEAR, yearsLater);
		cal.add(Calendar.MONTH, monthsLater);
		cal.add(Calendar.DAY_OF_MONTH, daysLater);
		return cal.getTime();
	}

	public static final Date addTime(Date day, int hhLater, int mmLater, int ssLater) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(day);
		cal.add(Calendar.HOUR_OF_DAY, hhLater);
		cal.add(Calendar.MINUTE, mmLater);
		cal.add(Calendar.SECOND, ssLater);
		return cal.getTime();
	}

	public static final int getDayOfMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.DAY_OF_MONTH);
	}

	public static final int getDayOfWeek(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.DAY_OF_WEEK);
	}

	public static final int getHour(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.HOUR_OF_DAY);
	}

	public static final int getMinute(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.MINUTE);
	}

	public static final int getSecond(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.SECOND);
	}

	public static final int getQuoter(Date date) {
		return getMonth(date) / 3 + 1;
	}

	public static final int getDayOfYear(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.DAY_OF_YEAR);
	}

	public static final int getWeek(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.WEEK_OF_YEAR);
	}

	public static final int getWeekOfMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.WEEK_OF_MONTH);
	}

	public static final int getBiWeek(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return (cal.get(Calendar.WEEK_OF_MONTH) - 1) / 2 + 1;

	}

	public static final int getSemiMo(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.DAY_OF_MONTH) < 15 ? 1 : 2;

	}

	public static final int getSemiYear(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.DAY_OF_YEAR) <= 177 ? 1 : 2;

	}

	public static final int getMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.MONTH);
	}

	public static final int getYear(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.YEAR);
	}

	final static public boolean isSameYear(Date d1, Date d2) {
		if( d1 == null || d2 == null )
			return false;

		Calendar c1 = Calendar.getInstance();
		c1.setTime(d1);

		Calendar c2 = Calendar.getInstance();
		c2.setTime(d2);

		if (c1.get(Calendar.YEAR) != c2.get(Calendar.YEAR))
			return false;

		return true;
	}

	final static public boolean isSameHour(Date d1, Date d2) {
		if( d1 == null || d2 == null )
			return false;

		Calendar c1 = Calendar.getInstance();
		c1.setTime(d1);

		Calendar c2 = Calendar.getInstance();
		c2.setTime(d2);

		if (c1.get(Calendar.YEAR) != c2.get(Calendar.YEAR))
			return false;

		if (c1.get(Calendar.DAY_OF_YEAR) != c2.get(Calendar.DAY_OF_YEAR))
			return false;

		if (c1.get(Calendar.HOUR_OF_DAY) != c2.get(Calendar.HOUR_OF_DAY))
			return false;

		return true;
	}

	final static public boolean isSameSec(Date d1, Date d2) {
		if( d1 == null || d2 == null )
			return false;

		Calendar c1 = Calendar.getInstance();
		c1.setTime(d1);

		Calendar c2 = Calendar.getInstance();
		c2.setTime(d2);

		if (c1.get(Calendar.YEAR) != c2.get(Calendar.YEAR))
			return false;

		if (c1.get(Calendar.DAY_OF_YEAR) != c2.get(Calendar.DAY_OF_YEAR))
			return false;

		if (c1.get(Calendar.HOUR_OF_DAY) != c2.get(Calendar.HOUR_OF_DAY))
			return false;

		if (c1.get(Calendar.MINUTE) != c2.get(Calendar.MINUTE))
			return false;

		if (c1.get(Calendar.SECOND) != c2.get(Calendar.SECOND))
			return false;

		return true;
	}

	final static public boolean isSameMinute(Date d1, Date d2) {
		if( d1 == null || d2 == null )
			return false;

		Calendar c1 = Calendar.getInstance();
		c1.setTime(d1);

		Calendar c2 = Calendar.getInstance();
		c2.setTime(d2);

		if (c1.get(Calendar.YEAR) != c2.get(Calendar.YEAR))
			return false;

		if (c1.get(Calendar.DAY_OF_YEAR) != c2.get(Calendar.DAY_OF_YEAR))
			return false;

		if (c1.get(Calendar.HOUR_OF_DAY) != c2.get(Calendar.HOUR_OF_DAY))
			return false;

		if (c1.get(Calendar.MINUTE) != c2.get(Calendar.MINUTE))
			return false;

		return true;
	}
	
	final static public boolean isSameDay(Date d1, Date d2) {
		if( d1 == null || d2 == null )
			return false;

		Calendar c1 = Calendar.getInstance();
		c1.setTime(d1);

		Calendar c2 = Calendar.getInstance();
		c2.setTime(d2);

		if (c1.get(Calendar.YEAR) != c2.get(Calendar.YEAR))
			return false;

		if (c1.get(Calendar.DAY_OF_YEAR) != c2.get(Calendar.DAY_OF_YEAR))
			return false;

		return true;
	}

	final static public boolean isSameQuoter(Date d1, Date d2) {
		if( d1 == null || d2 == null )
			return false;

		Calendar c1 = Calendar.getInstance();
		c1.setTime(d1);

		Calendar c2 = Calendar.getInstance();
		c2.setTime(d2);

		if (c1.get(Calendar.YEAR) != c2.get(Calendar.YEAR))
			return false;

		if (c1.get(Calendar.MONTH) / 3 != c2.get(Calendar.MONTH) / 3)
			return false;

		return true;
	}

	final static public boolean isSameMonth(Date d1, Date d2) {
		if( d1 == null || d2 == null )
			return false;
		
		Calendar c1 = Calendar.getInstance();
		c1.setTime(d1);

		Calendar c2 = Calendar.getInstance();
		c2.setTime(d2);

		if (c1.get(Calendar.YEAR) != c2.get(Calendar.YEAR))
			return false;

		if (c1.get(Calendar.MONTH) != c2.get(Calendar.MONTH))
			return false;

		return true;
	}

	final static public boolean isSameWeek(Date d1, Date d2) {
		if( d1 == null || d2 == null )
			return false;

		Calendar c1 = Calendar.getInstance();
		c1.setTime(d1);

		Calendar c2 = Calendar.getInstance();
		c2.setTime(d2);

		if (c1.get(Calendar.YEAR) != c2.get(Calendar.YEAR))
			return false;

		if (c1.get(Calendar.WEEK_OF_YEAR) != c2.get(Calendar.WEEK_OF_YEAR))
			return false;

		return true;
	}

	public static final Date now() {
		return new Date();
	}

	public static String rightnow() {
		return format(now(), RIGHT_NOW_FORMAT);
	}

	public static String now(String format) {
		return format(now(), format);
	}

	public static String rightnow(Date time) {
		return format(time, RIGHT_NOW_FORMAT);
	}

	public static String now(Date time) {
		return format(time, NOW_FORMAT);
	}


	public static final Date getDayStart(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	public static final Date getDayStart(Date day, int daysLater, int monthsLater, int yearsLater) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(day);
		cal.add(Calendar.YEAR, yearsLater);
		cal.add(Calendar.MONTH, monthsLater);
		cal.add(Calendar.DAY_OF_MONTH, daysLater);
		return cal.getTime();
	}
	
	public Date getDayEnd(Date date) {
	    Calendar calendar = Calendar.getInstance();
	    calendar.setTime(date);
	    calendar.set(Calendar.HOUR_OF_DAY, 23);
	    calendar.set(Calendar.MINUTE, 59);
	    calendar.set(Calendar.SECOND, 59);
	    calendar.set(Calendar.MILLISECOND, 999);
	    return calendar.getTime();
	}
	public static final Date getYearStart(Date date, int daysLater, int monthsLater, int yearsLater) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(cal.get(Calendar.YEAR), Calendar.JANUARY, 1, 0, 0, 0);
		cal.add(Calendar.YEAR, yearsLater);
		cal.add(Calendar.MONTH, monthsLater);
		cal.add(Calendar.DAY_OF_MONTH, daysLater);
		return cal.getTime();
	}

	public static final Date getYearStart(int year) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, Calendar.JANUARY);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		return cal.getTime();
	}

	public static final Date getYearStart(Date date) {
		return getYearStart(date, 0, 0, 0);
	}

	public static final long substruct(Date from, Date thru) {
		return from.getTime() - thru.getTime();
	}

	public static final boolean before(Date from, Date thru) {
		return substruct(from, thru) < 0;
	}

	public static final boolean beforeEqual(Date from, Date thru) {
		return substruct(from, thru) <= 0;
	}

	public static final boolean after(Date from, Date thru) {
		return substruct(from, thru) > 0;
	}

	public static final boolean afterEqual(Date from, Date thru) {
		return substruct(from, thru) >= 0;
	}

	public static final boolean isMn2Fr(Date date) {
		switch (UtilDateTime.getDayOfWeek(date)) {
		case Calendar.MONDAY:
		case Calendar.TUESDAY:
		case Calendar.WEDNESDAY:
		case Calendar.THURSDAY:
		case Calendar.FRIDAY:
			return true;
		}
		return false;
	}

	public static final boolean isWeekEnd(Date date) {
		switch (UtilDateTime.getDayOfWeek(date)) {
		case Calendar.SATURDAY:
		case Calendar.SUNDAY:
			return true;
		}
		return false;
	}

	public static final int getDayOfWeekCounter(int dayOfMonth) {
		return dayOfMonth / 7 + (dayOfMonth % 7 > 0 ? 1 : 0);
	}

	public static final String getDay(int day) {
		switch (day) {
		case Calendar.MONDAY:
			return "Mon";
		case Calendar.TUESDAY:
			return "Tue";
		case Calendar.WEDNESDAY:
			return "Wed";
		case Calendar.THURSDAY:
			return "Thu";
		case Calendar.FRIDAY:
			return "Fri";
		case Calendar.SATURDAY:
			return "Sat";
		case Calendar.SUNDAY:
			return "Sun";
		}
		return "";
	}

	/**
	 * Computes the date of Easter Sunday. The algorithm used was invented by
	 * the mathematician Carl Friedrich Gauss in 1800. Let y be the year (such
	 * as 1800 or 2001).
	 */
	public static final int getEasterSunday(int year) {
		// Divide y by 19 and call the remainder a. Ignore the quotient.
		int a = year % 19;
		// Divide y by 100 to get a quotient b and a remainder c.
		int b = year / 100;
		int c = year % 100;
		// Divide b by 4 to get a quotient d and a remainder e.
		int d = b / 4;
		int e = b % 4;
		// Divide 8 * b + 13 by 25 to get a quotient g. Ignore the remainder.
		int g = (8 * b + 13) / 25;
		// Divide 19 * a + b - d - g + 15 by 30 to get a remainder h. Ignore the
		// quotient.
		int h = (19 * a + b - d - g + 15) % 30;
		// Divide c by 4 to get a quotient j and a remainder k.
		int j = c / 4;
		int k = c % 4;
		// Divide a + 11 * h by 319 to get a quotient m. Ignore the remainder.
		int m = (a + 11 * h) / 319;
		// Divide 2 * e + 2 * j - k - h + m + 32 by 7 to get a remainder r.
		// Ignore the quotient.
		int r = (2 * e + 2 * j - k - h + m + 32) % 7;
		// Divide h - m + r + 90 by 25 to get a quotient n. Ignore the
		// remainder.
		int month = (h - m + r + 90) / 25;
		// Divide h - m + r + n + 19 by 32 to get a remainder p.
		int day = (h - m + r + month + 19) % 32;

		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month - 1);
		cal.set(Calendar.DAY_OF_MONTH, day);

		return cal.get(Calendar.DAY_OF_YEAR);
	}

	final static public XMLGregorianCalendar toXMLGregorianCalendar(Date date) {
		XMLGregorianCalendar gc = new XMLGregorianCalendarImpl();

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		gc.setYear(cal.get(Calendar.YEAR));
		gc.setMonth(cal.get(Calendar.MONTH) + 1);
		gc.setDay(cal.get(Calendar.DAY_OF_MONTH));
		gc.setHour(cal.get(Calendar.HOUR_OF_DAY));
		gc.setMinute(cal.get(Calendar.MINUTE));
		gc.setSecond(cal.get(Calendar.SECOND));
		gc.setMillisecond(cal.get(Calendar.MILLISECOND));

		return gc;
	}

	final static public Date toDate(XMLGregorianCalendar date) {
		return date.toGregorianCalendar().getTime();
	}

	public static final int getDaysInYear(int year) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		return cal.getActualMaximum(Calendar.DAY_OF_YEAR);
	}

	public static final int getDays(Date date1, Date date2) {
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date1);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(date2);
		int year1 = cal1.get(Calendar.YEAR), year2 = cal2.get(Calendar.YEAR);

		if (year1 == year2)
			return cal1.get(Calendar.DAY_OF_YEAR) - cal2.get(Calendar.DAY_OF_YEAR);

		int days = 0;
		if (year1 > year2) {
			days = getDaysInYear(year2) - cal2.get(Calendar.DAY_OF_YEAR);
			days += cal1.get(Calendar.DAY_OF_YEAR);
			for (int i = year2 + 1; i < year1; i++) {
				days += getDaysInYear(i);
			}
			return days;
		} else {
			days = getDaysInYear(year2) - cal2.get(Calendar.DAY_OF_YEAR);
			days += cal1.get(Calendar.DAY_OF_YEAR);
			for (int i = year1 + 1; i < year2; i++) {
				days += getDaysInYear(i);
			}
			return -days;
		}

	}

	public static final Date skipWeekends(Date date, int direction) {
		Calendar tempCal = Calendar.getInstance();
		tempCal.setTime(date);
		for (int d = tempCal.get(Calendar.DAY_OF_WEEK); 
				d == Calendar.SATURDAY || d == Calendar.SUNDAY; 
				d = tempCal.get(Calendar.DAY_OF_WEEK)) {
			tempCal.add(Calendar.DAY_OF_MONTH, direction);
		}
		return tempCal.getTime();

	}

	public static final Date getMonthdayForSpecifiedWeekday(Date date, int weekNumber, int dayOfWeek) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		int currDayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
		if (dayOfWeek < currDayOfWeek)
			cal.add(Calendar.DAY_OF_MONTH, weekNumber * 7 + dayOfWeek - currDayOfWeek);
		else
			cal.add(Calendar.DAY_OF_MONTH, (weekNumber - 1) * 7 + dayOfWeek - currDayOfWeek);
		return cal.getTime();
	}

	public static final Date getNearestMonth(Date date, Integer[] monthes) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int curMonth = cal.get(Calendar.MONTH);
		for (int year = 0; year < 10; year++) {
			for (int mo = 0; mo < monthes.length; mo++) {
				if (monthes[mo] >= curMonth || year > 0) {
					cal.set(Calendar.MONTH, monthes[mo]);
					return cal.getTime();
				}
			}
			cal.add(Calendar.YEAR, 1);
		}
		throw new RuntimeException("Can not fine nearest month for date [" + date + "] in ["
				+ UtilMisc.toString(monthes, ",", "") + "] monthes");
	}

	
	/*
	 * DEPRICATED
	 */
	
    public static java.util.Date toDate(String monthStr, String dayStr, String yearStr, String hourStr,
            String minuteStr, String secondStr) {
        int month, day, year, hour, minute, second;

        try {
            month = Integer.parseInt(monthStr);
            day = Integer.parseInt(dayStr);
            year = Integer.parseInt(yearStr);
            hour = Integer.parseInt(hourStr);
            minute = Integer.parseInt(minuteStr);
            second = Integer.parseInt(secondStr);
        } catch (Exception e) {
            return null;
        }
        return toDate(month, day, year, hour, minute, second);
    }
	
    public static java.util.Date toDate(int month, int day, int year, int hour, int minute, int second) {
        Calendar calendar = Calendar.getInstance();

        try {
            calendar.set(year, month - 1, day, hour, minute, second);
        } catch (Exception e) {
            return null;
        }
        return new java.util.Date(calendar.getTime().getTime());
    }

    public static java.sql.Timestamp nowTimestamp() {
        return new java.sql.Timestamp(System.currentTimeMillis());
    }

    public static Calendar toCalendar(Timestamp stamp) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(stamp.getTime());
        return cal;
    }

	// format long as mls, sec, min, h, d
	
	private static final double SEC = 1000.0;
	private static final double MIN = SEC * 60.0;
	private static final double HOUR = MIN * 60.0;
	private static final double DAY = HOUR * 24.0;
	private static final double MO = DAY * 30.35;
	private static final double YEAR = DAY*364.0;
	private static final double CENT = YEAR*100.0;

	public static String formatTime(final long value){
		return formatTime((double )value);
	}

	public static String formatTime(final double value){
	    final double[] dividers = new double[] { CENT, YEAR, MO, DAY, HOUR, MIN, SEC, 1 };
	    final String[] units = new String[] { "c", "y", "mo", "d", "h", "min", "sec", "mls" };
	    if(value < 1)
	        return "0 mls";
	    String result = null;
	    for(int i = 0; i < dividers.length; i++){
	        final double divider = dividers[i];
	        if(value >= divider){
	            result = _format(value, divider, units[i]);
	            break;
	        }
	    }
	    return result;
	}

	private static String _format(final double value,
	    final double divider,
	    final String unit){
	    final double result =
	        divider > 1 ? value / divider : value;
	    return new DecimalFormat("##0.#").format(result) + " " + unit;
	}

	public static String stopWatch(long start) {
		return formatTime(System.currentTimeMillis()-start);
	}

	public static String stopWatch(long start, int size) {
		return String.format("%s (1 in %s)", stopWatch(start), formatTime((System.currentTimeMillis()-start)/size));
	}

	public static long startWatch() {
		return System.currentTimeMillis();
	}
}