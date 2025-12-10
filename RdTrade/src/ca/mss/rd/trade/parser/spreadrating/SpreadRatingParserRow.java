package ca.mss.rd.trade.parser.spreadrating;

import java.util.HashMap;
import java.util.Map;

import ca.mss.rd.parser.impl.DefaultRow;
import ca.mss.rd.parser.impl.Getter;
import ca.mss.rd.parser.impl.ParserRow;

public class SpreadRatingParserRow extends DefaultRow implements ParserRow {

	final static public String module = SpreadRatingParserRow.class.getName();
	final static public long serialVersionUID = module.hashCode();

	static private Map<String, Integer> DATA_RECORD_MAPPER = new HashMap<String, Integer>();

	private String[][] SPREADRATINGPARSERROW_DATA_RECORD = new String[][] {
			null,
			{ "ActionIndicator", "EntityId", "EntityPublishedName", "EntitySector", "EntitySubSector", "CountryCode",
					"StateCode", "RegionCode", "RatingGroupCode", "TypeOfRating", "CreditRating", "RatingDate",
					"LongTermCreditWatch", "LongTermCreditWatchDate", "ShortTermCreditWatch", "ShortTermCreditWatchDate",
					"LongTermOutlook", "LongTermOutlookDate", "ShortTermOutlook", "ShortTermOutlookDate", "LongTermRating",
					"LongTermRatingDate", "ShortTermRating", "ShortTermRatingDate", "PriorCreditRating",
					"PriorLongTermCreditWatch", "PriorLongTermOutlook", "PriorShortTermCreditWatch", "PriorShortTermOutlook",
					"PriorLongTermRatingh", "PriorShortTermRatingh", "UnsolicitedRatingFlag", "Reserved" } };

	final public Record record = new Record();

	public class Record implements Getter {
		public String getActionindicator() {
			return SPREADRATINGPARSERROW_DATA_RECORD[0][0];
		}

		public String getEntityid() {
			return SPREADRATINGPARSERROW_DATA_RECORD[0][1];
		}

		public String getEntitypublishedname() {
			return SPREADRATINGPARSERROW_DATA_RECORD[0][2];
		}

		public String getEntitysector() {
			return SPREADRATINGPARSERROW_DATA_RECORD[0][3];
		}

		public String getEntitysubsector() {
			return SPREADRATINGPARSERROW_DATA_RECORD[0][4];
		}

		public String getCountrycode() {
			return SPREADRATINGPARSERROW_DATA_RECORD[0][5];
		}

		public String getStatecode() {
			return SPREADRATINGPARSERROW_DATA_RECORD[0][6];
		}

		public String getRegioncode() {
			return SPREADRATINGPARSERROW_DATA_RECORD[0][7];
		}

		public String getRatinggroupcode() {
			return SPREADRATINGPARSERROW_DATA_RECORD[0][8];
		}

		public String getTypeofrating() {
			return SPREADRATINGPARSERROW_DATA_RECORD[0][9];
		}

		public String getCreditrating() {
			return SPREADRATINGPARSERROW_DATA_RECORD[0][10];
		}

		public String getRatingdate() {
			return SPREADRATINGPARSERROW_DATA_RECORD[0][11];
		}

		public String getLongtermcreditwatch() {
			return SPREADRATINGPARSERROW_DATA_RECORD[0][12];
		}

		public String getLongtermcreditwatchdate() {
			return SPREADRATINGPARSERROW_DATA_RECORD[0][13];
		}

		public String getShorttermcreditwatch() {
			return SPREADRATINGPARSERROW_DATA_RECORD[0][14];
		}

		public String getShorttermcreditwatchdate() {
			return SPREADRATINGPARSERROW_DATA_RECORD[0][15];
		}

		public String getLongtermoutlook() {
			return SPREADRATINGPARSERROW_DATA_RECORD[0][16];
		}

		public String getLongtermoutlookdate() {
			return SPREADRATINGPARSERROW_DATA_RECORD[0][17];
		}

		public String getShorttermoutlook() {
			return SPREADRATINGPARSERROW_DATA_RECORD[0][18];
		}

		public String getShorttermoutlookdate() {
			return SPREADRATINGPARSERROW_DATA_RECORD[0][19];
		}

		public String getLongtermrating() {
			return SPREADRATINGPARSERROW_DATA_RECORD[0][20];
		}

		public String getLongtermratingdate() {
			return SPREADRATINGPARSERROW_DATA_RECORD[0][21];
		}

		public String getShorttermrating() {
			return SPREADRATINGPARSERROW_DATA_RECORD[0][22];
		}

		public String getShorttermratingdate() {
			return SPREADRATINGPARSERROW_DATA_RECORD[0][23];
		}

		public String getPriorcreditrating() {
			return SPREADRATINGPARSERROW_DATA_RECORD[0][24];
		}

		public String getPriorlongtermcreditwatch() {
			return SPREADRATINGPARSERROW_DATA_RECORD[0][25];
		}

		public String getPriorlongtermoutlook() {
			return SPREADRATINGPARSERROW_DATA_RECORD[0][26];
		}

		public String getPriorshorttermcreditwatch() {
			return SPREADRATINGPARSERROW_DATA_RECORD[0][27];
		}

		public String getPriorshorttermoutlook() {
			return SPREADRATINGPARSERROW_DATA_RECORD[0][28];
		}

		public String getPriorlongtermratingh() {
			return SPREADRATINGPARSERROW_DATA_RECORD[0][29];
		}

		public String getPriorshorttermratingh() {
			return SPREADRATINGPARSERROW_DATA_RECORD[0][30];
		}

		public String getUnsolicitedratingflag() {
			return SPREADRATINGPARSERROW_DATA_RECORD[0][31];
		}

		public String getReserved() {
			return SPREADRATINGPARSERROW_DATA_RECORD[0][32];
		}

		public String get(String name) {
			return SPREADRATINGPARSERROW_DATA_RECORD[0][DATA_RECORD_MAPPER.get(name)];
		}
	}

	public boolean populate(String[] data) {
		this.data[0] = data;
		return true;
	}

	public SpreadRatingParserRow() {
		headerTitles = new HashMap<String, String[][]>();
		headerTitles.put(DefaultRow.DEFAULT_HEADER_RECORD, SPREADRATINGPARSERROW_DATA_RECORD);

		this.data = SPREADRATINGPARSERROW_DATA_RECORD;

		headerClasses.put(DefaultRow.DEFAULT_HEADER_RECORD, Record.class);
	}

}
