package ng.ca.mss.rd.trader;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.testng.annotations.Test;

import ca.mss.rd.trader.model.indicator.IndicatorBreakLineMM;
import ca.mss.rd.trader.server.Trader;
import ca.mss.rd.util.Logger;
import ca.mss.rd.util.UtilDateTime;
import ca.mss.rd.util.UtilSort;

public class TestQuoteTrend extends TestProcessQuoteFlowBase {
	
	
	@Override
	protected String suiteName() {
		return "TestQuoteTrend";
	}

//	@Test(singleThreaded = true) public void TradeEURUSD() throws InterruptedException {		workflow = Trader.BackOffice.quoteSelect(quote="EURUSD", true);		oanda.createThread().run();	}
	
//	@Test(singleThreaded = true) public void TradeGBPUSD() throws InterruptedException {workflow = Trader.BackOffice.quoteSelect(quote="GBPUSD", true);oanda.createThread().run();}
//	@Test(singleThreaded = true) public void TradeUSDCHF() throws InterruptedException {workflow = Trader.BackOffice.quoteSelect(quote="USDCHF", true);oanda.createThread().run();}
	@Test(singleThreaded = true) public void TradeUSDJPY() throws InterruptedException {workflow = Trader.BackOffice.quoteSelect(quote="USDJPY", true);oanda.createThread().run();}
//	@Test(singleThreaded = true) public void TradeAUDUSD() throws InterruptedException {workflow = Trader.BackOffice.quoteSelect(quote="AUDUSD", true);oanda.createThread().run();}
//	@Test(singleThreaded = true) public void TradeUSDCAD() throws InterruptedException {workflow = Trader.BackOffice.quoteSelect(quote="USDCAD", true);oanda.createThread().run();}
//	@Test(singleThreaded = true) public void TradeXAUUSD() throws InterruptedException {workflow = Trader.BackOffice.quoteSelect(quote="XAUUSD", true);oanda.createThread().run();}
//	@Test(singleThreaded = true) public void TradeEURJPY() throws InterruptedException {workflow = Trader.BackOffice.quoteSelect(quote="EURJPY", true);oanda.createThread().run();}
//	@Test(singleThreaded = true) public void TradeEURGBP() throws InterruptedException {workflow = Trader.BackOffice.quoteSelect(quote="EURGBP", true);oanda.createThread().run();}
//	@Test(singleThreaded = true) public void TradeEURCHF() throws InterruptedException {workflow = Trader.BackOffice.quoteSelect(quote="EURCHF", true);oanda.createThread().run();}
//	@Test(singleThreaded = true) public void TradeUSDCNY() throws InterruptedException {workflow = Trader.BackOffice.quoteSelect(quote="USDCNY", true);oanda.createThread().run();}
//	@Test(singleThreaded = true) public void TradeEURSEK() throws InterruptedException {workflow = Trader.BackOffice.quoteSelect(quote="EURSEK", true);oanda.createThread().run();}
//	@Test(singleThreaded = true) public void TradeXAGUSD() throws InterruptedException {workflow = Trader.BackOffice.quoteSelect(quote="XAGUSD", true);oanda.createThread().run();}
//	@Test(singleThreaded = true) public void TradeUSDDKK() throws InterruptedException {workflow = Trader.BackOffice.quoteSelect(quote="USDDKK", true);oanda.createThread().run();}
//	@Test(singleThreaded = true) public void TradeNZDUSD() throws InterruptedException {workflow = Trader.BackOffice.quoteSelect(quote="NZDUSD", true);oanda.createThread().run();}
//	@Test(singleThreaded = true) public void TradeCHFJPY() throws InterruptedException {workflow = Trader.BackOffice.quoteSelect(quote="CHFJPY", true);oanda.createThread().run();}
//	@Test(singleThreaded = true) public void TradeGBPCHF() throws InterruptedException {workflow = Trader.BackOffice.quoteSelect(quote="GBPCHF", true);oanda.createThread().run();}
//	@Test(singleThreaded = true) public void TradeNZDJPY() throws InterruptedException {workflow = Trader.BackOffice.quoteSelect(quote="NZDJPY", true);oanda.createThread().run();}
//	@Test(singleThreaded = true) public void TradeEURNZD() throws InterruptedException {workflow = Trader.BackOffice.quoteSelect(quote="EURNZD", true);oanda.createThread().run();}
//	@Test(singleThreaded = true) public void TradeCADJPY() throws InterruptedException {workflow = Trader.BackOffice.quoteSelect(quote="CADJPY", true);oanda.createThread().run();}
//	@Test(singleThreaded = true) public void TradeEURCAD() throws InterruptedException {workflow = Trader.BackOffice.quoteSelect(quote="EURCAD", true);oanda.createThread().run();}
//	@Test(singleThreaded = true) public void TradeUS30USD() throws InterruptedException {workflow = Trader.BackOffice.quoteSelect(quote="US30USD", true);oanda.createThread().run();}
//	@Test(singleThreaded = true) public void TradeSPX500USD() throws InterruptedException {workflow = Trader.BackOffice.quoteSelect(quote="SPX500USD", true);oanda.createThread().run();}
//	@Test(singleThreaded = true) public void TradeBCOUSD() throws InterruptedException {workflow = Trader.BackOffice.quoteSelect(quote="BCOUSD", true);oanda.createThread().run();}
//	@Test(singleThreaded = true) public void TradeWTICOUSD() throws InterruptedException {workflow = Trader.BackOffice.quoteSelect(quote="WTICOUSD", true);oanda.createThread().run();}
//	@Test(singleThreaded = true) public void TradeDE30EUR() throws InterruptedException {workflow = Trader.BackOffice.quoteSelect(quote="DE30EUR", true);oanda.createThread().run();}
//	@Test(singleThreaded = true) public void TradeUK100GBP() throws InterruptedException {workflow = Trader.BackOffice.quoteSelect(quote="UK100GBP", true);oanda.createThread().run();}
//	@Test(singleThreaded = true) public void TradeJP225USD() throws InterruptedException {workflow = Trader.BackOffice.quoteSelect(quote="JP225USD", true);oanda.createThread().run();}
//	@Test(singleThreaded = true) public void TradeHK33HKD() throws InterruptedException {workflow = Trader.BackOffice.quoteSelect(quote="HK33HKD", true);oanda.createThread().run();}
//	@Test(singleThreaded = true) public void TradeNAS100USD() throws InterruptedException {workflow = Trader.BackOffice.quoteSelect(quote="NAS100USD", true);oanda.createThread().run();}
//	@Test(singleThreaded = true) public void TradeXCUUSD() throws InterruptedException {workflow = Trader.BackOffice.quoteSelect(quote="XCUUSD", true);oanda.createThread().run();}
//	@Test(singleThreaded = true) public void TradeGBPJPY() throws InterruptedException {workflow = Trader.BackOffice.quoteSelect(quote="GBPJPY", true);oanda.createThread().run();}
//	@Test(singleThreaded = true) public void TradeAUDJPY() throws InterruptedException {workflow = Trader.BackOffice.quoteSelect(quote="AUDJPY", true);oanda.createThread().run();}
//	@Test(singleThreaded = true) public void TradeAUDCAD() throws InterruptedException {workflow = Trader.BackOffice.quoteSelect(quote="AUDCAD", true);oanda.createThread().run();}
//	@Test(singleThreaded = true) public void TradeUSB05YUSD() throws InterruptedException {workflow = Trader.BackOffice.quoteSelect(quote="USB05YUSD", true);oanda.createThread().run();}
//	@Test(singleThreaded = true) public void TradeUSB10YUSD() throws InterruptedException {workflow = Trader.BackOffice.quoteSelect(quote="USB10YUSD", true);oanda.createThread().run();}
//	@Test(singleThreaded = true) public void TradeEU50EUR() throws InterruptedException {workflow = Trader.BackOffice.quoteSelect(quote="EU50EUR", true);oanda.createThread().run();}
//	@Test(singleThreaded = true) public void TradeDE10YBEUR() throws InterruptedException {workflow = Trader.BackOffice.quoteSelect(quote="DE10YBEUR", true);oanda.createThread().run();}
//	@Test(singleThreaded = true) public void TradeUK10YBGBP() throws InterruptedException {workflow = Trader.BackOffice.quoteSelect(quote="UK10YBGBP", true);oanda.createThread().run();}
//	@Test(singleThreaded = true) public void TradeNATGASUSD() throws InterruptedException {workflow = Trader.BackOffice.quoteSelect(quote="NATGASUSD", true);oanda.createThread().run();}
//	@Test(singleThreaded = true) public void TradeWHEATUSD() throws InterruptedException {workflow = Trader.BackOffice.quoteSelect(quote="WHEATUSD", true);oanda.createThread().run();}
//	@Test(singleThreaded = true) public void TradeSUGARUSD() throws InterruptedException {workflow = Trader.BackOffice.quoteSelect(quote="SUGARUSD", true);oanda.createThread().run();}
//	@Test(singleThreaded = true) public void TradeXPTUSD() throws InterruptedException {workflow = Trader.BackOffice.quoteSelect(quote="XPTUSD", true);oanda.createThread().run();}
//	@Test(singleThreaded = true) public void TradeXPDUSD() throws InterruptedException {workflow = Trader.BackOffice.quoteSelect(quote="XPDUSD", true);oanda.createThread().run();}
//	@Test(singleThreaded = true) public void TradeAU200AUD() throws InterruptedException {workflow = Trader.BackOffice.quoteSelect(quote="AU200AUD", true);oanda.createThread().run();}
//	@Test(singleThreaded = true) public void TradeSG30SGD() throws InterruptedException {workflow = Trader.BackOffice.quoteSelect(quote="SG30SGD", true);oanda.createThread().run();}

