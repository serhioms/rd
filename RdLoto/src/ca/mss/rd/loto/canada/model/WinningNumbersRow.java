package ca.mss.rd.loto.canada.model;

import java.util.HashMap;

import ca.mss.rd.parser.impl.DefaultRow;
import ca.mss.rd.parser.impl.ParserRow;
import ca.mss.rd.util.UtilString;

public class WinningNumbersRow extends DefaultRow implements ParserRow {

	final static public String module = WinningNumbersRow.class.getName();
	final static public long serialVersionUID = module.hashCode();

	final public static String[] HEADER_RECORD = new String[] { "LotoName", "Date", "WinTicket", "PrizeRequest" };
	private String[][] WINNINGNUMBERSROW_DATA_HEADER = new String[][] { null, HEADER_RECORD };

	final public Header record = new Header();

	public class Header {
		public String getLotoname() {
			return WINNINGNUMBERSROW_DATA_HEADER[0][0];
		}

		public String getDate() {
			return WINNINGNUMBERSROW_DATA_HEADER[0][1];
		}

		public String getWinticket() {
			return WINNINGNUMBERSROW_DATA_HEADER[0][2];
		}

		public String getPrizerequest() {
			return WINNINGNUMBERSROW_DATA_HEADER[0][3];
		}
	}

	public boolean populate(String[] data) {
		this.data[0] = data;
		data[0] = UtilString.getIdentifier(data[0]);
		return true;
	}

	public WinningNumbersRow() {
		headerTitles = new HashMap<String, String[][]>();
		headerTitles.put(DefaultRow.DEFAULT_HEADER_RECORD, WINNINGNUMBERSROW_DATA_HEADER);
		this.data = WINNINGNUMBERSROW_DATA_HEADER;
		headerClasses.put(DefaultRow.DEFAULT_HEADER_RECORD, Header.class);
	}
}
