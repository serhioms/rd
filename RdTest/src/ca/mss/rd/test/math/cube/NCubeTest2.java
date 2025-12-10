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
import ca.mss.rd.test.math.cube.NCubeTest2.MACube2.MAPoint2;
import ca.mss.rd.util.Logger;
import ca.mss.rd.util.UtilRand;


public class NCubeTest2 {
	
	static private boolean Logger_TEST_VERBOSE_isOn = false;
	static private boolean Logger_TEST_isOn = false;
	
	
	MACube2 mac;
	List<NCubeIterator<MACube2.MAPoint2, MACube2.MARegion>> iterList;
	int npp;
	Set<String> unique;
	String msg;
	
	@Before
	public void setUp() throws Exception {
		mac = new MACube2();
		iterList = new ArrayList<NCubeIterator<MACube2.MAPoint2, MACube2.MARegion>>();
		mac.populate(new int[]{3500, 1000, 6900, 5000});
		unique = new HashSet<String>();
	}

	@After
	public void tearDown() throws Exception {
		mac = null;
	}

	@Test
	public void boundaries(){
		assertTrue("Cube boundaries is wrong", mac.cubeRegion.left.getSlow() == 3500 && mac.cubeRegion.left.getFast() == 1000 && mac.cubeRegion.right.getSlow() == 6900 && mac.cubeRegion.right.getFast() == 5000);
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

		NCubeIterator<MACube2.MAPoint2, MACube2.MARegion> iter = mac.cubeRegion.iterator();
		iterList.add(iter);

		MAPoint2 step = iter.getStep(), size = iter.getSize(), left = iter.getLeft(), right = iter.getRight();
		Logger.TEST.printf("%dx%d/%d (%d-%d,%d-%d)", step.getSlow(), step.getFast(), size.getSlow()*size.getFast(), left.getSlow(), right.getSlow(), left.getFast(), right.getFast());

		for(npp=0; iter.hasNext(); ){
			++cnt;
			
			MACube2.MAPoint2 pnt = iter.next();

			if( unique.contains(pnt.getKey()) ){
				Logger.TEST_VERBOSE.printf(msg=String.format("Duplication key [%s]", pnt.getKey()));
				assertTrue(msg, false);
			} else {
				unique.add(pnt.getKey());
			}
			
			sum += pnt.getSlow();
			sum += pnt.getFast();
			
			profit = UtilRand.getRandDbl(10.0*level)-.111;
			
			iter.addSurf(profit);

			Logger.TEST_VERBOSE.printf("%6d) %5d, %-5d ->%-3d = %5.2f", ++npp, pnt.getSlow(), pnt.getFast(), iter.getIndex(), profit);
		}
		
		ttl += cnt;
		
		double[] surf=iter.getSurf();
		for(int i=0; i<surf.length; i++  ){
			if( surf[i] == 0.0 ){
				Logger.TEST.printf(msg=String.format("Surface not initialize properly [level=%d][index=%d]!", level, i));
				assertTrue(msg, false);
			}
		}
		
		assertTrue(String.format("Cube iterator is wrong. Expected (sum=%d for %d nodes)", sum, ttl), sum == 25400 && ttl == 4);
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

		NCubeIterator<MACube2.MAPoint2, MACube2.MARegion> iter = mac.cubeRegion.iterator();
		iterList.add(iter);

		MAPoint2 step = iter.getStep(), size = iter.getSize(), left = iter.getLeft(), right = iter.getRight();
		Logger.TEST.printf("%dx%d/%d (%d-%d,%d-%d)", step.getSlow(), step.getFast(), size.getSlow()*size.getFast(), left.getSlow(), right.getSlow(), left.getFast(), right.getFast());

		for(npp=0; iter.hasNext(); ){
			++cnt;
			
			MACube2.MAPoint2 pnt = iter.next();

			if( unique.contains(pnt.getKey()) ){
				Logger.TEST_VERBOSE.printf(msg=String.format("Duplication key [%s]", pnt.getKey()));
				assertTrue(msg, false);
			} else {
				unique.add(pnt.getKey());
			}
			
			sum += pnt.getSlow();
			sum += pnt.getFast();
			
			profit = UtilRand.getRandDbl(10.0*level)-.111;
			
			iter.addSurf(profit);

			Logger.TEST_VERBOSE.printf("%6d) %5d, %-5d ->%-3d = %5.2f", ++npp, pnt.getSlow(), pnt.getFast(), iter.getIndex(), profit);
		}
		
		ttl += cnt;
		
		double[] surf=iter.getSurf();
		for(int i=0; i<surf.length; i++  ){
			if( surf[i] == 0.0 ){
				Logger.TEST.printf(msg=String.format("Surface not initialize properly [level=%d][index=%d]!", level, i));
				assertTrue(msg, false);
			}
		}
		
		assertTrue(String.format("Cube iterator is wrong. Expected (sum=%d for %d nodes)", sum, ttl), sum == 116400 && ttl == 16);
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

		NCubeIterator<MACube2.MAPoint2, MACube2.MARegion> iter = mac.cubeRegion.iterator();
		iterList.add(iter);

		MAPoint2 step = iter.getStep(), size = iter.getSize(), left = iter.getLeft(), right = iter.getRight();
		Logger.TEST.printf("%dx%d/%d (%d-%d,%d-%d)", step.getSlow(), step.getFast(), size.getSlow()*size.getFast(), left.getSlow(), right.getSlow(), left.getFast(), right.getFast());

		for(npp=0; iter.hasNext(); ){
			++cnt;
			
			MACube2.MAPoint2 pnt = iter.next();

			if( unique.contains(pnt.getKey()) ){
				Logger.TEST_VERBOSE.printf(msg=String.format("Duplication key [%s]", pnt.getKey()));
				assertTrue(msg, false);
			} else {
				unique.add(pnt.getKey());
			}
			
			sum += pnt.getSlow();
			sum += pnt.getFast();
			
			profit = UtilRand.getRandDbl(10.0*level)-.111;
			
			iter.addSurf(profit);

			Logger.TEST_VERBOSE.printf("%6d) %5d, %-5d ->%-3d = %5.2f", ++npp, pnt.getSlow(), pnt.getFast(), iter.getIndex(), profit);
//			
//			if( npp == 2 )
//				npp += 0;
		}
		
		ttl += cnt;

		double[] surf=iter.getSurf();
		for(int i=0; i<surf.length; i++  ){
			if( surf[i] == 0.0 ){
				Logger.TEST.printf(msg=String.format("Surface not initialize properly [level=%d][index=%d]!", level, i));
				assertTrue(msg, false);
			}
		}
		
		assertTrue(String.format("Cube iterator is wrong. Expected (sum=%d for %d nodes)", sum, ttl), sum == 62694 && ttl == 9);
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

		NCubeIterator<MACube2.MAPoint2, MACube2.MARegion> iter = mac.cubeRegion.iterator();
		iterList.add(iter);

		MAPoint2 step = iter.getStep(), size = iter.getSize(), left = iter.getLeft(), right = iter.getRight();
		Logger.TEST.printf("%dx%d/%d (%d-%d,%d-%d)", step.getSlow(), step.getFast(), size.getSlow()*size.getFast(), left.getSlow(), right.getSlow(), left.getFast(), right.getFast());

		for(npp=0; iter.hasNext(); ){
			++cnt;
			
			MACube2.MAPoint2 pnt = iter.next();
			
			if( unique.contains(pnt.getKey()) ){
				Logger.TEST_VERBOSE.printf(msg=String.format("Duplication key [%s]", pnt.getKey()));
				assertTrue(msg, false);
			} else {
				unique.add(pnt.getKey());
			}

			sum += pnt.getSlow();
			sum += pnt.getFast();
			
			profit = UtilRand.getRandDbl(10.0*level)-.111;
			
			iter.addSurf(profit);
			
			Logger.TEST_VERBOSE.printf("%6d) %5d, %-5d ->%-3d = %5.2f", ++npp, pnt.getSlow(), pnt.getFast(), iter.getIndex(), profit);
		}
		
		ttl += cnt;
		
		double[] surf=iter.getSurf();
		for(int i=0; i<surf.length; i++  ){
			if( surf[i] == 0.0 ){
				Logger.TEST.printf(msg=String.format("Surface not initialize properly [level=%d][index=%d]!", level, i));
				assertTrue(msg, false);
			}
		}
		
		assertTrue(String.format("Cube iterator is wrong. Expected (sum=%d for %d nodes)", sum, ttl), sum == 783000 && ttl == 100);
	}
	

