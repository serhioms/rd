package ca.mss.rd.test.starter;

import org.apache.log4j.Logger;




/**
 * @author moskovsk
 *
 */
public class TestStandaloneStarter {
	
	final static public String module = TestStandaloneStarter.class.getName();
	final static public long serialVersionUID = module.hashCode();
	final private static org.apache.log4j.Logger logger = ca.mss.rd.util.Debug.getLogger(module);

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if( logger.isDebugEnabled() ) logger.debug("Test Standalone Starter - Successfully Done!");
	}

}


