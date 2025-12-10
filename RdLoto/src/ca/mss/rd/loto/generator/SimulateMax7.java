package ca.mss.rd.loto.generator;

import ca.mss.rd.util.Logger;



public class SimulateMax7 {

	private static final int MAX_SIMULATIONS = 100;
	private static final int MAX_POOL_SET = 1;
	private static final int MAX_POOL_SET_SIZE = 1;
	private static final int MAX_TICKETS_PRINT = 10;
	
	private static LetoTicketKombo TICKET = new LetoTicket7x5x2(); 
	private static LetoDrawHistory HISTORY = new LetoMax7DrawHistory(); 

	public static void main(String[] args) throws InterruptedException {
		
		Logger.LETO_INFO.isOn = true;
		Logger.LETO_VERBOSE.isOn = false;
		
		if( MAX_SIMULATIONS > 1 ){
			Logger.LETO_INFO.isOn = false;
			Logger.LETO_VERBOSE.isOn = false;
		}
		
		SimulateLoto simulator = new SimulateLoto(MAX_SIMULATIONS, MAX_POOL_SET, MAX_POOL_SET_SIZE, TICKET, HISTORY, MAX_TICKETS_PRINT);
		
		simulator.simulateAll();
	}
	
}
