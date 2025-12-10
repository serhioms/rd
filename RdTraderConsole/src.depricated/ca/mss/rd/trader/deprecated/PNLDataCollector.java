package ca.mss.rd.trader.deprecated;

import info.monitorenter.gui.chart.ITracePoint2D;
import info.monitorenter.gui.chart.TracePoint2D;

import java.math.BigDecimal;

import ca.mss.rd.trade.forex.FX;
import ca.mss.rd.trade.forex.FX.Currency;
import ca.mss.rd.trade.forex.FXIterator;
import ca.mss.rd.trader.deprecated.controller.ChartController;


public class PNLDataCollector extends BidAskDataCollector {

	final static public String module = PNLDataCollector.class.getName();
	final static public long serialVersionUID = module.hashCode();
	final static public org.apache.log4j.Logger logger = ca.mss.rd.util.Debug.getLogger(module);

	final static public double MAXIMUM_PROFIT = 3.0;
	final static public double MAXIMUM_LOSS = 3.0;
	
	final private FX bsbFx, sbbFx; 
	public boolean isBSB, isSBB;
	
	private BigDecimal bsbPnt;
	private BigDecimal sbbPnt;
	private BigDecimal pnlPnt;
	private BigDecimal pnlTotal;
	private BigDecimal bsbProfit;
	private BigDecimal sbbProfit;

	public PNLDataCollector(FXIterator quotesSource, ChartController man) {
		super(quotesSource, man);
		bsbFx = new FX(Currency.CAD, null, new BigDecimal("10000", FX.LEVERAGE));
		sbbFx = new FX(Currency.CAD, null, new BigDecimal("10000", FX.LEVERAGE));
		isBSB = false;
		isSBB = false;
	}

	public void restart(){
		pnlTotal = new BigDecimal(0.0);
		bsbProfit = new BigDecimal(0.0);
		sbbProfit = new BigDecimal(0.0);
		bsbPnt = new BigDecimal(0.0);
		sbbPnt = new BigDecimal(0.0);
		pnlPnt = new BigDecimal(0.0);
		super.restart();
	}

    public void doBuy() {
    	if( quote.ask != null )
    		bsbFx.doBuy(new BigDecimal("10000", FX.UNITS), new BigDecimal(quote.ask.doubleValue(), FX.PRICE));
    	isBSB = true;
    }

    public void doSell() {
    	if( quote.bid != null )
    		sbbFx.doSell(new BigDecimal("10000", FX.UNITS), new BigDecimal(quote.bid.doubleValue(), FX.PRICE));
		isSBB = true;
    }

    public void sellBack() {
		isBSB = false;
		pnlTotal = pnlTotal.add(bsbProfit);
		bsbProfit = new BigDecimal(0.0);
    }
	
    public void buyBack() {
		isSBB = false;
		pnlTotal = pnlTotal.add(sbbProfit);
		sbbProfit = new BigDecimal(0.0);
    }

	public final boolean isBSB() {
		return isBSB;
	}

	public final boolean isSBB() {
		return isSBB;
	}
    
	
	private ITracePoint2D getPnlPointBSB() {
		if( isBSB )
			return new TracePoint2D(time, bsbPnt.doubleValue());
		else
			return getZero();
	}

	private ITracePoint2D getPnlPointSBB() {
		return new TracePoint2D(time, sbbPnt.doubleValue());
	}

	private ITracePoint2D getPnlPoint() {
		return new TracePoint2D(time, pnlPnt.doubleValue() );
	}

	private ITracePoint2D getTotalPoint() {
		return new TracePoint2D(time, pnlTotal.doubleValue() );
	}

	private ITracePoint2D getZero() {
		return new TracePoint2D(time, 0.0);
	}
	
	@Override
	public void work() {
		if( !source.hasNextQuotes() ){
			super.interrupt();
			return;
		}

		super.work();
		try {
			calculate();
			if( ChartModel.isTraceShown("BSB") )
				pcs.firePropertyChange("BSB", null, getPnlPointBSB());
			
			if( ChartModel.isTraceShown("SBB") )
				pcs.firePropertyChange("SBB", null, getPnlPointSBB());

			if( ChartModel.isTraceShown("PNL") )
				pcs.firePropertyChange("PNL", null, getPnlPoint());
			
			if( ChartModel.isTraceShown("TOTAL") )
				pcs.firePropertyChange("TOTAL", null, getTotalPoint());

			if( ChartModel.isTraceShown("PnlDfA2") )
				pcs.firePropertyChange("PnlDfA2", null, new TracePoint2D(time, diff.alpha2));

			if( ChartModel.isTraceShown("PnlDfA1") )
				pcs.firePropertyChange("PnlDfA1", null, new TracePoint2D(time, diff.alpha1));
		
			if( ChartModel.isTraceShown("PnlDfB1") )
				pcs.firePropertyChange("PnlDfB1", null, new TracePoint2D(time, diff.betta1));
			
			if( ChartModel.isTraceShown("PnlDfA0") )
				pcs.firePropertyChange("PnlDfA0", null, new TracePoint2D(time, diff.alpha0));
		
			if( ChartModel.isTraceShown("PnlDfB0") )
				pcs.firePropertyChange("PnlDfB0", null, new TracePoint2D(time, diff.betta0));
		
			if( ChartModel.isTraceShown("PnlDfG0") )
				pcs.firePropertyChange("PnlDfG0", null, new TracePoint2D(time, diff.gamma0));

		}catch(Throwable t){
			logger.error("Can not add point to pnl trace", t);
		}
	}

	private void calculate(){
		if( source.isNew()) {
			pnlPnt = pnlTotal;
			if( isBSB ){
				if( bsbFx.isNotBought() ){
					doBuy();
				}
				bsbPnt = bsbFx.sellBack(new BigDecimal(quote.bid.doubleValue(), FX.PRICE), new BigDecimal("1.03292", FX.PRICE)).getPNL();
				pnlPnt = pnlPnt.add(bsbPnt);
				bsbProfit = bsbPnt;
			}
			if( isSBB ){
				if( sbbFx.isNotSold() ){
					doSell();
				}
				sbbPnt = sbbFx.buyBack(new BigDecimal(quote.ask.doubleValue(), FX.PRICE), new BigDecimal("1.03300", FX.PRICE)).getPNL();
				pnlPnt = pnlPnt.add(sbbPnt);
				sbbProfit = sbbPnt;
			}
//			if( isBSB || isSBB ){
//				double pnl = pnlPnt.doubleValue()-pnlTotal.doubleValue();
//				if( pnl > MAXIMUM_PROFIT  ){
//					// Stop profit
//					if( isBSB ){
//						RdForexTrader.app.sellBack();
//						RdForexTrader.app.doSell();
//					} 
//					if( isSBB ){
//						RdForexTrader.app.buyBack();
//						RdForexTrader.app.doBuy();
//					}
//				} else if( pnl < -MAXIMUM_LOSS ){
//					// Stop loss
//					if( isBSB ){
//						RdForexTrader.app.sellBack();
//					} else  if( isSBB ){
//						RdForexTrader.app.buyBack();
//					}
//				}
//			}
//			if( !isBSB && !isSBB ){
//				if( START_TIME < 50 )
//					candle = 50;
//				else 
//					candle = (midmax-mid)/(midmax-midmin)*100;
//				
//				if( candle > 75 ){
//					RdForexTrader.app.doSell();
//				} else if( candle < 25 ){
//					RdForexTrader.app.doBuy();
//				}
//			}
		}
	}

}
