package ca.mss.rd.trade.parser.markit.liquiditymetrics;

import java.util.HashMap;
import java.util.Map;

import ca.mss.rd.parser.impl.DefaultRow;
import ca.mss.rd.parser.impl.Getter;
import ca.mss.rd.parser.impl.ParserRow;

public class MarkitLiquidityMetricsRow extends DefaultRow implements ParserRow {

	final static public String module = MarkitLiquidityMetricsRow.class.getName();
	final static public long serialVersionUID = module.hashCode();

	static private Map<String, Integer> DATA_RECORD_MAPPER = new HashMap<String, Integer>();

	private String[][] MARKITLIQUIDITYMETRICSROW_DATA_RECORD = new String[][] {
			null,
			{ "Date", "Ticker", "Name", "RedCode", "Tier", "Currency", "DocClause", "RunningCoupon", "Primary Coupon", "Region",
					"MarkitSector", "Range", "Upfront6MMid", "Upfront6MBASpread", "ConvSpread6MMid", "ConvSpread6MBASpread",
					"Upfront1YMid", "Upfront1YBASpread", "ConvSpread1YMid", "ConvSpread1YBASpread", "Upfront2YMid",
					"Upfront2YBASpread", "ConvSpread2YMid", "ConvSpread2YBASpread", "Upfront3YMid", "Upfront3YBASpread",
					"ConvSpread3YMid", "ConvSpread3YBASpread", "Upfront4YMid", "Upfront4YBASpread", "ConvSpread4YMid",
					"ConvSpread4YBASpread", "Upfront5YMid", "Upfront5YBASpread", "ConvSpread5YMid", "ConvSpread5YBASpread",
					"Upfront7YMid", "Upfront7YBASpread", "ConvSpread7YMid", "ConvSpread7YBASpread", "Upfront10YMid",
					"Upfront10YBASpread", "ConvSpread10YMid", "ConvSpread10YBASpread", "BidAskType5Y", "DealersCount",
					"QuotesCount", "CompositeDepth5y", "EstimatedNotional", "Liquidity Score" } };

	final public Record record = new Record();

	public class Record implements Getter {
		public String getDate() {
			return MARKITLIQUIDITYMETRICSROW_DATA_RECORD[0][0];
		}

		public String getTicker() {
			return MARKITLIQUIDITYMETRICSROW_DATA_RECORD[0][1];
		}

		public String getName() {
			return MARKITLIQUIDITYMETRICSROW_DATA_RECORD[0][2];
		}

		public String getRedcode() {
			return MARKITLIQUIDITYMETRICSROW_DATA_RECORD[0][3];
		}

		public String getTier() {
			return MARKITLIQUIDITYMETRICSROW_DATA_RECORD[0][4];
		}

		public String getCurrency() {
			return MARKITLIQUIDITYMETRICSROW_DATA_RECORD[0][5];
		}

		public String getDocclause() {
			return MARKITLIQUIDITYMETRICSROW_DATA_RECORD[0][6];
		}

		public String getRunningcoupon() {
			return MARKITLIQUIDITYMETRICSROW_DATA_RECORD[0][7];
		}

		public String getPrimaryCoupon() {
			return MARKITLIQUIDITYMETRICSROW_DATA_RECORD[0][8];
		}

		public String getRegion() {
			return MARKITLIQUIDITYMETRICSROW_DATA_RECORD[0][9];
		}

		public String getMarkitsector() {
			return MARKITLIQUIDITYMETRICSROW_DATA_RECORD[0][10];
		}

		public String getRange() {
			return MARKITLIQUIDITYMETRICSROW_DATA_RECORD[0][11];
		}

		public String getUpfront6mmid() {
			return MARKITLIQUIDITYMETRICSROW_DATA_RECORD[0][12];
		}

		public String getUpfront6mbaspread() {
			return MARKITLIQUIDITYMETRICSROW_DATA_RECORD[0][13];
		}

		public String getConvspread6mmid() {
			return MARKITLIQUIDITYMETRICSROW_DATA_RECORD[0][14];
		}

		public String getConvspread6mbaspread() {
			return MARKITLIQUIDITYMETRICSROW_DATA_RECORD[0][15];
		}

		public String getUpfront1ymid() {
			return MARKITLIQUIDITYMETRICSROW_DATA_RECORD[0][16];
		}

		public String getUpfront1ybaspread() {
			return MARKITLIQUIDITYMETRICSROW_DATA_RECORD[0][17];
		}

		public String getConvspread1ymid() {
			return MARKITLIQUIDITYMETRICSROW_DATA_RECORD[0][18];
		}

		public String getConvspread1ybaspread() {
			return MARKITLIQUIDITYMETRICSROW_DATA_RECORD[0][19];
		}

