package ca.mss.rd.loto.generator;

import java.util.List;

import ca.mss.rd.util.Logger;
import ca.mss.rd.util.UtilMisc;

public class LetoTicket6x5x3 extends LetoTicketKombo {

	static final private int SIZE_TICKET = 6; 
	static final private int SIZE_POOL = 5; 
	static final private int SIZE_SUBPOOL = 3;
	
	static final public int POOL_LENGTH = SIZE_POOL*SIZE_SUBPOOL;

	public LetoTicket6x5x3() {
		super(SIZE_TICKET, SIZE_POOL, SIZE_SUBPOOL);
	}

	public static void main(String[] args) {
		LetoTicket6x5x3 lt = new LetoTicket6x5x3();
		
		List<int[]> tickets = lt.generateTickets("1,2,3,4,5,6,7,8,9,10,11,12,13,14,15");
		
		for(int i=0; i<tickets.size(); i++){
			int[] t = tickets.get(i);
			Logger.LETO.printf("%2d) %s", i+1, UtilMisc.toString(t));
		}
		Logger.LETO.printf("Done");
	}
	
}
