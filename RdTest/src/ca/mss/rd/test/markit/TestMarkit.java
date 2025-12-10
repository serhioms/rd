package ca.mss.rd.test.markit;

import static org.junit.Assert.assertTrue;

import java.io.File;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ca.mss.rd.trade.markit.RdUtilMarkIt;

import ca.mss.rd.util.Timing;
import ca.mss.rd.util.io.UtilFile;

public class TestMarkit {

	final static public String module = TestMarkit.class.getName();
	final static public long serialVersionUID = module.hashCode();
	final private static org.apache.log4j.Logger logger = ca.mss.rd.util.Debug.getLogger(module);

	final private static String DOWNLOAD_FOLDER = "results/http/";
	final private static String START_DATE = "20130711";
	
	
	//@Test
	public void LTraxComOp() {
		test(RdUtilMarkIt.LTraxComOp(START_DATE, DOWNLOAD_FOLDER));
	}

	@Test
	public void ABXIndexComposite() {
		test(RdUtilMarkIt.ABXIndexComposite(START_DATE, DOWNLOAD_FOLDER));
	}

	//@Test
	public void LoadCDSLiquidityMetrics() {
		test(RdUtilMarkIt.CDSLiquidityMetrics(START_DATE, DOWNLOAD_FOLDER));
	}

	//@Test
	public void AllContributions() {
		test(RdUtilMarkIt.AllContributions(START_DATE, DOWNLOAD_FOLDER));
	}


	/*
	 * Implementation
	 * 
	 */
	
	private void test(String filePath) {
				
		long size = UtilFile.fileSize(new File(filePath));
		
		assertTrue("[downloaded="+Timing.formatBytes(size)+"][time="+timing.total()+"]", size > 100);
	}

	@Before
	public void setUp() throws Exception {
		timing = new Timing();
	}

	@After
	public void tearDown() throws Exception {
	}

	private Timing timing;

}
