/**
 * 
 */
package ca.mss.rd.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import ca.mss.rd.util.model.Sortable;
import ca.mss.rd.util.model.SortableComparator;

/**
 * @author smoskov
 *
 */
public class UtilSort {

	public static final boolean IS_DESCENDING = false;
	public static final boolean IS_ASCENDING = true;

	final static public <T> void swap(List<T> l, int i, int k){
		l.set(k, l.set(i, l.get(k)));
	}

	// Collections interface substitution 
	final static public <T> void sort(List<T> list, Comparator<? super T> cmpr) {
		Collections.sort(list, cmpr);
	}

	// Collections interface substitution 
	final static public <T extends Comparable<? super T>> void sortComparable(List<T> list){
		Collections.sort(list);
	}

	
	// Sortable interface extension
	final public static <T extends Sortable<? super Number>> void sortSortable(List<T> list){
		sortSortable(list, IS_ASCENDING);
	}

	final public static <T extends Sortable<? super Number>> void sortSortable(List<T> list, boolean isAscending){
		Collections.sort(list, new SortableComparator<T>(isAscending));
	}


	// Map sorter
	final public static <X extends Number, Z> Map<X,Z> sortNumber(Map<X,Z> map){
		
		Map<X,Z> sorted = new TreeMap<X,Z>(new Comparator<X>(){
			@Override
			public int compare(X a, X b) {
				return a.doubleValue() < b.doubleValue()? -1 : 1;
			}
		});
		
		sorted.putAll(map);
		
		return sorted;
	}
	
	final public static <X extends Comparable<String>, Z> Map<X,Z> sortComparable(Map<X,Z> map){
		
		Map<X,Z> sorted = new TreeMap<X,Z>(new Comparator<X>(){
			@Override
			public int compare(X a, X b) {
				return a.compareTo(b.toString());
			}
		});
		
		sorted.putAll(map);
		
		return sorted;
	}
	
	
	// Arrays interface substitution 
	final public static int[] sortArray(int[] a) {
		Arrays.sort(a);
		return a;
	}

	final public static Object[] sortArray(Object[] a) {
		Arrays.sort(a);
		return a;
	}
}
