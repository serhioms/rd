package com.prosperica.common;

import java.text.NumberFormat;


public class CommonSettings {
	
	final static public String className = CommonSettings.class.getName();
	final static public long serialVersionUID = className.hashCode();

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

}


