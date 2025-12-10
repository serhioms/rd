package ca.mss.rd.loto.canada.model;

import java.util.HashMap;
import ca.mss.rd.parser.impl.DefaultRow;
import ca.mss.rd.parser.impl.ParserRow;

public class WinningPrizeRow extends DefaultRow implements ParserRow {

	final static public String module = WinningPrizeRow.class.getName();
	final static public long serialVersionUID = module.hashCode();

	final public static String[] HEADER_RECORD = new String[] { "Match", "WinTickets", "Prize" };
	private String[][] WINNINGPRIZEROW_DATA_HEADER = new String[][] { null, HEADER_RECORD};

	final public Header record = new Header();

	public class Header {
		public String getMatch() {
			return WINNINGPRIZEROW_DATA_HEADER[0][0];
		}

		public String getWintickets() {
			return WINNINGPRIZEROW_DATA_HEADER[0][1];
		}

		public String getPrize() {
			return WINNINGPRIZEROW_DATA_HEADER[0][2];
		}
	}

	public boolean populate(String[] data) {
		this.data[0] = data;
		return true;
	}

	public WinningPrizeRow() {
		headerTitles = new HashMap<String, String[][]>();
		headerTitles.put(DefaultRow.DEFAULT_HEADER_RECORD, WINNINGPRIZEROW_DATA_HEADER);
		this.data = WINNINGPRIZEROW_DATA_HEADER;
		headerClasses.put(DefaultRow.DEFAULT_HEADER_RECORD, Header.class);
	}

}
