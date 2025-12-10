/**
 * 
 */
package ca.mss.rd.tools.chess.com_wikispaces_chessprogramming_treesearch;

import ca.mss.rd.tools.chess.com_wikispaces_chessprogramming_treesearch.iface.NodeEvaluator;
import ca.mss.rd.tools.chess.com_wikispaces_chessprogramming_treesearch.iface.NodeNavigator;

/**
 * @author smoskov
 * 
 *         http://chessprogramming.wikispaces.com/Negamax
 * 
 * 
 *         Negamax is a common way of implementing minimax and derived
 *         algorithms. Instead of using two separate subroutines for the Min
 *         player and the Max player, it passes on the negated score due to
 *         following mathematical relation:
 * 
 *         max(a, b) == -min(-a, -b)
 * 
 *         This is how the pseudo-code of the recursive algorithm looks like.
 *         For clarity move making and unmaking is omitted.
 * 
 * 
 *         How to Use NegaMax
 * 
 *         Once you have your negaMax function � there are two questions which
 *         arise � i) how do you initially call negaMax, and ii) if negaMax is
 *         only returning an optimal score, then just how is it that you can
 *         know which particular move this score is related to? These two
 *         questions are related.
 * 
 *         One calls negaMax with another root negaMax which makes the call to
 *         the negaMax proper with the default search depth. In the body of the
 *         loop of this root negaMax, in the loop which generates all the root
 *         moves � there one holds a variable as you call negaMax on the
 *         movement of each piece � and that is where you find the particular
 *         move attached to the score � in the line where you find score > max,
 *         right after you keep track of it by adding max = score � in the root
 *         negamax, that is where you pick out your move � which is what the
 *         root negaMax will return (instead of a score).
 * 
 *         Note! In order for negaMax to work, your Static Evaluation function
 *         must return a score relative to the side to being evaluated, and this
 *         score must be symmetric (e.g. the simplest score evaluation could be:
 * 
 *         score = materialWeight * (numWhitePieces - numBlackPieces) * who2move
 *         //where who2move = 1 for white, and who2move = -1 for black).
 * 
 */
abstract public class Negamax extends NodeAdapter implements NodeNavigator, NodeEvaluator {

	/* (non-Javadoc)
	 * @see com.wikispaces.chessprogramming.treesearch.iface.NodeNavigator#isCutoff()
	 */
	@Override
	public boolean isCutoff() {
		return false;
	}

	/* (non-Javadoc)
	 * @see com.wikispaces.chessprogramming.treesearch.iface.NodeNavigator#search(int)
	 */
	@Override
	public void startSearch(boolean maximize) {
 		this.negaMax(0);
	}
	
	long negaMax(int depth) {

		if ( isMaxDepth(depth) )
			return evaluate(depth);

		long max = -MAXIMUM;

		for(int i=0; hasNext(depth, i); i++) {

			makeNext(depth, i);
			long score = -negaMax(depth + 1);
			takeBack(depth, score, isCutoff());

			if ( score > max )
				max = score;
		}
		return max;
	}
}
