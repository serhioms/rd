package ca.mss.rd.trade.parser.apollo;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import ca.mss.rd.parser.impl.AbstractParser;
import ca.mss.rd.parser.impl.ParserRow;
import ca.mss.rd.trade.parser.spreadrating.SpreadRatingParserRow;


/**
 * @author moskovsk
 * 
 */
public class ApolloParser <Row extends ParserRow> extends AbstractParser<Row> {

	final static public String module = ApolloParser.class.getName();
	final static public long serialVersionUID = module.hashCode();
	final private static org.apache.log4j.Logger logger = ca.mss.rd.util.Debug.getLogger(module);


	final public static String   APOLLO_HEADER = "HEADER";
	final public static String   APOLLO_TRAILER = "TRAILER";
	final public static String   APOLLO_DELIMITER = "\\|";
	
	private String[][][] APOLLO_TRAILER_RECORD = new String[][][]{
			{null, {"ident","hash","ident","fileName","date","source","flag","confidential"}},
			{null, {"ident","hash","ident","fileName","size","hash2","flag","confidential"}}
			};

	
	/*
	 * Here is actual parsing
	 * 
	 */
	private String[] parseRow(String row) {
		return row.split(APOLLO_DELIMITER);
	}

	@Override
	public String[][][] readTrailer() {
		return APOLLO_TRAILER_RECORD;
	}

	@Override
	public Map<String, String[]> readHeader() {
		for (String row = getFirstRow(); row != null; row = getNextRow()) {
			if( row.contains(APOLLO_HEADER) ){
				APOLLO_TRAILER_RECORD[0][0] = parseRow(row);
				continue;
			}
			Map<String, String[]> header = new HashMap<String, String[]>(1);
			header.put(SpreadRatingParserRow.DEFAULT_HEADER_RECORD, parseRow(row));
			return header;
		}
		throw new RuntimeException(module+" header is empty");
	}

	@SuppressWarnings("unused")
	@Override
	public boolean hasNext() {
		for (String row = getNextRow(); row != null; row = getNextRow()) {
			if( row.contains(APOLLO_TRAILER) ){
				APOLLO_TRAILER_RECORD[1][0] = parseRow(row);
				break;
			} else
				return populate(parseRow(row));
		}
		return false;
	}

	
	/*
	 * 
	 */
	
	public ApolloParser(String path) {
		super(path);
	}


	/*
	 * Generate and test 
	 */
	public static void main(String[] args) {
		new ApolloParser<ApolloParserRow>("C:\\mss\\cibc\\workspace\\RdCalyspo\\data\\apollo\\CALYPSO_DEALS_APOLLO_CVA_RSIQA.txt")
			//.generateCode();
			.test();
	}

}
