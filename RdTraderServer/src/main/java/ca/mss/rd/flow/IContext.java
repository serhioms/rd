package ca.mss.rd.flow;

import java.util.Map;

import ca.mss.rd.flow.prop.RdProp;


public interface IContext<WKFPROP> {

	public WKFPROP getWkfProp();

	public void setEnvProp(Map<String, RdProp> envProp);
	public Map<String, RdProp> getEnvProp();
	
	public void clear();
}
