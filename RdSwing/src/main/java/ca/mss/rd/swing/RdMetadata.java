package ca.mss.rd.swing;

import java.util.HashMap;
import java.util.Map;

import javax.swing.Icon;
import javax.swing.ImageIcon;

public class RdMetadata {
	
	static private Map<String,Icon> cache = new HashMap<String,Icon>(); 
	
	final protected Map<String,String> props;
	final protected String ident;
	 
	public RdMetadata(String ident, Map<String,String> props){
		this.ident = ident;
		this.props = props;
	}
	
	final public String getIdent(){
		return ident;
	}
	
	final public String getProperty(String key){
		return props==null? null: props.get(key);
	}
	
	final public Icon getIcon(String path){
		Icon icon = null;
		if( path != null )
			if( cache.containsKey(path) )
				icon = cache.get(path);
			else
				cache.put(path, icon=new ImageIcon(path));
		return icon;
	}
	
}
