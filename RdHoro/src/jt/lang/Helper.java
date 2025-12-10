package jt.lang;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.URL;
import java.util.Properties;

import jt.JTException;
class JTLangNotSupported extends JTException { 
	JTLangNotSupported() {
		super("Language not supported");
	}
}

 class Res {
            public URL getResource(String  res) {
                return this.getClass().getResource(res);
            }
        };

public abstract class Helper {

	/**
	 * @param args
	 */
    
	static Properties prop = null;
	static String langCode = "kan-en"; 
        

	public static String getTithiName(int i) throws JTException {
		String key = "tithi-" + i;
		if (prop == null ) {
			String fileName = "tithi-"+ langCode + "-asc.properties";
			prop = new Properties();
			try {  
                                prop.load(JTLangNotSupported.class.getClassLoader().getResourceAsStream(fileName));
			} catch (Exception e) {
				e.printStackTrace();
				throw (new JTException("Unable to locate/read property file [" + fileName + "] having tithi names for current language " + langCode));
			}
		}
		return prop.getProperty(key);
	}
	
	public static void main(String[] args) throws Exception{
		// TODO test
		
		FileOutputStream out = new FileOutputStream(new File("t.html"));
		PrintWriter pr = new PrintWriter(new OutputStreamWriter(out,"UTF-16"));
		setLangCode("kan-en");
		for (int i=1 ; i<31; i++)
			pr.println(i + " " + Helper.getTithiName(i));
		pr.flush();
	}

	public static String getLangCode() {
		return langCode;
	}

	public static void setLangCode(String langCode) throws JTLangNotSupported {
		checkLangSupported(langCode);
		Helper.langCode = langCode;
	}
	public static void checkLangSupported (String langCode) throws JTLangNotSupported {
		if (langCode == "kan") return;
		if (langCode == "kan-en") return;
		throw new JTLangNotSupported();
	}
}
