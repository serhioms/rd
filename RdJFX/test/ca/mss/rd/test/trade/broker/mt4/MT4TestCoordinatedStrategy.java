package ca.mss.rd.test.trade.broker.mt4;


import java.math.BigDecimal;
import java.util.Date;
import java.util.concurrent.ExecutorService;

import ca.mss.rd.trade.broker.mt4.MT4Account;
import ca.mss.rd.util.Logger;
import ca.mss.rd.util.UtilDateTime;

import com.jfx.Color;
import com.jfx.ErrAccountDisabled;
import com.jfx.ErrCommonError;
import com.jfx.ErrCustomIndicatorError;
import com.jfx.ErrIntegerParameterExpected;
import com.jfx.ErrInvalidAccount;
import com.jfx.ErrInvalidFunctionParamvalue;
import com.jfx.ErrInvalidPrice;
import com.jfx.ErrInvalidPriceParam;
import com.jfx.ErrInvalidStops;
import com.jfx.ErrInvalidTicket;
import com.jfx.ErrInvalidTradeParameters;
import com.jfx.ErrInvalidTradeVolume;
import com.jfx.ErrLongPositionsOnlyAllowed;
import com.jfx.ErrMarketClosed;
import com.jfx.ErrNoConnection;
import com.jfx.ErrNoOrderSelected;
import com.jfx.ErrNotEnoughMoney;
import com.jfx.ErrOffQuotes;
import com.jfx.ErrOldVersion;
import com.jfx.ErrOrderLocked;
import com.jfx.ErrPriceChanged;
import com.jfx.ErrRequote;
import com.jfx.ErrServerBusy;
import com.jfx.ErrTooFrequentRequests;
import com.jfx.ErrTooManyRequests;
import com.jfx.ErrTradeContextBusy;
import com.jfx.ErrTradeDisabled;
import com.jfx.ErrTradeExpirationDenied;
import com.jfx.ErrTradeModifyDenied;
import com.jfx.ErrTradeNotAllowed;
import com.jfx.ErrTradeTimeout;
import com.jfx.ErrTradeTimeout2;
import com.jfx.ErrTradeTimeout3;
import com.jfx.ErrTradeTimeout4;
import com.jfx.ErrTradeTooManyOrders;
import com.jfx.ErrUnknownSymbol;
import com.jfx.MT4;
import com.jfx.MarketInfo;
import com.jfx.MarketInformation;
import com.jfx.SelectionPool;
import com.jfx.SelectionType;
import com.jfx.TradeOperation;
import com.jfx.net.JFXServer;


public class MT4TestCoordinatedStrategy extends MT4TestEnv {

	public static long INTERVAL_SLEEP_MLS = 30000;

	ExecutorService threads = java.util.concurrent.Executors.newFixedThreadPool(10);

