package ca.mss.rd.util.model;


public class Argument {

	final public boolean isMandatory;
	final public String help, ident, def, domain, name;
	
	public String value;

	public Argument(String name, String ident, String domain, String help, boolean isMandatory, String def) {
		this.isMandatory = isMandatory;
		this.help = help;
		this.def = def;
		this.name = name.toLowerCase();
		this.ident = ident.toLowerCase();
		this.value = def;
		this.domain = domain;
	}
	
}
