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
abstract public class AlphaBeta extends NodeAdapter implements NodeNavigator, NodeEvaluator {

	/* (non-Javadoc)
	 * @see com.wikispaces.chessprogramming.treesearch.iface.NodeNavigator#search()
	 */
	@Override
	public void startSearch(boolean maximize) {
		this.alphaBetaMax(-MAXIMUM, MAXIMUM, 0);
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
			return evaluate(depth);

		for(int i=0; hasNext(depth, i); i++) {

			makeNext(depth, i);
			long score = alphaBetaMin(alpha, beta, depth + 1);
			takeBack(depth, score, isCutoff());

			if (score >= beta)
				return beta; // beta-cutoff

			if (score > alpha)
				alpha = score; // alpha acts like max in MiniMax
		}
		return alpha;
	}

	public long alphaBetaMin(long alpha, long beta, int depth) {

		if (isMaxDepth(depth) )
			return -evaluate(depth);

		for(int i=0; hasNext(depth, i); i++) {

			makeNext(depth, i);
			long score = alphaBetaMax(alpha, beta, depth + 1);
			takeBack(depth, score, isCutoff());

			if (score <= alpha)
				return alpha; // alpha-cutoff

			if (score < beta)
				beta = score; // beta acts like min in MiniMax
		}
		return beta;
	}

	/*
	 * Negamax Framework
	 * 
	 * Inside a negamax framework the routine looks simpler, but is not
	 * necessarily simpler to understand. Despite negating the returned score of
	 * the direct recursion, alpha of the min-player becomes minus beta of the
	 * max-player and vice versa, and the term alpha-cutoff or alpha-pruning is
	 * somehow diminished.
	 * 
	 * 
	 * Note #1: Notice the call to quiesce(). This performs a quiescence search,
	 * which makes the alpha-beta search much more stable.
	 * 
	 * Note #2: This function only returns the score for the position, not the
	 * best move. Normally, a different (but very similar) function is used for
	 * searching the root node. The SearchRoot function calls the alphaBeta
	 * function and returns both a score and a best move. Also, most search
	 * functions collect the principal variation not only for display purposes,
	 * but for a good guess as the leftmost path of the next iteration inside an
	 * iterative deepening framework.
	 */
	
	public long alphaBetaNegamax(long alpha, long beta, int depth) {

		if ( isMaxDepth(depth) )
			return alphaBetaQuiesce(alpha, beta, depth);

		for(int i=0; hasNext(depth, i); i++) {

			makeNext(depth, i);
			long score = -alphaBetaNegamax(-beta, -alpha, depth + 1);
			takeBack(depth, score, isCutoff());

			if (score >= beta)
				return beta; // beta-cutoff

			if (score > alpha)
				alpha = score; // alpha acts like max in MiniMax
		}
		return alpha;
	}

	/*
	 * http://chessprogramming.wikispaces.com/Fail-Soft
	 * 
	 * Fail-Soft is a term related to an Alpha-Beta like search. Returned scores
	 * might be outside the bounds:
	 * 
	 * an upper bound less than alpha at All-Nodes a lower bound greater than
	 * beta at Cut-Nodes
	 * 
	 * 
	 * In his 1983 paper Another optimization of alpha-beta search [1], John
	 * Philip Fishburn introduced Fail-Soft Alpha-Beta as an improvement of
	 * Fail-Hard without any extra work [2]. Fail-Soft has the reputation for
	 * searching less nodes than Fail-Hard, but might also require some care
	 * regarding to search instability issues in conjunction with transposition
	 * tables and various pruning-, reduction- and extension techniques.
	 * 
	 * In Chrilly Donninger's initial null move pruning implementation there was
	 * a deep search extension [3], if the null move was refuted by a mate
	 * attack, thus relying on Fail-Soft of a null window search, where many
	 * "random" moves may refute the null-move with or without score in the mate
	 * range.
	 * 
	 * 
	 * 
	 * Outside the Bounds Fail-Soft Alpha-Beta may return scores outside the
	 * bounds, that is either greater than beta or less than alpha. It has to
	 * keep track of the best score, which might be below alpha.
	 */
	public long alphaBetaFailSoft(long alpha, long beta, int depth) {

		long bestscore = -MAXIMUM;

		if ( isMaxDepth(depth) )
			return alphaBetaQuiesce(alpha, beta, depth);

		for(int i=0; hasNext(depth, i); i++) {

			makeNext(depth, i);
			long score = -alphaBetaFailSoft(-beta, -alpha, depth + 1);
			takeBack(depth, score, isCutoff());

			if (score >= beta)
				return score; // fail-soft beta-cutoff

			if (score > bestscore) {
				bestscore = score;
				if (score > alpha)
					alpha = score;
			}
		}
		return bestscore;
	}

