package ca.mss.rd.trade.markit;



public class RdMetadataMarkIt {

	final static public String module = RdMetadataMarkIt.class.getName();
	final static public long serialVersionUID = module.hashCode();
	

	final static public String MARKIT_REQUEST_URL = "https://www.markit.com/export.jsp";
	final static public String MARKIT_REQUEST_USERNAME = "user"; 
	final static public String MARKIT_REQUEST_PASSWORD = "password";
	final static public String MARKIT_REQUEST_DATE = "date";
	final static public String MARKIT_REQUEST_FORMAT = "format";
	final static public String MARKIT_REQUEST_REPORT = "report";
	final static public String MARKIT_REQUEST_TYPE = "type";
	final static public String MARKIT_REQUEST_FAMILY = "family";
	final static public String MARKIT_REQUEST_VERSION = "version";

	
	
	static public enum Format {
		XML("xml"),
		CSV("csv"),
		TAB("csv");
		
		final public String val;
		private Format(String toString) {
			this.val = toString;
		}
	}
	
	static public enum Report {
		Composites("COMPOSITES"),
		XComposites("XCOMPOSITES"),
		LiquidityMetrics("LIQUIDITY_METRICS"),
		AllContributions("AllContributions");
		
		final public String val;
		private Report(String toString) {
			this.val = toString;
		}
	}
	
	static public enum Type {
		CredIndex("credindex"),
		AbsCredIdx("abscredidx"),
		CDS("cds");
		
		final public String val;
		private Type(String toString) {
			this.val = toString;
		}
	}
	
	static public enum Family {
		ITRAXX_EUROPE("ITRAXX-EUROPE"),
		ABX_HE("ABX.HE"),
		CDX("CDX");
		
		final public String val;
		private Family(String toString) {
			this.val = toString;
		}
	}

	static public enum ZipItem {
		one(1),
		two(2);
		
		final public int val;
		private ZipItem(int toInt) {
			this.val = toInt;
		}
	}
	
	static public enum Version {
		one("1"),
		two("2"),
		three("3"),
		four("4"),
		five("5"),
		six("6"),
		seven("7"),
		eight("8"),
		night("9"),
		ten("10");
		
		final public String val;
		private Version(String toString) {
			this.val = toString;
		}
	}

}
