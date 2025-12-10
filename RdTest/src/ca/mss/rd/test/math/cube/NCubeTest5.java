package ca.mss.rd.test.math.cube;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ca.mss.rd.math.cube.NCube;
import ca.mss.rd.math.cube.impl.NCubeIterator;
import ca.mss.rd.util.Logger;
import ca.mss.rd.util.UtilRand;


public class NCubeTest5 {
	
	static private boolean Logger_TEST_VERBOSE_isOn = false;
	static private boolean Logger_TEST_isOn = false;

	MACube5 mac;
	List<NCubeIterator<MACube5.MAPoint5, MACube5.MARegion>> iterList;
	int npp;
	Set<String> unique;
	String msg;
	
	@Before
	public void setUp() throws Exception {
		mac = new MACube5();
		iterList = new ArrayList<NCubeIterator<MACube5.MAPoint5, MACube5.MARegion>>();
		mac.populate(new int[]{1000, 1000, 1000, 1000, 1000, 5000, 5000, 5000, 5000, 5000});
		unique = new HashSet<String>();
	}

	@After
	public void tearDown() throws Exception {
		mac = null;
	}

	@Test
	public void boundaries(){
		boolean result = true;
		
		for(int i=0; i<5; i++){
			result &= mac.cubeRegion.left.getDim(i) == 1000;
			result &= mac.cubeRegion.right.getDim(i) == 5000;
		}
		
		assertTrue("Cube boundaries is wrong", result);
	}


	@Test
	public void breakCube2(){
		int cnt=0, sum=0, ttl=0, level=1;
		double profit;
		
		unique.clear();
		
		Logger.TEST_VERBOSE.isOn = Logger_TEST_VERBOSE_isOn;
		Logger.TEST.isOn = Logger_TEST_isOn;

		mac.setBreaks(2);
		Logger.TEST.printf("\nCube %d", mac.getBreaks());
		Logger.TEST.printf("-------------------------------------------------------------");

		NCubeIterator<MACube5.MAPoint5, MACube5.MARegion> iter = mac.cubeRegion.iterator();
		iterList.add(iter);

		MACube5.MAPoint5 step = iter.getStep(), size = iter.getSize(), left = iter.getLeft(), right = iter.getRight();
		Logger.TEST.printf("%s/%d (%s)", step.getMetrics(), size.getSize(), left.getLimits(right));

		for(npp=0; iter.hasNext(); ){
			++cnt;
			
			MACube5.MAPoint5 pnt = iter.next();

			if( unique.contains(pnt.getKey()) ){
				Logger.TEST_VERBOSE.printf(msg=String.format("Duplication key [%s]", pnt.getKey()));
				assertTrue(msg, false);
			} else {
				unique.add(pnt.getKey());
			}
			
			sum += pnt.getDimSum();
			
			profit = UtilRand.getRandDbl(10.0*level)-.111;
			
			iter.addSurf(profit);

			Logger.TEST_VERBOSE.printf("%6d) %s ->%-3d = %5.2f", ++npp, pnt.getCoordinates(), iter.getIndex(), profit);
		}
		
		ttl += cnt;
		
		double[] surf=iter.getSurf();
		for(int i=0; i<surf.length; i++  ){
			if( surf[i] == 0.0 ){
				Logger.TEST.printf(msg=String.format("Surface not initialize properly [level=%d][index=%d]!", level, i));
				assertTrue(msg, false);
			}
		}
		
		assertTrue(String.format("Cube iterator is wrong. Expected (sum=%d for %d nodes)", sum, ttl), sum == 320032 && ttl == 32);
	}
	
