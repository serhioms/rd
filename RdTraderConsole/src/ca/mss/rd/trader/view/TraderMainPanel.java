package ca.mss.rd.trader.view;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTabbedPane;

import ca.mss.rd.swing.RdAppPanel;
import ca.mss.rd.swing.RdMenu;
import ca.mss.rd.swing.RdMenuModel;
import ca.mss.rd.swing.RdTab;
import ca.mss.rd.swing.RdTabModel;
import ca.mss.rd.trader.FxTConfig;

public class TraderMainPanel extends RdAppPanel implements ActionListener {

	final static public String module = TraderMainPanel.class.getSimpleName();
	final public static long serialVersionUID = module.hashCode();

	private JMenuBar menuBar;
	private String selectedItemName;

	private JTabbedPane tabbedPane;

	public TraderMainPanel() {
		super();
	}

	@Override
	public void setMenuBar(JMenuBar menuBar) {
		this.menuBar = menuBar;
	}

	@Override
	public void init() {
		
		// components in a GridLayout are stretched to fit space available
		setLayout(new GridLayout());
		//setBackground(java.awt.Color.BLACK);
		
		tabbedPane = new JTabbedPane();
		add(tabbedPane);
		
		RdMenu.createMenu(menuBar, new RdMenuModel() {

			@Override
			public String[] getRoot() {
				return FxTConfig.MENU_ROOT;
			}

			@Override
			public Map<String, String[]> getSubmenu() {
				return FxTConfig.MENU_ITEMS;
			}

			@Override
			public Map<String, Map<String, String>> getProps() {
				return FxTConfig.MENU_PROPS;
			}

			@Override
			public ActionListener getActionListener() {
				return TraderMainPanel.this;
			}
			
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JMenuItem source = (JMenuItem) (e.getSource());
		
		selectedItemName = source.getName();

		RdTab.createTab(tabbedPane, new RdTabModel(){

			@Override
			public String getIdent() {
				return selectedItemName;
			}

			@Override
			public Map<String,Map<String,String>> getProps() {
				return FxTConfig.TAB_PROPS;
			}
		});
	}
}
