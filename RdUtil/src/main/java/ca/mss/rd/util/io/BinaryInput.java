package ca.mss.rd.util.io;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;

public class BinaryInput {

	static public int BINARY_BUFFER_SIZE = 1024*1000; // 1 Mb
	
	private byte[] buffer = new byte[BINARY_BUFFER_SIZE];
	
	protected BufferedInputStream bis;

	
	public BinaryInput() {
		this.bis = null;
	}

	public BinaryInput open(){
		throw new RuntimeException("Open method must be overriden");
	}
	
	final public BinaryInput open(InputStream is) {
		close();
		bis = new BufferedInputStream(is);
		return this;
	}
	
	final public void close() {
		if (bis != null)
			try {
				bis.close();
			} catch (Exception e) {
				throw new RuntimeException("Binary input stream closed abnormally", e);
			} finally {
				this.bis = null;
			}
	}

	final public byte[] readRow() {
		int reads = 0;
		try {
			reads = bis.read(buffer);
			if( reads == 0 )
				return null;
			else if( reads < BINARY_BUFFER_SIZE ){
				return ByteBuffer.wrap(buffer, 0, reads).slice().array(); 	// 1.4s
				//return Arrays.copyOfRange(buffer, 0, reads);				// 2.1 s
			} else {
				return buffer;
			}
		} catch (Exception e) {
			return null;
		} finally {
			if (reads == 0)
				close();
		}
	}

}