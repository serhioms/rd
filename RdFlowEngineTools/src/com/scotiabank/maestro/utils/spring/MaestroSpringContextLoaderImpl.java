package com.scotiabank.maestro.utils.spring;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import ca.mss.rd.util.Logger;

public final class MaestroSpringContextLoaderImpl {
	private static final Map<String, AnnotationConfigApplicationContext> APPLICATION_CONTEXT_MAP = new HashMap<String, AnnotationConfigApplicationContext>();
	
	private MaestroSpringContextLoaderImpl() {		
	}
	
	public static synchronized AnnotationConfigApplicationContext openApplicationContext(Class<?>... appConfigClasses) {
		String contextKey = generateAppConfigClassesKey(appConfigClasses);
		AnnotationConfigApplicationContext applicationContext = APPLICATION_CONTEXT_MAP.get(contextKey);
		if(applicationContext==null) {
			assert( Logger.SPRING.isOn ? Logger.SPRING.printf("OPENING ApplicationContext: [" + contextKey + "]"): true);
			applicationContext = new AnnotationConfigApplicationContext(appConfigClasses);
			applicationContext.registerShutdownHook();
			APPLICATION_CONTEXT_MAP.put(contextKey, applicationContext);
		}
		return applicationContext;
	}

	public static synchronized void closeAllApplicationContexts() {
		Set<String> keySet = new HashSet<String>(APPLICATION_CONTEXT_MAP.keySet());
		for (String contextKey : keySet) {
			closeApplicationContext(contextKey);					
		}
	}
	
	public static synchronized void closeApplicationContext(Class<?>...appConfigClasses) {
		String contextKey = generateAppConfigClassesKey(appConfigClasses);
		closeApplicationContext(contextKey);		
	}

	private static void closeApplicationContext(String contextKey) {
		AnnotationConfigApplicationContext applicationContext = APPLICATION_CONTEXT_MAP.get(contextKey);
		if(applicationContext!=null) {
			assert( Logger.SPRING.isOn ? Logger.SPRING.printf("CLOSING ApplicationContext: [" + contextKey + "]"): true);
			applicationContext.close();
			APPLICATION_CONTEXT_MAP.remove(contextKey);
		}
	}

	
	private static String generateAppConfigClassesKey(Class<?>... appConfigClasses) {
		StringBuilder key = new StringBuilder();
		for (Class<?> appConfigClass : appConfigClasses) {
			if(key.length()>0) {
				key.append("|");
			}
			key.append(appConfigClass.getName());
		}
		return key.toString();
	}
	
}
