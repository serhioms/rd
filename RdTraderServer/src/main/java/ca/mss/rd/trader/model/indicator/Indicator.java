package ca.mss.rd.trader.model.indicator;

public interface Indicator {

	public void clean();

	public void process();
	
	public void draw();
	
	public void stop();

	public String getName();
}
