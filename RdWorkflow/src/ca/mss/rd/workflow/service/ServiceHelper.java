package ca.mss.rd.workflow.service;

import java.util.Map;

import ca.mss.rd.util.UtilReflection;

public class ServiceHelper {

	final static public void invoke(Class<?> delegateClass, String methodName, Map parameters) {
		UtilReflection.callStatic(delegateClass, methodName, new Class<?>[]{ Map.class}, new Object[]{parameters});
	}


	final static public void invoke(Object delegateObject, String methodName, Map parameters) {
		UtilReflection.call(delegateObject, methodName, new Class<?>[]{ Map.class}, new Object[]{parameters});
	}

}
