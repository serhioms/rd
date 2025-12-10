package ca.mss.rd.util.io;

import java.io.FileInputStream;

public class BinaryFileReader extends BinaryInput {
	final static public String module = BinaryFileReader.class.getName();
	final static public long serialVersionUID = module.hashCode();

	private String path;

	public final String getPath() {
		return path;
	}

	/**
	 * @param path
	 */
	public BinaryFileReader(String path) {
		this.path = path;
	}

	/* (non-Javadoc)
	 * @see ca.mss.rd.util.io.BinaryFileReader#open()
	 */
	@Override
	final public BinaryInput open() {
		try {
			return open(new FileInputStream(path));
		} catch (Exception e) {
			throw new RuntimeException("Can not open binary file [path=" + path + "]", e);
		}
	}

}
