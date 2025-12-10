package ca.mss.rd.flow.tools.exec;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import ca.mss.rd.flow.prop.RdExecDefault;

public class RdJavaExt extends RdExecDefault {

	private String className;

	private final Map<String, String> inMap = new HashMap<String, String>(0);

	public void addInMap(String name, String value) {
		inMap.put(name, value);
		getInSet().add(name);
		getValSet().add(value);
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
		getValSet().add(className);
	}

	public Map<String, String> getInMap() {
		return inMap;
	}

	@Override
	public String toString() {
		String in = "";
		for(Iterator<Entry<String, String>> iter=inMap.entrySet().iterator(); iter.hasNext(); ){
			Entry<String, String> next = iter.next();
			in += String.format(in.isEmpty()?"%s=%s":",%s=%s", next.getKey(), next.getValue());
		}

		String out = "";
		for(String name: getOutSet()){
			out += String.format(out.isEmpty()?"%s":",%s", name);
		}

		return String.format("JavaExt[class=%s]In[%s]Out[%s]", className, in, out);
	}
}
