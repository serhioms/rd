/*
 * $Id: BshUtil.java,v 1.3 2004/07/18 09:35:06 jonesde Exp $
 *
 * Copyright (c) 2001-2004 The Open For Business Project - www.ofbiz.org
 *
 * Permission is hereby granted, free of charge, to any person obtaining a
 * copy of this software and associated documentation files (the "Software"),
 * to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS
 * OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY
 * CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT
 * OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR
 * THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */
package ca.mss.rd.tools.expression;

import java.io.StringReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import bsh.BshClassManager;
import bsh.EvalError;
import bsh.Interpreter;
import bsh.NameSpace;

/**
 * BshUtil - BeanShell Utilities
 *
 *@author     <a href="mailto:jaz@ofbiz.org">Andy Zeneski</a>
 *@author     <a href="mailto:jonesde@ofbiz.org">David E. Jones</a>
 *@author     Oswin Ondarza and Manuel Soto
 *@created    Oct 22, 2002
 *@version    $Revision: 1.3 $
 */
public final class BshUtil {

    public static final String module = BshUtil.class.getName();
	private static org.apache.log4j.Logger logger = ca.mss.rd.util.Debug.getLogger(module);

	private static Map<ClassLoader,BshClassManager> masterClassManagers = new HashMap<ClassLoader,BshClassManager>();
    //public static UtilCache parsedScripts = new UtilCache("script.BshLocationParsedCache", 0, 0, false);
    
    /**
     * Evaluate a BSH condition or expression
     * @param expression The expression to evaluate
     * @param context The context to use in evaluation (re-written)
     * @return Object The result of the evaluation 
     * @throws EvalError
     */
    public static final Object eval(String expression, Map<String,Object> context) {
        Object result = null;
        if (expression == null || expression.equals("")) {
            logger.error("BSH Evaluation error. Empty expression");
            return null;
        }

        if (logger.isDebugEnabled()){
            if( logger.isDebugEnabled()) logger.debug("Evaluating -- " + expression);
            if( logger.isDebugEnabled()) logger.debug("Using Context -- " + context);
        }

        try {
            Interpreter bsh = makeInterpreter(context, context.getClass());
            // evaluate the expression

            result = bsh.eval(expression);
            
            if (logger.isDebugEnabled())
                if( logger.isDebugEnabled()) logger.debug("Evaluated to -- " + result);

            // read back the context info
            NameSpace ns = bsh.getNameSpace();
            String[] varNames = ns.getVariableNames();
            for (int x = 0; x < varNames.length; x++) {
                context.put(varNames[x], bsh.get(varNames[x]));
            }
        } catch (EvalError e) {
            throw new RuntimeException("BSH Evaluation error.", e);
        }
        return result;
    }

    public static Interpreter makeInterpreter(Map<String,Object> context, Class<?> claz) throws EvalError {
    	Interpreter bsh = getMasterInterpreter(null, claz);
        // Set the context for the condition
        if (context != null) {
            Set keySet = context.keySet();
            Iterator<?> i = keySet.iterator();
            while (i.hasNext()) {
                Object key = i.next();
                Object value = context.get(key);
                bsh.set((String) key, value);
            }
            // include the context itself in for easier access in the scripts
            bsh.set("context", context);
        }
        return bsh;
    }

    @SuppressWarnings("unused")
	public static Interpreter getMasterInterpreter(ClassLoader classLoader, Class<?> claz) {
        if (classLoader == null) {
            classLoader = Thread.currentThread().getContextClassLoader();
        }

        //find the "master" BshClassManager for this classpath
        BshClassManager master = (BshClassManager) BshUtil.masterClassManagers.get(classLoader);
        if (master == null) {
            synchronized( claz ) 
        	{
                master = (BshClassManager) BshUtil.masterClassManagers.get(classLoader);
                if (master == null) {
                    master = BshClassManager.createClassManager(null);
                    master.setClassLoader(classLoader);
                    BshUtil.masterClassManagers.put(classLoader, master);
                }
            }
        }
        
        if (master != null) {
            Interpreter interpreter = new Interpreter(new StringReader(""), System.out, System.err, false, new NameSpace(master, "global"), null, null);
            return interpreter;
        } else {
            Interpreter interpreter = new Interpreter();
            interpreter.setClassLoader(classLoader);
            return interpreter;
        }
    }
    
}
