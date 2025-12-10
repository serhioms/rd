package com.scotiabank.maestro.msg.workflow.condition;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;

public class WorkflowValidationMessageVO implements Serializable {
	private static final long serialVersionUID = WorkflowValidationMessageVO.class.getName().hashCode();
	
	private String code;
	private String message;
	private String name;
	
	/**
	 * Tell someone that this is bad and should stop processing ASAP
	 */
	public boolean shouldStopProcessing;

	public WorkflowValidationMessageVO(String code, String name, String message, boolean shouldStopProcessing) {
		super();
		this.code = code;
		this.name = name;
		this.message = message;
		this.shouldStopProcessing = shouldStopProcessing;
	}

	public WorkflowValidationMessageVO(String code, String name, String message) {
		this(code, name, message, false);
	}

	public WorkflowValidationMessageVO(String message) {
		this(null, null, message, false);
	}

	public String getName() {
		return name;
	}

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
