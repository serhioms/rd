package ca.mss.rd.util.io;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import ca.mss.rd.util.UtilMisc;

public class InputReader {

	private String row;
	protected BufferedReader br;
	protected DataInputStream in;

	
	public InputReader() {
		this.in = null;
		this.br = null;
		this.row = null;
	}

	public InputReader open(){
		throw new RuntimeException("Open method must be overriden");
	}
	
	final public InputReader open(InputStream fstream) {
		close();
		in = new DataInputStream(fstream);
		br = new BufferedReader(new InputStreamReader(in));
		return this;
	}
	
	final public void close() {
		if (in != null)
			try {
				in.close();
			} catch (Exception e) {
				throw new RuntimeException("Text input stream closed abnormally", e);
			} finally {
				this.in = null;
				this.br = null;
				this.row = null;
			}
	}

	final public String readRow() {
		try {
			for (row = br.readLine(); row != null; row = br.readLine()) {
				if (!UtilMisc.isEmpty(row))
					break;
			}
			return row;
		} catch (Exception e) {
			return row=null;
		} finally {
			if (row == null)
				close();
		}
	}

}