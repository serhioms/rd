package ca.mss.rd.tools.expression;

import gnu.jel.CompiledExpression;
import gnu.jel.Evaluator;
import gnu.jel.Library;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ca.mss.rd.util.UtilString;

public final class JelUtil {

	private static final String VALID_TOKEN_VARIABLE_REGEX = "\\$\\{[\\#?a-zA-Z0-9_\\-\\.]+}";
	private static final Pattern VALID_TOKEN_VARIABLE_PATTERN = Pattern.compile(VALID_TOKEN_VARIABLE_REGEX);

	private static final Map<String, CompiledExpression> cach = new HashMap<String, CompiledExpression>();

	public static final boolean isExpression(String exprValue) {
		if( !UtilString.isNull(exprValue) ) {
			Matcher matcher = VALID_TOKEN_VARIABLE_PATTERN.matcher(exprValue);
			return matcher.find();
		} else {
			return false;
		}
	}

	public static final String detokenizedExpression(String expr) {
		String dexpr = "";
		int end = -1;
		int start;
		int length = expr.length();
		
		for(Matcher matcher = VALID_TOKEN_VARIABLE_PATTERN.matcher(expr); matcher.find(); ){
			start = matcher.start();
			if( end > 0 && start > end ){
				dexpr += "+\""+expr.substring(end, start)+"\"+";
			}
			end = matcher.end();
			dexpr += expr.substring(start+2, end-1);
		}

		if( end > -1 ){
			if( end < length )
				dexpr += "+\""+expr.substring(end)+"\"";
		}
		
		return dexpr;
	}
	
	public static final CompiledExpression getCompiledExpression(String expr) {
		CompiledExpression cexpr = cach.get(expr);
		if (cexpr == null) {
			try {
				cexpr = Evaluator.compile(expr, JelLibrary.Singlton.instance.ref);
				cach.put(expr, cexpr);
			} catch (Exception e) {
				throw new RuntimeException(String.format("Can not compile [expr=%s] due to %s", expr, e.getMessage()), e);
			}

		}
		return cexpr;
	}

	/*
	 * Boolean
	 */
	public static final boolean evaluateBool(String expression, Map<String, Object> properties) {
		return (Boolean )evaluateObj(expression, properties);
	}

	public static final boolean evaluateBool(CompiledExpression compiledExpression, Map<String, Object> properties) {
		return (Boolean )evaluateObj(compiledExpression, properties);
	}

	/*
	 * Object
	 */
	public static final Object evaluateObj(String expression, Map<String, Object> properties) {
		JelResolver.Singlton.instance.ref.setProperties(properties);
		return evaluateObj(getCompiledExpression(expression), properties);
	}

	public static final Object evaluateObj(CompiledExpression compiledExpression, Map<String, Object> properties) {
		try {
			JelResolver.Singlton.instance.ref.setProperties(properties);
			return compiledExpression.evaluate(JelContext.Singlton.instance.ref);
		} catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	/*
	 * Context
	 */
	public static final void evaluateExpr(String name, String exprValue, Map<String, Object> properties) {
		if( JelUtil.isExpression(exprValue) ){
			properties.put(name, JelUtil.evaluateObj(JelUtil.detokenizedExpression(exprValue), properties));
		} else {
			properties.put(name, exprValue);
			JelResolver.Singlton.instance.ref.setProperties(properties);
		}
	}


	/*
	 * JEL initialization
	 */

	static private enum JelContext {
		Singlton;
		
		public final GenericSinglton<Object[]> instance = new GenericSinglton<Object[]>(newContext());

		private Object[] newContext() {
			return new Object[]{JelResolver.Singlton.instance.ref}; // this pointer for YYY getXXXProperty() method
		}

	}	
	
	
	static private enum JelLibrary {
		Singlton;
		
		public final GenericSinglton<Library> instance = new GenericSinglton<Library>(newLibrary());

		private Library newLibrary() {
			// **********************************************************
			// **** to use JEL we first have to define the namespace ****

			// we shall export the static methods of java.lang.Math
			Class<?>[] stLib = new Class<?>[1];
			stLib[0] = java.lang.Math.class;

			// we shall enable access to methods of two classes
			Class<?>[] dynLib = new Class<?>[1];
			// we export YYY getXXXProperty() methods for dynamic variable access
			dynLib[0] = JelResolver.DynaResolverProvider.class;

			// we shall allow dot operators on strings and data
			Class<?>[] dotLib = new Class<?>[1];
			dotLib[0] = String.class;

			// finally, the namespace is defined by constructing the library class
			return new Library(stLib, dynLib, dotLib, JelResolver.Singlton.instance.ref, null);
		}

	}	
	
	static private enum JelResolver {
		Singlton;
		
		public final GenericSinglton<DynaResolverProvider> instance = new GenericSinglton<DynaResolverProvider>(new DynaResolverProvider());
		
		static public class DynaResolverProvider extends gnu.jel.DVMap {

			private Map<String, Object> properties = new HashMap<String, Object>(0);

			final public void setProperties(Map<String, Object> properties){
				this.properties = properties;
			}
			
			/*
			 *  adds a new property
			 */
			@SuppressWarnings("unused")
			final public void addProperty(String name, Object value) {
				properties.put(name, value);
			};

			/* 
			 * Next we have those YYY getXXXProperty(String) methods described in
			 * the manual
			 */
			@SuppressWarnings("unused")
			final public String getStringProperty(String name) {
				return (String) properties.get(name);
			};

			/*
			 * Implements the method of DVResolver interface used by the compiler to query about available dynamic
			 * variables
			 * 
			 * (non-Javadoc)
			 * @see gnu.jel.DVMap#getTypeName(java.lang.String)
			 */
			@Override
			final public String getTypeName(String name) {
				Object val = properties.get(name);
				if (val == null)
					return null; // dynamic variable does not exist
				else if (val instanceof String)
					return "String";
				else // the type is not supported we say the variable is not defined
					return null;
			};
		}
	}

}