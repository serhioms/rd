package ca.mss.rd.util.pool;

import java.util.Stack;

public class ObjectPool<T> {

	final private Stack<T> idle = new Stack<T>();  
	final private Stack<T> borrowed = new Stack<T>();  

	/*
	 * Create an object using the factory or other implementation dependent
	 * mechanism, passivate it, and then place it in the idle object pool.
	 */
	public void addObject(T obj) {
		idle.add(obj);
	}

	/*
	 * Obtains an instance from this pool.
	 */
	public T borrowObject() {
		if( idle.size() > 0  )
			return borrowed.push(idle.pop());
		else
			return null;
	}

	public T borrowObject(T obj) {
		return borrowed.push(obj);
	}

	/*
	 * Clears any objects sitting idle in the pool, releasing any associated
	 * resources (optional operation).
	 */
	public void clear() {
		idle.clear();
	}

	/*
	 * Close this pool, and free any resources associated with it.
	 */
	public void close() {
		clear();
	}

	/*
	 * Return the number of instances currently borrowed from this pool
	 * (optional operation).
	 */
	public int getNumActive() {
		return borrowed.size();
	}

	/*
	 * Return the number of instances currently idle in this pool (optional
	 * operation).
	 */
	public int getNumIdle() {
		return idle.size();
	}

	/*
	 * Invalidates an object from the pool.
	 */
	public void invalidateObject(T obj) {
		borrowed.remove(obj);
		idle.remove(obj);
	}

	/*
	 * Return an instance to the pool.
	 */
	public void returnObject(T obj) {
		idle.push(borrowed.pop());
	}

}
