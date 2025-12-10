package ca.mss.rd.stat;

public class BiIntegral {

	private double psum = 0.0D, msum = 0.0D;
	private boolean isPlus = false, isMinus = false;
	
	public BiIntegral() {
	}

	public void clear(){
		psum = msum = 0.0D;
		isPlus = isMinus = false;
	}
	
	public void addValue(double d){
		if( d > 0.0 ){
			if( !isPlus && isMinus ){
				psum = 0.0D;
			}
			psum += d;
		} else if( d < 0.0 ){
			if( !isMinus && isPlus ){
				msum = 0.0D;
			}
			msum -= d;
		}
	}

	public boolean isGoingUp(double factor){
		if( isPlus )
			return psum >= factor*msum;
		else
			return false; 
	}

	public boolean isGoingDn(double factor){
		if( isMinus )
			return msum >= factor*psum;
		else
			return false; 
	}
}
