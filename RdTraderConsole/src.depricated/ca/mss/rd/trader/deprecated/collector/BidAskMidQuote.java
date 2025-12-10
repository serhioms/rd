package ca.mss.rd.trader.deprecated.collector;

import java.beans.PropertyChangeEvent;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import ca.mss.rd.math.FXSourceIterator;
import ca.mss.rd.parser.impl.Getter;
import ca.mss.rd.trader.model.Point;


@Deprecated
public class BidAskMidQuote extends RdCollector {

	public static final long serialVersionUID = BidAskMidQuote.class.hashCode();
	final static public String module = BidAskMidQuote.class.getSimpleName();
	final static public org.apache.log4j.Logger logger = ca.mss.rd.util.Debug.getLogger(module);
	
	static private int SIZE = 0;
	final static public int MID = SIZE++;
	final static public int BID = SIZE++;
	final static public int ASK = SIZE++;
	final static public int PREV_ASK = SIZE++;
	final static public int PREV_BID = SIZE++;
	final static public int PREV_MID = SIZE++;

	final protected FXSourceIterator source;
	final protected Set<String> quotes;
	
	protected long START_TIME;
	private Point point = new Point(SIZE, PREV_MID, MID);
	
	public BidAskMidQuote(FXSourceIterator source) {
		super(module);
		this.source = source;
		this.quotes = new HashSet<String>();
		restart();
	}

	@Override
	public void secundomer() throws InterruptedException {
		if( source.hasNext() ){
			Getter row = source.next();
			
			for(Iterator<String> iter=quotes.iterator(); iter.hasNext(); ){
				String quote = iter.next();
				
				point.val[PREV_ASK] = point.val[ASK];
				point.val[PREV_BID] = point.val[BID];
				point.val[PREV_MID] = point.val[MID];
				
				point.time = START_TIME++; //* 1.25;
				final double ask = point.val[ASK] = Double.parseDouble(row.get(quote+"Ask"));
				final double bid = point.val[BID] = Double.parseDouble(row.get(quote+"Bid"));
				point.val[MID] = (bid+ask)/2.0;

				pcs.firePropertyChange(quote, null, point);
			}
		} else {
			interrupt();
		}
	}

	public void restart(){
		source.reinitialize();
		point.reinitialize();
		START_TIME = 0L; //System.currentTimeMillis();
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		String name = evt.getPropertyName();
		logger.warn(module+": unhandled event [event="+name+"[from="+evt.getSource().getClass().getSimpleName()+"][value="+evt.getNewValue()+"]");
	}
	
	@Override
	public void addQuote(Set<String> quotes) {
		this.quotes.addAll(quotes);
	}
	
	@Override
    public final void interruptionHandler(Throwable t){
    	logger.debug(String.format("%s is stopped.", module));
    }

}
