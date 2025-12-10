package ca.mss.rd.loto.canada.model;

import java.util.HashMap;
import java.util.Map;

import ca.mss.rd.loto.LotoConfig;
import ca.mss.rd.parser.impl.AbstractParser;
import ca.mss.rd.parser.impl.DefaultRow;
import ca.mss.rd.parser.impl.ParserRow;

import ca.mss.rd.util.Timing;

/**
 * @author moskovsk
 * 
 */
public class WinningPrize <Row extends ParserRow> extends AbstractParser<Row> {

	final static public String module = WinningPrize.class.getSimpleName();
	final static public long serialVersionUID = module.hashCode();
	final static public org.apache.log4j.Logger logger = ca.mss.rd.util.Debug.getLogger(module);

	public WinningPrize(String lotoName) {
		super(LotoConfig.getCanadaPrizeFile(lotoName));
	}
	
	
	// Header
	static private Map<String, String[]> header = new HashMap<String, String[]>(1);

	// Trailer
	private String[][][] trailer = new String[][][]{{null, {"Trailer"}}};

	// Last not null row
	private String lastRow;
	
	/*
	 * Here is actual parsing
	 * 
	 */
	private String[] parseRow(String row) {
		return row.split(LotoConfig.ROW_DELIMITER);
	}

	@Override
	public String[][][] readTrailer() {
		trailer[0][0] = new String[]{lastRow};
		return trailer;
	}

	@Override
	public Map<String, String[]> readHeader() {
		String row = getFirstRow();
		header.put(DefaultRow.DEFAULT_HEADER_RECORD, parseRow(row));
		return header;
	}

	@Override
	public boolean hasNext() {
		for (String row=getNextRow(); row != null; ) {
			return populate(parseRow(lastRow = row));
		}
		return false;
	}

	@Override
	public void runThreadHandler() {
	}
	
	
	public static void main(String[] args) {
		logger.debug(module+" start...");

		Timing timing = new Timing();

		new WinningPrize<WinningPrizeRow>("LOTTARIO")
			//.generateCode();
			.test();
		
		logger.debug(module+" done [time="+timing.total()+"]");
	}

}
