/**
 * 
 */
package ca.mss.rd.chessictk.engine.impl;


import ictk.boardgame.chess.ChessMove;

import java.util.List;
import java.util.Stack;

import ca.mss.rd.chessictk.game.ChessGame;
import ca.mss.rd.tools.chess.com_wikispaces_chessprogramming_treesearch.iface.NodeNavigator;

import ca.mss.rd.util.Debug;
import ca.mss.rd.util.UtilString;


/**
 * @author smoskov
 *
 */
public class TreeSearch {

	public static final String module = TreeSearch.class.getName();
	static final long serialVersionUID = module.hashCode();
	private static org.apache.log4j.Logger logger = ca.mss.rd.util.Debug.getLogger(module);
	
	private ChessGame game;

	private ScoreCache scoreCache;
	private Stack<List<ChessMove>> legalStack;

	private List<ChessMove> legalMoves;

	public TreeSearch(ChessGame game) {
		this.game = game;
		this.scoreCache = new ScoreCache(game);
		this.legalStack = new Stack<List<ChessMove>>();
	}

	
	final public void start(NodeNavigator node, boolean maximize) {
		// Initialise legal moves
		legalMoves = game.getLegalMoves();
		
		// Search best moves
		node.startSearch(maximize);
		
		// Clean 
		scoreCache.clean3();
	}


	final public ChessMove getBestMove(boolean maximize, boolean isCutoff) {
		// Sort moves by evaluated scores
		List<ChessMove> contList = game.getContinuationList();
		
		// or just find one top scored move in case of cutoff style of search
		if( isCutoff )
			game.promoteMove(contList, maximize);
		else
			game.sortMoves(contList, maximize);
		
		// Best move is the first in sorted continuation list
		ChessMove bestMove = (ChessMove) contList.get(0);
	
		if (logger.isInfoEnabled())
			logger.info("[" + game.sideColor()
					+ "][contList=" + game.toString(contList) + "]");
		
		return bestMove;
	}

	final public boolean hasNext(int depth, int i, boolean maximize) {
		int size = legalMoves.size();

		if( i == 0 ){
			if( scoreCache.sort(legalMoves, !maximize) ){
				if( Debug.isVerboseEnabled) logger.debug("Sort legals    "+UtilString.space(depth*2)+"[depth="+depth+"][size="+size+"]");
			}
		} else if( i == size )
			game.cleanMoveList(legalMoves);

		return i < size;
	}

	final public void betaCutoff(int depth, int i, long beta) {
		if( Debug.isVerboseEnabled) logger.debug("Beta cutoff    "+UtilString.space(depth*2)+"[depth="+depth+"]["+i+"][beta="+beta+"]");
		
		game.cleanMoveList(legalMoves);
	}
	
	final public void alphaCutoff(int depth, int i, long alpha) {
		if( Debug.isVerboseEnabled) logger.debug("Alpha cutoff   "+UtilString.space(depth*2)+"[depth="+depth+"]["+i+"][alpha="+alpha+"]");
		
		game.cleanMoveList(legalMoves);
	}
	
	final public void makeNext(int depth, int i) {
		// do next move
		ChessMove move = legalMoves.get(i);
		game.addMove(move);

		// save current legal moves
		legalStack.push(legalMoves);

		// then initialize new one
		legalMoves = game.getLegalMoves();
		
		if( Debug.isVerboseEnabled) logger.debug("RdChessMove      "+UtilString.space(depth*2)+"[depth="+depth+"]["+i+"]["+game.color(move)+"][move="+move+"]");
	}

	final public void takeBack(int depth, long score, boolean isCutoff) {
		// Undo last move
		ChessMove move = game.retractMove();

		// Populate score into last move (score is not real in case of cuts off style)
		move.score = score;
		
		// Populate scored move to cache if it is real (not cutted)
		if( !isCutoff )
			scoreCache.put(move);

		// return to previous legal moves
		legalMoves = legalStack.pop();

		if (depth == 0) {
			if (Debug.isVerboseEnabled)
				logger.debug("[depth=" + depth
						+ "]["+game.sideColor()+"][move=" + move + "]"
						+ game.toString(game.getContinuationList()));
		}
	}

	final public boolean isEndOfSearch() {
		return legalMoves.isEmpty();
	}
	
}
