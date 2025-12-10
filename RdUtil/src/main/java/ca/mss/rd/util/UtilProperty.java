/**
 * 
 */
package ca.mss.rd.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import ca.mss.rd.util.exceptions.FileNotExists;
import ca.mss.rd.util.model.CommandLine;

/**
 * @author moskovsk
 * 
 */
public class UtilProperty {
	
	final static public String className = UtilProperty.class.getName();
	final static public long serialVersionUID = className.hashCode();

	static public boolean DO_POPULATE_PROPERTIES_DEFAULT = false;
	
	static {
		readConstants(UtilProperty.class); 
	}

	
	static private Properties properties = new Properties();

	final public static void readConstants(final Class<?> clazz) {
		final String clazzName = clazz.getSimpleName();
		
		String profile = clazzName+".properties";
		boolean isProfileGenerated = false;

		int count = -1;
		
		try {
			Properties constants = new Properties();

			URL url = readProperties(profile, constants);
			profile = url.getPath();
			
			for(Iterator<String> iter=UtilReflection.getStaticProperties(clazz).iterator(); iter.hasNext(); ){
				String fieldName = (String) iter.next();
				String fieldValue = constants.getProperty(fieldName);
				if (fieldValue != null) {
					try {
						UtilReflection.setStaticProperty(clazz, fieldName, fieldValue);
						if( Debug.isVerboseEnabled  ){
							assert( Logger.DEBUG.isOn ? Logger.DEBUG.printf("Set property [%s.%s=%s] from [profile=%s]", clazzName, fieldName, fieldValue, profile): true);
						}
					}catch(Throwable t){
						if( Logger.ERROR.isOn ) Logger.ERROR.printf(t, "Can not set static properety %s for class %s", fieldName, clazzName);
					} finally {
						count++;
					}
				} else if( Debug.isWarnEnabled ){
					assert( Logger.PROPERTY.isOn ? Logger.PROPERTY.printf("Can not find property [%s.%s] in [profile=%s][populate="+DO_POPULATE_PROPERTIES_DEFAULT+"]", clazzName, fieldName, profile): true);
					if( DO_POPULATE_PROPERTIES_DEFAULT ){
						constants.setProperty(fieldName, toString(UtilReflection.getStaticProperty(clazz, fieldName)));
						isProfileGenerated = true;
					}
				}
			}
			if( isProfileGenerated ){
				writeProperties(profile, constants);
			}
			assert( Logger.PROPERTY.isOn ? Logger.PROPERTY.printf("Load [%d] properties from [%s]", count+1, profile): true);
		}catch(Throwable t){
			assert( Logger.PROPERTY.isOn ? Logger.PROPERTY.printf(t, "Failed to read constants for [%s]", profile): true);
		}
	}

	final public static void writeConstants(final Class<?> clazz) {
		final String clazzName = clazz.getSimpleName();
		String profile = clazzName+".properties";
		int count = -1;
		try {
			Properties constants = new Properties();

			URL url = readProperties(profile, constants);
			profile = url.getPath();
			
			for(Iterator<String> iter=UtilReflection.getStaticProperties(clazz).iterator(); iter.hasNext(); ){
				String fieldName = (String) iter.next();
				constants.setProperty(fieldName, toString(UtilReflection.getStaticProperty(clazz, fieldName)));
			}
			writeProperties(profile, constants);
			if( count != -1){
				assert( Logger.PROPERTY.isOn ? Logger.PROPERTY.printf("Write [%d] properties to [%s]", count+1, profile): true);
			}
		}catch(Throwable t){
			if( Logger.ERROR.isOn ) Logger.ERROR.printf(t, "Failed to write constants for [%s]", profile);
		}
	}

	final static public int getInt(String key){
		try {
			return Integer.parseInt(getProperty(key));
		} catch (Throwable e) {
			throw new RuntimeException("Can not find [int] property ["+key+"]");
		}
	}

	final static public boolean getBoolean(String key){
		try {
			return Boolean.parseBoolean(getProperty(key));
		} catch (Throwable e) {
			throw new RuntimeException("Can not find RD boolean property ["+key+"]");
		}
	}

	final static public String getProperty(String key){
		String value = properties.getProperty(key);
		if( UtilMisc.isEmpty(value) ){
			throw new RuntimeException(String.format("Can not find property [%]",key));
		}
		return value;
	}

	final static public void writeProperties(File file, Properties properties) {
		try {
			 properties.store(new FileOutputStream(file), null);
		} catch (Throwable e) {
			throw new RuntimeException(String.format("Can not write to RD property file [%s][%s]", file, e.getMessage()), e);
		}
	}

	final static public void writeProperties(String fileName, Properties properties) {
		try {
			 properties.store(new FileOutputStream(fileName), null);
		} catch (Throwable e) {
			throw new RuntimeException(String.format("Can not write to RD property file [%s][%s]", fileName, e.getMessage()), e);
		}
	}

