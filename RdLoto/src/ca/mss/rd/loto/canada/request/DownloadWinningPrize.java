package ca.mss.rd.loto.canada.request;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import ca.mss.rd.download.RdDownload;
import ca.mss.rd.loto.LotoConfig;
import ca.mss.rd.util.Timing;
import ca.mss.rd.util.UtilHttp;
import ca.mss.rd.util.UtilHttp.RequestType;
import ca.mss.rd.util.runnable.RdExecutors;

public class DownloadWinningPrize extends RdDownload {

	final static public String module = DownloadWinningPrize.class.getSimpleName();
	final static public long serialVersionUID = module.hashCode();
	
	static public boolean 	DOWNLOAD_TO_RAM = true;
	static public boolean 	DOWNLOAD_TO_FILE = false;
	
	final public String lotoName;
	
	public DownloadWinningPrize(String lotoName, String requestParam) {
		super(LotoConfig.LOTO_CANADA_DOWNLOAD_PRIZE_URL, UtilHttp.toMapRequest(requestParam), RequestType.POST);
		this.lotoName = lotoName;
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
		return LotoConfig.getCanadaPrizeDownloadFile(lotoName);
	}
	
	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {

		Timing time = new Timing();
		
		DownloadWinningPrize dwp;
		
		ExecutorService executor = RdExecutors.newExecutorsPool(3, module, true, Thread.NORM_PRIORITY);
		executor.execute(dwp=new DownloadWinningPrize("LOTTOMAX", "gameID=73&drawNo=203&sdrawDate=1376096460000&=7spielID4"));
//		executor.execute(new DownloadWinningPrize("LOTTOMAX", "gameID=73&drawNo=203&sdrawDate=1376096460000&spielID=74"));
//		executor.execute(new DownloadWinningPrize("LOTTOMAX", "gameID=73&drawNo=203&sdrawDate=1376096460000&spielID=74"));
//		executor.execute(new DownloadWinningPrize("LOTTOMAX", "gameID=73&drawNo=203&sdrawDate=1376096460000&spielID=74"));
//		executor.execute(new DownloadWinningPrize("LOTTOMAX", "gameID=73&drawNo=203&sdrawDate=1376096460000&spielID=74"));
//		executor.execute(new DownloadWinningPrize("LOTTOMAX", "gameID=73&drawNo=203&sdrawDate=1376096460000&spielID=74"));
		
		executor.shutdown();
		if( !executor.awaitTermination(30, TimeUnit.SECONDS) )
			logger.error(module+" terminated [time=" + time.total()+ "]");

		logger.info(module+" done [time=" + time.total()+ "][new="+dwp.isNew()+"][exists="+dwp.isExists()+"]");
	}
	

}


