package ca.mss.rd.flow.tools.prop;

import java.sql.Types;


public enum DbParamType {
	STRING(Types.VARCHAR, "String"),
	NUMBER(Types.NUMERIC, "Number"),
	DATE(Types.DATE, "Date"),
	TIMESTAMP(Types.TIMESTAMP, "Timestamp");
    
	private int sqlType;
	private String displayName;
	
	private DbParamType(int sqlType, String displayName) {
		this.sqlType = sqlType;
		this.displayName = displayName;
	}
	
    public String value() {
        return name();
    }

    public static DbParamType fromValue(String v) {
        return valueOf(v);
    }
    
	public String getDisplayName() {
		return displayName;
	}
	
	public int sqlType() {
		return sqlType;
	}
}

