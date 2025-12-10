package ca.mss.rd.util.test;

import ca.mss.rd.util.Logger;

public class TestLogger {

	static public void main(String[] args){
		
		Logger.all();
		
		Logger.VERBOSE.printf("VERBOSE: "+Logger.VERBOSE.isOn );
		Logger.DEBUG.printf("DEBUG: "+Logger.DEBUG.isOn );
		Logger.INFO.printf("INFO: "+Logger.INFO.isOn );
		Logger.WARNING.printf("WARN: "+Logger.WARNING.isOn );
		if( Logger.ERROR.isOn ) Logger.ERROR.printf("ERROR: "+Logger.ERROR.isOn );
		
		if( Logger.ERROR.isOn ) Logger.ERROR.printf(new RuntimeException("Terrible Disaster..."));
	}
}
