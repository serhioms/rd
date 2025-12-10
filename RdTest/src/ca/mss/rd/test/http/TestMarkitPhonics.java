package ca.mss.rd.test.http;

import org.apache.log4j.Logger;


import ca.mss.rd.util.Timing;
import ca.mss.rd.util.UtilHttp;
import ca.mss.rd.util.UtilMisc;
import ca.mss.rd.util.io.UtilIO;

public class TestMarkitPhonics {

	final static public String module = TestMarkit.class.getName();
	final static public long serialVersionUID = module.hashCode();
	final private static org.apache.log4j.Logger logger = ca.mss.rd.util.Debug.getLogger(module);

	final static public String MARKIT_SITE_URL = "https://www.markit.com/export.jsp";
	final static private String CIBC_USERNAME = "GDSRCALYPSO";
	final static private String CIBC_PASSWORD = "CIBC1234";

	final static public String FORMAT_XML = "xml";
	final static public String FORMAT_CSV = "csv";
	final static public String FORMAT = FORMAT_XML;

	final static public String REPORT = "AllContributions";
	final static public String TYPE_DEFAULT_SWAP = "cds";
	final static public String FAMILY_CDX = "CDX";

	final static public String VERSION = "2";

	final static public String DATE = "20130514";
	final static public String FILE_PATH = "results/http/markit_site=phonics="+VERSION+"="+REPORT+"="+DATE+"."+FORMAT;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		logger.debug("Start...");
		
		Timing timing = new Timing();

		UtilHttp.write(MARKIT_SITE_URL 
				,UtilMisc.toMap(
						"user", CIBC_USERNAME, 
						"password", CIBC_PASSWORD,
						"date", DATE,
						"format", FORMAT,
						"report", REPORT,
						"type", TYPE_DEFAULT_SWAP,
						"version", VERSION
				) 
				,UtilHttp.RequestType.POST
				,UtilIO.getOutputStreamFile(FILE_PATH)
				,UtilHttp.RequestInputType.TEXT
				);

		logger.debug("Done [buffer="+Timing.formatBytes(UtilHttp.HTTP_BUFFER_KB)+"][time="+timing.total()+"]");
	}

	
}


