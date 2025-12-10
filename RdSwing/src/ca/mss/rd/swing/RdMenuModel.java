package ca.mss.rd.swing;

import java.awt.event.ActionListener;
import java.util.Map;

public interface RdMenuModel {

	public String[] getRoot();
	public Map<String,String[]> getSubmenu();
	public Map<String,Map<String,String>> getProps();

	public ActionListener getActionListener();

}
