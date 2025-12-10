package ca.mss.rd.flow.tools.prop;

public enum ActivitiVariableType {

    STRING,
    INTEGER,
    SHORT,
    LONG,
    DOUBLE,
    BOOLEAN,
    DATE,
    BINARY,
    SERIALIZABLE;

    public String value() {
        return name();
    }

    public static ActivitiVariableType fromValue(String v) {
        return valueOf(v);
    }

}