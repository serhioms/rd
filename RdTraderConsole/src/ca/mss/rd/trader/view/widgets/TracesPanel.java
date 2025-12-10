package ca.mss.rd.trader.view.widgets;

import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.HashSet;
import java.util.Set;

import javax.swing.AbstractButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JPanel;

import ca.mss.rd.swing.RdPanel;
import ca.mss.rd.swing.RdWidgets;
import ca.mss.rd.util.UtilMisc;

public class TracesPanel extends RdPanel {

	final static public String module = TracesPanel.class.getName();
	final public static long serialVersionUID = module.hashCode();

	final static public String EVENT_TRACE_SELECT = "IndTraceVisible";

	private final ItemListener quotesListener;
	private String valChooser = "";
	private JComponent[] checkBoxes;
	private JPanel cbpanel;
	
	public final Set<String> selectedTraces = new HashSet<String>(); 
	public final Set<String> autohiddenTraces = new HashSet<String>(); 

	public TracesPanel() {
		quotesListener = new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
            	AbstractButton ab = (AbstractButton)e.getSource();
		        //Logger.warn.println("Radio button item [%s] selected [%s]", cb.getText(), cb.isSelected());
				final String newVal = ab.getText()+":"+ab.isSelected();
	           	firePropertyChange(EVENT_TRACE_SELECT, valChooser, newVal);
				valChooser = newVal;
			}
        };
		init();
	}

	@Override
	public void init() {

		setLayout(new GridLayout());
		
		add( RdWidgets.createPanelBoxY( RdWidgets.createBoxCheck(UtilMisc.toList("1","2","3"))) );
	}

	public final void setSelectedTraces(String labels){
		if( labels!= null && !labels.isEmpty() ){
			String[] label = labels.split(",");
			for(int i=0; i<label.length; i++){
				selectedTraces.add(label[i]);
			}
		}
	}

	public final boolean isSelected(String trace){
		return selectedTraces.contains(trace);
	}

	public final void setSelected(String trace, boolean isSelected){
		if( isSelected )
			selectedTraces.add(trace);
		else
			selectedTraces.remove(trace);
	}

	public final String getSelectedTraces(){
		return selectedTraces.toString().replaceAll("\\[", "").replaceAll("\\]", "");
	}

	public final void createTraces(Set<String> traces, String selected){
		selectedTraces.clear();
		
		Object[] labels = (Object[] )traces.toArray();
		
		if( selected != null && !selected.isEmpty() ){
			// Reselect check boxes
			for(int i=0; i<labels.length; i++){
				String[] label = labels[i].toString().split(":");
				if( selected.contains(label[0]) ){
					labels[i] = label[0]+":Y";
					selectedTraces.add(label[0]);
				} else {
					labels[i] = label[0]+":N";
				}
			}
		}
		
		checkBoxes = RdWidgets.createBoxCheck(labels, quotesListener);
		removeAll();
		add( cbpanel = RdWidgets.createPanelBoxY(checkBoxes) );
		
		revalidate();
		repaint();
	}
	
	public final void manageSlowTraces(Set<String> traces, boolean doSelect){

		Component[] ac = cbpanel.getComponents();

		for(int i=0; i<ac.length; i++){
			Component c = ac[i];
			
			if( c instanceof JCheckBox ){
				JCheckBox cb = (JCheckBox )c;
				String traceName = cb.getText();
				if( traces.contains( traceName ) ){
					if( doSelect ){
						if( autohiddenTraces.contains( traceName ) && cb.isSelected() != doSelect ){
							cb.setSelected(doSelect);
							autohiddenTraces.remove(traceName);
						}
					} else {
						if( cb.isSelected() != doSelect ){
							cb.setSelected(doSelect);
							autohiddenTraces.add(traceName);
						}
					}
				}
			}
		}
		
		revalidate();
		repaint();
	}
}
