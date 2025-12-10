package ca.mss.rd.math;

import java.util.Iterator;

import ca.mss.rd.parser.impl.Getter;

@Deprecated
public interface FXSourceIterator extends Iterator<Getter> {

	public void reinitialize();
	
}
