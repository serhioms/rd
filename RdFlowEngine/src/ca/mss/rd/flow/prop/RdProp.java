package ca.mss.rd.flow.prop;

import com.scotiabank.maestro.orch.api.workflow.PropertyScopeTypeEnum;

public interface RdProp { // WorkflowPropertyVO

	public String getName();
	public String getValue();
	public PropertyScopeTypeEnum getScope();
	public String getSource();
	public String getTarget();

	public void setId(Integer id);
	public Integer getId();
	
	
}
