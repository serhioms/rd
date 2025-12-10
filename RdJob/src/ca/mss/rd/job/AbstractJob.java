package ca.mss.rd.job;

import ca.mss.rd.util.Logger;

abstract public class AbstractJob implements IJob {

	static final public boolean SHUTDOWN = true;
	static final public Shutdown shutdown = new Shutdown();

	static private long counter;
	
	protected long id;
	protected String name;
	protected boolean isShutdown;
	
	public AbstractJob() {
		this(false);
		id = ++counter;
	}

	public AbstractJob(boolean isShutdown) {
		this(isShutdown? 0L: -1L, isShutdown);
	}

	public AbstractJob(long id) {
		this(id, false);
	}

	public AbstractJob(String name) {
		this.name = name;
		this.isShutdown = false;
	}

	public AbstractJob(long id, boolean isShutdown) {
		this.isShutdown = isShutdown;
		this.id = id;
		
		if( isShutdown ){
			setName(IJob.SHUTDOWN);
		}
		
		assert( Logger.VERBOSE.isOn ? Logger.VERBOSE.printf("%s[%d] created", getName(), id): true);
	}


	@Override
	public boolean isShutdown() {
		return isShutdown;
	}

	@Override
	public long getId() {
		return id;
	}

	@Override
	public void setId(long id) {
		this.id = id;
	}

	@Override
	public String getName() {
		return name == null? getClass().getSimpleName(): name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}
	
	static private class Shutdown extends AbstractJob {

		public Shutdown() {
			super(true);
		}

		@Override
		public void executeJob() {
		}
		
	}

}
