package ca.mss.rd.test.http;

import org.apache.log4j.Logger;


import ca.mss.rd.util.Timing;
import ca.mss.rd.util.UtilHttp;
import ca.mss.rd.util.UtilMisc;
import ca.mss.rd.util.io.UtilIO;

public class TestTellusAirtime {

	final static public String module = TestTellusAirtime.class.getName();
	final static public long serialVersionUID = module.hashCode();
	final private static org.apache.log4j.Logger logger = ca.mss.rd.util.Debug.getLogger(module);

	final static public String LOGIN_URL = "https://www.telusmobility.com:443/sso/UI/Login?realm=telus&service=telusmobility";
	final static public String AIRTIME_URL = "https://www.telusmobility.com/sso/UI/Login?realm=telus&amp;service=telusmobility&amp;goto=https&#37;3A&#37;2F&#37;2Fsecure.telusmobility.com&#37;3A443&#37;2Fselfserveam&#37;2FAirtimeSummary.do";

	final static public String FILE_PATH = "C:/mss/cibc/workspace/RdTrade/data/tellus/tellus=2013-03-21.txt";

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		logger.debug("Start...");
		
		Timing timing = new Timing();

		UtilHttp.write(AIRTIME_URL 
				,UtilMisc.toMap(
						"IDToken1", "sergey.moskovskiy@gmail.com", 
						"IDToken2", "Hunter2012",
						"check1", "1"
				) 
				,UtilHttp.RequestType.POST
				,UtilIO.getOutputStreamFile(FILE_PATH)
		);

		logger.debug("Done [buffer="+Timing.formatBytes(UtilHttp.HTTP_BUFFER_KB)+"][time="+timing.total()+"]");
	}

	
}


