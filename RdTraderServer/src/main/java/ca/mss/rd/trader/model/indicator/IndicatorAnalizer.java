package ca.mss.rd.trader.model.indicator;

import ca.mss.rd.chart.point.DrawPoint;
import ca.mss.rd.trader.model.MarketTrend;
import ca.mss.rd.trader.server.wkf.ContextProcessQuote;
import ca.mss.rd.util.Logger;
import ca.mss.rd.util.UtilDateTime;
import ca.mss.rd.util.color.MyColor;

public class IndicatorAnalizer extends IndicatorDefault {
	
	private static final double ZEROP = Double.MIN_NORMAL;
	private static final double ZERON = -Double.MIN_NORMAL;

	final private ContextProcessQuote context;
	
	private String[] chartName;
	private String[] traceName;
	private DrawPoint[] drawPoint;

	long quoteCounter;
	double avgSpread;
	IndicatorBreakLineMM indctr;

	private double sumProfit, sumProfitBSB, sumProfitSBB;
	MarketTrend[] oldTrend;
	
	private double bsb, sbb;
	String[] oldReport;
	
	public IndicatorAnalizer(ContextProcessQuote context) {
		super("Analizer");
		this.context = context;
		init();
		populate(context.charts, chartName);
		populate(context.traces, traceName);
		populate(context.points, drawPoint);
		clean();
	}

	private void init() {
		chartName = new String[]{String.format("Analizer%s", context.symbol)};
		
		traceName = new String[]{
				 context.symbol+"-PRFT-BSB" 
				,context.symbol+"-PRFT-SBB" 
			};
		
		drawPoint = new DrawPoint[] { 
				  new DrawPoint(context.symbol, chartName[0], traceName[0], MyColor.getColor("red").darker())
				 ,new DrawPoint(context.symbol, chartName[0], traceName[1], MyColor.getColor("green").darker())
			};
	}
	
	
	@Override
	final public void draw() {
		drawPoint[0].val = sumProfitBSB;
		drawPoint[0].time = context.time;
		drawPoint[0].time24 = context.quote.time24;

		drawPoint[1].val = sumProfitSBB;
		drawPoint[1].time = context.time;
		drawPoint[1].time24 = context.quote.time24;

		context.drawPoint(drawPoint);
	}
	
	@Override
	public void clean() {
		clean(drawPoint);
		sumProfit = sumProfitBSB = sumProfitSBB = 0.0;
		for(IndicatorBreakLineMM indctr: context.blmm){
			indctr.trend.clear();
		}
		oldReport = null;
		oldTrend = null;
	}  

