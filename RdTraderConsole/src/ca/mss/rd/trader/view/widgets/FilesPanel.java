package ca.mss.rd.trader.view.widgets;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;

import ca.mss.rd.swing.RdPanel;
import ca.mss.rd.swing.RdWidgets;
import ca.mss.rd.trader.FxTConfig;
import ca.mss.rd.util.io.UtilFile;

public class FilesPanel extends RdPanel {

	final static public String module = FilesPanel.class.getName();
	final public static long serialVersionUID = module.hashCode();

	final static public String EVENT_CLEAR = "ClearChart";

	private final JButton year, month;
	private final DefaultListModel<String> model;
	public final JList<String> list;
	
	private final JLabel title;
	
	private final File rootDir;
	private File[] files;
	private final Stack<File> dirStack;

	public boolean isRunSelected, isRecordSelected;

	public FilesPanel() {

		title = new JLabel("");
		
		month = RdWidgets.createButton("M");
		month.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				// FilesPanel.this.firePropertyChange(EVENT_CLEAR, false, true);
			}
		});

		year = RdWidgets.createButton("Y");
		year.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				// FilesPanel.this.firePropertyChange(EVENT_CLEAR, false, true);
			}
		});

		model = new DefaultListModel<String>();
		list = RdWidgets.createList(model);
		
        list.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				if( e.getClickCount() == 2 ){
					processDoubleClick(list.locationToIndex(e.getPoint()));
				}
			}
		});
        
        rootDir = new File(FxTConfig.DATA_ROOT);

		dirStack = new Stack<File>();
		dirStack.push(rootDir);
		
		refresh();

		init();
	}

	@Override
	public void init() {

		setLayout(new GridLayout());
		
		add(RdWidgets.createPanelBoxY(new JComponent[] {
				RdWidgets.createPanelBoxX(new JComponent[] {title, year, month}), 
				RdWidgets.setHeight(RdWidgets.createPaneScroll(list), 1000) 
			})
		);
	}

	
	private void refresh(){
		model.removeAllElements();
		
		if( dirStack.size() > 1 ){
			model.addElement(".");
			model.addElement("..");
		}
		
		File curDir = dirStack.peek();
		files = UtilFile.scanFiles(curDir);
		
		for(int i=0; i<files.length; i++){
			model.addElement(files[i].getName());
		}
	
		title.setText(String.format("%-40s", curDir.getPath()));
	}
	
	private void processDoubleClick(int index){
		if( dirStack.size() > 1 ){
			if( index == 0 ){
				navigateRoot();
				return;
			} else if( index == 1 ){
				navigateParent();
				return;
			} else {
				index -= 2;
			}
		} 
		navigateDir(files[index]);
	}
	
	public void navigateRoot(){
		dirStack.clear();
		dirStack.push(rootDir);
		refresh();
	}
	
	public void navigateParent(){
		dirStack.pop();
		refresh();		
	}
	
	public void navigateDir(final File dir){
		if( dir.isDirectory() ){
			
			final String curDirAbsPath = dirStack.peek().getAbsolutePath();

			if( curDirAbsPath.equals(dir.getParentFile().getAbsolutePath()) ){
				dirStack.push(dir);
				refresh();
			} else if( curDirAbsPath.equals(dir.getAbsolutePath()) ){
				refresh();
			} else {
				List<File> newDirStack = new ArrayList<File>();
				File parent = dir;
				
				for(final String rootAbsPath = rootDir.getAbsolutePath(); parent != null && !parent.getAbsolutePath().equals(rootAbsPath); parent = parent.getParentFile()){
					newDirStack.add(parent);
				}
				
				if( parent == null )
					throw new RuntimeException(String.format("Directory [%s] does not belong to root [%s]", dir.getAbsolutePath(), rootDir.getAbsolutePath()));
				
				dirStack.clear();
				dirStack.push(rootDir);
				for(int i=newDirStack.size()-1; i>=0; i--){
					dirStack.push(newDirStack.get(i));
				}
				refresh();
			}
		} else {
			navigateFile(dir);
		}
	}
	
	public void navigateFile(File file){
		if( file.isDirectory() ){
			navigateDir(file);
		} else {
			navigateDir(file.getParentFile());
			list.setSelectedValue(file.getName(), true);
			// TODO: sometimes redrowing incorect due to wrong thread context?
			list.ensureIndexIsVisible(list.getSelectedIndex());
			revalidate();
		}
	}
	
	final public void freez(){
		list.setEnabled(false);
	}

	final public void enable(){
		list.setEnabled(true);
	}

}
