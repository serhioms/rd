package ca.mss.rd.test.mom;

import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ca.mss.rd.tools.mom.RdRealtime;
import ca.mss.rd.util.UtilMisc;

public class TestMOM {
	
	private String[] sources = new String[]{"eurusd"/*, "nzdyen"*/};
	private List<RdRealtime> mom;
	
	@Before
	public void setUp() throws Exception {
		mom = new LinkedList<RdRealtime>();
		
		for(int i=0; i<sources.length; i++ ){
			mom.add(new RdRealtime(sources[i], RdRealtime.toMap(
					"eurusd", UtilMisc.toList("slicer1","slicer2","slicer3","pnl"),
					"pnl", UtilMisc.toList("robot","graph")
			)));
		}
	}

	@Test
	public void test(){
		
		for(RdRealtime rt: mom){
			rt.startThread();
		}
		
		for(int counter = mom.size(); counter > 0; ){
		
			counter = mom.size();
			
			for(RdRealtime rt: mom){
				try { Thread.sleep(100L); } catch (InterruptedException e) {}
				if( rt.isDone() )
					counter--;
			}
		}
	}

}
