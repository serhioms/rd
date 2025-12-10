/**
 * 
 */
package ca.mss.rd.tools.chess.com_wikispaces_chessprogramming_treesearch;


/**
 * @author smoskov
 * 
 *         http://chessprogramming.wikispaces.com/MTD%28f%29
 * 
 * 
 *         MDT(f), 
 *         
 *         a search algorithm created by Aske Plaat and the short name
 *         for MTD(n, f), which stands for something like Memory-enhanced Test
 *         Driver with node n and value f. MTD is the name of a group of
 *         driver-algorithms that search minimax trees using null window
 *         alpha-beta with transposition table calls.
 * 
 *         In order to work, MTD(f) needs a first guess as to where the minimax
 *         value will turn out to be. The better than first guess is, the more
 *         efficient the algorithm will be, on average, since the better it is,
 *         the less passes the repeat-until loop will have to do to converge on
 *         the minimax value. If you feed MTD(f) the minimax value to start
 *         with, it will only do two passes, the bare minimum: one to find an
 *         upper bound of value x, and one to find a lower bound of the same
 *         value [1].
 *         
 *         
 */
abstract public class MemoryEnhancedTestDriver extends AlphaBeta {

	long mtdf(long f, int depth) {
		
		long bound[] = new long[] { -MAXIMUM, +MAXIMUM }; // lower, upper
		
		do {
			long beta = f + (f == bound[0] ? 1 : 0);
			
			f = alphaBetaMax(beta - 1, beta, depth);
		
			bound[(f < beta ? 1 : 0)] = f;
		
		} while (bound[0] < bound[1]);
		
		return f;
	}

}
