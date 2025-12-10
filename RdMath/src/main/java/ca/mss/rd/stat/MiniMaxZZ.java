package ca.mss.rd.stat;


public class MiniMaxZZ {

	public double min, max, val;
	
	public MiniMaxZZ() {
		clear();
	}

	public void clear(){
		max = min = 0.0;
	}

	public void addValue(double val){
		
		this.val = val;
		
		if( val > max ){
			max = val;
		}
		
		if( val < min ){
			min = val;
		}
	}
	
}
