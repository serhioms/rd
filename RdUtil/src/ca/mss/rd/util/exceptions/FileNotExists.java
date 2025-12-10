package ca.mss.rd.util.exceptions;

public class FileNotExists extends RuntimeException {

	public FileNotExists() {
	}

	public FileNotExists(String arg0) {
		super(arg0);
	}

	public FileNotExists(Throwable arg0) {
		super(arg0);
	}

	public FileNotExists(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

}
