package ca.mss.rd.trader.deprecated.model.indicator;

import java.awt.Color;
import java.util.Map;

import ca.mss.rd.trader.util.MyColor;

abstract public class DefaultIndicator implements Indicator {

	final public String ident;
	final public String[] inEvent;
	final protected String[] outEvent;
	final protected String[] chart;
	final protected String[] trace;
	final protected Color[] color;

	abstract public int size();
	abstract public String getTitle(int i);
	abstract public int getIndex(String title);

	public DefaultIndicator(Map<String,String> param) {

		this.outEvent = new String[size()];
		this.chart = new String[size()];
		this.trace = new String[size()];
		this.color = new Color[size()];

		if( param.containsKey("inEvent") ){
			inEvent = param.get("inEvent").split(",");
		} else {
			throw new RuntimeException(getClass().getName()+" must have inEvent");
		}
		
		String ident = inEvent[0];

		if( param.containsKey("outEvent") ){
			String[] events = param.get("outEvent").split("=>");
			ident += ":"+events[0];
			if( events.length == 1 ){
				for(int i=0,size=size(); i<size; i++){
					outEvent[i] = events[0]+":"+getTitle(i);
				}
			} else {
				if( events.length > 1)
					outEvent[Integer.parseInt(events[1])] = events[0];
					
				if( events.length > 2 )
					outEvent[Integer.parseInt(events[2])] = events[0];
			}
		}
		
		if( param.containsKey("chart") ){
			String[] charts = param.get("chart").split("=>");
			ident += ":"+charts[0];
			if( charts.length == 1 ){
				for(int i=0,size=size(); i<size; i++){
					chart[i] = charts[0]+":"+getTitle(i);
				}
			} else {
				if( charts.length > 1 ){
					if( "*".equals(charts[1]) ){
						for(int i=0,size=size(); i<size; i++){
							chart[i] = charts[0];
						}
					} else {
						chart[Integer.parseInt(charts[1])] = charts[0];
					}
				}
					
				if( charts.length > 2 )
					chart[Integer.parseInt(charts[2])] = charts[0];

			}
		}
		
		if( param.containsKey("trace") ){
			String[] traces = param.get("trace").split("=>");
			ident += ":"+traces[0];
			if( traces.length == 1 ){
				for(int i=0, size=size(); i<size; i++){
					trace[i] = traces[0]+":"+getTitle(i);
				}
			} else {
				if( traces.length > 1 )
					trace[Integer.parseInt(traces[1])] = traces[0];
					
				if( traces.length > 2 )
					trace[Integer.parseInt(traces[2])] = traces[0];
			}
		}
		
		if( param.containsKey("color") ){
			String[] pairs = param.get("color").split(",");
			for(int j=0; j<pairs.length; j++){
				String[] pair = pairs[j].split("=>");
				if( pair.length == 1 && pairs.length == 1){
					color[0] = MyColor.getColor(pair[0]);
					for(int i=1,size=size(); i<size; i++){
						color[i] = color[i-1].darker();
					}
				} else if( pair.length == 2 ) {
					color[getIndex(pair[1])] = MyColor.getColor(pair[0]);
				} else {
					throw new RuntimeException("Unexpected color coding for ["+ident+"]");
				}
			}
		}
		
		this.ident = ident;
	}

	@Override
	public String[] getInEvent() {
		return inEvent;
	}

	@Override
	public String getOutEvent(int i){
		return outEvent[i];
	}

	@Override
	public String getChartName(int i){
		return chart[i];
	}

	@Override
	public String getTraceName(int i){
		return trace[i];
	}

	@Override
	public Color getColor(int i){
		return color[i];
	}

	@Override
	public boolean isVisible(int i) {
		return chart[i] != null;
	}

    @Override
	public String getIdent() {
		return ident;
	}

}
