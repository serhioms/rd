package ca.mss.rd.flow.prop;

import java.util.Set;

import ca.mss.rd.flow.expr.Solver;

import com.scotiabank.maestro.msg.type.RdResult;

public interface RdExec<VAL,PROP> {

	public String getName();
	
	public RdResult execute(Solver<VAL,PROP> solver);
	
	public Set<String> getOutSet();
	public Set<String> getInSet();
	public Set<String> getValSet();

}