	@Override
	public void process() {

		context.isExtremum = context.startBSB = context.startSBB = context.stopTrade = false;

		quoteCounter = context.quote.quoteCounter;
		avgSpread = context.spread.ma_fltr.getAverage();

		if( oldReport == null ){
			oldReport = new String[context.blmm.size()];
			oldTrend = new MarketTrend[context.blmm.size()];
			for(int i=0; i<oldReport.length; i++){
				oldReport[i] = "";
				oldTrend = null;
			}
			
		}
		
		for(int i=0, isize=context.blmm.size(); i<isize; i++){
			
			indctr = context.blmm.get(i);

			if( indctr.bl.isBroken() || indctr.bl.isMin || indctr.bl.isGoingUp() || indctr.bl.isMax || indctr.bl.isGoingDn() ){

				if( indctr.bl.isGoingDn() ){
					sbb = (indctr.maxBid-context.quote.ask)/context.spread.ma_fltr.getAverage();
					bsb = (context.quote.bid-indctr.maxAsk)/context.spread.ma_fltr.getAverage(); 
				} else if( indctr.bl.isGoingUp() ){
					sbb = (indctr.minBid-context.quote.ask)/context.spread.ma_fltr.getAverage();
					bsb = (context.quote.bid-indctr.minAsk)/context.spread.ma_fltr.getAverage(); 
				}

				boolean isExtremum = false, isBroken = false;
				if( indctr.bl.isMin ){
					isExtremum = true;
					indctr.minAsk = context.quote.ask;
					indctr.minBid = context.quote.bid;
					sbb = (indctr.maxBid-indctr.minAsk)/context.spread.ma_fltr.getAverage();
					bsb = (indctr.minBid-indctr.maxAsk)/context.spread.ma_fltr.getAverage(); 
					if( i == 1 ) {
						context.blmm.get(1).sumBSB = context.blmm.get(0).sumBSB;
						context.blmm.get(1).sumSBB = context.blmm.get(0).sumSBB;
					}
				} else if( indctr.bl.isMax ){
					isExtremum = true;
					indctr.maxAsk = context.quote.ask;
					indctr.maxBid = context.quote.bid;
					sbb = (indctr.minBid-indctr.maxAsk)/context.spread.ma_fltr.getAverage();
					bsb = (indctr.maxBid-indctr.minAsk)/context.spread.ma_fltr.getAverage(); 
					if( i == 1 ) {
						context.blmm.get(1).sumBSB = context.blmm.get(0).sumBSB;
						context.blmm.get(1).sumSBB = context.blmm.get(0).sumSBB;
					}
				} else if( indctr.bl.isBroken() ){
					isBroken = true;
				}
				
				indctr.sumBSB += bsb; 
				indctr.sumSBB += sbb;
				
				indctr.bsb.addValue(bsb);
				indctr.sbb.addValue(sbb);
				
				MarketTrend curTrend = bsb>sbb?MarketTrend.Bool:sbb>bsb?MarketTrend.Bear:MarketTrend.Flat;
				MarketTrend intTrend = indctr.sumBSB>indctr.sumSBB?MarketTrend.Bool:indctr.sumSBB>indctr.sumBSB?MarketTrend.Bear:MarketTrend.Flat;
				
				String report = String.format("%3.2f\t%s\t%s\t%s\t%s\t%s\t%s\t%f\t%f\t%f\t%f\t%f\t%s\t%f\t%f\t%s\t%s\t%f\t%f\t%f\t%f\t%s", 
						indctr.bl.pipe, indctr.bl.isBroken()?"broken":"", 
						indctr.bl.isMin?"min":"",indctr.bl.isGoingUp()?"up":"",indctr.bl.isMax?"max":"",indctr.bl.isGoingDn()?"dn":"", indctr.bl.isMin?"min":indctr.bl.isMax?"max":"", 
						context.quote.bid, context.quote.ask, context.spread.ma_fltr.getAverage(), 
						bsb, sbb, curTrend, 
						indctr.sumBSB, indctr.sumSBB, intTrend,
						bsb > ZEROP || sbb > ZEROP? "pos": (bsb < ZERON || sbb < ZERON? "neg":""),
						indctr.bsb.min, indctr.bsb.max, indctr.sbb.min, indctr.sbb.max,
						(bsb == indctr.bsb.min && bsb > ZEROP) || (bsb == indctr.bsb.max && bsb > ZEROP)|| (sbb == indctr.sbb.min && sbb > ZERON) || (sbb == indctr.sbb.max && sbb > ZERON) ?"extr":""
					);
				
				if( !report.equals(oldReport[i]) ){
					oldReport[i] = report;
					if( i == 0 ){
						Logger.TREND_ANALIZER2.printf("\t%s\t%s\t%s", UtilDateTime.now(context.quote.time24), report, "0.3+1.3");
					} else if( isBroken || isExtremum ){
						Logger.TREND_ANALIZER2.printf("\t%s\t%s\t%s", UtilDateTime.now(context.quote.time24), report, "0.3+1.3+B+E");
					} else {
						Logger.TREND_ANALIZER2.printf("\t%s\t%s\t%s", UtilDateTime.now(context.quote.time24), report, "1.3-B-E");
					}
				}
				
				if( isExtremum ) {
					indctr.sumBSB = 0.0; 
					indctr.sumSBB = 0.0;
					indctr.bsb.clear();
					indctr.sbb.clear();
					bsb = sbb = 0.0;
				}
			}

			if( indctr.bl.isMax ){
				context.isExtremum = true; 
				indctr.trend.addProfitBSB((indctr.bl.max - indctr.bl.min)/avgSpread);
			} else  if( indctr.bl.isMin ){
				context.isExtremum = true; 
				indctr.trend.addProfitSBB((indctr.bl.max - indctr.bl.min)/avgSpread);
			} else {
				continue;
			}
			
			// logging...
			if( indctr.isProfitable()  ){
				if( Logger.TREND_ANALIZER_PROFITABLE.isOn ){ 
					Logger.TREND_ANALIZER_PROFITABLE.printf("%-75s Trend profit = %-6.2f", report(), indctr.trend.getProfitTotal());
				}
			} else if( Logger.TREND_ANALIZER_VERBOSE.isOn ){
				Logger.TREND_ANALIZER_VERBOSE.printf("%s", report());
			} else if( Logger.TREND_ANALIZER_TRENDABLE.isOn ){
				Logger.TREND_ANALIZER_TRENDABLE.printf("%s", report());
			}
			
			// Signals
			if( indctr.isTrendable() ){
				sumProfit = indctr.trend.getProfit();
				sumProfitBSB = indctr.trend.getProfitBSB();
				sumProfitSBB = indctr.trend.getProfitSBB();
				
				if( context.marketTrend != indctr.trend.getMarketTrend() ){
					context.marketTrend = indctr.trend.getMarketTrend();
					switch( context.marketTrend ){
					case Flat:
						break;
					case Bool:
						context.startBSB = true;
						break;
					case Bear:
						context.startSBB = true;
						break;
					default:
						throw new RuntimeException(String.format("Unexpected trend for analyze trendable BL for  %s", indctr.trend.getMarketTrend()));
					}
				}
			}

			if( indctr.isProfitable() ){
				context.stopTrade = true;
				context.marketTrend = indctr.trend.getMarketTrend();
				switch( context.marketTrend ){
				case Flat:
					break;
				case Bool:
					context.startSBB = true;
					break;
				case Bear:
					context.startBSB = true;
					break;
				default:
					throw new RuntimeException(String.format("Unexpected trend for analyze profitable BL for %s", indctr.trend.getMarketTrend()));
				}
				
				// Clean trends up to Broken Level 
				for(int j=0; j<=i; j++){
					context.blmm.get(j).trend.init();
				}
			}
		}
	}
	
	public String report(){
		return String.format("%10s\t%s\t%3s[%2.2f][%3s]: %s",
				String.format("%d.%d)", quoteCounter, indctr.trend.getTrendCounter()), 
				UtilDateTime.now(context.quote.time24), 
				indctr.trend.isBSB()?"BSB":"SBB", 
				indctr.bl.pipe, 
				indctr.bl.getIdent(), 
				indctr.trend.report());
	}

}