	public void showTradeState(MT4 con) throws ErrNoOrderSelected, ErrCustomIndicatorError, ErrIntegerParameterExpected, ErrInvalidFunctionParamvalue, ErrInvalidPriceParam, ErrInvalidTicket, ErrUnknownSymbol, ErrTradeNotAllowed, ErrCommonError, ErrInvalidTradeParameters, ErrServerBusy, ErrOldVersion, ErrNoConnection, ErrTooFrequentRequests, ErrAccountDisabled, ErrInvalidAccount, ErrTradeTimeout, ErrInvalidPrice, ErrInvalidStops, ErrInvalidTradeVolume, ErrMarketClosed, ErrTradeDisabled, ErrNotEnoughMoney, ErrPriceChanged, ErrOffQuotes, ErrRequote, ErrOrderLocked, ErrLongPositionsOnlyAllowed, ErrTooManyRequests, ErrTradeTimeout2, ErrTradeTimeout3, ErrTradeTimeout4, ErrTradeModifyDenied, ErrTradeContextBusy, ErrTradeExpirationDenied, ErrTradeTooManyOrders {
		Logger.MT4_STRATEGY.printf("Trade status on %s", UtilDateTime.format(con!=null?con.timeCurrent():UtilDateTime.now(), "kk:mm:ss"));
		Logger.MT4_STRATEGY.printf("-------------------------------------------");
		Logger.MT4_STRATEGY.printf("%25s = %d", "ordersTotal", con!=null?con.ordersTotal(): ordersTotal());
		Logger.MT4_STRATEGY.printf("%25s = %s%f", "accountBalance", accountCurrency(), con!=null?con.accountBalance():accountBalance());
		Logger.MT4_STRATEGY.printf("%25s = %s%f", "accountProfit", accountCurrency(), con!=null?con.accountProfit():accountProfit());
		Logger.MT4_STRATEGY.printf("%25s = %s%f", "accountMargin", accountCurrency(), con!=null?con.accountMargin():accountMargin());
		Logger.MT4_STRATEGY.printf("%25s = %s%f", "accountFreeMargin", accountCurrency(), con!=null?con.accountFreeMargin():accountFreeMargin());
		Logger.MT4_STRATEGY.printf("===========================================");

		if( ordersTotal() > 0 ){
			Logger.MT4_STRATEGY.printf("Open orders on %s", UtilDateTime.format(UtilDateTime.now(), "kk:mm:ss"));
			Logger.MT4_STRATEGY.printf("----------------------------------------------------------------------------------------------------------------------------------------------------------------");
	        for (int i=0, max=ordersTotal(); i < max; i++) {
	            if (orderSelect(i, SelectionType.SELECT_BY_POS, SelectionPool.MODE_TRADES)) {
	        		Logger.MT4_STRATEGY.printf("%d) [symbol=%s][type=%s][volume=%f][start=%s][open=%f][close=%f][profit=%s%f][ticket=%d][comment=%s]", i+1, 
	        				orderSymbol(), TradeOperation.getTradeOperation(orderType()), orderLots(), 
	        				UtilDateTime.format(orderOpenTime(), "kk:mm:ss"), orderOpenPrice(), orderClosePrice(), accountCurrency(), orderProfit(), 
	        				orderTicket(), orderComment());
	            }
	        }
			Logger.MT4_STRATEGY.printf("================================================================================================================================================================");
		}
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
		
		Logger.MT4_STRATEGY.printf("************************ coordinate.start on %s [sleep=%d]", UtilDateTime.format(UtilDateTime.now(), "kk:mm:ss"), coordinationIntervalMillis());

		try {
			
        	final MarketInformation mi = marketInfo(getSymbol());

        	if( ordersTotal() == 0 ){
	            threads.submit(new Runnable() {
	                @Override
	                public void run() {
                        try {
							placeOrder(getSymbol(),
							        TradeOperation.OP_BUY,
							        marketInfo(getSymbol(), MarketInfo.MODE_MINLOT),
							        mi.ASK,
							        1,
							        mi.ASK - mi.POINT * 200,
							        mi.ASK + mi.POINT * 200,
							        "Buy Minilot " + new Date(),
							        7, 
							        null, 
							        Color.AliceBlue.val
							);
						} catch (ErrUnknownSymbol e) {
			    			if( Logger.ERROR.isOn ) Logger.ERROR.printf(e, "Failed create order due to wrong order symbol: ", e.getMessage());
						}
	                }
	            });
	
	            threads.submit(new Runnable() {
	                @Override
	                public void run() {
                        try {
							placeOrder(getSymbol(),
							        TradeOperation.OP_SELL,
							        marketInfo(getSymbol(), MarketInfo.MODE_MINLOT),
							        mi.BID,
							        1,
							        mi.BID + mi.POINT * 200,
							        mi.BID - mi.POINT * 200,
							        "Sell Minilot " + new Date(),
							        7, 
							        null, 
							        Color.AliceBlue.val
							);
						} catch (ErrUnknownSymbol e) {
			    			if( Logger.ERROR.isOn ) Logger.ERROR.printf(e, "Failed create order due to wrong order symbol: ", e.getMessage());
						}
	                }
	            });
        	} else {
    	        for(int i=1, max=ordersTotal(); i<max; i++) {
    	            if (orderSelect(0, SelectionType.SELECT_BY_POS, SelectionPool.MODE_TRADES)) {
        	        	try {
        	        		if( orderProfit() > 0 )
		    					if( closeOrder(orderTicket(), orderLots(), orderSymbol(), 2, 0) )
		    						Logger.MT4_STRATEGY.printf("%d) Close order [symbol=%s][type=%s][volume=%f][start=%s][open=%f][close=%f][profit=%s%f][finish=%s][ticket=%d][comment=%s]", i+1, 
		    	        				orderSymbol(), TradeOperation.getTradeOperation(orderType()), orderLots(), 
		    	        				UtilDateTime.format(orderOpenTime(), "kk:mm:ss"), orderOpenPrice(), orderClosePrice(), 
		    	        				accountCurrency(), orderProfit(),
		    	        				UtilDateTime.format(orderCloseTime(), "kk:mm:ss"), 
		    	        				orderTicket(), orderComment());
		    					else
		    						Logger.MT4_STRATEGY.printf("%d) Failed to close order [symbol=%s][type=%s][volume=%f][start=%s][open=%f][close=%f][profit=%s%f][finish=%s][ticket=%d][comment=%s]", i+1, 
			    	        				orderSymbol(), TradeOperation.getTradeOperation(orderType()), orderLots(), 
			    	        				UtilDateTime.format(orderOpenTime(), "kk:mm:ss"), orderOpenPrice(), orderClosePrice(), 
			    	        				accountCurrency(), orderProfit(),
			    	        				UtilDateTime.format(orderCloseTime(), "kk:mm:ss"), 
			    	        				orderTicket(), orderComment());
        	        	} catch(ErrNoOrderSelected t){
    		    			if( Logger.ERROR.isOn ) Logger.ERROR.printf(t, "Failed close order due to wrong order symbol: ", t.getMessage());
        	        	}
    	            }
    	        }
        	}
        	
            
		} catch (ErrUnknownSymbol e) {
			if( Logger.ERROR.isOn ) Logger.ERROR.printf(e, "Unexpected error: ", e.getMessage());
		}
        
        try {
			showTradeState(null);
		} catch (Throwable e) {
			if( Logger.ERROR.isOn ) Logger.ERROR.printf(e, "Unexpected error: ", e.getMessage());
		}
        
		Logger.MT4_STRATEGY.printf("************************ coordinate.finish on %s", UtilDateTime.format(UtilDateTime.now(), "kk:mm:ss"));

    }
 
