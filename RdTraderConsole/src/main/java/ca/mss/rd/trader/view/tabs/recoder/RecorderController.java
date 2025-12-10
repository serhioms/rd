package ca.mss.rd.trader.view.tabs.recoder;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import ca.mss.rd.parser.impl.GenericRecord;
import ca.mss.rd.swing.RdTab;
import ca.mss.rd.swing.RdTabModel;
import ca.mss.rd.trader.FxTConfig;
import ca.mss.rd.trader.src.oanda.FxOandaFile;
import ca.mss.rd.trader.src.oanda.QuoteEvent;
import ca.mss.rd.trader.src.oanda.QuotesSource;
import ca.mss.rd.trader.util.RecordDataToFile;
import ca.mss.rd.trader.view.widgets.QuotesCheckPanel;
import ca.mss.rd.trader.view.widgets.WidgetEvent;
import ca.mss.rd.util.Logger;
import ca.mss.rd.util.UtilDateTime;
import ca.mss.rd.util.UtilMisc;
import ca.mss.rd.util.UtilReflection;

public class RecorderController implements PropertyChangeListener {

	@SuppressWarnings("unused")
	private static final long serialVersionUID = RecorderController.class.hashCode();
	final static public String module = RecorderController.class.getSimpleName();
	final static public org.apache.log4j.Logger logger = ca.mss.rd.util.Debug.getLogger(module);

	static public final String EVENT_RUN_BUTTON = "CtrlRunButton";
	static public final String EVENT_DRAW_CHART = "CtrlDrawChart";
	static public final String EVENT_NEW_FILE = "CtrlNewFile";

	private final RdTab tab;
	public final String dataSource;
	public final QuotesSource quotesSource;
	private final RecordDataToFile recorder;
	private boolean isRecorderOn, isOnline;
	private String[] header;
	
	public final List<String> visibleCharts = new ArrayList<String>();
	
	static private final Set<RecorderController> allRecorderControllersInstances = new HashSet<RecorderController>();
	
	public RecorderController(RdTabModel model) {
		super();
		this.tab = new RdTab(model);
		this.dataSource = tab.getProperty("DataSource");
		this.quotesSource = (QuotesSource )UtilReflection.instance(FxTConfig.getDataSourceClass(dataSource));
		this.quotesSource.addPropertyChangeListener(this);
		this.recorder = new RecordDataToFile(dataSource);
		allRecorderControllersInstances.add(this);
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {

		final String eventName = evt.getPropertyName();
		final String sourceClass = evt.getSource().getClass().getSimpleName();
		final Object oldval = evt.getOldValue();
		final Object newval = evt.getNewValue();

		if( QuoteEvent.EVENT_RECORD.equals(eventName)) {
			if( isOnline ){
				GenericRecord record = (GenericRecord )newval;
				if( isRecorderOn ){
					recorder.dumpAppend(record.getRow());
		            pcs.firePropertyChange(QuoteEvent.EVENT_RECORD, oldval, newval);
				}
			    if( visibleCharts.size() > 0 ){
		            pcs.firePropertyChange(EVENT_DRAW_CHART, "", record);
			    }
			}
		} else if( QuoteEvent.EVENT_HEADER.equals(eventName)) {
			header = (String[] )newval;
			if( recorder.create() ){
				recorder.dumpAppend(UtilMisc.toString(header, "\t", ""));
			}
            pcs.firePropertyChange(QuoteEvent.EVENT_HEADER, "", header);
		} else if( QuoteEvent.EVENT_NEXT_DAY.equals(eventName)) {
			recorder.dumpClose();
			if( recorder.create() ){
				recorder.dumpAppend(UtilMisc.toString(header, "\t", ""));
			}
            pcs.firePropertyChange(QuoteEvent.EVENT_HEADER, "", header);
		} else if (RecoderButtonsPanel.EVENT_RUN.equals(eventName)) {
			this.isOnline = ((Boolean )newval).booleanValue();
			if( isOnline ){
				if( !quotesSource.isRunning() ){
					recorder.setFilePostfix(String.format("%s=%d-sec", dataSource, quotesSource.getThresholdMls()/1000));
					String lastrow = recorder.getLastRow();
					if( lastrow != null ){
						String[] token = lastrow.split("\t");
						String date = token[1]+" "+token[2];
						quotesSource.setStartDate(UtilDateTime.parse(date, FxOandaFile.ROW_DATE_TIME_FORMATTER));
					} else {
						quotesSource.setStartDate(UtilDateTime.getDayStart(UtilDateTime.now()));
			            pcs.firePropertyChange(EVENT_NEW_FILE, null, recorder.getFileName());
					}
					quotesSource.startThread();
				}
			} else if( isAllOffLine() ){
				quotesSource.interruptThread();
				recorder.dumpClose();
			}
		} else if (RecoderButtonsPanel.EVENT_RECODER.equals(eventName)) {
			this.isRecorderOn = ((Boolean )newval).booleanValue();
			if( isRecorderOn ){
                pcs.firePropertyChange(EVENT_RUN_BUTTON, false, true);
			} else {
				recorder.dumpClose();
			}
		} else if (RecoderButtonsPanel.EVENT_CLEAR.equals(eventName)) {
			assert( Logger.EVENT.isOn ? Logger.EVENT.printf(String.format("%s: Process event [event=%s][from=%s][old=%s][new=%s]", module, eventName, sourceClass, oldval, newval)): true);
		} else if (WidgetEvent.EVENT_LATENCY.equals(eventName)) {
			if( oldval != null ){ // skip system events which oldval == null
				quotesSource.setThresholdMls(((Integer)newval).intValue()*1000);
			}
		} else if (QuotesCheckPanel.EVENT_QUOTE_CHOOSER.equals(eventName)) {
			final String[] showQuoteChart = ((String )newval).split(":"); // new=GBPUSD:true
			quoteChooserHandler(showQuoteChart[0], Boolean.parseBoolean(showQuoteChart[1]));
		} else {
			//logger.warn(String.format("%s: Unhandled event [event=%s][from=%s][old=%s][new=%s]", module, eventName, sourceClass, oldval, newval));
		}
	}


	private final boolean isAllOffLine(){
		for(Iterator<RecorderController> iter=allRecorderControllersInstances.iterator(); iter.hasNext(); ){
			if( iter.next().isOnline )
				return false;
		}
		return true;
	}

	@SuppressWarnings("unused")
	private final boolean isAnyRecorderOn(){
		for(Iterator<RecorderController> iter=allRecorderControllersInstances.iterator(); iter.hasNext(); ){
			if( iter.next().isRecorderOn)
				return true;
		}
		return false;
	}

	public final void quoteChooserHandler(final String chartName, final boolean isShow){
		if( isShow )
			visibleCharts.add(chartName);
		else
			visibleCharts.remove(chartName);
	}

	/*
     * Property change support
     */
    final protected PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    public final void addPropertyChangeListener(PropertyChangeListener listener) {
    	pcs.addPropertyChangeListener(listener);
    }
}
