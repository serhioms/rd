package ca.mss.rd.test.workflow;

import org.junit.Before;
import org.junit.Test;

import ca.mss.rd.test.workflow.interceptors.JUnitInterceptor;
import ca.mss.rd.test.workflow.interceptors.JUnitWorkflow;
import ca.mss.rd.test.workflow.repo.TraderMOM;
import ca.mss.rd.workflow.proc.WkfProcessor;
import ca.mss.rd.workflow.proc.inthread.WkfProcessorImpl;

public class TestTraderWorkflow {

	private WkfProcessor processor;
	
	@Before
	public void setUp() throws Exception {
		processor = new WkfProcessorImpl();
		processor.setWkfInterceptor(new JUnitInterceptor());
	}

	@Test
	public void testTraderMOM(){
		processor.startWorkflow(new JUnitWorkflow(new TraderMOM()));
	}
	
}
