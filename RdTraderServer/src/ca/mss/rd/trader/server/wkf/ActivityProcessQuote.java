package ca.mss.rd.trader.server.wkf;

import ca.mss.rd.flow.ActivityType;
import ca.mss.rd.flow.ActivityVisitor;
import ca.mss.rd.flow.IWorkflow;
import ca.mss.rd.flow.simple.RetriableActivity;
import ca.mss.rd.trader.model.indicator.Indicator;
import ca.mss.rd.util.Logger;

public class ActivityProcessQuote extends RetriableActivity<Object,ContextProcessQuote> {

	final public Indicator indicator;

	public ActivityProcessQuote(Indicator indicator) {
		super(ActivityType.Task);
		this.indicator = indicator;
		setName(indicator.getName());
	}

	@Override
	public void visitor(ActivityVisitor visitor, IWorkflow<Object,ContextProcessQuote> wkf) {
		switch (visitor){
		case clean:
			assert( Logger.ACTIVITY.isOn ? Logger.ACTIVITY.printf("%s.clean[%s]", indicator.getClass().getSimpleName(), indicator.getName()): true);
			indicator.clean();
			break;
		case stop:
			indicator.stop();
			assert( Logger.ACTIVITY.isOn ? Logger.ACTIVITY.printf("%s.stop[%s]", indicator.getClass().getSimpleName(), indicator.getName()): true);
			break;
		}
	}

	@Override
	public void task() {
		
		indicator.process();
		indicator.draw();
		
		assert( Logger.ACTIVITY.isOn ? Logger.ACTIVITY.printf("%s.taskBody[%s]", indicator.getClass().getSimpleName(), indicator.getName()): true);
	}

}
