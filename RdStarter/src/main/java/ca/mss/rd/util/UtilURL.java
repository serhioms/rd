package ca.mss.rd.util;

import java.io.File;
import java.net.URL;
import java.util.StringTokenizer;

import ca.mss.rd.exceprion.RdCanNotFindResource;
import ca.mss.rd.starter.AppEnvironment;


public class UtilURL {

	/**
	 * @param name
	 * @return
	 */
	final public static URL getClaspathURL(String name){
		return Thread.currentThread().getContextClassLoader().getResource(name);
	}
	
	/** 
	* @param fileName
	* @return
	*/ 
	final public static URL getPropertyURL(String fileName){
		String location = "";
		for(StringTokenizer folders=new StringTokenizer(AppEnvironment.propDirList, File.pathSeparator); folders.hasMoreElements(); ) {
			String folder = AppEnvironment.appHome+File.separator+folders.nextToken();
			String path = folder+File.separator+fileName;
			location += (location.length()==0? "": File.separator)+folder;
			File file = new File(path);
			if( file.exists() ){
				try {
					return file.toURI().toURL();
				}catch(Exception e ){
					continue;
				}
			}
		}
		return null;
	}

	final public static URL getResource(String name) throws RdCanNotFindResource {
        URL url = getPropertyURL(name);

		if( url == null ) url = getClaspathURL(name);
		if( url == null ) url = ClassLoader.getSystemResource(name);

		if( url == null ) 	
			throw new RdCanNotFindResource("Can not find resource [name="+name+"]");
		
		return url;
	}

}
