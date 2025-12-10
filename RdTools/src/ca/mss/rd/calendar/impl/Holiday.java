package ca.mss.rd.calendar.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import ca.mss.rd.util.UtilDateTime;

public class Holiday {
	final static public String className = Holiday.class.getName();
	final static public long serialVersionUID = className.hashCode();
	
	final static public String ALL_PROVIANCES = "ALL_PROVIANCES";
	final static public String NON_OFFICIAL_HOLIDAY = "Not an official holiday";
	final static public Set<String> ZERO_SET_OF_HOLIDAY_PROVIANCES = new HashSet<String>();

	final static public int NA = -1;

	final public String title;
	final public boolean isUnconditional;
	
	private boolean before;
	private boolean easterSunday;
	private int dayOfMonth = NA;
	
	final public Set<String> proviances;
	
	private Holiday(String title, boolean isUnconditional, Set<String> proviances) {
		this.title = title;
		this.isUnconditional = isUnconditional;
		this.proviances = proviances;
	}

	final public Holiday beforeEasterSunday(){
		this.before = true;
		this.easterSunday = true;
		return this;
	}
	
	final public Holiday afterEasterSunday(){
		this.before = false;
		this.easterSunday = true;
		return this;
	}
	
	final public Holiday beforeDayOfMonth(int dayOfMonth){
		this.before = true;
		this.dayOfMonth = dayOfMonth;
		return this;
	}
	
	final public Holiday afterDayOfMonth(int dayOfMonth){
		this.before = false;
		this.dayOfMonth = dayOfMonth;
		return this;
	}
	
	final private Holiday checkProviance(Proviance proviance){
		if( proviances.contains(ALL_PROVIANCES) )
			return this;
		else if( proviances.contains(proviance.getProvianceTitle()) )
			return this;
		else if( proviances.contains(NON_OFFICIAL_HOLIDAY) )
			return this;
		else 
			return null;
	}
	
	
	final public Holiday check(Date date, Proviance proviance){
		if( isUnconditional )
			return checkProviance(proviance);
		else if( isTrue(date) )
			return checkProviance(proviance);
		else 
			return null;
	}
	
	final public Holiday check(Date date){
		if( isUnconditional )
			return this;
		else if( isTrue(date) )
			return this;
		else 
			return null;
	}
	
	private int dom;
	final public boolean isTrue(Date date){
		if( dayOfMonth != NA ){
			if( before )
				return (dom=dayOfMonth - UtilDateTime.getDayOfMonth(date)) >=0 && dom <=6;
			else
				return (dom=UtilDateTime.getDayOfMonth(date) - dayOfMonth) >=0 && dom <=6;
				
		} else if( easterSunday ){
			if( before )
				return (dom=UtilDateTime.getEasterSunday(UtilDateTime.getYear(date)) - UtilDateTime.getDayOfYear(date)) >=0 && dom <=6;
			else
				return (dom=UtilDateTime.getDayOfYear(date) - UtilDateTime.getEasterSunday(UtilDateTime.getYear(date))) >=0 && dom <=6;
					
		} else
			return false;
	}
	
	final static public Holiday unconditional(String title){
		return new Holiday(title, true, ZERO_SET_OF_HOLIDAY_PROVIANCES);
	}
	
	final static public Holiday unconditional(String title, Set<String> proviances){
		return new Holiday(title, true, proviances);
	}
	
	final static public Holiday conditional(String title){
		return new Holiday(title, false, ZERO_SET_OF_HOLIDAY_PROVIANCES);
	}
	
	final static public Holiday conditional(String title, Set<String> proviances){
		return new Holiday(title, false, proviances);
	}
	
}


