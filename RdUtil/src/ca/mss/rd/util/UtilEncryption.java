package ca.mss.rd.util;


public class UtilEncryption {

	final static public String module = UtilEncryption.class.getName();
	final static public long serialVersionUID = module.hashCode();

	final static public String hexEncode(final String s) {
		return hexEncode(s.getBytes());
	}

	final static public String hexDecode(final String s) {
		return new String(hexDecode(s.getBytes()));
	}

	final static private String convert = "0123456789abcdef";
	// final static private String convert = "0123456789ABCDEF"; // un comment if you want upper case

	final static public String hexEncode(final byte[] ba) {
		String result = "";
		for (int i = 0; i < ba.length; i++) {
			final byte b = ba[i];
			final int high = (b >> 4) & 0x0F;
			final int low = (b & 0x0F);
			result += convert.charAt(high);
			result += convert.charAt(low);
		}
		return result;
	}

	final static public byte[] hexDecode(final byte[] s) {
		final int length = s.length / 2;
		final byte[] buf = new byte[length];
		for (int i = 0; i < length; i++) {
			final int nyb1 = hexval(s[2 * i]);
			final int nyb2 = hexval(s[2 * i + 1]);
			buf[i] = (byte) ((nyb1 * 16) + nyb2);
		}
		return buf;
	}

	// given a char '0' ...'f' or 'F', returns 0..15
	final static private int hexval(final byte c) {
		if ('0' <= c && c <= '9')
			return (c - '0');
		if ('a' <= c && c <= 'f')
			return (c - 'a' + 10);
		if ('A' <= c && c <= 'F')
			return (c - 'A' + 10);
		return 0;
	}
}
