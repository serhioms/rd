/**
 * 
 */
package ca.mss.rd.chessictk.wkfrepo;

import ca.mss.rd.workflow.dynamic.DynaFlow;
import ca.mss.rd.workflow.reader.WkfReader;

/**
 * @author smoskov
 *
 */
final public class ChessWorkflow extends DynaFlow {

	public static final String module = ChessWorkflow.class.getName();

	private WkfReader wkfReader;
	
	/**
	 * 
	 */
	public ChessWorkflow() {
		super();
	}

	/**
	 * @param wkfReader
	 */
	public ChessWorkflow(WkfReader wkfReader) {
		setWkfReader(wkfReader);
	}

	/**
	 * @return the wkfReader
	 */
	/* (non-Javadoc)
	 * @see ca.mss.workflow.dynamic.DynaFlow#getWkfReader()
	 */
	final public WkfReader getWkfReader() {
		return wkfReader;
	}

	/**
	 * @param wkfReader the wkfReader to set
	 */
	final public void setWkfReader(WkfReader wkfReader) {
		this.wkfReader = wkfReader;
		buildWorkflow();
	}

}
