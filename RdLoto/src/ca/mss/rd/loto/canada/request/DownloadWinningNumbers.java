package ca.mss.rd.loto.canada.request;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import ca.mss.rd.download.RdDownload;
import ca.mss.rd.loto.LotoConfig;

import ca.mss.rd.util.Timing;
import ca.mss.rd.util.runnable.RdExecutors;


public class DownloadWinningNumbers extends RdDownload {

	final static public String module = DownloadWinningNumbers.class.getSimpleName();
	final static public long serialVersionUID = module.hashCode();
	final static public org.apache.log4j.Logger logger = ca.mss.rd.util.Debug.getLogger(module);

	static public boolean 	DOWNLOAD_TO_RAM = true;
	static public boolean 	DOWNLOAD_TO_FILE = true;

	public DownloadWinningNumbers() {
		super(LotoConfig.LOTO_CANADA_WINNING_NUMBERS_DOWNLOAD_URL);
	}

	
	@Override
	public boolean isDownloadToFile() {
		return DOWNLOAD_TO_FILE;
	}

	@Override
	public boolean isDownloadToRAM() {
		return DOWNLOAD_TO_RAM;
	}

	@Override
	public String getDownloadFile() {
		return LotoConfig.LOTO_CANADA_WINNING_NUMBERS_DOWNLOAD_FILE;
	}


	public static void main(String[] args) throws Throwable {

		Timing time = new Timing();

		DownloadWinningNumbers dwn;
		
		ExecutorService executor = RdExecutors.newExecutorsPool(3, module, true, Thread.NORM_PRIORITY);
		executor.execute(dwn=new DownloadWinningNumbers());
		executor.execute(new DownloadWinningNumbers());
		executor.execute(new DownloadWinningNumbers());
		executor.execute(new DownloadWinningNumbers());
		executor.execute(new DownloadWinningNumbers());

		
		executor.shutdown();
		if( !executor.awaitTermination(30, TimeUnit.SECONDS) )
			logger.error(module+" terminated [time=" + time.total()+ "]");
		
		logger.info(module+" done [time=" + time.total()+ "][new="+dwn.isNew()+"][exists="+dwn.isExists()+"]");
	}
}
