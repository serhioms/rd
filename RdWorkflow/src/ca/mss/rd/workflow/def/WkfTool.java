/**
 * 
 */
package ca.mss.rd.workflow.def;

import java.util.List;
import java.util.Map;

import ca.mss.rd.workflow.impl.WkfToolDefinition;

/**
 * @author smoskov
 *
 */
public interface WkfTool {

	public String getId();
	public String getDescr();
	public WkfActivityToolType getType();
	public Map<String, WkfToolDefinition> getDefinition();
	public List<String> getParam();


}
