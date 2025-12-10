package ca.mss.rd.util.model;

public class SubPool {

	public int index;
	public int[] subpool;

	public SubPool(int len, int index) {
		this.subpool = new int[len];
		this.index = index;
	}
}