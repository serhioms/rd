/**
 * 
 */
package ca.mss.rd.workflow.reader;

import java.util.Map;

import ca.mss.rd.workflow.def.WkfActivityImplementation;
import ca.mss.rd.workflow.def.WkfActivityInstantiation;
import ca.mss.rd.workflow.def.WkfActivityMode;
import ca.mss.rd.workflow.def.WkfTool;
import ca.mss.rd.workflow.def.WkfTransitionType;

/**
 * @author smoskov
 *
 */
public interface WkfReader {

	public int getProcessSize();
	
	public int getActivitySize(int process);
	public boolean getActivityCanStart(int process, int activity);
	public WkfActivityImplementation getActivityImplementation(int process, int activity);
	public WkfActivityInstantiation getActivityInstantiation(int process, int activity);
	public WkfActivityMode getActivityStartMode(int process, int activity);
	public int getActivityPriority(int process, int activity);
	public WkfActivityMode getActivityFinishMode(int process, int activity);
	public String getActivityId(int process, int activity);
	public String getActivityName(int process, int activity);
	public String getActivityDesc(int process, int activity);
	public WkfTool[] getActivityTool(int process, int activity);
	
	public int getTransitionSize(int process);
	public String getTransitionFrom(int process, int transition);
	public String getTransitionTo(int process, int transition);
	public WkfTransitionType getTransitionType(int process, int transition);
	public String getTransitionId(int process, int transition);
	public String getTransitionExpression(int process, int transition);
	
	public Map<String, Object> getInitialData(int process);
}
