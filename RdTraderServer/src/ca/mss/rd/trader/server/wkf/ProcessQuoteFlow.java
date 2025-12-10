package ca.mss.rd.trader.server.wkf;

import java.beans.PropertyChangeSupport;

import ca.mss.rd.flow.IFlowManager;
import ca.mss.rd.flow.simple.DefaultWorkflow;
import ca.mss.rd.parser.impl.GenericRecord;
import ca.mss.rd.trader.model.Quote;
import ca.mss.rd.trader.model.indicator.IndicatorDefault;
import ca.mss.rd.trader.model.indicator.TraderEvent;
import ca.mss.rd.util.Logger;
import ca.mss.rd.util.UtilDateTime;

public class ProcessQuoteFlow extends DefaultWorkflow<Object,ContextProcessQuote> {

	public ProcessQuoteFlow(String quoteName, PropertyChangeSupport pcs) {
		super(String.format("ProcessQuotesFlow.%s", quoteName));

		assert( Logger.WORKFLOW.isOn ? Logger.WORKFLOW.printf("Create [%s]", getName()): true);

		context = new ContextProcessQuote(quoteName, pcs);
		
		context.sendEvent(TraderEvent.CREATE_BL_INDICATOR, "", 0);

		workflow(this, 
				ProcessQuoteFlowRepository.workflowPNLTrend(this, context)
//				ProcessQuoteFlowRepository.workflowBreakLineTrend(this, context)
//				ProcessQuoteFlowRepository.workflowBreakTime(this, context)
//				ProcessQuoteFlowRepository.workflowReverseAverage(this, context)
//				ProcessQuoteFlowRepository.workflowAnalizer(this, context)
//				ProcessQuoteFlowRepository.workflowHuntingTrend(this, context)
//				ProcessQuoteFlowRepository.workflowBreakLine(this, context)
		);
	}

	public ActivityProcessQuote newActivity(IndicatorDefault indicator) {
		return new ActivityProcessQuote(indicator);
	}

	public void startFlow(IFlowManager wkfman, GenericRecord record) {
		assert( Logger.WORKFLOW_VERBOSE.isOn ? Logger.WORKFLOW_VERBOSE.printf("%s[%d].startFlow on %s", getName(), getId(), UtilDateTime.rightnow()): true);
		context.quote = Quote.populate(record, context.symbol);
		wkfman.queue(this);
	}

}
