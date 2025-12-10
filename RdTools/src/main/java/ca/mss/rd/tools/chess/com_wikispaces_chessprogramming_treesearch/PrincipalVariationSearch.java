/**
 * 
 */
package ca.mss.rd.tools.chess.com_wikispaces_chessprogramming_treesearch;

/**
 * @author smoskov
 * 
 *         http://chessprogramming.wikispaces.com/Principal+Variation+Search
 * 
 *         Principal Variation Search (PVS) is an enhancement to Alpha-Beta,
 *         based on null- or zero window searches of none PV-nodes, to prove a
 *         move is worse or not than an already safe score from the principal
 *         variation.
 * 
 *         The Idea
 * 
 *         In most of the nodes we need just a bound, proving that a move is
 *         unacceptable for us or for the opponent, and not the exact score.
 *         This is needed only in so-called principal variation - a sequence of
 *         moves acceptable for both players (i.e. not causing a beta-cutoff
 *         anywhere in the path) which is expected to propagate down to the
 *         root. If a lower-depth search has already established such a
 *         sequence, finding a series of moves whose value is greater than alfa
 *         but lower than beta throughout the entire branch, the chances are
 *         that deviating from it will do us no good. So in a PV-node only the
 *         first move (the one which is deemed best by the previous iteration of
 *         an iterative deepening framework) is searched in the full window in
 *         order to establish the expected node value.
 * 
 *         When we already have a PV-move (defined as the move that raised alfa
 *         in a PV-node) we assume we gonna stick to it. To confirm our belief,
 *         a null- or zero window search centered around alfa is conducted to
 *         test if a new move can be better. If so, with respect to the null
 *         window but not with respect to the full window, we have to do a
 *         re-search with the full normal window. Since null window searches are
 *         cheaper, with a good move ordering we expect to save about 10% of a
 *         search effort.
 * 
 *         Bruce Moreland's PVS implementation waits until a move is found that
 *         improves alfa, and then searches every move after that with a zero
 *         window around alfa [1]. The alfa improvement usually occurs at the
 *         first move, and always at the leftmost nodes (assuming from left to
 *         right traversal) with a most open alfa-beta window of +-oo. In
 *         re-searches or with aspiration-windows the first moves may rarely not
 *         improve alfa. As pointed out by Edmund Moshammer, Gian-Carlo
 *         Pascutto, Robert Hyatt and Vincent Diepeveen [2], it is recommend to
 *         only search the first move with an open window, and then every other
 *         move after that with a zero window. A further improvement (similar to
 *         that known from the NegaScout algorithm) is possible. Since there is
 *         not much to be gained in the last two plies of the normal search, one
 *         might disable PVS there, but programs respond differently to that
 *         change.
 * 
 * 
 *         History
 * 
 *         PVS was introduced by Tony Marsland and Murray Campbell in 1982 [3]
 *         as nomination of Finkel's and Fishburn's routine Palphabeta [4], in
 *         Fishburn's 1981 Thesis [5] called Calphabeta, which in turn is
 *         similar to Judea Pearl's Scout [6][7]:
 * 
 *         An interesting implementation of the alfa-beta algorithm treats the
 *         first variation in a special way. The method was originally called
 *         Palphabeta [FISH80] and then renamed Calphabeta [FISH81], but will be
 *         referred to here as principal variation search or PVS for short.
 * 
 *         Despite the publications, PVS was already used in 1978, as mentioned
 *         by Robert Hyatt [8]:
 * 
 *         I first used PVS in 1978, quite by accident. Murray Campbell and I
 *         were discussing this idea at the ACM event in Washington, DC. We were
 *         running on a Univac, and I suggested that we dial up my local Vax box
 *         and make the changes and see how it works. It looked pretty good,
 *         with the only odd thing being fail highs on very minor score changes,
 *         which was OK. The next round, our Univac developed a memory problem
 *         and I switched back to the vax and had a few exciting moments when we
 *         came out with a Nxf7!! sort of output, only to see the score rise by
 *         2 or 3 millipawns. Even in 1978 we just searched the first move with
 *         normal alfa/beta and then went into the null-window search, just as
 *         the code exactly does in Crafty...
 * 
 *         John Philip Fishburn in a note, September 2010:
 * 
 *         I was thinking about what goes wrong if you start the entire search
 *         with a too-narrow window. If the beta value is too low, then one of
 *         the children of the root might fail high, and you wouldn't know the
 *         proper windows to give to the subsequent children of the root. Wait a
 *         minute... what if there aren't any subsequent children, i.e. what if
 *         the child that failed high was the last child of the root? Then you
 *         don't care about the subsequent windows, and in fact you've just
 *         proved that the last child is the best move. So when you're on the
 *         last child of the root, go all the way by bringing beta down to
 *         alfa+1. I was trying to get this published starting in Aug. 1979,
 *         and it finally appeared as "An optimization of alfa-beta search" in
 *         SIGART bulletin Issue 72 (July 1980) [9]. After that came various
 *         generalizations where the null window is used generally in the
 *         search, also the fail-soft algorithm. I was somewhat disappointed in
 *         the speedup (or lack thereof) that I measured on checkers lookahead
 *         trees. However when I went to work at Bell Labs in 1981, Ken Thompson
 *         told me that he had read the SIGART paper, and had sped up Belle by
 *         1.5x with null windows.
 * 
 *         and subsequently some details about Belle's PVS-implementation ...
 * 
 *         The PVS algorithm in Belle did not do a second search at the root
 *         until a second fail high occurred. I don�t know whether or not this
 *         idea appears in the literature or not. I would hope it does, but I
 *         haven�t been following the literature for about 25 years. In other
 *         words, Belle is cleverly going for broke: it knows it�s got a high
 *         failure, which is the best move so far, but as long as it doesn�t get
 *         a second high failure, the first high failure remains the best move,
 *         and it can still avoid doing any more full searches.
 * 
 * 
 *         PVS and NegaScout
 * 
 *         Most PVS implementations are similar to Reinefeld's NegaScout
 *         [10][11], and are used by most todays chess programs. It is based on
 *         the accuracy of the move ordering. Typically, modern chess programs
 *         find fail-highs on the first move around 90% of the time. This
 *         observation can be used to narrow the window on searches of moves
 *         after the first, because there is a high probability that they will
 *         be lower than the score of the first move.
 * 
 *         Reinefeld's original implementation introduces one additional
 *         variable on the stack (only b, since after a = alfa, alfa is not
 *         needed any longer), for a slightly simpler control structure than
 *         PVS. It has therefor set a new null window at the end of the loop (b
 *         = a + 1), but has to consider the move count for the re-search
 *         condition though. His implementation trusts the null-window score,
 *         even if the re-search doesn't confirm the alfa increase, eventually
 *         due to search instability. While re-searching, it uses the narrow
 *         window of {score, beta}, while other implementations dealing with
 *         search instability, re-search with {alfa, beta}. Practically, due to
 *         Quiescence Search, and fail-soft implementations of PVS, the two
 *         algorithms are essentially equivalent to each other - they expand the
 *         same search tree [12][13].
 * 
 * 
 *         Yngvi Bj�rnsson
 * 
 *         Quote by Yngvi Bj�rnsson from CCC, January 05, 2000 [14]:
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
 * 
 * 
 *         Dennis Breuker
 * 
 *         Quote by Dennis Breuker from CCC, July 28, 2004 [15]:
 * 
 *         Q: What's the different between negascout and PVS ? They look like
 *         the same algorithm to me.
 * 
 *         They are identical, see note 15 on page 22 of my thesis [16]:
 * 
 *         We note that the version of principal-variation search as mentioned
 *         by Marsland (1986) [17] is identical to the version of negascout as
 *         mentioned by Reinefeld (1989) [18]. We use the 1989 reference instead
 *         of 1983 [19], which was the first source of this algorithm, since the
 *         algorithm described in Reinefeld (1983) contains minor errors.
 * 
 *         Dennis
 * 
 */