		public String getUpfront2ymid() {
			return MARKITLIQUIDITYMETRICSROW_DATA_RECORD[0][20];
		}

		public String getUpfront2ybaspread() {
			return MARKITLIQUIDITYMETRICSROW_DATA_RECORD[0][21];
		}

		public String getConvspread2ymid() {
			return MARKITLIQUIDITYMETRICSROW_DATA_RECORD[0][22];
		}

		public String getConvspread2ybaspread() {
			return MARKITLIQUIDITYMETRICSROW_DATA_RECORD[0][23];
		}

		public String getUpfront3ymid() {
			return MARKITLIQUIDITYMETRICSROW_DATA_RECORD[0][24];
		}

		public String getUpfront3ybaspread() {
			return MARKITLIQUIDITYMETRICSROW_DATA_RECORD[0][25];
		}

		public String getConvspread3ymid() {
			return MARKITLIQUIDITYMETRICSROW_DATA_RECORD[0][26];
		}

		public String getConvspread3ybaspread() {
			return MARKITLIQUIDITYMETRICSROW_DATA_RECORD[0][27];
		}

		public String getUpfront4ymid() {
			return MARKITLIQUIDITYMETRICSROW_DATA_RECORD[0][28];
		}

		public String getUpfront4ybaspread() {
			return MARKITLIQUIDITYMETRICSROW_DATA_RECORD[0][29];
		}

		public String getConvspread4ymid() {
			return MARKITLIQUIDITYMETRICSROW_DATA_RECORD[0][30];
		}

		public String getConvspread4ybaspread() {
			return MARKITLIQUIDITYMETRICSROW_DATA_RECORD[0][31];
		}

		public String getUpfront5ymid() {
			return MARKITLIQUIDITYMETRICSROW_DATA_RECORD[0][32];
		}

		public String getUpfront5ybaspread() {
			return MARKITLIQUIDITYMETRICSROW_DATA_RECORD[0][33];
		}

		public String getConvspread5ymid() {
			return MARKITLIQUIDITYMETRICSROW_DATA_RECORD[0][34];
		}

		public String getConvspread5ybaspread() {
			return MARKITLIQUIDITYMETRICSROW_DATA_RECORD[0][35];
		}

		public String getUpfront7ymid() {
			return MARKITLIQUIDITYMETRICSROW_DATA_RECORD[0][36];
		}

		public String getUpfront7ybaspread() {
			return MARKITLIQUIDITYMETRICSROW_DATA_RECORD[0][37];
		}

		public String getConvspread7ymid() {
			return MARKITLIQUIDITYMETRICSROW_DATA_RECORD[0][38];
		}

		public String getConvspread7ybaspread() {
			return MARKITLIQUIDITYMETRICSROW_DATA_RECORD[0][39];
		}

		public String getUpfront10ymid() {
			return MARKITLIQUIDITYMETRICSROW_DATA_RECORD[0][40];
		}

		public String getUpfront10ybaspread() {
			return MARKITLIQUIDITYMETRICSROW_DATA_RECORD[0][41];
		}

		public String getConvspread10ymid() {
			return MARKITLIQUIDITYMETRICSROW_DATA_RECORD[0][42];
		}

		public String getConvspread10ybaspread() {
			return MARKITLIQUIDITYMETRICSROW_DATA_RECORD[0][43];
		}

		public String getBidasktype5y() {
			return MARKITLIQUIDITYMETRICSROW_DATA_RECORD[0][44];
		}

		public String getDealerscount() {
			return MARKITLIQUIDITYMETRICSROW_DATA_RECORD[0][45];
		}

		public String getQuotescount() {
			return MARKITLIQUIDITYMETRICSROW_DATA_RECORD[0][46];
		}

		public String getCompositedepth5y() {
			return MARKITLIQUIDITYMETRICSROW_DATA_RECORD[0][47];
		}

		public String getEstimatednotional() {
			return MARKITLIQUIDITYMETRICSROW_DATA_RECORD[0][48];
		}

		public String getLiquidityScore() {
			return MARKITLIQUIDITYMETRICSROW_DATA_RECORD[0][49];
		}
		
		public String get(String name) {
			return MARKITLIQUIDITYMETRICSROW_DATA_RECORD[0][DATA_RECORD_MAPPER.get(name)];
		}
		
	}

	public boolean populate(String[] data) {
		this.data[0] = data;
		return true;
	}

	public MarkitLiquidityMetricsRow() {
		headerTitles = new HashMap<String, String[][]>();
		headerTitles.put(DefaultRow.DEFAULT_HEADER_RECORD, MARKITLIQUIDITYMETRICSROW_DATA_RECORD);

		this.data = MARKITLIQUIDITYMETRICSROW_DATA_RECORD;
		headerClasses.put(DefaultRow.DEFAULT_HEADER_RECORD, Record.class);
	}

}
