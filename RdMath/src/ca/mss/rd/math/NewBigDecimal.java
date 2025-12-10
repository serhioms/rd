package ca.mss.rd.math;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class NewBigDecimal {

	final static public int SCALE = 20;
	
	final static public MathContext MATH_CONTEXT = new MathContext(SCALE, RoundingMode.HALF_UP);	

	final static public BigDecimal ONE = new BigDecimal(1.0D, MATH_CONTEXT);
	
	final static public BigDecimal getInstance(double d){
		return new BigDecimal(d, MATH_CONTEXT);
	}
	
}
