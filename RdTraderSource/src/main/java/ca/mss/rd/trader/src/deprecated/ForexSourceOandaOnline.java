package ca.mss.rd.trader.src.deprecated;

import java.math.BigDecimal;

import ca.mss.rd.trade.forex.FX;
import ca.mss.rd.trade.forex.FXBridge2BD;
import ca.mss.rd.trade.forex.FXIterator;
import ca.mss.rd.trade.forex.FXQuote;

import ca.mss.rd.util.UtilDateTime;
import ca.mss.rd.util.UtilEncryption;
import ca.mss.rd.util.UtilMisc;
import ca.mss.rd.util.encryption.RC4Oanda;
import ca.mss.rd.http.HttpException;

@Deprecated
public class ForexSourceOandaOnline extends FXBridge2BD implements FXIterator {


	final static public String module = ForexSourceOandaOnline.class.getName();
	final static public long serialVersionUID = module.hashCode();
	final private static org.apache.log4j.Logger logger = ca.mss.rd.util.Debug.getLogger(module);

	static public String URL = "http://fxtrade.oanda.com/lfr/rates_fxtrade-common";
	static public String FILE = "/Users/smoskovskiy/workspace/rd/RdTraderConsole/data/forex/quotes.txt";
	static public String DATE_FORMATTER = "yyyMMdd-kkmmss-SSS";

	static public boolean 	DOWNLOAD_TO_RAM = true;
	static public boolean 	DOWNLOAD_TO_FILE = false;
	
	final private RC4Oanda rc4;
	private String tstamp = "";
	private long waitBeforeDownload = MINIMAL_WAIT_BEFORE_DOWNLOAD;
	private FXQuote quote;

	
	public ForexSourceOandaOnline(FXQuote quote) {
		super(URL);
		super.cache = null;
		this.quote = quote;
		rc4 = new RC4Oanda("aaf6cb4f0ced8a211c2728328597268509ade33040233a11af".getBytes());
	}
	
	@Override
	final public boolean isDownloadToFile() {
		return DOWNLOAD_TO_FILE;
	}

	@Override
	final public boolean isDownloadToRAM() {
		return DOWNLOAD_TO_RAM;
	}

	@Override
	final public String getDownloadFile() {
		return FILE+"."+tstamp+"."+waitBeforeDownload+"-"+REDUSE_DOWNLOAD_FREQUENCY_AFTER;
	}
	
	@Override
	protected void setFileContent(String fileContent) {

		if( fileContent.contains("503 Service Temporarily Unavailable") )
			throw new HttpException("Service Unavailable: "+URL);
		
		super.setFileContent(new String(rc4.decrypt(UtilEncryption.hexDecode(fileContent.getBytes()))));
	}

	
	int counter = 0;
	static public long MINIMAL_WAIT_BEFORE_DOWNLOAD = 100L;
	static public int REDUSE_DOWNLOAD_FREQUENCY_AFTER = 2;
	static public long REDUSE_DOWNLOAD_FREQUENCY_AFTER_MLS = 60L*1000L;

	@Override
	public void reinitialize() {
	}
	
	@Override
	final public FXQuote getNextQuotes() throws InterruptedException {
		waitBeforeDownload = 0L;
		download(UtilMisc.toMap("tstamp", UtilDateTime.now().toString()));
		
		if( isNew() ){
			quote.bid = null; 
			quote.ask = null; 
	
			String[] rows = getFileContent().split("\n");
			
			for(int i=0; i<rows.length; i++){
				//logger.debug(rows[i]);
				
				String[] cols = rows[i].split("=");
				
				if( !quote.pair.acronim.equals(cols[0]) )
					continue;
				
				quote.bid = new BigDecimal(cols[1]); 
				quote.ask = new BigDecimal(cols[2]); 
				
				break;
			}
		}
		return quote;
	}

	@Override
	final public boolean hasNextQuotes(){
		return true;
	}

	static private String[] HEADER = new String[]{"Subj", "Bid", "Ask", "1", "2", "Spread", "3", "4"};
	
	@Override
	public String getHeader() {
		String row = getFileContent();
		row = row.replaceAll(" / ", "\t");
		row = row.replaceAll("=", "\t");
		
		String[] rows = row.split("\n");
		
		String header = "Date\t";

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
		
		return header;
	}

	@Override
	public String getRow() {
		String row = getFileContent();
		row = row.replaceAll(" / ", "\t");
		row = row.replaceAll("=", "\t");
		row = row.replaceAll("\n", "\t");
		row = UtilDateTime.formatDate(UtilDateTime.now())+"\t"+row; // Save date/time of data online saving
		return row;
	}

	public static void main(String[] args) {
		
		ForexSourceOandaOnline fo = new ForexSourceOandaOnline(new FXQuote(FX.CurrencyPair.EUR_USD));
		
		for(int i=0; true; i++ ){
			try {
				fo.getNextQuotes();
				if( fo.isNew() ){
					logger.debug(i+" "+fo.waitBeforeDownload+" mls "+REDUSE_DOWNLOAD_FREQUENCY_AFTER);
				}
			}catch(Throwable t){
				logger.error("Skip "+fo.getDownloadFile(), t);
			}
		}

	}


	@Override
	public String toString() {
		return "OandaOnline";
	}

}
