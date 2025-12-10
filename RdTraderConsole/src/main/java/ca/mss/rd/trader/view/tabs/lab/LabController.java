package ca.mss.rd.trader.view.tabs.lab;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import ca.mss.rd.parser.impl.GenericRecord;
import ca.mss.rd.swing.RdTab;
import ca.mss.rd.swing.RdTabModel;
import ca.mss.rd.trader.FxTConfig;

import ca.mss.rd.trader.src.oanda.QuoteEvent;
import ca.mss.rd.trader.src.oanda.QuotesSource;
import ca.mss.rd.trader.view.widgets.QuotesCheckPanel;
import ca.mss.rd.trader.view.widgets.TracesPanel;
import ca.mss.rd.trader.view.widgets.WidgetEvent;
import ca.mss.rd.util.Logger;
import ca.mss.rd.util.UtilReflection;

public class LabController implements PropertyChangeListener {

	final static public String module = LabController.class.getSimpleName();
	final static public org.apache.log4j.Logger logger = ca.mss.rd.util.Debug.getLogger(module);

	static public final String EVENT_CHART_MARK_DRAW = "CtrlPointDraw";
	static public final String EVENT_NEW_FILE = "CtrlNewFile";
	static public final String EVENT_TRACE_SHOW = "CtrlTraceShow";
	static public final String EVENT_TRACE_HIDE = "CtrlTraceHide";

	private RdTab tab;
	public String dataSource;
	public QuotesSource quoteSource;
	private boolean doRun;
	
	public LabController() {
		super();
	}

	public LabController(RdTabModel model) {
		this();
		this.tab = new RdTab(model);
		this.dataSource = tab.getProperty("DataSource");
		this.quoteSource = (QuotesSource )UtilReflection.instance(FxTConfig.getDataSourceClass(dataSource));
		this.quoteSource.addPropertyChangeListener(this);

	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {

		final String eventName = evt.getPropertyName();
		@SuppressWarnings("unused")
		final String sourceClass = evt.getSource().getClass().getSimpleName();
		final Object oldval = evt.getOldValue();
		final Object newval = evt.getNewValue();

		if( QuoteEvent.EVENT_RECORD.equals(eventName)) {
			final GenericRecord record = (GenericRecord )newval;
			if( doRun ){
	            
				pcs.firePropertyChange(QuoteEvent.EVENT_RECORD, oldval, newval);

	            if( quoteSource.getThresholdMls() <= 0 ){
					try {
						quoteSource.lock();
					} catch (InterruptedException e) {
						if( Logger.ERROR.isOn ) Logger.ERROR.printf(e, "Failed to lock quote serializer");
					}
				}

			}
		} else if( QuoteEvent.EVENT_HEADER.equals(eventName)) {
			final String[] quotes = (String[] )newval;
            pcs.firePropertyChange(QuoteEvent.EVENT_HEADER, oldval, newval);
		} else if (LabButtonsPanel.EVENT_RUN.equals(eventName)) {
			this.doRun = ((Boolean )newval).booleanValue();
			if( doRun && !quoteSource.isRunning() ){
				if( quoteSource.isFirst() ){
		            pcs.firePropertyChange(LabButtonsPanel.EVENT_CLEAR, oldval, newval);
				}
		    	quoteSource.startThread();
			} else if( !doRun && quoteSource.isRunning() ) {
				quoteSource.interruptThread();
			}
		} else if (WidgetEvent.EVENT_LATENCY.equals(eventName)) {
			quoteSource.setThresholdMls(((Long)newval).longValue());
		} else if (QuotesCheckPanel.EVENT_QUOTE_CHOOSER.equals(eventName)) {
			final String[] quotetkn = ((String )newval).split(":"); // new=GBPUSD:true
		} else if (QuoteEvent.EVENT_FINISH.equals(eventName)) {
            pcs.firePropertyChange(QuoteEvent.EVENT_FINISH, oldval, newval);
		} else if (LabButtonsPanel.EVENT_CLEAR.equals(eventName)) {
			if( quoteSource.isRunning() ){
				quoteSource.interruptThread();
			} else {
				quoteSource.restart();
			}
		} else {
			//logger.warn(String.format("%s: Unhandled event [event=%s][from=%s][old=%s][new=%s]", module, eventName, sourceClass, oldval, newval));
		}
	}


	/*
     * Property change support
     */
    public final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    public final void addPropertyChangeListener(PropertyChangeListener listener) {
    	pcs.addPropertyChangeListener(listener);
    }
}
