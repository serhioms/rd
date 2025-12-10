package org.apache.log4j;

public class Logger {

	public Logger(String clazz) {
		throw new RuntimeException("Log4j.jar is not available");
	}

	public Logger(Class<?> clazz) {
		throw new RuntimeException("Log4j.jar is not available");
	}

	
	public boolean isVerboseEnabled() {

		return false;
	}

	
	public boolean isAllEnabled() {

		return false;
	}

	
	public void verbose(Throwable t, String msg, Object... args) {

	}

	
	public void verbose(String msg, Object... args) {

	}

	
	public void debug(Throwable t, String msg, Object... args) {

	}

	
	public void debug(String msg, Object... args) {

	}

	
	public void error(Throwable t, String msg, Object... args) {

	}

	
	public void error(String msg, Object... args) {

	}

	
	public void fatal(Throwable t, String msg, Object... args) {

	}

	
	public void fatal(String msg, Object... args) {

	}

	
	public void info(Throwable t, String msg, Object... args) {

	}

	
	public void info(String msg, Object... args) {

	}

	
	public boolean isDebugEnabled() {

		return false;
	}

	
	public boolean isInfoEnabled() {

		return false;
	}

	
	public void warn(Throwable t, String msg, Object... args) {

	}

	
	public void warn(String msg, Object... args) {

	}

	
	public void shutDown() {
	}

}
