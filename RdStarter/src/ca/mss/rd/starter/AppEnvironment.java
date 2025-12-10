package ca.mss.rd.starter;

import java.io.File;

final public class AppEnvironment {

	final public static String module = AppEnvironment.class.getName();
	final public static long serialVersionUID = module.hashCode();
	
	final public static String appHome = new File(defaultValue(System.getProperty("home"), defaultValue(System.getenv("RD_HOME"), "."))).getAbsolutePath();

	final public static String propDirList = defaultValue(System.getProperty("prop"), "prop");
	
	final public static String libDirList = defaultValue(System.getProperty("lib"), "lib");;
	
	final public static boolean isDebug = System.getProperty("DEBUG") != null;

	final public static String mainClassName = System.getProperty("main");

	/**
	 * @param val
	 * @param def
	 * @return
	 */
	final private static String defaultValue(String val, String def){
		return val == null? def: val;
	}
}
