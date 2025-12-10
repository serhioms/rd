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

public class LatencyRadioPanel extends RdPanel {
	
	private static final long serialVersionUID = LatencyRadioPanel.class.hashCode();
	final static public org.apache.log4j.Logger logger = ca.mss.rd.util.Debug.getLogger(LatencyRadioPanel.class);
	
	final public String[] labels;

 	final public JComponent[] radio;
	final public ButtonGroup group;
    
	private long latency;
	
    public LatencyRadioPanel(String[] labels){
    	this.labels = labels;
    	
		group = new ButtonGroup();
    	radio = RdWidgets.createBoxRadio(labels, new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
            	AbstractButton ab = (AbstractButton)e.getSource();
                long value = convert(ab.getText());
                if( latency != value ){
			        logger.debug(String.format("Radio button item [%s] selected", ab.getText()));
	               	LatencyRadioPanel.this.firePropertyChange(WidgetEvent.EVENT_LATENCY, latency, value);
	               	latency = value;
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

	
	
	
	final static private long convert(String text){
    	long lval = 0L;
    	if( text.contains("ml")){
    		text = text.replace("ml", "");
    		lval = Long.parseLong(text);
    	} else if( text.contains("s")){
    		text = text.replace("s", "");
    		lval = Long.parseLong(text)*1000L;
    	} else if( text.contains("m")){
    		text = text.replace("m", "");
    		lval = Long.parseLong(text)*1000L*60L;
    	} else if( text.contains("h")){
    		text = text.replace("h", "");
    		lval = Long.parseLong(text)*1000L*60L*60L;
    	} else if( text.contains("d")){
    		text = text.replace("d", "");
    		lval = Long.parseLong(text)*1000L*60L*60L*24L;
    	} else if( text.contains("w")){
    		text = text.replace("w", "");
    		lval = Long.parseLong(text)*1000L*60L*60L*24L*7L;
    	} else if( text.contains("mo")){
    		text = text.replace("mo", "");
    		lval = Long.parseLong(text)*1000L*60L*60L*24L*30L;
    	} else {
    		lval = Long.parseLong(text);
    	}
    	return lval;
    }

	final public int getIndex(long latency){
		for(int i=0; i<labels.length; i++){
			if( convert(labels[i]) == latency)
				return i;
		}
		return 0;
	}
}

