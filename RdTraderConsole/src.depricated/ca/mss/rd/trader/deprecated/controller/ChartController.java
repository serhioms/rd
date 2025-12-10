package ca.mss.rd.trader.deprecated.controller;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ca.mss.rd.math.FXSourceIterator;
import ca.mss.rd.trader.deprecated.collector.BidAskMidQuote;
import ca.mss.rd.trader.deprecated.collector.RdCollector;
import ca.mss.rd.trader.deprecated.model.AvailableData;
import ca.mss.rd.trader.deprecated.model.SelectedData;
import ca.mss.rd.trader.deprecated.model.indicator.Indicator;
import ca.mss.rd.trader.model.Point;
import ca.mss.rd.trader.util.MyColor;

import ca.mss.rd.util.UtilMisc;
import ca.mss.rd.util.UtilReflection;

@Deprecated
public class ChartController implements PropertyChangeListener {

	final static public String module = ChartController.class.getSimpleName();
	final static public org.apache.log4j.Logger logger = ca.mss.rd.util.Debug.getLogger(module);

	private String dataSource;
	private RdCollector collector;
	final public Map<String, List<Indicator>> activeIndicators = new HashMap<String,List<Indicator>>();
    

	public ChartController() {
	}

	
	public final boolean isBSB() {
		return false;
	}

	public final void setBSB(boolean isBSB) {
	}

	public final boolean isSBB() {
		return false;
	}

	public final void setSBB(boolean isSBB) {
	}

	public void start() {
    	collector.start();
	}


	public void stop() {
		collector.interrupt();
	}

	public void restart() {
		collector.restart();
	}
	
	public final String getDataSource() {
		return dataSource;
	}

	public void setDataSource(String dataSource){
		this.dataSource = dataSource;
		FXSourceIterator iter = (FXSourceIterator) UtilReflection.instantiateObject(AvailableData.getDataSourceClass(dataSource));
		setCollector(new BidAskMidQuote(iter));
		restart();
	}

	/*
	 * Private Indicator's control
	 */
	private final void setCollector(RdCollector collector) {
		this.collector = collector;
		collector.addPropertyChangeListener(this);
	}


	public final void setLatencyDelay(long latencyDelay) {
		if( collector != null ){
			collector.setThresholdMls((latencyDelay));
		}
	}

	public final void addQuote(Set<String> quotes) {
		if( collector != null ){
			collector.addQuote(quotes);
		}
		if( getDataSource() != null ){
			for(Iterator<String> iter=quotes.iterator(); iter.hasNext(); ){
				String quoteName = iter.next();
	
				// indicators
				for(int i=SelectedData.INSTANCE.selectedPipes.get(getDataSource()).size()-1; i>=0; i--){
					
					String[] pair=SelectedData.INSTANCE.selectedPipes.get(getDataSource()).get(i).split("=>");
					
					if( pair.length > 1 ){
						addSelectedIndicator(UtilMisc.toMap(
								"class", "ca.mss.rd.trader.deprecated.model.indicator.QuoteSlicer" 
								,"pipe", pair[0]
								,"inEvent", quoteName
								,"chart", quoteName+"=>1"
								,"trace", quoteName+":Slicer:"+pair[0]+"=>1"
								,"color", pair[1]+"=>1"
								));
					} else {
						String color = MyColor.getNextColor();
						addSelectedIndicator(UtilMisc.toMap(
								"class", "ca.mss.rd.trader.deprecated.model.indicator.QuoteSlicer" 
								,"pipe", SelectedData.INSTANCE.selectedPipes.get(getDataSource()).get(i)
								,"inEvent", quoteName
								,"chart", quoteName+"=>1"
								,"trace", quoteName+":Slicer:"+SelectedData.INSTANCE.selectedPipes.get(getDataSource()).get(i)+"=>1"
								,"color", color+"=>1"
								));
					}
				}
				
				// pnl
				addSelectedIndicator(UtilMisc.toMap(
						"class", "ca.mss.rd.trader.deprecated.model.indicator.QuotePNL" 
						,"inEvent", quoteName+",doBuy"
						,"chart", quoteName+":PNL=>*"
						,"trace", quoteName+":PNL"
						,"color", "magenta=>SUM,white=>TTL,blue=>BSB,yellow=>SBB"
						));
			}
		}
	}
	
    private void processActiveIndicators(List<Indicator> indicators, Point point){
		List<Indicator> nextIndicators;
		Point nextPoint;
		for(Iterator<Indicator> iter=indicators.iterator(); iter.hasNext(); ){
			Indicator indicator = iter.next();
			
			indicator.process(point);
			
			for(int i=0, sizei=indicator.size(); i<sizei; i++){
				nextPoint = indicator.getPoint(i);
				
				if( indicator.getOutEvent(i) != null ){
					nextIndicators = activeIndicators.get(indicator.getOutEvent(i));
					if( nextIndicators != null ){
						processActiveIndicators(nextIndicators, nextPoint);
					}
				}

				if( indicator.getTraceName(i) != null ){
					pcs.firePropertyChange(indicator.getTraceName(i), null, nextPoint);
				}
			}
		}
    }

	final private void addSelectedIndicator(Map<String,String> param) {
		Indicator indicator = (Indicator )UtilReflection.instantiateObject(param.get("class"), new Class[]{Map.class}, new Object[]{param});
		
		String[] events = indicator.getInEvent();
		
		for(int i=0; i<events.length; i++){
			List<Indicator> ailst = activeIndicators.get(events[i]);
			if( ailst == null ){
				ailst = new ArrayList<Indicator>();
				activeIndicators.put(events[i], ailst);
			}
			
			final String ident = indicator.getIdent();
			for(Iterator<Indicator> iter=ailst.iterator(); iter.hasNext(); ){
				if( ident.equals(iter.next().getIdent()) )
					continue;
			}
	
			ailst.add(indicator);
		}
		pcs.firePropertyChange("addIndicator", null, indicator);
	}
    
	@Override
	public void propertyChange(PropertyChangeEvent evt) {

		final String name = evt.getPropertyName();
		final List<Indicator> indicators = activeIndicators.get(name);
		
		if( "doBuy".equals(name) || "doSell".equals(name) || "buyBack".equals(name) || "sellBack".equals(name) || "startRecord".equals(name) || "stopRecord".equals(name)){
			// TODO: processActiveIndicators(indicators, name);
		} else if( indicators != null  ){
			processActiveIndicators(indicators, (Point )evt.getNewValue());
		} else if( "latencyDelay".equals(name)){
			setLatencyDelay((Long )evt.getNewValue());
		} else if( "addQuote".equals(name)){
			addQuote(UtilMisc.toSet((String )evt.getNewValue()));
		} else if( "threadStart".equals(name)){
			for(Iterator<List<Indicator>> iter1=activeIndicators.values().iterator(); iter1.hasNext(); )
				for(Iterator<Indicator> iter2=iter1.next().iterator(); iter2.hasNext(); )
					iter2.next().start();
		} else {
			logger.warn(module+": unhandled event [event="+name+"][from="+evt.getSource().getClass().getSimpleName()+"][value="+evt.getNewValue()+"]");
		}
		pcs.firePropertyChange(evt);
	}

	/*
     * Property change support
     */
    final private PropertyChangeSupport pcs = new PropertyChangeSupport(this);
    
    final public void addPropertyChangeListener(PropertyChangeListener listener) {
    	pcs.addPropertyChangeListener(listener);
    }
}
