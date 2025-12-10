package ca.mss.rd.trader.src.oanda;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import ca.mss.rd.download.RdDownload;
import ca.mss.rd.parser.impl.GenericRecord;
import ca.mss.rd.trade.parser.oanda.OandaParser;
import ca.mss.rd.trade.parser.oanda.OandaParserNewRow;
import ca.mss.rd.util.Logger;
import ca.mss.rd.util.UtilDateTime;
import ca.mss.rd.util.UtilEncryption;
import ca.mss.rd.util.UtilMisc;
import ca.mss.rd.util.UtilProperty;
import ca.mss.rd.util.UtilRand;
import ca.mss.rd.util.encryption.RC4Oanda;
import ca.mss.rd.http.HttpException;
import ca.mss.rd.util.runnable.RdRunnable;

public class FxOandaOnline extends RdDownload implements QuotesSource {

	final static public String module = FxOandaOnline.class.getSimpleName();
	final static public long serialVersionUID = module.hashCode();
	//private final static org.apache.log4j.Logger logger = ca.mss.rd.util.Debug.getLogger(module);
	
	// testing only
	static private boolean IS_SIMULATION = false;

	static public String FILE_DATE_FORMATTER = "yyyMMdd-kkmmss-SSS";
	static public String ROW_DATE_FORMATTER = "yy:MM:dd";
	static public String ROW_TIME_FORMATTER = "kk:mm:ss.SSS";
	static public String ROW_TIME_REQ_FORMATTER = "ss.SSS";
	
	static public String URL = "http://fxtrade.oanda.com/lfr/rates_fxtrade-common";
	static public String FILE = "/Users/smoskovskiy/workspace/rd/RdTraderConsole/data/forex/oanda/oanda=%s=%d.txt";

	final static private int REQUEST_THRESHOULD_SEC = 1;

	static public boolean 	DOWNLOAD_TO_RAM = true;
	static public boolean 	DOWNLOAD_TO_FILE = false;
	
	static public long WAIT_AFTER_INTERRUPTION_SEC = 10;;
	
	static { 
		UtilProperty.readConstants(FxOandaOnline.class); 
	}
	
	private final RC4Oanda rc4;
	private final Row row;
	private final OandaParserNewRow orow;
	private boolean isHeader;
	
	private Date startDate;
	private int quoteCounter;

	@Override
	final public int getQuoteCounter() {
		return quoteCounter;
	}
	
	private FxOandaOnline() {
		super(URL);
		super.cache = null;
		row = this.new Row();
		orow = new OandaParserNewRow();
		rc4 = new RC4Oanda("aaf6cb4f0ced8a211c2728328597268509ade33040233a11af".getBytes());
		setThresholdMls(REQUEST_THRESHOULD_SEC*1000);
	}

	private static class Singlton {
		final public static FxOandaOnline instance = new FxOandaOnline();
	}
	
	static public FxOandaOnline instance() {
		return Singlton.instance;
	}	
	/*
	 * Download implementation 
	 */
	
	@Override
	public final boolean isDownloadToFile() {
		return DOWNLOAD_TO_FILE;
	}

	@Override
	public final boolean isDownloadToRAM() {
		return DOWNLOAD_TO_RAM;
	}

	@Override
	public final String getDownloadFile() {
		return String.format(FILE, UtilDateTime.format(getTimeon(), FILE_DATE_FORMATTER), REQUEST_THRESHOULD_SEC);
	}
	
	@Override
	public final void setFileContent(String fileContent) {

		if( fileContent.contains("503 Service Temporarily Unavailable") )
			throw new HttpException("Service Unavailable: "+URL);
		
		super.setFileContent(new String(rc4.decrypt(UtilEncryption.hexDecode(fileContent.getBytes()))));
	}

	@Override
	public void setStartDate(Date date) {
		this.startDate = date;
	}

	@Override
	public Date getStartDate() {
		return startDate;
	}
	
	@Override
	public final void download() throws InterruptedException {
		if( IS_SIMULATION ){
			Thread.sleep(190L + UtilRand.getRandLong(300L));
		} else {
			String tstamp = getTimeon().toString();
			assert( Logger.HTTP.isOn ? Logger.HTTP.printf("Oanda requested: %s", tstamp): true);
			download(UtilMisc.toMap("tstamp", tstamp));
			assert( Logger.HTTP.isOn ? Logger.HTTP.printf("Oanda  received: %s", row.row==null?"nothing!":row.row.substring(0, Math.min(row.row.length(), 50))): true);
			setStartDate(getTimeon());
		}

	}

