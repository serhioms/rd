package ca.mss.rd.flow.tools.exec;

import ca.mss.rd.flow.prop.RdExecDefault;



public class RdSqlLoader extends RdExecDefault {

	private String dataFileUri, controlFileUri, rejectFileUri, discardFileUri, logFileUri, exitCodeProp, nRejected, nDiscardRowsProp;

	public String getRejectFileUri() {
		return rejectFileUri;
	}


	public void setRejectFileUri(String rejectFileUri) {
		this.rejectFileUri = rejectFileUri;
		getValSet().add(rejectFileUri);
	}


	public String getControlFileUri() {
		return controlFileUri;
	}


	public void setControlFileUri(String controlFileUri) {
		this.controlFileUri = controlFileUri;
		getValSet().add(controlFileUri);
	}


	public String getDataFileUri() {
		return dataFileUri;
	}


	public void setDataFileUri(String dataFileUri) {
		this.dataFileUri = dataFileUri;
		getValSet().add(dataFileUri);
	}


	public String getDiscardFileUri() {
		return discardFileUri;
	}


	public void setDiscardFileUri(String discardFileUri) {
		this.discardFileUri = discardFileUri;
		getValSet().add(discardFileUri);
	}


	public String getExitCodeProp() {
		return exitCodeProp;
	}


	public void setExitCodeProp(String exitCodeProp) {
		this.exitCodeProp = exitCodeProp;
		getOutSet().add(exitCodeProp);
	}


	public String getLogFileUri() {
		return logFileUri;
	}


	public void setLogFileUri(String logFileUri) {
		this.logFileUri = logFileUri;
		getValSet().add(logFileUri);
	}


	public String getnDiscardRowsProp() {
		return nDiscardRowsProp;
	}


	public void setnDiscardRowsProp(String nDiscardRowsProp) {
		this.nDiscardRowsProp = nDiscardRowsProp;
		getOutSet().add(nDiscardRowsProp);
	}


	public String getnRejected() {
		return nRejected;
	}


	public void setnRejected(String nRejected) {
		this.nRejected = nRejected;
		getOutSet().add(nRejected);
	}


	@Override
	public String toString() {
		return String.format("SqlLoader[data=%s][control=%s][reject=%s][discard=%s][log=%s][exitCode=%s][nrejected=%s][ndiscarded=%s]",dataFileUri, controlFileUri, rejectFileUri, discardFileUri, logFileUri, exitCodeProp, nRejected, nDiscardRowsProp);
	}
}
