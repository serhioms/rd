package ca.mss.rd.test.trade.broker.mt4;


import java.io.IOException;
import java.util.ArrayList;

import ca.mss.rd.trade.broker.mt4.MT4Account;
import ca.mss.rd.util.Logger;
import ca.mss.rd.util.UtilDateTime;

import com.jfx.ErrUnknownSymbol;
import com.jfx.MT4;
import com.jfx.net.JFXServer;
import com.jfx.strategy.Strategy;
import com.jfx.strategy.StrategyRunner;


public class MT4TestEnv extends Strategy {

	public static long INTERVAL_SLEEP_MLS = 10000;
	
	public static long INTERVAL_COORDINATE_MLS = 3000;
	public static int INTERVAL_TIMER_TERMINAL_MLS = 2000;
    
	static {
		String key = System.getProperty("nj4x_mt4_activation_key");
		Logger.MT4_ENV.printf("nj4x_mt4_activation_key=%s",  key);
		
		if( key == null ){
			throw new RuntimeException("No activation key!");
		}
	}

	/*
	 * current orders loading, restoration to the previous launching moment.
	 */
	@Override
	public void init(String symbol, int period, StrategyRunner strategyRunner) throws ErrUnknownSymbol, IOException {
		Logger.MT4_ENV.printf("************************ init [%s %s][symbol=%s][period=%d]", accountCompany(), accountNumber(), symbol, period);
		super.init(symbol, period, strategyRunner);

		if (Logger.MT4_ENV.isOn)
			showVariables();
		if (Logger.MT4_ENV.isOn)
			showInstruments();
	}

	
	
	
	
	public void showInstruments() {
		ArrayList<Instrument> instruments = getInstruments();
		if (instruments != null) {
			Logger.MT4_ENV.printf("Available instruments:");
			Logger.MT4_ENV.printf("----------------------");
			for (int i = 0; i < instruments.size(); i++) {
				Instrument s = instruments.get(i);
				Logger.MT4_ENV.printf("%d) %s", i, s);
			}
			Logger.MT4_ENV.printf("====================");
		}
	}

	public void showVariables() {
		int gmax = globalVariablesTotal();
		Logger.MT4_ENV.printf("Available variables:");
		Logger.MT4_ENV.printf("--------------------");
		for (int i = 0; i < gmax; i++) {
			String name = globalVariableName(i);
			Logger.MT4_ENV.printf("%d) %s = %s", i, name, globalVariableGet(name));
		}
		Logger.MT4_ENV.printf("====================");
	}

	/*
	 * releasing owned resources (e.g opened files closing) when leaving the
	 * advisor
	 */
	@Override
	public void deinit() {
		Logger.MT4_ENV.printf("************************ deinit - releasing owned resources");
	}

	
	
	@Override
	public long coordinationIntervalMillis() {
		return INTERVAL_COORDINATE_MLS;
	}

	/*
	 * Here can be placed the logic of mechanical trading system
	 * 
	 * Strategy.coordinate() is called by NJ4X framework once per coordinationIntervalMillis() - defined period. It is 60 seconds by default.
	 * 
	 * Initially it was an analog of the "start" method of a native strategy (expert/advisor) - so it was called when new tick arrives.
	 * Now its purpose is to do your main trading strategy tasks, like monitoring existing positions, rising new market orders etc.
	 * To get real-time ticks use TickListener functionality (more details are in examples and at http://nj4x.com/forum page)
	 *  
	 */
	@Override
    public void coordinate() {
		
		if( coordinationIntervalMillis() >= Long.MAX_VALUE )
			return;
		else
			Logger.MT4_TIMER.printf("************************ coordinate on %s", UtilDateTime.format(UtilDateTime.now(), "kk:mm:ss"));
    }
    
	public void addTimerTerminal(int timerInterval) throws IOException {
		Terminal mt = addTerminal(new TimerListener() {
			@Override
			public void onTimer(MT4 connection) {
				try {
					Logger.MT4_TIMER.printf("************************ Timer terminal on %s", UtilDateTime.format(UtilDateTime.now(), "kk:mm:ss"));
				} catch(Throwable t){
					if( Logger.ERROR.isOn ) Logger.ERROR.printf(t, "Unexpected error during onTimer: ", t.getMessage());
				}
			}
		}, timerInterval).connect();
		
		Logger.MT4_TIMER.printf("Created terminal [%12s]", mt.getType());
	}    
	
	
	
	
	/*
	 * Terminals of this type provide simultaneous OrderSend/Modify/Cancel/Delete operations - one can close two different orders from two different Threads in parallel -
	 * Strategy.OrderClose method will not block if there is enough ORDERS_WORKER terminals are opened for the strategy.
	 */
	public void addOrderTerminal() throws IOException {
		Terminal mt = addTerminal(TerminalType.ORDERS_WORKER).connect();
		Logger.MT4_ENV.printf("Created terminal [%12s]", mt.getType());
	}

	
	public static void main(String[] args) throws Throwable {
		final MT4TestEnv test = new MT4TestEnv();

		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				try {
					Logger.MT4_ENV.printf("Disconnect [%s]!", MT4Account.SERVER);
					// sampleStrategy.disconnect();
				} catch (Throwable t) {
					throw new RuntimeException("xexe", t);
				}
			}
		});

		Logger.MT4_ENV.printf("You may kill terminal.exe to test reconnection...");
		test.setReconnect(true);

		Logger.MT4_ENV.printf("Connect to %s %s...", MT4Account.SERVER, MT4Account.USER);
		test.connect(MT4Account.TERMINAL_SERVER_IP_ADDRESS, MT4Account.TERMINAL_SERVER_PORT, MT4Account.SERVER, MT4Account.USER, MT4Account.PASSWORD);

		test.addTimerTerminal(INTERVAL_TIMER_TERMINAL_MLS);
		
		test.addOrderTerminal();

		Logger.MT4_ENV.printf("\n\n%d sec example ...\n", INTERVAL_SLEEP_MLS/1000);
		Thread.sleep(INTERVAL_SLEEP_MLS);
		
		shutdown();

		Logger.MT4_ENV.printf("\n\nExit - by!");
		System.exit(0);
	}

	public static void shutdown() {
		Logger.MT4_ENV.printf("%s shutdown!", MT4Account.SERVER);
		JFXServer.stop();
	}

}