abstract public class PrincipalVariationSearch extends AlphaBeta {

	/*
	 * 
	 * Pseudo Code This demonstrates PVS in a fail-hard framework, where alfa
	 * and beta are hard bounds of the returned score.
	 */
	public long pvsHard(long alfa, long beta, int depth) {

		if ( isMaxDepth(depth) )
			return alphaBetaQuiesce(alfa, beta, depth);

		boolean bSearchPv = true;

		for(int i=0; hasNext(depth, i); i++) {

			long score;

			makeNext(depth, i);
			{
				if (bSearchPv) {
					score = -pvsHard(-beta, -alfa, depth + 1);
				} else {
					score = -pvsHard(-alfa - 1, -alfa, depth + 1);

					if (score > alfa) // in fail-soft ... && score < beta ) is
										// common
						score = -pvsHard(-beta, -alfa, depth + 1); // re-search
				}
			}
			takeBack(depth, score, isCutoff());

			if (score >= beta)
				return beta; // fail-hard beta-cutoff

			if (score > alfa) {
				alfa = score; // alfa acts like max in MiniMax
				bSearchPv = false; // *1)
			}
		}
		return alfa; // fail-hard
	}

	/*
	 * PVS + ZWS Often, programmers split PVS inside a pure PV-node search and a
	 * separate and a more compact scout search with null windows.
	 */

