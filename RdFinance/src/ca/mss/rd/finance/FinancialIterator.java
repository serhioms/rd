package ca.mss.rd.finance;

import java.util.Date;
import java.util.Iterator;

abstract public class FinancialIterator<E> implements Iterator<E> {

	abstract public Date nextDate();
	
	public boolean hasNext() {
		return true;
	}

	public void remove() {}
	
}