	final static public URL readProperties(String fileName, Properties prop) {
		InputStream is = null; 
		int i=0;
		String[] prosubfolders = new String[]{".", "."+File.separator+"bin", "."+File.separator+"prop"};
		try {
			for(; i<prosubfolders.length; i++) {
				File file = new File(prosubfolders[i]+File.separator+fileName);
				if( file.exists() ){
				    prop.load(is = new FileInputStream(file));
				    if( properties != null ){
				    	properties.putAll(prop);
				    }
				    return new URL("file:///"+file.getAbsolutePath().replaceAll("\\\\", "/"));
				}
			}
		} catch (Throwable t) {
			if( Logger.ERROR.isOn ) Logger.ERROR.printf(t, "Can not read properties as file from [file="+prosubfolders[i]+File.separator+fileName+"]");
		} finally {
			if( is != null )
				try{ is.close(); }catch(Throwable t){}
		}

		try {
			URL url = UtilProperty.class.getClassLoader().getResource(fileName);
			if( url != null ){
			    prop.load(is=url.openStream());
			    return url;
			}
		} catch (Throwable t) {
			if( Logger.ERROR.isOn ) Logger.ERROR.printf(t, "Can not read properties vie classloader from [file="+fileName+"]");
		} finally {
			if( is != null )
				try{ is.close(); }catch(Throwable t){}
		}

		throw new FileNotExists("Can not find property file ["+fileName+"]");
	}

	final static public String toString(Object o) {
		String result = "";
		
		if( o instanceof String[] ){
			String[] arr = (String[] )o;
			for(int i=0; i<arr.length; i++){
				if( i > 0 ){
					result += ",";
				}
				result += arr[i];
			}
		} else {
			result = o.toString();
		}
		
		return result;
	}
	
	
	// parse main method String[] args into commandLine
	
	
	final public static void parse(String[] args, CommandLine[] commandLine) {
		try {
			Map<String, String> values = new HashMap<String, String>();
			Map<String, String> synonyms = new HashMap<String, String>();
			
			for(CommandLine cm: commandLine ){
				synonyms.put(cm.getParameter().ident, cm.getParameter().name);
				
				String[] domains = cm.getParameter().domain.split("\\|");
				if( domains.length > 0 ){
					for(String domain: domains){
						synonyms.put(domain.toLowerCase(), cm.getParameter().name);
					}
				}
			}
			
			for (String par : args) {
				if (par.startsWith("--")) {
					populateParam(par.substring(2), values, synonyms);
				} else if (par.startsWith("-")) {
					populateParam(par.substring(1), values, synonyms);
				} else {
					populateParam(par, values, synonyms);
				}
			}
			
			for(CommandLine cm: commandLine ){
				if( values.containsKey(cm.getParameter().name) ){
					cm.getParameter().value = values.remove(cm.getParameter().name);
					if( "help".equalsIgnoreCase(cm.getParameter().name) ){
						System.out.println(populateHelp(commandLine));
						System.exit(0);
					}
				} else if( cm.getParameter().isMandatory ) {
					throw new RuntimeException(String.format("Mandatory parameter not defined: %s", cm.getParameter().name));
				}
			}
			
			if( !values.isEmpty() ){
				throw new RuntimeException(String.format("Unexpected parameter(s): %s", values.keySet()));
			}
		} catch (Throwable t) {
			System.out.println(populateHelp(commandLine));
			throw new RuntimeException(String.format("Failed to parse command line:\n\n%s\n", UtilMisc.toString(args, " ", "")), t);
		}
	}

	final private static void populateParam(String arg, Map<String, String> map, Map<String, String> synonyms) {
		String[] arr = UtilString.split1(arg, "=");
		if( arr.length > 1){
			if( synonyms.containsKey(arr[0].toLowerCase()) )
				map.put(synonyms.get(arr[0].toLowerCase()), arr[1]);
			else
				map.put(arr[0].toLowerCase(), arr[1]);
		} else if( synonyms.containsKey(arr[0].toLowerCase()) )
			map.put(synonyms.get(arr[0].toLowerCase()), arr[0]);
		else
			map.put(arr[0].toLowerCase(), null);
	}
	

	final private static String populateHelp(CommandLine[] commandLine) {
		String usage = "Usage: ", descr="", command="", help="";

		
		for(CommandLine cm: commandLine ){
			if( cm.getParameter().isMandatory ){
				command += String.format("--%s=%s ", cm.getParameter().name, (cm.getParameter().def!=null? cm.getParameter().def: "<"+cm.getParameter().domain.split("\\|")[0]+">"));
			}
		}
		
		for(CommandLine cm: commandLine ){
			if( !cm.getParameter().isMandatory ){
				if( "help".equalsIgnoreCase(cm.getParameter().name) )
					command += String.format("[--%s] ", cm.getParameter().name);
				else
					command += String.format("[--%s=%s] ", cm.getParameter().name, (cm.getParameter().def!=null? cm.getParameter().def: "<"+cm.getParameter().domain.split("\\|")[0]+">"));
			}
		}

		int width1 = 0;
		for(CommandLine cm: commandLine ){
			if( !"help".equalsIgnoreCase(cm.getParameter().name) ){
				String str1 = String.format("-%s, --%s=<%s>", cm.getParameter().ident, cm.getParameter().name, cm.getParameter().domain);
				width1 = Math.max(width1, str1.length()+5);
			}
		}
		
		for(CommandLine cm: commandLine ){
			if( "help".equalsIgnoreCase(cm.getParameter().name) )
				help = cm.getParameter().help;

			descr += String.format("%-"+width1+"s %s%s\n"
						,(cm.getParameter().name.equalsIgnoreCase(cm.getParameter().domain)?
												 String.format("-%s, --%s", cm.getParameter().ident, cm.getParameter().domain)
												:String.format("-%s, --%s=<%s>", cm.getParameter().ident, cm.getParameter().name, cm.getParameter().domain)) 
						,("help".equalsIgnoreCase(cm.getParameter().name)? "this screen...": cm.getParameter().help) 
						,(cm.getParameter().def==null?"":String.format("; default value is <%s>", cm.getParameter().def)));
		}
		
		return String.format("%s%s\n%s\n\nMandatory arguments to long options are mandatory for short options too.\n%s\n", usage, command, help, descr);
	}

}