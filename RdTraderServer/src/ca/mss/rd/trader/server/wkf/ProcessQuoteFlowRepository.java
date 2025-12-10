package ca.mss.rd.trader.server.wkf;

import ca.mss.rd.flow.IActivity;
import ca.mss.rd.trader.model.indicator.IndicatorAnalizer;
import ca.mss.rd.trader.model.indicator.IndicatorBLTrend;
import ca.mss.rd.trader.model.indicator.IndicatorBidMidAsk;
import ca.mss.rd.trader.model.indicator.IndicatorBreakLine;
import ca.mss.rd.trader.model.indicator.IndicatorBreakLineMM;
import ca.mss.rd.trader.model.indicator.IndicatorBreakTime;
import ca.mss.rd.trader.model.indicator.IndicatorPNL;
import ca.mss.rd.trader.model.indicator.IndicatorRevAvg;
import ca.mss.rd.trader.model.indicator.IndicatorSpread;
import ca.mss.rd.trader.model.indicator.IndicatorTrade;
import ca.mss.rd.trader.model.indicator.IndicatorTradeHearCutter;
import ca.mss.rd.trader.model.indicator.IndicatorZero;

public class ProcessQuoteFlowRepository {



	@SuppressWarnings("unchecked")
	public static IActivity<Object,ContextProcessQuote> workflowPNLTrend(ProcessQuoteFlow flow, ContextProcessQuote context) {
		flow.setName("WorkflowPNLTrend");
		return flow.sequentialSet(
				 flow.newActivity(new IndicatorBidMidAsk(context))
				,flow.newActivity(new IndicatorSpread(context))
				,flow.newActivity(new IndicatorPNL(context))
				,flow.newActivity(new IndicatorTrade(context))
		);
	}

	
	
	
	
	@SuppressWarnings("unchecked")
	static public final IActivity<Object,ContextProcessQuote> workflowBreakLineTrend(ProcessQuoteFlow flow, ContextProcessQuote context) {
		int index = 0;
		return flow.sequentialSet(
				 flow.newActivity(new IndicatorBidMidAsk(context))
				,flow.newActivity(new IndicatorSpread(context))
				,flow.newActivity(new IndicatorZero(context, "white"))
				,flow.parallelSet(
						 flow.newActivity(new IndicatorBLTrend(context, index++, .33, "pink"))
						,flow.newActivity(new IndicatorBLTrend(context, index++, 1.0, "pink"))
						,flow.newActivity(new IndicatorBLTrend(context, index++, 2.0, "pink"))
						,flow.newActivity(new IndicatorBLTrend(context, index++, 3.0, "pink"))
						,flow.newActivity(new IndicatorBLTrend(context, index++, 4.0, "pink"))
						,flow.newActivity(new IndicatorBLTrend(context, index++, 5.0, "pink"))
						,flow.newActivity(new IndicatorBLTrend(context, index++, 6.0, "pink"))
				)
				,flow.parallelSet(
						 flow.newActivity(new IndicatorBreakLine(context, index++, .33, "orange"))
						,flow.newActivity(new IndicatorBreakLine(context, index++, 1.0, "orange"))
						,flow.newActivity(new IndicatorBreakLine(context, index++, 2.0, "orange"))
						,flow.newActivity(new IndicatorBreakLine(context, index++, 3.0, "orange"))
						,flow.newActivity(new IndicatorBreakLine(context, index++, 4.0, "orange"))
						,flow.newActivity(new IndicatorBreakLine(context, index++, 5.0, "orange"))
						,flow.newActivity(new IndicatorBreakLine(context, index++, 6.0, "orange"))
				)
		);
	}


	
	
	
	@SuppressWarnings({ "unchecked"})
	static public final IActivity<Object,ContextProcessQuote> workflowBreakTime(ProcessQuoteFlow flow, ContextProcessQuote context) {
		int index = 0;
		return flow.sequentialSet(
				 flow.newActivity(new IndicatorBidMidAsk(context))
				,flow.newActivity(new IndicatorSpread(context))
				,flow.parallelSet(
						 flow.newActivity(new IndicatorBreakTime(context, index++, .33, "pink"))
						,flow.newActivity(new IndicatorBreakTime(context, index++, 1.0, "pink"))
						,flow.newActivity(new IndicatorBreakTime(context, index++, 2.0, "pink"))
						,flow.newActivity(new IndicatorBreakTime(context, index++, 3.0, "pink"))
						,flow.newActivity(new IndicatorBreakTime(context, index++, 4.0, "pink"))
						,flow.newActivity(new IndicatorBreakTime(context, index++, 5.0, "pink"))
						,flow.newActivity(new IndicatorBreakTime(context, index++, 6.0, "pink"))
				)
		);
	}

	
	

	
	@SuppressWarnings("unchecked")
	static public final IActivity<Object,ContextProcessQuote> workflowReverseAverage(ProcessQuoteFlow flow, ContextProcessQuote context) {
		int index = 0;
		return flow.sequentialSet(
				 flow.newActivity(new IndicatorBidMidAsk(context))
				,flow.newActivity(new IndicatorSpread(context))
				,flow.newActivity(new IndicatorRevAvg(context))
				,flow.parallelSet(
						 flow.newActivity(new IndicatorBreakLineMM(context, index++, 0.33, "orange")) 
//						,flow.activity(new IndicatorBreakLineMM(context, index++, 1.0, "orange")) 
//						,flow.activity(new IndicatorBreakLineMM(context, index++, 1.1, "orange"))
//						,flow.activity(new IndicatorBreakLineMM(context, index++, 1.2, "orange")) 
						,flow.newActivity(new IndicatorBreakLineMM(context, index++, 1.3, "orange"))
//						,flow.activity(new IndicatorBreakLineMM(context, index++, 1.4, "orange")) 
//						,flow.activity(new IndicatorBreakLineMM(context, index++, 1.5, "orange"))
//						,flow.activity(new IndicatorBreakLineMM(context, index++, 2.0, "orange")) 
//						,flow.activity(new IndicatorBreakLineMM(context, index++, 3.0, "orange"))
//						,flow.activity(new IndicatorBreakLineMM(context, index++, 4.0, "orange")) 
//						,flow.activity(new IndicatorBreakLineMM(context, index++, 5.0, "orange"))
//						,flow.activity(new IndicatorBreakLineMM(context, index++, 6.0, "orange")) 
//						,flow.activity(new IndicatorBreakLineMM(context, index++, 7.0, "orange"))
//						,flow.activity(new IndicatorBreakLineMM(context, index++, 8.0, "orange"))
//						,flow.activity(new IndicatorBreakLineMM(context, index++, 9.0, "orange"))
				)
		);
	}
	
	
	
	

