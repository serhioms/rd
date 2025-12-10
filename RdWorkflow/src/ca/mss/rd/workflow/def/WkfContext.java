package ca.mss.rd.workflow.def;


public interface WkfContext {

	public boolean evaluate(WkfTransition transition);
	public void run(WkfActivity activity);
	public WkfData getData();

}
