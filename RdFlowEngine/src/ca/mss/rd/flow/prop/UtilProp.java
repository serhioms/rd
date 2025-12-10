package ca.mss.rd.flow.prop;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.scotiabank.maestro.orch.api.workflow.PropertyScopeTypeEnum;


public class UtilProp {

	public static List<RdProp> toGlobalProList(String... args) {
		List<RdProp> props = new ArrayList<RdProp>(0);
		
		if( args != null ){
			for (int i = 0; i < args.length; i += 2) {
				props.add(toProp(args[i + 0], args[i + 1]));
			}
		}
		
		return props;
	}
	
	public static Map<String, RdProp> toGlobalProMap(String... args) {
		Map<String, RdProp> props = new HashMap<String, RdProp>(0);
		if( args != null ){
			for (int i = 0; i < args.length; i += 2) {
				RdProp prop = toProp(args[i + 0], args[i + 1]);
				prop.setId(i);
				props.put(args[i + 0], prop);
			}
		}
		
		return props;
	}
	
	public static RdProp toProp(String name, String value, PropertyScopeTypeEnum scope) {
		return new DefProp(name, value, scope);
	}
	
	public static RdProp toProp(String name, PropertyScopeTypeEnum scope, String outName) {
		return new DefProp(name, scope, outName);
	}
	
	public static RdProp toProp(String name, String value) {
		return new DefProp(name, value, PropertyScopeTypeEnum.GLOBAL);
	}
}
