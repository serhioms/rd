package ca.mss.rd.trader.deprecated;

import info.monitorenter.gui.chart.ITrace2D;
import info.monitorenter.gui.chart.TracePoint2D;
import ca.mss.rd.trade.forex.FXIterator;
import ca.mss.rd.trade.forex.FXQuote;
import ca.mss.rd.trader.deprecated.controller.ChartController;
import ca.mss.rd.trader.model.Point;

import ca.mss.rd.util.UtilMath;
import ca.mss.rd.util.runnable.RdRunnable;

public class BidAskDataCollector extends RecordDataRunnable{

	final static public org.apache.log4j.Logger logger = ca.mss.rd.util.Debug.getLogger(module);
	
	final public static int POOL_SIZE = 10; //4; //10;
	final public static int POOL_FACTOR = 2; //10; //2;

	final protected FXIterator source;
	final protected ChartController man;
	
	protected FXQuote quote;
	protected double time = 0.0;
	
	protected long START_TIME = 0L; //System.currentTimeMillis();
	protected double bidaskBase = Double.MIN_VALUE;
	protected double log, mid, bid, ask;
	protected Double midprev;
	protected double midmin, midmax;
	protected double midmincnt, midmaxcnt;
	
	private MiniMidiMax[] mmm = new MiniMidiMax[POOL_SIZE];
	public Differential diff;
	
	protected boolean mi, ma, md;
	protected ITrace2D df;
	
	public BidAskDataCollector(FXIterator quotesSource, ChartController man) {
		super("fxtrade.oanda.ca");
		this.source = quotesSource;
		this.man = man;
		this.quote = null; //new FXQuote(man.pair);
		for(int i=0,size=POOL_FACTOR; i<mmm.length; i++) {
			mmm[i] = new MiniMidiMax(size);
			size *= POOL_FACTOR;
		}
		diff = new Differential();
		restart();
	}

	private void calculate() {
		// need it once to be calculated
		if( source.isNew()) {
			if( bidaskBase == Double.MIN_VALUE) {
				bidaskBase = UtilMath.ceil(quote.ask.doubleValue(), 0);
			}
		}
		
		//currTime = (double) (System.currentTimeMillis() - START_TIME) / 1000.0;
		time = START_TIME++ * 1.25/60.0;

		ask = quote.ask.doubleValue() - bidaskBase;
		bid = quote.bid.doubleValue() - bidaskBase;
		mid = (bid+ask)/2.0;

		if( midprev == null ){
			midprev = mid;
		}
		log = Math.log(mid/midprev);
		midprev = mid;

		// Resist/support
		midmin = 0;
		midmax = 0;
		midmincnt = 0;
		midmaxcnt = 0;
		
		Point p = new Point(1,0,0);
		p.val[0]=mid;
		p.time=time;
			
		for(int i=0; i<mmm.length; i++){
			mi = ChartModel.isTraceShown("Mn"+i);
			ma = ChartModel.isTraceShown("Mx"+i);
			md = ChartModel.isTraceShown("Md"+i);
			if( mi || ma || md ){
				mmm[i].push(p);
				if( mi ){
					midmincnt += 1;
					midmin += mmm[i].min.val[0];
				}
				if( ma ){
					midmaxcnt += 1;
					midmax += mmm[i].max.val[0];
				}
			}
		}
		if( midmincnt > 0 ){
			midmin /= midmincnt;		
			midmax /= midmaxcnt;		
		}

		diff.push(p);
	}

	private Point midPoint = new Point(1,0,0);
	
	@Override
	public void work() {

		if( !source.hasNextQuotes() ){
			super.interrupt();
			return;
		}
		
		try {
			quote = source.getNextQuotes();
			
			calculate();
			
			if( ChartModel.isTraceShown("ASK") )
				pcs.firePropertyChange("ASK", null, new TracePoint2D(time, ask));

			if( ChartModel.isTraceShown("BID") )
				pcs.firePropertyChange("BID", null, new TracePoint2D(time, bid));

			if( ChartModel.isTraceShown("MID") ){
				midPoint.time = time;
				midPoint.val[0] = mid;
				pcs.firePropertyChange("MID", null, new TracePoint2D(time, mid));
			}

			if( ChartModel.isTraceShown("LOG") )
				pcs.firePropertyChange("LOG", null, new TracePoint2D(time, log));
			
			if( ChartModel.isTraceShown("midN") )
				pcs.firePropertyChange("midN", null, new TracePoint2D(time, midmin));

			if( ChartModel.isTraceShown("midX") )
				pcs.firePropertyChange("midX", null, new TracePoint2D(time, midmax));
	
			if( ChartModel.isTraceShown("midN") && ChartModel.isTraceShown("midX") ){
				for(int i=0; i<mmm.length; i++){
					if( mmm[i].isValid() ){
						if( ChartModel.isTraceShown("Mn"+i) )
							pcs.firePropertyChange("Mn"+i, null, new TracePoint2D(time, mmm[i].min.val[0]));
			
						if( ChartModel.isTraceShown("Mx"+i) )
							pcs.firePropertyChange("Mx"+i, null, new TracePoint2D(time, mmm[i].max.val[0]));
			
						if( ChartModel.isTraceShown("Md"+i) )
							pcs.firePropertyChange("Md"+i, null, new TracePoint2D(time, mmm[i].mid));
					}
				}
			}

			if( ChartModel.isTraceShown("DfA") )
				if( diff.isValid() )
					pcs.firePropertyChange("DfA", null, new TracePoint2D(time, diff.alpha0));
			
			if( ChartModel.isTraceShown("DfB") )
				if( diff.isValid() )
					pcs.firePropertyChange("DfB", null, new TracePoint2D(time, diff.betta0));
			
			if( ChartModel.isTraceShown("DfG") )
				if( diff.isValid() )
					pcs.firePropertyChange("DfG", null, new TracePoint2D(time, diff.gamma0));
			
			if( isRecoderOn() ){
				dumpAppend(source.getRow());
			}
			
		}catch(Throwable t){
			logger.error("Can not calculate bid/ask points", t);
		}
	}

	public void restart(){
		source.reinitialize();
		time = 0.0;
		quote.ask = null;
		quote.bid = null;
		midprev = null;
		for( MiniMidiMax f: mmm) {
			f.clear();
		}
		START_TIME = 0L; //System.currentTimeMillis();
	}

	@Override
	public RdRunnable start(){
		if( isRecoderOn() ){
			dumpCreate(source.getHeader(), source.getClass().getSimpleName());
		}
		return super.start();
	}
	
	@Override
	public void setRecoderOn() {
		if( super.isRunning() ){
			dumpCreate(source.getHeader(), source.getClass().getSimpleName());
		}
		super.setRecoderOn();
	}
}
