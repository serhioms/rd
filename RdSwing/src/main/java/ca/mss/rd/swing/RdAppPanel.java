package ca.mss.rd.swing;

import javax.swing.JMenuBar;
import javax.swing.JPanel;

abstract public class RdAppPanel extends JPanel {
	
	final static public String module = RdAppPanel.class.getName();
	final static public long serialVersionUID = module.hashCode();

	abstract public void setMenuBar(JMenuBar menuBar);
	abstract public void init();

}
