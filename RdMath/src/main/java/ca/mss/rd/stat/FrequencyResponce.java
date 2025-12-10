package ca.mss.rd.stat;


public class FrequencyResponce {

	static public int DEFAULT_DEPTH = 100;
	
	private double[] values;
	private int[] freq;
	private int depth, size;
	
	public FrequencyResponce() {
		this(DEFAULT_DEPTH);
	}

	public FrequencyResponce(int depth) {
		setDepth(depth);
	}

	public final void clear(){
		values = new double[depth];
		freq = new int[depth];
		size = 0;
	}

	public final int size(){
		return size;
	}
	
	public final void setDepth(int depth){
		if( depth < 2 )
			throw new RuntimeException("Frequency Responce depth can't be less then 2");
		clear();
	}

	public final int getDepth() {
		return depth;
	}
	
	public final void addValue(double val){
		if( size == 0 ){
			values[size++] = val;
		} else if( size < depth ){
			int pos = binarySearch(0, size, val);
		}
	}
	
	public final double getLeft(int percent){
		return 0.0;
	}
	
	protected int binarySearch(int l, int r, double v){
		int p = 0;
		while( l != r ){
			p = (r+l)/2;
			if( v > values[p]){
				l = p;
			} else if( v < values[p] ){
				r = p;
			} else
				break;
		}
		return p;
	}
}
