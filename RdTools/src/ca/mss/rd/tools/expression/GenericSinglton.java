package ca.mss.rd.tools.expression;

public class GenericSinglton<T> {
	
    public final T ref;
    
    public GenericSinglton(T instance) {
        this.ref = instance;
    }
    
}