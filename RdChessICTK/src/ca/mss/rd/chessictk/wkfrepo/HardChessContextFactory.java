/**
 * 
 */
package ca.mss.rd.chessictk.wkfrepo;

import ca.mss.rd.workflow.reader.WkfReader;

/**
 * @author mss
 *
 */
public class HardChessContextFactory extends DynaChessContextFactory {

	/**
	 * 
	 */
	public HardChessContextFactory() {
		setServiceEngine(new HardChessServiceEngine());
		setExpressionEngine(new HardChessExpressionEngine());
	}

	/**
	 * @param wkfReader
	 */
	public HardChessContextFactory(WkfReader wkfReader) {
		super(wkfReader);
		setServiceEngine(new HardChessServiceEngine());
		setExpressionEngine(new HardChessExpressionEngine());
	}

}
