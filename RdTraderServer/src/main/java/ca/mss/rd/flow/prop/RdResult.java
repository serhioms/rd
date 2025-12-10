package ca.mss.rd.flow.prop;

public class RdResult  {
	
	private static final String SILENCE = "";

	private long start, stop;


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
