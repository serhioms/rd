package ca.mss.rd.trader.src.oanda;

import java.beans.PropertyChangeListener;
import java.util.Date;
import java.util.Set;

import ca.mss.rd.util.runnable.RdRunnableInt;

public interface QuotesSource extends RdRunnableInt {

    public void addPropertyChangeListener(PropertyChangeListener listener);
	
    // Set latency
	public void setThresholdMls(long mls);
    public long getThresholdMls();
	
	public Set<String> getQuotes(String[] header);

	public void restart();
	public boolean isFirst();

	public void setStartDate(Date date);
	public Date getStartDate();
	
	public int getQuoteCounter();
}