	@Override
	public final String toString() {
		return "OandaOnline";
	}

	/*
	 * Row 
	 */
	final static private String[] HEADER = new String[]{"Subj", "Bid", "Ask", "1", "2", "Spread", "3", "4"};
	public class Row {
	
		public final String[] getHeader() {
			String row = !IS_SIMULATION? getFileContent(): "EURUSD\nEURUSDBid\nEURUSDAsk\nEURUSD1\nEURUSD2\nEURUSDSpread\nEURUSD3\nEURUSD4\nGBPUSD\nGBPUSDBid\nGBPUSDAsk\nGBPUSD1\nGBPUSD2\nGBPUSDSpread\nGBPUSD3\nGBPUSD4\nUSDCHF\nUSDCHFBid\nUSDCHFAsk\nUSDCHF1\nUSDCHF2\nUSDCHFSpread\nUSDCHF3\nUSDCHF4\nUSDJPY\nUSDJPYBid\nUSDJPYAsk\nUSDJPY1\nUSDJPY2\nUSDJPYSpread\nUSDJPY3\nUSDJPY4\nAUDUSD\nAUDUSDBid\nAUDUSDAsk\nAUDUSD1\nAUDUSD2\nAUDUSDSpread\nAUDUSD3\nAUDUSD4\nUSDCAD\nUSDCADBid\nUSDCADAsk\nUSDCAD1\nUSDCAD2\nUSDCADSpread\nUSDCAD3\nUSDCAD4\nXAUUSD\nXAUUSDBid\nXAUUSDAsk\nXAUUSD1\nXAUUSD2\nXAUUSDSpread\nXAUUSD3\nXAUUSD4\nEURJPY\nEURJPYBid\nEURJPYAsk\nEURJPY1\nEURJPY2\nEURJPYSpread\nEURJPY3\nEURJPY4\nEURGBP\nEURGBPBid\nEURGBPAsk\nEURGBP1\nEURGBP2\nEURGBPSpread\nEURGBP3\nEURGBP4\nEURCHF\nEURCHFBid\nEURCHFAsk\nEURCHF1\nEURCHF2\nEURCHFSpread\nEURCHF3\nEURCHF4\nUSDCNY\nUSDCNYBid\nUSDCNYAsk\nUSDCNY1\nUSDCNY2\nUSDCNYSpread\nUSDCNY3\nUSDCNY4\nEURSEK\nEURSEKBid\nEURSEKAsk\nEURSEK1\nEURSEK2\nEURSEKSpread\nEURSEK3\nEURSEK4\nXAGUSD\nXAGUSDBid\nXAGUSDAsk\nXAGUSD1\nXAGUSD2\nXAGUSDSpread\nXAGUSD3\nXAGUSD4\nUSDDKK\nUSDDKKBid\nUSDDKKAsk\nUSDDKK1\nUSDDKK2\nUSDDKKSpread\nUSDDKK3\nUSDDKK4\nNZDUSD\nNZDUSDBid\nNZDUSDAsk\nNZDUSD1\nNZDUSD2\nNZDUSDSpread\nNZDUSD3\nNZDUSD4\nCHFJPY\nCHFJPYBid\nCHFJPYAsk\nCHFJPY1\nCHFJPY2\nCHFJPYSpread\nCHFJPY3\nCHFJPY4\nGBPCHF\nGBPCHFBid\nGBPCHFAsk\nGBPCHF1\nGBPCHF2\nGBPCHFSpread\nGBPCHF3\nGBPCHF4\nNZDJPY\nNZDJPYBid\nNZDJPYAsk\nNZDJPY1\nNZDJPY2\nNZDJPYSpread\nNZDJPY3\nNZDJPY4\nEURNZD\nEURNZDBid\nEURNZDAsk\nEURNZD1\nEURNZD2\nEURNZDSpread\nEURNZD3\nEURNZD4\nCADJPY\nCADJPYBid\nCADJPYAsk\nCADJPY1\nCADJPY2\nCADJPYSpread\nCADJPY3\nCADJPY4\nEURCAD\nEURCADBid\nEURCADAsk\nEURCAD1\nEURCAD2\nEURCADSpread\nEURCAD3\nEURCAD4\nUS30USD\nUS30USDBid\nUS30USDAsk\nUS30USD1\nUS30USD2\nUS30USDSpread\nUS30USD3\nUS30USD4\nSPX500USD\nSPX500USDBid\nSPX500USDAsk\nSPX500USD1\nSPX500USD2\nSPX500USDSpread\nSPX500USD3\nSPX500USD4\nBCOUSD\nBCOUSDBid\nBCOUSDAsk\nBCOUSD1\nBCOUSD2\nBCOUSDSpread\nBCOUSD3\nBCOUSD4\nWTICOUSD\nWTICOUSDBid\nWTICOUSDAsk\nWTICOUSD1\nWTICOUSD2\nWTICOUSDSpread\nWTICOUSD3\nWTICOUSD4\nDE30EUR\nDE30EURBid\nDE30EURAsk\nDE30EUR1\nDE30EUR2\nDE30EURSpread\nDE30EUR3\nDE30EUR4\nUK100GBP\nUK100GBPBid\nUK100GBPAsk\nUK100GBP1\nUK100GBP2\nUK100GBPSpread\nUK100GBP3\nUK100GBP4\nJP225USD\nJP225USDBid\nJP225USDAsk\nJP225USD1\nJP225USD2\nJP225USDSpread\nJP225USD3\nJP225USD4\nHK33HKD\nHK33HKDBid\nHK33HKDAsk\nHK33HKD1\nHK33HKD2\nHK33HKDSpread\nHK33HKD3\nHK33HKD4\nNAS100USD\nNAS100USDBid\nNAS100USDAsk\nNAS100USD1\nNAS100USD2\nNAS100USDSpread\nNAS100USD3\nNAS100USD4\nXCUUSD\nXCUUSDBid\nXCUUSDAsk\nXCUUSD1\nXCUUSD2\nXCUUSDSpread\nXCUUSD3\nXCUUSD4\nGBPJPY\nGBPJPYBid\nGBPJPYAsk\nGBPJPY1\nGBPJPY2\nGBPJPYSpread\nGBPJPY3\nGBPJPY4\nAUDJPY\nAUDJPYBid\nAUDJPYAsk\nAUDJPY1\nAUDJPY2\nAUDJPYSpread\nAUDJPY3\nAUDJPY4\nAUDCAD\nAUDCADBid\nAUDCADAsk\nAUDCAD1\nAUDCAD2\nAUDCADSpread\nAUDCAD3\nAUDCAD4\nUSB05YUSD\nUSB05YUSDBid\nUSB05YUSDAsk\nUSB05YUSD1\nUSB05YUSD2\nUSB05YUSDSpread\nUSB05YUSD3\nUSB05YUSD4\nUSB10YUSD\nUSB10YUSDBid\nUSB10YUSDAsk\nUSB10YUSD1\nUSB10YUSD2\nUSB10YUSDSpread\nUSB10YUSD3\nUSB10YUSD4\nEU50EUR\nEU50EURBid\nEU50EURAsk\nEU50EUR1\nEU50EUR2\nEU50EURSpread\nEU50EUR3\nEU50EUR4\nDE10YBEUR\nDE10YBEURBid\nDE10YBEURAsk\nDE10YBEUR1\nDE10YBEUR2\nDE10YBEURSpread\nDE10YBEUR3\nDE10YBEUR4\nUK10YBGBP\nUK10YBGBPBid\nUK10YBGBPAsk\nUK10YBGBP1\nUK10YBGBP2\nUK10YBGBPSpread\nUK10YBGBP3\nUK10YBGBP4\nNATGASUSD\nNATGASUSDBid\nNATGASUSDAsk\nNATGASUSD1\nNATGASUSD2\nNATGASUSDSpread\nNATGASUSD3\nNATGASUSD4\nWHEATUSD\nWHEATUSDBid\nWHEATUSDAsk\nWHEATUSD1\nWHEATUSD2\nWHEATUSDSpread\nWHEATUSD3\nWHEATUSD4\nSUGARUSD\nSUGARUSDBid\nSUGARUSDAsk\nSUGARUSD1\nSUGARUSD2\nSUGARUSDSpread\nSUGARUSD3\nSUGARUSD4\nXPTUSD\nXPTUSDBid\nXPTUSDAsk\nXPTUSD1\nXPTUSD2\nXPTUSDSpread\nXPTUSD3\nXPTUSD4\nXPDUSD\nXPDUSDBid\nXPDUSDAsk\nXPDUSD1\nXPDUSD2\nXPDUSDSpread\nXPDUSD3\nXPDUSD4\nAU200AUD\nAU200AUDBid\nAU200AUDAsk\nAU200AUD1\nAU200AUD2\nAU200AUDSpread\nAU200AUD3\nAU200AUD4\nSG30SGD\nSG30SGDBid\nSG30SGDAsk\nSG30SGD1\nSG30SGD2\nSG30SGDSpread\nSG30SGD3\nSG30SGD4";
			row = row.replaceAll(" / ", "\t");
			row = row.replaceAll("=", "\t");
			
			String[] rows = row.split("\n");
			
			// Add additional timing data
			String header = "Same\tDate\tTime\tReqstd\tDurtn\t";
	
			for(int i=0; i<rows.length; i++){
				String[] cols = rows[i].split("\t");
				
				String subj = cols[0].replace("/", "");
				
				if( i == 0 ) 
					header += subj;
				else
					header += "\t"+subj;
				
				for(int j=1; j<cols.length; j++){
					header += "\t" + subj + HEADER[j];
				}
			}
			
			return header.split("\t");
		}
	