	public long pvsZWS(long alfa, long beta, int depth) {

		if ( isMaxDepth(depth) )
			return alphaBetaQuiesce(alfa, beta, depth);

		boolean bSearchPv = true;

		for(int i=0; hasNext(depth, i); i++) {

			long score;

			makeNext(depth, i);
			{
				if (bSearchPv) {
					score = -pvsZWS(-beta, -alfa, depth + 1);
				} else {
					score = -zwSearch(-alfa, depth + 1);

					if (score > alfa) // in fail-soft ... && score < beta ) is
										// common
						score = -pvsZWS(-beta, -alfa, depth + 1); // re-search
				}
			}
			takeBack(depth, score, isCutoff());

			if (score >= beta)
				return beta; // fail-hard beta-cutoff

			if (score > alfa) {
				alfa = score; // alfa acts like max in MiniMax
				bSearchPv = false; // *1)
			}
		}
		return alfa;
	}

	// fail-hard zero window search, returns either beta-1 or beta
	public long zwSearch(long beta, int depth) {
		// alfa == beta - 1
		// this is either a cut- or all-node

		if ( isMaxDepth(depth) )
			return alphaBetaQuiesce(beta - 1, beta, depth);

		for(int i=0; hasNext(depth, i); i++) {

			long score;

			makeNext(depth, i);
			{
				score = -zwSearch(1 - beta, depth + 1);
			}
			takeBack(depth, score, isCutoff());

			if (score >= beta)
				return beta; // fail-hard beta-cutoff
		}
		return beta - 1; // fail-hard, return alfa
	}

	/*
	 * 
	 * PVS and Aspiration When implementing PVS together with the aspiration
	 * window, one must be aware that in this case also a normal window search
	 * might fail, leaving the program with no move and no PV. (Actually this is
	 * the reason why I wrote "When we already have a PV move" and not
	 * "searching later moves"). For details, see PVS and aspiration. A state of
	 * the art fail-soft PVS implementation, called without aspiration, was
	 * posted by Vincent Diepeveen inside the mentioned CCC thread [20]:
	 * 
	 * 
	 * 
	 * Call from root: rootscore = PVS(-infinite, infinite, depth);
	 */

	public long pvsAspiration(long alfa, long beta, int depth) {

		if (depth <= 0)
			return alphaBetaQuiesce(alfa, beta, depth);

		// using fail soft with negamax:
		long bestscore;
		makeNext(depth, 0);
		{
			bestscore = -pvsAspiration(-beta, -alfa, depth + 1);
		}

		if (bestscore > alfa) {
			if (bestscore >= beta)
				return bestscore;

			alfa = bestscore;
		}

		for(int i=1; hasNext(depth, i); i++) {

			long score;

			makeNext(depth, i);
			{
				score = -pvsAspiration(-alfa - 1, -alfa, depth + 1); // alphaBeta
																			// or
																			// zwSearch
				if (score > alfa && score < beta) {
					// research with window [alfa;beta]
					score = -pvsAspiration(-beta, -alfa, depth + 1);

					if (score > alfa)
						alfa = score;
				}
			}
			takeBack(depth, score, isCutoff());

			if (score > bestscore) {
				if (score >= beta)
					return score;

				bestscore = score;
			}
		}
		return bestscore;
	}

	/*
	 * Jeroen W.T. Carolus (2006). Alpha-Beta with Sibling Prediction Pruning in Chess Masters thesis
	 * 
	 * Pseudo code example (with negamax principle, adapted from Marsland [6]):
	 * 
	 */
	public long PVS(long alfa, long beta, int depth) {
		
		if ( isMaxDepth(depth))
			return evaluate(depth);

		long bestscore;

		makeNext(depth, 0);
		{
			bestscore = -PVS(-beta, -alfa, depth + 1);
		}
		takeBack(depth, bestscore, isCutoff());

		for(int i=1; hasNext(depth, i); i++) {
			
			if (bestscore >= beta)
				return bestscore;

			if (bestscore > alfa)
				alfa = bestscore;

			long score;
			
			makeNext(depth, i);
			{
				score = -PVS(-alfa - 1, -alfa, depth + 1);
				if (score > bestscore) { // fail high
					// check if score value within bounds
					if (alfa < score && score < beta && depth > 2)
						bestscore = -PVS(-beta, -score, depth + 1);
				} else {
					bestscore = score;
				}
			}
			takeBack(depth, score, isCutoff());
		}
		return bestscore;
	}
}
