package ca.mss.rd.util.io;

import java.io.BufferedWriter;
import java.io.FileWriter;

public class TextFileWriter {
	final static public String module = TextFileWriter.class.getName();
	final static public long serialVersionUID = module.hashCode();

	private String path;
	private BufferedWriter out;
	
	/**
	 * @return the path
	 */
	public final String getPath() {
		return path;
	}

	/**
	 * @param path
	 *            the path to set
	 */
	public final void setPath(String path) {
		this.path = path;
	}

	/**
	 * @param path
	 */
	public TextFileWriter(String path) {
		this();
		this.path = path;
	}

	public TextFileWriter() {
		this.out = null;
	}

	final public TextFileWriter open() {
		try {
			close();
			out = new BufferedWriter(new FileWriter(path));
		} catch (Exception e) {
			throw new RuntimeException("Can not open file [path=" + path + "]", e);
		}
		return this;
	}

	final public void close() {
		if (out != null)
			try {
				out.flush();
				out.close();
			} catch (Exception e) {
				throw new RuntimeException("File closed abnormally [path=" + path + "]", e);
			} finally {
				this.out = null;
			}
	}

	final public TextFileWriter write(String row) {
		try {
			out.write(row);
		} catch (Exception e) {
			throw new RuntimeException("Can not write row to file [path=" + path + "]", e);
		}
		return this;
	}

	final public TextFileWriter writeln(String row) {
		try {
			out.write(row);
			out.write("\n");
		} catch (Exception e) {
			throw new RuntimeException("Can not write row to file [path=" + path + "]", e);
		}
		return this;
	}

	final public TextFileWriter writeln() {
		try {
			out.write("\n");
		} catch (Exception e) {
			throw new RuntimeException("Can not write row to file [path=" + path + "]", e);
		}
		return this;
	}

}
