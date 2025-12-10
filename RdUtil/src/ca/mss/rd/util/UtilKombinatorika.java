package ca.mss.rd.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ca.mss.rd.util.model.SubPool;

import com.objectwave.utility.Combinations;
import com.objectwave.utility.CombinatoricException;

public class UtilKombinatorika {

	final static public String module = UtilKombinatorika.class.getName();
	final static public long serialVersionUID = module.hashCode();

	public static Iterator<ArrayList<SubPool>> getBinomialIterator(SubPool[] n, int k) {

		if (!((n.length > k) && k > 0 && n.length > 0)) {
			throw new RuntimeException(String.format("Binom N(%d) must be greater or equal k(%d) and greater tnen ZERO", n.length, k));
		}

		try {
			Iterable<ArrayList<SubPool>> iterable = UtilMisc.iterable(new Combinations<SubPool>(n, k));
			return iterable.iterator();
		} catch (CombinatoricException e) {
			throw new RuntimeException(String.format("Can not build binomial iterator for %d/%d", n, k), e);
		}
	}

	public static Iterator<Integer[]> getFactorialIterator(int n, int k) {

		if (!(k > 0 && n > 0)) {
			throw new RuntimeException(String.format("Factorial N(%d) and k(%d) must be greater then ZERO", n, k));
		}

		List<Integer[]> factorial = new ArrayList<Integer[]>();
		
		int[] kval = UtilMisc.toIndexInt(k);
		int ni;
		int[] nval = new int[n];
		do {

			Integer[] clone = new Integer[n];
			for (int i=0; i < n; i++) {
				clone[i] = kval[nval[i]];
			}
			factorial.add(clone);
			
			ni = 1;
			for (int i = n - 1; i >= 0; i--) {
				if (ni == 0)
					break;

				nval[i] += ni;
				ni = 0;

				if (nval[i] == k) {
					ni = 1;
					nval[i] = 0;
				}
			}
		} while (ni != 1); // Call this method iteratively until a carry is left over

		return factorial.iterator();
	}

}
