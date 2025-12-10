package ca.mss.rd.trade.broker.mt4;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ca.mss.rd.trade.broker.RdOrder;
import ca.mss.rd.trade.broker.RdTickListener;
import ca.mss.rd.trade.broker.RdTradeInterface;
import ca.mss.rd.util.Logger;
import ca.mss.rd.util.UtilDateTime;

import com.jfx.Broker;
import com.jfx.Color;
import com.jfx.ErrUnknownSymbol;
import com.jfx.MT4;
import com.jfx.MarketInfo;
import com.jfx.MarketInformation;
import com.jfx.SelectionPool;
import com.jfx.SelectionType;
import com.jfx.TickInfo;
import com.jfx.TradeOperation;
import com.jfx.net.JFXServer;
import com.jfx.strategy.Strategy;
import com.jfx.strategy.Strategy.Instrument;
import com.jfx.strategy.Strategy.Terminal;
import com.jfx.strategy.Strategy.TerminalType;
import com.jfx.strategy.Strategy.TickListener;
import com.jfx.strategy.Strategy.TimerListener;
import com.jfx.strategy.StrategyRunner;

abstract public class RdTradeInterfaceMT4 implements RdTradeInterface {

	private static final String NAME = "MT4";

	public static String DEFAULT_TERMINAL_IP = "127.0.0.1";
	public static int DEFAULT_TERMINAL_PORT = 7788;

	public final String broker;
	public final String user;
	public final String terminalIP;
	public final String password;
	public final int terminalPORT;
	
	private long tradeIntervalMls;

	public void trade(){
		if (Logger.TRADE_INT.isOn)
			Logger.TRADE_INT.printf("%10s: Trade on %s", NAME, UtilDateTime.rightnow());
		
	}
	
	public RdTradeInterfaceMT4(String broker, String user, String password) throws IOException {
		this(broker, user, password, DEFAULT_TERMINAL_IP, DEFAULT_TERMINAL_PORT);
	}

