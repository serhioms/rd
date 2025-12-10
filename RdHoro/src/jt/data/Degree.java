package jt.data;

public class Degree {

	/**
	 * @param args
	 */
	
	private double latlong;
	public Degree(double val) {
		latlong  = val;
	}
	
	public double getDoubleVal() {
		return latlong;
	}
	
	public String toString() {
		String str = new Integer((int)latlong).toString();
		double min = (latlong - (int)latlong) * 60;
		str = str + "*" + (int)(min) + "\'" + (int)((min - (int)min) * 60) + "\"";
		return str;
	}
	public static void main(String[] args) {
		Degree ln = new Degree(10.2);
		System.out.println(ln);
	}

}
