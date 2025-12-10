package ca.mss.rd.test.math.stat;

import static org.junit.Assert.assertTrue;

import java.text.DecimalFormat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ca.mss.rd.stat.Change;
import ca.mss.rd.util.Logger;


public class MathBreakLineTest {

	static DecimalFormat nice = new DecimalFormat("00.0");

	double[][] testData;
	
	String[] testCasesChangeIndic, testCasesBreakLine, testCasesBreakLineMM;
	
	@Before
	public void setUp() throws Exception {
		
		Logger.TEST.isOn = true;
		
		testData = new double[][]{{}
				,{1,1,1,	3,	4,	5,	6,	10.0,	9.0,	8.0,	7,	6,	5,	6,	5}
				,{1,1,1,	3,	4,	5,	6,	17.0,	9.0,	8.0,	7,	6,	5,	6,	5}
				,{1,1,1,	3,	4,	5,	6,	6.4,	5.5,	3.4,	7,	6,	5,	6,	5}
				,{1,1,1,	3,	4,	5,	6,	6.4,	4.8,	3.4,	7,	6,	5,	6,	5}
				,{7, 7.5, 8, 8.5, 9, 9.5, 10, 10.3, 10, 9.5, 9, 8.5, 8, 7.5, 7, 6.5, 6, 5.5, 5, 4.7, 5, 5.5, 6, 6.5, 7, 7.5, 7, 6.5, 6, 5.5, 5, 4.5, 4, 3.5, 3, 2.5, 2, 1.5, 1}
		};
		
		testCasesChangeIndic = new String[]{ "" 
				,"Change indicator: 1.1) 1.0= true 1.2) 1.0=false 1.3) 1.0=false 1.4) 3.0= true 1.5) 4.0= true 1.6) 5.0= true 1.7) 6.0= true 1.8) 10.0= true 1.9) 9.0= true 1.10) 8.0= true 1.11) 7.0= true 1.12) 6.0= true 1.13) 5.0= true 1.14) 6.0= true 1.15) 5.0= true "
		};
		
		testCasesBreakLine = new String[]{ "" 
				,"Break Line indicator: 1.1) 1.0=false (1.0) 1.2) 1.0=false (1.0) 1.3) 1.0=false (1.0) 1.4) 3.0= true (3.0) 1.5) 4.0=false (3.0) 1.6) 5.0= true (5.0) 1.7) 6.0=false (5.0) 1.8) 10.0= true (10.0) 1.9) 9.0=false (10.0) 1.10) 8.0= true (8.0) 1.11) 7.0=false (8.0) 1.12) 6.0= true (6.0) 1.13) 5.0=false (6.0) 1.14) 6.0=false (6.0) 1.15) 5.0=false (6.0) "
				,"Break Line indicator: 2.1) 1.0=false (1.0) 2.2) 1.0=false (1.0) 2.3) 1.0=false (1.0) 2.4) 3.0= true (3.0) 2.5) 4.0=false (3.0) 2.6) 5.0= true (5.0) 2.7) 6.0=false (5.0) 2.8) 17.0= true (17.0) 2.9) 9.0= true (9.0) 2.10) 8.0=false (9.0) 2.11) 7.0= true (7.0) 2.12) 6.0=false (7.0) 2.13) 5.0= true (5.0) 2.14) 6.0=false (5.0) 2.15) 5.0=false (5.0) "
				,"Break Line indicator: 3.1) 1.0=false (1.0) 3.2) 1.0=false (1.0) 3.3) 1.0=false (1.0) 3.4) 3.0= true (3.0) 3.5) 4.0=false (3.0) 3.6) 5.0= true (5.0) 3.7) 6.0=false (5.0) 3.8) 6.4=false (5.0) 3.9) 5.5=false (5.0) 3.10) 3.4= true (3.4) 3.11) 7.0= true (7.0) 3.12) 6.0=false (7.0) 3.13) 5.0= true (5.0) 3.14) 6.0=false (5.0) 3.15) 5.0=false (5.0) "
				,"Break Line indicator: 4.1) 1.0=false (1.0) 4.2) 1.0=false (1.0) 4.3) 1.0=false (1.0) 4.4) 3.0= true (3.0) 4.5) 4.0=false (3.0) 4.6) 5.0= true (5.0) 4.7) 6.0=false (5.0) 4.8) 6.4=false (5.0) 4.9) 4.8=false (5.0) 4.10) 3.4= true (3.4) 4.11) 7.0= true (7.0) 4.12) 6.0=false (7.0) 4.13) 5.0= true (5.0) 4.14) 6.0=false (5.0) 4.15) 5.0=false (5.0) "
		};

		testCasesBreakLineMM = new String[]{ "" 
			,"Break Line MM indicator: 1.1) 01.0=false {01.0,false(    ),false(    )} 1.2) 01.0=false {01.0,false(    ),false(    )} 1.3) 01.0=false {01.0,false(    ),false(    )} 1.4) 03.0= true {03.0,false(    ),false(    )} 1.5) 04.0=false {03.0,false(    ),false(    )} 1.6) 05.0= true {05.0,false(    ),false(    )} 1.7) 06.0=false {05.0,false(    ),false(    )} 1.8) 10.0= true {10.0,false(    ),false(    )} 1.9) 09.0=false {10.0,false(    ),false(    )} 1.10) 08.0= true {08.0, true(10.0),false(    )} 1.11) 07.0=false {08.0,false(    ),false(    )} 1.12) 06.0= true {06.0,false(    ),false(    )} 1.13) 05.0=false {06.0,false(    ),false(    )} 1.14) 06.0=false {06.0,false(    ),false(    )} 1.15) 05.0=false {06.0,false(    ),false(    )} "
			,"Break Line MM indicator: 2.1) 01.0=false {01.0,false(    ),false(    )} 2.2) 01.0=false {01.0,false(    ),false(    )} 2.3) 01.0=false {01.0,false(    ),false(    )} 2.4) 03.0= true {03.0,false(    ),false(    )} 2.5) 04.0=false {03.0,false(    ),false(    )} 2.6) 05.0= true {05.0,false(    ),false(    )} 2.7) 06.0=false {05.0,false(    ),false(    )} 2.8) 17.0= true {17.0,false(    ),false(    )} 2.9) 09.0= true {09.0, true(17.0),false(    )} 2.10) 08.0=false {09.0,false(    ),false(    )} 2.11) 07.0= true {07.0,false(    ),false(    )} 2.12) 06.0=false {07.0,false(    ),false(    )} 2.13) 05.0= true {05.0,false(    ),false(    )} 2.14) 06.0=false {05.0,false(    ),false(    )} 2.15) 05.0=false {05.0,false(    ),false(    )} "
			,"Break Line MM indicator: 3.1) 01.0=false {01.0,false(    ),false(    )} 3.2) 01.0=false {01.0,false(    ),false(    )} 3.3) 01.0=false {01.0,false(    ),false(    )} 3.4) 03.0= true {03.0,false(    ),false(    )} 3.5) 04.0=false {03.0,false(    ),false(    )} 3.6) 05.0= true {05.0,false(    ),false(    )} 3.7) 06.0=false {05.0,false(    ),false(    )} 3.8) 06.4=false {05.0,false(    ),false(    )} 3.9) 05.5=false {05.0,false(    ),false(    )} 3.10) 03.4= true {03.4, true(06.4),false(    )} 3.11) 07.0= true {07.0,false(    ), true(03.4)} 3.12) 06.0=false {07.0,false(    ),false(    )} 3.13) 05.0= true {05.0, true(07.0),false(    )} 3.14) 06.0=false {05.0,false(    ),false(    )} 3.15) 05.0=false {05.0,false(    ),false(    )} "
			,"Break Line MM indicator: 4.1) 01.0=false {01.0,false(    ),false(    )} 4.2) 01.0=false {01.0,false(    ),false(    )} 4.3) 01.0=false {01.0,false(    ),false(    )} 4.4) 03.0= true {03.0,false(    ),false(    )} 4.5) 04.0=false {03.0,false(    ),false(    )} 4.6) 05.0= true {05.0,false(    ),false(    )} 4.7) 06.0=false {05.0,false(    ),false(    )} 4.8) 06.4=false {05.0,false(    ),false(    )} 4.9) 04.8= true {04.9, true(06.4),false(    )} 4.10) 03.4= true {03.4,false(    ),false(    )} 4.11) 07.0= true {07.0,false(    ), true(03.4)} 4.12) 06.0=false {07.0,false(    ),false(    )} 4.13) 05.0= true {05.0, true(07.0),false(    )} 4.14) 06.0=false {05.0,false(    ),false(    )} 4.15) 05.0=false {05.0,false(    ),false(    )} "
			,"Break Line MM indicator: 5.1) 07.0=false {07.0,false(    ),false(    )} 5.2) 07.5=false {07.0,false(    ),false(    )} 5.3) 08.0= true {08.0,false(    ),false(    )} 5.4) 08.5=false {08.0,false(    ),false(    )} 5.5) 09.0= true {09.0,false(    ),false(    )} 5.6) 09.5=false {09.0,false(    ),false(    )} 5.7) 10.0= true {10.0,false(    ),false(    )} 5.8) 10.3=false {10.0,false(    ),false(    )} 5.9) 10.0=false {10.0,false(    ),false(    )} 5.10) 09.5=false {10.0,false(    ),false(    )} 5.11) 09.0= true {09.0, true(10.3),false(    )} 5.12) 08.5=false {09.0,false(    ),false(    )} 5.13) 08.0= true {08.0,false(    ),false(    )} 5.14) 07.5=false {08.0,false(    ),false(    )} 5.15) 07.0= true {07.0,false(    ),false(    )} 5.16) 06.5=false {07.0,false(    ),false(    )} 5.17) 06.0= true {06.0,false(    ),false(    )} 5.18) 05.5=false {06.0,false(    ),false(    )} 5.19) 05.0= true {05.0,false(    ),false(    )} 5.20) 04.7=false {05.0,false(    ),false(    )} 5.21) 05.0=false {05.0,false(    ),false(    )} 5.22) 05.5=false {05.0,false(    ),false(    )} 5.23) 06.0= true {06.0,false(    ), true(04.7)} 5.24) 06.5=false {06.0,false(    ),false(    )} 5.25) 07.0= true {07.0,false(    ),false(    )} 5.26) 07.5=false {07.0,false(    ),false(    )} 5.27) 07.0=false {07.0,false(    ),false(    )} 5.28) 06.5= true {06.5, true(07.5),false(    )} 5.29) 06.0=false {06.5,false(    ),false(    )} 5.30) 05.5= true {05.5,false(    ),false(    )} 5.31) 05.0=false {05.5,false(    ),false(    )} 5.32) 04.5= true {04.5,false(    ),false(    )} 5.33) 04.0=false {04.5,false(    ),false(    )} 5.34) 03.5= true {03.5,false(    ),false(    )} 5.35) 03.0=false {03.5,false(    ),false(    )} 5.36) 02.5= true {02.5,false(    ),false(    )} 5.37) 02.0=false {02.5,false(    ),false(    )} 5.38) 01.5= true {01.5,false(    ),false(    )} 5.39) 01.0=false {01.5,false(    ),false(    )} "
		};
	}

