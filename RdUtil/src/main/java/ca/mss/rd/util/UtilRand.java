package ca.mss.rd.util;

import java.math.BigDecimal;
import java.util.Random;

public class UtilRand {

	final static public String module = UtilRand.class.getName();
	final static public long serialVersionUID = module.hashCode();

	final static Random rnd = new Random(UtilDateTime.now().getTime());
	
	final static public int getRandInt(int from, int to){
		return from + rnd.nextInt(to-from);
	}

	final static public int getRandInt(int to){
		return rnd.nextInt(to);
	}

	final static public double getRandDbl(double to){
		return new BigDecimal(rnd.nextDouble()).multiply(new BigDecimal(to)).doubleValue();
	}

	final static public long getRandLong(long from, long to){
		return from + getRandLong(to);
	}

	final static public long getRandLong(long to){
		return new BigDecimal(rnd.nextDouble()).multiply(new BigDecimal(to)).longValue();
	}
}
