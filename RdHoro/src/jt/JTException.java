package jt;

public class JTException extends Exception {
	
	static final long serialVersionUID =0xcafebaba;
	public long getUID() {
		return serialVersionUID;
	}

	public JTException() {
	}

	public JTException(String message) {
		super(message);
	}

	public JTException(Throwable cause) {
		super(cause);
	}

	public JTException(String message, Throwable cause) {
		super(message, cause);
	}

}
