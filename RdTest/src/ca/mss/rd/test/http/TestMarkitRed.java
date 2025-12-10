package ca.mss.rd.test.http;

import org.apache.log4j.Logger;


import ca.mss.rd.util.Timing;
import ca.mss.rd.util.UtilHttp;
import ca.mss.rd.util.UtilMisc;
import ca.mss.rd.util.io.UtilIO;

public class TestMarkitRed {

	final static public String module = TestMarkit.class.getName();
	final static public long serialVersionUID = module.hashCode();
	final private static org.apache.log4j.Logger logger = ca.mss.rd.util.Debug.getLogger(module);

	final static public String MARKIT_SITE_URL = "https://www.markit.com/export.jsp";
	final static private String CIBC_USERNAME = "GDSRCALYPSO";
	final static private String CIBC_PASSWORD = "CIBC1234";

	final static public String FORMAT_XML = "xml";
	final static public String FORMAT_CSV = "csv";
	final static public String FORMAT_TAB = "csv";


	final static public String FILE_PATH = "C:/mss/cibc/workspace/RdTrade/data/red/redindex=2013-03-15.txt";

	final static public int ZIP_ITEM_NUMBER = 2; // read file #2 from downloaded archive

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		logger.debug("Start...");
		
		Timing timing = new Timing();

		// https://products.markit.com/warehouse/export.jsp?doNotGzip=true&date=21-03-2013&format=xml&report=credindexannex&a=1363897061586&version=9&family=CDX
			
		UtilHttp.write(MARKIT_SITE_URL 
				,UtilMisc.toMap(
						"user", CIBC_USERNAME, 
						"password", CIBC_PASSWORD,
						"format", FORMAT_XML,
						"date", "20130320",
						"report", "credindexannex",
						"family", "CDX",
						"version", "9"
				) 
				,UtilHttp.RequestType.POST
				,UtilIO.getOutputStreamFile(FILE_PATH)
				,UtilHttp.RequestInputType.ZIP
				,ZIP_ITEM_NUMBER
				);

		logger.debug("Done [buffer="+Timing.formatBytes(UtilHttp.HTTP_BUFFER_KB)+"][time="+timing.total()+"]");
	}

	
}


