package ca.mss.rd.excel;

import ca.mss.rd.util.UtilMath;

public class ExcelFunctions {

	final public static String className = ExcelFunctions.class.getName();
	final public static long serialVersionUID = className.hashCode();
	
	final public static double ZERO = 0.0099999999999999999999999999999999D;
	
	final static public double round(double value, int scale){
		return UtilMath.round(value, scale);
	}
	
	final public static double PMT(double interestRate, double principalValue, int numberOfPeriods){
		return interestRate * principalValue * Math.pow(1.0+interestRate, numberOfPeriods)/(1.0-Math.pow(1+interestRate,numberOfPeriods));
	}

}
