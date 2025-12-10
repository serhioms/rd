package ca.mss.rd.loto.generator;

import ca.mss.rd.util.Logger;



public class SimulateOnt649 {

	// Done [simulation=10   ][pools=10 ][subpool=200] = Violations [seq=2067 ][subpool=10584 ][ticket=174064] in 7 sec
	// Done [simulation=100  ][pools=100][subpool=200] = Violations [seq=22957][subpool=114259][ticket=0     ] in 1.1 min
	// Done [simulation=100  ][pools=100][subpool=100] = Violations [seq=11505][subpool=56685 ]                in 11.9 sec
	// Done [simulation=10000][pools=1  ][subpool=5  ] = Violations [seq=595  ][subpool=2366  ]                in 14.2 sec
	// Done [simulation=10000][pools=1  ][subpool=3  ] = Violations [seq=387  ][subpool=1196  ]                in 14.1 sec
	// Done [simulation=10000][pools=1  ][subpool=2  ] = Violations [seq=221  ][subpool=562   ]                in 12.6 sec
	// Done [simulation=100  ][pools=100][subpool=2  ] = Violations [seq=224  ][subpool=578   ]                in 204 mls
	// Done [simulation=1000 ][pools=13 ][subpool=2  ] = Violations [seq=259  ][subpool=753   ]                in 672 mls
	
	private static final int MAX_SIMULATIONS = 1000;
	private static final int MAX_POOL_SET = 13;
	private static final int MAX_POOL_SET_SIZE = 2;
	private static final int MAX_TICKETS_PRINT = Integer.MAX_VALUE;
	
	private static LetoTicketKombo TICKET = new LetoTicket6x5x3(); 
	private static LetoDrawHistory HISTORY = new LetoOnt649DrawHistory(); 

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
