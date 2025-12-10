package ca.mss.rd.trader.deprecated.panels;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

@Deprecated
public class LatencyTimePanel extends JPanel {
	
	private static final long serialVersionUID = LatencyTimePanel.class.hashCode();
	
	static public int LATENCY_SELECTED = 2;
	static public String[] LATENCY_RADIO = new String[]{"0","1","10","100","250","500","750","1s","3s","5s","10s","15s","20s","30s","1m","10m","20m","1h","1d"};

 	final public JRadioButton[] radio;
	final public ButtonGroup group;
    
	private long latencyDelay;
	
    public LatencyTimePanel(PropertyChangeListener[] listener){
    	
    	for(int i=0; i<listener.length; i++){
    		addPropertyChangeListener(listener[i]);
    	}
    	
    	group = new ButtonGroup();
    	radio = new JRadioButton[LATENCY_RADIO.length];
    	
    	for(int i=0; i<radio.length; i++){
    		radio[i] = new JRadioButton(LATENCY_RADIO[i]);
        	group.add(radio[i]);
    		add(radio[i]);
    		radio[i].addActionListener(new ActionListener() {
              public void actionPerformed(ActionEvent e) {
                  JRadioButton source = (JRadioButton)e.getSource();
                  setLatencyDelay(convert2mls(source.getText()));
              }
          });    		
    	}
		radio[LATENCY_SELECTED].setSelected(true);
        setLatencyDelay(convert2mls(LATENCY_RADIO[LATENCY_SELECTED]));
		
		setLayout( new FlowLayout());
		setVisible(true);
    }
    
    final public long getLatencyDelay() {
		return latencyDelay;
	}

    final public void setLatencyDelay(long latencyDelay) {
		this.latencyDelay = latencyDelay;
		pcs.firePropertyChange("latencyDelay", null, latencyDelay);
	}

	final static private long convert2mls(String tval){
    	long lval = 0L;
    	if( tval.contains("ml")){
    		tval = tval.replace("ml", "");
    		lval = Long.parseLong(tval);
    	} else if( tval.contains("s")){
    		tval = tval.replace("s", "");
    		lval = Long.parseLong(tval)*1000L;
    	} else if( tval.contains("m")){
    		tval = tval.replace("m", "");
    		lval = Long.parseLong(tval)*1000L*60L;
    	} else if( tval.contains("h")){
    		tval = tval.replace("h", "");
    		lval = Long.parseLong(tval)*1000L*60L*60L;
    	} else if( tval.contains("d")){
    		tval = tval.replace("d", "");
    		lval = Long.parseLong(tval)*1000L*60L*60L*24L;
    	} else {
    		lval = Long.parseLong(tval);
    	}
    	return lval;
    }
	
	
    /*
     * Property change support
     */
    private PropertyChangeSupport pcs;
    
    final public void addPropertyChangeListener(PropertyChangeListener listener) {
    	if( pcs == null ){
    		pcs = new PropertyChangeSupport(this);
    	}
    	pcs.addPropertyChangeListener(listener);
    }
}