	@Override
	protected void suiteReport() {
        if( Logger.TREND_ANALIZER_SORT.isOn ){
		
		    UtilSort.sort(sortedResult, new Comparator<Result>() {
				@Override
				public int compare(Result a, Result b) {
					return (a.bsbSum+a.sbbSum) > (b.bsbSum+b.sbbSum)? -1: 1;
				}
		    });
		    
	        for(Result result: sortedResult ){
	        	print(result);
	        }
        }
	}


	@Override
	protected void testReport() {
		if( Logger.TREND_ANALIZER.isOn ){ 
			Logger.TREND_ANALIZER_TOTAL.printf("\n%s", HourCumulativeRESULTs);
		} else if( Logger.TREND_ANALIZER_TOTAL.isOn ){
			HourCumulativeRESULTs = "";
			eventHandler_HourCumulativeResult(0);
			Logger.TREND_ANALIZER_TOTAL.printf("\n%s", HourCumulativeRESULTs);
		}
        if( Logger.TREND_ANALIZER_SORT.isOn ){
        	if( result != null ){
	        	Logger.TREND_ANALIZER_SORT.printf("Quote %s done - %d results added.", quote, result.length);
				for(int i=0; i<result.length; i++){
					if( result[i].pipe > 0.9 ){
						result[i].quote = quote;
						sortedResult.add(result[i]);
					}
				}
        	}
        }
	}

