package ca.mss.rd.trader.model.indicator;

import ca.mss.rd.trader.model.RevAvgCubeModel;
import ca.mss.rd.trader.model.cube.MaximumHistory;
import ca.mss.rd.trader.model.cube.RevAvgCubeFinder;
import ca.mss.rd.trader.server.wkf.ContextProcessQuote;
import ca.mss.rd.util.Logger;
import ca.mss.rd.util.UtilDateTime;


public class IndicatorRevAvg extends IndicatorDefault {

	final public static boolean DO_NOT_FORCE = false;
	final public static boolean FORCE = true;

	// Open multiple trades
	public static boolean IS_OPEN_COUPLE_TRADES = false;

	// Search region break
	public static int BREAK_SIZE = 10;
	
	// Search extremum
	public static boolean IS_SEARCH = true;
	
	// IS_SEARCH == false
	public static RevAvgCubeFinder.CubeSpace ONE_SPOT = new RevAvgCubeFinder.CubeSpace(4520, 3000);

	// IS_SEARCH == true
	public static RevAvgCubeFinder.CubeSpace SEARCH_REGION = new RevAvgCubeFinder.CubeSpace(3500, 6900, 1000, 5000);

	
	private final ContextProcessQuote context;
	private final RevAvgCubeModel ramodel;
	private final RevAvgCubeFinder rafinder;
	private final MaximumHistory maxHistory;

	
	private long counter;
	private long startTime;
	
	public IndicatorRevAvg(ContextProcessQuote context) {
		super("RevAvg");
		this.context = context;
		
		this.ramodel = new RevAvgCubeModel(context);
		this.rafinder = IS_SEARCH? new RevAvgCubeFinder(SEARCH_REGION, BREAK_SIZE): new RevAvgCubeFinder(ONE_SPOT);
		this.maxHistory = new MaximumHistory();
		

		clean();
		
		Logger.REVAVG_INDICATOR.printf("Initialization done %s", rafinder.getSurfaceSize());
	}

	@Override
	public void clean() {
		ramodel.clean();
		rafinder.clean();
		counter = 0;
		startTime = System.currentTimeMillis();
		Logger.TRADE_CLOSE.isOn = !IS_SEARCH;
	}

	@Override
	public void process() {
		counter++;
		
		rafinder.process(ramodel, maxHistory);
		
		if( IS_SEARCH ){
			analyzeSearchResult(DO_NOT_FORCE);
		}
	}

	@Override
	public void stop() {
		ramodel.finalizeSimulate(maxHistory);

		if( IS_SEARCH ){
			
			analyzeSearchResult(FORCE);
			
			assert( Logger.TRADE_CLOSE_SURFACE.isOn? Logger.TRADE_CLOSE_SURFACE.printf("\n%s", rafinder.getSurfaceTable()): true);
		} else {
			
			assert( Logger.TRADE_CLOSE.isOn ? Logger.TRADE_CLOSE.printf("Total profit: %.2f", maxHistory.max.profit): true);
		}
	}
	
