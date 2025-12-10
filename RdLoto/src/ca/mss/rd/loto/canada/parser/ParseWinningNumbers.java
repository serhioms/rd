package ca.mss.rd.loto.canada.parser;

import ca.mss.rd.loto.LotoConfig;
import ca.mss.rd.loto.canada.model.WinningNumbersRow;
import ca.mss.rd.loto.canada.request.DownloadWinningNumbers;
import ca.mss.rd.parser.text.TextParserUniversal;

import ca.mss.rd.util.Timing;
import ca.mss.rd.util.io.InputReader;
import ca.mss.rd.util.io.StringReader;
import ca.mss.rd.util.io.TextFileReader;
import ca.mss.rd.util.io.TextFileWriter;
import ca.mss.rd.util.io.UtilFile;

public class ParseWinningNumbers <Row extends WinningNumbersRow> extends TextParserUniversal<Row> {

	final static public String module = ParseWinningNumbers.class.getSimpleName();
	final static public long serialVersionUID = module.hashCode();
	final static public org.apache.log4j.Logger logger = ca.mss.rd.util.Debug.getLogger(module);

	private DownloadWinningNumbers dwn;
	
	public ParseWinningNumbers(DownloadWinningNumbers dwn) {
		this.dwn = dwn;
	}

	public ParseWinningNumbers() {
		this(null);
	}

	@Override
	public InputReader instantiateInputReader() {
		if( dwn == null )
			return new TextFileReader(LotoConfig.LOTO_CANADA_WINNING_NUMBERS_DOWNLOAD_FILE);
		else if( dwn.isDownloadToRAM() )
			return new StringReader(dwn.getFileContent());
		else if( dwn.isDownloadToFile() )
			return new TextFileReader(dwn.filePath);
		else
			throw new RuntimeException("There is no source for parsing.");
	}
	
	final public boolean isExists(){ 
		return UtilFile.isFileExist(LotoConfig.LOTO_CANADA_WINNING_NUMBERS_FILE);
	}

	@Override
	public String getHeaderKey() {
		return "Current Winning Numbers";
	}

	@Override
	public String[][] getLex() {
		return new String[][]{
				// Line Filter													Start								Mid				End		Splitter	Next Line	Table	SpecialCase
				{  "<a href=\"/lotteries/games/howtoplay.do?", 					"alt=\"", 							null, 			"\"",	"",  "",	null,		null,	null}, 
				{  "<td id=\"lottery_border_top\">", 								null, 							null, 			"\n",	"",  "",	"2",		null,	"Draw in Progress"},
				{  "src=\"/assets/img/num_fff/", 								"alt=\"", 							null, 			"\"",	"",  "-",	null,		"y",	null},
				{  "winningNumbersForm", 										"<input type=\"hidden\" name=\"", 	"\" value=\"", 	"\">",	"=", "&",	null,		"y",	null},
				{  "</tr>"}
			};
	}

	@Override
	public String[][] getSkip() {
		return new String[][]{
				// SKIP START		END	
				{  "ENCORE",		null}, 
				{  "EARLY BIRD",	"</table>"}, 
			};
	}

	@Override
	public String[] getHeader() {
		return WinningNumbersRow.HEADER_RECORD;
	}
	
	@Override
	public Object instantiateRow(){
		return new WinningNumbersRow();
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
				WinningNumbersRow row = next();
				
				// No content - no file
				if( file == null ){
					file = new TextFileWriter(LotoConfig.LOTO_CANADA_WINNING_NUMBERS_FILE).open();
					
					// Header
					for(int i=0; i<HEADER.length; i++){
						if( i > 0 )
							file.write(LotoConfig.ROW_DELIMITER);
			
						file.write(HEADER[i]);
					}
					file.writeln();
				}
				
				if( row.record.getLotoname() != null )
					file.write(row.record.getLotoname().replaceAll("/", "").replaceAll(" ", "").replaceAll("\u00B7","-"));
				file.write(LotoConfig.ROW_DELIMITER);
				if( row.record.getDate() != null )
					file.write(row.record.getDate());
				file.write(LotoConfig.ROW_DELIMITER);
				if( row.record.getWinticket() != null )
					file.write(row.record.getWinticket());
				file.write(LotoConfig.ROW_DELIMITER);
				if( row.record.getPrizerequest() != null )
					file.write(row.record.getPrizerequest());
				file.writeln();
			}
			
			if( logger.isDebugEnabled() )
				logger.debug("Parsed ["+counter+"] rows to ["+LotoConfig.LOTO_CANADA_WINNING_NUMBERS_FILE+"]");
			
		}catch(Throwable t){
			logger.error("Can not parse downloaded winning numbers ["+LotoConfig.LOTO_CANADA_WINNING_NUMBERS_DOWNLOAD_FILE+"] due to ["+t.getMessage()+"]", t);
		} finally {
			if( file != null ) file.close();
		}
	}	
	
	
	
	public static void main(String[] args) {
		
		logger.debug(module+" start...");

		Timing time = new Timing();

		new ParseWinningNumbers<WinningNumbersRow>().parse();

		
		logger.debug(module+" done [time="+time.total()+"]");
		
	}
	
}
