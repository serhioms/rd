package ca.mss.rd.loto.canada.request;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import ca.mss.rd.download.RdDownload;
import ca.mss.rd.loto.LotoConfig;
import ca.mss.rd.util.Timing;
import ca.mss.rd.util.UtilHttp;
import ca.mss.rd.util.UtilHttp.RequestType;
import ca.mss.rd.util.runnable.RdExecutors;

public class DownloadRate extends RdDownload {

	final static public String module = DownloadRate.class.getSimpleName();
	final static public long serialVersionUID = module.hashCode();
	
	static public boolean 	DOWNLOAD_TO_RAM = true;
	static public boolean 	DOWNLOAD_TO_FILE = false;

	static public String GOOGLE_DOWNLOAD_RATE_URL = "https://www.google.com/finance/converter?a=1&from=%s&to=%s";

	public DownloadRate() {
		super(GOOGLE_DOWNLOAD_RATE_URL, RequestType.GET);
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
		return "test.txt";
	}
	
	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {

		Timing time = new Timing();
		
		DownloadRate dwp = new DownloadRate();
		
		dwp.download();
		
		logger.info(module+" done [time=" + time.total()+ "][new="+dwp.isNew()+"][exists="+dwp.isExists()+"]");
	}
	

}


