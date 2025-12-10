package ca.mss.rd.util.model;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public enum StandardCharset {
	 ISO_8859_1(StandardCharsets.ISO_8859_1)
	,US_ASCII(StandardCharsets.US_ASCII)
	,UTF_16(StandardCharsets.UTF_16)
	,UTF_16BE(StandardCharsets.UTF_16BE)
	,UTF_16LE(StandardCharsets.UTF_16LE)
	,UTF_8(StandardCharsets.UTF_8)
	;
	
	final public Charset charset;

	private StandardCharset(Charset charset) {
		this.charset = charset;
	}
	
	public static Charset charset(String name){
		return valueOf(name.toUpperCase()).charset;
	}
}
