package ca.mss.rd.flow.prop;


public interface RdProp { // WorkflowPropertyVO

	public String getName();
	public String getValue();
	public String getSource();
	public String getTarget();

	public void setId(Integer id);
	public Integer getId();
	
	
}
