package ca.mss.rd.trader.model;

public class MAPoint {
	
	public double profit;
	public int slow, fast;
	
	public MAPoint(int slow, int fast) {
		this.slow = slow; 
		this.fast = fast;
	}
	public MAPoint(double profit) {
		this.profit = profit; 
	}
	public MAPoint(String sf) {
		String[] sfa = sf.split("[,/]");
		this.slow = Integer.parseInt(sfa[0].trim()); 
		this.fast = Integer.parseInt(sfa[1].trim());
	}
	public void set(int slow, int fast, double profit) {
		this.profit = profit;
		this.slow = slow;
		this.fast = fast;
	}
	public  void set(MAPoint point) {
		profit = point.profit;
		slow = point.slow;
		fast = point.fast;
	}
	public boolean checkMax(MAPoint point) {
		if( point.profit > profit){
			set(point);
			return true;
		}
		return false;
	}
	public boolean equals(MAPoint extr) {
		return slow == extr.slow && fast == extr.fast;
	}
}
