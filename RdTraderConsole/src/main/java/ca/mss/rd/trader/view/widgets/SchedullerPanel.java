package ca.mss.rd.trader.view.widgets;

import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;

import ca.mss.rd.swing.RdPanel;
import ca.mss.rd.swing.RdWidgets;
import ca.mss.rd.util.Logger;
import ca.mss.rd.util.UtilDateTime;

public class SchedullerPanel extends RdPanel {

	final static public String module = SchedullerPanel.class.getName();
	final public static long serialVersionUID = module.hashCode();

	final static public String EVENT_SCHEDULE = "Schedule";
	final static public String EVENT_SCHEDULE_CHANGE_DAY = "ScheduleDay";
	final static public String EVENT_SCHEDULE_CHANGE_DAY_FROM = "ScheduleDayFrom";
	final static public String EVENT_SCHEDULE_CHANGE_DAY_TO = "ScheduleDayTo";
	final static public int SCHEDULER_STYLE_1 = 1;
	final static public int SCHEDULER_STYLE_2 = 2;

	final public JSpinner from, to;
	final public JButton applay;
	private ItemListener listener, fromListener, toListener;

	final int schedStyle;
	
	private String oldDay = "", oldDayTo = "", oldDayFrom = "";
	
	public SchedullerPanel(int schedStyle) {
		
		this.schedStyle = schedStyle;
		this.from = RdWidgets.createTimeSpinner();
		this.to = RdWidgets.createTimeSpinner();
		this.applay = RdWidgets.createButton("Applay Schedule");

		from.getModel().setValue(UtilDateTime.setTime(UtilDateTime.now(), 17, 0));
		to.getModel().setValue(UtilDateTime.setTime(UtilDateTime.now(), 17, 0));

		applay.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
            	SchedullerPanel.this.firePropertyChange(EVENT_SCHEDULE, false, true);
            }
        });		

		listener = new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
            	AbstractButton ab = (AbstractButton)e.getSource();
            	assert( Logger.GUI.isOn ? Logger.GUI.printf("Radio button item [%s] selected [%s]", ab.getText(), ab.isSelected()): true);
	           	SchedullerPanel.this.firePropertyChange(EVENT_SCHEDULE_CHANGE_DAY, oldDay, ab.getText());
	           	oldDay = ab.getText();
			}
        };

		fromListener = new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
            	AbstractButton ab = (AbstractButton)e.getSource();
            	assert( Logger.GUI.isOn ? Logger.GUI.printf("Radio button item [%s] selected [%s]", ab.getText(), ab.isSelected()): true);
	           	SchedullerPanel.this.firePropertyChange(EVENT_SCHEDULE_CHANGE_DAY_FROM, oldDayFrom, ab.getText());
	           	oldDayFrom = ab.getText();
			}
        };

		toListener = new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
            	AbstractButton ab = (AbstractButton)e.getSource();
            	assert( Logger.GUI.isOn ? Logger.GUI.printf("Radio button item [%s] selected [%s]", ab.getText(), ab.isSelected()): true);
	           	SchedullerPanel.this.firePropertyChange(EVENT_SCHEDULE_CHANGE_DAY_TO, oldDayTo, ab.getText());
	           	oldDayTo = ab.getText();
			}
        };

		init();

	}

	@Override
	public void init() {
		setLayout(new GridLayout());
		
		JPanel panelScheduller = null;
		
		if( schedStyle == SCHEDULER_STYLE_1 ){
			panelScheduller = RdWidgets.createPanelGrid(3, 1, new JComponent[]{
				RdWidgets.createPanelBoxX(RdWidgets.createBoxCheck(new String[]{"Sn:Y", "Mn:Y", "Th:Y", "Wd:Y", "Tu:Y", "Fr:Y", "St:N"}, listener)),
				RdWidgets.createPanelFlow(new JComponent[]{new JLabel("From"), from, new JLabel("To"), to}),
				applay
			});
		} else if( schedStyle == SCHEDULER_STYLE_2 ){
			panelScheduller = RdWidgets.createPanelBoxY(
					new JComponent[]{
							RdWidgets.createPanelFlow( 
									new JComponent[]{RdWidgets.createPanelGrid(1, 2, new JComponent[]{new JLabel("From"), from})}, 
									new JComponent[]{RdWidgets.createPanelGrid(1, 7, RdWidgets.createBoxRadio(new String[]{"Sn:Y", "Mn", "Th", "Wd", "Tu", "Fr", "St"}, fromListener))}
							),
							RdWidgets.createPanelFlow( 
									new JComponent[]{RdWidgets.createPanelGrid(1, 2, new JComponent[]{new JLabel("To"), to})}, 
									new JComponent[]{RdWidgets.createPanelGrid(1, 7, RdWidgets.createBoxRadio(new String[]{"Sn:N", "Mn", "Th", "Wd", "Tu", "Fr:Y", "St"}, toListener))}
							),
						applay
					}
			);
		} else {
			throw new RuntimeException("Unsupported scheduller style ["+schedStyle+"]");
		}
		
		add(panelScheduller);
	}

}
