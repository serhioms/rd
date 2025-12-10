/**
 * 
 */
package ca.mss.rd.trade.parser.optexzerocurve;

import java.util.HashMap;
import java.util.Map;

import ca.mss.rd.parser.impl.AbstractParser;
import ca.mss.rd.parser.impl.DefaultRow;
import ca.mss.rd.parser.impl.ParserRow;


/**
 * @author moskovsk
 * 
 */
public class OptexZeroCurveParser <Row extends ParserRow> extends AbstractParser<Row> {

	final static public String module = OptexZeroCurveParser.class.getName();
	final static public long serialVersionUID = module.hashCode();

	// parser keywords
	final public static String[] OPTEX_HEADER = new String[] { "<HEADER>", "</HEADER>" };
	final public static String[] OPTEX_HEADER_FUTURES = new String[] { "<FUTURES>", "</FUTURES>" };
	final public static String[] OPTEX_HEADER_OTHER = new String[] { "<OTHER>", "</OTHER>" };
	final public static String[] OPTEX_DATA = new String[] { "<DATA>", "</DATA>" };
	final public static String   OPTEX_TRAILER = "TRAILER ";
	final public static String   OPTEX_DELIMITER = "<DEL>";
	final public static String   OPTEX_DELIMITER_TRAILER = " ";
	
	// Actual trailer record
	private String[][][] OPTEX_TRAILER_RECORD = new String[][][]{{null, {"rows", "N/A", "N/A", "N/A", "fileName", "date", "email"}}};


	/*
	 * Read header
	 * @see ca.mss.AbstractParser#readHeader()
	 */
	@Override
	public Map<String, String[]> readHeader() {
		Map<String, String[]> header = null;
		for (String row = getFirstRow(); row != null; row = getNextRow()) {
			if (row.contains(OPTEX_HEADER[0]))
				header = new HashMap<String, String[]>();
			else if (row.contains(OPTEX_HEADER[1]))
				break;
			else if (row.contains(OPTEX_HEADER_FUTURES[0])) {
				header.put(OptexZeroCurveParserRow.OPTEXZEROCURVEPARSERROW_HEADER_FUTURES, parseHeader(row, OPTEX_HEADER_FUTURES));
			} else if (row.contains(OPTEX_HEADER_OTHER[0])) {
				header.put(OptexZeroCurveParserRow.OPTEXZEROCURVEPARSERROW_HEADER_OTHER, parseHeader(row, OPTEX_HEADER_OTHER));
			}
		}

		if (header == null)
			throw new RuntimeException("Can not find "+getSubClassName()+" header [signature="+OPTEX_HEADER[0]+OPTEX_HEADER[1]+"]");
		if (header.size() == 0)
			throw new RuntimeException(getSubClassName()+" header is empty");
		
		return header;
	}

	/* Read data row vie iterator
	 * @see java.util.Iterator#hasNext()
	 */
	@Override
	public boolean hasNext() {
		for (String row = getNextRow(); row != null; row = getNextRow()) {
			if (row.contains(OPTEX_DATA[0]))
				continue;
			else if (row.contains(OPTEX_DATA[1]))
				break;
			else
				return populate(parseRow(row));

		}
		return false;
	}

	/* Read trailer
	 * @see ca.mss.parser.AbstractParser#readTrailer()
	 */
	@SuppressWarnings("unused")
	@Override
	public String[][][] readTrailer() {
		for (String row = getNextRow(); row != null; row = getNextRow()) {
			if (row.contains(OPTEX_TRAILER) )
				return parseTrailer(row);
			else
				throw new RuntimeException(getSubClassName()+" trailer contains unexpected data ["+row+"]");
		}
		return null;
	}

	/*
	 * Here is actual row parsing
	 * 
	 */
	private String[] parseHeader(String row) {
		String[] header = row.split(OPTEX_DELIMITER);
		for (int i=0; i < header.length; i++) {
			header[i] = header[i].trim();
		}
		return header;
	}

	private String[] parseHeader(String row, String[] limit) {
		for (int i=0; i < limit.length; i++) {
			row = row.replace(limit[i], "");
		}
		return parseHeader(row);
	}

	
	private String[] parseRow(String row) {
		return row.split(OPTEX_DELIMITER);
	}

	private String[][][] parseTrailer(String row) {
		OPTEX_TRAILER_RECORD[0][0] = row.replace(OPTEX_TRAILER, "").split(OPTEX_DELIMITER_TRAILER);
		return OPTEX_TRAILER_RECORD;
	}

	/**
	 * @param path
	 */
	public OptexZeroCurveParser(String path) {
		super(path);
	}
	
	public static void main(String[] args) {
		new OptexZeroCurveParser<DefaultRow>("D:\\workspace\\2012\\rd\\RdTrade\\data\\optex\\zero_curves\\FPToolsZeroCurveData_20120518_195500.txt.20120518")
			//.generateCode();
			.test();
	}

}