	/* "Break Line MM indicator: 
	 *  4.1) 01.0=false {01.0,false(    ),false(    )} 
	 *  4.2) 01.0=false {01.0,false(    ),false(    )} 
	 *  4.3) 01.0=false {01.0,false(    ),false(    )} 
	 *  4.4) 03.0= true {03.0,false(    ),false(    )} 
	 *  4.5) 04.0=false {03.0,false(    ),false(    )} 
	 *  4.6) 05.0= true {05.0,false(    ),false(    )} 
	 *  4.7) 06.0=false {05.0,false(    ),false(    )} 
	 *  4.8) 06.4=false {05.0,false(    ),false(    )} 
	 *  4.9) 04.8= true {04.9, true(06.4),false(    )} 
	 * 4.10) 03.4= true {03.4,false(    ),false(    )} 
	 * 4.11) 07.0= true {07.0,false(    ), true(03.4)} 
	 * 4.12) 06.0=false {07.0,false(    ),false(    )} 
	 * 4.13) 05.0= true {05.0, true(07.0),false(    )} 
	 * 4.14) 06.0=false {05.0,false(    ),false(    )} 
	 * 4.15) 05.0=false {05.0,false(    ),false(    )} "
	 * 
	 * 
	*/
	
	@After
	public void tearDown() throws Exception {
	}


