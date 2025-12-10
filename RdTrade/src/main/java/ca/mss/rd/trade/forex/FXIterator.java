package ca.mss.rd.trade.forex;



public interface FXIterator {
	
	public String getHeader();
	public String getRow();

	public void reinitialize();
	public boolean hasNextQuotes();
	public FXQuote getNextQuotes() throws InterruptedException;

	public boolean isNew();

}
