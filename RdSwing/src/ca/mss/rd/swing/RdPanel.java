package ca.mss.rd.swing;

import java.awt.BorderLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JPanel;

public class RdPanel extends JPanel implements PropertyChangeListener {

	final static public String module = RdPanel.class.getName();
	final public static long serialVersionUID = module.hashCode();

	/*
	 * Must be overridden 
	 */
	public void init() {
		setLayout(new BorderLayout());
		add(RdWidgets.createPanelBoxX(RdWidgets.createButtons(5, 1)), BorderLayout.NORTH);
		add(RdWidgets.createPanelBoxY(RdWidgets.createButtons(5, 10)), BorderLayout.WEST);
		add(RdWidgets.createPanelBorder(RdWidgets.createButtons(5, 20)), BorderLayout.CENTER);
		add(RdWidgets.createPanelBoxY(RdWidgets.createButtons(5, 30)), BorderLayout.EAST);
		add(RdWidgets.createPanelGrid(2, 3, RdWidgets.createButtons(6, 40)), BorderLayout.SOUTH);
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		final String eventName = evt.getPropertyName();
		final String sourceClass = evt.getSource().getClass().getSimpleName();
		final Object oldval = evt.getOldValue();
		final Object newval = evt.getNewValue();

		System.out.println(String.format("%s: Unhandled event [event=%s][from=%s][old=%s][new=%s]", module, eventName, sourceClass, oldval, newval));
	}

	
}
