package ca.mss.rd.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * @author moskovsk
 * 
 */
public class UtilReflection {

	public static final String module = UtilReflection.class.getName();
	public static final long serialVersionUID = module.hashCode();

	/*
	 * Static basics
	 */
	public static Object instance(String className) throws RuntimeException {
		Class<?> clazz = instantiateClass(className);

		if( isStaticMethodExist("instance", clazz) )
			return callStatic(clazz, "instance");
		else
			throw new RuntimeException("Can not instantiate object for ["+className+"]. Static public method instance() must be declared.");
	}

	public static Class<?> instantiateClass(String className) throws RuntimeException {
		try {
			return Class.forName(className);
		} catch(Throwable t) {
			throw new RuntimeException(String.format("Can not instantiate class for name [%s]", className), t);
		}
	}

	public static boolean isStaticMethodExist(String methodName, Class<?> clazz) {
		try {
			return clazz.getMethod(methodName, new Class<?>[] {}) != null;
		} catch (NoSuchMethodException e) {
			return false;
		}
	}
	
	public static Object callStatic(Class<?> delegateClass, String methodName) throws RuntimeException {
		return callStatic(delegateClass, methodName, new Class<?>[] {}, new Object[] {});
	}

	public static Object callStatic(Class<?> delegateClass, String methodName, Class<?>[] paramTypes, Object[] params)
			throws RuntimeException {
		try {
			Method method = delegateClass.getMethod(methodName, paramTypes);
			return method.invoke(null, params);
		} catch(Throwable t) {
			throw new RuntimeException(String.format("Can not call static method [%s] for class [%s] with params [{%s},{%s}]", methodName, delegateClass.getName(), UtilMisc.toString(paramTypes, ",", ""), UtilMisc.toString(params, ",", "")), t);
		}
	}

	/*
	 * Static methods, fields, setters/getters
	 */

