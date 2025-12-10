package ca.mss.rd.trader.deprecated;


public class Differential extends FIFO {

	final public static int DIFF_LEVEL = 4;

	public double alpha0, betta0, gamma0;
	public double alpha1, betta1;
	public double alpha2;
	
	private double x0, x1, x2, x3;
	
	public Differential(){
		super(DIFF_LEVEL);
		alpha0 = betta0 = gamma0 = alpha1 = betta1 = alpha2 = 0.0;
	}

	public void calculate(){
		if( isValid() ){
			x0 = pool.get(0).val[0];
			x1 = pool.get(1).val[0];
			x2 = pool.get(2).val[0];
			x3 = pool.get(3).val[0];
			alpha0 = x0-x1;
			alpha1 = x1-x2;
			alpha2 = x3-x3;
			betta0 = alpha0-alpha1;
			betta1 = alpha1-alpha2;
			gamma0 = betta0-betta1;
			gamma0 *= 10000;
			betta0 *= 10000;
			alpha0 *= 10000;
			betta1 *= 10000;
			alpha1 *= 10000;
			alpha2 *= 10000;
		}
	} 		
}

