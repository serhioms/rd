package ca.mss.rd.test.math.stat;

import static org.junit.Assert.assertTrue;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ca.mss.rd.stat.AverageMooving1;
import ca.mss.rd.stat.AverageMooving;
import ca.mss.rd.stat.StdDevMooving1;
import ca.mss.rd.stat.StdDevMooving2;
import ca.mss.rd.stat.StdDevMoovingPopulation;
import ca.mss.rd.stat.StdDevMoovingSample;
import ca.mss.rd.stat.AverageSimple;
import ca.mss.rd.stat.StdDevPopulationSimple;
import ca.mss.rd.util.Logger;

public class MathStatSimpleTest {

	String[] testCasesStdDevSample;
	String[] testCasesStdDevPopulation;
	String[] testCasesAvg;
	
	@Before
	public void setUp() throws Exception {
		Logger.TEST.isOn = true;
		
		testCasesStdDevSample = new String[]{ ""
				,"Moving st.dev depth = 1 1) d=0.000000 2) d=0.000000 3) d=0.000000 4) d=0.000000 5) d=0.000000 6) d=0.000000 7) d=0.000000 8) d=0.000000 9) d=0.00000010) d=0.000000"
				,"Moving st.dev depth = 2 1) d=0.000000 2) d=0.707107 3) d=0.707107 4) d=0.707107 5) d=0.707107 6) d=0.707107 7) d=0.707107 8) d=0.707107 9) d=0.70710710) d=0.707107"
				,"Moving st.dev depth = 3 1) d=0.000000 2) d=0.707107 3) d=1.000000 4) d=1.000000 5) d=1.000000 6) d=1.000000 7) d=1.000000 8) d=1.000000 9) d=1.00000010) d=1.000000"
				,"Moving st.dev depth = 4 1) d=0.000000 2) d=0.707107 3) d=1.000000 4) d=1.290994 5) d=1.290994 6) d=1.290994 7) d=1.290994 8) d=1.290994 9) d=1.29099410) d=1.290994"
				,"Moving st.dev depth = 5 1) d=0.000000 2) d=0.707107 3) d=1.000000 4) d=1.290994 5) d=1.581139 6) d=1.581139 7) d=1.581139 8) d=1.581139 9) d=1.58113910) d=1.581139"
				,"Moving st.dev depth = 6 1) d=0.000000 2) d=0.707107 3) d=1.000000 4) d=1.290994 5) d=1.581139 6) d=1.870829 7) d=1.870829 8) d=1.870829 9) d=1.87082910) d=1.870829"
				,"Moving st.dev depth = 7 1) d=0.000000 2) d=0.707107 3) d=1.000000 4) d=1.290994 5) d=1.581139 6) d=1.870829 7) d=2.160247 8) d=2.160247 9) d=2.16024710) d=2.160247"
				,"Moving st.dev depth = 8 1) d=0.000000 2) d=0.707107 3) d=1.000000 4) d=1.290994 5) d=1.581139 6) d=1.870829 7) d=2.160247 8) d=2.449490 9) d=2.44949010) d=2.449490"
				,"Moving st.dev depth = 9 1) d=0.000000 2) d=0.707107 3) d=1.000000 4) d=1.290994 5) d=1.581139 6) d=1.870829 7) d=2.160247 8) d=2.449490 9) d=2.73861310) d=2.738613"
				,"Moving st.dev depth = 10 1) d=0.000000 2) d=0.707107 3) d=1.000000 4) d=1.290994 5) d=1.581139 6) d=1.870829 7) d=2.160247 8) d=2.449490 9) d=2.73861310) d=3.027650"
		};

		testCasesStdDevPopulation = new String[]{ ""
				,"Moving st.dev depth = 1 1) d=0.000000 2) d=0.000000 3) d=0.000000 4) d=0.000000 5) d=0.000000 6) d=0.000000 7) d=0.000000 8) d=0.000000 9) d=0.00000010) d=0.000000"
				,"Moving st.dev depth = 2 1) d=0.000000 2) d=0.500000 3) d=0.500000 4) d=0.500000 5) d=0.500000 6) d=0.500000 7) d=0.500000 8) d=0.500000 9) d=0.50000010) d=0.500000"
				,"Moving st.dev depth = 3 1) d=0.000000 2) d=0.500000 3) d=0.816497 4) d=0.816497 5) d=0.816497 6) d=0.816497 7) d=0.816497 8) d=0.816497 9) d=0.81649710) d=0.816497"
				,"Moving st.dev depth = 4 1) d=0.000000 2) d=0.500000 3) d=0.816497 4) d=1.118034 5) d=1.118034 6) d=1.118034 7) d=1.118034 8) d=1.118034 9) d=1.11803410) d=1.118034"
				,"Moving st.dev depth = 5 1) d=0.000000 2) d=0.500000 3) d=0.816497 4) d=1.118034 5) d=1.414214 6) d=1.414214 7) d=1.414214 8) d=1.414214 9) d=1.41421410) d=1.414214"
				,"Moving st.dev depth = 6 1) d=0.000000 2) d=0.500000 3) d=0.816497 4) d=1.118034 5) d=1.414214 6) d=1.707825 7) d=1.707825 8) d=1.707825 9) d=1.70782510) d=1.707825"
				,"Moving st.dev depth = 7 1) d=0.000000 2) d=0.500000 3) d=0.816497 4) d=1.118034 5) d=1.414214 6) d=1.707825 7) d=2.000000 8) d=2.000000 9) d=2.00000010) d=2.000000"
				,"Moving st.dev depth = 8 1) d=0.000000 2) d=0.500000 3) d=0.816497 4) d=1.118034 5) d=1.414214 6) d=1.707825 7) d=2.000000 8) d=2.291288 9) d=2.29128810) d=2.291288"
				,"Moving st.dev depth = 9 1) d=0.000000 2) d=0.500000 3) d=0.816497 4) d=1.118034 5) d=1.414214 6) d=1.707825 7) d=2.000000 8) d=2.291288 9) d=2.58198910) d=2.581989"
				,"Moving st.dev depth = 10 1) d=0.000000 2) d=0.500000 3) d=0.816497 4) d=1.118034 5) d=1.414214 6) d=1.707825 7) d=2.000000 8) d=2.291288 9) d=2.58198910) d=2.872281"
		};

		testCasesAvg = new String[]{ ""
				,"Moving st.dev depth = 1 1) d=1.000000 2) d=2.000000 3) d=3.000000 4) d=4.000000 5) d=5.000000 6) d=6.000000 7) d=7.000000 8) d=8.000000 9) d=9.00000010) d=10.000000"
				,"Moving st.dev depth = 2 1) d=1.000000 2) d=1.500000 3) d=2.500000 4) d=3.500000 5) d=4.500000 6) d=5.500000 7) d=6.500000 8) d=7.500000 9) d=8.50000010) d=9.500000"
				,"Moving st.dev depth = 3 1) d=1.000000 2) d=1.500000 3) d=2.000000 4) d=3.000000 5) d=4.000000 6) d=5.000000 7) d=6.000000 8) d=7.000000 9) d=8.00000010) d=9.000000"
				,"Moving st.dev depth = 4 1) d=1.000000 2) d=1.500000 3) d=2.000000 4) d=2.500000 5) d=3.500000 6) d=4.500000 7) d=5.500000 8) d=6.500000 9) d=7.50000010) d=8.500000"
				,"Moving st.dev depth = 5 1) d=1.000000 2) d=1.500000 3) d=2.000000 4) d=2.500000 5) d=3.000000 6) d=4.000000 7) d=5.000000 8) d=6.000000 9) d=7.00000010) d=8.000000"
				,"Moving st.dev depth = 6 1) d=1.000000 2) d=1.500000 3) d=2.000000 4) d=2.500000 5) d=3.000000 6) d=3.500000 7) d=4.500000 8) d=5.500000 9) d=6.50000010) d=7.500000"
				,"Moving st.dev depth = 7 1) d=1.000000 2) d=1.500000 3) d=2.000000 4) d=2.500000 5) d=3.000000 6) d=3.500000 7) d=4.000000 8) d=5.000000 9) d=6.00000010) d=7.000000"
				,"Moving st.dev depth = 8 1) d=1.000000 2) d=1.500000 3) d=2.000000 4) d=2.500000 5) d=3.000000 6) d=3.500000 7) d=4.000000 8) d=4.500000 9) d=5.50000010) d=6.500000"
				,"Moving st.dev depth = 9 1) d=1.000000 2) d=1.500000 3) d=2.000000 4) d=2.500000 5) d=3.000000 6) d=3.500000 7) d=4.000000 8) d=4.500000 9) d=5.00000010) d=6.000000"
				,"Moving st.dev depth = 10 1) d=1.000000 2) d=1.500000 3) d=2.000000 4) d=2.500000 5) d=3.000000 6) d=3.500000 7) d=4.000000 8) d=4.500000 9) d=5.00000010) d=5.500000"
		};

	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void AverageMooving(){
		boolean success = true;
		
		for(int i=0; i<testCasesAvg.length; i++){
			
			if( testCasesAvg[i].length() == 0)
				continue;
			
			AverageMooving stat = new AverageMooving(i);
			
			String result = String.format("Moving st.dev depth = %d", stat.getWindowSize());
			
			for(double d=1.0; d<11.0; d+=1.0){
				stat.addValue(d);
				
				result += String.format("%2.0f) d=%3.6f", d, stat.getAverage());
			}
			
			if( !testCasesAvg[i].equals(result) ){
				success = false;
				Logger.TEST.printf(",\"%s\"\n", result);
			}
		}
		Logger.TEST.printf("Moving average OPTIMIZED testcase done [%b]\n", success);
		
		assertTrue(success);
	}


	@Test
	public void AverageMooving1(){
		boolean success = true;
		
		for(int i=0; i<testCasesAvg.length; i++){
			
			if( testCasesAvg[i].length() == 0)
				continue;
			
			AverageMooving1 stat = new AverageMooving1(i);
			
			String result = String.format("Moving st.dev depth = %d", stat.getDepth());
			
			for(double d=1.0; d<11.0; d+=1.0){
				stat.addValue(d);
				
				result += String.format("%2.0f) d=%3.6f", d, stat.getAverage());
			}
			
			if( !testCasesAvg[i].equals(result) ){
				success = false;
				Logger.TEST.printf(",\"%s\"\n", result);
			}
		}
		Logger.TEST.printf("Moving average 1 testcase done [%b]\n", success);
		
		assertTrue(success);
	}

	@Test
	public void StdDevMoovingPopulation(){
		boolean success = true;
		
		for(int i=0; i<testCasesStdDevPopulation.length; i++){
			
			if( testCasesStdDevPopulation[i].length() == 0)
				continue;
			
			StdDevMoovingPopulation stat = new StdDevMoovingPopulation(i);
			
			String result = String.format("Moving st.dev depth = %d", stat.getWindowSize());
			
			for(double d=1.0; d<11.0; d+=1.0){
				stat.addValue(d);
				
				result += String.format("%2.0f) d=%3.6f", d, stat.getStDev());
			}
			
			if( !testCasesStdDevPopulation[i].equals(result) ){
				success = false;
				Logger.TEST.printf(",\"%s\"\n", result);
			}
		}
		Logger.TEST.printf("Moving standard deviation OPTIMIZED POPULATION testcase done [%b]\n", success);

		assertTrue(success);
	}
	
	@Test
	public void StdDevMoovingSample(){
		boolean success = true;
		
		for(int i=0; i<testCasesStdDevSample.length; i++){
			
			if( testCasesStdDevSample[i].length() == 0)
				continue;
			
			StdDevMoovingSample stat = new StdDevMoovingSample(i);
			
			String result = String.format("Moving st.dev depth = %d", stat.getWindowSize());
			
			for(double d=1.0; d<11.0; d+=1.0){
				stat.addValue(d);
				
				result += String.format("%2.0f) d=%3.6f", d, stat.getStDev());
			}
			
			if( !testCasesStdDevSample[i].equals(result) ){
				success = false;
				Logger.TEST.printf(",\"%s\"\n", result);
			}
		}
		Logger.TEST.printf("Moving standard deviation OPTIMIZED SAMPLE testcase done [%b]\n", success);

		assertTrue(success);
	}
	
	@Test
	public void StdDevMooving1(){
		boolean success = true;
		
		for(int i=0; i<testCasesStdDevPopulation.length; i++){
			
			if( testCasesStdDevPopulation[i].length() == 0)
				continue;
			
			StdDevMooving1 stat = new StdDevMooving1(i);
			
			String result = String.format("Moving st.dev depth = %d", stat.getDepth());
			
			for(double d=1.0; d<11.0; d+=1.0){
				stat.addValue(d);
				
				result += String.format("%2.0f) d=%3.6f", d, stat.getStdDev());
			}
			
			if( !testCasesStdDevPopulation[i].equals(result) ){
				success = false;
				Logger.TEST.printf(",\"%s\"\n", result);
			}
		}
		Logger.TEST.printf("Moving standard deviation 1 testcase done [%b]\n", success);

		assertTrue(success);
	}
	
	@Test
	public void StdDevMooving2(){
		boolean success = true;
		
		for(int i=0; i<testCasesStdDevPopulation.length; i++){
			
			if( testCasesStdDevPopulation[i].length() == 0)
				continue;
			
			StdDevMooving2 stat = new StdDevMooving2(i);
			
			String result = String.format("Moving st.dev depth = %d", stat.getDepth());
			
			for(double d=1.0; d<11.0; d+=1.0){
				stat.addValue(d);
				
				result += String.format("%2.0f) d=%3.6f", d, stat.getStdDev());
			}
			
			if( !testCasesStdDevPopulation[i].equals(result) ){
				success = false;
				Logger.TEST.printf(",\"%s\"\n", result);
			}
		}
		Logger.TEST.printf("Moving standard deviation 2 testcase done [%b]\n", success);

		assertTrue(success);
	}
	
	@Test
	public void StandardDeviationCommons(){
		boolean success = true;
		
		for(int i=0; i<testCasesStdDevSample.length; i++){
			
			if( testCasesStdDevSample[i].length() == 0)
				continue;
			
			// Get a DescriptiveStatistics instance
			DescriptiveStatistics stat = new DescriptiveStatistics();
			stat.setWindowSize(i);
			
			String result = String.format("Moving st.dev depth = %d", stat.getWindowSize());
			
			for(double d=1.0; d<11.0; d+=1.0){
		        stat.addValue(d);
				
				result += String.format("%2.0f) d=%3.6f", d, stat.getStandardDeviation());
			}
			
			if( !testCasesStdDevSample[i].equals(result) ){
				success = false;
				Logger.TEST.printf(",\"%s\"\n", result);
			}
		}
		Logger.TEST.printf("Moving standard deviation COMMONS testcase done [%b]\n", success);

		assertTrue(success);
	}


	@Test
	public void AverageCommons(){
		boolean success = true;
		
		for(int i=0; i<testCasesAvg.length; i++){
			
			if( testCasesAvg[i].length() == 0)
				continue;
			
			// Get a DescriptiveStatistics instance
			DescriptiveStatistics stat = new DescriptiveStatistics();
			stat.setWindowSize(i);
			
			String result = String.format("Moving st.dev depth = %d", stat.getWindowSize());
			
			for(double d=1.0; d<11.0; d+=1.0){
				stat.addValue(d);
				
				result += String.format("%2.0f) d=%3.6f", d, stat.getMean());
			}
			
			if( !testCasesAvg[i].equals(result) ){
				success = false;
				Logger.TEST.printf(",\"%s\"\n", result);
			}
		}
		Logger.TEST.printf("Moving average COMMONS testcase done [%b]\n", success);
		
		assertTrue(success);
	}

	@Test
	public void AverageSimple(){
		boolean success = true;
		
		for(int i=0; i<testCasesAvg.length; i++){
			
			if( testCasesAvg[i].length() == 0)
				continue;
			
			if( i != (testCasesAvg.length-1) )
				continue;
			
			AverageSimple stat = new AverageSimple();
			
			String result = String.format("Moving st.dev depth = %d", i);
			
			for(double d=1.0; d<11.0; d+=1.0){
				stat.addValue(d);
				
				result += String.format("%2.0f) d=%3.6f", d, stat.getAverage());
			}
			
			if( !testCasesAvg[i].equals(result) ){
				success = false;
				Logger.TEST.printf(",\"%s\"\n", result);
			}
		}
		Logger.TEST.printf("Moving average SIMPLE testcase done [%b]\n", success);
		
		assertTrue(success);
	}

	@Test
	public void StdDevSimplePopulation(){
		boolean success = true;
		
		for(int i=0; i<testCasesStdDevPopulation.length; i++){
			
			if( testCasesStdDevPopulation[i].length() == 0)
				continue;
			
			if( i != (testCasesAvg.length-1) )
				continue;

			StdDevPopulationSimple stat = new StdDevPopulationSimple();
			
			String result = String.format("Moving st.dev depth = %d", i);
			
			for(double d=1.0; d<11.0; d+=1.0){
				stat.addValue(d);
				
				result += String.format("%2.0f) d=%3.6f", d, stat.getStDev());
			}
			
			if( !testCasesStdDevPopulation[i].equals(result) ){
				success = false;
				Logger.TEST.printf(",\"%s\"\n", result);
			}
		}
		Logger.TEST.printf("Moving standard deviation SIMPLE POPULATION testcase done [%b]\n", success);

		assertTrue(success);
	}
}
