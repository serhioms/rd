package ca.mss.rd.parser.impl;

import java.util.HashMap;
import java.util.Map;


public class DefaultRow implements ParserRow {

	final static public String module = DefaultRow.class.getName();
	final static public long serialVersionUID = module.hashCode();

	final public static String DEFAULT_HEADER_RECORD = "Record";

	
	public DefaultRow() {
	}
	
	protected String[][] data;
	protected String row;

	@Override
	public String[][] toArray() {
		return data;
	}

	protected Map<String, String[][]> headerTitles;

	@Override
	public Map<String, String[][]> headerDefinition() {
		return headerTitles;
	}

	final protected Map<String, Class<?>> headerClasses = new HashMap<String, Class<?>>();

	@Override
	public Map<String, Class<?>> headerClasses() {
		return headerClasses;
	}

	@Override
	public boolean populate(String[] data) {
		return true;
	}

	@Override
	public boolean populate(String[] data, String row) {
		this.row = row; 
		return populate(data);
	}

	
}
