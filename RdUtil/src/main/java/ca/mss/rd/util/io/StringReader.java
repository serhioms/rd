package ca.mss.rd.util.io;

import java.io.ByteArrayInputStream;

public class StringReader extends InputReader {
	final static public String module = StringReader.class.getName();
	final static public long serialVersionUID = module.hashCode();

	private String content;

	public StringReader(String content) {
		this.content = content;
	}

	@Override
	final public InputReader open() {
		try {
			return open(new ByteArrayInputStream(content.getBytes()));
		} catch (Exception e) {
			throw new RuntimeException("Can not open stream from string [" + content.substring(0, 10) + "...]", e);
		}
	}

}
