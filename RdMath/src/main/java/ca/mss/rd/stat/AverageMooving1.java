package ca.mss.rd.stat;

import java.util.LinkedList;
import java.util.Queue;

public class AverageMooving1 {

	static public int DEFAULT_DEPTH = 2;

	protected final Queue<Double> values = new LinkedList<Double>();
	protected int depth;
	protected double sum;
	
	public AverageMooving1() {
		this(DEFAULT_DEPTH);
	}

	public AverageMooving1(int depth) {
		setDepth(depth);
		clear();
	}

	public void clear(){
		values.clear();
		sum = 0.0D;
	}

	public final int size(){
		return values.size();
	}
	
	public final int getDepth() {
		return depth;
	}
	
	public final boolean isFull() {
		return values.size() == depth;
	}
	
	public final boolean isAvailable() {
		return values.size() > 0;
	}
	
	public void setDepth(int depth){
		if( depth < 1 )
			throw new RuntimeException("Mooving depth can't be less then one");
		this.depth = depth;
		for(int i=values.size(); i>depth; i--){
			sum -= values.poll();
		}
	}

	public void addValue(double val){
		if( values.size() < depth ){
			sum += val;
		} else {
			sum += val - values.poll();
		}
		values.add(val);
	}
	
	public final double getAverage(){
		return sum/values.size();
	}
	
}
