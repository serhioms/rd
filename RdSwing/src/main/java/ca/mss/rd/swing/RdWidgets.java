package ca.mss.rd.swing;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.util.Calendar;
import java.util.Collection;

import javax.swing.AbstractButton;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JToggleButton;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerDateModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class RdWidgets {

	static public final JList<String> createList(ListModel<String> model){
		JList<String> list = new JList<String>(model);
		list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        list.setSelectedIndex(0);
        
        list.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				//Logger.DEBUG.printf("Selected [%d,%d]", e.getFirstIndex(), e.getLastIndex());
			}
		});
        return list;
	}
	
	static public final JComponent[] createButtons(int size, int start) {
		JComponent[] c = new JComponent[size];
		for(int i=0; i<c.length; i++){
			c[i] = createButton(Integer.toString(start++));
		}
		return c;
	}
	
	static public final JComponent[] createButtons(int size) {
		return createButtons(size, 1);
	}
	
	static public final JComponent[] createButtons(String[] labels) {
		JComponent[] c = new JComponent[labels.length];
		for(int i=0; i<c.length; i++){
			c[i] = createButton(labels[i]);
		}
		return c;
	}
	
	static public final JButton createButton(String label) {
		JButton b = new JButton(label);
		return b;
	}
	
	static public final JToggleButton createButtonToggle(String on, String off){
		JToggleButton b = new JToggleButton(off);
		b.setName(on+","+off);
        b.addItemListener(new ItemListener() {
            @Override
			public void itemStateChanged(ItemEvent e) {
            	AbstractButton ab = (AbstractButton)e.getSource();
                boolean selected = ab.getModel().isSelected();
                String[] onoff = ab.getName().split(",");
                ab.setText(selected? onoff[0]: onoff[1]);
            }
        });
        return b;
	}
	
	static public final JComponent[] createBoxCheck(Object[] labels){
		return createBoxCheck(labels, null);
	}
	
	static public final JComponent[] createBoxCheck(Object[] labels, ItemListener il){
		JComponent[] jc = new JComponent[labels.length];
		for(int i=0; i<labels.length; i++ ){
			String[] label = labels[i].toString().split(":");
			
			JCheckBox checkbox = new JCheckBox(label[0]);
			// TODO: make check box mnemonic to be less stupid
			// checkbox.setMnemonic(KeyEvent.VK_1+i);
			
			if( il != null )
				checkbox.addItemListener(il);

			checkbox.setSelected(true);
			if( !(label.length > 1? "Y".equalsIgnoreCase(label[1]): false) ){
				checkbox.setSelected(false);
			}
			
			jc[i] = checkbox;
		}
		return jc;
	}
	
	static public final JComponent[] createBoxCheck(Collection<String> c){
		return createBoxCheck(c.toArray());
	}
	
	static public final JComponent[] createBoxCheck(Collection<String> c, ItemListener il){
		return createBoxCheck(c.toArray(), il);
	}
	
	static public final JComponent[] createBoxRadio(Collection<String> c){
		return createBoxRadio(c.toArray(), null);
	}
	
	static public final JComponent[] createBoxRadio(Collection<String> c, ItemListener il){
		return createBoxRadio(c.toArray(), il);
	}
	
	static public final JComponent[] createBoxRadio(Object[] labels){
		return createBoxRadio(labels, null);
	}
	
	static public final JComponent[] createBoxRadio(Object[] labels, ItemListener il){
		JComponent[] jc = new JComponent[labels.length];
		
        ButtonGroup group = new ButtonGroup();
        
        for(int i=0; i<labels.length; i++ ){
			String[] label = labels[i].toString().split(":");
			
			JRadioButton radiob = new JRadioButton(label[0]);
			radiob.setMnemonic(KeyEvent.VK_1+i);
			
			if( il != null )
				radiob.addItemListener(il);
			
			if( label.length > 1 )
				radiob.setSelected("Y".equalsIgnoreCase(label[1]));
			else
				radiob.setSelected(false);
			
			jc[i] = radiob;
			group.add(radiob);   
		}
		return jc;
	}
	
	static public final JSpinner createTimeSpinner(){
		SpinnerDateModel model = new SpinnerDateModel();
		model.setCalendarField(Calendar.MINUTE);

		JSpinner spinner = new JSpinner();
		spinner.setModel(model);
		spinner.setEditor(new JSpinner.DateEditor(spinner, "h:mm a"));
	
		return spinner;
	}
	
	static public final int NORTH = 0;
	static public final int WEST = 1;
	static public final int CENTER = 2;
	static public final int EAST = 3;
	static public final int SOUTH = 4;
	
	static public final JTabbedPane createTabPane(String[] labels, JComponent[] ...args) {
		JTabbedPane p = new JTabbedPane();
		for(int j=0; j<args.length; j++){
			JComponent[] c = (JComponent[] )args[j];
			for(int i=0; i<c.length; i++){
				if( c[i] != null ){
					p.addTab(labels[i], RdWidgets.createPaneScroll(c[i]));
				}
			}
		}
		return p;
	}
	
	static public final JPanel createPanelBoxY(JComponent[] ...args) {
		JPanel p = new JPanel();
		p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
		for(int j=0; j<args.length; j++){
			JComponent[] c = (JComponent[] )args[j];
			for(int i=0; i<c.length; i++){
				if( c[i] != null )
					p.add(c[i]);
			}
		}
		return p;
	}
	
	static public final JPanel createPanelBoxYGlue(JComponent[] ...args) {
		JPanel p = new JPanel();
		p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
		for(int j=0; j<args.length; j++){
			JComponent[] c = (JComponent[] )args[j];
			for(int i=0; i<c.length; i++){
				if( i > 0 )
					p.add(Box.createHorizontalGlue());
				if( c[i] != null )
					p.add(c[i]);
			}
		}
		return p;
	}
	
	static public final JPanel createPanelBoxXGlue(JComponent[] ...args) {
		JPanel p = new JPanel();
		p.setLayout(new BoxLayout(p, BoxLayout.X_AXIS));
		for(int j=0; j<args.length; j++){
				JComponent[] c = (JComponent[] )args[j];
			for(int i=0; i<c.length; i++){
				if( i > 0 )
					p.add(Box.createHorizontalGlue());
				if( c[i] != null ){
					p.add(c[i]);
				}
			}
		}
		return p;
	}
	
	static public final JPanel createPanelBoxX(JComponent[] ...args) {
		JPanel p = new JPanel();
		p.setLayout(new BoxLayout(p, BoxLayout.X_AXIS));
		for(int j=0; j<args.length; j++){
			JComponent[] c = (JComponent[] )args[j];
			for(int i=0; i<c.length; i++){
				if( c[i] != null )
					p.add(c[i]);
			}
		}
		return p;
	}
	
	static public final JPanel createPanelBorder(JComponent[] ...args) {
		JPanel p = new JPanel();
		p.setLayout(new BorderLayout());
		for(int j=0; j<args.length; j++){
			JComponent[] c = (JComponent[] )args[j];
			for(int i=0; i<c.length; i++){
				if( c[i] != null )
					p.add(c[i]);
			}
		}
		return p;
	}
	
	static public final JPanel createPanelBorder(JComponent north, JComponent west, JComponent center, JComponent east, JComponent south ){
		JPanel p = new JPanel();
		p.setLayout(new BorderLayout());
		if( north != null )
			p.add(north, BorderLayout.NORTH);
		if( west != null )
			p.add(west, BorderLayout.WEST);
		if( center != null )
			p.add(center, BorderLayout.CENTER);
		if( east != null )
			p.add(east, BorderLayout.EAST);
		if( south != null )
			p.add(south, BorderLayout.SOUTH);
		return p;
	}

	static public final JPanel setWidth(JPanel p, int width) {
		p.setSize(new Dimension(width, p.getHeight()));
		return p;
	}

	static public final JScrollPane setWidth(JScrollPane p, int width) {
		p.setPreferredSize(new Dimension(width, p.getHeight()));
		return p;
	}

	static public final JScrollPane setHeight(JScrollPane p, int height) {
		p.setPreferredSize(new Dimension(p.getWidth(), height));
		return p;
	}

	static public final JScrollPane createPaneScroll(JComponent c) {
		JScrollPane p = new JScrollPane();
		p.setViewportView(c);	
		return p;
	}

	static public final JPanel createPanelGrid(int rows, int cols, JComponent[] ...args) {
		JPanel p = new JPanel();
		p.setLayout(new GridLayout(rows, cols));
		for(int j=0; j<args.length; j++){
			JComponent[] c = (JComponent[] )args[j];
			for(int i=0; i<c.length; i++){
				if( c[i] != null )
					p.add(c[i]);
			}
		}
		return p;
	}
	
	static public final JPanel createPanelFlow(JComponent[] ...args) {
		JPanel p = new JPanel();
	    p.setLayout(new FlowLayout ());
		for(int j=0; j<args.length; j++){
			JComponent[] c = (JComponent[] )args[j];
			for(int i=0; i<c.length; i++){
				if( c[i] != null )
					p.add(c[i]);
			}
		}
		return p;
	  }
	
	static public final JPanel createPanelFlow(JComponent ...args) {
		JPanel p = new JPanel();
	    p.setLayout(new FlowLayout ());
		for(int j=0; j<args.length; j++){
			p.add(args[j]);
		}
		return p;
	  }
}
