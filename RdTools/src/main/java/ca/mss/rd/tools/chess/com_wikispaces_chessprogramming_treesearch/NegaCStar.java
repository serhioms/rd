/**
 * 
 */
package ca.mss.rd.tools.chess.com_wikispaces_chessprogramming_treesearch;

/**
 * @author smoskov
 * 
 * http://chessprogramming.wikispaces.com/NegaC*
 * 
 *         C* and NegaC*, 
 *         
 *         an idea to turn a Depth-First to a Best-First search,
 *         like MTD(f) to utilize null window searches of a fail-soft Alpha-Beta
 *         routine, and to use the bounds that are returned in a bisection
 *         scheme. This yields in the C* algorithm, already proposed by Kevin
 *         Coplan in 1981 [1] and NegaC*, a NegaMax implementation of C*,
 *         introduced by Jean-Christophe Weill in 1991 [2] [3].
 *         
 *         
 */
abstract public class NegaCStar extends AlphaBeta {

	public long negaCStar(long min, long max, int depth) {

		long score = min;

		while (min != max) {
			long alpha = (min + max) / 2;

			score = alphaBetaFailSoft(alpha, alpha + 1, depth + 1);

			if (score > alpha)
				min = score;
			else
				max = score;
		}
		return score;
	}
}
