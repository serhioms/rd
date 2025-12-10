package ca.mss.rd.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class UtilString {

	final static public String className = UtilString.class.getName();
	final static public long serialVersionUID = className.hashCode();

	static private String SPACES = "                                                                                                                                               ";
	static public Pattern UNDESCORE_2_UPPER_CASE_CHAR = Pattern.compile( "_([a-zA-Z])" );
	

	static public boolean isNull(String str) {
		return str == null || "".equals(str.trim()) ;
	}

	static public String stringToIdentifier(String str) {
		
		str = str.replaceAll("[^ a-zA-Z0-9]", "");
		
		// remove double spaces
		for(String s; true; str = s){
			s = str.replaceAll("  ", " ");
			if( s.equals(str) ){
				break;
			}
		}
		
		if( str != null && str.length() > 0 ){

			// exchange spaces
			str = str.replaceAll(" ", "_").trim();

			// Make each undescored char uppercase
			Matcher matcher = UNDESCORE_2_UPPER_CASE_CHAR.matcher( str );
			StringBuffer sbuffer = new StringBuffer();
			while( matcher.find() ) {
				matcher.appendReplacement(sbuffer, matcher.group(1).toUpperCase());
			}
			
			matcher.appendTail(sbuffer);
			str = sbuffer.toString();
			
			// Make first char lowercase
			if( str.length() == 1 ){
				str = str.toLowerCase();
			} else {
				str = str.substring(0,1).toLowerCase()+str.substring(1);
			}
		}
		
		return str;
	}

	final static public String capitolize(String str) {
		if( str.length() > 1)
			return str.substring(0,1).toUpperCase()+str.substring(1).toLowerCase();
		else
			return str.toUpperCase();
	}

	@Deprecated
	final static public String buildIdentifier(String str) {
		String newStr = "";
		
		String[] words = str.split("[ ()-]");
		for(int i=0; i<words.length; i++){
			newStr += capitolize(words[i]);
		}
		return newStr;
	}

	public static boolean equals(String a, String b) {
		if( isNull(a) ){
			return isNull(b);
		} else if( isNull(b) ){
			return isNull(a);
		} else 
			return a.equals(b);
	}
	
	final static public boolean isZero(String s){
		return UtilValidate.isEmpty(s)? true: "0".equals(s.trim());
	}
	
	final static public long parseLong(String s){
		return isZero(s)? 0L: Long.parseLong(s);
	}
	
	final static public String alignRight(Object o, int n){
		return alignRight(o.toString().trim(), n);
	}
	
	final static public String alignRight(String s, int n){
		int length = s.length();
		if( length > n){
			s = s.trim();
			length = s.length();
		}
		if( length < n)
			return (SPACES.substring(0, n-length))+s;
		return s;
	}
	
	final static public String alignLeft(String s, int n){
		if( s == null )
			return SPACES.substring(0, n);
		int length = s.length();
		if( length > n){
			s = s.trim();
			length = s.length();
		}
		if( length < n)
			return s+(SPACES.substring(0, n-length));
		return s;
	}
	
	final static public String alignCenter(String s, int n){
		int length = s.length();
		if( length > n){
			s = s.trim();
			length = s.length();
		}
		if( length < n){
			int left = (n-length)/2;
			int right = (n-length)-left;
			return (SPACES.substring(0, left))+s+SPACES.substring(0, right);
		}
		return s;
	}
	
	final static public String getRightString(String str, String[] substr) {
		for(int i=0, idx; i<substr.length; i++){
			if( (idx = str.indexOf(substr[i])) != -1 ){
				return str.substring(idx+substr[i].length());
			}
		}
		return null;
	}

	final static public boolean containsString(String str, String[] substr) {
		for(int i=0; i<substr.length; i++){
			if( str.indexOf(substr[i]) != -1 )
				return true;
		}
		return false;
	}

	final static public String getLeftString(String str, String[] substr) {
		for(int i=0, idx; i<substr.length; i++){
			if( (idx=str.indexOf(substr[i])) != -1 ){
				return str.substring(0, idx);
			}
		}
		return null;
	}

	final static public int toString(Object obj){
		return Integer.parseInt(obj.toString().split("@")[1], 16);
	}

	final static public String getIdentifier(String str){
		return str.replaceAll("[^0-9a-zA-Z_]+", "");
	}

	final static public String getHttpFilename(String http){
		return http.replaceAll(":\\/\\/", "-").replaceAll("\\/+", "-").replaceAll("\\?", "=").replaceAll("[^0-9a-zA-Z_=\\-\\.]+", "");
	}
	
	
	/*
	 * DEPRICATED
	 */
	
    public static String space(int n){
    	switch(n){
    	case 0:  return "";
    	case 1:  return " ";
    	case 2:  return "  ";
    	case 3:  return "   ";
    	case 4:  return "    ";
    	case 5:  return "     ";
    	case 6:  return "      ";
    	case 7:  return "       ";
    	case 8:  return "        ";
    	case 9:  return "         ";
    	case 10: return "          ";
    	case 11: return "           ";
    	case 12: return "            ";
    	case 13: return "             ";
    	case 14: return "              ";
    	case 15: return "               ";
    	case 16: return "                ";
    	case 17: return "                 ";
    	case 18: return "                  ";
    	case 19: return "                   ";
    	case 20: return "                    ";
    	case 21: return "                     ";
    	case 22: return "                      ";
    	case 23: return "                       ";
    	case 24: return "                        ";
    	case 25: return "                         ";
    	case 26: return "                          ";
    	case 27: return "                           ";
    	case 28: return "                            ";
    	case 29: return "                             ";
    	case 30: return "                              ";
    	case 31: return "                               ";
    	case 32: return "                                ";
    	case 33: return "                                 ";
    	case 34: return "                                  ";
    	case 35: return "                                   ";
    	case 36: return "                                    ";
    	case 37: return "                                     ";
    	case 38: return "                                      ";
    	case 39: return "                                       ";
    	case 40: return "                                        ";
    	case 41: return "                                         ";
    	case 42: return "                                          ";
    	case 43: return "                                           ";
    	case 44: return "                                            ";
    	case 45: return "                                             ";
    	case 46: return "                                              ";
    	case 47: return "                                               ";
    	case 48: return "                                                ";
    	case 49: return "                                                 ";
    	case 50: return "                                                  ";
    	default:
    		String spaces = space(n % 50);
    		for(int i=0, size=n/50; i<size; i++){
    			spaces += space(50);
    		}
    		return spaces;
    	}
    }

    public static String[] split1(String str, String sym) {
    	//return str.split(sym, 1);
		int n = str.indexOf(sym);
		if( n == -1 )
			return new String[]{str};
		else
			return new String[]{str.substring(0, n), str.substring(n+sym.length())};
	}

    public static String substring(String str, int n, String pfix) {
    	if( str == null )
    		return "";
    	else if( str.length() > n )
    		return str.substring(0, n)+(pfix==null?"":pfix);
   		else
   			return str;
	}
}


