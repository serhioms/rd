package ca.mss.rd.home.test;

import java.nio.charset.Charset;
import java.security.MessageDigest;

import ca.mss.rd.util.Logger;
import ca.mss.rd.util.UtilDateTime;
import ca.mss.rd.util.UtilProperty;
import ca.mss.rd.util.io.BinaryFileReader;
import ca.mss.rd.util.io.UtilFile;
import ca.mss.rd.util.model.CommandLine;
import ca.mss.rd.util.model.Argument;
import ca.mss.rd.util.model.StandardAlgorithm;
import ca.mss.rd.util.model.StandardCharset;

public class TestBinary {
	
	public enum TestBinaryArgs implements CommandLine {
		 command("c", "copy|verify", "command name", CommandLine.MANDATORY, null)
		,from("f", "src", "path to source folder/file", CommandLine.MANDATORY, null)
		,to("t", "dest", "path to destination folder", CommandLine.MANDATORY, null)
		,algorithm("a", "MD2|MD5|SHA-1|SHA-256|SHA-384|SHA-512", "digest algorithm", CommandLine.OPTIONAL, "SHA-512")
		,encoding("e", "UTF_8|UTF_16LE|UTF_16BE|UTF_16|US_ASCII|ISO_8859_1", "digest encoding", CommandLine.OPTIONAL, "UTF_8")
		,digestdir("d", "dir", "digest sub-directory",CommandLine.OPTIONAL, "./digest")
		,help("h", "help", "Copy source file to destination with content verification", CommandLine.HELP, null)
		;
		
		public final Argument arg;
		
		private TestBinaryArgs(String ident, String domain, String help, boolean isMandatory, String def) {
			arg = new Argument(name(), ident, domain, help, isMandatory, def);
		}

		@Override
		final public Argument getParameter() {
			return arg;
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		UtilProperty.parse(args, TestBinaryArgs.values());
		
		String algorithm = StandardAlgorithm.algorithm(TestBinaryArgs.algorithm.arg.value);
		Charset charset = StandardCharset.charset(TestBinaryArgs.encoding.arg.value);

		Logger.TEST.printf("Do [%s] from [%s] to [%s] with [%s/%s]", TestBinaryArgs.command.arg.value, TestBinaryArgs.from.arg.value, TestBinaryArgs.to.arg.value, algorithm, charset);
		
		BinaryFileReader file = new BinaryFileReader(TestBinaryArgs.from.arg.value);

		MessageDigest md = null;
		int chanks = 0; 
		
		long start = UtilDateTime.startWatch();

		try {
			md = MessageDigest.getInstance(algorithm);
			
			file.open();
			
			for(byte[] row = file.readRow(); row != null; row = file.readRow() ){
				// Process bytes
				chanks++;
				//md.update(row);
			}
			
		} catch(Throwable t){
			if( Logger.ERROR.isOn ) Logger.ERROR.printf(t);
		} finally {
			file.close();
		}
		
		Logger.TEST.printf("Done %s/%d in %s: %s/%s {%s}", UtilFile.formatSize(UtilFile.fileSize(TestBinaryArgs.from.arg.value)), chanks, UtilDateTime.stopWatch(start), 
			algorithm, TestBinaryArgs.encoding.arg.value, new String(md.digest(), charset));
		
		Logger.TEST.printf("Done %s/%d in %s: %s/%s {%s}", UtilFile.formatSize(UtilFile.fileSize(TestBinaryArgs.from.arg.value)), chanks, UtilDateTime.stopWatch(start), 
			algorithm, "UTF_16", new String(md.digest(), StandardCharset.charset(("UTF_16"))));
			
		Logger.TEST.printf("Done %s/%d in %s: %s/%s {%s}", UtilFile.formatSize(UtilFile.fileSize(TestBinaryArgs.from.arg.value)), chanks, UtilDateTime.stopWatch(start), 
			algorithm, "US_ASCII", new String(md.digest(), StandardCharset.charset(("US_ASCII"))));
			
		Logger.TEST.printf("Done %s/%d in %s: %s/%s {%s}", UtilFile.formatSize(UtilFile.fileSize(TestBinaryArgs.from.arg.value)), chanks, UtilDateTime.stopWatch(start), 
			algorithm, "ISO_8859_1", new String(md.digest(), StandardCharset.charset(("ISO_8859_1"))));

		Logger.TEST.printf("Done");
	}
	
}
