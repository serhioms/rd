package ca.mss.rd.parser.impl;

public interface GenericRecord {

	public String getValue(String header);
	public String[] getHeader();
	
	public String getRow();
	
	public String getTime();
	public String getDate();
	
}
