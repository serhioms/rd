package ca.mss.rd.wkfdef.iwutil;

import java.util.List;

import ca.mss.rd.flow.prop.RdProp;
import ca.mss.rd.wkfdef.iw.ComponentReference;
import ca.mss.rd.wkfdef.iw.ExternalWorkflowReference;
import ca.mss.rd.wkfdef.iw.Parallel;
import ca.mss.rd.wkfdef.iw.Sequential;
import ca.mss.rd.wkfdef.iw.Stage;
import ca.mss.rd.wkfdef.iw.WorkflowDefinition;

public interface IWFlowVisitor {

	public void workflowDefinition(int level, WorkflowDefinition iwwkf, List<RdProp> input, List<RdProp> output, List<RdProp> general);
	
	public void stage(int level, Stage iwstage);
	public void parallel(int level, Parallel iwparallel);
	public void sequential(int level, Sequential iwsequential);

	public void componentReference(int level, ComponentReference iwcomponentReference);

	public void externalWorkflowReference(int level, ExternalWorkflowReference iwexternalWorkflowReference, List<RdProp> input, List<RdProp> output);

	public void workflowEnds(int level, WorkflowDefinition iwwkf);
	public void parallelEnds(int level, Parallel iwparallel);
	public void sequentialEnds(int level, Sequential iwsequential);
	
}
