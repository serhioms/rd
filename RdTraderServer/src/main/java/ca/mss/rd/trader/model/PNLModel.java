package ca.mss.rd.trader.model;

import ca.mss.rd.stat.AverageMooving;
import ca.mss.rd.stat.BiIntegral;
import ca.mss.rd.stat.Differential;
import ca.mss.rd.stat.DifferentialMooving;
import ca.mss.rd.stat.Integral;
import ca.mss.rd.trader.server.wkf.ContextProcessQuote;
import ca.mss.rd.util.Logger;
import ca.mss.rd.util.UtilDateTime;


public class PNLModel {

	public static final double LIMIT_LEVEL = 0.70;

	public static final int PNL_MA_LEVEL  = 1; 
	public static final int PNL_MA_DEPTH  = 3*20; 

	final private ContextProcessQuote context;
	
	private MovingPNL mpnl;
	private AverageMooving[] apnl;
	private DifferentialMooving dpnl;
	private Differential sdpnl;
	private Integral spnl;
	private BiIntegral bpnl;

	
	public double val, up, dn, diff, diff2, avgN, avg1, sum, abow, below, bool, bear;
	
	public PNLModel(ContextProcessQuote context) {
		this.context = context;
		this.mpnl = new MovingPNL();
		this.dpnl = new DifferentialMooving(PNL_MA_DEPTH);
		this.apnl = new AverageMooving[PNL_MA_LEVEL+1];
		for(int i=0; i<apnl.length; i++){
			this.apnl[i] = new AverageMooving(PNL_MA_DEPTH);
		}
		this.spnl = new Integral();
		this.sdpnl = new Differential();
		this.bpnl = new BiIntegral();
		clear();
	}

	
	public void clear() {
		mpnl.clear();
		dpnl.clear();
		spnl.clear();
		bpnl.clear();
		sdpnl.clear();
		val = up = dn = diff = diff2 = avgN = avg1 = sum = abow = below = bool = bear = 0;
	}


	final public void calc(Quote quote) {

		// pnl val
		if( mpnl.isStart){
			mpnl.updatePNL(quote);
			val = mpnl.openPnl-mpnl.closePnl; // shift to 0 level
		} else if( context.spread.isTiny() ){
			if( context.marketTrend == MarketTrend.Bool){
				mpnl.buy(quote);
			} else if( context.marketTrend == MarketTrend.Bear){
				mpnl.sell(quote);
			} else {
				mpnl.buy(quote);
			}
			val = mpnl.openPnl-mpnl.closePnl; // shift to 0 level
		}

		// pnl tube
		up = -mpnl.openPnl;
		dn = mpnl.openPnl;

		
		for(int i=0; i<apnl.length; i++){
			if( i == 0 )
				apnl[i].addValue(val);
			else
				apnl[i].addValue(apnl[i-1].getAverage());
		}
		avg1 = apnl[1].getAverage(); 
		avgN = apnl[PNL_MA_LEVEL].getAverage(); 

		sdpnl.addValue(avgN);
		diff = sdpnl.getDifferential(100.0); 
		
		dpnl.addValue(avgN);
		diff2 = dpnl.getDifferential(10.0); 

		spnl.addValue(val);
		sum = spnl.getIntegral(5*up);

		bpnl.addValue(val);

		if( spnl.isCrossedZero() ){
			context.marketTrend = val > 0.0? MarketTrend.Bool: val < 0.0? MarketTrend.Bear: MarketTrend.NA;
			assert( Logger.PNL_ANALIZER_VERBOSE.isOn ? Logger.PNL_ANALIZER_VERBOSE.printf("CrossedZero %s: %s", UtilDateTime.formatTime(quote.time24), context.marketTrend): true);
			//clear();
		}

		if( context.marketTrend == MarketTrend.Bool){
			bool = val;
			bear = 0;
		} else if( context.marketTrend == MarketTrend.Bear){
			bear = val;
			bool = 0;
		} else {
			bear = bool = 0;
		}
		
		
//		if( mpnl.gettingAbow(up, LIMIT_LEVEL) /*&& bpnl.isGoingUp(2.0)*/ ){
//			if( context.marketTrend != MarketTrend.Bool )
//				Logger.PNL_ANALIZER.printf("Boool: %d%%", mpnl.abowLevel(up));
//			context.marketTrend = MarketTrend.Bool;
//			abow = val;
//			below = 0;
//		} else if( mpnl.gettingBelow(dn, LIMIT_LEVEL)  /*&& bpnl.isGoingDn(2.0)*/ ){
//			if( context.marketTrend != MarketTrend.Bear ) 
//				Logger.PNL_ANALIZER.printf("Beear: %d%%", mpnl.belowLevel(dn));
//			context.marketTrend = MarketTrend.Bear; 
//			abow = 0;
//			below = val;
// 		} else {
//			if( context.marketTrend != MarketTrend.NA ) 
//				Logger.PNL_ANALIZER.printf("%s: %d%%", context.marketTrend, context.marketTrend==MarketTrend.Bear?mpnl.belowLevel(dn): mpnl.abowLevel(up));
//			context.marketTrend = MarketTrend.NA;
//			abow = 0;
//			below = 0;
// 		}
		
	}
	
}
