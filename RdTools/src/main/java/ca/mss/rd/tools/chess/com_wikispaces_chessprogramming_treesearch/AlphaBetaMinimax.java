/**
 * 
 */
package ca.mss.rd.tools.chess.com_wikispaces_chessprogramming_treesearch;

import ca.mss.rd.tools.chess.com_wikispaces_chessprogramming_treesearch.iface.NodeEvaluator;
import ca.mss.rd.tools.chess.com_wikispaces_chessprogramming_treesearch.iface.NodeNavigator;

/**
 * @author smoskov
 * 
 *         http://chessprogramming.wikispaces.com/Alpha-Beta
 * 
 *         The Alpha-Beta algorithm (Alpha-Beta Pruning, Alpha-Beta Heuristic)
 *         is a significant enhancement to the minimax search algorithm that
 *         eliminates the need to search large portions of the game tree.
 *         Remarkably, it does this without any potential of overlooking a
 *         better move. If one already has found a quite good move and search
 *         for alternatives, one refutation is enough to avoid it. No need to
 *         look for even stronger refutations. The algorithm maintains two
 *         values, alpha and beta. They represent the minimum score that the
 *         maximizing player is assured of and the maximum score that the
 *         minimizing player is assured of respectively. Consider the following
 *         example...
 * 
 * 
 *         How it works
 * 
 *         Say it is white's turn to move, and we are searching to a depth of 2
 *         (that is, we are consider all of white's moves, and all of black's
 *         responses to each of those moves.) First we pick one of white's
 *         possible moves - let's call this Possible Move #1. We consider this
 *         move and every possible response to this move by black. After this
 *         analysis, we determine that the result of making Possible Move #1 is
 *         an even position. Then, we move on and consider another of white's
 *         possible moves (Possible Move #2.) When we consider the first
 *         possible counter-move by black, we discover that playing this results
 *         in black winning a Rook! In this situation, we can safely ignore all
 *         of black's other possible responses to Possible Move #2 because we
 *         already know that Possible Move #1 is better. We really don't care
 *         exactly how much worse Possible Move #2 is. Maybe another possible
 *         response wins a Queen, but it doesn't matter because we know that we
 *         can achieve at least an even game by playing Possible Move #1. The
 *         full analysis of Possible Move #1 gave us a lower bound. We know that
 *         we can achieve at least that, so anything that is clearly worse can
 *         be ignored.
 * 
 *         The situation becomes even more complicated, however, when we go to a
 *         search depth of 3 or greater, because now both players can make
 *         choices affecting the game tree. Now we have to maintain both a lower
 *         bound and an upper bound (called Alpha and Beta.) We maintain a lower
 *         bound because if a move is too bad we don't consider it. But we also
 *         have to maintain an upper bound because if a move at depth 3 or
 *         higher leads to a continuation that is too good, the other player
 *         won't allow it, because there was a better move higher up on the game
 *         tree that he could have played to avoid this situation. One player's
 *         lower bound is the other player's upper bound.
 * 
 *         Savings
 * 
 *         The savings of alpha beta can be considerable. If a standard minimax
 *         search tree has x nodes, an alpha beta tree in a well-written program
 *         can have a node count close to the square-root of x. How many nodes
 *         you can actually cut, however, depends on how well ordered your game
 *         tree is. If you always search the best possible move first, you
 *         eliminate the most of the nodes. Of course, we don't always know what
 *         the best move is, or we wouldn't have to search in the first place.
 *         Conversely, if we always searched worse moves before the better
 *         moves, we wouldn't be able to cut any part of the tree at all! For
 *         this reason, good move ordering is very important, and is the focus
 *         of a lot of the effort of writing a good chess program. As pointed
 *         out by Levin in 1961, assuming constantly b moves for each node
 *         visited and search depth n, the maximal number of leaves in
 *         alpha-beta is equivalent to minimax, b ^ n. Considering always the
 *         best move first, it is b ^ ceil(n/2) plus b ^ floor(n/2) minus one.
 *         The minimal number of leaves is shown in following table which also
 *         demonstrates the odd-even effect:
 * 
 * 
 * 
 *         History Alpha-Beta was invented independently by several researchers
 *         and pioneers from the 50s, and further research until the 80s, most
 *         notable by
 * 
 *         John McCarthy proposed the idea of Alpha-Beta in 1956 at the
 *         Dartmouth Conference [2] Allen Newell and Herbert Simon Approximation
 *         in 1958 Arthur Samuel Approximation in 1959 Daniel Edwards and
 *         Timothy Hart, Description in 1961 [3] Alexander Brudno, Description
 *         in 1963 Samuel Fuller, John Gaschnig, James Gillogly, Analysis 1973
 *         [4] Donald Knuth, Analysis in 1975 [5] Knuth and Moore's famous
 *         Function F2, aka AlphaBeta Knuth already introduced an iterative
 *         solution, see Iterative Search G�rard M. Baudet, Analysis in 1978
 * 
 * 
 *         Quotes McCarthy Quote by John McCarthy Human-Level AI is harder than
 *         it seemed in 1955:
 * 
 *         Chess programs catch some of the human chess playing abilities but
 *         rely on the limited effective branching of the chess move tree. The
 *         ideas that work for chess are inadequate for go. Alpha-beta pruning
 *         characterizes human play, but it wasn't noticed by early chess
 *         programmers - Turing, Shannon, Pasta and Ulam, and Bernstein. We
 *         humans are not very good at identifying the heuristics we ourselves
 *         use. Approximations to alpha-beta used by Samuel, Newell and Simon,
 *         McCarthy. Proved equivalent to minimax by Hart and Levin,
 *         independently by Brudno. Knuth gives details.
 * 
 *         Knuth Quote by Knuth [6]:
 * 
 *         It is interesting to convert this recursive procedure to an iterative
 *         (non-recursive) form by a sequence of mechanical transformations, and
 *         to apply simple optimizations which preserve program correctness. The
 *         resulting procedure is surprisingly simple, but not as easy to prove
 *         correct as the recursive form.
 * 
 * 
 * 
 */
