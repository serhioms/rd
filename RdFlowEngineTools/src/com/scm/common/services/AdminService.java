package com.scm.common.services;

import java.io.FileInputStream;
import java.util.Enumeration;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.scm.common.crypto.FileEncrypter;

public class AdminService
{
	static private AdminService _instance = null;
	private Properties _conf = null;
	private String _confFileName;

	private AdminService()
	{
		initResource();
	}

	public static synchronized AdminService getInstance()
	{
		if (_instance == null) {
			_instance = new AdminService();
		}
		return _instance;
	}

	public Properties getConfigProperties()
	{
		return _conf;
	}


	public String getConfFileName()
	{
		return _confFileName;
	}

	private void overwriteProperties()
	{
		Enumeration names = _conf.propertyNames();
		while (names.hasMoreElements()) {

			String name = (String)names.nextElement();

			if (System.getProperty(name) != null) {
				System.out.println("OVERWRITING : " + name + " ->" + System.getProperty(name));
				_conf.setProperty(name, System.getProperty(name));
			}
		}
	}
	
	private void initResource()
	{
		// load configurations
		if (_conf == null) {
         _conf = new Properties();
         _confFileName = System.getProperty("SCM_CONFIG_FILE");
         if (_confFileName == null) {
            throw new RuntimeException("System property SCM_CONFIG_FILE is undefined.");
         }

         String[] paths = _confFileName.split(",");
         for (int i = 0; i < paths.length; i++) {
            String path = paths[i];
            boolean loaded = false;
            System.out.println("Loading properties file: " + path);

            // try to load from file system first
            try {
                if (path.endsWith(".bin")) {
                   _conf.load(FileEncrypter.getInputStream(path));
                }
                else {
                   _conf.load(new FileInputStream(path));
                }
                loaded = true;
             }
             catch (Exception e) {
             	//System.err.println("Error: Could not load properties successfully");
             	//e.printStackTrace();
             }

          // otherwise, use classpath
             if (!loaded) {
                try {
                	path = path.replaceAll("\\\\", "/");
                   _conf.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(
                         path));
                }
                catch (Exception x) {
                   System.out.println("Warning: Error accessing " + path + " - NO CONFIGURATION LOADED");
                }
             }
         }
         
         // try to load from file system first
         /*
         try {
            _conf.load(new FileInputStream(_confFileName));
         }
         catch (IOException ie) {}
                           
         // otherwise, use classpath
         if (_conf.size() == 0) {
            try {
               _conf.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(_confFileName));
            }
            catch (Exception x) {
               throw new RuntimeException("Unable to load " + _confFileName, x);
            }
         }
         */         

         // override config file values with command line -D definitions
         if (System.getProperty("OVERWRITE_PROPERTIES") != null) {
            overwriteProperties();
         }

         // perform substitutions for ${} definitions
         for (Enumeration e = _conf.keys(); e.hasMoreElements(); ) {
            String key = (String)e.nextElement();
            String value = _conf.getProperty(key);
            Pattern p = Pattern.compile("\\$\\{[^\\}]*\\}");
            Matcher m = p.matcher(value);
            boolean found = m.find();
            if (found) {
               while (found) {
                  int replaced = 0;
                  int undefined = 0;
                  StringBuffer buf = new StringBuffer();
                  while (found) {
                     String k = value.substring(m.start() + 2, m.end() - 1);
                     if (k.equals(key))
                        throw new IllegalArgumentException("Cyclic definition: " + k);

                     String v = _conf.getProperty(k);
                     if (v == null) {
                        v = m.group();
                        undefined++;
                     }

                     m.appendReplacement(buf, escapeDollar(v));
                     replaced++;
                     found = m.find();
                  }
                  m.appendTail(buf);
                  value = buf.toString();

                  if (replaced == undefined)
                     break;

                  m = p.matcher(value);
                  found = m.find();
               }
               _conf.setProperty(key, value);
            }
         }
      }

	}

   private static String escapeDollar(String txt)
   {
      StringBuffer buf = new StringBuffer(txt);
      for (int i = 0; (i = buf.indexOf("$", i)) >= 0; i += 2)
         buf.insert(i, '\\');
      return buf.toString();
   }

	public static String getJavaString(Object sqlValue)
	{
		if (sqlValue == null)
			return "";
		return String.valueOf(sqlValue);
	}

	public String getProperty(String key)
	{
		return _conf.getProperty(key);
	}

	/*
	public com.scm.common.jdbc.DatabaseAccess getDatabaseAccess()
	{
		throw new java.lang.UnsupportedOperationException();
	}
	*/
}
