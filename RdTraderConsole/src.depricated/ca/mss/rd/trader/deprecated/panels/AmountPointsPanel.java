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
public class AmountPointsPanel extends JPanel {
	
	private static final long serialVersionUID = AmountPointsPanel.class.hashCode();
	
	static public String[] AMOUNT_RADIO = new String[]{"50","100","250","500","750","1K","2K","5K","10K","25K","50K","100K"};
	static public int AMOUNT_SELECTED = AMOUNT_RADIO.length-1;
   
	final public JRadioButton[] radio;
	final public ButtonGroup group;
    
	private int maxPointAmount;
	
    public AmountPointsPanel(PropertyChangeListener[] listener){

    	for(int i=0; i<listener.length; i++){
    		addPropertyChangeListener(listener[i]);
    	}

       	group = new ButtonGroup();
    	radio = new JRadioButton[AMOUNT_RADIO.length];
    	
    	for(int i=0; i<radio.length; i++){
    		radio[i] = new JRadioButton(AMOUNT_RADIO[i]);
        	group.add(radio[i]);
    		add(radio[i]);
    		radio[i].addActionListener(new ActionListener() {
              public void actionPerformed(ActionEvent e) {
                  JRadioButton source = (JRadioButton)e.getSource();
                  setMaxPointAmount(convert2amount(source.getText()));
              }
          });    		
    	}
		radio[AMOUNT_SELECTED].setSelected(true);
		setMaxPointAmount(convert2amount(AMOUNT_RADIO[AMOUNT_SELECTED]));
		
		setLayout( new FlowLayout());
		setVisible(true);
	}
    
    final public int getMaxSize() {
		return maxPointAmount;
	}

    final public void setMaxPointAmount(int maxPointAmount) {
		this.maxPointAmount = maxPointAmount;
		pcs.firePropertyChange("maxPointAmount", null, maxPointAmount);
	}

    final static private int convert2amount(String tval){
		int ival;
    	if( tval.contains("K")){
    		tval = tval.replace("K", "");
    		ival = Integer.parseInt(tval)*1000;
    	} else if( tval.contains("M")){
    		tval = tval.replace("M", "");
    		ival = Integer.parseInt(tval)*1000000;
    	} else {
    		ival = Integer.parseInt(tval);
    	}
    	return ival;
    }
    
    /*
     * Property change support
     */
    private PropertyChangeSupport pcs = new PropertyChangeSupport(this);
    
    final public void addPropertyChangeListener(PropertyChangeListener listener) {
    	if( pcs == null ){
    		pcs = new PropertyChangeSupport(this);
    	}
    	pcs.addPropertyChangeListener(listener);
    }
}
