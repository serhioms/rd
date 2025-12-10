package ca.mss.rd.flow.sp;

import java.util.Comparator;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import ca.mss.rd.flow.IContext;
import ca.mss.rd.flow.expr.jel.SpelSolver;
import ca.mss.rd.flow.prop.RdProp;

public class SPContext extends SpelSolver implements IContext<SPFlowProp> {

	public final SPFlowProp wkfProp = new SPFlowProp();
	private Map<String, RdProp> envProp;
	
	public SPContext() {
		super();
	}

	@Override
	public SPFlowProp getWkfProp() {
		return wkfProp;
	}

	@Override
	public void clear() {
		evalExprContext(envProp);
	}

	@Override
	public void setEnvProp(Map<String, RdProp> envProp) {
		this.envProp = envProp;
		evalExprContext(envProp);
	}

	@Override
	public Map<String, RdProp> getEnvProp() {
		return envProp;
	}
	
	private void evalExprContext(Map<String, RdProp> map) {
		if( map != null ){
			exprContext.clear();
			
			Map<String, RdProp> props = new TreeMap<String, RdProp>(new ValueComparator<String>(map));
			props.putAll(map);
			
			for(RdProp prop: props.values()) {
				evalExpression(prop.getName(), prop.getValue());
			}
		}
	}

	// Sort by value not by key as it is expected by TreeMap comparator
    static public class ValueComparator<T> implements Comparator<T> {
    	Map<T, RdProp> map;
     
    	public ValueComparator(Map<T, RdProp> map) {
    		this.map = map;
    	}

    	public int compare(T keyA, T keyB) {
    		return map.get(keyA).getId().compareTo(map.get(keyB).getId());
    	}
    }
    
}
