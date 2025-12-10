/**
 * 
 */
package ca.mss.rd.trade.value;

import java.util.List;

import ca.mss.rd.trade.calypso.boprocess.mom.Receiver;
import ca.mss.rd.trade.calypso.environment.Defaults;
import ca.mss.rd.trade.calypso.refdata.Currency;
import ca.mss.rd.trade.calypso.refdata.DateCalculator;
import ca.mss.rd.trade.calypso.refdata.DateRoll;
import ca.mss.rd.trade.calypso.refdata.HolidayCalendar;
import ca.mss.rd.trade.calypso.refdata.Method;
import ca.mss.rd.trade.calypso.refdata.SDFilter;
import ca.mss.rd.trade.calypso.refdata.Timezoon;

/**
 * @author moskovsk
 *
 */
public class ValueKickOffCutOff extends ValueBoolean {

	final static public String className = ValueKickOffCutOff.class.getName();
	final static public long serialVersionUID = className.hashCode();

	public Timezoon timezoon;
	public List<HolidayCalendar> holidays;
	public DateRoll dateRoll;
	public SDFilter sdFilter;
	public Currency currency;
	public Receiver receiver;
	public Method method;
	public DateCalculator dateCalculator;
	public ValueBoolean checkHoliday, absoluteTime, noCutOff;
	
	public Value kickOffDaysLag, kickOffTimeHH, kickOffTimeMM, scanFrequencyHH, scanFrequencyMM;

	/**
	 * 
	 */
	public ValueKickOffCutOff() {
		super(true);
		this.timezoon = Defaults.getTimezoon();
		this.holidays = Defaults.getHolidays();
		this.dateRoll = Defaults.getDateRoll();
		this.sdFilter = Defaults.getSDFilter();
		this.currency = Defaults.getCurrency();
		this.receiver = Defaults.getReceiver();
		this.method = Defaults.getMethod();
		this.dateCalculator = Defaults.getDateCalculator();
		this.checkHoliday = Defaults.isCheckHoliday();
		this.absoluteTime = Defaults.isAbsoluteTime();
		this.noCutOff = Defaults.noCutOff();
		this.kickOffDaysLag = Defaults.getKickOffDaysLag();
		this.kickOffTimeHH = Defaults.getKickOffTimeHH();
		this.kickOffTimeMM = Defaults.getKickOffTimeMM();
		this.scanFrequencyHH = Defaults.getScanFrequencyHH();
		this.scanFrequencyMM = Defaults.getScanFrequencyMM();
	}

}



