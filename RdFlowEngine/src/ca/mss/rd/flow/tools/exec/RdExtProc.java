package ca.mss.rd.flow.tools.exec;

import ca.mss.rd.flow.prop.RdExecDefault;


public class RdExtProc extends RdExecDefault {

	private String commandLine, exitCode, workingDir;

	
	public RdExtProc() {
		super();
	}

	public String getCommandLine() {
		return commandLine;
	}

	public void setCommandLine(String commandLine) {
		this.commandLine = commandLine;
		getValSet().add(commandLine);
	}

	public String getExitCode() {
		return exitCode;
	}

	public void setExitCode(String exitCode) {
		this.exitCode = exitCode;
		getOutSet().add(exitCode);
	}

	public String getWorkingDir() {
		return workingDir;
	}

	public void setWorkingDir(String workingDir) {
		this.workingDir = workingDir;
		getValSet().add(workingDir);
	}


	@Override
	public String toString() {
		return String.format("ExtProc[command=%s]Exit[%s]Dir[%s]", commandLine, exitCode, workingDir);
	}

}
