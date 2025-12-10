package ca.mss.rd.test.trade.broker.mt4;


import java.io.IOException;

import ca.mss.rd.trade.broker.mt4.MT4Account;
import ca.mss.rd.util.Logger;
import ca.mss.rd.util.UtilDateTime;

import com.jfx.MT4;
import com.jfx.TickInfo;


public class MT4TestTick extends MT4TestEnv {

	@Override
	public long coordinationIntervalMillis() {
		return Long.MAX_VALUE;
	}

    public void addTickTerminal(final String symbol) throws IOException {
    	Terminal mt = addTerminal(symbol, new TickListener() {
			@Override
			public void onTick(TickInfo tick, MT4 connection) {
				try {
					Logger.MT4_TICK.printf("Tick listener [time=%s][%s={%s CAD}]", UtilDateTime.format(tick.time, "kk:mm:ss"), symbol, tick, connection.accountCurrency());
				} catch(Throwable t){
					if( Logger.ERROR.isOn ) Logger.ERROR.printf(t, "Unexpected error during onTick: ", t.getMessage());
				}
			}
		}).connect();		
		
		Logger.MT4_TICK.printf("Created terminal [%12s][%s]", mt.getType(), symbol);
    }
    
	
	public static void main(String[] args) throws Throwable {
		final MT4TestTick test = new MT4TestTick();

		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				try {
					Logger.MT4_ENV.printf("Disconnect [%s]!", MT4Account.SERVER);
					//test.disconnect();
					//test.tsClient.killMT4Terminal(null, null);
				} catch (Throwable t) {
					throw new RuntimeException("xexe", t);
				}
			}
		});

		Logger.MT4_ENV.printf("You may kill terminal.exe to test reconnection...");
		test.setReconnect(true);

		Logger.MT4_ENV.printf("Connect to %s %s...", MT4Account.SERVER, MT4Account.USER);
		test.connect(MT4Account.TERMINAL_SERVER_IP_ADDRESS, MT4Account.TERMINAL_SERVER_PORT, MT4Account.SERVER, MT4Account.USER, MT4Account.PASSWORD);

		/*
		 * Terminals of this type are used to get different symbol's real-time ticks in parallel.
		 */
		test.addTickTerminal(test.getSymbol());
		test.addTickTerminal("AUDCAD");
		test.addTickTerminal("AUDJPY");
		test.addTickTerminal("EURJPY");
		
		Logger.MT4_ENV.printf("\n\n%d sec example ...\n", INTERVAL_SLEEP_MLS/1000);
		Thread.sleep(INTERVAL_SLEEP_MLS);
		
		shutdown();

		Logger.MT4_ENV.printf("\n\nExit - by!");
		System.exit(0);
	}

}
