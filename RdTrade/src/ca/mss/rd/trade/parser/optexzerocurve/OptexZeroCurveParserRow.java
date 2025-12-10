package ca.mss.rd.trade.parser.optexzerocurve;

import java.util.HashMap;

import ca.mss.rd.parser.impl.DefaultRow;
import ca.mss.rd.parser.impl.ParserRow;

public class OptexZeroCurveParserRow extends DefaultRow implements ParserRow {

	final static public String module = OptexZeroCurveParserRow.class.getName();
	final static public long serialVersionUID = module.hashCode();

	private String[][] OPTEXZEROCURVEPARSERROW_DATA_OTHER = new String[][] {
			null,
			{ "buildname", "curvename", "curvestatus", "curvedate", "label", "type", "start", "end", "na", "rate (bid)",
					"basis swap (offer-bid)", "no futures convexity adj", "UseConvexityAdjustment", "CalculatedYieldBid",
					"CalculatedYieldOffer" } };

	final public Other other = new Other();

	public class Other {
		public String getBuildname() {
			return OPTEXZEROCURVEPARSERROW_DATA_OTHER[0][0];
		}

		public String getCurvename() {
			return OPTEXZEROCURVEPARSERROW_DATA_OTHER[0][1];
		}

		public String getCurvestatus() {
			return OPTEXZEROCURVEPARSERROW_DATA_OTHER[0][2];
		}

		public String getCurvedate() {
			return OPTEXZEROCURVEPARSERROW_DATA_OTHER[0][3];
		}

		public String getLabel() {
			return OPTEXZEROCURVEPARSERROW_DATA_OTHER[0][4];
		}

		public String getType() {
			return OPTEXZEROCURVEPARSERROW_DATA_OTHER[0][5];
		}

		public String getStart() {
			return OPTEXZEROCURVEPARSERROW_DATA_OTHER[0][6];
		}

		public String getEnd() {
			return OPTEXZEROCURVEPARSERROW_DATA_OTHER[0][7];
		}

		public String getNa() {
			return OPTEXZEROCURVEPARSERROW_DATA_OTHER[0][8];
		}

		public String getRateBid() {
			return OPTEXZEROCURVEPARSERROW_DATA_OTHER[0][9];
		}

		public String getBasisSwapOfferBid() {
			return OPTEXZEROCURVEPARSERROW_DATA_OTHER[0][10];
		}

		public String getNoFuturesConvexityAdj() {
			return OPTEXZEROCURVEPARSERROW_DATA_OTHER[0][11];
		}

		public String getUseconvexityadjustment() {
			return OPTEXZEROCURVEPARSERROW_DATA_OTHER[0][12];
		}

		public String getCalculatedyieldbid() {
			return OPTEXZEROCURVEPARSERROW_DATA_OTHER[0][13];
		}

		public String getCalculatedyieldoffer() {
			return OPTEXZEROCURVEPARSERROW_DATA_OTHER[0][14];
		}
	}

	private String[][] OPTEXZEROCURVEPARSERROW_DATA_FUTURES = new String[][] {
			null,
			{ "buildname", "curvename", "curvestatus", "curvedate", "label", "type", "start", "end", "futures price",
					"bid price", "basis swap (offer-bid)", "futures convexity adj", "UseConvexityAdjustment",
					"CalculatedYieldBid", "CalculatedYieldOffer" } };

	final public Futures futures = new Futures();

	public class Futures {
		public String getBuildname() {
			return OPTEXZEROCURVEPARSERROW_DATA_FUTURES[0][0];
		}

		public String getCurvename() {
			return OPTEXZEROCURVEPARSERROW_DATA_FUTURES[0][1];
		}

		public String getCurvestatus() {
			return OPTEXZEROCURVEPARSERROW_DATA_FUTURES[0][2];
		}

		public String getCurvedate() {
			return OPTEXZEROCURVEPARSERROW_DATA_FUTURES[0][3];
		}

		public String getLabel() {
			return OPTEXZEROCURVEPARSERROW_DATA_FUTURES[0][4];
		}

		public String getType() {
			return OPTEXZEROCURVEPARSERROW_DATA_FUTURES[0][5];
		}

		public String getStart() {
			return OPTEXZEROCURVEPARSERROW_DATA_FUTURES[0][6];
		}

		public String getEnd() {
			return OPTEXZEROCURVEPARSERROW_DATA_FUTURES[0][7];
		}

		public String getFuturesPrice() {
			return OPTEXZEROCURVEPARSERROW_DATA_FUTURES[0][8];
		}

		public String getBidPrice() {
			return OPTEXZEROCURVEPARSERROW_DATA_FUTURES[0][9];
		}

		public String getBasisSwapOfferBid() {
			return OPTEXZEROCURVEPARSERROW_DATA_FUTURES[0][10];
		}

		public String getFuturesConvexityAdj() {
			return OPTEXZEROCURVEPARSERROW_DATA_FUTURES[0][11];
		}

		public String getUseconvexityadjustment() {
			return OPTEXZEROCURVEPARSERROW_DATA_FUTURES[0][12];
		}

		public String getCalculatedyieldbid() {
			return OPTEXZEROCURVEPARSERROW_DATA_FUTURES[0][13];
		}

		public String getCalculatedyieldoffer() {
			return OPTEXZEROCURVEPARSERROW_DATA_FUTURES[0][14];
		}
	}

	final public static String OPTEXZEROCURVEPARSERROW_HEADER_OTHER = "Other";
	final public static String OPTEXZEROCURVEPARSERROW_HEADER_FUTURES = "Futures";

	public boolean populate(String[] data) {
		if ("fut".equals(data[5])) {
			this.OPTEXZEROCURVEPARSERROW = OPTEXZEROCURVEPARSERROW_HEADER_FUTURES;
			this.data = OPTEXZEROCURVEPARSERROW_DATA_FUTURES;
			this.data[0] = data;
		} else {
			this.OPTEXZEROCURVEPARSERROW = OPTEXZEROCURVEPARSERROW_HEADER_OTHER;
			this.data = OPTEXZEROCURVEPARSERROW_DATA_OTHER;
			this.data[0] = data;
		}
		return true;
	}

	private String OPTEXZEROCURVEPARSERROW;

	public boolean isOther() {
		return OPTEXZEROCURVEPARSERROW_HEADER_OTHER.equals(OPTEXZEROCURVEPARSERROW);
	}

	public boolean isFutures() {
		return OPTEXZEROCURVEPARSERROW_HEADER_FUTURES.equals(OPTEXZEROCURVEPARSERROW);
	}

	public OptexZeroCurveParserRow() {
		headerTitles = new HashMap<String, String[][]>();
		headerTitles.put(OPTEXZEROCURVEPARSERROW_HEADER_OTHER, OPTEXZEROCURVEPARSERROW_DATA_OTHER);
		headerTitles.put(OPTEXZEROCURVEPARSERROW_HEADER_FUTURES, OPTEXZEROCURVEPARSERROW_DATA_FUTURES);
	}

}