		String row, origRow, prevRow = "abrakadabra";
		
		public final GenericRecord getRecord() {
			row = "";
			if( IS_SIMULATION ){
				origRow = row = "EUR/USD\t1.38511\t1.38520\t1.38511\t20\t0.9\t0\t-0.900000\tGBP/USD\t1.68088\t1.68108\t1.68088\t108\t2.0\t0\t-0.800000\tUSD/CHF\t0.88017\t0.88038\t0.88017\t38\t2.1\t0\t-0.300000\tUSD/JPY\t102.477\t102.494\t102.477\t94\t1.7\t1\t0.400000\tAUD/USD\t0.92573\t0.92595\t0.92573\t95\t2.2\t0\t-0.500000\tUSD/CAD\t1.10219\t1.10238\t1.10219\t38\t1.9\t0\t-1.700000\tXAU/USD\t1296.271\t1296.532\t1296.271\t532\t26.1\t0\t-18.900000\tEUR/JPY\t141.941\t141.968\t141.941\t68\t2.7\t0\t-0.100000\tEUR/GBP\t0.82395\t0.82410\t0.82395\t410\t1.5\t0\t-0.300000\tEUR/CHF\t1.21910\t1.21938\t1.21910\t38\t2.8\t0\t-0.200000\tUSD/CNY\t6.16119\t6.16519\t6.16119\t519\t40.0\t1\t2.500000\tEUR/SEK\t9.06657\t9.07138\t9.06657\t7138\t48.1\t0\t-41.900000\tXAG/USD\t19.57552\t19.60052\t19.57552\t60052\t250.0\t0\t-50.000000\tUSD/DKK\t5.38902\t5.38943\t5.38902\t43\t4.1\t0\t-3.400000\tNZD/USD\t0.85367\t0.85403\t0.85367\t403\t3.6\t1\t0.500000\tCHF/JPY\t116.411\t116.445\t116.411\t45\t3.4\t1\t0.300000\tGBP/CHF\t1.47952\t1.47986\t1.47952\t86\t3.4\t0\t-3.200000\tNZD/JPY\t87.488\t87.527\t87.488\t527\t3.9\t0\t-0.500000\tEUR/NZD\t1.62191\t1.62265\t1.62191\t265\t7.4\t0\t-0.100000\tCAD/JPY\t92.958\t92.985\t92.958\t85\t2.7\t0\t-0.400000\tEUR/CAD\t1.52671\t1.52705\t1.52671\t705\t3.4\t0\t-3.500000\tUS30/USD\t16440.2\t16442.3\t16440.2\t2.3\t2.1\t0\t-0.900000\tSPX500/USD\t1870.1\t1870.5\t1870.1\t5\t0.4\t0\t-0.400000\tBCO/USD\t108.229\t108.269\t108.229\t69\t4.0\t0\t-3.500000\tWTICO/USD\t101.358\t101.370\t101.358\t70\t1.2\t0\t-6.300000\tDE30/EUR\t9465.1\t9465.7\t9465.1\t7\t0.6\t0\t-1.600000\tUK100/GBP\t6691.0\t6692.1\t6691.0\t2.1\t1.1\t0\t-0.400000\tJP225/USD\t14407.2\t14422.2\t14407.2\t22.2\t15.0\t0\t-3.000000\tHK33/HKD\t22122.4\t22126.4\t22122.4\t6.4\t4.0\t0\t-12.500000\tNAS100/USD\t3544.9\t3545.3\t3544.9\t5.3\t0.4\t0\t-1.100000\tXCU/USD\t3.13602\t3.13957\t3.13602\t957\t35.5\t0\t-9.500000\tGBP/JPY\t172.263\t172.293\t172.263\t93\t3.0\t0\t-1.100000\tAUD/JPY\t94.869\t94.895\t94.869\t95\t2.6\t0\t-0.500000\tAUD/CAD\t1.02042\t1.02076\t1.02042\t76\t3.4\t0\t-1.400000\tUSB05Y/USD\t119.855\t119.863\t119.855\t63\t0.8\t0\t-2.200000\tUSB10Y/USD\t124.738\t124.754\t124.738\t54\t1.6\t0\t-1.400000\tEU50/EUR\t3144.7\t3145.8\t3144.7\t5.8\t1.1\t0\t-1.900000\tDE10YB/EUR\t144.951\t144.962\t144.951\t62\t1.1\t0\t-1.900000\tUK10YB/GBP\t111.035\t111.046\t111.035\t46\t1.1\t0\t-1.900000\tNATGAS/USD\t4.799\t4.802\t4.799\t802\t0.3\t0\t-0.200000\tWHEAT/USD\t7.006\t7.009\t7.006\t9\t0.3\t0\t-2.700000\tSUGAR/USD\t0.17056\t0.17066\t0.17056\t66\t1.0\t0\t-6.500000\tXPT/USD\t1417.149\t1420.149\t1417.149\t20.149\t300.0\t0\t-1200.000000\tXPD/USD\t800.033\t803.633\t800.033\t3.633\t360.0\t0\t-240.000000\tAU200/AUD\t5559.9\t5561.9\t5559.9\t61.9\t2.0\t0\t-4.000000\tSG30/SGD\t365.36\t365.57\t365.36\t57\t2.1\t0\t-1.900000";
			} else {
				origRow = getFileContent();
				row = origRow.replaceAll(" / ", "\t");
				row = row.replaceAll("=", "\t");
				row = row.replaceAll("\n", "\t");
			}
			// Add additional timing data
			row = getWorkDuration()+"\t"+row; 
			row = UtilDateTime.format(getWorkStart(), ROW_TIME_REQ_FORMATTER)+"\t"+row;
			row = UtilDateTime.format(getTimeon(), ROW_TIME_FORMATTER)+"\t"+row;
			row = UtilDateTime.format(getTimeon(), ROW_DATE_FORMATTER)+"\t"+row;
			row = (prevRow.equals(origRow)?"Y":"N")+"\t"+row;
			prevRow = origRow;
			orow.populate(row.split(OandaParser.ROW_DELIMITER), row);
			return orow.record; 
		}
	}
	
