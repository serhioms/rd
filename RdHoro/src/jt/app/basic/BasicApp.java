package jt.app.basic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

import jt.Horoscope;
import jt.data.Degree;
import jt.data.HoroData;

public class BasicApp {

	public static void main(String args[]) {

		Date dt = null;
		HoroData horoData = null ;
		if (args.length > 0 && args[0].equals("quite")) {
			//Current horoscope for bangalore
			horoData = new HoroData(new Date(), new Degree(12.96), new Degree(77.77));
		}
		else {
			BufferedReader in = new BufferedReader(new InputStreamReader(
					System.in));
			SimpleDateFormat dateFormat = new SimpleDateFormat(
					"dd/mm/yyyy hh:mm");
			do {
				try {
					System.out.println("Enter Date and time in "
							+ dateFormat.toPattern() + " format:");
					String dateStr = in.readLine();
					dt = dateFormat.parse(dateStr);
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			} while (dt == null);
			System.out.println("Date " + dt);
			double lat, lon;
			String s = null;
			System.out.println("Enter latitude:");
			do {
				try {
					s = in.readLine();
				} catch (IOException e) {
					System.out.println(e.getMessage());
				}
			} while (s == null);
			lat = Double.parseDouble(s);
			s = null;
			System.out.println("Enter longitude:");
			do {
				try {
					s = in.readLine();
				} catch (IOException e) {
					System.out.println(e.getMessage());
				}
			} while (s == null);
			lon = Double.parseDouble(s);
			Degree lt = new Degree(lat);
			Degree ln = new Degree(lon);
			System.out.println("Lat : " + lt.toString() + " Lon : "
					+ ln.toString());
			horoData = new HoroData(dt, lt, ln);
		}
		Horoscope hr = new Horoscope(horoData);
		hr.Calculate();
		
		
		System.out.println(hr.toString());
	}
}
