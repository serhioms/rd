package ca.mss.rd.parser.impl;

import java.util.Map;


public interface ParserRow {

	public Map<String, String[][]> headerDefinition();
	public Map<String, Class<?>> headerClasses();
	
	public boolean populate(String[] data);
	public boolean populate(String[] data, String row);
	
	public String[][] toArray();

}


