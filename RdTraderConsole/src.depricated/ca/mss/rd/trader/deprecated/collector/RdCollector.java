package ca.mss.rd.trader.deprecated.collector;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Set;

import ca.mss.rd.util.runnable.RdRunnableSecundomer;

@Deprecated
abstract public class RdCollector extends RdRunnableSecundomer implements PropertyChangeListener {

	
	public RdCollector(String name) {
		super(name);
	}
	
	abstract public void restart();
	abstract public void addQuote(Set<String> quotes);

	@Override
	public void afterWork() {
	}

	/*
     * Property change support
     */
    final protected PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    final public void addPropertyChangeListener(PropertyChangeListener listener) {
    	pcs.addPropertyChangeListener(listener);
    }
}
