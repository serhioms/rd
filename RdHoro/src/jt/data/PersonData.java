package jt.data;

import java.util.Calendar;
import java.util.Date;

public class PersonData extends HoroData {
	private String name;
	private boolean male;
	//private HoroData birthData;
	PersonData(String name, Date dt, boolean isMale) {
		super(dt);
		this.name =name;
		male = isMale;
		
	}
	
	public String  toString() {
		
		return name + " " + (male?"Male":"Female") + " " + super.toString() ;
	}
	public static void main(String args[]) {
		System.out.println("Hello ..");
		PersonData data = new PersonData("Dileep", new Date(),true);
		Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
		System.out.println(data.toString());
	}
}
