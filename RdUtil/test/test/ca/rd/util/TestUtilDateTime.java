package test.ca.rd.util;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ca.mss.rd.util.Logger;
import ca.mss.rd.util.UtilDateTime;

public class TestUtilDateTime {

	long[] timeLong = new long[]{777, 777*35, 777*350, 777*350*35, 777*350*35*12, 777*350*35*12*17};
	String[] timeString = new String[]{"777 mls","27.2 sec","4.5 min","2.6 h","1.3 d","22.5 d"};
	
	@Before
	public void setUp() throws Exception {
		Logger.TEST.isOn = true;
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void timeFormat(){
		boolean success = true;

		
		for(int i=0; i<timeLong.length; i++){
			long time = timeLong[i];
			
			String label = UtilDateTime.formatTime(time);
			
			success &= label.equals(timeString[i]); 
					
			Logger.TEST.printf("%d = %s", time, label);
		}
		
		assertTrue(success);
	}

}
