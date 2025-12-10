package ca.mss.rd.trader.view.widgets;

import java.awt.FlowLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JComponent;
import javax.swing.JRadioButton;

import ca.mss.rd.swing.RdPanel;
import ca.mss.rd.swing.RdWidgets;

public class MaxPointRadioPanel extends RdPanel {
	
	private static final long serialVersionUID = MaxPointRadioPanel.class.hashCode();
	final static public org.apache.log4j.Logger logger = ca.mss.rd.util.Debug.getLogger(MaxPointRadioPanel.class);
	
	final public String[] labels;

 	final public JComponent[] radio;
	final public ButtonGroup group;
    
	private int maxPoint;
	
    public MaxPointRadioPanel(String[] labels){
    	this.labels = labels;
    	
		group = new ButtonGroup();
    	radio = RdWidgets.createBoxRadio(labels, new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
            	AbstractButton ab = (AbstractButton)e.getSource();
                int value = convert(ab.getText());
                if( maxPoint != value ){
	            	logger.debug(String.format("Radio button item [%s] selected", ab.getText()));
	               	MaxPointRadioPanel.this.firePropertyChange(WidgetEvent.EVENT_MAXPOINT, maxPoint, value);
	               	maxPoint = value;
                }
			}
        });
    	
        init();
	}

	@Override
	public void init() {
		setLayout(new FlowLayout());
        add(RdWidgets.createPanelFlow(radio));
    }
    
	public void init(int initial) {
		((JRadioButton )(radio[initial])).setSelected(true);
	}

	
	
	
	final static private int convert(String text){
		int ival;
    	if( text.contains("K")){
    		text = text.replace("K", "");
    		ival = Integer.parseInt(text)*1000;
    	} else if( text.contains("M")){
    		text = text.replace("M", "");
    		ival = Integer.parseInt(text)*1000000;
    	} else {
    		ival = Integer.parseInt(text);
    	}
    	return ival;
    }
	
	final public int getIndex(int maxPoints){
		for(int i=0; i<labels.length; i++){
			if( convert(labels[i]) == maxPoints)
				return i;
		}
		return 0;
	}
}

