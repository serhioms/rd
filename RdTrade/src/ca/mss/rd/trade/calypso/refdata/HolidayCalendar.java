/**
 * 
 */
package ca.mss.rd.trade.calypso.refdata;


/**
 * @author moskovsk
 * 
 * They are used throughout the system for determining business days. A holiday 
 * calendar is identified by its city code throughout the system
 *
 */
public class HolidayCalendar {

	final static public String className = HolidayCalendar.class.getName();
	final static public long serialVersionUID = className.hashCode();

	public CityCode cityCode;
	
	
	/**
	 * @param cityCode
	 */
	public HolidayCalendar(CityCode cityCode) {
		this.cityCode = cityCode;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return cityCode.name();
	}


	public enum CityCode {
		NYC,
		EUR;
	}
	
	
	
}


