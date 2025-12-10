package ca.mss.rd.tools.sorter;

import java.util.List;

import ca.mss.rd.util.UtilSort;
import ca.mss.rd.util.model.Sortable;

/*
 * @(#)QSortAlgorithm.java      1.3   29 Feb 1996 James Gosling
 *
 * Copyright (c) 1994-1996 Sun Microsystems, Inc. All Rights Reserved.
 *
 * Permission to use, copy, modify, and distribute this software
 * and its documentation for NON-COMMERCIAL or COMMERCIAL purposes and
 * without fee is hereby granted. 
 * Please refer to the file http://www.javasoft.com/copy_trademarks.html
 * for further important copyright and trademark information and to
 * http://www.javasoft.com/licensing.html for further important
 * licensing information for the Java (tm) Technology.
 * 
 * SUN MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF
 * THE SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED
 * TO THE IMPLIED WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE, OR NON-INFRINGEMENT. SUN SHALL NOT BE LIABLE FOR
 * ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR
 * DISTRIBUTING THIS SOFTWARE OR ITS DERIVATIVES.
 * 
 * THIS SOFTWARE IS NOT DESIGNED OR INTENDED FOR USE OR RESALE AS ON-LINE
 * CONTROL EQUIPMENT IN HAZARDOUS ENVIRONMENTS REQUIRING FAIL-SAFE
 * PERFORMANCE, SUCH AS IN THE OPERATION OF NUCLEAR FACILITIES, AIRCRAFT
 * NAVIGATION OR COMMUNICATION SYSTEMS, AIR TRAFFIC CONTROL, DIRECT LIFE
 * SUPPORT MACHINES, OR WEAPONS SYSTEMS, IN WHICH THE FAILURE OF THE
 * SOFTWARE COULD LEAD DIRECTLY TO DEATH, PERSONAL INJURY, OR SEVERE
 * PHYSICAL OR ENVIRONMENTAL DAMAGE ("HIGH RISK ACTIVITIES").  SUN
 * SPECIFICALLY DISCLAIMS ANY EXPRESS OR IMPLIED WARRANTY OF FITNESS FOR
 * HIGH RISK ACTIVITIES.
 */

/**
 * A quick sort demonstration algorithm
 * SortAlgorithm.java
 *
 * @author James Gosling
 * @author Kevin A. Smith
 * @version     @(#)QSortAlgorithm.java 1.3, 29 Feb 1996
 * extended with TriMedian and InsertionSort by Denis Ahrens
 * with all the tips from Robert Sedgewick (Algorithms in C++).
 * It uses TriMedian and InsertionSort for lists shorts than 4.
 * <fuhrmann@cs.tu-berlin.de>
 * 
 * May 2011
 * Slightly modified by Sergey Moskovskiy 
 * against original version from here:
 * http://www.cs.ubc.ca/~harrison/Java/FastQSortAlgorithm.java.html
 * 
 */
public class FastQSortAlgorithm<T extends Sortable<Long>> {
	
	public void sort(List<T> contList, boolean isDescending) {
		if( isDescending ){
			fastQSortDesc(contList, 0, contList.size() - 1);
			fastQSortIterationDesc(contList, 0, contList.size() - 1);
		} else {
			fastQSortAsc(contList, 0, contList.size() - 1);
			fastQSortIterationAsc(contList, 0, contList.size() - 1);
		}
	}

	private void fastQSortAsc(List<T> a, int l, int r) {
		int M = 4;
		int i;
		int j;
		long v;
		if ((r - l) > M) {
			i = (r + l) / 2;
			if (a.get(l).sortby() > a.get(i).sortby())
				UtilSort.swap(a, l, i);
			// Tri-Median Methode!
			if (a.get(l).sortby() > a.get(r).sortby())
				UtilSort.swap(a,  l, r);
			if (a.get(i).sortby() > a.get(r).sortby())
				UtilSort.swap(a, i, r);
			j = r - 1;
			UtilSort.swap(a,  i, j);
			i = l;
			v = a.get(j).sortby();
			for (;;) {
				while (a.get(++i).sortby() < v)
					;
				while (a.get(--j).sortby() > v)
					;
				if (j < i)
					break;
				UtilSort.swap(a, i, j);
			}
			UtilSort.swap(a, i, r - 1);
			fastQSortAsc(a, l, j);
			fastQSortAsc(a, i + 1, r);
		}
	}

	private void fastQSortIterationAsc(List<T> a, int lo0, int hi0) {
		int i;
		int j;
		long v;
		for (i = lo0 + 1; i <= hi0; i++) {
			v = a.get(i).sortby();
			j = i;
			while ((j > lo0) && (a.get(j - 1).sortby() > v)) {
				UtilSort.swap(a, j, j - 1);
				j--;
			}
			//SortUtil.swap(a, j, i);
		}
	}
	
	private void fastQSortDesc(List<T> a, int l, int r) {
		int M = 4;
		int i;
		int j;
		long v;
		if ((r - l) > M) {
			i = (r + l) / 2;
			if (a.get(l).sortby() < a.get(i).sortby())
				UtilSort.swap(a, l, i);
			// Tri-Median Methode!
			if (a.get(l).sortby() < a.get(r).sortby())
				UtilSort.swap(a,  l, r);
			if (a.get(i).sortby() < a.get(r).sortby())
				UtilSort.swap(a, i, r);
			j = r - 1;
			UtilSort.swap(a,  i, j);
			i = l;
			v = a.get(j).sortby();
			for (;;) {
				while (a.get(++i).sortby() > v)
					;
				while (a.get(--j).sortby() < v)
					;
				if (j < i)
					break;
				UtilSort.swap(a, i, j);
			}
			UtilSort.swap(a, i, r - 1);
			fastQSortDesc(a, l, j);
			fastQSortDesc(a, i + 1, r);
		}
	}

	private void fastQSortIterationDesc(List<T> a, int lo0, int hi0) {
		int i;
		int j;
		long v;
		for (i = lo0 + 1; i <= hi0; i++) {
			v = a.get(i).sortby();
			j = i;
			while ((j > lo0) && (a.get(j - 1).sortby() < v)) {
				UtilSort.swap(a, j, j - 1);
				j--;
			}
			//SortUtil.swap(a, j, i);
		}
	}
}