	/*
	 * Closes opened order. 
	 * If the function succeeds, the return value is true. 
	 * If the function fails, the return value is false. To get the detailed error information, call GetLastError().
	 */
	public boolean closeOrder(
			int ticket,		// Unique number of the order ticket. 
			double lots, 	// Number of lots.
			String  orderSymbol, 	// Preferred closing price.
			int slippage, 	// Value of the maximum price slippage in points.
			int arrowColor	// Color of the closing arrow on the chart. 
							// If the parameter is missing or has CLR_NONE value closing arrow will not be drawn on the chart.
			) {
		try {
			if (orderType() == TradeOperation._OP_BUY) {
				return orderClose(ticket, lots, marketInfo(orderSymbol, MarketInfo.MODE_BID), slippage, arrowColor);
			} else if (orderType() == TradeOperation._OP_SELL) {
				return orderClose(ticket, lots, marketInfo(orderSymbol, MarketInfo.MODE_ASK), slippage, arrowColor);
			}
        } catch(Throwable e) {
			if( Logger.ERROR.isOn ) Logger.ERROR.printf(e, "Failed close order %d order due to ", ticket, e.getMessage());
        }
		return false;
	}

	/*
	 * Returns ticket number assigned to the order by the trade server and throws an exception in case of failure.
	 */
	public int placeOrder(String symbol, 	// Symbol for trading.
			TradeOperation cmd, 			// Operation type. It can be any of the Trade operation enumeration.
			double volume, 					// Number of lots.
			double price, 					// Preferred price of the trade.
			int slippage, 					// Maximum price slippage for buy or sell orders in points.
			double stoploss, 				// StopLoss level.
			double takeprofit, 				// TakeProfit level.
			String comment,					// Order comment text. Last part of the comment may be changed by server.
			int magic, 						// Order magic number. May be used as user defined identifier.
			Date expiration,				// Order expiration time (for pending orders only)
			long arrowColor) {
			
			int ticket = -1;
			try {
				double point = marketInfo(symbol, MarketInfo.MODE_POINT);

				ticket = super.orderSend(symbol, cmd, volume, normalize(price, point), slippage, normalize(stoploss, point), normalize(takeprofit, point), comment, magic, expiration, arrowColor);
				
				Logger.MT4_STRATEGY.printf("Order %s placed [ticket=%d][time=%s]", TradeOperation.getTradeOperation(cmd.val), ticket, UtilDateTime.format(UtilDateTime.now(), "kk:mm:ss"));
            } catch(Throwable e) {
    			if( Logger.ERROR.isOn ) Logger.ERROR.printf(e, "Failed create %s order due to ", TradeOperation.getTradeOperation(cmd.val), e.getMessage());
            }
   			return ticket;
	}

    private double normalize(double bid, double point) {
        return new BigDecimal(bid).setScale((int) (1 / point)).doubleValue();
    }	
	
    
    
	public static void main(String[] args) throws Throwable {
		final MT4TestCoordinatedStrategy sampleStrategy = new MT4TestCoordinatedStrategy();

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
		sampleStrategy.setReconnect(true);

		Logger.MT4_ENV.printf("Connect to %s %s...", MT4Account.SERVER, MT4Account.USER);
		sampleStrategy.connect(MT4Account.TERMINAL_SERVER_IP_ADDRESS, MT4Account.TERMINAL_SERVER_PORT, MT4Account.SERVER, MT4Account.USER, MT4Account.PASSWORD);

		sampleStrategy.addOrderTerminal();
		
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
