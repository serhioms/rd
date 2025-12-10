/**
 * 
 */
package ca.mss.rd.test.workflow.interceptors;

 import ca.mss.rd.workflow.def.WkfContext;
import ca.mss.rd.workflow.def.WkfContextFactory;

/**
 * @author smoskov
 *
 */
public class JUnitContextFactory implements WkfContextFactory {

	public static final String module = JUnitContextFactory.class.getName();
	private static org.apache.log4j.Logger logger = ca.mss.rd.util.Debug.getLogger(module);
	
	private WkfContextFactory wkfContextFactory;
	
	
	/**
	 * @param wkfContextFactory
	 */
	public JUnitContextFactory(WkfContextFactory wkfContextFactory) {
		this.wkfContextFactory = wkfContextFactory;
	}


	/* (non-Javadoc)
	 * @see ca.mss.workflow.def.WkfContextFactory#createNewContext()
	 */
	@Override
	public WkfContext createNewContext() {
		WkfContext context = wkfContextFactory.createNewContext();
		if( logger.isDebugEnabled()) logger.debug("New context [context="+context+"]");
		return new JUnitContext(context);
	}
	
}
