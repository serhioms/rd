package ca.mss.rd.trader.deprecated;

import java.util.ArrayList;

import ca.mss.rd.trader.model.Point;

abstract public class FIFO {
	
	final public int size;
	final public ArrayList<Point> pool; 
	
	public FIFO(int size){
		this.size = size;
		this.pool = new ArrayList<Point>(size);
	}

	public boolean isValid(){
		return pool.size() == size;
	}
		
	final public void push(Point p){
		if( pool.size() < size ){
			pool.add(0, p);
		} else {
			pool.add(0, p);
			pool.remove(size);
		}
		calculate();
	} 		

	final public void clear(){
		pool.clear();
	} 		

	abstract public void calculate();
}

