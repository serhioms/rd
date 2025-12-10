package ca.mss.rd.trader.model.indicator;

import ca.mss.rd.trader.model.MAPoint;
import ca.mss.rd.trader.model.MARegion;
import ca.mss.rd.trader.model.ProfitSurfaceFinder;
import ca.mss.rd.trader.model.RevAvgModel;
import ca.mss.rd.trader.server.wkf.ContextProcessQuote;
import ca.mss.rd.util.Logger;
import ca.mss.rd.util.UtilDateTime;


public class IndicatorRevAvg2 extends IndicatorDefault {

	public static int BREAK_SIZE = 10;
	public static boolean IS_OPEN_COUPLE_TRADES = false;
	

	public static boolean IS_SEARCH = false;
	
	// IS_SEARCH == false
	public static MAPoint ONE_SPOT = new MAPoint(4520, 3000); // 8.28spr 4520/3000

	// IS_SEARCH == true
	public static MARegion SEARCH_REGION = new MARegion(new MAPoint(3500, 1000), new MAPoint(6900, 5000), BREAK_SIZE);

	
	private final ContextProcessQuote context;

	private final RevAvgModel ramodel;
	private final ProfitSurfaceFinder psfinder;

	private long counter;
	private long startTime;
	private MAPoint lastExtr;
	
	public IndicatorRevAvg2(ContextProcessQuote context) {
		super("RevAvg2");
		this.context = context;
		
		this.ramodel = new RevAvgModel(context);
		this.psfinder = new ProfitSurfaceFinder();

		if( IS_SEARCH ){
			psfinder.add(new ProfitSurface(SEARCH_REGION.center, SEARCH_REGION));
		} else {
			psfinder.add(new ProfitSurface(ONE_SPOT));
		}
		
		clean();
		
		Logger.REVAVG.printf("Initialization done %s", psfinder.getSurfaceLength());
	}

	@Override
	public void clean() {
		ramodel.clean();
		psfinder.clean();
		lastExtr = new MAPoint(0.0); 
		counter = 0;
		startTime = System.currentTimeMillis();
		Logger.TRADE_CLOSE.isOn = !IS_SEARCH; 
	}

	@Override
	public void process() {
		counter++;
		
		double mid = (context.quote.bid + context.quote.ask) * 0.5;

		psfinder.process(mid, ramodel);

		if( !lastExtr.equals(psfinder.extrReg.extr) ){
			
			lastExtr.set(psfinder.extrReg.extr);
			
			if( IS_SEARCH ){
				if( psfinder.isAnyExtrStepSmall() ){
					if( !psfinder.isExist1() ){
						psfinder.add(new ProfitSurface(psfinder.extrReg.left));
					}
				} else {
					if( !psfinder.isExistN() ){
						psfinder.add(new ProfitSurface(psfinder.extrReg.center, psfinder.extrReg));
					}
				}
			}
			
			if( Logger.REVAVG.isOn && IS_SEARCH ){
				Logger.REVAVG.printf("Time %8s MAX = %6.2f (%4d,%-4d) in (%4d-%-4d:%-4d/%4d-%-4d:%-4d) %s", 
						UtilDateTime.formatTime(System.currentTimeMillis() - startTime), 
						psfinder.extrSpot.profit, psfinder.extrSpot.slow, psfinder.extrSpot.fast,
						psfinder.extrReg.left.slow,psfinder.extrReg.slowEnd(), psfinder.extrReg.step.slow, psfinder.extrReg.left.fast,psfinder.extrReg.fastEnd(), psfinder.extrReg.step.fast 
						,"change");
			}
		}
		
		if( Logger.REVAVG.isOn && IS_SEARCH && counter > 1 && counter %1000 == 0 ){
			if( Logger.REVAVG.isOn ){
				Logger.REVAVG.printf("Time %8s MAX = %6.2f (%4d,%-4d) in (%4d-%-4d:%-4d/%4d-%-4d:%-4d)", 
						UtilDateTime.formatTime(System.currentTimeMillis() - startTime), 
						psfinder.extrSpot.profit, psfinder.extrSpot.slow, psfinder.extrSpot.fast,
						psfinder.extrReg.left.slow,psfinder.extrReg.slowEnd(), psfinder.extrReg.step.slow, psfinder.extrReg.left.fast,psfinder.extrReg.fastEnd(), psfinder.extrReg.step.fast 
						);
			}
		}
	}

	@Override
	public void stop() {
		ramodel.stop();
		if( Logger.TRADE_CLOSE_SURFACE.isOn ) 
			if( IS_SEARCH ) 
				Logger.TRADE_CLOSE_SURFACE.printf("\n%s", psfinder.getTable());
			else
				Logger.TRADE_CLOSE_SURFACE.printf("Total profit: %.2f", ramodel.totalProfit);
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
