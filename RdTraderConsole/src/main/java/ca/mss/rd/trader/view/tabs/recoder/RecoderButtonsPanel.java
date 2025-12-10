package ca.mss.rd.trader.view.tabs.recoder;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JToggleButton;

import ca.mss.rd.swing.RdPanel;
import ca.mss.rd.swing.RdWidgets;

public class RecoderButtonsPanel extends RdPanel {

	final static public String module = RecoderButtonsPanel.class.getName();
	final public static long serialVersionUID = module.hashCode();

	final static public String EVENT_RUN = "RunRequest";
	final static public String EVENT_RECODER = "RecordResponce";
	final static public String EVENT_CLEAR = "ClearChart";

	final public JToggleButton run, record;
	final public JButton clear;
	
	public boolean isRunSelected, isRecordSelected;
	
	public RecoderButtonsPanel() {
		
		run = RdWidgets.createButtonToggle("Off Line", "On Line");
		run.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
            	AbstractButton abstractButton = (AbstractButton) actionEvent.getSource();
                boolean selected = abstractButton.getModel().isSelected();
                RecoderButtonsPanel.this.firePropertyChange(EVENT_RUN, isRunSelected, selected);
                isRunSelected = selected;
            }
        });

		record = RdWidgets.createButtonToggle("Record Off", "Record On");
		record.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
            	AbstractButton abstractButton = (AbstractButton) actionEvent.getSource();
                boolean selected = abstractButton.getModel().isSelected();
                RecoderButtonsPanel.this.firePropertyChange(EVENT_RECODER, isRecordSelected, selected);
                isRecordSelected = selected;
            }
        });	

		clear = RdWidgets.createButton("Clear Charts");
		clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                RecoderButtonsPanel.this.firePropertyChange(EVENT_CLEAR, false, true);
            }
        });
        
		init();
	}

	@Override
	public void init() {
		setLayout(new GridLayout());
		add(RdWidgets.createPanelBoxX(new JComponent[]{run, record, clear}));
	}

}
