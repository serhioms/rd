package ca.mss.rd.workflow.def;



public interface WkfTransition {

	public boolean isConditional();
	public boolean isOtherwise();
	
	public WkfCondition getCondition();

	public WkfActivity getEndActivity();
	public WkfActivity getBeginActivity();
	public String getId();
	
	public boolean evaluate(WkfContext context);

}
