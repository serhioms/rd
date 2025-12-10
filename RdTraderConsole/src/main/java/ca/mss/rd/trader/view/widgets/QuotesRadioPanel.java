package ca.mss.rd.trader.view.widgets;

import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Set;

import javax.swing.AbstractButton;
import javax.swing.JComponent;
import javax.swing.JRadioButton;

import ca.mss.rd.swing.RdPanel;
import ca.mss.rd.swing.RdWidgets;
import ca.mss.rd.trader.FxTConfig;

public class QuotesRadioPanel extends RdPanel {

	final static public String module = QuotesRadioPanel.class.getName();
	final public static long serialVersionUID = module.hashCode();

	final static public String EVENT_QUOTE_CHOOSER = "QuoteChooser";
	
	private JComponent panelQuotes;
	private final ItemListener quotesListener;
	
	private String valChooser = "";
	
	public QuotesRadioPanel() {

		quotesListener = new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
            	AbstractButton ab = (AbstractButton)e.getSource();
				final String newVal = ab.getText()+":"+ab.isSelected();
				QuotesRadioPanel.this.firePropertyChange(EVENT_QUOTE_CHOOSER, valChooser, newVal);
				valChooser = newVal;
			}
        };
        
		init();
	}

	@Override
	public void init() {
		setLayout(new GridLayout());
		add(RdWidgets.createPaneScroll(panelQuotes=RdWidgets.createPanelBoxY(RdWidgets.createBoxRadio(FxTConfig.FOREX_QUOTE, quotesListener))));
	}

	public void update(Set<String> quotesLabels){
		
		Component[] ac = panelQuotes.getComponents();
		
		for(int i=0; i<ac.length; i++){
			Component c = ac[i];
			
			if( c instanceof JRadioButton ){
				JRadioButton cb = (JRadioButton )c;
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
			JComponent[] jc = RdWidgets.createBoxRadio(quotesLabels);
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
			
			if( c instanceof JRadioButton ){
				JRadioButton cb = (JRadioButton )c;
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
			
			if( c instanceof JRadioButton ){
				JRadioButton cb = (JRadioButton )c;
				if( labels.contains(cb.getText())){
					cb.setSelected(true);
				}
			}
		}
	}
	
	public void setSelected(String label){
		Component[] ac = panelQuotes.getComponents();

		for(int i=0; i<ac.length; i++){
			Component c = ac[i];
			
			if( c instanceof JRadioButton ){
				JRadioButton cb = (JRadioButton )c;
				if( label.contains(cb.getText())){
					cb.setSelected(true);
					return;
				}
			}
		}
	}
	
	public void freez(){
		Component[] ac = panelQuotes.getComponents();

		for(int i=0; i<ac.length; i++){
			Component c = ac[i];
			
			if( c instanceof JRadioButton ){
				JRadioButton cb = (JRadioButton )c;
				cb.setEnabled(false);
			}
		}
	}
	
	public void enable(){
		Component[] ac = panelQuotes.getComponents();

		for(int i=0; i<ac.length; i++){
			Component c = ac[i];
			
			if( c instanceof JRadioButton ){
				JRadioButton cb = (JRadioButton )c;
				cb.setEnabled(true);
			}
		}
	}
	
}
