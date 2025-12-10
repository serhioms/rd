package ca.mss.rd.loto;

import java.util.Date;

import ca.mss.rd.loto.canada.model.WinningNumbers;
import ca.mss.rd.loto.canada.model.WinningNumbersRow;
import ca.mss.rd.loto.canada.model.WinningPrizeRow;
import ca.mss.rd.loto.canada.parser.ParseWinningNumbers;
import ca.mss.rd.loto.canada.parser.ParseWinningPrize;
import ca.mss.rd.loto.canada.request.DownloadWinningNumbers;
import ca.mss.rd.loto.canada.request.DownloadWinningPrize;

import ca.mss.rd.util.Timing;
import ca.mss.rd.util.UtilDateTime;
import ca.mss.rd.util.UtilProperty;
import ca.mss.rd.util.UtilReflection;
import ca.mss.rd.util.cache.RdCache;
import ca.mss.rd.util.runnable.RdRunnable;

public class RefreshLoto extends RdRunnable {

	final static public String module = RefreshLoto.class.getSimpleName();
	final static public long serialVersionUID = module.hashCode();
	final static public org.apache.log4j.Logger logger = ca.mss.rd.util.Debug.getLogger(module);

	static public int MAX_NUMOF_THREADS = 10;
	static public int THREADS_PRIORITY = Thread.NORM_PRIORITY;

	static {
		UtilProperty.readConstants(DownloadWinningNumbers.class);
	}

	final static public String LAST_DOWNLOAD_TIMESTAMP = "last.download.timestamp";
	
	final private RdCache<String, String> cache;
	private Date lastDownloadTimestamp;

	private RefreshLoto() {
		super(module);
		cache = new RdCache<String, String>(module);
		cache.read();

		if( cache.contains( LAST_DOWNLOAD_TIMESTAMP ) )
			lastDownloadTimestamp = UtilDateTime.parse(cache.get(LAST_DOWNLOAD_TIMESTAMP));

	}

	public final Date getLastDownloadTimestamp() {
		return lastDownloadTimestamp;
	}

	@Override
	public void runThreadHandler() throws InterruptedException {
		refresh();
	}

	public void refresh() throws InterruptedException {
		
		final Date now = UtilDateTime.now();

		DownloadWinningNumbers dwn = new DownloadWinningNumbers();
		ParseWinningNumbers<WinningNumbersRow> pwn = new ParseWinningNumbers<WinningNumbersRow>(dwn);
		
		if( !dwn.isExists() || !UtilDateTime.isSameHour(now, lastDownloadTimestamp) || !pwn.isExists() ){
			dwn.download();
			cache.put(LAST_DOWNLOAD_TIMESTAMP, UtilDateTime.formatDate(lastDownloadTimestamp = now));
			cache.save();
		}
		
		if( !pwn.isExists() || dwn.isNew() ){
			pwn.parse();
		}

		WinningNumbers<WinningNumbersRow> wn = new WinningNumbers<WinningNumbersRow>();
		wn.readHeader();
		while( wn.hasNext() ){
			
			WinningNumbersRow row = wn.next();

			if( logger.isDebugEnabled()) logger.debug("Check for ["+row.record.getLotoname()+"]["+row.record.getDate()+"] prize downloading...");

			DownloadWinningPrize dwp = new DownloadWinningPrize(row.record.getLotoname(), row.record.getPrizerequest());
			ParseWinningPrize<WinningPrizeRow> pwp = new ParseWinningPrize<WinningPrizeRow>(dwp);

			if( !dwp.isExists() || dwn.isNew() || !pwp.isPrizeExists()){
				dwp.download();
			}
			
			if( !pwp.isPrizeExists() || dwp.isNew()  )
				try {
					Object pwpLotoSpecial = UtilReflection.instantiateObject("ca.mss.rd.loto.canada.parser.ParseWinningPrize"+row.record.getLotoname());
					UtilReflection.call(pwpLotoSpecial, "parse");
				}catch(Throwable t){
					pwp.parse();
				}
		}
	}


	/**
	 * @param args
	 */
	public static void main(String[] args) throws InterruptedException {
		logger.debug(module+" start...");

		Timing time = new Timing();
		
		new RefreshLoto().refresh();
		
		logger.debug(module+" done [time="+time.total()+"]");
	}

}