	/*
	 * REPORTS
	 */
	static public class Result {
		public double pipe, bsbSum, minBsbSum, maxBsbSum, minBsbSumg, maxBsbSumg, sbbSum, minSbbSum, maxSbbSum, minSbbSumg, maxSbbSumg, minTime, maxTime, avgTime, minTimeg, maxTimeg, avgTimeg;
		public int extremumCounter, bsbDealCounter, sbbDealCounter; 
		public String quote;
	}

	public  List<Result> sortedResult = new ArrayList<Result>();
	public  Result[] result, prevResult;
	public String HourCumulativeRESULTs = "";
	
	@Override
	protected void eventHandler_CreateBlIndicator(Integer size) {
		result = new Result[size];
		prevResult = new Result[size];
		for(int i=0; i < size; i++ ){
			result[i] = new Result();
			prevResult[i] = new Result();
		}		
		HourCumulativeRESULTs = "";
	}

	@Override
	protected void analizeBlIndicator(IndicatorBreakLineMM indctr) {
//		result[indctr.index].extremumCounter = indctr.extremumCounter;
//		result[indctr.index].pipe = indctr.bl.pipe;
//		result[indctr.index].bsbDealCounter = indctr.bsbDealCounter;
//		result[indctr.index].bsbSum = indctr.bsbProfitSum;
//		result[indctr.index].minBsbSum = indctr.mmBsbProfit.min==Double.MAX_VALUE? 0: indctr.mmBsbProfit.min;
//		result[indctr.index].maxBsbSum = indctr.mmBsbProfit.max==Double.MIN_VALUE? 0: indctr.mmBsbProfit.max;
//		result[indctr.index].minBsbSumg = indctr.mmBsbProfitGlobal.min==Double.MAX_VALUE? 0: indctr.mmBsbProfitGlobal.min;
//		result[indctr.index].maxBsbSumg = indctr.mmBsbProfitGlobal.max==Double.MIN_VALUE? 0: indctr.mmBsbProfitGlobal.max;
//		result[indctr.index].sbbDealCounter = indctr.sbbDealCounter;
//		result[indctr.index].sbbSum = indctr.sbbProfitSum;
//		result[indctr.index].minSbbSum = indctr.mmSbbProfit.min==Double.MAX_VALUE? 0: indctr.mmSbbProfit.min;
//		result[indctr.index].maxSbbSum = indctr.mmSbbProfit.max==Double.MIN_VALUE? 0: indctr.mmSbbProfit.max;
//		result[indctr.index].minSbbSumg = indctr.mmSbbProfitGlobal.min==Double.MAX_VALUE? 0: indctr.mmSbbProfitGlobal.min;
//		result[indctr.index].maxSbbSumg = indctr.mmSbbProfitGlobal.max==Double.MIN_VALUE? 0: indctr.mmSbbProfitGlobal.max;
//		result[indctr.index].minTime = indctr.mmtimesec.min;
//		result[indctr.index].maxTime = indctr.mmtimesec.max;
//		result[indctr.index].minTimeg = indctr.mmtimesecg.min;
//		result[indctr.index].maxTimeg = indctr.mmtimesecg.max;
//		result[indctr.index].avgTime = indctr.avgtimesec.getAverage();
//		result[indctr.index].avgTimeg = indctr.avgtimesecg.getAverage();
	}


