package ca.mss.rd.trader.view.widgets;

import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import ca.mss.rd.swing.RdSlider;

public class LatencySliderPanel extends RdSlider {

	final static public String module = MaxPointSliderPanel.class.getName();
	final public static long serialVersionUID = module.hashCode();
	
	final static public String TITLE = "Latency for quering: %s sec";

	public int latency = 0;
	
	public LatencySliderPanel(int min, int max, int minor, int major) {
		super(TITLE, min, max, minor, major);
        slider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                JSlider source = (JSlider)e.getSource();
                if(!source.getValueIsAdjusting()) {
                    int value = source.getValue();
                    if( value > 0 ){
	                    LatencySliderPanel.this.firePropertyChange(WidgetEvent.EVENT_LATENCY, latency, value);
	                	latency = value;
	                	setTitle(Integer.toString(latency));
                    } else {
                        slider.setValue(LatencySliderPanel.super.minor);
                    }
                }
            }
        });	 
	}

	public void init(int initial) {
        slider.setValue(initial);
	}

}
