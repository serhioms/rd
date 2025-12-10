package ca.mss.rd.util.model;

import java.util.Comparator;

public interface Sortable<T extends Number> extends Comparable<T>, Comparator<T> {
	
	public static final String NOT_IMPLEMENTED = "Not implemented";
	
	public T sortby();
}
