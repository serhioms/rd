package ca.mss.rd.trade.parser.spreadrating;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import ca.mss.rd.parser.impl.AbstractParser;
import ca.mss.rd.parser.impl.ParserRow;


/**
 * @author moskovsk
 * 
 */
public class SpreadRatingParser<Row extends ParserRow> extends AbstractParser<Row> {

	final static public String module = SpreadRatingParser.class.getName();
	final static public long serialVersionUID = module.hashCode();
	final private static org.apache.log4j.Logger logger = ca.mss.rd.util.Debug.getLogger(module);


	// Header
	static private String[] SP_HEADER_RECORD = new String[]{"ActionIndicator","EntityId","EntityPublishedName","EntitySector",
		"EntitySubSector","CountryCode","StateCode","RegionCode","RatingGroupCode","TypeOfRating","CreditRating",
		"RatingDate","LongTermCreditWatch","LongTermCreditWatchDate","ShortTermCreditWatch","ShortTermCreditWatchDate",
		"LongTermOutlook","LongTermOutlookDate","ShortTermOutlook","ShortTermOutlookDate","LongTermRating","LongTermRatingDate",
		"ShortTermRating","ShortTermRatingDate",
		"PriorCreditRating","PriorLongTermCreditWatch","PriorLongTermOutlook","PriorShortTermCreditWatch","PriorShortTermOutlook",
		"PriorLongTermRatingh","PriorShortTermRatingh","UnsolicitedRatingFlag","Reserved"};
	static private Map<String, String[]> header = new HashMap<String, String[]>(1);
	static {
		header.put(SpreadRatingParserRow.DEFAULT_HEADER_RECORD, SP_HEADER_RECORD);
	}

	// Data row column's offsets
	final static private int POS_ACTION_INDICATOR = 1-1;
	final static private int POS_ENTITY_ID = 2-1;
	final static private int POS_ENTITY_PUBLISHED_NAME = 20-1;
	final static private int POS_ENTITY_SECTOR = 110-1;
	final static private int POS_ENTITY_SUB_SECTOR = 120-1;
	final static private int POS_COUNTRY_CODE = 130-1;
	final static private int POS_STATE_CODE = 133-1;
	final static private int POS_REGION_CODE = 136-1;
	final static private int POS_RATING_GROUP_CODE = 156-1;
	final static private int POS_TYPE_OF_RATING = 166-1;
	final static private int POS_CREDIT_RATING = 186-1;
	final static private int POS_RATING_DATE = 216-1;
	final static private int POS_LONG_TERM_CREDITWATCH = 234-1;
	final static private int POS_LONG_TERM_CREDITWATCH_DATE = 249-1;
	final static private int POS_SHORT_TERM_CREDITWATCH = 267-1;
	final static private int POS_SHORT_TERM_CREDITWATCH_DATE = 282-1;
	final static private int POS_LONG_TERM_OUTLOOK = 300-1;
	final static private int POS_LONG_TERM_OUTLOOK_DATE = 315-1;
	final static private int POS_SHORT_TERM_OUTLOOK = 333-1;
	final static private int POS_SHORT_TERM_OUTLOOK_DATE = 348-1;
	final static private int POS_LONG_TERM_RATING = 366-1;
	final static private int POS_LONG_TERM_RATING_DATE = 376-1;
	final static private int POS_SHORT_TERM_RATING = 394-1;
	final static private int POS_SHORT_TERM_RATING_DATE = 404-1;
	final static private int POS_PRIOR_CREDIT_RATING = 422-1;
	final static private int POS_PRIOR_LONG_TERM_CREDITWATCH = 452-1;
	final static private int POS_PRIOR_LONG_TERM_OUTLOOK = 467-1;
	final static private int POS_PRIOR_SHORT_TERM_CREDITWATCH = 482-1;
	final static private int POS_PRIOR_SHORT_TERM_OUTLOOK = 497-1;
	final static private int POS_PRIOR_LONG_TERM_RATING = 512-1;
	final static private int POS_PRIOR_SHORT_TERM_RATING = 522-1;
	final static private int POS_UNSOLICITED_RATING_FLAG = 532-1;
	final static private int POS_RESERVED = 533-1;	

	final static private int[] dataRowPos = new int[]{POS_ACTION_INDICATOR,POS_ENTITY_ID,POS_ENTITY_PUBLISHED_NAME,
		POS_ENTITY_SECTOR,POS_ENTITY_SUB_SECTOR,POS_COUNTRY_CODE,POS_STATE_CODE,POS_REGION_CODE,POS_RATING_GROUP_CODE,
		POS_TYPE_OF_RATING,POS_CREDIT_RATING,POS_RATING_DATE,POS_LONG_TERM_CREDITWATCH,POS_LONG_TERM_CREDITWATCH_DATE,
		POS_SHORT_TERM_CREDITWATCH,POS_SHORT_TERM_CREDITWATCH_DATE,POS_LONG_TERM_OUTLOOK,POS_LONG_TERM_OUTLOOK_DATE,
		POS_SHORT_TERM_OUTLOOK,POS_SHORT_TERM_OUTLOOK_DATE,POS_LONG_TERM_RATING,POS_LONG_TERM_RATING_DATE,
		POS_SHORT_TERM_RATING,POS_SHORT_TERM_RATING_DATE,POS_PRIOR_CREDIT_RATING,POS_PRIOR_LONG_TERM_CREDITWATCH,
		POS_PRIOR_LONG_TERM_OUTLOOK,POS_PRIOR_SHORT_TERM_CREDITWATCH,POS_PRIOR_SHORT_TERM_OUTLOOK,
		POS_PRIOR_LONG_TERM_RATING,POS_PRIOR_SHORT_TERM_RATING,POS_UNSOLICITED_RATING_FLAG,POS_RESERVED};	

	// Trailer
	private String[][][] SP_TRAILER_RECORD = new String[][][]{{null, {"filename"}}};

	/*
	 * File parsing
	 * 
	 */
	private String[] parseData(String row) {
		String[] field = new String[dataRowPos.length];
		
		for(int i=0, last=dataRowPos.length-1; i<=last; i++){
			if( i == last )
				field[i] = row.substring(dataRowPos[i]).trim();
			else
				field[i] = row.substring(dataRowPos[i], dataRowPos[i+1]).trim();
		}
		
		return field;
	}

	private void parseTrailer(String row) {
		SP_TRAILER_RECORD[0][0] = new String[]{row.trim()};
	}
	
	
	@SuppressWarnings("unused")
	@Override
	public Map<String, String[]> readHeader() {
		for (String row = getFirstRow(); row != null; row = getNextRow()) {
			parseTrailer(row);
			break;
		}
		return header;
	}
	
	@Override
	public String[][][] readTrailer() {
		return SP_TRAILER_RECORD;
	}

	@SuppressWarnings("unused")
	@Override
	public boolean hasNext() {
		for (String row = getNextRow(); row != null; row = getNextRow()) {
			return populate(parseData(row));
		}
		return false;
	}
	
	/**
	 * @param path
	 */
	public SpreadRatingParser(String path) {
		super(path);
	}

	/*
	 * Generate and test 
	 */
	public static void main(String[] args) {
		new SpreadRatingParser<SpreadRatingParserRow>("C:\\mss\\cibc\\workspace\\RdCalyspo\\data\\optex\\spreadrating\\GISF_ERD_2012_05_01_15_00_00_TO_2012_05_02_15_00_00.txt")
			//.generateCode();
			.test();
	}

}