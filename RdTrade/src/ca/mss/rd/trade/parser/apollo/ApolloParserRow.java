package ca.mss.rd.trade.parser.apollo;

import java.util.HashMap;
import java.util.Map;

import ca.mss.rd.parser.impl.DefaultRow;
import ca.mss.rd.parser.impl.Getter;
import ca.mss.rd.parser.impl.ParserRow;

public class ApolloParserRow extends DefaultRow implements ParserRow {

	final static public String module = ApolloParserRow.class.getName();
	final static public long serialVersionUID = module.hashCode();

	static private Map<String, Integer> DATA_RECORD_MAPPER = new HashMap<String, Integer>();
	
	private String[][] APOLLOPARSERROW_DATA_RECORD = new String[][] {
			null,
			{ "ProductClass", "ProductGroup", "ProductType", "Reference", "ReferenceLong", "Coupon", "StartDate", "EndDate",
					"Type", "Frequency", "StandardTenor", "Basis", "AccrualCalendar", "AccrualBDC", "Trans_id", "Leg_id",
					"BuySell", "Currency", "Notional", "PayInstrument", "RecInstrument", "OrgUnit", "Book", "SourceMTM",
					"SourceMTMInUSD", "SourceMTMCCY", "ValDate", "Trader", "Counterparty", "CounterpartyName",
					"BaseCounterparty", "TradeDate", "MaturityDate", "System", "CDSIndexFlag", "CashFlowFlag",
					"ExecutionTimestamp", "isCleared", "ClearingVenue", "ExecutionVenue", "USINameSpace", "USITransID",
					"isAllocated", "isMultiAsset", "SecondaryAssetClass", "isNovated", "isPartialTermination",
					"isCashFlowChange", "CorporateAction", "Version" } };

	final public Record record = new Record();

	public class Record implements Getter {
		public String getProductclass() {
			return APOLLOPARSERROW_DATA_RECORD[0][0];
		}

		public String getProductgroup() {
			return APOLLOPARSERROW_DATA_RECORD[0][1];
		}

		public String getProducttype() {
			return APOLLOPARSERROW_DATA_RECORD[0][2];
		}

		public String getReference() {
			return APOLLOPARSERROW_DATA_RECORD[0][3];
		}

		public String getReferencelong() {
			return APOLLOPARSERROW_DATA_RECORD[0][4];
		}

		public String getCoupon() {
			return APOLLOPARSERROW_DATA_RECORD[0][5];
		}

		public String getStartdate() {
			return APOLLOPARSERROW_DATA_RECORD[0][6];
		}

		public String getEnddate() {
			return APOLLOPARSERROW_DATA_RECORD[0][7];
		}

		public String getType() {
			return APOLLOPARSERROW_DATA_RECORD[0][8];
		}

		public String getFrequency() {
			return APOLLOPARSERROW_DATA_RECORD[0][9];
		}

		public String getStandardtenor() {
			return APOLLOPARSERROW_DATA_RECORD[0][10];
		}

		public String getBasis() {
			return APOLLOPARSERROW_DATA_RECORD[0][11];
		}

		public String getAccrualcalendar() {
			return APOLLOPARSERROW_DATA_RECORD[0][12];
		}

		public String getAccrualbdc() {
			return APOLLOPARSERROW_DATA_RECORD[0][13];
		}

		public String getTrans_id() {
			return APOLLOPARSERROW_DATA_RECORD[0][14];
		}

		public String getLeg_id() {
			return APOLLOPARSERROW_DATA_RECORD[0][15];
		}

		public String getBuysell() {
			return APOLLOPARSERROW_DATA_RECORD[0][16];
		}

		public String getCurrency() {
			return APOLLOPARSERROW_DATA_RECORD[0][17];
		}

		public String getNotional() {
			return APOLLOPARSERROW_DATA_RECORD[0][18];
		}

		public String getPayinstrument() {
			return APOLLOPARSERROW_DATA_RECORD[0][19];
		}

