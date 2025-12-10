package ca.mss.rd.loto.canada.model;

import java.util.HashMap;
import ca.mss.rd.parser.impl.DefaultRow;
import ca.mss.rd.parser.impl.ParserRow;

public class WinningPrizeKenoRow extends DefaultRow implements ParserRow {

	final static public String module = WinningPrizeKenoRow.class.getName();
	final static public long serialVersionUID = module.hashCode();

	final public static String[] HEADER_RECORD = new String[] { "Category", "Bet1D", "Bet2D", "Bet5D", "Bet10D", "PrizePer1DWagered" };
	private String[][] WINNINGPRIZEROW_DATA_HEADER = new String[][] { null, HEADER_RECORD};

	final public Header record = new Header();

	public class Header {
		public String getCategory() {
			return WINNINGPRIZEROW_DATA_HEADER[0][0];
		}

		public String getBet1D() {
			return WINNINGPRIZEROW_DATA_HEADER[0][1];
		}

		public String getBet2D() {
			return WINNINGPRIZEROW_DATA_HEADER[0][2];
		}

		public String getBet5D() {
			return WINNINGPRIZEROW_DATA_HEADER[0][3];
		}

		public String getBet10D() {
			return WINNINGPRIZEROW_DATA_HEADER[0][4];
		}

		public String getPrizePer1DWagered() {
			return WINNINGPRIZEROW_DATA_HEADER[0][5];
		}
	}

	public boolean populate(String[] data) {
		this.data[0] = data;
		return true;
	}

	public WinningPrizeKenoRow() {
		headerTitles = new HashMap<String, String[][]>();
		headerTitles.put(DefaultRow.DEFAULT_HEADER_RECORD, WINNINGPRIZEROW_DATA_HEADER);
		this.data = WINNINGPRIZEROW_DATA_HEADER;
		headerClasses.put(DefaultRow.DEFAULT_HEADER_RECORD, Header.class);
	}

}
