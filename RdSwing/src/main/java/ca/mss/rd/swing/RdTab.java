package ca.mss.rd.swing;

import java.awt.Component;

import javax.swing.Icon;
import javax.swing.JTabbedPane;

import ca.mss.rd.util.UtilReflection;

public class RdTab extends RdMetadata {
	
	public RdTab(RdTabModel model){
		super(model.getIdent(), model.getProps().get(model.getIdent()));
	}
	
	final public String getLabel(){
		return props==null? ident: getProperty("Label");
	}
	
	final public int getMnemonic(){
		return props==null? -1: props.get("Mnemonic")==null? -1: Integer.parseInt(props.get("Mnemonic"));
	}
	
	final public String getTip(){
		return getProperty("Tip");
	}
	
	final public String getClazz(){
		return getProperty("Clazz");
	}
	
	final public Icon getIcon(){
		return props==null? null: super.getIcon(props.get("Icon"));
	}
	
	final static public void createTab(JTabbedPane tabbedPane, RdTabModel model) {
		RdTab md = new RdTab(model);
		
		String className = md.getClazz();
		
		Component tabpanel = (Component )UtilReflection.instantiateObject(className, new Class<?>[]{RdTabModel.class}, new Object[]{model});

		tabbedPane.addTab(md.getLabel(), md.getIcon(), tabpanel, md.getTip());
		tabbedPane.setMnemonicAt(0, md.getMnemonic());
	}

}
