package ca.mss.rd.test.http;

import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import org.apache.log4j.Logger;


import ca.mss.rd.util.UtilDateTime;
import ca.mss.rd.util.UtilHttp;
import ca.mss.rd.util.UtilMisc;
import ca.mss.rd.util.UtilProperty;

public class TestBidCactus  extends Thread {

	final static public String module = TestMarkit.class.getName();
	final static public long serialVersionUID = module.hashCode();
	final private static org.apache.log4j.Logger logger = ca.mss.rd.util.Debug.getLogger(module);

	final static public String DATA_FILE_NAME = "BidCactus.properties";

	final static public int MINUTE_NUMBER = 1;
	final static public int THREADS_NUMBER = 1;
	final static public int REQUESTS_NUMBER = 1;
	final static public int REQUESTS_TIMEOUT = 1000*60*5;

	final static public int UPDATE_URL_INDEX = 0;
	final static public String[] UPDATE_URL = new String[]{
		"http://sundial.bidcactus.com/auctions/updates?xss_token=cfqzjothefi8&x=1351706146&y=1350414461&_=1351712336593"
	};
	
	final static public String FILE_PATH = "C:/mss/cibc/workspace/RdTest/results/http/bidcactus.xml";

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		for(int t=0; t<THREADS_NUMBER; t++){
			TestBidCactus bid = new TestBidCactus();
			bid.run();
		}
	}

	TestBidCactus() {
		super();
		setDaemon(false);
	}

	static private int counter = 1;
	static private int failure = 0;
	static private int last = 0;
	
	public void run() {
		int threadNumber = counter++;
		while( true ){
			
			Date now = UtilDateTime.now();
			int min = UtilDateTime.getMinute(now);
			int sec = UtilDateTime.getSecond(now);
			
			if( min % MINUTE_NUMBER == 0 && min != last) {
				Set<String> users = getUserSet();
				if( users != null ){
					String key = UtilDateTime.format(now, "HHmm");
					logger.debug(users.size()+"\t"+key);
					Properties data = new Properties();
					UtilProperty.readProperties(DATA_FILE_NAME, data);
					String value = data.getProperty(key);
					value = (value!=null? value+",":"")+users.size();
					data.setProperty(key, value);
					UtilProperty.writeProperties(DATA_FILE_NAME, data);
				} else {
					failure++;
				}
				
				last = min;
			}
			
			try {
				Thread.currentThread().sleep((60-sec)*1000);
			} catch (InterruptedException ie) {
			}
		}
	}
	
	
	public static Set<String> getUserSet(){
		Set<String> users = new HashSet<String>();
		
		try {
	
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			
			UtilHttp.write( UPDATE_URL[UPDATE_URL_INDEX], 
					UtilMisc.<String>toMap(), 
					UtilHttp.RequestType.GET,
					os);
			
			String r = os.toString();
			String[] ra = r.split("\"w\":\"");
			
			for(int i=1; i<ra.length; i++){
				String user = ra[i].split("\"")[0];
				users.add(user);
			}
			
			return users;
		}catch(RuntimeException e){
			failure++;
		}
		
		return null;
	}
}


