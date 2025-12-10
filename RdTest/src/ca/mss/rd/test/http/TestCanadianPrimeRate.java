package ca.mss.rd.test.http;

import org.apache.log4j.Logger;


import ca.mss.rd.util.Timing;
import ca.mss.rd.util.UtilHttp;
import ca.mss.rd.util.UtilMisc;
import ca.mss.rd.util.io.UtilIO;

public class TestCanadianPrimeRate {

	final static public String module = TestCanadianPrimeRate.class.getName();
	final static public long serialVersionUID = module.hashCode();
	final private static org.apache.log4j.Logger logger = ca.mss.rd.util.Debug.getLogger(module);

	final static public String TARGET_URL = "http://www.ratehub.ca/charts/f-3-p.csv";
	final static public String FILE_PATH = "../results/http/canadian_prime.txt";

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		
		System.out.println("Start...");
		
		Timing timing = new Timing();

		UtilHttp.write( TARGET_URL, 
				UtilMisc.toMap(
						"db", "pubmed",
						"term", "breast+cancer+risk+associated+with+congeners+of+polychlorinated+biphenyls"
				), 
				UtilHttp.RequestType.POST,
				UtilIO.getOutputStreamFile(FILE_PATH));

		logger.debug("Done [buffer="+Timing.formatBytes(UtilHttp.HTTP_BUFFER_KB)+"][time="+timing.total()+"]");
	}
	
}


