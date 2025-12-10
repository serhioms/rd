package ca.mss.rd.util;

import java.io.IOException;
import java.net.URL;
import java.util.Properties;

import org.apache.log4j.Logger;

public class Debug extends Logger {

	static public boolean isVerboseEnabled = true;
	static public boolean isDebugEnabled = true;
	static public boolean isInfoEnabled = true;
	static public boolean isWarnEnabled = true;
	static public boolean isErrorEnabled = true;
	static public boolean isFatalEnabled = true;

	static public boolean isAsyncronos = false;

	static {
		Properties properties = new Properties();
		try {
			ClassLoader cl = Debug.class.getClassLoader();
			URL log4jprops = cl.getResource("log4j.properties");
			if (log4jprops != null) {
				properties.load(log4jprops.openStream());

				if (UtilValidate.isNotEmpty(properties.get("isAsyncronos")))
					isAsyncronos = properties.get("isAsyncronos").toString().equals("true");

				if (UtilValidate.isNotEmpty(properties.get("isVerboseEnabled")))
					isVerboseEnabled = properties.get("isVerboseEnabled").toString().equals("true");

				if (UtilValidate.isNotEmpty(properties.get("isDebugEnabled")))
					isDebugEnabled = properties.get("isDebugEnabled").toString().equals("true");

				if (UtilValidate.isNotEmpty(properties.get("isInfoEnabled")))
					isInfoEnabled = properties.get("isInfoEnabled").toString().equals("true");

				if (UtilValidate.isNotEmpty(properties.get("isWarnEnabled")))
					isWarnEnabled = properties.get("isWarnEnabled").toString().equals("true");

				if (UtilValidate.isNotEmpty(properties.get("isErrorEnabled")))
					isErrorEnabled = properties.get("isErrorEnabled").toString().equals("true");

				if (UtilValidate.isNotEmpty(properties.get("isFatalEnabled")))
					isFatalEnabled = properties.get("isFatalEnabled").toString().equals("true");
			}
		} catch (IOException e) {
		}

	}

	private Debug() {
		super("");
	}
}