	@Test
	public void breakCube103() throws RuntimeException {
		int cnt=0, sum=0, ttl=0, level=1;
		double profit;

		unique.clear();
		
		Logger.TEST_VERBOSE.isOn = Logger_TEST_VERBOSE_isOn;
		Logger.TEST.isOn = Logger_TEST_isOn;

		mac.setBreaks(10);
		Logger.TEST.printf("\nCube %d", mac.getBreaks());
		Logger.TEST.printf("-------------------------------------------------------------");

		NCubeIterator<MACube2.MAPoint2, MACube2.MARegion> iter = mac.cubeRegion.iterator();
		iterList.add(iter);

		MAPoint2 step = iter.getStep(), size = iter.getSize(), left = iter.getLeft(), right = iter.getRight();
		Logger.TEST.printf("%dx%d/%d (%d-%d,%d-%d)", step.getSlow(), step.getFast(), size.getSlow()*size.getFast(), left.getSlow(), right.getSlow(), left.getFast(), right.getFast());

		for(npp=0; iter.hasNext(); ){
			++cnt;
			
			MACube2.MAPoint2 pnt = iter.next();
			
			if( unique.contains(pnt.getKey()) ){
				Logger.TEST_VERBOSE.printf(msg=String.format("Duplication key [%s]", pnt.getKey()));
				assertTrue(msg, false);
			} else {
				unique.add(pnt.getKey());
			}
			
			sum += pnt.getSlow();
			sum += pnt.getFast();
			
			profit = UtilRand.getRandDbl(10.0*level)-.111;
			
			if( iter.getSurfValue() != 0.0 ){
				Logger.TEST_VERBOSE.printf(msg=String.format("Index duplication [->%d]", iter.getIndex()));
				assertTrue(msg, false);
			}

			iter.addSurf(profit);
			
			Logger.TEST_VERBOSE.printf("%6d) %5d, %-5d ->%-3d = %5.2f", ++npp, pnt.getSlow(), pnt.getFast(), iter.getIndex(), profit);
			
			if( iter.isBreakable() ){
				if( cnt == 3 ){
					ttl += breakCube(pnt.clone(), iter.getStep(), String.format("%d", cnt), level+1);
					Logger.TEST.printf("%dx%d/%d (%d-%d,%d-%d)", step.getSlow(), step.getFast(), size.getSlow()*size.getFast(), left.getSlow(), right.getSlow(), left.getFast(), right.getFast());
				}
			}
		}
		
		ttl += cnt;

		Logger.TEST.printf("Total: %d", ttl);
		
		double[] surf=iter.getSurf();
		for(int i=0; i<surf.length; i++  ){
			if( surf[i] == 0.0 ){
				Logger.TEST.printf(msg=String.format("Surface not initialize properly [level=%d][index=%d]!", level, i));
				assertTrue(msg, false);
			}
		}
		
		assertTrue(String.format("Cube iterator is wrong. Expected (sum=%d for %d nodes)", sum, ttl), sum == 783000 && ttl == 304);
	}

