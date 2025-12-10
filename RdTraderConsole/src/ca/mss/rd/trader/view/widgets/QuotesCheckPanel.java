package ca.mss.rd.trader.view.widgets;

import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Set;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;

import ca.mss.rd.swing.RdPanel;
import ca.mss.rd.swing.RdWidgets;
import ca.mss.rd.trader.FxTConfig;
import ca.mss.rd.util.Logger;

public class QuotesCheckPanel extends RdPanel {

	final static public String module = QuotesCheckPanel.class.getName();
	final public static long serialVersionUID = module.hashCode();

	final static public String EVENT_QUOTE_CHOOSER = "QuoteChooser";
	
	private JComponent panelQuotes;

	private final ItemListener quotesListener;
	private String valChooser = "";
	
	private final JButton on, off, inverse;
	
	public QuotesCheckPanel() {

		on = RdWidgets.createButton("T");
		on.addActionListener(new ActionListener() {
            @Override
			public void actionPerformed(ActionEvent e) {
                setAll(true);
            }
        });
		
		off = RdWidgets.createButton("F");
		off.addActionListener(new ActionListener() {
            @Override
			public void actionPerformed(ActionEvent e) {
                setAll(false);
            }
        });
		
		inverse = RdWidgets.createButton("I");
		inverse.addActionListener(new ActionListener() {
            @Override
			public void actionPerformed(ActionEvent e) {
                setAll(null);
            }
        });
				
		quotesListener = new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
            	AbstractButton ab = (AbstractButton)e.getSource();
            	assert( Logger.GUI.isOn ? Logger.GUI.printf("Radio button item [%s] selected [%s]", ab.getText(), ab.isSelected()): true);
				final String newVal = ab.getText()+":"+ab.isSelected();
	           	QuotesCheckPanel.this.firePropertyChange(EVENT_QUOTE_CHOOSER, valChooser, newVal);
				valChooser = newVal;
			}
        };
        
		init();
	}

	@Override
	public void init() {

		setLayout(new GridLayout());
		
		add(RdWidgets.createPanelBoxY(
				new JComponent[]{RdWidgets.createPanelFlow(new JComponent[]{on, off, inverse})},
				new JComponent[]{RdWidgets.createPaneScroll(panelQuotes=RdWidgets.createPanelBoxY(RdWidgets.createBoxCheck(FxTConfig.FOREX_QUOTE, quotesListener)))}
				));
	}

	public void setAll(Boolean b){
		Component[] ac = panelQuotes.getComponents();

		for(int i=0; i<ac.length; i++){
			Component c = ac[i];
			
			if( c instanceof JCheckBox ){
				JCheckBox cb = (JCheckBox )c;
				if( b == null ){
					cb.setSelected(!cb.isSelected());
				} else {
					cb.setSelected(b);
				}
			}
		}
		panelQuotes.revalidate();
		panelQuotes.repaint();
	}

	public void update(Set<String> quotesLabels){
		
		Component[] ac = panelQuotes.getComponents();
		
		for(int i=0; i<ac.length; i++){
			Component c = ac[i];
			
			if( c instanceof JCheckBox ){
				JCheckBox cb = (JCheckBox )c;
				if( quotesLabels.contains(cb.getText()) ){
					quotesLabels.remove(cb.getText());
				} else {
					panelQuotes.remove(c);
				}
			} else {
				panelQuotes.remove(c);
			}
		}

		if( !quotesLabels.isEmpty() ){
			JComponent[] jc = RdWidgets.createBoxCheck(quotesLabels);
			for(int i=0; i<jc.length; i++){
				panelQuotes.add(jc[i]);
			}
			panelQuotes.revalidate();
			panelQuotes.repaint();
		}
		
	}

	
	public String getSelected(){
		String selected = "";
		
		Component[] ac = panelQuotes.getComponents();

		for(int i=0; i<ac.length; i++){
			Component c = ac[i];
			
			if( c instanceof JCheckBox ){
				JCheckBox cb = (JCheckBox )c;
				if( cb.isSelected() ){
					if( selected.length() > 0 )
						selected += ","+cb.getText();
					else
						selected = cb.getText();
				}
					
			}
		}
		return selected;
	}
	
	public void setSelected(Set<String> labels){
		Component[] ac = panelQuotes.getComponents();

		for(int i=0; i<ac.length; i++){
			Component c = ac[i];
			
			if( c instanceof JCheckBox ){
				JCheckBox cb = (JCheckBox )c;
				if( labels.contains(cb.getText())){
					cb.setSelected(true);
				}
			}
		}
	}
}
