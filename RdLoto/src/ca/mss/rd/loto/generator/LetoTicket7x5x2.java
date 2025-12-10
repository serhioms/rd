package ca.mss.rd.loto.generator;

import java.util.List;

import ca.mss.rd.util.Logger;
import ca.mss.rd.util.UtilMisc;

public class LetoTicket7x5x2 extends LetoTicketKombo {

	static final public int SIZE_TICKET = 7; 
	static final public int SIZE_POOL = 5; 
	static final public int SIZE_SUBPOOL = 2;
	
	static final public int POOL_LENGTH = SIZE_POOL*SIZE_SUBPOOL;
	
	public LetoTicket7x5x2() {
		super(SIZE_TICKET, SIZE_POOL, SIZE_SUBPOOL);
	}

	public static void main(String[] args) {
		LetoTicket7x5x2 lt = new LetoTicket7x5x2();
		
		List<int[]> tickets = lt.generateTickets("1,2,3,4,5,6,7,8,9,10,10");
		
		for(int i=0; i<tickets.size(); i++){
			int[] t = tickets.get(i);
			Logger.LETO.printf("%2d) %s", i+1, UtilMisc.toString(t));
		}
		Logger.LETO.printf("Done");
	}
}
