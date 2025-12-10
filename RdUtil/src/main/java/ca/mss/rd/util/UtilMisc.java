package ca.mss.rd.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

final public class UtilMisc {

	static public Map<String, String[]> toMapArray(String... args) {
		Map<String, String[]> map = new HashMap<String, String[]>();

		for (int i = 0; i < args.length; i += 2) {
			map.put(args[i + 0].toString(), args[i + 1].toString().split(","));
		}

		return map;
	}

	static public Map<String, Map<String, String>> toMapMap(String... args) {
		Map<String, Map<String, String>> map = new HashMap<String, Map<String, String>>();

		for (int i = 0; i < args.length; i += 2) {
			String[] kv = args[i + 1].toString().split(",");
			Map<String, String> kvmap = new HashMap<String, String>();
			map.put(args[i + 0].toString(), kvmap);
			for (int j = 0; j < kv.length; j++) {
				String[] kva = kv[j].split("=");
				if (kva.length > 1)
					kvmap.put(kva[0], kva[1]);
				else
					kvmap.put(kva[0], null);
			}
		}

		return map;
	}

	static public <T> String toString(T[] arr, String separator, String flex) {
		String result = "";

		for (int i = 0; i < arr.length; i++) {
			if (i > 0) {
				result += separator;
			}
			result += flex + arr[i].toString() + flex;
		}

		return result;
	}

	static public <T> String toString(Vector<T> v, String separator, String flex) {
		String result = "";

		for (int i = 0, max = v.size(); i < max; i++) {
			if (i > 0) {
				result += separator;
			}
			result += flex + v.get(i).toString() + flex;
		}

		return result;
	}

	static public <T> String toString(Iterator<T> iter, String separator, String flex) {
		String result = "";

		for (int i = 0; iter.hasNext(); i++) {
			if (i > 0) {
				result += separator;
			}
			result += flex + iter.next().toString() + flex;
		}

		return result;
	}

	static public <T> Set<T> toSet(T... list) {
		Set<T> set = new LinkedHashSet<T>();

		for (T item : list) {
			set.add(item);
		}

		return set;
	}

	static public Set<Integer> toSet(int[] a) {
		return toSet(a, 0, a.length);
	}

	static public Set<Integer> toSet(int[] a, int n) {
		return toSet(a, 0, n);
	}

	static public Set<Integer> toSet(int[] vals, int b, int e) {
		Set<Integer> set = new HashSet<Integer>();

		for(int i=b; i<e; i++) {
			set.add(vals[i]);
		}

		return set;
	}

	static public <T> Set<T> toSetOrdered(T... arg) {
		Set<T> set = new LinkedHashSet<T>();

		for (T item : arg) {
			set.add(item);
		}

		return set;
	}

	static public <T> Vector<T> toVector(T... arg) {
		final Vector<T> vec = new Vector<T>();

		for (int i = 0; i < arg.length; i++) {
			vec.add(arg[i]);
		}

		return vec;
	}

	static public <T> List<T> toList(T... arg) {
		final List<T> list = new ArrayList<T>();

		for (int i = 0; i < arg.length; i++) {
			list.add(arg[i]);
		}

		return list;
	}

	static public <T> Map<T, T> toMap(T... args) {
		Map<T, T> map = new HashMap<T, T>();

		for (int i = 0; i < args.length; i += 2) {
			map.put(args[i + 0], args[i + 1]);
		}

		return map;
	}

	static public <T> Map<T, T> toMapOrdered(T... arg) {
		Map<T, T> map = new LinkedHashMap<T, T>();

		for (int i = 0; i < arg.length; i += 2) {
			map.put(arg[i + 0], arg[i + 1]);
		}

		return map;
	}

	final static public <T> boolean isEmpty(T o) {
		return o == null;
	}

	final static public boolean isEmpty(String s) {
		return s == null || s.trim().length() == 0;
	}

	final static public boolean isSameOrEmpty(String s1, String s2) {
		if (isEmpty(s1))
			if (isEmpty(s2))
				return true;
			else
				return false;

		if (isEmpty(s2))
			return false;
		else if (s1.equals(s2))
			return true;
		else
			return false;
	}

	final static public <T> boolean isEmpty(Set<T> s) {
		return s == null || s.size() == 0;
	}

	final static public <T> boolean isEmpty(List<T> list) {
		return list == null || list.size() == 0;
	}

	final static public boolean isTrue(String s, boolean defaultValue) {
		if (!UtilMisc.isEmpty(s)) {
			if ("true".equalsIgnoreCase(s))
				return true;
			else if ("false".equalsIgnoreCase(s))
				return false;
		}
		return defaultValue;
	}

	final static public <T> String emptyIfNull(T o) {
		return o == null ? "" : o.toString();
	}

