package ca.mss.rd.test.http;

import org.apache.log4j.Logger;


import ca.mss.rd.util.Timing;
import ca.mss.rd.util.UtilHttp;
import ca.mss.rd.util.UtilMisc;
import ca.mss.rd.util.io.UtilIO;

public class TestEnergyApp {

	final static public String module = TestEnergyApp.class.getSimpleName();
	final static public long serialVersionUID = module.hashCode();
	final private static org.apache.log4j.Logger logger = ca.mss.rd.util.Debug.getLogger(module);

	final static public String FILE_PATH = "results/http/energyapp=";

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		logger.debug("Start...");
		
		Timing timing = new Timing();

		String token = "eyJhbGciOiJSUzI1NiJ9.eyJqdGkiOiIxMS5jNzQ2MmQ4Yi1jYWEzLTRmMjgtYWE2Ny0wY2Y3NmExZmUzYjgiLCJhdWQiOiJjb21tZXJjZSIsInBybiI6ImJyZXR0QGRhdGFjdXN0b2RpYW4uY29tIiwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbInVzZXIiXX19.Qz_SRDCRZgLKM15gmzUBi0fKDZJBL4T590Vz2UEXWcJFDytGgSNwikizs2UI4nJqKApPNzLvDCV6ixkbQ3AlEYQ9RnLUpHYzs8yEQrk3btLGYl4Yz1z8QcR53tRfO568FiSTbGyHKrPZ18Rw8qSGFKjBl0slILllN7FB26_nKVk";
		String baseUrl = "https://greenbutton.affsys.com/";
		String start = "1372651200";
		String duration = "2764800";

		UtilHttp.write(baseUrl + "ldc/api/v1/UsagePoint" 
				,UtilMisc.toMap(
						"start", start,
						"duration", duration
				) 
				,UtilMisc.toMap(
						"Authorization", "Bearer " + token
				) 
				,UtilHttp.RequestType.GET
				,UtilIO.getOutputStreamFile(FILE_PATH)
				);

		logger.debug("Done [buffer="+Timing.formatBytes(UtilHttp.HTTP_BUFFER_KB)+"][time="+timing.total()+"]");
	}

	
}


