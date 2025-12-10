package ca.mss.rd.parser.zample;

import java.util.HashMap;
import java.util.Map;

import ca.mss.rd.parser.impl.AbstractParser;
import ca.mss.rd.parser.impl.DefaultRow;
import ca.mss.rd.parser.impl.ParserRow;

/**
 * @author moskovsk
 * 
 */
public class SimpleParser <Row extends ParserRow> extends AbstractParser<Row> {

	final static public String module = SimpleParser.class.getName();
	final static public long serialVersionUID = module.hashCode();

	final public static String ROW_DELIMITER = ",";
	
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
		return row.split(ROW_DELIMITER);
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

	
	/*
	 * 
	 */
	
	public SimpleParser(String path) {
		super(path);
	}



	/*
	 * Generate and test 
	 */
	public static void main(String[] args) {
		new SimpleParser<DefaultRow>("C:/***/.txt")
			//.generateCode();
			.test();
	}

}