	@Test
	public void ChangeIndic(){
		boolean success = true;

		
		for(int i=0; i<testCasesChangeIndic.length; i++){
			
			Change change = new ca.mss.rd.stat.Change();
			
			if( testCasesChangeIndic[i].length() == 0)
				continue;
			
			String result = String.format("Change indicator: ");
			
			for(int j=0; j<testData[i].length; j++){

				change.addValue(testData[i][j]);
				
				result += String.format("%d.%d) %2.1f=%5b ", i, j+1, testData[i][j], change.isChange());
			}
			
			if( !testCasesChangeIndic[i].equals(result) ){
				success = false;
				Logger.TEST.printf("\t\t,\"%s\"\n", result);
			}
		}
		Logger.TEST.printf("Change indicator done [%b]\n", success);
		
		assertTrue(success);
	}


	@Test
	public void BreakLine(){
		boolean success = true;

		
		for(int i=0; i<testCasesBreakLine.length; i++){
			
			ca.mss.rd.stat.BreakLine breakline = new ca.mss.rd.stat.BreakLine();
			
			if( testCasesBreakLine[i].length() == 0)
				continue;
			
			String result = String.format("Break Line indicator: ");
			
			for(int j=0; j<testData[i].length; j++){
				
				breakline.addValue(testData[i][j], 1.5);
				
				result += String.format("%d.%d) %2.1f=%5b (%2.1f) ", i, j+1, testData[i][j], breakline.isBroken(), breakline.getBLValue());
			}
			
			if( !testCasesBreakLine[i].equals(result) ){
				success = false;
				Logger.TEST.printf("\t\t,\"%s\"\n", result);
			}
		}
		Logger.TEST.printf("Break Line done [%b]\n", success);
		
		assertTrue(success);
	}

