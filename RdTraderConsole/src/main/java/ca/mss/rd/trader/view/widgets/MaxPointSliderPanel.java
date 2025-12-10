package ca.mss.rd.trader.view.widgets;

import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import ca.mss.rd.swing.RdSlider;

public class MaxPointSliderPanel extends RdSlider {

	final static public String module = MaxPointSliderPanel.class.getName();
	final public static long serialVersionUID = module.hashCode();
	
	final static public String TITLE = "Maximum amount of points: %s";

	public int maximum;
	
	public MaxPointSliderPanel(int min, int max, int minor, int major) {
		super(TITLE, min, max, minor, major);
        slider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                JSlider source = (JSlider)e.getSource();
                if(!source.getValueIsAdjusting()) {
                    int value = source.getValue();
                    if( value > 0 ){
	                    MaxPointSliderPanel.this.firePropertyChange(WidgetEvent.EVENT_MAXPOINT, maximum, value);
	                    maximum = value;
	                	setTitle(Integer.toString(maximum));
                    } else {
                        slider.setValue(MaxPointSliderPanel.super.minor);
                    }
                }
            }
        });	        
	}

	public void init(int initial) {
        slider.setValue(initial);

	}

}
