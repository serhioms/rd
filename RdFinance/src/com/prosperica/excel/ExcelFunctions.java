package com.prosperica.excel;

import ca.mss.rd.util.UtilMath;

public class ExcelFunctions {

	final public static String className = ExcelFunctions.class.getName();
	final public static long serialVersionUID = className.hashCode();
	
	final public static double ZERO = 0.000000000000000000009D;
	
	final static public double round(double value, int scale){
		return UtilMath.round(value, scale);
	}
	
	final public static double PMT(double interestRate, double principalValue, int numberOfPeriods){
		if( numberOfPeriods <= 0 )
			return 0;
		else if( interestRate > ZERO )
			return interestRate * principalValue * Math.pow(1.0+interestRate, numberOfPeriods)/(1.0-Math.pow(1+interestRate,numberOfPeriods));
		else 
			return -principalValue/numberOfPeriods;
	}

}
