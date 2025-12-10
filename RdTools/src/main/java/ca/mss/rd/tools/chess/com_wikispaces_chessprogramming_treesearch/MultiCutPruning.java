/**
 * 
 */
package ca.mss.rd.tools.chess.com_wikispaces_chessprogramming_treesearch;

/**
 * @author smoskov
 * 
 *         http://chessprogramming.wikispaces.com/Multi-Cut
 * 
 * 
 *         Multi-Cut is a speculative pruning mechanism for chessplaying
 *         programs created by Yngvi Bj�rnsson [1] [2] . The basic idea is to
 *         perform a reduced search of the first C (i.e. 3) up to M (i.e. 6)
 *         moves, to prove an expected Cut-node is not singular, that is
 *         multiple (C) moves fail high, and to prune the whole subtree in that
 *         case by returning the hard beta bound. Mark Winands' enhanced forward
 *         pruning applies Multi-Cut even at expected All-nodes, with slight
 *         modifications on a PVS framework [3] .
 * 
 *         Abstract from the Workshop Chess and Mathematics, 2008 [5] [6] :
 * 
 *         The alpha-beta algorithm is the most popular method for searching
 *         game-trees in adversary board games such as chess. It is much more
 *         efficient than a plain brute-force minimax search because it allows a
 *         large portion of the game-tree to be pruned, while still backing up
 *         the correct game-tree value. However, the number of nodes visited by
 *         the algorithm still increases exponentially with increasing search
 *         depth. This obviously limits the scope of the search, since
 *         game-playing programs must meet external time constraints: often
 *         having only a few minutes to make a decision.
 * 
 *         To somewhat alleviate this problem so-called speculative-pruning
 *         methods are used to cut off less interesting lines of play
 *         prematurely, while allowing interesting lines to be explored more
 *         deeply.
 * 
 *         Here we discuss one such speculative-pruning method called multi-cut,
 *         which makes pruning decisions based not only on the risk of pruning
 *         off relevant lines of play, but also on the likelihood of such an
 *         erroneous pruning decision affecting the move decision at the root of
 *         the search tree. The method has been successfully employed by several
 *         of the world�s strongest commercial chess program for a number of
 *         years.
 * 
 *         Pseudo Code Multi-Cut inside a null window- or zero window search of
 *         a fail-hard PVS framework, applied at expected Cut-nodes:
 * 
 */
abstract public class MultiCutPruning extends AlphaBeta {

	// M is the number of moves to look at when checking for mc-prune.
	private int M;

	// C is the number of cutoffs to cause an mc-prune, C < M.
	private int C;

	// R is the search depth reduction for mc-prune searches.
	private int R;

	public long zwSearch(long beta, int depth, boolean cut) {

		if (depth <= 0)
			return alphaBetaQuiesce(beta - 1, beta, depth);

		if (depth >= R && cut) {
			int c = 0;

			for (int i = 0; i < M && hasNext(depth, i); i++) {

				makeNext(depth, i);
				long score = -zwSearch(1 - beta, depth + 1 - R, !cut);
				takeBack(depth, score, isCutoff());

				if (score >= beta) {
					if (++c == C)
						return beta; // mc-prune
				}
			}
		}

		for(int i=0; hasNext(depth, i); i++) {

			makeNext(depth, i);
			long score = -zwSearch(1 - beta, depth + 1, !cut);
			takeBack(depth, score, isCutoff());

			if (score >= beta)
				return beta;
		}

		return beta - 1;
	}
}
