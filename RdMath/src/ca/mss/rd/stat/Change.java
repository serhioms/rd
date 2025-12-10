package ca.mss.rd.stat;

public class Change {

	private double v;
	private boolean isChange;
	
	public Change() {
		super();
		clean();
	}

	public void clean(){
		v = Double.POSITIVE_INFINITY;
	}
	
	public void addValue(double d){
		isChange = d != v;
		v = d;
	}

	public final double getValue(){
		return v;
	}

	public final boolean isChange() {
		return isChange;
	}
}