/**
 * 
 */
package ca.mss.rd.tools.chess.com_wikispaces_chessprogramming_treesearch;

import ca.mss.rd.tools.chess.com_wikispaces_chessprogramming_treesearch.iface.NodeEvaluator;
import ca.mss.rd.tools.chess.com_wikispaces_chessprogramming_treesearch.iface.NodeNavigator;

/**
 * @author smoskov
 * 
 *         http://chessprogramming.wikispaces.com/Minimax
 * 
 * 
 *         Minimax is an algorithm used to determine the score in a zero-sum
 *         game after a certain number of moves, with best play according to an
 *         evaluation function.
 * 
 * 
 *         The algorithm can be explained like this: In a one-ply search, where
 *         only move sequences with length one are examined, the side to move
 *         (max player) can simply look at the evaluation after playing all
 *         possible moves. The move with the best evaluation is chosen. But for
 *         a two-ply search, when the opponent also moves, things become more
 *         complicated. The opponent (min player) also chooses the move that
 *         gets the best score. Therefore, the score of each move is now the
 *         score of the worst that the opponent can do.
 * 
 *         Implementation
 * 
 *         Below the pseudo code for an indirect recursive depth-first search.
 *         For clarity move making and unmaking before and after the recursive
 *         call is omitted.
 * 
 * 
 */

abstract public class Minimax extends NodeAdapter implements NodeNavigator, NodeEvaluator {


	@Override
	public boolean isNegaluated() {
		return false; // true turns minimax to negamax style
	}

	/* (non-Javadoc)
	 * @see com.wikispaces.chessprogramming.treesearch.iface.NodeNavigator#isCutoff()
	 */
	@Override
	public boolean isCutoff() {
		return false;
	}

	/* (non-Javadoc)
	 * @see com.wikispaces.chessprogramming.treesearch.iface.NodeEvaluator#evaluate(int)
	 */
	@Override
	public void startSearch(boolean maximize) {
		if( maximize )
			maxi(0);
		else
			mini(0);
	}

	long maxi(int depth) {
		
		if ( isMaxDepth(depth))
			return negaluate(depth);

		long max = -MAXIMUM;

		for(int i=0; hasNext(depth, i); i++) {

			makeNext(depth, i);
			long score = mini(depth + 1);
			takeBack(depth, score, isCutoff());

			if (score > max)
				max = score;
		}
		return max;
	}

	long mini(int depth) {

		if ( isMaxDepth(depth))
			return negaluate(depth);

		long min = +MAXIMUM;

		for(int i=0; hasNext(depth, i); i++) {

			makeNext(depth, i);
			long score = maxi(depth + 1);
			takeBack(depth, score, isCutoff());

			if ( score < min)
				min = score;
		}
		return min;
	}

}