	@Test
	public void breakCube3(){
		int cnt=0, sum=0, ttl=0, level=1;
		double profit;
		
		unique.clear();
		
		Logger.TEST_VERBOSE.isOn = Logger_TEST_VERBOSE_isOn;
		Logger.TEST.isOn = Logger_TEST_isOn;

		mac.setBreaks(3);
		Logger.TEST.printf("\nCube %d", mac.getBreaks());
		Logger.TEST.printf("-------------------------------------------------------------");

		NCubeIterator<MACube5.MAPoint5, MACube5.MARegion> iter = mac.cubeRegion.iterator();
		iterList.add(iter);

		MACube5.MAPoint5 step = iter.getStep(), size = iter.getSize(), left = iter.getLeft(), right = iter.getRight();
		Logger.TEST.printf("%s/%d (%s)", step.getMetrics(), size.getSize(), left.getLimits(right));

		for(npp=0; iter.hasNext(); ){
			++cnt;
			
			MACube5.MAPoint5 pnt = iter.next();

			if( unique.contains(pnt.getKey()) ){
				Logger.TEST_VERBOSE.printf(msg=String.format("Duplication key [%s]", pnt.getKey()));
				assertTrue(msg, false);
			} else {
				unique.add(pnt.getKey());
			}
			
			sum += pnt.getDimSum();
			
			profit = UtilRand.getRandDbl(10.0*level)-.111;
			
			iter.addSurf(profit);

			Logger.TEST_VERBOSE.printf("%6d) %s ->%-3d = %5.2f", ++npp, pnt.getCoordinates(), iter.getIndex(), profit);
		}
		
		ttl += cnt;
		
		double[] surf=iter.getSurf();
		for(int i=0; i<surf.length; i++  ){
			if( surf[i] == 0.0 ){
				Logger.TEST.printf(msg=String.format("Surface not initialize properly [level=%d][index=%d]!", level, i));
				assertTrue(msg, false);
			}
		}
		
		assertTrue(String.format("Cube iterator is wrong. Expected (sum=%d for %d nodes)", sum, ttl), sum == 2834838 && ttl == 243);
	}
	
	
	@Test
	public void breakCube4(){
		int cnt=0, sum=0, ttl=0, level=1;
		double profit;
		
		unique.clear();
		
		Logger.TEST_VERBOSE.isOn = Logger_TEST_VERBOSE_isOn;
		Logger.TEST.isOn = Logger_TEST_isOn;

		mac.setBreaks(4);
		Logger.TEST.printf("\nCube %d", mac.getBreaks());
		Logger.TEST.printf("-------------------------------------------------------------");

		NCubeIterator<MACube5.MAPoint5, MACube5.MARegion> iter = mac.cubeRegion.iterator();
		iterList.add(iter);

		MACube5.MAPoint5 step = iter.getStep(), size = iter.getSize(), left = iter.getLeft(), right = iter.getRight();
		Logger.TEST.printf("%s/%d (%s)", step.getMetrics(), size.getSize(), left.getLimits(right));

		for(npp=0; iter.hasNext(); ){
			++cnt;
			
			MACube5.MAPoint5 pnt = iter.next();

			if( unique.contains(pnt.getKey()) ){
				Logger.TEST_VERBOSE.printf(msg=String.format("Duplication key [%s]", pnt.getKey()));
				assertTrue(msg, false);
			} else {
				unique.add(pnt.getKey());
			}
			
			sum += pnt.getDimSum();
			
			profit = UtilRand.getRandDbl(10.0*level)-.111;
			
			iter.addSurf(profit);

			Logger.TEST_VERBOSE.printf("%6d) %s ->%-3d = %5.2f", ++npp, pnt.getCoordinates(), iter.getIndex(), profit);
		}
		
		ttl += cnt;
		
		double[] surf=iter.getSurf();
		for(int i=0; i<surf.length; i++  ){
			if( surf[i] == 0.0 ){
				Logger.TEST.printf(msg=String.format("Surface not initialize properly [level=%d][index=%d]!", level, i));
				assertTrue(msg, false);
			}
		}
		
		assertTrue(String.format("Cube iterator is wrong. Expected (sum=%d for %d nodes)", sum, ttl), sum == 12801024 && ttl == 1024);
	}
	
	@Test
	public void breakCube10(){
		int cnt=0, sum=0, ttl=0, level=1;
		double profit;
		
		unique.clear();
		
		Logger.TEST_VERBOSE.isOn = Logger_TEST_VERBOSE_isOn;
		Logger.TEST.isOn = Logger_TEST_isOn;

		mac.setBreaks(10);
		Logger.TEST.printf("\nCube %d", mac.getBreaks());
		Logger.TEST.printf("-------------------------------------------------------------");

		NCubeIterator<MACube5.MAPoint5, MACube5.MARegion> iter = mac.cubeRegion.iterator();
		iterList.add(iter);

		MACube5.MAPoint5 step = iter.getStep(), size = iter.getSize(), left = iter.getLeft(), right = iter.getRight();
		Logger.TEST.printf("%s/%d (%s)", step.getMetrics(), size.getSize(), left.getLimits(right));

		for(npp=0; iter.hasNext(); ){
			++cnt;
			
			MACube5.MAPoint5 pnt = iter.next();

			if( unique.contains(pnt.getKey()) ){
				Logger.TEST_VERBOSE.printf(msg=String.format("Duplication key [%s]", pnt.getKey()));
				assertTrue(msg, false);
			} else {
				unique.add(pnt.getKey());
			}
			
			sum += pnt.getDimSum();
			
			profit = UtilRand.getRandDbl(10.0*level)-.111;
			
			iter.addSurf(profit);

			Logger.TEST_VERBOSE.printf("%6d) %s ->%-3d = %5.2f", ++npp, pnt.getCoordinates(), iter.getIndex(), profit);
		}
		
		ttl += cnt;
		
		double[] surf=iter.getSurf();
		for(int i=0; i<surf.length; i++  ){
			if( surf[i] == 0.0 ){
				Logger.TEST.printf(msg=String.format("Surface not initialize properly [level=%d][index=%d]!", level, i));
				assertTrue(msg, false);
			}
		}
		
		assertTrue(String.format("Cube iterator is wrong. Expected (sum=%d for %d nodes)", sum, ttl), sum == 1400100000 && ttl == 100000);
	}
	
