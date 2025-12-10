package ca.mss.rd.trade.parser.markit.liquiditymetrics;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import ca.mss.rd.parser.impl.AbstractParser;
import ca.mss.rd.parser.impl.DefaultRow;
import ca.mss.rd.parser.impl.ParserRow;


/**
 * @author moskovsk
 * 
 */
public class MarkitLiquidityMetrics <Row extends ParserRow> extends AbstractParser<Row> {

	final static public String module = MarkitLiquidityMetrics.class.getName();
	final static public long serialVersionUID = module.hashCode();
	final private static org.apache.log4j.Logger logger = ca.mss.rd.util.Debug.getLogger(module);

	final public static String ROW_DELIMITER = "\",\"";
	
	// Header
	static private Map<String, String[]> header = new HashMap<String, String[]>(1);

	// Trailer
	private String[][][] trailer = new String[][][]{{null, {"Trailer"}}};

	// Row
	private String lastRow;
	
	/*
	 * Here is actual parsing
	 * 
	 */
	private String[] parseRow(String row) {
		return row.substring(1, row.length()-1).split(ROW_DELIMITER);
	}

	@Override
	public String[][][] readTrailer() {
		trailer[0][0] = new String[]{lastRow};
		return trailer;
	}

	@Override
	public Map<String, String[]> readHeader() {
		String row = getFirstRow();
		row = getNextRow();
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

	
	/*
	 * 
	 */
	
	public MarkitLiquidityMetrics(String path) {
		super(path);
	}

	/*
	 * Generate and test 
	 */
	public static void main(String[] args) {
		new MarkitLiquidityMetrics<MarkitLiquidityMetricsRow>("C:/mss/cibc/workspace/RdTrade/data/markit/markit_site=2012-10-25.txt")
			//.generateCode();
			.test();
	}

}