	private void analyzeSearchResult(boolean force) {
		
		if( Logger.REVAVG.isOn ) {
			if( force || (counter > 1 && counter %1000 == 0 ) ){
				Logger.REVAVG.printf("Time %8s/%8s MAX = %6.2f (%s) in (%s)" 
				,UtilDateTime.formatTime(System.currentTimeMillis() - startTime) 
				,UtilDateTime.now(context.quote.time24)
				,maxHistory.lastMax.profit, maxHistory.lastMax.point.getCoordinates()
				,maxHistory.getExtrRegion());
			}
		}

		if( maxHistory.isNoChanges() ){
			;
		} else if( maxHistory.isSamePoint() ){
			if( !maxHistory.isSameProfit() ){
				
				maxHistory.updateProfit();
				
				if( maxHistory.max.profit > 0.0 ){
					assert( Logger.REVAVG_INDICATOR.isOn ? Logger.REVAVG_INDICATOR.printf("Time %8s/%8s MAX = %6.2f (%s) in (%s): %s" 
							,UtilDateTime.formatTime(System.currentTimeMillis() - startTime) 
							,UtilDateTime.now(context.quote.time24)
							,maxHistory.lastMax.profit, maxHistory.lastMax.point.getCoordinates()
							,maxHistory.getExtrRegion() 
							,maxHistory.report()): true);
				}
			}
		} else {
			maxHistory.updatePoint();
			
			
			if( maxHistory.max.profit > 0.0 ){
				assert( Logger.REVAVG_INDICATOR.isOn ? Logger.REVAVG_INDICATOR.printf("Time %8s/%8s MAX = %6.2f (%s) in (%s): %s" 
						,UtilDateTime.formatTime(System.currentTimeMillis() - startTime) 
						,UtilDateTime.now(context.quote.time24)
						,maxHistory.lastMax.profit, maxHistory.lastMax.point.getCoordinates()
						,maxHistory.getExtrRegion() 
						,maxHistory.report()): true);
			}
		}
	}

}



































//OpTime    	Type	Volume	Symbol	Price 	S/L   	T/P   	ClTime    	Price 	ProMin	ProMax	Profit	Total   	Comment
//23:24:42  	Buy 	1.0  	EURUSD	1.36485	      	      	23:49:30  	1.36495	{-1.39 	1.69  }	1.00  	12.68   	3926/1422:ONE TRADE
//23:28:51  	Buy 	1.0  	EURUSD	1.36486	0.0   	0.0   	23:49:30  	1.36495	{-1.00 	1.79  }	0.90  	10.66   	4000/1490:ONE TRADE

//23:24:57  	Buy 	1.0  	EURUSD	1.36481	      	      	23:51:15  	1.3649	{-1.00 	0.90  }	0.90  	10.93   	6900/3450:ONE TRADE
//20:42:45  	Buy 	1.0  	EURUSD	1.36529	0.0   	0.0   	23:13:30  	1.36502	{-2.89 	0.00  }	-2.69 	7.92   		6030/3130:ONE TRADE

//22:48:21  	Buy 	1.0  	EURUSD	1.36517	0.0   	0.0   	23:59:21  	1.3648	{-3.68 	0.00  }	-3.68 	-5.55   	2000/750 :ONE TRADE  

// #1
//17:43:00  	Sell	1.0		EURUSD	1.36564	      	      	20:35:09	1.36529	-1.59	3.48	3.48	186.29		5956/2978:MANY TRADES (53)
//20:35:09  	Buy 	1.0  	EURUSD	1.36529	      	      	23:12:54  	1.36503	{-2.59 	0.00  }	-2.59 	6.61    	5956/2978:ONE TRADE   (5)

//#2
//23:14:24  	Sell	1.0  	EURUSD	1.36502	      	      	23:23:18  	1.36489	{-0.95 	1.23  }	1.23  	147.15  	4290/4235:MANY TRADES (172)
//23:23:18  	Buy 	1.0  	EURUSD	1.36489	      	      	23:58:15  	1.36474	{-1.49 	1.39  }	-1.49 	-2.21   	4290/4235:ONE TRADE   (15)

//18:04:09  	Sell	1.0  	EURUSD	1.36559	      	      	23:24:57  	1.36481	{-1.79 	7.33  }	7.33  	89.86   	6900/3450:MANY TRADES
//17:40:18  	Sell	1.0  	EURUSD	1.36564	0.0   	0.0   	20:42:45  	1.36529	{-1.60 	3.51  }	3.51  	38.01   	6030/3130:MANY TRADES
//22:07:15  	Sell	1.0  	EURUSD	1.36516	0.0   	0.0   	23:28:51  	1.36486	{-0.93 	2.79  }	2.79  	47.36   	4000/1490:MANY TRADES   
