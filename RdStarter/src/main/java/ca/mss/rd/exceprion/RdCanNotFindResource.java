package ca.mss.rd.exceprion;

public class RdCanNotFindResource extends RuntimeException {

	public RdCanNotFindResource() {
	}

	public RdCanNotFindResource(String arg0) {
		super(arg0);
	}

	public RdCanNotFindResource(Throwable arg0) {
		super(arg0);
	}

	public RdCanNotFindResource(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

}
