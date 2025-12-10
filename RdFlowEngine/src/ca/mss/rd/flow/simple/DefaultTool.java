package ca.mss.rd.flow.simple;

import ca.mss.rd.flow.IActivity;
import ca.mss.rd.flow.IContext;
import ca.mss.rd.flow.ITool;
import ca.mss.rd.job.AbstractJob;

abstract public class DefaultTool<PROP, CONTEXT extends IContext<PROP>> extends AbstractJob implements ITool {

	static public final String NAME = "DefTool";

	private IActivity<PROP,CONTEXT> activity;
	private boolean isSync = ITool.SYNC_TOOL; 

	abstract public void executeTool();

	public DefaultTool(IActivity<PROP,CONTEXT> activity) {
		super();
		this.activity = activity;
		setName(NAME);
	}

	public final IActivity<PROP,CONTEXT> getActivity() {
		return activity;
	}

	public final void setActivity(DefaultActivity<PROP,CONTEXT> activity) {
		this.activity = activity;
	}

	@Override
	public final void executeJob() {
		
		activity.getManager().startNotify(this);

		if( isSync() ){
			/*
			 * Synchronous tool implementation
			 */
			executeTool();

			activity.getManager().endNotify(this);
			
			if( activity.decrementToolCounter() == 0 ){
				activity.finalizeActivity( this );
			}
		} else {
			// TODO: Asynchronous tool must be cleared on workflow manager level by external process
			throw new RuntimeException(String.format("DefTool.Execute Asynchronose Job %s not implemented yet (Activity=%d)", getName(), activity.getId()));
		}

	}

	@Override
	public String getPath() {
		return String.format("%s.%d", activity.getPathId(), getId());
	}

	@Override
	public boolean isSync() {
		return isSync;
	}

	public final void setSync(boolean isSync) {
		this.isSync = isSync;
	}

	
}