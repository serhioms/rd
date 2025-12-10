/**
 * 
 */
package ca.mss.rd.tools.chess.com_wikispaces_chessprogramming_treesearch;

import ca.mss.rd.tools.chess.com_wikispaces_chessprogramming_treesearch.iface.NodeEvaluator;

/**
 * @author smoskov
 *
 */
abstract public class NodeAdapter implements NodeEvaluator {

	/* (non-Javadoc)
	 * @see com.wikispaces.chessprogramming.treesearch.iface.NodeEvaluator#negaluate(int)
	 */
	@Override
	public long negaluate(int depth) {
		return getMaxDepth()%2 == 0? evaluate(depth): isNegaluated()? -evaluate(depth): evaluate(depth);
	}

	/* (non-Javadoc)
	 * @see com.wikispaces.chessprogramming.treesearch.iface.NodeEvaluator#maximize()
	 */
	@Override
	public boolean maximize() {
		return isNegaluated()? true: getMaxDepth()%2 == 0? true: false;
	}

	/* (non-Javadoc)
	 * @see com.wikispaces.chessprogramming.treesearch.iface.NodeEvaluator#isNegaluated()
	 */
	@Override
	public boolean isNegaluated() {
		return true; // by default acts as negaluate 
	}
	
}
