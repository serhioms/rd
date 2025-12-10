package ca.mss.rd.loto.canada.parser;

import java.util.concurrent.TimeUnit;

import ca.mss.rd.loto.LotoConfig;
import ca.mss.rd.loto.canada.model.WinningPrizeRow;
import ca.mss.rd.loto.canada.request.DownloadWinningPrize;
import ca.mss.rd.parser.text.TextParserUniversal;

import ca.mss.rd.util.Timing;
import ca.mss.rd.util.io.InputReader;
import ca.mss.rd.util.io.StringReader;
import ca.mss.rd.util.io.TextFileReader;
import ca.mss.rd.util.io.TextFileWriter;
import ca.mss.rd.util.io.UtilFile;

public class ParseWinningPrize <Row extends WinningPrizeRow> extends TextParserUniversal<Row> {


	final static public String module = ParseWinningPrize.class.getSimpleName();
	final static public long serialVersionUID = module.hashCode();
	final static public org.apache.log4j.Logger logger = ca.mss.rd.util.Debug.getLogger(module);

	final public String lotoName;
	private DownloadWinningPrize dwp;
	
	public ParseWinningPrize(String lotoName) {
		this.lotoName= lotoName; 
	}

	public ParseWinningPrize(DownloadWinningPrize dwp) {
		this.dwp= dwp; 
		this.lotoName= dwp.lotoName; 
	}


	@Override
	public InputReader instantiateInputReader() {
		if( dwp == null )
			return new TextFileReader(LotoConfig.getCanadaPrizeDownloadFile(lotoName));
		else if( dwp.isDownloadToRAM() )
			return new StringReader(dwp.getFileContent());
		else if( dwp.isDownloadToFile() )
			return new TextFileReader(LotoConfig.getCanadaPrizeDownloadFile(lotoName));
		else
			throw new RuntimeException("There is no source for parsing.");
	}

	final public boolean isPrizeExists(){ 
		return UtilFile.isFileExist(LotoConfig.getCanadaPrizeFile(lotoName));
	}

	@Override
	public String getHeaderKey() {
		return "WINNINGS FOR";
	}

	@Override
	public String[][] getLex() {
		return new String[][]{
				// Line Filter																Start		Mid				End		Splitter	Next Line	Table	SpecialCase
				{  "<p class=\"blue\"><strong>", 											null, 		null, 			"\n",	"",  "",	"1", 		null,	null},
				{  "<p class=\"blue\"><strong>", 											"<strong>",	null, 			"<",	"",  "",	null, 		null,	null},
				{  "<td id=\"lottery_border\" align=\"right\"><p class=\"blue\"><strong>",	null, 		null, 			"\n",	"",  "",	"1", 		null,	null}, 
				{  "</table>"}
			};
	}

	@Override
	public String[][] getSkip() {
		return new String[][]{
				// SKIP START		END	
			};
	}

	@Override
	public String[] getHeader() {
		return WinningPrizeRow.HEADER_RECORD;
	}
	
	@Override
	public Object instantiateRow(){
		return new WinningPrizeRow();
	}
	
	
	@Override
	public void runThreadHandler() {
		parse();
	}
	
	public void parse() {

		TextFileWriter file = null;
		int counter = 0;

		try {
			
			// Body
			for(readHeader(); hasNext(); counter++){
				WinningPrizeRow row = next();
				
				// No content - no file
				if( file == null ){
					file = new TextFileWriter(LotoConfig.getCanadaPrizeFile(lotoName)).open();
					// Header
					for(int i=0; i<HEADER.length; i++){
						if( i > 0 )
							file.write(LotoConfig.ROW_DELIMITER);
			
						file.write(HEADER[i]);
					}
					file.writeln();
				}
					
				if( row.record.getMatch() != null )
					file.write(row.record.getMatch());
				file.write(LotoConfig.ROW_DELIMITER);
				if( row.record.getWintickets() != null )
					file.write(row.record.getWintickets());
				file.write(LotoConfig.ROW_DELIMITER);
				if( row.record.getPrize() != null )
					file.write(row.record.getPrize());
				file.writeln();
			}
			
			if( logger.isDebugEnabled())
				logger.debug("Parsed ["+counter+"] rows for ["+lotoName+"]");
			
		}catch(Throwable t){
			logger.error("Can not parse downloaded winning numbers for ["+lotoName+"]", t);
		} finally {
			if( file != null ) file.close();
		}
	}	
	
	
	/*
	 * Test 
	 */
	public static void main(String[] args) throws InterruptedException {
		logger.debug(module+" start...");

		Timing time = new Timing();

		new ParseWinningPrize<WinningPrizeRow>("LOTTO649").run();

		
		logger.debug(module+" done [time="+time.total()+"]");

	}
	
}
