package ca.mss.rd.util.model;



public enum StandardAlgorithm {
	 MD2("MD2")
	,MD5("MD5")
	,SHA_1("SHA-1")
	,SHA_256("SHA-256")
	,SHA_384("SHA-384")
	,SHA_512("SHA-512")
	;
	
	final public String algorithm;
	
	private StandardAlgorithm(String algorithm) {
		this.algorithm = algorithm;
	}
	
	public static String algorithm(String name){
		return valueOf(name.toUpperCase().replace("-", "_")).algorithm;
	}
}