		public String getRecinstrument() {
			return APOLLOPARSERROW_DATA_RECORD[0][20];
		}

		public String getOrgunit() {
			return APOLLOPARSERROW_DATA_RECORD[0][21];
		}

		public String getBook() {
			return APOLLOPARSERROW_DATA_RECORD[0][22];
		}

		public String getSourcemtm() {
			return APOLLOPARSERROW_DATA_RECORD[0][23];
		}

		public String getSourcemtminusd() {
			return APOLLOPARSERROW_DATA_RECORD[0][24];
		}

		public String getSourcemtmccy() {
			return APOLLOPARSERROW_DATA_RECORD[0][25];
		}

		public String getValdate() {
			return APOLLOPARSERROW_DATA_RECORD[0][26];
		}

		public String getTrader() {
			return APOLLOPARSERROW_DATA_RECORD[0][27];
		}

		public String getCounterparty() {
			return APOLLOPARSERROW_DATA_RECORD[0][28];
		}

		public String getCounterpartyname() {
			return APOLLOPARSERROW_DATA_RECORD[0][29];
		}

		public String getBasecounterparty() {
			return APOLLOPARSERROW_DATA_RECORD[0][30];
		}

		public String getTradedate() {
			return APOLLOPARSERROW_DATA_RECORD[0][31];
		}

		public String getMaturitydate() {
			return APOLLOPARSERROW_DATA_RECORD[0][32];
		}

		public String getSystem() {
			return APOLLOPARSERROW_DATA_RECORD[0][33];
		}

		public String getCdsindexflag() {
			return APOLLOPARSERROW_DATA_RECORD[0][34];
		}

		public String getCashflowflag() {
			return APOLLOPARSERROW_DATA_RECORD[0][35];
		}

		public String getExecutiontimestamp() {
			return APOLLOPARSERROW_DATA_RECORD[0][36];
		}

		public String getIscleared() {
			return APOLLOPARSERROW_DATA_RECORD[0][37];
		}

		public String getClearingvenue() {
			return APOLLOPARSERROW_DATA_RECORD[0][38];
		}

		public String getExecutionvenue() {
			return APOLLOPARSERROW_DATA_RECORD[0][39];
		}

		public String getUsinamespace() {
			return APOLLOPARSERROW_DATA_RECORD[0][40];
		}

		public String getUsitransid() {
			return APOLLOPARSERROW_DATA_RECORD[0][41];
		}

		public String getIsallocated() {
			return APOLLOPARSERROW_DATA_RECORD[0][42];
		}

		public String getIsmultiasset() {
			return APOLLOPARSERROW_DATA_RECORD[0][43];
		}

		public String getSecondaryassetclass() {
			return APOLLOPARSERROW_DATA_RECORD[0][44];
		}

		public String getIsnovated() {
			return APOLLOPARSERROW_DATA_RECORD[0][45];
		}

		public String getIspartialtermination() {
			return APOLLOPARSERROW_DATA_RECORD[0][46];
		}

		public String getIscashflowchange() {
			return APOLLOPARSERROW_DATA_RECORD[0][47];
		}

		public String getCorporateaction() {
			return APOLLOPARSERROW_DATA_RECORD[0][48];
		}

		public String getVersion() {
			return APOLLOPARSERROW_DATA_RECORD[0][49];
		}

		public String get(String name) {
			return APOLLOPARSERROW_DATA_RECORD[0][DATA_RECORD_MAPPER.get(name)];
		}
		
	}

	public boolean populate(String[] data) {
		this.data[0] = data;
		return true;
	}

	public ApolloParserRow() {
		headerTitles = new HashMap<String, String[][]>();
		headerTitles.put(DefaultRow.DEFAULT_HEADER_RECORD, APOLLOPARSERROW_DATA_RECORD);

		this.data = APOLLOPARSERROW_DATA_RECORD;
		headerClasses.put(DefaultRow.DEFAULT_HEADER_RECORD, Record.class);
	}

}
