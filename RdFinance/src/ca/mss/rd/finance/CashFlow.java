package ca.mss.rd.finance;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import ca.mss.rd.finance.CalendarSequence.CalendarSequenceIterator;
import ca.mss.rd.finance.CashFlowEvents.CashFlowEvent;
import ca.mss.rd.finance.CashFlowEvents.OneTimeEvent;
import ca.mss.rd.finance.CashFlowEvents.RecurrentEvent;
import ca.mss.rd.util.UtilDateTime;



public class CashFlow {

	final static public String className = CashFlow.class.getName();

	final private RecurrentEvent recurrentEvent;
	final private ArrayList<OneTimeEvent> oneTimeEvents;
	final private Map<String, OneTimeEvent> oneTimeEventMap;

	public CashFlow() {
		this.recurrentEvent = null;
		this.oneTimeEventMap = null;
		this.oneTimeEvents = new ArrayList<OneTimeEvent>();
	}

	public CashFlow(RecurrentEvent recurrentEvent) {
		this.recurrentEvent = recurrentEvent;
		this.oneTimeEventMap = new HashMap<String, OneTimeEvent>();
		this.oneTimeEvents = null;
	}

	final private String getKey(Date onDate){
		switch(recurrentEvent.calendar.frequency ){
		case ANNUALLY: return UtilDateTime.format(onDate, "yyyy");
		case MONTHLY: return UtilDateTime.format(onDate, "yyyyMM");
		case BI_WEEKLY: return UtilDateTime.format(onDate, "yyyyMM")+UtilDateTime.getBiWeek(onDate);
		case SEMI_ANNUALLY: return UtilDateTime.format(onDate, "yyyy")+UtilDateTime.getSemiYear(onDate);
		case SEMI_MONTHLY: return UtilDateTime.format(onDate, "yyyyMM")+UtilDateTime.getSemiMo(onDate);
		case WEEKLY: return UtilDateTime.format(onDate, "yyyyMMWW");
		}
		return null;
	}
	
	final public void add(OneTimeEvent event){
		if( oneTimeEventMap != null ){
			if( event.descr == null )
				event.descr = recurrentEvent.descr;
			oneTimeEventMap.put(getKey(event.onDate), event);
		}
		if( oneTimeEvents != null ){
			int k=0;
			for(int i=0,max=oneTimeEvents.size(); i<max; i++){
				if( UtilDateTime.after(oneTimeEvents.get(i).onDate, event.onDate) ){
					k = i+1;
				}
			}
			oneTimeEvents.add(k, event);
		}
	}
	
	public class OneTimeFlowIterator extends FinancialIterator<CashFlowEvent> {
		
		final static public int BELOW_ZERO = -1;
		private int index;
		
		private OneTimeFlowIterator() {
			this.index = BELOW_ZERO;
		}

		final public CashFlowEvent next() {
			return oneTimeEvents.get(++index);
		}

		@Override
		public boolean hasNext() {
			return index+1 < oneTimeEvents.size();
		}

		@Override
		public Date nextDate() {
			return oneTimeEvents.get(index+1).onDate;
		}

	}
	
	public class RecurrentFlowIterator extends FinancialIterator<CashFlowEvent> {
		
		private CalendarSequenceIterator iter;
		
		private RecurrentFlowIterator() {
			this.iter = recurrentEvent.calendar.iterator();
		}

		private RecurrentFlowIterator(Date fromDate) {
			this.iter = recurrentEvent.calendar.iterator(fromDate);
		}

		final public CashFlowEvent next() {
			recurrentEvent.onDate = iter.next();
			OneTimeEvent onetimeEvent =  oneTimeEventMap.get(getKey(recurrentEvent.onDate));
			if( onetimeEvent != null)
				return onetimeEvent;
			else
				return recurrentEvent;
		}

		@Override
		public boolean hasNext() {
			return iter.hasNext();
		}

		@Override
		public Date nextDate() {
			return iter.nextDate();
		}

	}
	
	final public FinancialIterator<CashFlowEvent> iterator(){
		if( recurrentEvent == null )
			return new OneTimeFlowIterator();
		else
			return new RecurrentFlowIterator();
	}
	
	final public FinancialIterator<CashFlowEvent> iterator(Date fromDate){
		if( fromDate == null )
			return iterator();
		else if( recurrentEvent == null )
			return new OneTimeFlowIterator();

		FinancialIterator<CashFlowEvent> iter = new RecurrentFlowIterator(fromDate);
		while( UtilDateTime.before(fromDate, iter.nextDate()) ) iter.next();
		return iter;
	}

}


