package ca.mss.rd.excel;

import java.math.BigDecimal;


public class ExcelFunctionsDouble {

	final public static String className = ExcelFunctionsDouble.class.getName();
	final public static long serialVersionUID = className.hashCode();
	
	final public static double round(double d, int scale){
		BigDecimal bd = new BigDecimal(d);  
		return bd.setScale(scale, bd.ROUND_HALF_UP).doubleValue();
	}

	final public static double PMT(double interestRate, int numberOfPeriods, double principalValue){
		return interestRate * principalValue * Math.pow(1.0+interestRate, numberOfPeriods)/(1.0-Math.pow(1+interestRate,numberOfPeriods));
	}

	final public static double loanPeriodicRate(double annualRate, int compoundFrequency){
		return annualRate/compoundFrequency;
	}
	
	final public static double mortgageAnnualRate(double annualRate, int compoundFrequency){
		return Math.pow(1+loanPeriodicRate(annualRate, compoundFrequency), compoundFrequency)-1.0;
	}
	
	final public static double mortgagePeriodicRate(double annualRate, int compoundFrequency, int paymentFrequency){
		return Math.pow(1+mortgageAnnualRate(annualRate, compoundFrequency), 1.0/paymentFrequency)-1.0;
	}
	
}