	@Override
	public final void startThreadHandler() throws InterruptedException {
		super.startThreadHandler();
		isHeader = true;
		quoteCounter = 0; // TODO: must be initiated at the beginning of the day rather after start downloading
    	logger.debug(String.format("%s is started", module));
	}

	@Override
    public final void interruptionHandler(Throwable t){
    	logger.debug(String.format("%s %s", module, t.getMessage()), t);
		new RdRunnable("RestartOnline") {
			@Override
		    public void runThreadHandler() throws InterruptedException{
				sleep(WAIT_AFTER_INTERRUPTION_SEC*1000L);
				FxOandaOnline.this.startThread();
			}
		}.startThread();
    }

	@Override
	public boolean isFirst() {
		return true;
	}


	@Override
    public final void endThreadHandler(){
    	logger.debug(String.format("%s is finished", module));
		pcs.firePropertyChange(QuoteEvent.EVENT_FINISH, false, true);
    }

	@Override
	public final void postSecundomer(){
		quoteCounter++;
		if( isHeader ){
			isHeader = false;
			pcs.firePropertyChange(QuoteEvent.EVENT_HEADER, null, row.getHeader());
		}
		//logger.debug(row.getRow());
		pcs.firePropertyChange(QuoteEvent.EVENT_RECORD, null, row.getRecord());

		if( !UtilDateTime.isSameMinute(getTimeon(), getNextTimeon()) )
			pcs.firePropertyChange(QuoteEvent.EVENT_NEXT_MINUTE, false, true);
				
		if( !UtilDateTime.isSameHour(getTimeon(), getNextTimeon()) )
			pcs.firePropertyChange(QuoteEvent.EVENT_NEXT_HOUR, false, true);
		
		if( !UtilDateTime.isSameDay(getTimeon(), getNextTimeon()) )
			pcs.firePropertyChange(QuoteEvent.EVENT_NEXT_DAY, false, true);
	}

	@Override
	public final Set<String> getQuotes(String[] header){
		//final String[] arow = headerRow.split("\t");
		final Set<String> quotesLabels = new HashSet<String>();
		
		for(int i=5; i<header.length; i+=8){
			quotesLabels.add(header[i].trim()); 
		}
		return quotesLabels;
	}
	
	@Override
	public void restart() {
		throw new RuntimeException("Not impemented [restart]");
	}

}
