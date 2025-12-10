/**
 * 
 */
package ca.mss.rd.tools.chess.com_wikispaces_chessprogramming_treesearch;

/**
 * @author smoskov
 * 
 *         http://chessprogramming.wikispaces.com/NegaScout
 * 
 *         NegaScout is an Alpha-Beta enhancement and improvement of Judea
 *         Pearl's Scout-algorithm [1], introduced by Alexander Reinefeld in
 *         1983 [2]. The improvements rely on a Negamax framework and some
 *         fail-soft issues concerning the two last plies, which did not require
 *         any re-searches.
 * 
 *         NegaScout vs. PVS
 * 
 *         NegaScout works similar to Tony Marsland's and Murray Campbell's PVS
 *         [3]. NegaScout's fail-soft refinements always returns correct minimax
 *         scores at the two lowest levels, since it assumes that all horizon
 *         nodes would have the same score for the (in that case redundant)
 *         re-search, which most programs can not guarantee due to possible
 *         extensions [4] and possible bound dependency of quiescence search and
 *         evaluation.
 * 
 *         NegaScout just searches the first move with an open window, and then
 *         every move after that with a zero window, whether alpha was already
 *         improved or not. Some PVS implementations wait until an
 *         alpha-improvement before using zero window at PV-Nodes [5].
 * 
 *         Reinefeld's original implementation introduces one additional
 *         variable on the stack (only b, since after a = alpha, alpha is not
 *         needed any longer), for a slightly simpler control structure than
 *         PVS. It has therefor set a new null window at the end of the loop (b
 *         = a + 1), but has to consider the move count for the re-search
 *         condition though. His implementation trusts the null-window score,
 *         even if the re-search doesn't confirm the alpha increase, eventually
 *         due to search instability. While re-searching, it uses the narrow
 *         window of {score, beta}, while other implementations dealing with
 *         search instability, re-search with {alpha, beta}. Practically, due to
 *         Quiescence Search, and fail-soft implementations of PVS, the two
 *         algorithms are essentially equivalent to each other - they expand the
 *         same search tree [6][7].
 * 
 *         Yngvi Bj�rnsson Quote by Yngvi Bj�rnsson from CCC, January 05, 2000
 *         [8]:
 * 
 *         Search-wise PVS and Negascout are identical (except the deep-cutoffs
 *         on the PV you mention), they are just formulated differently. In
 *         Negascout the same routine is used for searching both the PV and the
 *         rest of the tree, whereas PVS is typically formulated as two
 *         routines: PVS (for searching the PV) and NWS (for the null-window
 *         searches). Negascout and PVS were developed about the same time in
 *         the early '80 (82-83), but independently. I guess, that's part of the
 *         reason we know them by different names. Personally, I've always found
 *         the PVS/NWS formulation the most intuative, it's easier to understand
 *         what's really going on.
 * 
 *         -Yngvi
 * 
 *         Dennis Breuker Quote by Dennis Breuker from CCC, July 28, 2004 [9]:
 * 
 *         Q: What's the different between negascout and PVS ? They look like
 *         the same algorithm to me.
 * 
 *         They are identical, see note 15 on page 22 of my thesis [10]:
 * 
 *         We note that the version of principal-variation search as mentioned
 *         by Marsland (1986) [11] is identical to the version of negascout as
 *         mentioned by Reinefeld (1989) [12]. We use the 1989 reference instead
 *         of 1983 [13], which was the first source of this algorithm, since the
 *         algorithm described in Reinefeld (1983) contains minor errors.
 * 
 *         Dennis
 * 
 */
abstract public class NegaScout extends AlphaBeta {

	/*
	 * Original by Alexander Reinefeld [15]
	 */
	public long negaScoutOriginal(long alpha, long beta, int depth) {

		/* compute minimax value of position p */
		if (  isMaxDepth(depth))
			return evaluate(depth); /* leaf node */

		long a = alpha;
		long b = beta;

		for(int i=0; hasNext(depth, i); i++) {

			makeNext(depth, i);
			long t = -negaScoutOriginal(-b, -a, depth + 1);
			takeBack(depth, t, isCutoff());

			if ((t > a) && (t < beta) && (i > 0) && (depth < getMaxDepth() - 1))
				a = -negaScoutOriginal(-beta, -t, depth + 1); /* re-search */

			a = max(a, t);

			if (a >= beta)
				return a; /* cut-off */

			b = a + 1; /* set new null window */
		}
		return a;
	}

	/*
	 * Alternative Following implementation addresses the mentioned issues,
	 * wider window for re-searches:
	 */
	public long negaScoutAlternative(long alpha, long beta, int depth) {

		if (  isMaxDepth(depth))
			return alphaBetaQuiesce(alpha, beta, depth); /* leaf node */

		long a = alpha;
		long b = beta;

		for(int i=0; hasNext(depth, i); i++) {

			makeNext(depth, i);
			long t = -negaScoutAlternative(-b, -alpha, depth + 1);
			takeBack(depth, t, isCutoff());

			if ((t > a) && (t < beta) && (i > 0))
				t = -negaScoutAlternative(-beta, -alpha, depth + 1); /* re-search */

			alpha = max(alpha, t);

			if (alpha >= beta)
				return alpha; /* cut-off */

			b = alpha + 1; /* set new null window */
		}
		return alpha;
	}

	private long max(long a, long b) {
		return (a > b ? a : b);
	}

}
