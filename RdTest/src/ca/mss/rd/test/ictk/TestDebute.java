package ca.mss.rd.test.ictk;

import java.io.File;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.apache.commons.io.FileUtils;


import ca.mss.rd.chessictk.debutetree.DebuteTree;
 import org.apache.log4j.Logger;

public class TestDebute extends TestCase {

	final public static String module = TestDebute.class.getName();
	static final long serialVersionUID = module.hashCode();
	private final static org.apache.log4j.Logger logger = ca.mss.rd.util.Debug.getLogger(module);

	long start;
	int total;
	long finish;
	long time;
	double rate;
	long exceptions;

	DebuteTree dtree;
	
	static String readDebute = "";
	static String loadGames = "";
	
	
	/**
	 * 
	 */
	public TestDebute() {
		super();
	}

	/**
	 * @param name
	 */
	public TestDebute(String name) {
		super(name);
	}

	public static Test suite() {

		TestSuite suite = new TestSuite("Debute test");

		suite.addTest(new TestDebute("loadGames"));
		suite.addTest(new TestDebute("readDebute"));
		suite.addTest(new TestDebute("compare"));
		
		return suite;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp();

		dtree = new DebuteTree();
		dtree.setDebutePath("../ChessEngine/pgn/debute20.txt");
		dtree.setPgnPath("../ChessEngine/pgn/maestro.pgn");
		
		// **********************************************************
		start = System.currentTimeMillis();
		total = 0;
		exceptions = 0;

		System.out.println("*** Start [Name=" + getName() + "]");
	}

	public void tearDown() {
		finish = System.currentTimeMillis();
		time = (finish - start);
		rate = (time * 1.0) / (total * 1.0);

		System.out.println("*** Finish[Name=" + getName() + "][total=" + total
				+ "][time=" + time + "][rate=" + rate + "][exceptions="
				+ exceptions + "]");
	}

	public void readDebute() {
		dtree.loadTree();
		total = dtree.MAX_GAMES;
		
		readDebute = dtree.getDebuteTree().toString();
		assertTrue("Debute tree successfully deserialized.", true);
	}


	public void loadGames() {
		dtree.loadPGN();
		total = dtree.MAX_GAMES;

		loadGames = dtree.getDebuteTree().toString();
		
		try {
			FileUtils.writeStringToFile(new File(dtree.getDebutePath()), loadGames);
			assertTrue("Games tree successfully loaded.", true);
		}catch(Exception e){
			assertTrue(e.getMessage(), false);
		}
	}

	public void compare() {
		assertEquals(true, loadGames.equals(readDebute));
	}

}
