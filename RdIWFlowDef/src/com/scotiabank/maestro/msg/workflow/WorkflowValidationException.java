package com.scotiabank.maestro.msg.workflow;

public class WorkflowValidationException extends RuntimeException {

	private static final long serialVersionUID = WorkflowValidationException.class.getName().hashCode();

	public WorkflowValidationException(String msg) {
		super(msg);
	}

	
	public WorkflowValidationException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
