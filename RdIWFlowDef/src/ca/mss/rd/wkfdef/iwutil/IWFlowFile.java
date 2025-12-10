package ca.mss.rd.wkfdef.iwutil;

import java.util.Map;

import ca.mss.rd.job.AbstractJob;
import ca.mss.rd.util.Logger;
import ca.mss.rd.wkfdef.iw.WorkflowDefinition;

public class IWFlowFile extends AbstractJob {

	private String filePath;
	private Map<String, WorkflowDefinition> repo;
	
	
	public IWFlowFile(String filePath, Map<String, WorkflowDefinition> repo) {
		super();
		this.filePath = filePath;
		this.repo = repo;
	}


	public IWFlowFile(boolean shutdown) {
		super(shutdown);
	}


	@Override
	public void executeJob() {
		try {
			WorkflowDefinition wkf = IWHelper.readFlowDefn(filePath);
			repo.put(wkf.getExternalWorkflowCode(), wkf);
		} catch(Throwable t){
			if( Logger.ERROR.isOn ) Logger.ERROR.printf(t);
		}
	}
	
}
