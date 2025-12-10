package ca.mss.rd.loto.generator;

import ca.mss.rd.util.UtilMisc;
import ca.mss.rd.util.UtilRand;


public class LetoPool49 {

	final static int DEF_SIZE = 49;
	final static int DEF_RAND = 3;

	final public int[] pool;
	
	public LetoPool49() {
		this(DEF_SIZE);
	}

	public LetoPool49(int[] pool) {
		this.pool = UtilMisc.cloneInt(pool);
	} 
	
	public LetoPool49(int size) {
		this.pool = UtilMisc.toInt(size);
	} 
	
	public LetoPool49(int left, int right) {
		this.pool = UtilMisc.toInt(left, right);
	} 
	
	public void randomize() {
		randomize(DEF_RAND);
	} 
	
	public void randomize(int r) {
		randomize(pool, r);
	}
	
	public void randomize(int[] pool, int r) {
		int k;
		
		do {
			for(int i=0; i<pool.length; i++){
				while( (k=UtilRand.getRandInt(pool.length)) == i );
				
				int kval = pool[k];
				pool[k] = pool[i];
				pool[i] = kval;
			}
		} while( r-- > 0);
	}

}
