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
public class WinningNumbers <Row extends ParserRow> extends AbstractParser<Row> {

	final static public String module = WinningNumbers.class.getSimpleName();
	final static public long serialVersionUID = module.hashCode();
	final static public org.apache.log4j.Logger logger = ca.mss.rd.util.Debug.getLogger(module);

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

	
	public WinningNumbers() {
		super(LotoConfig.LOTO_CANADA_WINNING_NUMBERS_FILE);
	}

	
	
	@Override
	final public void runThreadHandler() {
	}

	/*
	 * Generate and test 
	 */
	public static void main(String[] args) {
		logger.debug("Start...");

		Timing timing = new Timing();

		new WinningNumbers<WinningNumbersRow>()
			//.generateCode();
			.test();
		
		logger.debug("Done [time="+timing.total()+"]");
	}

}
