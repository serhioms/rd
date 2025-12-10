package ca.mss.rd.trader.view.tabs.lab;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JTextField;
import javax.swing.JToggleButton;

import ca.mss.rd.swing.RdPanel;
import ca.mss.rd.swing.RdWidgets;
import ca.mss.rd.util.Logger;

public class LabButtonsPanel extends RdPanel {

	final static public String module = LabButtonsPanel.class.getName();
	final public static long serialVersionUID = module.hashCode();

	final static public String EVENT_RUN = "RunLab";
	final static public String EVENT_CLEAR = "ClearLab";
	final static public String EVENT_STOP_COUNTER = "StopCounter";
	final static public String EVENT_START_COUNTER = "StartCounter";

	final public JToggleButton run;
	final public JButton clear;
	
	final public JTextField start;
	final public JTextField stop;
	
	public boolean isRunSelected;
	
	public LabButtonsPanel() {
		
		run = RdWidgets.createButtonToggle("Stop", "Start");
		run.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
            	AbstractButton abstractButton = (AbstractButton) actionEvent.getSource();
                boolean selected = abstractButton.getModel().isSelected();
                assert( Logger.EVENT.isOn ? Logger.EVENT.printf("LabButtonsPanel.EVENT_RUN [%d]", Thread.currentThread().getId()): true);
                LabButtonsPanel.this.firePropertyChange(EVENT_RUN, isRunSelected, selected);
                isRunSelected = selected;
            }
        });

		clear = RdWidgets.createButton("Clear");
		clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                LabButtonsPanel.this.firePropertyChange(EVENT_CLEAR, false, true);
            }
        });
        
		stop = new JTextField();
		stop.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
                LabButtonsPanel.this.firePropertyChange(EVENT_STOP_COUNTER, null, stop.getText());
            }
        });
		stop.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if( e.getClickCount() == 1 ){
					stop.setText("");
	                LabButtonsPanel.this.firePropertyChange(EVENT_STOP_COUNTER, null, "");
				}
			}
        });
		stop.addPropertyChangeListener(new PropertyChangeListener() {
			
			@Override
			public void propertyChange(PropertyChangeEvent arg0) {
                LabButtonsPanel.this.firePropertyChange(EVENT_STOP_COUNTER, null, stop.getText());
			}
		});

		start = new JTextField();
		start.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
                LabButtonsPanel.this.firePropertyChange(EVENT_START_COUNTER, null, start.getText());
            }
        });
		start.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if( e.getClickCount() == 1 ){
					start.setText("");
	                LabButtonsPanel.this.firePropertyChange(EVENT_START_COUNTER, null, "");
				}
			}
        });
		start.addPropertyChangeListener(new PropertyChangeListener() {
			
			@Override
			public void propertyChange(PropertyChangeEvent arg0) {
                LabButtonsPanel.this.firePropertyChange(EVENT_START_COUNTER, null, start.getText());
			}
		});

		init();
	}

	@Override
	public void init() {
		setLayout(new GridLayout());
		add(RdWidgets.createPanelBoxX(new JComponent[]{run, clear, start, stop}));
	}

}
