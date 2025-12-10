package ca.mss.rd.trader.server;

import java.beans.PropertyChangeSupport;
import java.util.Iterator;
import java.util.Map.Entry;

import ca.mss.rd.chart.manager.chart2d.RdChart2dMan;
import ca.mss.rd.chart.point.DrawPoint;
import ca.mss.rd.flow.IFlowManager;
import ca.mss.rd.flow.WorkflowManager;
import ca.mss.rd.flow.event.WorkflowListener;
import ca.mss.rd.parser.impl.GenericRecord;
import ca.mss.rd.trader.server.wkf.ProcessQuoteFlow;
import ca.mss.rd.util.Logger;
import ca.mss.rd.util.UtilMisc;
import ca.mss.rd.util.UtilProperty;
import ca.mss.rd.util.UtilString;
import ca.mss.rd.util.map.SmartMap;

public enum Trader {
	
	BackOffice;

	static public final String EVENT_CHART_DRAW = "CtrlChartDraw";
	static public final String EVENT_TRACE_PANEL_CREATE = "CtrlTracePanelCreate";
	static public final String EVENT_CHART_SHOW = "CtrlChartShow";
	static public final String EVENT_CHART_HIDE = "CtrlChartHide";
	static public final String EVENT_TRACE_CREATE = "CtrlTraceCreate";

	static public int MAX_FLOW_QUEUE = 1000;
	static public int MAX_FLOW_THREADS = 1;
	
	static public int MAX_ACTIVITY_QUEUE = 100000;
	static public int MAX_ACTIVITY_THREADS = 1;
	
	static public int MAX_TOOL_QUEUE = 0;
	static public int MAX_TOOL_THREADS = 0;

	static {
		UtilProperty.readConstants(Trader.class);
	}

	private IFlowManager wkfman;
	private PropertyChangeSupport pcs;
	
	private String selectedQuote;
	private ProcessQuoteFlow selectedFlow;
	
	final public void setPCS(PropertyChangeSupport pcs) {
		this.pcs = pcs;
	}

	final private SmartMap<String, ProcessQuoteFlow> processQuoteFlow = new SmartMap<String, ProcessQuoteFlow>(){
		@Override
		public ProcessQuoteFlow valueFactory(Object key) {
			if( pcs == null )
				throw new NullPointerException("PropertyChangeSupport::Trader.pcs must not be null!");
			else 
				return new ProcessQuoteFlow(key.toString(), pcs);
		}
		
	};
	
	final public void startupWkfEngine() {
		wkfman = new WorkflowManager(
				MAX_FLOW_QUEUE, MAX_FLOW_THREADS, 
				MAX_ACTIVITY_QUEUE, MAX_ACTIVITY_THREADS, 
				MAX_TOOL_QUEUE, MAX_TOOL_THREADS);
		

		// Add workflow extra logging here
		// wkfman.addListener(new WorkflowLogger());
		
		wkfman.startup();
	}

	final public void shutdownWkfEngine() {
		
		if( RdChart2dMan.counter.size() == 0 ){
			assert( Logger.CHART.isOn ? Logger.CHART.printf("No loses!"): true);
		} else if( Logger.ERROR.isOn ) { 
			if( Logger.ERROR.isOn ) Logger.ERROR.printf("RdChart2dMan losses:");
			for(Iterator<Entry<Object, Integer[]>> iter = RdChart2dMan.counter.entrySet().iterator(); iter.hasNext(); ){
				Entry<Object, Integer[]> entry = iter.next();
				if( Logger.ERROR.isOn ) Logger.ERROR.printf("%s = %d", entry.getKey(), entry.getValue()[0]);
			}
		}
		
		wkfman.shutdown();
	}

	final public boolean isRunning(){
		return wkfman.isRunning();
	}

	final public IFlowManager getFlowManager(){
		return wkfman;
	}

	
	public long stopCounter=0, startCounter=0, rowCounter;
	
	final public void quoteProcess(GenericRecord record) {
		assert( Logger.TRADER_VERBOSE.isOn ? Logger.TRADER_VERBOSE.printf("Process quote for %s [%s]", record.getTime(), selectedQuote): true);
		
		try{
			selectedFlow.startFlow(wkfman, record);
		}catch(Throwable t){
			if( Logger.ERROR.isOn ) Logger.ERROR.printf("%s", t.getMessage());
		}
	}
	


	public ProcessQuoteFlow quoteSelect(String selectedQuote, boolean isOn) {
		assert( Logger.TRADER.isOn ? Logger.TRADER.printf("Select quote [%s = %b]", selectedQuote, isOn): true);

		selectedFlow = processQuoteFlow.get(selectedQuote);

		if( isOn ){
			this.selectedQuote = selectedQuote;
			
			if( !selectedFlow.context.traces.isEmpty() ){
				pcs.firePropertyChange(EVENT_TRACE_PANEL_CREATE, null, selectedFlow.context.traces);
			}

			if( !selectedFlow.context.charts.isEmpty() ){
				pcs.firePropertyChange(EVENT_CHART_SHOW, null, selectedFlow.context.charts);
			}
			
		} else {
			if( !selectedFlow.context.charts.isEmpty() ){
				pcs.firePropertyChange(EVENT_CHART_HIDE, null, selectedFlow.context.charts);
			}
		}
		
		return selectedFlow;
	}

	public void traceSelectHandler(String traceName, boolean isOn) {
		assert( Logger.TRADER.isOn ? Logger.TRADER.printf("traceSelectHandler [%s=%b]", traceName, isOn): true);

		if( isOn ){
			ProcessQuoteFlow flow = processQuoteFlow.get(selectedQuote);
			
			for(DrawPoint dp: flow.context.points){
				if( traceName.equals(dp.traceName) ){
					pcs.firePropertyChange(EVENT_TRACE_CREATE, null, dp);
				}
			}
		}
	}


	
	
	

	public void quoteSet(String[] quotes) {
		assert( Logger.TRADER.isOn ? Logger.TRADER.printf("quoteSet %d %s", quotes.length, UtilMisc.toSet(quotes)): true);
	}

	public void stopCounter(String newval) {
		stopCounter = UtilString.parseLong(newval);
	}

	public void startCounter(String newval) {
		startCounter = UtilString.parseLong(newval);
	}

	// Clear flow before rerun
	final public void clearFlow() {
		if( selectedFlow != null ){
			selectedFlow.clearFlow();
		}
		processQuoteFlow.clear();
	}

	// Finish flow
	final public void stopFlow() {
		if( selectedFlow != null ){
			selectedFlow.stopFlow();
		}
	}

	public boolean ifStop(int rowCounter) {
		this.rowCounter = rowCounter;
		//return stopCounter > 0 && rowCounter == stopCounter;
		if( stopCounter > 0 && rowCounter == stopCounter ){
			return true;
		} else
			return false;

	}

	public boolean ifShow() {
		// return rowCounter >= startCounter;
		if( rowCounter >= startCounter )
			return true;
		else
			return false;
	}

	
	public void addWorkflowListener(WorkflowListener<?,?> l){
		wkfman.addListener(l);
	}
}