	@Test
	public void breakCube103(){
		int cnt=0, sum=0, ttl=0, level=1;
		double profit;
		
		unique.clear();
		
		Logger.TEST_VERBOSE.isOn = Logger_TEST_VERBOSE_isOn;
		Logger.TEST.isOn = Logger_TEST_isOn;

		mac.setBreaks(10);
		Logger.TEST.printf("\nCube %d", mac.getBreaks());
		Logger.TEST.printf("-------------------------------------------------------------");

		NCubeIterator<MACube5.MAPoint5, MACube5.MARegion> iter = mac.cubeRegion.iterator();
		iterList.add(iter);

		MACube5.MAPoint5 step = iter.getStep(), size = iter.getSize(), left = iter.getLeft(), right = iter.getRight();
		Logger.TEST.printf("%s/%d (%s)", step.getMetrics(), size.getSize(), left.getLimits(right));

		for(npp=0; iter.hasNext(); ){
			++cnt;
			
			MACube5.MAPoint5 pnt = iter.next();

			if( unique.contains(pnt.getKey()) ){
				Logger.TEST_VERBOSE.printf(msg=String.format("Duplication key [%s]", pnt.getKey()));
				assertTrue(msg, false);
			} else {
				unique.add(pnt.getKey());
			}
			
			sum += pnt.getDimSum();
			
			profit = UtilRand.getRandDbl(10.0*level)-.111;
			
			iter.addSurf(profit);

			Logger.TEST_VERBOSE.printf("%6d) %s ->%-3d = %5.2f", ++npp, pnt.getCoordinates(), iter.getIndex(), profit);
			
			if( iter.isBreakable() ){
				if( cnt == 3 ){
					ttl += breakCube(pnt.clone(), iter.getStep(), String.format("%d", cnt), level+1);
					Logger.TEST.printf("%s/%d (%s)", step.getMetrics(), size.getSize(), left.getLimits(right));
				}
			}
		}
		
		ttl += cnt;
		
		double[] surf=iter.getSurf();
		for(int i=0; i<surf.length; i++  ){
			if( surf[i] == 0.0 ){
				Logger.TEST.printf(msg=String.format("Surface not initialize properly [level=%d][index=%d]!", level, i));
				assertTrue(msg, false);
			}
		}
		
		assertTrue(String.format("Cube iterator is wrong. Expected (sum=%d for %d nodes)", sum, ttl), sum == 1400100000 && ttl == 500064);
	}

	private int breakCube(MACube5.MAPoint5 ppoint, MACube5.MAPoint5 pstep, String prefix, int level) {
		int cnt=0, ttl=0;
		double profit;
		
		NCubeIterator<MACube5.MAPoint5, MACube5.MARegion> iter = ppoint.getSubRegion(pstep).iterator();

		MACube5.MAPoint5 step = iter.getStep(), size = iter.getSize(), left = iter.getLeft(), right = iter.getRight();
		Logger.TEST.printf("%s/%d (%s)", step.getMetrics(), size.getSize(), left.getLimits(right));

		iterList.add(iter);
		for(; iter.hasNext(); ){
			++cnt;
			
			MACube5.MAPoint5 pnt = iter.next();

			if( unique.contains(pnt.getKey()) ){
				Logger.TEST_VERBOSE.printf(msg=String.format("Duplication key [%s]", pnt.getKey()));
				assertTrue(msg, false);
			} else {
				unique.add(pnt.getKey());
			}
			
			profit = UtilRand.getRandDbl(10.0*level)-.111;
			
			iter.addSurf(profit);

			Logger.TEST_VERBOSE.printf("%6d) %s ->%-3d = %5.2f", ++npp, pnt.getCoordinates(), iter.getIndex(), profit);
			
			if( iter.isBreakable() ){
				if( cnt == 3 ){
					ttl += breakCube(pnt.clone(), iter.getStep(), String.format("%d", cnt), level+1);
					Logger.TEST.printf("%s/%d (%s)", step.getMetrics(), size.getSize(), left.getLimits(right));
				}
			}
		}
		
		ttl += cnt;
		
		double[] surf=iter.getSurf();
		for(int i=0; i<surf.length; i++  ){
			if( surf[i] == 0.0 ){
				Logger.TEST.printf(msg=String.format("Surface not initialize properly [level=%d][index=%d]!", level, i));
				assertTrue(msg, false);
			}
		}
		
		return ttl+cnt;
	}

	public class MACube5 extends NCube<MACube5.MAPoint5, MACube5.MARegion> {
		
		public class MAPoint5 extends NCube<MAPoint5,MARegion>.NPoint {
			public MAPoint5() {
				super();
			}
		}

		public class MARegion extends NCube<MAPoint5,MARegion>.NRegion {
		}

		@Override
		public MAPoint5 newPoint() {
			return new MAPoint5();
		}
		
		@Override
		public MARegion newRegion() {
			return new MARegion();
		}

		public MACube5() {
			super(5);
		}

	}

}