	@Test
	public void BreakLineMM(){
		boolean success = true;

		for(int i=0; i<testCasesBreakLineMM.length-1; i++){
			
			ca.mss.rd.stat.BreakLineMM breakline = new ca.mss.rd.stat.BreakLineMM();
			
			if( testCasesBreakLineMM[i].length() == 0)
				continue;
			
			String result = String.format("Break Line MM indicator: ");
			
			for(int j=0; j<testData[i].length; j++){

				breakline.addValue(testData[i][j], 1.5);
				
				result += String.format("%d.%d) %s=%5b {%s,%5b(%s),%5b(%s)} ", i, j+1, nice.format(testData[i][j]), breakline.isBroken(), nice.format(breakline.getBLValue()), breakline.isMax,  breakline.isMax?nice.format(breakline.max):"    ", breakline.isMin, breakline.isMin?nice.format(breakline.min):"    ");
			}
			
			if( !testCasesBreakLineMM[i].equals(result) ){
				success = false;
				Logger.TEST.printf("\t\t,\"%s\"\n", result);
				Logger.TEST.printf("\t//\t,\"%s\"\n", compare(result, testCasesBreakLineMM[i]));
			}
		}
		Logger.TEST.printf("Break Line MM done [%b]\n", success);
		
		assertTrue(success);
	}

	@Test
	public void BreakLineMMLast(){
		boolean success = true;

		for(int i=testCasesBreakLineMM.length-1; i<testCasesBreakLineMM.length; i++){
			
			ca.mss.rd.stat.BreakLineMM bl20 = new ca.mss.rd.stat.BreakLineMM();
			ca.mss.rd.stat.BreakLineMM bl10 = new ca.mss.rd.stat.BreakLineMM();
			ca.mss.rd.stat.BreakLineMM bl1 = new ca.mss.rd.stat.BreakLineMM();
			
			if( testCasesBreakLineMM[i].length() == 0)
				continue;

			String result = String.format("Break Line MM indicator: ");

			for(int j=0; j<testData[i].length; j++){

				bl20.addValue(testData[i][j], 2.0);
				bl10.addValue(testData[i][j], 1.0);
				bl1.addValue(testData[i][j], 0.1);

				result += String.format("%d.%d) %s=%5b {%s,%5b(%s),%5b(%s)} ", i, j+1, nice.format(testData[i][j]), bl10.isBroken(), nice.format(bl10.getBLValue()), bl10.isMax,  bl10.isMax?nice.format(bl10.max):"    ", bl10.isMin, bl10.isMin?nice.format(bl10.min):"    ");

				Logger.TEST.printf("\t%s\t%s\t%s\t%s\t%s", (testData[i][j]), (bl20.getBLValue()), (bl10.getBLValue()),  (bl1.getBLValue()), bl20.isMax?(bl20.getBLValue()):bl20.isMin?(bl20.getBLValue()):bl10.isMax?(bl10.getBLValue()):bl10.isMin?(bl10.getBLValue()):bl1.isMax?(bl1.getBLValue()):bl1.isMin?(bl1.getBLValue()):"");
			}
			if( !testCasesBreakLineMM[i].equals(result) ){
				success = false;
				Logger.TEST.printf("\t\t,\"%s\"\n", result);
				Logger.TEST.printf("\t//\t,\"%s\"\n", compare(result, testCasesBreakLineMM[i]));
			}
			
		}
		Logger.TEST.printf("Break Line MM last done [%b]\n", success);
		
		assertTrue(success);
	}

	
	private String compare(String test, String example){
		String result = "";
		
		int size = Math.min(test.length(), example.length());
		
		for(int i=0; i<size; i++){
			if( test.charAt(i) == example.charAt(i))
				result += " ";
			else
				result += test.charAt(i);
		}
		
		return result;
	}

}
