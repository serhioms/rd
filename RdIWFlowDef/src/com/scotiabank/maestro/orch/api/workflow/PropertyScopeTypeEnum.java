package com.scotiabank.maestro.orch.api.workflow;

import hidden.org.codehaus.plexus.interpolation.util.StringUtils;

public enum PropertyScopeTypeEnum {

	GLOBAL("Property is available throughout workflow engine."),
	GENERAL("Property is available throughout the entire workflow."),
	INPUT("Property is used as an input"),
	OUTPUT("Property is used as an output");
	
	private String desc;
	
	private PropertyScopeTypeEnum(String desc) {
		this.desc = desc;
	}
	
	public String getDesc() {
		return this.desc;
	}

	public String getDescriptor() {
		String descriptor = this.name().toLowerCase();
		return StringUtils.capitalizeFirstLetter(descriptor);
	}
}
