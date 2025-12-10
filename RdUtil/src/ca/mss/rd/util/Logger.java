package ca.mss.rd.util;

import java.io.PrintStream;

public enum Logger {
	VERBOSE(false), 
	DEBUG(true), 
	INFO(false),
	IMPORTANT(false),
	WARNING(true),
	ERROR(true),

	THREAD(false), 
	CHART(false), 
	EVENT(false), 
	GUI(false), 
	HTTP(false), 
	PROPERTY(false), 

	JOB_POOL(false), 
	JOB_POOL_VERBOSE(false), 

	WKFMAN(false),
	
	WORKFLOW(true), 
	WORKFLOW_DEFN(false), 
	WORKFLOW_VALIDATION(false), 
	WORKFLOW_VERBOSE(false), 
	WORKFLOW_SOLVER(true), 

	VALIDATION(false),
	
	ACTIVITY(false), 
	ACTIVITY_VERBOSE(false), // Really accurate but without IDs 
	ACTIVITY_WAIT(false), 

	TOOL(false), 
	TOOL_VERBOSE(true), 

	OANDA(false),
	OANDA_INTERRUPTION(true),
	OANDA_VERBOSE(false),
	
	TRADER(false),
	TRADER_VERBOSE(false),
	
	PNL_ANALIZER(true),
	PNL_ANALIZER_VERBOSE(false),
	
	INDICATOR(false),
	INDICATOR_VERBOSE(false),
	
	TREND_ANALIZER(false),
	TREND_ANALIZER2(true),
	TREND_ANALIZER_VERBOSE(false),
	TREND_ANALIZER_TRENDABLE(false),
	TREND_ANALIZER_PROFITABLE(false),
	
	TREND_ANALIZER_1H(false),
	TREND_ANALIZER_TOTAL(false),
	TREND_ANALIZER_SORT(true),

	TRADE(false),
	TRADE_VERBOSE(false),
	TRADE_OPEN(false),
	TRADE_CLOSE(true),
	TRADE_CLOSE_FINAL(false),
	
	TRADE_CLOSE_SURFACE(false),
	
	TRADE_RESULT(false),	 // depricated ?

	MT4_ENV(false), 
	MT4_TICK(true),
	MT4_TIMER(true),
	MT4_STRATEGY(true),
	
	TRADE_ENV(false), 
	TRADE_INT(false), 
	TRADE_STRATEGY(true), 
	TRADE_TICK(true), 

	REVAVG(false), 
	REVAVG_INDICATOR(true), 
	REVAVG_FINDER_VERBOSE(false), 
	REVAVG_MODEL_QUOTE(false), 
	REVAVG_MODEL_MINIMAX(false), 
	
	REVAVG_VERBOSE(true),  // depricated ?
	REVAVG_MINIMAX(false), // depricated ?

	SIGNAL_OPN(true), 
	SIGNAL_CLS(true), 

	TEMP(true), 

	TEST(true),
	TEST_VERBOSE(true),
	TEST_SKIPPED_EVENTS(false),
	RESULT(true),

	DB(true), 
	PROP(true), 
	SPRING(true), 

	CHESS(true), 

	LETO_VERBOSE(true),
	LETO_INFO(true),
	LETO_DRAW(true),
	LETO_SIMULATE(true),
	LETO(true);
	

	public boolean isOn;

	private Logger() {
		this.isOn = true;
	}

	private Logger(boolean isOn) {
		this.isOn = isOn;
	}

	public final boolean printf(Throwable t, String s, Object... args) {
		if( isOn ){
			String msg = String.format("%25s: ",this.name()) + String.format(s, args);
			println(msg, isRed()? System.err: System.out);
			t.printStackTrace(isRed()? System.err: System.out);
		}
		return true;
	}
	
	public final boolean printf(Throwable t) {
		if( isOn ){
			println(String.format("%25s: ", this.name())+t.getMessage(), isRed()? System.err: System.out);
			t.printStackTrace(isRed()? System.err: System.out);
		}
		return true;
	}
	
	public final boolean printf(String s, Object... args) {
		if( isOn ){
			String msg = String.format("%25s: %s", this.name(), String.format(s, args));
			println(msg, isRed()? System.err: System.out);
		}
		return true;
	}
	
	/*
	 * A little convinience
	 */
	static public void all() {
		VERBOSE.isOn = true; 
		DEBUG.isOn = true; 
		INFO.isOn = true;
		IMPORTANT.isOn = true;
		WARNING.isOn = true;
		ERROR.isOn = true;
		TEST.isOn = true;
	}

	/*
	 * A little privacy
	 */
	private final boolean isRed(){
		return this == ERROR;
	}
	
	static private void println(String msg, PrintStream ps) {
		ps.println(msg);
	}

	static public void flush() {
		System.out.flush();
		System.err.flush();
	}

}