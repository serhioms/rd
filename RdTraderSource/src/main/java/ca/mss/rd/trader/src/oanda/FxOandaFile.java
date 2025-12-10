package ca.mss.rd.trader.src.oanda;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import ca.mss.rd.trade.parser.oanda.OandaParserNew;
import ca.mss.rd.trade.parser.oanda.OandaParserNewRow;
import ca.mss.rd.util.Logger;
import ca.mss.rd.util.runnable.RdRunnableInfinite;

public class FxOandaFile extends RdRunnableInfinite implements QuotesSource {

	final static public String module = FxOandaFile.class.getSimpleName();
	
	static public String FILE_DATE_FORMATTER = "yyyMMdd-kkmmss-SSS";
	static public String ROW_DATE_FORMATTER = "yy:MM:dd";
	static public String ROW_TIME_FORMATTER = "kk:mm:ss.SSS";
	static public String ROW_DATE_TIME_FORMATTER = ROW_DATE_FORMATTER+" "+ROW_TIME_FORMATTER;
	static public String ROW_TIME_REQ_FORMATTER = "ss.SSS";
	
	static public String OANDA_FILE = "/Users/smoskovskiy/workspace/rd/RdTraderConsole/data/Oanda-Online/2014/07-Jul/27/20140702=Oanda-Online=3-sec.txt";
	
	private final OandaParserNew<OandaParserNewRow> parser;
	private OandaParserNewRow row;
	private long thresholdMls;
	private boolean isHeader;
	
	private Date startDate;
	private int quoteCounter;

	static public FxOandaFile instance() {
		return new FxOandaFile();
	}	

	
	@Override
	final public int getQuoteCounter() {
		return quoteCounter;
	}


	public FxOandaFile() {
		super(module);
		parser = new OandaParserNew<OandaParserNewRow>(OANDA_FILE);
		restart();
	}

	@Override
	public final String toString() {
		return "OandaFile";
	}

	@Override
	public boolean isFirst() {
		return isHeader;
	}

	@Override
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	
	@Override
	public Date getStartDate() {
		return startDate;
	}

	
	@Override
	public void runThreadHandler() throws InterruptedException {
		if( parser.hasNext() ){
			row = parser.next();
			quoteCounter++;
			
			if( isHeader ){
				isHeader = false;
				pcs.firePropertyChange(QuoteEvent.EVENT_HEADER, null, row.record.getHeader());

				assert( Logger.OANDA.isOn ? Logger.OANDA.printf("Process header %5d) %s", quoteCounter, Arrays.toString(row.record.getHeader())): true);
			}

			pcs.firePropertyChange(QuoteEvent.EVENT_RECORD, null, row.record);
			
			assert( Logger.OANDA_VERBOSE.isOn ? (quoteCounter % 1000 == 0 ? Logger.OANDA_VERBOSE.printf("Process record %5d) %s", quoteCounter, row.record.getRow()): true): true);
			
		} else {
			interruptThread();
			restart();
		}
	}
	
	@Override
    public final void interruptionHandler(Throwable t){
		assert( Logger.OANDA_INTERRUPTION.isOn ? Logger.OANDA_INTERRUPTION.printf("%s %s", module, t.getMessage()): true);
		pcs.firePropertyChange(QuoteEvent.EVENT_STOP, null, t);
    }

	@Override
    public final void startThreadHandler() throws InterruptedException {
		assert( Logger.OANDA.isOn ? Logger.OANDA.printf("%s is started", module): true);
		pcs.firePropertyChange(QuoteEvent.EVENT_START, false, true);
    }

	@Override
    public final void endThreadHandler() {
		assert( Logger.OANDA.isOn ? Logger.OANDA.printf("%s is finished", module): true);
		pcs.firePropertyChange(QuoteEvent.EVENT_FINISH, false, true);
    }

	@Override
	public final Set<String> getQuotes(String[] header){
		final Set<String> quotesLabels = new HashSet<String>();
		
		for(int i=5; i<header.length; i+=8){
			quotesLabels.add(header[i].trim()); 
		}
		return quotesLabels;
	}
	
	@Override
	public void restart() {
		parser.readHeader();
		isHeader = true;
		quoteCounter = 0;
		if( serializer.availablePermits() != 1 ){
			serializer.drainPermits();
			serializer.release();
		}

	}

	@Override
	public void delayThreadHandler() throws InterruptedException {
		if( thresholdMls > 0 ){
			sleep(thresholdMls);
		}
	}

	/*
	 * Getters/Setters
	 */
	
	public final int getThresholdSec() {
		throw new RuntimeException("Not impemented [getThresholdSec]");
	}

	public final void setThresholdSec(int thresholdSec) {
		throw new RuntimeException("Not impemented [setThresholdSec]");
	}

	public final void setThresholdMls(long mls) {
		this.thresholdMls = mls;
	}

	
	public long getThresholdMls() {
		return thresholdMls;
	}


	/*
     * Property change support
     */
    final protected PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    final public void addPropertyChangeListener(PropertyChangeListener listener) {
    	pcs.addPropertyChangeListener(listener);
    }

}
