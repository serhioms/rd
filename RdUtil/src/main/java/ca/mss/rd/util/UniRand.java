package ca.mss.rd.util;

import java.util.Iterator;

/*W
 * Generate sequence of unique random numbers from 0 to N-1
 * 
 */
public class UniRand implements Iterator<Integer> {
	
	final private int[] ari;
	private int lastIndex;

	public UniRand(int n) {
		super();
		ari = new int[n];
		lastIndex = ari.length;
		for(int i=0; i<lastIndex; i++){
			ari[i] = i;
		}
	}

	final public int getSize(){
		return ari.length;
	}

	@Override
	public void remove() {
	}

	final public Iterator<Integer> iterator(){
		lastIndex = ari.length;
		return this;
	}
	
	@Override
	final public boolean hasNext() {
		return lastIndex > 0;
	}

	@Override
	final public Integer next() {
		int nextIndex = UtilRand.getRandInt(lastIndex);
		
//		int next = ari[nextIndex];
//		ari[nextIndex] = ari[--lastIndex];
//		ari[lastIndex] = next;

		// Additional messing does not really improve
		int swapIndex = UtilRand.getRandInt(--lastIndex, ari.length);
		int next = ari[nextIndex];
		ari[nextIndex] = ari[lastIndex];
		ari[lastIndex] = ari[swapIndex];
		ari[swapIndex] = next;

		return next;
		
	}
	
}
