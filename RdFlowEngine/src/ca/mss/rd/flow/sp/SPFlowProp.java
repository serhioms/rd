package ca.mss.rd.flow.sp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ca.mss.rd.flow.prop.RdExec;
import ca.mss.rd.flow.prop.RdProp;
import ca.mss.rd.util.map.SmartMap;

public class SPFlowProp {

	private static final String STAGE_PRO = "STAGE_PRO";
	private static final String SUBFLOW_OUTPUT = "SUBFLOW_OUTPUT";
	private static final String SUBFLOW_INPUT = "SUBFLOW_INPUT";
	private static final String WKF_OUTPUT = "WKF_OUTPUT";
	private static final String WKF_INPUT = "WKF_INPUT";
	private static final String WKF_GENERAL = "WKF_GENERAL";

	static public final String[] PROTYPE = new String[]{WKF_INPUT, WKF_OUTPUT, WKF_GENERAL, SUBFLOW_INPUT, SUBFLOW_OUTPUT, STAGE_PRO}; 
	
	public final Map<String, RdExec<String,RdProp>> propExec = new HashMap<String, RdExec<String,RdProp>>(0);  // activityId/exec
	private final SmartMap<String, Map<String,List<RdProp>>> propDef = new SmartMap<String, Map<String, List<RdProp>>>(){
		@Override
		public Map<String, List<RdProp>> valueFactory(Object key) {
			return SmartMap.<String, List<RdProp>>mapFactory(0);
		}
	};

	public SPFlowProp() {
		super();
	}

	public Map<String,List<RdProp>> getWkfPropGeneral() {
		return propDef.get(WKF_GENERAL);
	}

	public Map<String,List<RdProp>> getWkfPropInput() {
		return propDef.get(WKF_INPUT);
	}

	public Map<String,List<RdProp>> getWkfPropOutput() {
		return propDef.get(WKF_OUTPUT);
	}

	public Map<String,List<RdProp>> getSubPropInput() {
		return propDef.get(SUBFLOW_INPUT);
	}

	public Map<String,List<RdProp>> getSubPropOutput() {
		return propDef.get(SUBFLOW_OUTPUT);
	}

	public Map<String, RdExec<String,RdProp>> getStageExec() {
		return propExec;
	}
	
}
