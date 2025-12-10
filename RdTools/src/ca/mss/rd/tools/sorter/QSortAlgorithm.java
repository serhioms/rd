package ca.mss.rd.tools.sorter;

import java.util.List;

import ca.mss.rd.util.UtilSort;
/*
 * @(#)QSortAlgorithm.java	1.6f 95/01/31 James Gosling
 *
 * Copyright (c) 1994-1995 Sun Microsystems, Inc. All Rights Reserved.
 *
 * Permission to use, copy, modify, and distribute this software
 * and its documentation for NON-COMMERCIAL or COMMERCIAL purposes and
 * without fee is hereby granted. 
 * Please refer to the file http://java.sun.com/copy_trademarks.html
 * for further important copyright and trademark information and to
 * http://java.sun.com/licensing.html for further important licensing
 * information for the Java (tm) Technology.
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
import ca.mss.rd.util.model.Sortable;

/**
 * A quick sort demonstration algorithm
 * SortAlgorithm.java, Thu Oct 27 10:32:35 1994
 *
 * @author James Gosling
 * @version 	1.6f, 31 Jan 1995
 */
/**
 * 19 Feb 1996: Fixed to avoid infinite loop discoved by Paul Haeberli.
 *              Misbehaviour expressed when the pivot element was not unique.
 *              -Jason Harrison
 *
 * 21 Jun 1996: Modified code based on comments from Paul Haeberli, and
 *              Peter Schweizer (Peter.Schweizer@mni.fh-giessen.de).  
 *              Used Daeron Meyer's (daeron@geom.umn.edu) code for the
 *              new pivoting code. - Jason Harrison
 *
 * 09 Jan 1998: Another set of bug fixes by Thomas Everth (everth@wave.co.nz)
 *              and John Brzustowski (jbrzusto@gpu.srv.ualberta.ca).
 * 
 * May 2011
 * Slightly modified by Sergey Moskovskiy 
 * against original version from here:
 * http://www.cs.ubc.ca/~harrison/Java/QSortAlgorithm.java.html
 * 
 */

class QSortAlgorithm<T extends Sortable<Long>> {

	final public void sort(List<T> contList, boolean isDescending) {
		if( isDescending ){
			qSortDesc(contList, 0, contList.size() - 1);
		} else {
			qSortAsc(contList, 0, contList.size() - 1);
		}
	}

	final private void qSortDesc(List<T> a, int lo0, int hi0) {
		int lo = lo0;
		int hi = hi0;

		if (lo >= hi) {
			return;
		} else if (lo == hi - 1) {
			/*
			 * sort a two element contList by swapping if necessary
			 */
			if (a.get(lo).sortby() < a.get(hi).sortby()) {
				UtilSort.swap(a, lo, hi);
			}
			return;
		}

		/*
		 * Pick a pivot and move it out of the way
		 */
		long pivot = a.get((lo + hi) / 2).sortby();
		UtilSort.swap(a, (lo + hi) / 2, hi);

		while (lo < hi) {
			/*
			 * Search forward from a[lo] until an element is found that is
			 * greater than the pivot or lo >= hi
			 */
			while (a.get(lo).sortby() >= pivot && lo < hi) {
				lo++;
			}

			/*
			 * Search backward from a[hi] until element is found that is less
			 * than the pivot, or lo >= hi
			 */
			while (pivot >= a.get(hi).sortby() && lo < hi) {
				hi--;
			}

			/*
			 * Swap elements a[lo] and a[hi]
			 */
			if (lo < hi) {
				UtilSort.swap(a, lo, hi);
			}

		}

		/*
		 * Put the median in the "center" of the contList
		 */
		UtilSort.swap(a, hi0, hi);

		/*
		 * Recursive calls, elements a[lo0] to a[lo-1] are less than or equal to
		 * pivot, elements a[hi+1] to a[hi0] are greater than pivot.
		 */
		qSortDesc(a, lo0, lo - 1);
		qSortDesc(a, hi + 1, hi0);
	}

	final private void qSortAsc(List<T> a, int lo0, int hi0) {
		int lo = lo0;
		int hi = hi0;

		if (lo >= hi) {
			return;
		} else if (lo == hi - 1) {
			/*
			 * sort a two element contList by swapping if necessary
			 */
			if (a.get(lo).sortby() > a.get(hi).sortby()) {
				UtilSort.swap(a, lo, hi);
			}
			return;
		}

		/*
		 * Pick a pivot and move it out of the way
		 */
		long pivot = a.get((lo + hi) / 2).sortby();
		UtilSort.swap(a, (lo + hi) / 2, hi);

		while (lo < hi) {
			/*
			 * Search forward from a[lo] until an element is found that is
			 * greater than the pivot or lo >= hi
			 */
			while (a.get(lo).sortby() <= pivot && lo < hi) {
				lo++;
			}

			/*
			 * Search backward from a[hi] until element is found that is less
			 * than the pivot, or lo >= hi
			 */
			while (pivot <= a.get(hi).sortby() && lo < hi) {
				hi--;
			}

			/*
			 * Swap elements a[lo] and a[hi]
			 */
			if (lo < hi) {
				UtilSort.swap(a, lo, hi);
			}

		}

		/*
		 * Put the median in the "center" of the contList
		 */
		UtilSort.swap(a, hi0, hi);

		/*
		 * Recursive calls, elements a[lo0] to a[lo-1] are less than or equal to
		 * pivot, elements a[hi+1] to a[hi0] are greater than pivot.
		 */
		qSortAsc(a, lo0, lo - 1);
		qSortAsc(a, hi + 1, hi0);
	}

}