	@SuppressWarnings({ "unchecked"})
	static public final IActivity<Object,ContextProcessQuote> workflowAnalizer(ProcessQuoteFlow flow, ContextProcessQuote context) {
		int index = 0;
		return flow.sequentialSet(
				 flow.newActivity(new IndicatorBidMidAsk(context))
				,flow.newActivity(new IndicatorSpread(context))
				,flow.parallelSet(
						 flow.newActivity(new IndicatorBreakLineMM(context, index++, 0.33, "orange")) 
						,flow.newActivity(new IndicatorBreakLineMM(context, index++, 1.0, "orange")) 
						,flow.newActivity(new IndicatorBreakLineMM(context, index++, 1.1, "orange"))
						,flow.newActivity(new IndicatorBreakLineMM(context, index++, 1.2, "orange")) 
						,flow.newActivity(new IndicatorBreakLineMM(context, index++, 1.3, "orange"))
						,flow.newActivity(new IndicatorBreakLineMM(context, index++, 1.4, "orange")) 
						,flow.newActivity(new IndicatorBreakLineMM(context, index++, 1.5, "orange"))
						,flow.newActivity(new IndicatorBreakLineMM(context, index++, 2.0, "orange")) 
						,flow.newActivity(new IndicatorBreakLineMM(context, index++, 3.0, "orange"))
						,flow.newActivity(new IndicatorBreakLineMM(context, index++, 4.0, "orange")) 
						,flow.newActivity(new IndicatorBreakLineMM(context, index++, 5.0, "orange"))
						,flow.newActivity(new IndicatorBreakLineMM(context, index++, 6.0, "orange")) 
						,flow.newActivity(new IndicatorBreakLineMM(context, index++, 7.0, "orange"))
						,flow.newActivity(new IndicatorBreakLineMM(context, index++, 8.0, "orange"))
						,flow.newActivity(new IndicatorBreakLineMM(context, index++, 9.0, "orange"))
				)
				,flow.newActivity(new IndicatorAnalizer(context))
		);
	}

	
	
	
	@SuppressWarnings({ "unchecked"})
	static public final IActivity<Object,ContextProcessQuote> workflowHuntingTrend(ProcessQuoteFlow flow, ContextProcessQuote context) {
		int index = 0;
		return flow.sequentialSet(
				 flow.newActivity(new IndicatorBidMidAsk(context))
				,flow.newActivity(new IndicatorSpread(context))
				,flow.parallelSet(
						 flow.newActivity(new IndicatorBreakLineMM(context, index++, 0.33, "orange")) 
						,flow.newActivity(new IndicatorBreakLineMM(context, index++, 1.0, "orange")) 
						,flow.newActivity(new IndicatorBreakLineMM(context, index++, 1.1, "orange"))
						,flow.newActivity(new IndicatorBreakLineMM(context, index++, 1.2, "orange")) 
						,flow.newActivity(new IndicatorBreakLineMM(context, index++, 1.3, "orange"))
						,flow.newActivity(new IndicatorBreakLineMM(context, index++, 1.4, "orange")) 
						,flow.newActivity(new IndicatorBreakLineMM(context, index++, 1.5, "orange"))
						,flow.newActivity(new IndicatorBreakLineMM(context, index++, 2.0, "orange")) 
						,flow.newActivity(new IndicatorBreakLineMM(context, index++, 3.0, "orange"))
						,flow.newActivity(new IndicatorBreakLineMM(context, index++, 4.0, "orange")) 
						,flow.newActivity(new IndicatorBreakLineMM(context, index++, 5.0, "orange"))
						,flow.newActivity(new IndicatorBreakLineMM(context, index++, 6.0, "orange")) 
						,flow.newActivity(new IndicatorBreakLineMM(context, index++, 7.0, "orange"))
						,flow.newActivity(new IndicatorBreakLineMM(context, index++, 8.0, "orange"))
						,flow.newActivity(new IndicatorBreakLineMM(context, index++, 9.0, "orange"))
				)
				,flow.newActivity(new IndicatorAnalizer(context))
				,flow.newActivity(new IndicatorTradeHearCutter(context))
		);
	}

	
	
	
	@SuppressWarnings({ "unchecked"})
	static public final IActivity<Object,ContextProcessQuote> workflowBreakLine(ProcessQuoteFlow flow, ContextProcessQuote context) {
		int index = 0;
		return flow.sequentialSet(
				 flow.newActivity(new IndicatorBidMidAsk(context))
				,flow.newActivity(new IndicatorSpread(context))
				,flow.parallelSet(
						 flow.newActivity(new IndicatorBreakLineMM(context, index++, 0.33, "orange")) 
						,flow.newActivity(new IndicatorBreakLineMM(context, index++, 1.0, "orange")) 
						,flow.newActivity(new IndicatorBreakLineMM(context, index++, 1.1, "orange"))
						,flow.newActivity(new IndicatorBreakLineMM(context, index++, 1.2, "orange")) 
						,flow.newActivity(new IndicatorBreakLineMM(context, index++, 1.3, "orange"))
						,flow.newActivity(new IndicatorBreakLineMM(context, index++, 1.4, "orange")) 
						,flow.newActivity(new IndicatorBreakLineMM(context, index++, 1.5, "orange"))
						,flow.newActivity(new IndicatorBreakLineMM(context, index++, 2.0, "orange")) 
						,flow.newActivity(new IndicatorBreakLineMM(context, index++, 3.0, "orange"))
						,flow.newActivity(new IndicatorBreakLineMM(context, index++, 4.0, "orange")) 
						,flow.newActivity(new IndicatorBreakLineMM(context, index++, 5.0, "orange"))
						,flow.newActivity(new IndicatorBreakLineMM(context, index++, 6.0, "orange")) 
						,flow.newActivity(new IndicatorBreakLineMM(context, index++, 7.0, "orange"))
						,flow.newActivity(new IndicatorBreakLineMM(context, index++, 8.0, "orange"))
						,flow.newActivity(new IndicatorBreakLineMM(context, index++, 9.0, "orange"))
				)
		);
	}




}