	/*
	 * 
	 * http://chessprogramming.wikispaces.com/Fail-Hard
	 * 
	 * Fail-Hard is a term related to an Alpha-Beta like search, to make Alpha
	 * and Beta hard bounds of the returned value of the search. Even terminal
	 * nodes which indicate draw or mate scores are supposed to be adjusted on
	 * the hard alpha-beta bounds.
	 * 
	 * Fail-Hard Condition: Alpha <= Score <= Beta
	 */
	public long alphaBetaFailHard(long alpha, long beta, int depth) {

		long bestscore = -MAXIMUM;

		if ( isMaxDepth(depth) )
			return alphaBetaQuiesce(alpha, beta, depth);

		for(int i=0; hasNext(depth, i); i++) {

			makeNext(depth, i);
			long score = -alphaBetaFailHard(-beta, -alpha, depth + 1);
			takeBack(depth, score, isCutoff());

			if (alpha <= score && score <= beta)
				return score; // fail-hard beta-cutoff

			if (score > bestscore) {
				bestscore = score;
				if (score > alpha)
					alpha = score;
			}
		}
		return bestscore;
	}

	/**
	 * 
	 * http://chessprogramming.wikispaces.com/Quiescence+Search
	 * 
	 * Virtually all chess programs, at the end of the main search perform a
	 * more limited quiescence search, containing fewer moves. The purpose of
	 * this search is to only evaluate "quiet" positions, or positions where
	 * there are no winning tactical moves to be made. This search is needed to
	 * avoid the horizon effect. Simply stopping your search when you reach the
	 * desired depth and then evaluate, is very dangerous. Consider the
	 * situation where the last move you consider is QxP. If you stop there and
	 * evaluate, you might think that you have won a pawn. But what if you were
	 * to search one move deeper and find that the next move is PxQ? You didn't
	 * win a pawn, you actually lost a queen. Hence the need to make sure that
	 * you are evaluating only quiescent (quiet) positions.
	 * 
	 * 
	 * Limiting Quiescence
	 * 
	 * Despite the fact that quiescence searches are typically very short, about
	 * 50%-90% nodes are spent there, so it is worthwhile to apply some pruning
	 * there. Apart from not trying moves with the static exchange evaluation <
	 * 0, delta pruning can be used for that purpose.
	 * 
	 * 
	 * Standing Pat
	 * 
	 * In order to allow the quiescence search to stabilize, we need to be able
	 * to stop searching without necessarily searching all available captures.
	 * In addition, we need a score to return in case there are no captures
	 * available to be played. This is done by a using the static evaluation as
	 * a "stand-pat" score (the term is taken from the game of poker, where it
	 * denotes playing one's hand without drawing more cards). At the beginning
	 * of quiescence, the position's evaluation is used to establish a lower
	 * bound on the score. This is theoretically sound because we can usually
	 * assume that there is at least one move that can either match or beat the
	 * lower bound. This is based on the Null Move Observation - it assumes that
	 * we are not in Zugzwang. If the lower bound from the stand pat score is
	 * already greater than or equal to beta, we can return the stand pat score
	 * (fail-soft) or beta (fail-hard) as a lower bound. Otherwise, the search
	 * continues, keeping the evaluated "stand-pat" score as an lower bound if
	 * it exceeds alpha, to see if any tactical moves can increase alpha.
	 * 
	 * 
	 * Checks
	 * 
	 * Some programs search treat checks and check evasions specially in
	 * quiescence. The idea behind this is that if the side to move is in check,
	 * the position is not quiet, and there is a threat that needs to be
	 * resolved. In this case, all evasions to the check are searched. Stand pat
	 * is not allowed if we are in check, for two reasons. First, because we are
	 * not sure that there is a move that can match alpha--in many positions a
	 * check can mean a serious threat that cannot be resolved. Second, because
	 * we are searching every move in the position, rather than only captures.
	 * Standing pat assumes that even if we finish searching all moves, and none
	 * of them increase alpha, one of the non-tactical moves can most likely
	 * raise alpha. This is not valid if we search every move.
	 * 
	 * The other case of treating checks specially is the checking moves
	 * themselves. Some programs, after searching all the captures in a position
	 * without finding a move to raise alpha, will generate non-capture moves
	 * that give check. This has to be limited somehow, however, because in most
	 * given positions there will be very many long and pointless checking
	 * sequences that do not amount to anything. Most programs achieve this
	 * limit by delta pruning checks, as well as limiting the generation of
	 * checks to the first X plies of quiescence.
	 * 
	 * 
	 * 
	 */

	public long alphaBetaQuiesce(long alpha, long beta, int depth) {

		long stand_pat = evaluate(depth);

		if (stand_pat >= beta)
			return beta;

		if (alpha < stand_pat)
			alpha = stand_pat;

		for(int i=0; hasQuiescence(depth, i); i++) {

			makeQuiescence(depth, i);
			long score = -alphaBetaQuiesce(-beta, -alpha, depth+1);
			takeBack(depth, score, isCutoff());

			if (score >= beta)
				return beta;

			if (score > alpha)
				alpha = score;
		}

		return alpha;
	}
}
