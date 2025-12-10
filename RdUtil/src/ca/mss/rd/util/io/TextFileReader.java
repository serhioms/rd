package ca.mss.rd.util.io;

import java.io.FileInputStream;

public class TextFileReader extends InputReader {
	final static public String module = TextFileReader.class.getName();
	final static public long serialVersionUID = module.hashCode();

	private String path;

	public final String getPath() {
		return path;
	}

	/**
	 * @param path
	 */
	public TextFileReader(String path) {
		this.path = path;
	}

	/* (non-Javadoc)
	 * @see ca.mss.rd.util.io.TextReader#open()
	 */
	@Override
	final public InputReader open() {
		try {
			return open(new FileInputStream(path));
		} catch (Exception e) {
			throw new RuntimeException("Can not open text file [path=" + path + "]", e);
		}
	}

}
