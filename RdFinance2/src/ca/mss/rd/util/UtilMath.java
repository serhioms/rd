package ca.mss.rd.util;

import java.math.BigDecimal;
import java.math.RoundingMode;


public class UtilMath {
	final public static String className = UtilMath.class.getName();
	final public static long serialVersionUID = className.hashCode();
	
	final static public BigDecimal round(BigDecimal bd, int scale){
		return bd.setScale(scale, BigDecimal.ROUND_HALF_UP);
	}

	final static public BigDecimal ceil(BigDecimal bd, int scale){
		return bd.setScale(scale, RoundingMode.CEILING);
	}

	final static public double round(double value, int scale){
		return round(new BigDecimal(value), scale).doubleValue();
	}

	final static public double ceil(double value, int scale){
		return ceil(new BigDecimal(value), scale).doubleValue();
	}

	final public static double max(double a, double b){
		return (a>b)?a:b;
	}

	final public static double min(double a, double b){
		return (b<=a)?b:a;
	}

	final public static int max(int a, int b){
		return (a>=b)?a:b;
	}

	final public static int min(int a, int b){
		return (a<=b)?a:b;
	}
}