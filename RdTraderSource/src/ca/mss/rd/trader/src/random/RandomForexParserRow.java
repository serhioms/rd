package ca.mss.rd.trader.src.random;

import java.util.HashMap;
import java.util.Map;
import ca.mss.rd.parser.impl.DefaultRow;
import ca.mss.rd.parser.impl.Getter;
import ca.mss.rd.parser.impl.ParserRow;

public class RandomForexParserRow extends DefaultRow implements ParserRow {

	final static public String module = RandomForexParserRow.class.getName();
	final static public long serialVersionUID = module.hashCode();

	static private String map(Map<String, Integer> map, String name, int index) {
		map.put(name, index);
		return name;
	}

	static private Map<String, Integer> DATA_RECORD_MAPPER = new HashMap<String, Integer>();

	static private String[] DATA_RECORD_HEADER = new String[] { map(DATA_RECORD_MAPPER, "ASK", 0),
			map(DATA_RECORD_MAPPER, "BID", 1) };

	private String[][] DATA_RECORD = new String[][] { null, DATA_RECORD_HEADER };
	final public Record record = new Record();

	public class Record implements Getter {
		public String getAsk() {
			return DATA_RECORD[0][0];
		}

		public String getBid() {
			return DATA_RECORD[0][1];
		}

		public String get(String name) {
			return DATA_RECORD[0][DATA_RECORD_MAPPER.get(name)];
		}
	}

	public boolean populate(String[] data) {
		this.data[0] = data;
		return true;
	}

	public RandomForexParserRow() {
		headerTitles = new HashMap<String, String[][]>();
		headerTitles.put(DefaultRow.DEFAULT_HEADER_RECORD, DATA_RECORD);

		data = DATA_RECORD;

		headerClasses.put(DefaultRow.DEFAULT_HEADER_RECORD, Record.class);
	}

}
