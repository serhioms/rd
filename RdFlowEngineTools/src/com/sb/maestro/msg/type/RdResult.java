package com.scotiabank.maestro.msg.type;

import java.util.ArrayList;
import java.util.List;

import ca.mss.rd.flow.tools.prop.DbParam;

public class RdResult  {
	
	private static final String SILENCE = "";

	private long start, stop;
	
	private StageTaskStatusTypeEnum completedStatus;
	
	private List<DbParam> results = new ArrayList<DbParam>(0);
	private String message = SILENCE;

	public RdResult() {
	}

	public long getStart() {
		return start;
	}

	public void setStart(long start) {
		this.start = start;
	}

	public long getStop() {
		return stop;
	}

	public void setStop(long stop) {
		this.stop = stop;
	}

	public StageTaskStatusTypeEnum getCompletedStatus() {
		return completedStatus;
	}

	public void setCompletedStatus(StageTaskStatusTypeEnum completedStatus) {
		this.completedStatus = completedStatus;
	}

	public List<DbParam> getResults() {
		return results;
	}

	public void setResults(List<DbParam> results) {
		this.results = results;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public long getDuration() {
		return stop-start;
	}
	
}
