package ca.mss.rd.trader.deprecated;

import ca.mss.rd.trader.model.Point;


public class MiniMidiMax extends FIFO {
	
	public Point min, max;
	public double mid;
	
	public MiniMidiMax(int size){
		super(size);
	}

	public void calculate(){
		double ma = Double.MIN_VALUE;
		double mi = Double.MAX_VALUE;
		double n=0;
		mid = 0.0;
		for(Point p : pool) {
			mid += p.val[0];
			n++;
			if( p.val[0] > ma ){
				max = p;
				ma = max.val[0];
			}
			if( p.val[0] < mi ){
				min = p;
				mi = min.val[0];
			}
		}
		if( n > 0 ){
			mid /= n;
		}
	} 		
}

