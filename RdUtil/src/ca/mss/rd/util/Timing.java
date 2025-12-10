package ca.mss.rd.util;

public class Timing {

	private long lastTime, startTime, duration=0L, totalDuration=0L;

	public Timing(){
		start();
	}
	
	final public void start() {
		startTime = lastTime = System.currentTimeMillis();
	}

	final public String duration() {
		duration = System.currentTimeMillis() - lastTime;
		lastTime += duration;
		return formatMls(duration);
	}

	final public String total() {
		totalDuration = System.currentTimeMillis() - startTime;
		return formatMls(totalDuration);
	}
	
	final static public String formatMls(long mls) {
		long sec = mls/1000;
		if( sec/3600 > 0 )
			return UtilMath.round(sec/3600.0, 2) + " h";
		
		if( sec/60 > 0 )
			return UtilMath.round(sec/60.0, 2) + " min";

		if( sec > 0 )
			return UtilMath.round(sec, 2) + " sec";

		return mls + " mls";
	}

	final static public String formatBytes(long bytes) {
		long temp = bytes/1024/1024/1024;
		if( temp > 0 )
			return temp + " Gb";
		
		temp = bytes/1024/1024;
		if( temp > 0 )
			return temp + " Mb";
		
		temp = bytes/1024;
		if( temp > 0 )
			return temp + " Kb";

		return bytes + " b";
	}

}


