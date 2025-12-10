package ca.mss.rd.trade.parser.optexzerocurve;

import java.util.HashMap;
import java.util.Map;

import ca.mss.rd.parser.impl.DefaultRow;
import ca.mss.rd.parser.impl.Getter;
import ca.mss.rd.parser.impl.ParserRow;

public class QuantifiOptexZeroCurveParserRow extends DefaultRow implements ParserRow {

	final static public String module = QuantifiOptexZeroCurveParserRow.class.getName();
	final static public long serialVersionUID = module.hashCode();

	static private Map<String, Integer> DATA_RECORD_MAPPER = new HashMap<String, Integer>();

	private String[][] QUANTIFIOPTEXZEROCURVEPARSERROW_DATA_HEADER = new String[][] {
			null,
			{ "Tenor", " CurveName", " CurveDate", " MarketSet", " Type", " Label", " Maturity Date", " Calculated Yield",
					" SortOrder" } };

	final public Record record = new Record();

	public class Record implements Getter {
		public String getTenor() {
			return QUANTIFIOPTEXZEROCURVEPARSERROW_DATA_HEADER[0][0];
		}

		public String getCurvename() {
			return QUANTIFIOPTEXZEROCURVEPARSERROW_DATA_HEADER[0][1];
		}

		public String getCurvedate() {
			return QUANTIFIOPTEXZEROCURVEPARSERROW_DATA_HEADER[0][2];
		}

		public String getMarketset() {
			return QUANTIFIOPTEXZEROCURVEPARSERROW_DATA_HEADER[0][3];
		}

		public String getType() {
			return QUANTIFIOPTEXZEROCURVEPARSERROW_DATA_HEADER[0][4];
		}

		public String getLabel() {
			return QUANTIFIOPTEXZEROCURVEPARSERROW_DATA_HEADER[0][5];
		}

		public String getMaturityDate() {
			return QUANTIFIOPTEXZEROCURVEPARSERROW_DATA_HEADER[0][6];
		}

		public String getCalculatedYield() {
			return QUANTIFIOPTEXZEROCURVEPARSERROW_DATA_HEADER[0][7];
		}

		public String getSortorder() {
			return QUANTIFIOPTEXZEROCURVEPARSERROW_DATA_HEADER[0][8];
		}
		
		public String get(String name) {
			return QUANTIFIOPTEXZEROCURVEPARSERROW_DATA_HEADER[0][DATA_RECORD_MAPPER.get(name)];
		}
	}

	public boolean populate(String[] data) {
		this.data[0] = data;
		return true;
	}

	public QuantifiOptexZeroCurveParserRow() {
		headerTitles = new HashMap<String, String[][]>();
		headerTitles.put(DefaultRow.DEFAULT_HEADER_RECORD, QUANTIFIOPTEXZEROCURVEPARSERROW_DATA_HEADER);

		this.data = QUANTIFIOPTEXZEROCURVEPARSERROW_DATA_HEADER;

		headerClasses.put(DefaultRow.DEFAULT_HEADER_RECORD, Record.class);
	}

}
