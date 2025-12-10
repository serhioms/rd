package ca.mss.rd.test.math;

import ca.mss.rd.stat.DifferentialMooving;
import ca.mss.rd.util.Logger;


public class TestDifferential {

	public static void main(String[] args) throws Exception {
		Logger.DEBUG.isOn = true;
		
		DifferentialMooving dm = new DifferentialMooving(100);
		
		double d = 0;
		dm.addValue(d+=1);
		Logger.DEBUG.printf("%f) Average = %f, Differential = %f", d, dm.getAverage(), dm.getDifferential(1.0));

		dm.addValue(d+=1);
		Logger.DEBUG.printf("%f) Average = %f, Differential = %f", d, dm.getAverage(), dm.getDifferential(1.0));

		dm.addValue(d+=1);
		Logger.DEBUG.printf("%f) Average = %f, Differential = %f", d, dm.getAverage(), dm.getDifferential(1.0));

		dm.addValue(d=2);
		Logger.DEBUG.printf("%f) Average = %f, Differential = %f", d, dm.getAverage(), dm.getDifferential(1.0));

		dm.addValue(d-=1);
		Logger.DEBUG.printf("%f) Average = %f, Differential = %f", d, dm.getAverage(), dm.getDifferential(1.0));

	}

}
