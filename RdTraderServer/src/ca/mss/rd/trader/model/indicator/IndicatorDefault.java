package ca.mss.rd.trader.model.indicator;

import java.util.Set;

import ca.mss.rd.chart.point.DrawPoint;

abstract public class IndicatorDefault implements Indicator {

	final public String name;
	
	
	public IndicatorDefault(String name) {
		this.name = name;
	}

	protected void populate(Set<String> set, String[] names) {
		for(String name: names){
			set.add(name);
		}
	}

	protected void populate(Set<DrawPoint> set, DrawPoint[] points) {
		for(DrawPoint point: points){
			set.add(point);
		}
	}

	protected final void clean(DrawPoint[] dp) {
		for(int i=0; i<dp.length; i++){
			dp[i].val = 0.0;
			dp[i].preval = 0.0;
			dp[i].time = 0;
		}
	}

	protected final String[] merge(String[] parent, String[] chield) {
		String[] newChield = new String[parent.length+chield.length];
		for(int i=0; i<chield.length; i++){
			newChield[i] = chield[i];
		}
		for(int i=chield.length; i<newChield.length; i++){
			newChield[i] = parent[i-chield.length];
		}
		return newChield;
	}

	@Override
	public void draw() {
		throw new RuntimeException("Default draw must be overriden!");
	}

	@Override
	public void stop() {
	}

	@Override
	public String getName() {
		return name;
	}
	
}
