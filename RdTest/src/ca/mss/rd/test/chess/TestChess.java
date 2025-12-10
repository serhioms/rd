package ca.mss.rd.test.chess;

import org.junit.Before;
import org.junit.Test;

import ca.mss.rd.test.workflow.interceptors.JUnitInterceptor;
import ca.mss.rd.test.workflow.interceptors.JUnitWorkflow;
import ca.mss.rd.test.workflow.repo.TraderMOM;
import ca.mss.rd.workflow.proc.WkfProcessor;
import ca.mss.rd.workflow.proc.inthread.WkfProcessorImpl;
import ca.mss.rd.workflow.reader.xpdl.WkfReaderXPDL;

public class TestChess {

	private WkfProcessor processor;
	private WkfReaderXPDL xpdlReader; 
	
	@Before
	public void setUp() throws Exception {
		processor = new WkfProcessorImpl();
		processor.setWkfInterceptor(new JUnitInterceptor());
		xpdlReader = new WkfReaderXPDL();
	}

	@Test
	public void testTraderMOM(){
		xpdlReader.parseWorkflow("repository/triplechess1.xpdl");
		processor.startWorkflow(new JUnitWorkflow(new TraderMOM()));
	}
	
}
