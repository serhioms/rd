package com.scotiabank.maestro.msg.workflow.condition;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

import ca.mss.rd.util.Logger;

public class WorkflowValidationVO implements Serializable {
	private static final long serialVersionUID = WorkflowValidationVO.class.getName().hashCode();
	
	public final List<WorkflowValidationMessageVO> message = new ArrayList<WorkflowValidationMessageVO>(0);
	
	public int countActivity, countStage, countSubflow, countSeq, countPar, countStart, countFail, countSkip, countIn, countVal, countOut, countDis, countGen, countGlob, countVar;
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(message);
	}

	public void add(WorkflowValidationMessageVO msg) {
		message.add(msg);
		Logger.WARNING.printf("%4d) %s[%s]: %s", message.size(), msg.getName(), msg.getCode(), msg.getMessage());
	}

	
}
