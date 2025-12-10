package ca.mss.rd.trader.deprecated.panels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeSupport;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;

 public class RecoderButtonPanel extends JPanel {

	final static public String module = ButtonPanel.class.getName();
	final public static long serialVersionUID = module.hashCode();
	
    public final JButton clearBtn;
    public final JButton startBtn;
    public final JButton recordBtn;

    public RecoderButtonPanel() {
        super();
        
        this.startBtn = new JButton("Start");
        this.recordBtn = new JButton("Record");
        this.clearBtn = new JButton("Clear");
        
        init();
    }
    
    private void init(){

    	// TODO: setBackground(java.awt.Color.WHITE);
        
    	configureButton(startBtn);
        configureButton(recordBtn);
        configureButton(clearBtn);
        
        setLayout(new BoxLayout(this, 1));

        JComponent stretch = new JPanel();
        // TODO: stretch.setBackground(java.awt.Color.WHITE);
        stretch.setLayout(new BoxLayout(stretch, 0));
        stretch.add(Box.createHorizontalGlue());
        stretch.add(startBtn);
        stretch.add(Box.createHorizontalGlue());
        stretch.add(recordBtn);
        stretch.add(Box.createHorizontalGlue());
        stretch.add(clearBtn);
        stretch.add(Box.createHorizontalGlue());
        add(stretch);
    }

    
    private void configureButton(JButton button) {
    	// TODO: button.setBackground(java.awt.Color.WHITE);
    	button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JButton source = (JButton )e.getSource();
        		pcs.firePropertyChange(source.getText(), null, "");
            }
        });
    }
    
	/*
     * Property change support
     */
    final private PropertyChangeSupport pcs = new PropertyChangeSupport(this);
    
}