	public RdTradeInterfaceMT4(String broker, String user, String password, String terminalIP, int terminalPORT) {

		this.broker = broker;
		this.user = user;
		this.password = password;
		this.terminalIP = terminalIP;
		this.terminalPORT = terminalPORT;
		
		this.tradeIntervalMls = Long.MAX_VALUE;

		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				try {
					Logger.TRADE_INT.printf("%10s: Disconnect from [%s] on %s", NAME,  getBroker(), UtilDateTime.rightnow());
				} catch (Throwable t) {
					;
				}
			}
		});

		mt4.setReconnect(true);
	}

	public String getBroker(){
		return String.format("%s %s", RdTradeInterfaceMT4.this.broker, RdTradeInterfaceMT4.this.user);
	}
	
	public void connect() throws IOException{
		Logger.TRADE_INT.printf("%10s: Connecting to [%s] on %s ...",NAME, getBroker(), UtilDateTime.rightnow());
		mt4.connect(terminalIP, terminalPORT, new Broker(broker), user, password);
	}
	
	public void disconnect() throws IOException{
		mt4.disconnect();
	}
	
	private final Strategy mt4 = new Strategy() {
		/*
		 * current orders loading, restoration to the previous launching moment.
		 */
		@Override
		public final void init(String symbol, int period, StrategyRunner strategyRunner) throws ErrUnknownSymbol, IOException {

			if (Logger.TRADE_INT.isOn)
				Logger.TRADE_INT.printf("%10s: Init [%s %s][symbol=%s][period=%d] on %s", NAME, accountCompany(), accountNumber(), symbol, period, UtilDateTime.rightnow());

			super.init(symbol, period, strategyRunner);
		}

		/*
		 * releasing owned resources (e.g opened files closing) when leaving the
		 * advisor
		 */
		@Override
		public void deinit() {

			if (Logger.TRADE_INT.isOn)
				Logger.TRADE_INT.printf("%10s: De-Init  on %s", NAME, UtilDateTime.rightnow());

			super.deinit();

		}

		@Override
		public long coordinationIntervalMillis() {
			return tradeIntervalMls;
		}

		/*
		 * Here can be placed the logic of mechanical trading system
		 * 
		 * Strategy.coordinate() is called by NJ4X framework once per
		 * coordinationIntervalMillis() - defined period. It is 60 seconds by
		 * default.
		 * 
		 * Initially it was an analog of the "start" method of a native strategy
		 * (expert/advisor) - so it was called when new tick arrives. Now its
		 * purpose is to do your main trading strategy tasks, like monitoring
		 * existing positions, rising new market orders etc. To get real-time
		 * ticks use TickListener functionality (more details are in examples
		 * and at http://nj4x.com/forum page)
		 */
		@Override
		public void coordinate() {
			if( coordinationIntervalMillis() == Long.MAX_VALUE )
				return;
			
			RdTradeInterfaceMT4.this.trade();
		}

	};

	/*
	 * (non-Javadoc)
	 * 
	 * @see ca.mss.rd.trade.broker.mt4.RdTradeInterface#getInstrumentList()
	 */
	@Override
	public final Set<String> getInstrumentList() {
		Set<String> set = new HashSet<String>();

		for (Instrument inst : mt4.getInstruments()) {
			set.add(inst.getName());
		}

		return set;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ca.mss.rd.trade.broker.mt4.RdTradeInterface#getVariableList()
	 */
	@Override
	public final Map<String, Double> getVariableList() {
		Map<String, Double> map = new HashMap<String, Double>();

		for (int i = 0, max = mt4.globalVariablesTotal(); i < max; i++) {
			String name = mt4.globalVariableName(i);
			map.put(name, mt4.globalVariableGet(name));
		}
		return map;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ca.mss.rd.trade.broker.mt4.RdTradeInterface#getTotalOrders()
	 */
	@Override
	public final int getTotalOrders() {
		return mt4.ordersTotal();
	}

	@Override
	public final String getCurrency() {
		return mt4.accountCurrency();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ca.mss.rd.trade.broker.mt4.RdTradeInterface#getBalance()
	 */
	@Override
	public final double getBalance() {
		return mt4.accountBalance();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ca.mss.rd.trade.broker.mt4.RdTradeInterface#getProfit()
	 */
	@Override
	public final double getProfit() {
		return mt4.accountProfit();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ca.mss.rd.trade.broker.mt4.RdTradeInterface#getMargin()
	 */
	@Override
	public final double getMargin() {
		return mt4.accountMargin();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ca.mss.rd.trade.broker.mt4.RdTradeInterface#getFreeMargin()
	 */
	@Override
	public final double getFreeMargin() {
		return mt4.accountFreeMargin();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ca.mss.rd.trade.broker.mt4.RdTradeInterface#getOrderList()
	 */
	@Override
	@SuppressWarnings("deprecation")
	public final List<RdOrder> getOrderList() {
		int max = mt4.ordersTotal();

		List<RdOrder> list = new ArrayList<RdOrder>(max);

		for (int i = 0; i < max; i++) {
			if (mt4.orderSelect(i, SelectionType.SELECT_BY_POS, SelectionPool.MODE_TRADES)) {
				RdOrder trade = new RdOrder();

				try {
					trade.symbol = mt4.orderSymbol();
					trade.type = TradeOperation.getTradeOperation(mt4.orderType()).toString();
					trade.volume = mt4.orderLots();
					trade.openDate = mt4.orderOpenTime();
					trade.closeDate = mt4.orderCloseTime();
					trade.openPrice = mt4.orderOpenPrice();
					trade.closePrice = mt4.orderClosePrice();
					trade.currency = mt4.accountCurrency();
					trade.profit = mt4.orderProfit();
					trade.ticket = mt4.orderTicket();
					trade.comment = mt4.orderComment();
				} catch (Throwable t) {
					trade.exception = t;
					trade.isValid = false;
				}

				list.add(trade);
			}
		}
		return list;
	}

	/*
	 * Closes opened order. If the function succeeds, the return value is true.
	 * If the function fails, the return value is false. To get the detailed
	 * error information, call GetLastError().
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see ca.mss.rd.trade.broker.mt4.RdTradeInterface#closeOrder(int, double,
	 * java.lang.String, int, int)
	 */
	@Override
	public final boolean closeOrder(
			int ticket, 			// Unique number of the order ticket.
			double lots, 			// Number of lots.
			String orderSymbol, 	// Preferred closing price.
			int slippage, 			// Value of the maximum price slippage in points.
			int arrowColor 			// Color of the closing arrow on the chart. If the parameter is missing or has CLR_NONE value closing arrow will not be drawn on the chart.
	) {
		try {
			if (Logger.TRADE_INT.isOn)
				Logger.TRADE_INT.printf("%10s: Closing order [ticket=%d] on %s", NAME, ticket, UtilDateTime.rightnow());

			if (mt4.orderType() == TradeOperation._OP_BUY) {
				return mt4.orderClose(ticket, lots, mt4.marketInfo(orderSymbol, MarketInfo.MODE_BID), slippage, arrowColor);
			} else if (mt4.orderType() == TradeOperation._OP_SELL) {
				return mt4.orderClose(ticket, lots, mt4.marketInfo(orderSymbol, MarketInfo.MODE_ASK), slippage, arrowColor);
			}
		} catch (Throwable e) {
			if( Logger.ERROR.isOn ) Logger.ERROR.printf(e, "%10s: Failed close order [ticket=%d] on %s due to ", NAME, ticket, UtilDateTime.rightnow(), e.getMessage());
		}
		return false;
	}

	/*
	 * Returns ticket number assigned to the order by the trade server and
	 * throws an exception in case of failure.
	 */
	@Override
	@SuppressWarnings("deprecation")
	public final int placeOrder(
			String symbol, 						// Symbol for trading.
			TradeOperation cmd, 				// Operation type. It can be any of the Trade operation enumeration.
			double volume,						// Number of lots.
			double price, 						// Preferred price of the trade.
			int slippage, 						// Maximum price slippage for buy or sell orders in points.
			double stoploss, 					// StopLoss level.
			double takeprofit, 					// TakeProfit level.
			String comment, 					// Order comment text. Last part of the comment may be changed by server.
			int magic, 							// Order magic number. May be used as user defined identifier.
			Date expiration, 					// Order expiration time (for pending orders only)
			long arrowColor) {

		int ticket = -1;
		try {
			double point = mt4.marketInfo(symbol, MarketInfo.MODE_POINT);

			ticket = mt4.orderSend(symbol, cmd, volume, normalize(price, point), slippage, normalize(stoploss, point), normalize(takeprofit, point), comment, magic, expiration, arrowColor);

			if (Logger.TRADE_INT.isOn)
				Logger.TRADE_INT.printf("%10s: Order %s placed on %s [ticket=%d]", TradeOperation.getTradeOperation(cmd.val), UtilDateTime.rightnow(), ticket);
		} catch (Throwable e) {
			if( Logger.ERROR.isOn ) Logger.ERROR.printf(e, "%10s: Failed create %s order on %s due to ", NAME, TradeOperation.getTradeOperation(cmd.val), UtilDateTime.rightnow(), e.getMessage());
		}
		return ticket;
	}

	@Override
	public final int placeOrderSBB(String symbol, double volume, String comment, int magic) {
		
		double stoploss = 0.0;
		double takeprofit = 0.0;
		double price = 0.0;
		
		try {
			
			MarketInformation mi= mt4.marketInfo(symbol);
			
			price = mi.BID;
			stoploss = price + mi.POINT * 200;
			takeprofit = price - mi.POINT * 200;
			
		} catch (ErrUnknownSymbol e) {
			if( Logger.ERROR.isOn ) Logger.ERROR.printf(e, "%10s: Failed calculate stop/loss for SBB due to ", NAME, e.getMessage());
		}
		
		return placeOrder(symbol, TradeOperation.OP_SELL, volume, price, 1, stoploss, takeprofit, comment, magic, null, Color.AliceBlue.val);
	}

	@Override
	public final int placeOrderBSB(String symbol, double volume, String comment, int magic) {
		
		double stoploss = 0.0;
		double takeprofit = 0.0;
		double price = 0.0;
		
		try {
			
			MarketInformation mi= mt4.marketInfo(symbol);
			
			price = mi.ASK;
			stoploss = price - mi.POINT * 200;
			takeprofit = price + mi.POINT * 200;
			
		} catch (ErrUnknownSymbol e) {
			if( Logger.ERROR.isOn ) Logger.ERROR.printf(e, "%10s: Failed calculate stop/loss for BSB due to ", NAME, e.getMessage());
		}
		
		return placeOrder(symbol, TradeOperation.OP_BUY, volume, price, 1, stoploss, takeprofit, comment, magic, null, Color.AliceBlue.val);
	}

	private final double normalize(double bid, double point) {
		return new BigDecimal(bid).setScale((int) (1 / point)).doubleValue();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ca.mss.rd.trade.broker.mt4.RdTradeInterface#addTickTerminal(java.lang
	 * .String)
	 */
	@Override
	public final void addTickTerminal(final String symbol, final RdTickListener listener) throws IOException {
		Terminal mt = mt4.addTerminal(symbol, new TickListener() {
			@Override
			public void onTick(TickInfo tick, MT4 connection) {
				try {
					assert(Logger.TRADE_INT.isOn? Logger.TRADE_INT.printf("%10s: Add tick listener on %s [%s={%s %s}]", NAME, UtilDateTime.rightnow(tick.time), symbol, tick, connection.accountCurrency()): true);
					if( listener != null ){
						listener.onTick(tick.time, symbol, tick.bid, tick.ask, tick.orderProfitLossMap);
					}
				} catch (Throwable t) {
					if( Logger.ERROR.isOn ) Logger.ERROR.printf(t, "%10s: Unexpected error during onTick: ", NAME, t.getMessage());
				}
			}
		}).connect();

		if (Logger.TRADE_INT.isOn)
			Logger.TRADE_INT.printf("%10s: Created terminal [%12s][%s] on %s", NAME, mt.getType(), symbol, UtilDateTime.rightnow());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ca.mss.rd.trade.broker.mt4.RdTradeInterface#addTimerTerminal(int)
	 */
	@Override
	public final void addTimerTerminal(int timerInterval) throws IOException {
		Terminal mt = mt4.addTerminal(new TimerListener() {
			@Override
			public void onTimer(MT4 connection) {
				try {
					if (Logger.TRADE_INT.isOn)
						Logger.TRADE_INT.printf("%10s: Add timer terminal on %s", NAME, UtilDateTime.rightnow());
				} catch (Throwable t) {
					if( Logger.ERROR.isOn ) Logger.ERROR.printf(t, "%10s: Unexpected error during onTimer: ", NAME, t.getMessage());
				}
			}
		}, timerInterval).connect();

		if (Logger.TRADE_INT.isOn)
			Logger.TRADE_INT.printf("%10s: Created terminal [%12s] on %s", NAME, mt.getType(), UtilDateTime.rightnow());
	}

	/*
	 * Terminals of this type provide simultaneous
	 * OrderSend/Modify/Cancel/Delete operations - one can close two different
	 * orders from two different Threads in parallel - Strategy.OrderClose
	 * method will not block if there is enough ORDERS_WORKER terminals are
	 * opened for the strategy.
	 */
	public final void addOrderTerminal() throws IOException {
		Terminal mt = mt4.addTerminal(TerminalType.ORDERS_WORKER).connect();
		if (Logger.TRADE_INT.isOn)
			Logger.TRADE_INT.printf("%10s: Created terminal [%12s] on %s", NAME, mt.getType(), UtilDateTime.rightnow());
	}

	@Override
	public String getDefaultQuote() {
		return mt4.getSymbol();
	}
	
	
	public final long getTradeIntervalMls() {
		return tradeIntervalMls;
	}


	public final void setTradeIntervalMls(long tradeIntervalMls) {
		this.tradeIntervalMls = tradeIntervalMls;
	}



	
	static public final void shutdown() {
		if (Logger.TRADE_INT.isOn)
			Logger.TRADE_INT.printf("%10s: Shutdown on %s", NAME, UtilDateTime.rightnow());
		JFXServer.stop();
	}

	static {
		String key = System.getProperty("nj4x_mt4_activation_key");
		Logger.TRADE_ENV.printf("nj4x_mt4_activation_key=%s",  key);
		
		if( key == null ){
			throw new RuntimeException("No activation key!");
		}
	}
}
