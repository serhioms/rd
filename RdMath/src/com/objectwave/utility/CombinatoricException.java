package com.objectwave.utility;

/**
 */
public class CombinatoricException extends Exception {
	
	public static final String module = CombinatoricException.class.getName();
	public static final long serialVersionUID = module.hashCode();
	
    public CombinatoricException (String str)
    {
        super(str);
    }
}
