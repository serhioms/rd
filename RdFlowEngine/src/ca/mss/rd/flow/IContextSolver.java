package ca.mss.rd.flow;

import java.util.Map;

import ca.mss.rd.flow.expr.Solver;
import ca.mss.rd.flow.prop.RdProp;


public interface IContextSolver<VAL,PROP> extends IContext<PROP>, Solver<VAL,PROP> {

	public PROP getWkfProp();

	public void setEnvProp(Map<String, RdProp> envProp);
	public Map<String, RdProp> getEnvProp();
	
	public void clear();
}