abstract public class AlphaBetaMinimax extends NodeAdapter implements NodeNavigator, NodeEvaluator {

	/* (non-Javadoc)
	 * @see com.wikispaces.chessprogramming.treesearch.iface.NodeEvaluator#isNegaluated()
	 */
	@Override
	public boolean isNegaluated() {
		return false; // by default acts as negaluate 
	}
	
	/* (non-Javadoc)
	 * @see com.wikispaces.chessprogramming.treesearch.iface.NodeNavigator#isCutoff()
	 */
	@Override
	public boolean isCutoff() {
		return true;
	}

	/* (non-Javadoc)
	 * @see com.wikispaces.chessprogramming.treesearch.iface.NodeNavigator#search(int)
	 */
	@Override
	public void startSearch(boolean maximize) {
		if( maximize )
			this.alphaBetaMax(-MAXIMUM, +MAXIMUM, 0);
		else
			this.alphaBetaMin(-MAXIMUM, +MAXIMUM, 0);
	}

	/*
	 * Max versus Min
	 * 
	 * A C-like pseudo code implementation of the alpha-beta algorithm with
	 * distinct indirect recursive routines for the max- and min-player, similar
	 * to the minimax routines. Making and unmaking moves is omitted, and should
	 * be done before and after the recursive calls. So called beta-cutoffs
	 * occur for the max-play, alpha-cutoffs for the min-player.
	 */
	public long alphaBetaMax(long alpha, long beta, int depth) {

		if (isMaxDepth(depth) )
			return negaluate(depth);

		for(int i=0; hasNext(depth, i); i++) {

			makeNext(depth, i);
			long score = alphaBetaMin(alpha, beta, depth + 1);
			takeBack(depth, score, isCutoff());

			if (score >= beta)
				return betaCutoff(depth, i, beta); // beta-cutoff

			if (score > alpha)
				alpha = score; // alpha acts like max in MiniMax
		}
		return alpha;
	}

	public long alphaBetaMin(long alpha, long beta, int depth) {

		if (isMaxDepth(depth) )
			return negaluate(depth);

		for(int i=0; hasNext(depth, i); i++) {

			makeNext(depth, i);
			long score = alphaBetaMax(alpha, beta, depth + 1);
			takeBack(depth, score, isCutoff());

			if (score <= alpha)
				return alphaCutoff(depth, i, alpha); // alpha-cutoff

			if (score < beta)
				beta = score; // beta acts like min in MiniMax
		}
		return beta;
	}

}
