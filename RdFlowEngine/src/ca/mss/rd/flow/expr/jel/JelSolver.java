package ca.mss.rd.flow.expr.jel;

import gnu.jel.CompiledExpression;
import gnu.jel.Evaluator;
import gnu.jel.Library;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ca.mss.rd.flow.expr.Solver;

public class JelSolver implements Solver<Object, Object> {

	private static final String VALID_TOKEN_VARIABLE_REGEX = "\\$\\{[a-zA-Z0-9_\\-\\.]+}";
	private static final Pattern VALID_TOKEN_VARIABLE_PATTERN = Pattern.compile(VALID_TOKEN_VARIABLE_REGEX);

	public final Map<String, Object> exprContext = new HashMap<String, Object>(0);
	
	private final DynaResolverProvider jelResolver;
	private final Object[] jelContext;
	private final Library jelLibrary;

	public JelSolver() {
		jelResolver = new DynaResolverProvider();
		jelContext = new Object[]{jelResolver};
		jelLibrary = newLibrary();
	}

	
	@Override
	public Map<String, Object> getExprContext() {
		return exprContext;
	}


	@Override
	public void setExprContext(Map<String, Object> exprContext) {
		this.exprContext.clear();
		this.exprContext.putAll(exprContext);
	}


	@Override
	public final Object evalExpression(String name, String exprValue) {
		if( isExpression(exprValue) ){
			try {
				return evalExpression(name, getCompiledExpression(detokenizedExpression(exprValue)));
			} catch( RuntimeException e){
				detokenizedExpression(exprValue);
				throw e;
			}
		} else {
			Object value = exprValue;
			exprContext.put(name, value);
			return value;
		}
	}
	
	@Override
	public final boolean evalCondition(String exprValue) {
		if( isExpression(exprValue) ){
			try {
				Boolean result = (Boolean )evalExpression(null, getCompiledExpression(detokenizedExpression(exprValue)));
				return result == null? false: result.booleanValue();
			} catch( RuntimeException e){
				detokenizedExpression(exprValue);
				throw e;
			}
		} else {
			return false;
		}
	}
	
	public final Object evalExpression(String name, CompiledExpression compiledExpression) {
		try {
			Object value = compiledExpression.evaluate(jelContext);
			exprContext.put(name, value);
			return value;
		} catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}
	

	@Override
	public final boolean isExpression(String exprValue) {
		if( exprValue == null || exprValue.isEmpty() ) {
			return false;
		} else {
			return VALID_TOKEN_VARIABLE_PATTERN.matcher(exprValue).find();
		}
	}

	@Override
	public final String detokenizedExpression(String expr) {
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

	public final Map<String, Object> getContext() {
		return exprContext;
	}
	
	public CompiledExpression getCompiledExpression(String expr) {
		try {
			return Evaluator.compile(expr, jelLibrary);
		} catch (Exception e) {
			throw new RuntimeException(String.format("Can not compile [expr[%s][%s]] due to %s", expr, e.getMessage()), e);
		}
	}

	private final Library newLibrary() {
		// **********************************************************
		// **** to use JEL we first have to define the namespace ****

		// we shall export the static methods of java.lang.Math
		Class<?>[] stLib = new Class<?>[1];
		stLib[0] = java.lang.Math.class;

		// we shall enable access to methods of two classes
		Class<?>[] dynLib = new Class<?>[1];
		// we export YYY getXXXProperty() methods for dynamic variable access
		dynLib[0] = DynaResolverProvider.class;

		// we shall allow dot operators on strings and data
		Class<?>[] dotLib = new Class<?>[1];
		dotLib[0] = String.class;

		// finally, the namespace is defined by constructing the library class
		return new Library(stLib, dynLib, dotLib, jelResolver, null);
	}
	
	public class DynaResolverProvider extends gnu.jel.DVMap {
		/*
		 * Implements the method of DVResolver interface used by the compiler to query about available dynamic
		 * variables
		 * 
		 * (non-Javadoc)
		 * @see gnu.jel.DVMap#getTypeName(java.lang.String)
		 */
		@Override
		final public String getTypeName(String name) {
			Object val = exprContext.get(name);
			if (val == null)
				return null; // dynamic variable does not exist
			else if (val instanceof String)
				return "String";
			else // the type is not supported we say the variable is not defined
				return null;
		};
		
		/*
		 *  adds a new property
		 */
		final public void addProperty(String name, Object value) {
			exprContext.put(name, value);
		};

		/*
		 *  Next we have those YYY getXXXProperty(String) methods described in the manual
		 */
		final public String getStringProperty(String name) {
			return (String) exprContext.get(name);
		};	}

}
