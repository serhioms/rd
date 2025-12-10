/**
 * 
 */
package ca.mss.rd.workflow.impl;

import java.util.List;

import ca.mss.rd.workflow.def.WkfActivityToolType;

/**
 * @author smoskov
 *
 */
public class WkfToolContext {
	
	private String id;
	private String descr;
	private WkfActivityToolType type;
	private List<String> param;

	private WkfToolDefinition[] definition;

	/**
	 * @return the id
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
	public WkfToolDefinition[] getDefinition() {
		return definition;
	}
	/**
	 * @param definition the definition to set
	 */
	public void setDefinition(WkfToolDefinition[] definition) {
		this.definition = definition;
	}
	
	public String getDefinitionStr(){
		String def = "";
		if( definition != null ){
			for(int i=0; i<definition.length; i++){
				def = def + "["+definition[i].name+ "="+definition[i].value + (definition[i].content==null?"":"["+definition[i].content+"]")+"]"; 
			}
		}
		return def;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return getType()+"::"+getId()+(getDescr()==null?"":"/*"+getDescr()+"*/")+"{"+getDefinitionStr()+"}";
	}
	
	
}
