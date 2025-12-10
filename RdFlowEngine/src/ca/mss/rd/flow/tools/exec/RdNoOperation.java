package ca.mss.rd.flow.tools.exec;

import com.scotiabank.maestro.msg.type.RdResult;

import ca.mss.rd.flow.expr.Solver;
import ca.mss.rd.flow.prop.RdExecDefault;
import ca.mss.rd.flow.prop.RdProp;



public class RdNoOperation extends RdExecDefault {

	private String input, outPropName, descr;
	private Integer ntasks, ninterimStatusResponses;
	private Long durationBetweenEventsMls;

	public String getInput() {
		return input;
	}

	public void setInput(String input) {
		this.input = input;
	}

	public Integer getNtasks() {
		return ntasks;
	}

	public void setNtasks(Integer ntasks) {
		this.ntasks = ntasks;
	}

	public Integer getNinterimStatusResponses() {
		return ninterimStatusResponses;
	}

	public void setNinterimStatusResponses(Integer ninterimStatusResponses) {
		this.ninterimStatusResponses = ninterimStatusResponses;
	}

	public Long getDurationBetweenEventsMls() {
		return durationBetweenEventsMls;
	}

	public void setDurationBetweenEventsMls(Long durationBetweenEventsMls) {
		this.durationBetweenEventsMls = durationBetweenEventsMls;
	}

	public String getOutPropName() {
		return outPropName;
	}

	public void setOutPropName(String outPropName) {
		this.outPropName = outPropName;
		getOutSet().add(outPropName);
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	@Override
	public String toString() {
		return String.format("NoOperation[input=%s][output=%s][ntasks=%d][ninterim=%d][duration=%d][descr=%s]", input, outPropName, ntasks, ninterimStatusResponses, durationBetweenEventsMls, descr);
	}

	@Override
	public RdResult execute(Solver<String, RdProp> solver) {
		return null;
	}
	
	
}
