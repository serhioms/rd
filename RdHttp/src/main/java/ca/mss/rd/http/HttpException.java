package ca.mss.rd.http;

public class HttpException extends RuntimeException {

	public HttpException() {
	}

	public HttpException(String message) {
		super(message);
	}

	public HttpException(Throwable cause) {
		super(cause);
	}

	public HttpException(String message, Throwable cause) {
		super(message, cause);
	}

}
