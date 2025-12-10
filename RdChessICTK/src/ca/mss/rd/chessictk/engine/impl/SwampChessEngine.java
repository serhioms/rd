package ca.mss.rd.chessictk.engine.impl;

import ictk.boardgame.chess.ChessMove;
import ca.mss.rd.chessictk.engine.ChessEngine;
import ca.mss.rd.chessictk.game.ChessGame;
import ca.mss.rd.tools.chess.com_wikispaces_chessprogramming_treesearch.AlphaBetaMinimax;
import ca.mss.rd.tools.chess.com_wikispaces_chessprogramming_treesearch.Minimax;



public class SwampChessEngine extends AlphaBetaMinimax implements ChessEngine {

	public static final String module = SwampChessEngine.class.getName();
	static final long serialVersionUID = module.hashCode();
	private static org.apache.log4j.Logger logger = ca.mss.rd.util.Debug.getLogger(module);

	private ChessGame game;
	private PositionEvaluator evaluator;
	private TreeSearch treeSearch;
	
	// Here is maximum search depth should be specified
	private int maxDepth = 6;


	/*
	 * 
	 */
	public SwampChessEngine(ChessGame game) {
		this.game = game;
		this.treeSearch = new TreeSearch(game);
		this.evaluator = new PositionEvaluator(game);
	}

	/* (non-Javadoc)
	 * @see com.wikispaces.chessprogramming.treesearch.iface.NodeEvaluator#getMaxDepth()
	 */
	@Override
	public int getMaxDepth() {
		return maxDepth;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ca.mss.chess.engine.ChessEngine#generateMove()
	 */
	@Override
	final public ChessMove generateMove() {
		// Which turn to move
		evaluator.populateSide();
		// Do actual search
		treeSearch.start(this, maximize());
		// Get best one
		return treeSearch.getBestMove(maximize(), isCutoff());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.wikispaces.chessprogramming.treesearch.iface.NodeEvaluator#evaluate
	 * (int)
	 */
	@Override
	final public long evaluate(int depth) {
		return evaluator.evaluate(depth, maxDepth);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.wikispaces.chessprogramming.treesearch.iface.NodeNavigator#hasNext
	 * (int, int)
	 */
	@Override
	final public boolean hasNext(int depth, int i) {
		return treeSearch.hasNext(depth, i, maximize());
	}

	/* (non-Javadoc)
	 * @see com.wikispaces.chessprogramming.treesearch.iface.NodeNavigator#betaCutOff(int, int, long)
	 */
	@Override
	public long betaCutoff(int depth, int i, long beta) {
		treeSearch.betaCutoff(depth, i, beta);
		return beta+1; // to make diff between cutoff score and real score
	}

	/* (non-Javadoc)
	 * @see com.wikispaces.chessprogramming.treesearch.iface.NodeNavigator#alphaCutOff(int, int, long)
	 */
	@Override
	public long alphaCutoff(int depth, int i, long alpha) {
		treeSearch.alphaCutoff(depth, i, alpha);
		return alpha-1; // to make diff between cutoff score and real score
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.wikispaces.chessprogramming.treesearch.iface.NodeNavigator#makeNext
	 * (int, int)
	 */
	@Override
	final public void makeNext(int depth, int i) {
		treeSearch.makeNext(depth, i);
	}

	@Override
	final public void takeBack(int depth, long score, boolean isCutoff) {
		treeSearch.takeBack(depth, score, isCutoff);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.wikispaces.chessprogramming.treesearch.iface.NodeNavigator#hasQuiescence
	 * (int, int)
	 */
	@Override
	final public boolean hasQuiescence(int depth, int i) {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.wikispaces.chessprogramming.treesearch.iface.NodeNavigator#makeQuiescence
	 * (int, int)
	 */
	@Override
	final public void makeQuiescence(int depth, int i) {
		// TODO Auto-generated method stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.wikispaces.chessprogramming.treesearch.iface.NodeNavigator#isMaxDepth
	 * (int)
	 */
	@Override
	final public boolean isMaxDepth(int depth) {
		return (depth == maxDepth) || treeSearch.isEndOfSearch() || game.isEndOfGame();
	}
	
	
}
