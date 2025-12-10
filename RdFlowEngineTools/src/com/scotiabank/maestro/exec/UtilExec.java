package com.scotiabank.maestro.exec;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import ca.mss.rd.flow.tools.prop.DbParamType;
public class UtilExec {

	public static final String DEFAULT_DATA_FORMAT_TIMESTAMP = "yyyy MM dd HH mm ss S";
	public static final String DEFAULT_DATA_FORMAT_DATE = "yyyy MM dd";

	static private String dateFormat = DEFAULT_DATA_FORMAT_DATE;
	static private String timestampFormat = DEFAULT_DATA_FORMAT_TIMESTAMP;

	public static Object getParameterValue(Object value, DbParamType type) {
		Object result = null;
		
		if( value == null ) {
			return null;
		}
		
		switch (type) {
		case DATE:
			result = parseDate(value.toString(), getDateFormat());
			break;
		case TIMESTAMP:
			result = parseDate(value.toString(), getTimestampFormat());
			break;
		case NUMBER:
		case STRING:
			result = value;
			break;
		default:
			throw new IllegalStateException("Unable to handle parameter type " + type.name());
		}
		
		return result;
	}

	public static String parameterToString(Object value, DbParamType type) {
		String result = null;
		
		if (value == null) {
			return null;
		}
		
		switch (type) {
		case DATE:
			result = formatDate(value, getDateFormat());
			break;
		case TIMESTAMP:
			result = formatDate(value, getTimestampFormat());
			break;
		case NUMBER:
		case STRING:
			result = value.toString();
			break;
		default:
			throw new IllegalStateException("Unable to handle parameter type " + type.name());
		}
		
		return result;		
	}
	
	static private Date parseDate(String value, String format) {
		Date result = null;
		
		try {
			SimpleDateFormat formatter = new SimpleDateFormat(format);
			java.util.Date utilDate = formatter.parse(value);
			result = new Date(utilDate.getTime());
		} catch (ParseException e) {
			throw new IllegalStateException(String.format("Unable to parse date '%s' using pattern '%s'", value, format), e);
		}
		
		return result;
	}
	
	static private String formatDate(Object value, String format) {
		String result = null;
		
		try {
			SimpleDateFormat formatter = new SimpleDateFormat(format);
			result = formatter.format(value);
		} catch (Exception e) {
			throw new IllegalStateException(String.format("Unable to format value '%s' using pattern '%s'", value.toString(), format), e);
		}
		
		return result;
	}
	
	static public String getDateFormat() {
		return dateFormat == null ? DEFAULT_DATA_FORMAT_DATE : dateFormat;
	}

	static public String getTimestampFormat() {
		return timestampFormat == null ? DEFAULT_DATA_FORMAT_TIMESTAMP : timestampFormat;
	}

}
