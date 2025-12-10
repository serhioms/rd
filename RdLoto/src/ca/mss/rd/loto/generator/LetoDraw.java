package ca.mss.rd.loto.generator;

import ca.mss.rd.util.UtilDateTime;
import ca.mss.rd.util.UtilMisc;


public class LetoDraw {

	final public static String IN_DATE_FORMAT = "MMM d, yyyy";
	final public static String OUT_DATE_FORMAT = "yy-MM-dd";

	final public String date;
	final public int[] draw;
	final private LetoDrawHistory ldh;
	
	private int[][] tickets;
	private int[][] pools;
	
	public LetoDraw(LetoDrawHistory ldh, String date, int[] draw) {
		this.ldh = ldh;
		this.date = UtilMisc.isEmpty(date)? date: UtilDateTime.format(UtilDateTime.parse(date, IN_DATE_FORMAT), OUT_DATE_FORMAT);
		this.draw = draw;
	}

	public int[][] getTicket() {
		if( tickets == null ){
			tickets = ldh.readTicket(ldh.getDrawTitle(), date);
		}
		return tickets;
	}

	public int[][] getPool() {
		if( pools == null ){
			pools = ldh.readPool(ldh.getDrawTitle(), date);
		}
		return pools;
	}
	
}
