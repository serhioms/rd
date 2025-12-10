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

	final static public int ceil(double value){
		return ceil(new BigDecimal(value), 0).intValue();
	}

	final public static BigDecimal max(BigDecimal a, BigDecimal b){
		return a.max(b);
	}

	final public static BigDecimal min(BigDecimal a, BigDecimal b){
		return a.min(b);
	}

	public static Object percentage(double a, double b) {
		if( a > 0 && b > 0 )
			return a*100.0/(a+b);
		else if( a > 0 && b < 0 )
			return a*100.0/(a-b);
		else if( a <= 0 && b < 0 )
			return b*100.0/(Math.abs(2*b)-Math.abs(a));
		else if( b == 0.0 )
			return a*100.0/Math.abs(a);
		throw new RuntimeException(String.format("Can not calculate persentage for [%f] and [%f]", a, b));
	}

	public static BigDecimal bigDecimal(long size) {
		return new BigDecimal(size);
	}
}
