package ca.mss.rd.flow.tools.prop;



public class DbParam {

	public String name;
	public String expr;
	public Object value;
	public DbParamType type;

	public DbParam(String name, String type, String value) {
		super();
		this.name = name;
		this.type = DbParamType.fromValue(type);
		this.expr = value;
	}

	public DbParam(String name, DbParamType type, Object value) {
		super();
		this.name = name;
		this.type = type;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public DbParamType getType() {
		return type;
	}

	public void setType(DbParamType type) {
		this.type = type;
	}

	public Object getValue() {
		return value;
	}

	public String getExpr() {
		return expr;
	}

	public void setExpr(String expr) {
		this.expr = expr;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return String.format("DbParam [%s %s=%s]", type,  name, value);
	}

	
}
