package ca.mss.rd.swing;


import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JSlider;


public class RdSlider extends RdPanel {

		final static public String module = RdSlider.class.getName();
		final public static long serialVersionUID = module.hashCode();
		
	    public int min, max, major, minor;
	    public String title;

		final public JSlider slider;

		public RdSlider(String title, int min, int max, int minor, int major) {
	        this.title = title;
			this.min = min;
	        this.max = max;
	        this.minor = minor;
	        this.major = major;
	        
	        slider = new JSlider(min, max);
			
	        init();
		}

		@Override
		public void init() {
			setLayout(new GridLayout());
			
	        //slider.setBackground(java.awt.Color.WHITE);
	        if( major > 0 )
	        	slider.setMajorTickSpacing(major);
	        if( minor > 0 )
	        	slider.setMinorTickSpacing(minor);
	        slider.setSnapToTicks(true);
	        slider.setPaintLabels(true);
	        slider.setPaintTicks(true);
	        setTitle("");
	        
	        add(slider);
		}
		
		final public void setTitle(String value){
	        slider.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), String.format(title, value), 1, 3));
		}

	}
