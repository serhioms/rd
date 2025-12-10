package ca.mss.rd.util;

import java.text.NumberFormat;
import java.util.Date;

public class UtilFormat {

	final static public int SCALE;
	final static public NumberFormat NUMBER_FORMATTER;

	final static public double ZERO = 0.0D;
	final static public int ZERO_EXTRA = 0;
	final static public int PERCENT_100 = 100;

	static {
		SCALE = 2;
		NUMBER_FORMATTER = NumberFormat.getInstance();
		NUMBER_FORMATTER.setMaximumFractionDigits(SCALE);
		NUMBER_FORMATTER.setMinimumFractionDigits(SCALE);
	}

	
	final static public String format(String s){
		return s;
	}
	
	final static public String format(int n){
		return Integer.toString(n);
	}
	
	final static public String format(Date d){
		return UtilDateTime.formatYMD(d);
	}

	public static final String format(double d){
		return NUMBER_FORMATTER.format(d);
	}

}
