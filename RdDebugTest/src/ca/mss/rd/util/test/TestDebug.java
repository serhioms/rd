package ca.mss.rd.util.test;



public class TestDebug {

	static public final org.apache.log4j.Logger logger = ca.mss.rd.util.Debug.getLogger(TestDebug.class);
	
	static public void main(String[] args){
		
		logger.debug("DEBUG: "+logger.isDebugEnabled());
		logger.info("INFO: "+logger.isInfoEnabled());
		logger.warn("WARN");
		logger.error("ERROR");
		
		logger.error("ERROR", new RuntimeException("Terrible Disaster..."));
	}
}
