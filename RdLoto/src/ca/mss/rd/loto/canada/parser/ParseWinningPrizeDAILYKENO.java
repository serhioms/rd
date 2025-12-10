package ca.mss.rd.loto.canada.parser;

import ca.mss.rd.loto.LotoConfig;
import ca.mss.rd.loto.canada.model.WinningPrizeKenoRow;
import ca.mss.rd.parser.text.TextParserUniversal;

import ca.mss.rd.util.Timing;
import ca.mss.rd.util.io.TextFileWriter;

public class ParseWinningPrizeDAILYKENO <Row extends WinningPrizeKenoRow> extends TextParserUniversal<Row> {


	final static public String module = ParseWinningPrizeDAILYKENO.class.getSimpleName();
	final static public long serialVersionUID = module.hashCode();
	final static public org.apache.log4j.Logger logger = ca.mss.rd.util.Debug.getLogger(module);

	final static public String lotoName = "DAILYKENO";
	
	public ParseWinningPrizeDAILYKENO() {
		super(LotoConfig.getCanadaPrizeDownloadFile(lotoName));
	}

	@Override
	public String getHeaderKey() {
		return "WINNINGS FOR";
	}

	@Override
	public String[][] getLex() {
		return new String[][]{
				// Line Filter																Start		Mid				End		Splitter	Next Line	Table	SpecialCase	
				{  "<td id=\"lottery_border\" align=\"center\"><p class=\"blue\"><strong>", "<strong>",	null, 			"<",	"",  "",	null,		null,	null},
				{  "<td id=\"lottery_border\" align=\"center\"><p class=\"blue\"><strong>", "<strong>",	null, 			"<",	"",  "",	null,		null,	null},
				{  "<td id=\"lottery_border\" align=\"center\"><p class=\"blue\"><strong>", "<strong>",	null, 			"<",	"",  "",	null,		null,	null},
				{  "<td id=\"lottery_border\" align=\"center\"><p class=\"blue\"><strong>", "<strong>",	null, 			"<",	"",  "",	null,		null,	null},
				{  "<td id=\"lottery_border\" align=\"center\"><p class=\"blue\"><strong>", "<strong>",	null, 			"<",	"",  "",	null,		null,	null},
				{  "<td id=\"lottery_border\" align=\"right\"><p class=\"blue\"><strong>",  "<strong>",	null, 			"<",	"",  "",	null,		null,	null},
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
		return WinningPrizeKenoRow.HEADER_RECORD;
	}
	
	@Override
	public Object instantiateRow(){
		return new WinningPrizeKenoRow();
	}
	
	@Override
	public void runThreadHandler() {
		
		TextFileWriter file = null;
		int counter = 0;

		try {
			file = new TextFileWriter(LotoConfig.getCanadaPrizeFile(lotoName));
			
			file.open();
			
			// Header
			for(int i=0; i<HEADER.length; i++){
				if( i > 0 )
					file.write(LotoConfig.ROW_DELIMITER);
	
				file.write(HEADER[i]);
			}
			file.writeln();
			
			// Body
			for(readHeader(); hasNext(); counter++){
				WinningPrizeKenoRow row = next();
				if( row.record.getCategory() != null )
					file.write(row.record.getCategory());
				file.write(LotoConfig.ROW_DELIMITER);
				if( row.record.getBet1D() != null )
					file.write(row.record.getBet1D());
				file.write(LotoConfig.ROW_DELIMITER);
				if( row.record.getBet2D() != null )
					file.write(row.record.getBet2D());
				file.write(LotoConfig.ROW_DELIMITER);
				if( row.record.getBet5D() != null )
					file.write(row.record.getBet5D());
				file.write(LotoConfig.ROW_DELIMITER);
				if( row.record.getBet10D() != null )
					file.write(row.record.getBet10D());
				file.write(LotoConfig.ROW_DELIMITER);
				if( row.record.getPrizePer1DWagered() != null )
					file.write(row.record.getPrizePer1DWagered());
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
	public static void main(String[] args) {
		logger.debug(module+" start...");

		Timing time = new Timing();

		new ParseWinningPrizeDAILYKENO<WinningPrizeKenoRow>().run();

		
		logger.debug(module+" done [time="+time.total()+"]");

	}
	
}
