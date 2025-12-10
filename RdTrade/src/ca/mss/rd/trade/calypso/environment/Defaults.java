/**
 * 
 */
package ca.mss.rd.trade.calypso.environment;

import java.util.ArrayList;
import java.util.List;

import ca.mss.rd.trade.calypso.boprocess.mom.Receiver;
import ca.mss.rd.trade.calypso.refdata.Currency;
import ca.mss.rd.trade.calypso.refdata.DateCalculator;
import ca.mss.rd.trade.calypso.refdata.DateRoll;
import ca.mss.rd.trade.calypso.refdata.HolidayCalendar;
import ca.mss.rd.trade.calypso.refdata.Method;
import ca.mss.rd.trade.calypso.refdata.SDFilter;
import ca.mss.rd.trade.calypso.refdata.Time;
import ca.mss.rd.trade.calypso.refdata.Timezoon;
import ca.mss.rd.trade.value.Value;
import ca.mss.rd.trade.value.ValueBoolean;


/**
 * @author moskovsk
 *
 */
public class Defaults {

	final static public String className = Defaults.class.getName();
	final static public long serialVersionUID = className.hashCode();

	final static public List<HolidayCalendar> getHolidays(){
		List<HolidayCalendar> holidays = new ArrayList<HolidayCalendar>();
		
		holidays.add(new HolidayCalendar(HolidayCalendar.CityCode.NYC));
		holidays.add(new HolidayCalendar(HolidayCalendar.CityCode.EUR));
		
		return holidays;
	}
	
	final static public DateRoll getDateRoll(){
		return DateRoll.NO_CHANGE;
	}

	final static public Timezoon getTimezoon(){
		return new Timezoon();
	}

	final static public SDFilter getSDFilter(){
		return new SDFilter();
	}

	final static public Currency getCurrency(){
		return new Currency();
	}

	final static public Receiver getReceiver(){
		return new Receiver("ABCBUSS33");
	}
	
	final static public Method getMethod(){
		return Method.ALL;
	}

	final static public DateCalculator getDateCalculator(){
		return DateCalculator.NONE;
	}

	final static public ValueBoolean isCheckHoliday (){
		return new ValueBoolean(true);
	}

	final static public ValueBoolean isAbsoluteTime (){
		return new ValueBoolean(false);
	}

	final static public ValueBoolean noCutOff (){
		return new ValueBoolean(true);
	}

	final static public Value getKickOffDaysLag(){
		return new Value(-2);
	}

	final static public Value getKickOffTimeHH(){
		return new Value(Time.H16);
	}

	final static public Value getKickOffTimeMM(){
		return new Value(Time.minutes(0));
	}

	final static public Value getScanFrequencyHH(){
		return new Value(Time.hour(0));
	}

	final static public Value getScanFrequencyMM(){
		return new Value(Time.minutes(0));
	}
	
}




