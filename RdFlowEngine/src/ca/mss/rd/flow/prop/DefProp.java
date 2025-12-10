package ca.mss.rd.flow.prop;

import java.util.Comparator;
import java.util.Map;

import com.scotiabank.maestro.orch.api.workflow.PropertyScopeTypeEnum;

public class DefProp implements RdProp {

	final protected String nameSource;
	final protected String valueTarget;
	final protected PropertyScopeTypeEnum scope; 
	final public boolean isMap; 
	
	private Integer id; 
	
	
	private DefProp() {
		throw new RuntimeException("Default constructor is not allowed!");
	}

	// FIXME: Clear out property scope  
	public DefProp(String name, String value) {
		this(name, value, PropertyScopeTypeEnum.GLOBAL);
	}

	public DefProp(String name, String value, PropertyScopeTypeEnum scope) {
		this.nameSource = name;
		this.valueTarget = value;
		this.scope = scope;
		this.isMap = false;
	}

	public DefProp(String source, PropertyScopeTypeEnum scope, String target) {
		this.nameSource = source;
		this.valueTarget = target;
		this.scope = scope;
		this.isMap = true;
	}

	@Override
	public String getName() {
		return nameSource;
	}

	@Override
	public String getSource() {
		return nameSource;
	}

	@Override
	public String getTarget() {
		return valueTarget;
	}

	@Override
	public String getValue() {
		return valueTarget;
	}

	@Override
	public PropertyScopeTypeEnum getScope() {
		return scope;
	}

	
	@Override
	public String toString() {
		return String.format("%s.%s=\"%s\"", scope, nameSource, valueTarget==null?"": valueTarget);
	}

	@Override
	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public Integer getId() {
		return id;
	}
	
}
