package ca.mss.rd.stat;

import java.math.BigDecimal;

import ca.mss.rd.math.NewBigDecimal;


public class MovingArray {

	protected BigDecimal[] val;
	protected int size, idx;
	protected int depth;
	protected BigDecimal sized;
	
	public MovingArray(int depth) {
		setWindowSize(depth);

	}

	public void clear(){
		size = 0;
		sized = NewBigDecimal.getInstance(0.0D);
		idx = 0;
	}

	final public int size(){
		return size;
	}
	
	final public int getWindowSize() {
		return depth;
	}
	
	final public boolean isFull() {
		return size == depth;
	}
	
	final public boolean isAvailable() {
		return size > 0;
	}
	
	public void setWindowSize(int depth){
		if( depth < 1 )
			throw new RuntimeException("Mooving array can't have zero depth or below");
		
		this.depth = depth;
		this.val = new BigDecimal[depth];

		clear();
	}

	final protected BigDecimal poll(){
		if( idx == size )
			return val[0];
		else if( idx == 0 )
			return val[size-1];
		else
			return val[idx];
	}
	
	public void addValue(double d){
		if( size < depth ){
			sized = sized.add(NewBigDecimal.ONE);
			size++;
		}
		
		if( idx == size ){
			idx = 0;
		}
		
		val[idx++] = NewBigDecimal.getInstance(d);
	}
}
