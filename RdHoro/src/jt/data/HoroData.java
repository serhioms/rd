package jt.data;

import java.util.Date;


/**
 * @author  Dileep Bapat
 * @description 
 * HoroData is basic data needed for horoscope calculation like date and time with place.
 */
public class HoroData {

	/**
	 * @param args
	 */
	private Date date;
	private Degree longitude;
	private Degree lattitude;
	
	public HoroData(Date horoDate){
		date = horoDate;
		longitude = new Degree(75.01528);
		lattitude = new Degree(14.34584);
	}
	
	public HoroData(Date horoDate, Degree lat, Degree lon){
		date = horoDate;
		this.lattitude = lat;
		this.longitude = lon;
	}
	
	public String toString () {
		
		return "Date and Time : "+ date.toString() + " Longitude :" + longitude + " Lattitude :" + lattitude ;
	}
	public static void main(String[] args) {
		System.out.println(new HoroData(new Date()).toString());

	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Degree getLattitude() {
		return lattitude;
	}

	public void setLattitude(Degree lattitude) {
		this.lattitude = lattitude;
	}

	public Degree getLongitude() {
		return longitude;
	}

	public void setLongitude(Degree longitude) {
		this.longitude = longitude;
	}

}
