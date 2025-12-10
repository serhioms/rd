package ca.mss.rd.flow.tools.prop;


public enum ActivitiVariableScope {

	LOCAL, GLOBAL;

	public String value() {
		return name();
	}

	public static ActivitiVariableScope fromValue(String v) {
		return valueOf(v);
	}

}