	final static public List<String> getStaticProperties(Class<?> clazz) {
		List<String> fieldNameList = new ArrayList<String>();
		Field[] fields = clazz.getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			int modifier = fields[i].getModifiers();
			if( (modifier & Modifier.FINAL) == 0 && (modifier & Modifier.STATIC) >0 && (modifier & Modifier.PUBLIC) > 0 ) {
				fieldNameList.add(fields[i].getName());
			}
		}
		return fieldNameList;
	}

	final static public List<String> getStaticMethods(Class<?> clazz) {
		List<String> methodsList = new ArrayList<String>();
		Method[] methods = clazz.getDeclaredMethods();
		for (int i = 0; i < methods.length; i++) {
			int modifier = methods[i].getModifiers();
			if( (modifier & Modifier.STATIC) ==0 && (modifier & Modifier.PUBLIC) > 0 ) {
				methodsList.add(methods[i].getName());
			}
		}
		return methodsList;
	}

	final static public List<String> getStaticGetters(Class<?> clazz) {
		List<String> methodsList = new ArrayList<String>();
		Method[] methods = clazz.getDeclaredMethods();
		for (int i = 0; i < methods.length; i++) {
			int modifier = methods[i].getModifiers();
			if( (modifier & Modifier.STATIC) ==0 && (modifier & Modifier.PUBLIC) > 0 ) {
				if( methods[i].getName().startsWith("get") )
					methodsList.add(methods[i].getName());
			}
		}
		return methodsList;
	}

	public static Object getStaticProperty(Object delegateObject, String proName) throws RuntimeException {
		try {
			return delegateObject.getClass().getField(proName).get(delegateObject);
		} catch(Throwable t) {
			throw new RuntimeException(String.format("Can not get static property [%s] for object [%s]", proName, delegateObject), t);
		}
	}

	public static Object getStaticProperty(Class<?> delegateClass, String proName) throws RuntimeException {
		try {
			return delegateClass.getField(proName).get(delegateClass);
		} catch(Throwable t) {
			throw new RuntimeException(String.format("Can not get static property [%s] for class [%s]", proName, delegateClass.getName()), t);
		}
	}

	public static void setStaticProperty( Class<?> delegateClass, String proName, String proValue) throws RuntimeException {
		try {
			Field field = delegateClass.getField(proName);
			String typeClassName = field.getType().getName();
			
			if (typeClassName.equals(String.class.getName())) {
				field.set(null, proValue);
			} else if (typeClassName.equals("long")) {
				field.setLong(null, Long.parseLong(proValue.trim()));
			} else if (typeClassName.equals("int")) {
				field.setInt(null, Integer.parseInt(proValue.trim()));
			} else if (typeClassName.equals("boolean")) {
				field.setBoolean(null, Boolean.parseBoolean(proValue.trim()));
			} else if (typeClassName.equals("double")) {
				field.setDouble(null, Double.parseDouble(proValue.trim()));
			} else if (typeClassName.equals(Boolean.class.getName())) {
				field.set(null, new Boolean(proValue.trim()));
			} else if (typeClassName.equals(Long.class.getName())) {
				field.set(null, new Long(proValue.trim()));
			} else if (typeClassName.equals(Integer.class.getName())) {
				field.set(null, new Integer(proValue.trim()));
			} else if (typeClassName.equals(Double.class.getName())) {
				field.set(null, new Double(proValue.trim()));
			} else if (typeClassName.equals(HashSet.class.getName())) {
				Set<String> tmpSet = readSet(new HashSet<String>(), proValue);
				if (!tmpSet.isEmpty()) {
					field.set(null, tmpSet);
				}
			} else if (typeClassName.equals(HashMap.class.getName())) {
				Map<String,String> tmpSet = readMap(new HashMap<String,String>(), proValue);
				if (!tmpSet.isEmpty()) {
					field.set(null, tmpSet);
				}
			} else if (typeClassName.equals("[Ljava.lang.String;")) {
				List<String> tmpList = new ArrayList<String>();
				StringTokenizer st = new StringTokenizer(proValue, ",");
				while (st.hasMoreTokens()) {
					tmpList.add(st.nextToken());
				}
				if (!tmpList.isEmpty()) {
					String[] arr = new String[tmpList.size()];
					tmpList.toArray(arr);
					field.set(null, arr);
				}
			} else if (typeClassName.equals(List.class.getName())) {
				List<String> tmpList = new ArrayList<String>();
				StringTokenizer st = new StringTokenizer(proValue, ",");
				while (st.hasMoreTokens()) {
					tmpList.add(st.nextToken());
				}
				if (!tmpList.isEmpty()) {
					field.set(null, tmpList);
				}
			} else if (typeClassName.equals(SimpleDateFormat.class.getName())) {
				field.set(null, new SimpleDateFormat(proValue));
			} else if (typeClassName.equals(Character.class.getName())) {
				if (proValue.length() > 0) {
					field.set(null, new Character(proValue.trim().charAt(0)));
				}
			}
		} catch (Throwable t) {
			throw new RuntimeException(String.format("Can not set [%.%=%]", delegateClass.getSimpleName(), proName, proValue), t);
		}
	}

	static private final Map<String,String> readMap(HashMap<String,String> map, String proValue){
		String[] ti = proValue.substring(1, proValue.length()-1).split("=");
		String key = ti[0].trim();
		for(int i=1; i<ti.length; i++){
			String[] tj = ti[i].split(",");
			String value = "";
			for(int j=0,maxj=tj.length-(i==ti.length-1?0:1); j<maxj; j++){
				if( j > 0 )
					value += ",";
				value += tj[j].trim();
			}
			map.put(key, value);
			key = tj[tj.length-1].trim();
		}
		return map;
	}
	
	static private final Set<String> readSet(Set<String> set, String proValue){
		StringTokenizer st = new StringTokenizer(proValue, ",");
		while (st.hasMoreTokens()) {
			set.add(st.nextToken());
		}
		return set;
	}
	
	/*
	 * Object
	 */
	public static Object instantiateObject(String className) throws RuntimeException {
		try {
			return Class.forName(className).newInstance();
		} catch(Throwable t) {
			throw new RuntimeException(String.format("Can not instantiate object for class [%s]", className), t);
		}
	}
	
	public static Object instantiateObject(String className, @SuppressWarnings("rawtypes") Class<?>[] paramTypes, Object[] params) throws RuntimeException {
		try {
			Class<?> cl = Class.forName(className);
			Constructor<?> cons = cl.getConstructor(paramTypes);
			Object obj =  cons.newInstance(params);
			return obj;
		} catch(Throwable t) {
			throw new RuntimeException(String.format("Can not instantiate object for class [%s] with params [{%s},{%s}]", className, UtilMisc.toString(paramTypes, ",", ""), UtilMisc.toString(params, ",", "")), t);
		}
	}

	public static Method getMethod(Object delegateObject, String methodName, @SuppressWarnings("rawtypes") Class<?>[] paramTypes) throws NoSuchMethodException {
		Class<?> delegateClass = delegateObject.getClass();
		return delegateClass.getMethod(methodName, paramTypes);
	}
	
	public static Object call(Object delegateObject, String methodName) throws RuntimeException {
		return call(delegateObject, methodName, new Class<?>[] {}, new Object[] {});
	}

	public static Object call(Object delegateObject, String methodName, @SuppressWarnings("rawtypes") Class<?>[] paramTypes, Object[] params)
			throws RuntimeException {
		try {
			Method method = getMethod(delegateObject, methodName, paramTypes);
			return method.invoke(delegateObject, params);
		} catch(Throwable t) {
			throw new RuntimeException(String.format("Can not call method [%s] for object [%s] with params [{%s},{%s}]", methodName, delegateObject, UtilMisc.toString(paramTypes, ",", ""), UtilMisc.toString(params, ",", "")), t);
		}
	}
		
	/*
	 * Object setters/getters
	 */

	public static Object getter(Object delegateObject, String proName) throws RuntimeException {
		return call(delegateObject, "get" + proName);
	}

	public static void setter(Object delegateObject, String proName, Object proValue) throws RuntimeException {
		call(delegateObject, "set" + proName, new Class<?>[] { proValue.getClass() }, new Object[] { proValue });
	}

	public static String[] getAllGetters(Object delegateObject) throws RuntimeException {
		return getAllGettersExcept(delegateObject, new String[] {});
	}


	public static String[] getAllGettersExcept(Object delegateObject, String[] exceptList) throws RuntimeException {
		List<String> getters = new ArrayList<String>();

		Method[] methods = delegateObject.getClass().getMethods();
		for (int i = 0; i < methods.length; i++) {
			String method = methods[i].getName();
			if (method.startsWith("get")) {
				if (!"getClass".equals(method)) {
					boolean collectIt = true;
					for (int j = 0; j < exceptList.length; j++) {
						if (method.equals(exceptList[j])) {
							collectIt = false;
							break;
						}
					}
					if (collectIt) {
						getters.add(method.substring(3));
					}
				}
			}
		}
		return getters.toArray(new String[getters.size()]);
	}

	public static String[] getAllSettersExcept(Object delegateObject, String[] exceptions) throws RuntimeException {
		List<String> getters = new ArrayList<String>();

		Method[] methods = delegateObject.getClass().getMethods();
		for (int i = 0; i < methods.length; i++) {
			String method = methods[i].getName();
			if (method.startsWith("set")) {
				boolean collectIt = true;
				for (int j = 0; j < exceptions.length; j++) {
					if (method.equals(exceptions[j])) {
						collectIt = false;
						break;
					}
				}
				if (collectIt) {
					getters.add(method.substring(3));
				}
			}
		}
		return getters.toArray(new String[getters.size()]);
	}

}