	@Override
	protected void eventHandler_HourCumulativeResult(Integer counter) {
		
		if( counter > 0 ){
			HourCumulativeRESULTs += String.format("CUMULATIVE RESULTS AFTER %s\n", UtilDateTime.formatTime(counter*3*1000));
		}


		for(int i=0; i < result.length; i++ ){
			if( result[i].extremumCounter > 0 )
				HourCumulativeRESULTs += String.format("%2d) %2.1f  #%-3d BSB=%-6.1f {%4.1f,%4.1f}   #%-3d SBB=%-6.1f {%4.1f,%4.1f} #%-4d time=%s {%s - %s}\n" 
						,i 
						,result[i].pipe 
						,result[i].bsbDealCounter 
						,result[i].bsbSum 
						,result[i].minBsbSumg 
						,result[i].maxBsbSumg 
						,result[i].sbbDealCounter 
						,result[i].sbbSum 
						,result[i].minSbbSumg 
						,result[i].maxSbbSumg 
						,result[i].extremumCounter
						,UtilDateTime.formatTime(result[i].avgTimeg)
						,UtilDateTime.formatTime(result[i].minTimeg) 
						,UtilDateTime.formatTime(result[i].maxTimeg)
					);
		}		
		HourCumulativeRESULTs += ("--------------------------------------------------------------------------------------------------------------\n");

		if( Logger.TREND_ANALIZER_1H.isOn){
			Logger.TREND_ANALIZER_1H.printf("RESULTS AFTER %s", UtilDateTime.formatTime(counter*3.0*1000));
			
			for(int i=0; i < result.length; i++ ){
				if( (result[i].extremumCounter - prevResult[i].extremumCounter) > 0 )
					Logger.TREND_ANALIZER_1H.printf("%2d) %2.1f  #%-3d BSB=%-6.1f {%4.1f,%4.1f}   #%-3d SBB=%-6.1f {%4.1f,%4.1f} #%-4d time=%s {%s - %s}" 
							,i 
							,result[i].pipe 
							,result[i].bsbDealCounter  - prevResult[i].bsbDealCounter
							,result[i].bsbSum - prevResult[i].bsbSum 
							,result[i].minBsbSum
							,result[i].maxBsbSum
							,result[i].sbbDealCounter  - prevResult[i].sbbDealCounter
							,result[i].sbbSum - prevResult[i].sbbSum 
							,result[i].minSbbSum
							,result[i].maxSbbSum
							,result[i].extremumCounter - prevResult[i].extremumCounter
							,UtilDateTime.formatTime(result[i].avgTime)
							,UtilDateTime.formatTime(result[i].minTime) 
							,UtilDateTime.formatTime(result[i].maxTime)
						);
			}		
			
			Logger.TREND_ANALIZER_1H.printf("--------------------------------------------------------------------------------------------------------------");
			

			// copy to previous 
			for(int i=0; i < result.length; i++ ){
				prevResult[i].avgTime = result[i].avgTime;
				prevResult[i].avgTimeg = result[i].avgTimeg;
				prevResult[i].bsbDealCounter = result[i].bsbDealCounter;
				prevResult[i].bsbSum = result[i].bsbSum;
				prevResult[i].sbbSum = result[i].sbbSum;
				prevResult[i].minBsbSum = result[i].minBsbSum;
				prevResult[i].maxBsbSum = result[i].maxBsbSum;
				prevResult[i].minBsbSumg = result[i].minBsbSumg;
				prevResult[i].maxBsbSumg = result[i].maxBsbSumg;
				prevResult[i].maxTime = result[i].maxTime;
				prevResult[i].minTime = result[i].minTime;
				prevResult[i].maxTimeg = result[i].maxTimeg;
				prevResult[i].minTimeg = result[i].minTimeg;
				prevResult[i].pipe = result[i].pipe;
				prevResult[i].minSbbSum = result[i].minSbbSum;
				prevResult[i].maxSbbSum = result[i].maxSbbSum;
				prevResult[i].minSbbSumg = result[i].minSbbSumg;
				prevResult[i].maxSbbSumg = result[i].maxSbbSumg;
				prevResult[i].sbbDealCounter = result[i].sbbDealCounter;
				prevResult[i].extremumCounter = result[i].extremumCounter;
			}
		}
	}

	private void print(Result result) {
		Logger.RESULT.printf("%10s[%2.1f] = TTL=%-6.1f | #%-3d BSB=%-6.1f {%4.1f,%4.1f}   #%-3d SBB=%-6.1f {%4.1f,%4.1f} #%-4d time=%s {%s - %s}" 
				,result.quote 
				,result.pipe 
				,result.bsbSum+result.sbbSum 
				,result.bsbDealCounter
				,result.bsbSum 
				,result.minBsbSum
				,result.maxBsbSum
				,result.sbbDealCounter
				,result.sbbSum 
				,result.minSbbSum
				,result.maxSbbSum
				,result.extremumCounter
				,UtilDateTime.formatTime(result.avgTime)
				,UtilDateTime.formatTime(result.minTime) 
				,UtilDateTime.formatTime(result.maxTime)
			);
	}

	
}
