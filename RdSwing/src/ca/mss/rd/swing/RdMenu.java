package ca.mss.rd.swing;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.KeyStroke;

public class RdMenu extends RdMetadata {
	
	private static int itemcounter = 0;
	
	final private RdMenuModel model;
	
	public RdMenu(String ident, RdMenuModel model){
		super(ident, model.getProps().get(ident));
		this.model = model;
	}
	
	final public String getLabel(){
		return props==null? ident: getProperty("Label");
	}
	
	final public int getAccelerator(){
		if( props != null && props.get("Accelerator") != null )
			return Integer.parseInt(props.get("Accelerator"));
		else
			return KeyEvent.VK_1+(itemcounter++);
	}

	final public Icon getIcon(){
		return props==null? null: super.getIcon(props.get("Icon"));
	}

	final public int getMnemonic(){
		return props==null? -1: props.get("Mnemonic")==null? -1: Integer.parseInt(props.get("Mnemonic"));
	}
	
	final public String getDescription(){
		return getProperty("Description");
	}
	
	final public boolean isItem(){
		return props==null? true: "Item".equals(props.get("Type"));
	}
	
	final public boolean isMenu(){
		return props==null? false: "Menu".equals(props.get("Type"));
	}

	
	final static public void createMenu(JMenuBar menuBar, RdMenuModel model) {
		JMenu menu;
		String[] root = model.getRoot();
		for(int i=0; i<root.length; i++){
			RdMenu md = new RdMenu(root[i], model);
			menu = new JMenu( md.getLabel() != null? md.getLabel():md.getIdent());
			if( md.getMnemonic() >= 0 )
				menu.setMnemonic(md.getMnemonic());
			if( md.getDescription() != null )
				menu.getAccessibleContext().setAccessibleDescription(md.getDescription());
			menuBar.add(menu);

			md.createSubmenu(menu);
		}
		
		// DEMO
		JMenu submenu;
		JMenuItem subitem;
		JRadioButtonMenuItem rbMenuItem;
		JCheckBoxMenuItem cbMenuItem;
		menu = new JMenu("Demo");
		menu.setMnemonic(KeyEvent.VK_D);
		menu.getAccessibleContext().setAccessibleDescription("The only menu in this program that has menu items");
		menuBar.add(menu);

		// a group of JMenuItems
		subitem = new JMenuItem("A text-only menu item", KeyEvent.VK_T);
		subitem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, ActionEvent.ALT_MASK));
		subitem.getAccessibleContext().setAccessibleDescription("This doesn't really do anything");
		menu.add(subitem);

		subitem = new JMenuItem("Both text and icon", new ImageIcon("images/demo.png"));
		subitem.setMnemonic(KeyEvent.VK_B);
		menu.add(subitem);

		subitem = new JMenuItem(new ImageIcon("images/demo.png"));
		subitem.setMnemonic(KeyEvent.VK_D);
		menu.add(subitem);
		
		// a group of radio button menu items
		menu.addSeparator();
		ButtonGroup group = new ButtonGroup();
		rbMenuItem = new JRadioButtonMenuItem("A radio button menu item");
		rbMenuItem.setSelected(true);
		rbMenuItem.setMnemonic(KeyEvent.VK_R);
		group.add(rbMenuItem);
		menu.add(rbMenuItem);

		rbMenuItem = new JRadioButtonMenuItem("Another one");
		rbMenuItem.setMnemonic(KeyEvent.VK_O);
		group.add(rbMenuItem);
		menu.add(rbMenuItem);

		// a group of check box menu items
		menu.addSeparator();
		cbMenuItem = new JCheckBoxMenuItem("A check box menu item");
		cbMenuItem.setMnemonic(KeyEvent.VK_C);
		menu.add(cbMenuItem);

		cbMenuItem = new JCheckBoxMenuItem("Another one");
		cbMenuItem.setMnemonic(KeyEvent.VK_H);
		menu.add(cbMenuItem);

		// a submenu
		menu.addSeparator();
		submenu = new JMenu("A submenu");
		submenu.setMnemonic(KeyEvent.VK_S);

		subitem = new JMenuItem("An item in the submenu");
		subitem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_2, ActionEvent.ALT_MASK));
		submenu.add(subitem);

		subitem = new JMenuItem("Another item");
		submenu.add(subitem);
		menu.add(submenu);
	}

	final private void createSubmenu(JMenu menu){
		JMenu submenu;
		JMenuItem subitem;
	
		String[] subitems = model.getSubmenu().get(ident);
		for(int j=0; j<subitems.length; j++){
			RdMenu md = new RdMenu(subitems[j], model);
			if( md.isItem() ){
				if( md.getIcon() != null )
					subitem = new JMenuItem(md.getLabel() != null? md.getLabel():md.getIdent(), md.getIcon());
				else
					subitem = new JMenuItem(md.getLabel() != null? md.getLabel():md.getIdent());

				if( model.getActionListener() != null )
					subitem.addActionListener(model.getActionListener());

				subitem.setName(md.getIdent());
				subitem.setAccelerator(KeyStroke.getKeyStroke(md.getAccelerator(), ActionEvent.ALT_MASK));

				if( md.getMnemonic() >= 0 )
					subitem.setMnemonic(md.getMnemonic());

				if( md.getDescription() != null )
					subitem.getAccessibleContext().setAccessibleDescription(md.getDescription());

				menu.add(subitem);
			}
			if( md.isMenu() ){
				menu.addSeparator();
				menu.setName(md.getIdent());
				submenu = new JMenu(md.getLabel());
				if( md.getMnemonic() >= 0 )
					submenu.setMnemonic(md.getMnemonic());
				md.createSubmenu(submenu);
				menu.add(submenu);
			}
		}
	}
	
}