	final static public <T> String emptyIfNull(T o, T r) {
		return o == null ? "" : emptyIfNull(r);
	}

	public static <T> Iterable<T> iterable(final Enumeration<T> en) {
		if (en == null) {
			throw new NullPointerException();
		}
		return new Iterable<T>() {
			public Iterator<T> iterator() {
				return new Iterator<T>() {
					public boolean hasNext() {
						return en.hasMoreElements();
					}

					public T next() {
						return en.nextElement();
					}

					public void remove() {
						throw new UnsupportedOperationException();
					}
				};
			}
		};
	}

	public static int[] toIndexInt(int n) {
		int[] index = new int[n];
		for (int i = 0; i < n; i++) {
			index[i] = i;
		}
		return index;
	}

	public static int[] toInt(String[] sarr) {
		int[] arr = new int[sarr.length];
		for (int i=0; i < sarr.length; i++) {
			arr[i] = Integer.parseInt(sarr[i].trim());
		}
		return arr;
	}

	public static int[] toInt(int e) {
		int[] index = new int[e];
		for (int i=0; i<e; i++) {
			index[i] = i+1;
		}
		return index;
	}

	public static int[] toInt(int b, int e) {
		int[] index = new int[e-b+1];
		for (int i=b; i<=e; i++) {
			index[i-b] = i+1;
		}
		return index;
	}

	public static List<int[]> toListInt(int[][] a) {
		List<int[]> clone = new ArrayList<int[]>();
		for (int i=0; i<a.length; i++) {
			clone.add(a[i]);
		}
		return clone;
	}

	public static Integer[] cloneInteger(int[] a) {
		Integer[] clone = new Integer[a.length];
		for (int i = 0; i < a.length; i++) {
			clone[i] = a[i];
		}
		return clone;
	}

	public static int[] cloneInt(int[] a) {
		return cloneInt(a, 0, a.length);
	}

	public static int[] cloneInt(int[] a, int e) {
		return cloneInt(a, 0, e);
	}

	public static int[] cloneInt(int[] a, int b, int e) {
		int[] clone = new int[e-b];
		for (int i=0,j=b; j<e; i++,j++) {
			clone[i] = a[j];
		}
		return clone;
	}

	public static boolean equals(int[] a, int[] b) {
		for(int i=0; i<a.length; i++){
			if( a[i] != b[i] )
				return false;
		}
		return true;
	}
	
	public static String toString(int[] t) {
		return toString(t, "%d");
	}

	public static String toString(int[] t, String fm) {
		String s = null, fm2 = "%s," + fm;
		for (int i = 0; i < t.length; i++) {
			if (i == 0)
				s = String.format(fm, t[i]);
			else
				s = String.format(fm2, s, t[i]);
		}
		return s;
	}
	
	public static String toString(double[] t, String fm) {
		String s = null, fm2 = "%s," + fm;
		for (int i = 0; i < t.length; i++) {
			if (i == 0)
				s = String.format(fm, t[i]);
			else
				s = String.format(fm2, s, t[i]);
		}
		return s;
	}
	
	public static String toSortedCloneString(int[] a, String fm) {
		return toString(UtilSort.sortArray(cloneInt(a)), fm);
	}
	
	public static String toSortedString(int[] a, String fm) {
		return toString(UtilSort.sortArray(a), fm);
	}
	
	public static String toSortedString(int[] a) {
		return toSortedString(a, "%d");
	}
	
	public static String toSortedCloneString(int[] a, int n) {
		return toSortedString(cloneInt(a, n));
	}

	public static int[] cloneSort(int[] a, int n) {
		return UtilSort.sortArray(cloneInt(a, n));
	}

	public static int[] cloneSort(int[] a) {
		return cloneSort(a, a.length);
	}

	public static BigDecimal[] toBigDecimal(int n) {
		BigDecimal[] index = new BigDecimal[n];
		for (int i = 0; i < n; i++) {
			index[i] = new BigDecimal(0);
		}
		return index;
	}

	public static double[] toDouble(int n) {
		double[] index = new double[n];
		for (int i = 0; i < n; i++) {
			index[i] = 0.0;
		}
		return index;
	}
	
	public static void copy(int[] a, Set<Integer> s) {
		s.clear();
		for(int v: a) {
			s.add(v);
		}
	}

	public static <K,V> String toString(Map<K, V> map) {
		String result = "";
		for(Map.Entry<K, V> item: map.entrySet()){
			result += String.format("[%s=%s]", item.getKey().toString(), item.getValue().toString());
		}
		return result;
	}

	public static <V> String toString(List<V> map) {
		if( map != null )
			return map.toString();
		else
			return "[]";
	}
}