	private int breakCube(MAPoint2 ppoint, MAPoint2 pstep, String prefix, int level) {
		int cnt=0, ttl=0;
		double profit;
		
		NCubeIterator<MACube2.MAPoint2, MACube2.MARegion> iter = ppoint.getSubRegion(pstep).iterator();

		MAPoint2 step = iter.getStep(), size = iter.getSize(), left = iter.getLeft(), right = iter.getRight();
		Logger.TEST.printf("%dx%d/%d (%d-%d,%d-%d)", step.getSlow(), step.getFast(), size.getSlow()*size.getFast(), left.getSlow(), right.getSlow(), left.getFast(), right.getFast());

		iterList.add(iter);

		while( iter.hasNext() ){
			++cnt;

			MACube2.MAPoint2 pnt = iter.next();
			if( unique.contains(pnt.getKey()) ){
				Logger.TEST_VERBOSE.printf(msg=String.format("Duplication key [%s]", pnt.getKey()));
				assertTrue(msg, false);
			} else {
				unique.add(pnt.getKey());
			}

			profit = UtilRand.getRandDbl(10.0*level)-.111;
			
			if( iter.getSurfValue() != 0.0 ){
				Logger.TEST_VERBOSE.printf(msg=String.format("Index duplication [->%d]", iter.getIndex()));
				assertTrue(msg, false);
			}
			
			iter.addSurf(profit);
			
			Logger.TEST_VERBOSE.printf("%6d) %5d, %-5d ->%-3d = %5.2f", ++npp, pnt.getSlow(), pnt.getFast(), iter.getIndex(), profit);
			
			if( iter.isBreakable() ){
				if( cnt == 3 ){
					ttl += breakCube(pnt.clone(), iter.getStep(), String.format("%s.%d", prefix, cnt), level+1);
					Logger.TEST.printf("%dx%d/%d (%d-%d,%d-%d)", step.getSlow(), step.getFast(), size.getSlow()*size.getFast(), left.getSlow(), right.getSlow(), left.getFast(), right.getFast());
				}
			}
//			if( npp == 100 )
//				npp += 0;
		}

		double[] surf=iter.getSurf();
		for(int i=0; i<surf.length; i++  ){
			if( surf[i] == 0.0 ){
				Logger.TEST.printf(msg=String.format("Surface not initialize properly [level=%d][index=%d]!", level, i));
				assertTrue(msg, false);
			}
		}
		
		return ttl+cnt;
	}

	
	public class MACube2 extends NCube<MACube2.MAPoint2, MACube2.MARegion> {
		
		public class MAPoint2 extends NCube<MAPoint2,MARegion>.NPoint {
			
			public MAPoint2() {
				super();
			}

			public final int getSlow(){
				return dim[0];
			}
			
			public final int getFast(){
				return dim[1];
			}
		}

		public class MARegion extends NCube<MAPoint2,MARegion>.NRegion {
		}

		@Override
		public MAPoint2 newPoint() {
			return new MAPoint2();
		}
		
		@Override
		public MARegion newRegion() {
			return new MARegion();
		}

		public MACube2() {
			super(2);
		}

	}

}
