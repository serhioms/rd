package ca.mss.rd.trader.deprecated.panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.Set;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import ca.mss.rd.trader.util.MyColor;
import ca.mss.rd.util.Logger;

abstract public class ButtonPanel extends JPanel {

	final static public String module = ButtonPanel.class.getName();
	final public static long serialVersionUID = module.hashCode();
	
	protected JComboBox<String> dataChooser;
    protected JButton clearBtn;
    protected JButton snapshotBtn;
    protected JButton startStopBtn;
    protected JButton recordBtn;
    protected JButton buysellBtn;
    protected JButton sellbuyBtn;
    protected JButton revertBtn;

    protected LatencyTimePanel latencyPanel;
    protected AmountPointsPanel amountPanel;

    public ButtonPanel() {
        super();
    }
    
    abstract public void revertHandler();
    
	private void createRevertButton() {
    	revertBtn = new JButton("Revert");
    	revertBtn.setBackground(Color.WHITE);
        revertBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	revertHandler();
            }
        });
    }
    

    abstract public void startHandler(String command);
    
    private void createStartStopButton() {
        startStopBtn = new JButton("Start");
        startStopBtn.setBackground(Color.WHITE);
        startStopBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JButton source = (JButton )e.getSource();
                startHandler(source.getText());
            }
        });
    }
    
    abstract public void sbbHandler(String command);

    private void createSellBuyButton() {
    	sellbuyBtn = new JButton("sbb");
    	sellbuyBtn.setBackground(Color.WHITE);
        sellbuyBtn.setForeground(MyColor.COLOR_SBB);
        sellbuyBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                JButton source = (JButton)e.getSource();
                sbbHandler(source.getText());
            }
        });
    }
    
    abstract public void bsbHandler(String command);

    private void createBuySellButton() {
    	buysellBtn = new JButton("bsb");
    	buysellBtn.setBackground(Color.WHITE);
        buysellBtn.setForeground(MyColor.COLOR_BSB);
        buysellBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                JButton source = (JButton)e.getSource();
                bsbHandler(source.getText());
            }
        });
    }

    abstract public void recordHandler(String command);

    private void createRecordButton() {
    	recordBtn = new JButton("Record");
    	recordBtn.setBackground(Color.WHITE);
        recordBtn.setBackground(Color.WHITE);
        recordBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                JButton source = (JButton)e.getSource();
                recordHandler(source.getText());
            }
        });
    }

    abstract public void clearHandler();

    private void createClearButton() {
        clearBtn = new JButton("Clear");
        clearBtn.setBackground(Color.WHITE);
        clearBtn.setBackground(Color.WHITE);
        clearBtn.addActionListener(new ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                clearHandler();
            }
        });
    }

    abstract public Set<String> getDataSources();
    abstract public void setDataSource(String name);

    private void createDataChooserButton() {
        dataChooser = new JComboBox<String>();
        dataChooser.setBackground(Color.WHITE);

        for(Iterator<String> iter=getDataSources().iterator(); iter.hasNext(); dataChooser.addItem(iter.next()));
        
        
        dataChooser.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
            	setDataSource(dataChooser.getSelectedItem().toString());
            }
        });
        
        dataChooser.setSelectedIndex(0);
        dataChooser.setMaximumSize(new Dimension(200, clearBtn.getMaximumSize().height));
    }

    public void initControlButtonPanel(){

    	setBackground(Color.WHITE);
        createStartStopButton();
        createRevertButton();
        createBuySellButton();
        createSellBuyButton();
        createRecordButton();
        createClearButton();
        createDataChooserButton();
        setLayout(new BoxLayout(this, 1));

        javax.swing.JComponent stretch = new JPanel();
        stretch.setBackground(Color.WHITE);
        stretch.setLayout(new BoxLayout(stretch, 0));
        stretch.add(Box.createHorizontalGlue());
        stretch.add(startStopBtn);
        stretch.add(Box.createHorizontalGlue());
        stretch.add(revertBtn);
        stretch.add(Box.createHorizontalGlue());
        stretch.add(buysellBtn);
        stretch.add(Box.createHorizontalGlue());
        stretch.add(sellbuyBtn);
        stretch.add(Box.createHorizontalGlue());
        stretch.add(clearBtn);
        if(snapshotBtn != null) {
            stretch.add(Box.createHorizontalGlue());
            stretch.add(snapshotBtn);
        }
        stretch.add(Box.createHorizontalGlue());
        stretch.add(recordBtn);
        stretch.add(Box.createHorizontalGlue());
        stretch.add(dataChooser);
        stretch.add(Box.createHorizontalGlue());
        add(stretch);
    }

}
