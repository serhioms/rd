package ca.mss.rd.util.model;

import java.util.Comparator;

// public static <T> void sort(List<T> list, Comparator<? super T> c)

public class SortableComparator<T extends Sortable<? super Number>> implements Comparator<T> {

	final public boolean isAscending;
	
	public SortableComparator(boolean isAscending) {
		this.isAscending = isAscending;
	}

	@Override
	public int compare(T a, T b) {
		if( isAscending )
			return a.sortby().doubleValue() > b.sortby().doubleValue()? -1 : 1;
		else
			return a.sortby().doubleValue() < b.sortby().doubleValue()? -1 : 1;
	}

}
