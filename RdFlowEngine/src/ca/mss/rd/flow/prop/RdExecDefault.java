package ca.mss.rd.flow.prop;

import java.util.HashSet;
import java.util.Set;

import ca.mss.rd.flow.expr.Solver;

import com.scotiabank.maestro.msg.type.RdResult;


public class RdExecDefault implements RdExec<String,RdProp> {

	private String name;

	private final Set<String> outSet = new HashSet<String>(0);
	private final Set<String> inSet = new HashSet<String>(0);
	private final Set<String> valSet = new HashSet<String>(0);

	public RdExecDefault() {
		super();
	}

	@Override
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void addOutSet(String name) {
		outSet.add(name);
	}

	@Override
	public Set<String> getOutSet() {
		return outSet;
	}

	
	@Override
	public Set<String> getInSet() {
		return inSet;
	}

	@Override
	public Set<String> getValSet() {
		return valSet;
	}

	@Override
	public RdResult execute(Solver<String, RdProp> solver) {
		throw new RuntimeException(String.format("Execution of %s not implemented!", this.getClass().getSimpleName()));
	}
	
}
