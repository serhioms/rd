package ca.mss.rd.test.workflow;

import junit.framework.TestCase;
import ca.mss.rd.test.workflow.interceptors.JUnitInterceptor;
import ca.mss.rd.test.workflow.interceptors.JUnitWorkflow;
import ca.mss.rd.test.workflow.repo.BatchLoader;
import ca.mss.rd.test.workflow.repo.HelloWorld;
import ca.mss.rd.test.workflow.repo.SimpleCycle;
import ca.mss.rd.test.workflow.repo.SplitAnd;
import ca.mss.rd.test.workflow.repo.SplitJoinAnd;
import ca.mss.rd.test.workflow.repo.SplitJoinAnd2;
import ca.mss.rd.test.workflow.repo.SplitXor;
import ca.mss.rd.workflow.proc.WkfProcessor;
import ca.mss.rd.workflow.proc.inthread.WkfProcessorImpl;

public class TestWorkflows extends TestCase {

	private WkfProcessor processor;
	
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		processor = new WkfProcessorImpl();
		processor.setWkfInterceptor(new JUnitInterceptor());
	}

	public void testHelloWorld(){
		processor.startWorkflow(new JUnitWorkflow(new HelloWorld()));
	}
	
	public void testBatchLoader(){
		processor.startWorkflow(new JUnitWorkflow(new BatchLoader()));
	}
	
	public void testSplitXor(){
		processor.startWorkflow(new JUnitWorkflow(new SplitXor()));
	}
	
	public void testSplitAnd(){
		processor.startWorkflow(new JUnitWorkflow(new SplitAnd()));
	}
	
	public void testSplitJoinAnd(){
		processor.startWorkflow(new JUnitWorkflow(new SplitJoinAnd()));
	}

	public void testSplitJoinAnd2(){
		processor.startWorkflow(new JUnitWorkflow(new SplitJoinAnd2()));
	}
	
	public void testSimpleCycle(){
		processor.startWorkflow(new JUnitWorkflow(new SimpleCycle()));
	}
	
}
