package ca.mss.rd.flow.tools.prop;


public enum ChecksumAlgoritm {

	    MD_5("MD5"),
	    SHA_1("SHA1"),
	    SHA_256("SHA256");

	    private final String value;

	    ChecksumAlgoritm(String v) {
	        value = v;
	    }

	    public String value() {
	        return value;
	    }

	    public static ChecksumAlgoritm fromValue(String v) {
	        for (ChecksumAlgoritm c: ChecksumAlgoritm.values()) {
	            if (c.value.equals(v)) {
	                return c;
	            }
	        }
	        throw new IllegalArgumentException(v);
	    }

	}
