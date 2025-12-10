package ca.mss.rd.chessictk.engine.impl;


import ictk.boardgame.chess.ChessMove;

import java.util.List;
import java.util.Random;

import ca.mss.rd.chessictk.engine.ChessEngine;
import ca.mss.rd.chessictk.game.ChessGame;


public class RandomChessEngine implements ChessEngine {

	final private static Random random = new Random();
	
	private ChessGame game;
	
	public RandomChessEngine(ChessGame game) {
		this.game = game;
	}

	/* (non-Javadoc)
	 * @see ca.mss.chess.engine.ChessEngine#generateMove()
	 */
	final public ChessMove generateMove() {
		List<ChessMove> legal = game.getLegalMoves();
		return legal .get(random.nextInt(legal.size()));
    }
	
}
