package ca.mss.rd.trader.server.wkf;

import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ca.mss.rd.chart.point.DrawPoint;
import ca.mss.rd.flow.IContext;
import ca.mss.rd.flow.prop.RdProp;
import ca.mss.rd.trader.model.MarketTrend;
import ca.mss.rd.trader.model.PNLModel;
import ca.mss.rd.trader.model.Quote;
import ca.mss.rd.trader.model.SpreadModel;
import ca.mss.rd.trader.model.TradeModel;
import ca.mss.rd.trader.model.TradeOrderReport;
import ca.mss.rd.trader.model.indicator.IndicatorBLTrend;
import ca.mss.rd.trader.model.indicator.IndicatorBreakLine;
import ca.mss.rd.trader.model.indicator.IndicatorBreakLineMM;
import ca.mss.rd.trader.model.indicator.IndicatorBreakTime;
import ca.mss.rd.trader.server.Trader;
import ca.mss.rd.util.Logger;

public class ContextProcessQuote implements IContext<Object> {

	final private PropertyChangeSupport pcs;
	
	final public String symbol;
	final public SpreadModel spread = new SpreadModel();
	final public PNLModel pnl = new PNLModel(this);
	
	final public TradeModel<TradeOrderReport> tm = new TradeModel<TradeOrderReport>(){
		@Override
		public TradeOrderReport newTradeOrderReport(double avgSpread) {
			return new TradeOrderReport(this, avgSpread);
		}
		
	}; 
	public MarketTrend marketTrend;
	
	final public List<IndicatorBreakLine> bl = new ArrayList<IndicatorBreakLine>(0);
	final public List<IndicatorBreakLineMM> blmm = new ArrayList<IndicatorBreakLineMM>(0);
	final public List<IndicatorBreakTime> btime = new ArrayList<IndicatorBreakTime>(0);
	final public List<IndicatorBLTrend> blt = new ArrayList<IndicatorBLTrend>(0);
	

	public Quote quote;
	public long time;
	public double mid;
	public double val;

	public boolean startBSB, startSBB, stopTrade, isExtremum;

	final public Set<String> charts = new LinkedHashSet<String>(0);
	final public Set<String> traces = new LinkedHashSet<String>(0);
	final public Set<DrawPoint> points = new LinkedHashSet<DrawPoint>(0);

	public ContextProcessQuote(String quoteName, PropertyChangeSupport pcs) {
		this.symbol = quoteName;
		this.pcs = pcs;
		this.quote = new Quote(quoteName);
		clear();
	}

	@Override
	final public void clear(){
		assert(Logger.INDICATOR.isOn? Logger.INDICATOR.printf("ContextProcessQuote.clean"): true);
		spread.clear();
		pnl.clear();
		quote.clear();
		tm.clear();
		
		bl.clear();
		blmm.clear();
		btime.clear();
		blt.clear();

		marketTrend = MarketTrend.NA;
		startBSB = startSBB = stopTrade = isExtremum = false;
	}

	
	final public void drawPoint(DrawPoint[] drawPoint) {
		sendEvent(Trader.EVENT_CHART_DRAW, "", drawPoint);
	}
	
	final public void sendEvent(String eventName, Object valold, Object valnew) {
		pcs.firePropertyChange(eventName, valold, valnew);
	}

	@Override
	public Object getWkfProp() {
		throw new RuntimeException("Not implemented!");
	}

	@Override
	public void setEnvProp(Map<String, RdProp> envProp) {
		throw new RuntimeException("Not implemented!");
	}

	@Override
	public Map<String, RdProp> getEnvProp() {
		throw new RuntimeException("Not implemented!");
	}
	
}
