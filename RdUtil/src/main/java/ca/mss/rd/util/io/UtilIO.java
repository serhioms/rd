package ca.mss.rd.util.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.zip.ZipInputStream;

public class UtilIO {
	final static public String module = UtilIO.class.getName();
	final static public long serialVersionUID = module.hashCode();

	static public int BUFFER_SIZE = 1024 * 22; // 22K

	final static public OutputStream getOutputStreamString() {
		return new ByteArrayOutputStream();
	}

	final static public OutputStream getOutputStreamFile(String filePath) {
		try {
			File file = new File(filePath.toString());
			return new BufferedOutputStream(new FileOutputStream(file));
		} catch (Throwable t1) {
			try {
				File file = new File(filePath.toString());
				UtilFile.createParentFolders(file);
				return new BufferedOutputStream(new FileOutputStream(file));
			} catch (Throwable t2) {
				throw new RuntimeException("Can not get output stream to file ["+filePath+"]", t2);
			}
		}
	}

	final static public void write(InputStream is, OutputStream os) {

		try {
			byte[] buf = new byte[BUFFER_SIZE];
			for (int count = is.read(buf, 0, buf.length); count > 0; count = is.read(buf, 0, buf.length)) {
				os.write(buf, 0, count);
			}
		} catch (Throwable t) {
			throw new RuntimeException("Failed write to output stream from input stream", t);
		} finally {
			try {
				os.flush();
				os.close();
			} catch (Throwable t) {
			} finally {
			}
		}
	}

	final static public void write(String str, OutputStream os) {
		OutputStreamWriter osw = null;

		try {
			osw = new OutputStreamWriter(os);
			osw.write(str);
		} catch (Throwable t) {
			throw new RuntimeException("Failed write to output stream from input stream", t);
		} finally {
			try {
				osw.flush();
				osw.close();
			} catch (Throwable t) {
			} finally {
			}
		}
	}

	final static public void writeZip(InputStream is, OutputStream os, int entries) {

		try {
			ZipInputStream zis = new ZipInputStream(new BufferedInputStream(is));
			
			// required before 1st read or/and 1st file is T&Cs - ingnore
			while( entries-- > 0 ) zis.getNextEntry(); 

			write(zis, os);
		} catch (Throwable t) {
			throw new RuntimeException("Failed write to output stream from input zip stream", t);
		} finally {
			try {
				os.flush();
				os.close();
			} catch (Throwable t) {
			} finally {
			}
		}
	}
}
