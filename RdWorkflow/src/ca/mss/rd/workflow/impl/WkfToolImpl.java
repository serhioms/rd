/**
 * 
 */
package ca.mss.rd.workflow.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import ca.mss.rd.workflow.def.WkfActivityToolType;
import ca.mss.rd.workflow.def.WkfTool;

/**
 * @author smoskov
 *
 */
public class WkfToolImpl implements WkfTool {
	
	final public static String module = WkfToolImpl.class.getName();
	static final long serialVersionUID = module.hashCode();

	private String id;
	private String descr;
	private WkfActivityToolType type;
	private List<String> param;

	private Map<String, WkfToolDefinition> definition;
	
	/**
	 * @return the id
	 */
	/* (non-Javadoc)
	 * @see ca.mss.workflow.def.WkfTool#getId()
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the descr
	 */
	/* (non-Javadoc)
	 * @see ca.mss.workflow.def.WkfTool#getDescr()
	 */
	public String getDescr() {
		return descr;
	}
	/**
	 * @param descr the descr to set
	 */
	public void setDescr(String descr) {
		this.descr = descr;
	}
	/**
	 * @return the type
	 */
	/* (non-Javadoc)
	 * @see ca.mss.workflow.def.WkfTool#getType()
	 */
	public WkfActivityToolType getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(WkfActivityToolType type) {
		this.type = type;
	}
	/**
	 * @return the param
	 */
	/* (non-Javadoc)
	 * @see ca.mss.workflow.def.WkfTool#getParam()
	 */
	public List<String> getParam() {
		return param;
	}
	/**
	 * @param param the param to set
	 */
	public void setParam(List<String> param) {
		this.param = param;
	}
	
	/**
	 * @return the definition
	 */
	/* (non-Javadoc)
	 * @see ca.mss.workflow.def.WkfTool#getDefinition()
	 */
	public Map<String, WkfToolDefinition> getDefinition() {
		return definition;
	}
	/**
	 * @param definition
	 */
	public void setDefinition(Map<String, WkfToolDefinition> definition) {
		this.definition = definition;
	}
	
	public String getDefinitionStr(){
		String def = "";
		if( definition != null ){
			Iterator<Entry<String, WkfToolDefinition>> iter = definition.entrySet().iterator();
			while( iter.hasNext() ){
				Entry<String, WkfToolDefinition> entry = iter.next();
				WkfToolDefinition definition = entry.getValue();
				def = def + "["+definition.name+ "="+definition.value + (definition.content==null?"":"["+definition.content+"]")+"]"; 
			}
		}
		return def;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return getType()+"::"+getId()+(getDescr()==null?"":"/*"+getDescr()+"*/")+"("+param+")"+"{"+getDefinitionStr()+"}";
	}
	
	
}
