package ca.mss.rd.util.io;

import java.io.File;
import java.io.FilenameFilter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WildcardFilter implements FilenameFilter {

	public WildcardFilter(String pattern) {
		pattern_m = Pattern.compile(pattern);

	}

	public boolean accept(File dir, String name) {

		Matcher m = pattern_m.matcher(name);
		return m.matches();

	}

	private Pattern pattern_m = null;

}