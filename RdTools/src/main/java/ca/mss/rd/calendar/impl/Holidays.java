package ca.mss.rd.calendar.impl;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


public class Holidays {
	final static public String className = Holidays.class.getName();
	final static public long serialVersionUID = className.hashCode();
	
	final public Set<Holiday> holiday = new HashSet<Holiday>();
	
	final public boolean contains(String proviance){
		for(Iterator<Holiday> iter=holiday.iterator(); iter.hasNext(); ){
			if( iter.next().proviances.contains(proviance) ){
				return true; 
			}
		}
		return false;
	}
	
	final public String getTitle(){
		String title = "";
		Set<Holiday> hash = new HashSet<Holiday>();
		for(Iterator<Holiday> iter=holiday.iterator(); iter.hasNext(); ){
			Holiday holiday = iter.next();
			if( !hash.contains(holiday) ){
				hash.add(holiday);
				if( title.length() == 0 )
					title = holiday.title;
				else
					title = title + "," +holiday.title;
			}
		}
		return title;
	}
}


