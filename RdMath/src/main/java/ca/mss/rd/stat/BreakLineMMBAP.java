package ca.mss.rd.stat;

/* BreakLineMM is Mid but BA - Bid/Ask/Profit*/
public class BreakLineMMBAP extends BreakLineMM {
	
	public double bidBroken, bidMax, bidMin; 
	public double askBroken, askMax, askMin;
	public long tmin, tmax, time;
	public double d;
	
	public BreakLineMMBAP() {
		super();
	}

	public void clear(){
		super.clear();
		bidBroken = bidMax = bidMin = askBroken = askMax = askMin = 0.0;
		tmin = tmax = 0L;
	}

	public void addValue(double d, double limit, double bid, double ask, long time){
		this.time = time;
		this.d = d;
		if( tmin == 0L ){
			min = max = time;
			bidBroken = bidMax = bidMin = bid;
			askBroken = askMax = askMin = ask;
		}
		super.addValue(d, limit);
		if( isBroken ){
			bidBroken = bid; 
			askBroken = ask;
		}
		if( isMax ){
			bidMax = bid; 
			askMax = ask;
			tmin = time;
		}
		if( isMin ){
			bidMin = bid; 
			askMin = ask;
			tmax = time;
		}
	}

	/*
	 *  (+) for bsb
	 *  (-) for sbb
	 */
	public double trendRetro(){
		return (max - min)/Double.longBitsToDouble(tmax-tmin); 
	}

	/*
	 *  (+) for bsb
	 *  (-) for sbb
	 */
	public double trend(){
		return tmax > tmin? (d - max)/Double.longBitsToDouble(time - tmax): (d - min)/Double.longBitsToDouble(time-tmin);
	}

	public double profit(){
		return bidMax - askMin; 
	}

	public boolean isBSB(){
		return tmin < tmax; 
	}

	public boolean isSBB(){
		return tmax < tmin; 
	}

	public boolean isProfitable(){
		return bidMax > askMin; 
	}
}
