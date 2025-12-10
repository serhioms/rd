package ca.mss.rd.util.model;

public interface CommandLine {

	final static public boolean MANDATORY = true;
	final static public boolean OPTIONAL = false;
	final static public boolean HELP = OPTIONAL;
	
	public Argument getParameter();
}
