package ng.ca.mss.rd.trader;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;

import ca.mss.rd.flow.IActivity;
import ca.mss.rd.flow.ITool;
import ca.mss.rd.flow.IWorkflow;
import ca.mss.rd.flow.event.WorkflowEvent;
import ca.mss.rd.flow.event.WorkflowListener;
import ca.mss.rd.parser.impl.GenericRecord;
import ca.mss.rd.trader.model.indicator.IndicatorBreakLineMM;
import ca.mss.rd.trader.model.indicator.TraderEvent;
import ca.mss.rd.trader.server.Trader;
import ca.mss.rd.trader.server.wkf.ContextProcessQuote;
import ca.mss.rd.trader.server.wkf.ProcessQuoteFlow;
import ca.mss.rd.trader.src.oanda.FxOandaFile;
import ca.mss.rd.trader.src.oanda.QuoteEvent;
import ca.mss.rd.util.Logger;
import ca.mss.rd.util.UtilDateTime;

abstract public class TestProcessQuoteFlowBase implements PropertyChangeListener {
	
	abstract protected String suiteName();
	abstract protected void testReport();
	abstract protected void suiteReport();

	abstract protected void analizeBlIndicator(IndicatorBreakLineMM newval);
	abstract protected void eventHandler_CreateBlIndicator(Integer size);
	abstract protected void eventHandler_HourCumulativeResult(Integer timeCounter);
	
	
	PropertyChangeSupport pcs = new PropertyChangeSupport(this);

	private long startSuite, startTest;
	
	protected FxOandaFile oanda;
	protected ProcessQuoteFlow workflow;
	protected String quote;

	@BeforeTest
	public final void beforeTest() {
		if( Logger.TEST.isOn) {
			Logger.TEST.printf("%-15s [%s][%20s]","Suite start", UtilDateTime.rightnow(), suiteName());
			startSuite = System.currentTimeMillis();
		}

		oanda = new FxOandaFile();
		oanda.addPropertyChangeListener(this);
		
		pcs.addPropertyChangeListener(this);

		Trader.BackOffice.setPCS(pcs);
		Trader.BackOffice.startupWkfEngine();
		
		// Workflow serializer
		Trader.BackOffice.addWorkflowListener(new WorkflowListener<Object,ContextProcessQuote>() {

			@Override
			public void stateChange(WorkflowEvent event, IWorkflow<Object,ContextProcessQuote> wkf) throws Throwable {
				switch(event.source){
				case FlowEnd:
					if( oanda.getThresholdMls() <= 0 )
						oanda.release();
					break;
				}
			}

			@Override
			public void stateChange(WorkflowEvent event, IActivity<Object,ContextProcessQuote> act) throws Throwable {
			}

			@Override
			public void stateChange(WorkflowEvent event, ITool tool) throws Throwable {
			}
		});
	}

	@BeforeMethod
	public final void beforeMethod() throws InterruptedException {
		if( Logger.TEST.isOn) {
			Logger.TEST.printf("%-15s [%s]", "Test start", UtilDateTime.rightnow());
			startTest = System.currentTimeMillis();
		}
	}
	
	
	@AfterMethod
	public final void afterMethod() throws InterruptedException {
		assert( Logger.TEST.isOn? Logger.TEST.printf("%-15s [%s][%20s][%s]", "Test end", UtilDateTime.rightnow(), quote, UtilDateTime.formatTime(System.currentTimeMillis()-startTest)): true);

		testReport();
	}


	@AfterTest
	public final void afterTest() throws InterruptedException {
		Trader.BackOffice.shutdownWkfEngine();

		assert( Logger.TEST.isOn? Logger.TEST.printf("%-15s [%s][%20s][%s]", "Suite end", UtilDateTime.rightnow(), suiteName(), UtilDateTime.formatTime(System.currentTimeMillis()-startSuite)): true);
		
		suiteReport();
	}

	


	@Override
	public final void propertyChange(PropertyChangeEvent evt) {
		
		final String eventName = evt.getPropertyName();
		final Object valnew = evt.getNewValue();

		if( QuoteEvent.EVENT_RECORD.equals(eventName)) {
			if( oanda.getThresholdMls() <= 0 ){
				try {
					oanda.lock();
				} catch (InterruptedException e) {
					if( Logger.ERROR.isOn ) Logger.ERROR.printf(e, "Failed to lock quote serializer");
				}
			}
	    	Trader.BackOffice.quoteProcess( (GenericRecord )valnew);
		} if( QuoteEvent.EVENT_START.equals(eventName)){
			Trader.BackOffice.clearFlow();
		} if( QuoteEvent.EVENT_FINISH.equals(eventName) ){
			Trader.BackOffice.stopFlow();
		} if( TraderEvent.ANALIZE_BL_INDICATOR.equals(eventName)){
			analizeBlIndicator((IndicatorBreakLineMM )valnew);
		} if( TraderEvent.CREATE_BL_INDICATOR.equals(eventName)){
			eventHandler_CreateBlIndicator((Integer )valnew);
		} if( TraderEvent.HOUR_CUMULATIVE_RESULT.equals(eventName)){
			eventHandler_HourCumulativeResult((Integer )valnew);
		} else if( Logger.TEST_SKIPPED_EVENTS.isOn ) {
			Logger.TEST_SKIPPED_EVENTS.printf("Skip [%s]", eventName);
		}
	}

}
