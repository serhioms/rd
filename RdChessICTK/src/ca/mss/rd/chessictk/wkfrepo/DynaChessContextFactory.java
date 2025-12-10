/**
 * 
 */
package ca.mss.rd.chessictk.wkfrepo;

import ca.mss.rd.workflow.def.WkfContext;
import ca.mss.rd.workflow.dynamic.DynaContext;
import ca.mss.rd.workflow.dynamic.DynaContextFactory;
import ca.mss.rd.workflow.dynamic.DynaData;
import ca.mss.rd.workflow.dynamic.expression.WkfExpressionEngine;
import ca.mss.rd.workflow.dynamic.service.WkfServiceEngine;
import ca.mss.rd.workflow.expression.JelExprEngine;
import ca.mss.rd.workflow.reader.WkfReader;
import ca.mss.rd.workflow.service.WkfServiceEngineImpl;

/**
 * @author mss
 *
 */
public class DynaChessContextFactory extends DynaContextFactory {

	private WkfServiceEngine serviceEngine = new WkfServiceEngineImpl();
	private WkfExpressionEngine expressionEngine = new JelExprEngine();
	private ChessProcedure chessProcedures = new ChessProcedure();
	
	/**
	 * @return the chessProcedures
	 */
	public final ChessProcedure getChessProcedures() {
		return chessProcedures;
	}

	/**
	 * @param chessProcedures the chessProcedures to set
	 */
	public final void setChessProcedures(ChessProcedure chessProcedures) {
		this.chessProcedures = chessProcedures;
	}
	
	/**
	 * @return the service
	 */
	public WkfServiceEngine getServiceEngine() {
		return serviceEngine;
	}

	/**
	 * @param service the service to set
	 */
	public void setServiceEngine(WkfServiceEngine serviceEngine) {
		this.serviceEngine = serviceEngine;
	}

	/**
	 * @return the expressionEngine
	 */
	public final WkfExpressionEngine getExpressionEngine() {
		return expressionEngine;
	}

	/**
	 * @param expressionEngine the expressionEngine to set
	 */
	public final void setExpressionEngine(WkfExpressionEngine expressionEngine) {
		this.expressionEngine = expressionEngine;
	}

	/**
	 * 
	 */
	public DynaChessContextFactory() {
	}

	/**
	 * @param wkfReader
	 */
	public DynaChessContextFactory(WkfReader wkfReader) {
		super(wkfReader);
	}

	
	/* (non-Javadoc)
	 * @see ca.mss.workflow.def.WkfContextFactory#createNewContext()
	 */
	@Override
	public WkfContext createNewContext() {
		DynaContext context = new DynaContext();
		context.setData(new DynaData(getInitialData()));
		context.setServiceEngine(serviceEngine);
		context.setProcedureDelegator(chessProcedures);
		context.setExpressionEngine(expressionEngine);
		return context;
	}

}
