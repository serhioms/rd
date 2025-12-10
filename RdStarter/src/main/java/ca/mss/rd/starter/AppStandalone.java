package ca.mss.rd.starter;

import java.io.File;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

final public class AppStandalone {
	
	final static public String module = AppStandalone.class.getName();
	final static public long serialVersionUID = module.hashCode();
	final private static Logger logger = Logger.getLogger(module);
	
	final public static AppClasspath classPath = new AppClasspath(System.getProperty("java.class.path"));
	final public static Set<String> skipFolders = toSet(".cvs", ".svn");
	final public static Set<String> loadExts = toSet(".jar", ".zip",  ".class", ".properties", ".xml", ".xsd");

	public AppStandalone(){
		super();
	}

	final static public void showUsage(String[] args) {
		
		logger.log(Level.INFO, "Command line:\n"
					+ "java "
					+ (AppEnvironment.isDebug?"-DDEBUG ":"-DnoDEBUG")
					+ " -Dhome="+AppEnvironment.appHome
					+ " -Dprop="+AppEnvironment.propDirList
					+ " -Dlib="+AppEnvironment.libDirList
					+ " -Dmain="+AppEnvironment.mainClassName
					+ " -jar jdstarter.jar "+toString(args)+"\n");
		
		logger.log(Level.INFO, "Command options:\n"
					+ "java [-DDEBUG|-DnoDEBUG] [-Dhome=HOME-PATH] [-Dlib=DIR-LIST] [-Dprop=DIR-LIST] [-Dmain=FULL-CLASS*NAME] [***] -jar startup.jar [-? | -help] [args]\n"
					+ "[-DDEBUG]                put on starter debug messages mode (noDEBUG or just miss it)\n"
					+ "[-Dhome=HOME-PATH]       path to application home ('.' by default)\n"
					+ "[-Dprop=DIR-LIST]        semicolon delemited list of property folders ('.\\prop' by default)\n"
					+ "[-Dlib=DIR-LIST]         semicolon delemited list of library folders ('.\\lib' by default)\n"
					+ "[-Dmain=FULL-CLASSNAME]  name of main class to start\n"
					+ "[***]                    any other parameters (see java -help)\n"
					+ "[-? | -help]             print this\n"
					+ "[args]                   list of arguments passed directly to the main class");
	}

	/**
	 * @param args
	 * @return
	 */
	final static public String toString(String[] args){
		StringBuffer buf = new StringBuffer();
		for(int i=0; i<args.length; i++){
			if( i > 0 ) buf.append(",");
			buf.append(args[i]);
		}
		return buf.toString();
	}

	/**
	 * 
	 * @param args
	 * @throws Exception
	 */
	final static public void main(String[] args) {
		
		// show proper usage message
		String firstArg = args.length > 0 ? args[0] : "";
		if( firstArg.equals("-help") || firstArg.equals("-?") ) {
			showUsage(args);
			System.exit(0);
		}

		// load libs into classpath
		for(StringTokenizer path=new StringTokenizer(AppEnvironment.libDirList, File.pathSeparator); path.hasMoreElements(); ) {
			generateClasspath(AppEnvironment.appHome+File.separator+path.nextToken(), true);
		}
        
		// load properties into classpath
		for(StringTokenizer path=new StringTokenizer(AppEnvironment.propDirList, File.pathSeparator); path.hasMoreElements(); ) {
			generateClasspath(AppEnvironment.appHome+File.separator+path.nextToken(), true);
		}
        
		// set classpath system property
		System.setProperty("java.class.path", classPath.toString());
		System.setProperty("rd.home", AppEnvironment.appHome);
		ClassLoader classloader = classPath.getClassLoader();
		
		// set thread classloader
		Thread.currentThread().setContextClassLoader(classloader);
	
		if( AppEnvironment.isDebug ) {
			logger.info("Startup Classloader> " + classloader.toString());
			logger.info("Startup Classpath> " + classPath.toString());
		}
		
		// run main
		try {
			logger.info("Try to start> " + AppEnvironment.mainClassName+"("+toString(args)+")");
			Class<?> delegateClass  = classloader.loadClass(AppEnvironment.mainClassName);
			Method method = delegateClass.getMethod("main", new Class<?>[] { args.getClass() });
			method.invoke(null, new Object[] {args});
		}catch(Throwable t) {
			logger.log(Level.SEVERE, "Failed to run main() for [className="+AppEnvironment.mainClassName+"]", t);
			System.exit(1);
		}
	}
	

	/** 
	 * Recursively load all files into classpath 
	 * 
	 * @param path
	 * @param recurse
	 */
	final static protected void generateClasspath(String path, boolean recurse) {
		
		File folder = new File(path);
		
		if( folder.exists() ) {
			
			File files[] = folder.listFiles();
			
			for (int i = 0; i < files.length; i++) {
			
				String file = files[i].getName().toLowerCase();
				
				if( files[i].isDirectory() && !skipFolders.contains(file) && recurse ) {
					
					try {
						generateClasspath(files[i].getCanonicalPath(), recurse);
					} catch(Throwable t ){
						throw new RuntimeException("Can not load resources from folder "+file, t);
					}
				} else {
					
					for(String loadExt: loadExts ){
						if( file.contains(loadExt) ){
							classPath.addComponent(files[i]);
						}
					}
				}
			}
		} else {
			logger.log(Level.WARNING, "Can not find folder: "+folder);
		}
	}

	/**
	 * @param vals
	 * @return
	 */
	final static protected Set<String> toSet(String... vals){
		Set<String> set = new HashSet<String>();

		for(String v: vals){
			set.add(v);
		}
		
		return set;
	}	
	
}
