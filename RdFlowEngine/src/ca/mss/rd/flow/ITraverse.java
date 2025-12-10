package ca.mss.rd.flow;


public interface ITraverse<PROP, CONTEXT extends IContext<PROP>> {

	public void visitor(IActivity<PROP,CONTEXT> activity, IWorkflow<PROP,CONTEXT> wkf);
	
}
