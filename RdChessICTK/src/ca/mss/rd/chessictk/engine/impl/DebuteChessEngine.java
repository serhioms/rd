package ca.mss.rd.chessictk.engine.impl;

import ictk.boardgame.chess.ChessGameInfo;
import ictk.boardgame.chess.ChessMove;
import ictk.boardgame.chess.ChessResult;

import java.util.Map;
import java.util.Random;
import java.util.Set;

import ca.mss.rd.chessictk.debutetree.DebuteTree;
import ca.mss.rd.chessictk.engine.ChessEngine;
import ca.mss.rd.chessictk.game.ChessGame;



public class DebuteChessEngine implements ChessEngine {

	final public static String module = DebuteChessEngine.class.getName();
	static final long serialVersionUID = module.hashCode();
	private final static org.apache.log4j.Logger logger = ca.mss.rd.util.Debug.getLogger(module);

	final private static Random random = new Random();
	
	private ChessGame game;
	private static DebuteTree dtree;
	
	public DebuteChessEngine(ChessGame game) {
		this.game = game;
		if( dtree == null ){
			dtree = new DebuteTree();
			dtree.setDebutePath("../ChessEngine/pgn/debute20.txt");
			dtree.setPgnPath("../ChessEngine/pgn/maestro.pgn");
			dtree.loadTree();
			//dtree.loadPGN();
		}
	}

	/* (non-Javadoc)
	 * @see ca.mss.chess.engine.ChessEngine#generateMove()
	 */
	final public ChessMove generateMove() {
		int m = game.getCurrentMoveNumber2();

		String move = "N/A";

		Set<String> level = null;

		Map<String, Map> tree = dtree.getDebuteTree();
		Map<String, Map> prev = tree;
		
		//List<RdChessMove> firstAll = game.getFirstAll();
		
		ChessMove first = game.goToBegin();
		if( first != null ){
			tree = tree.get(game.moveToString(first));
			for(int i=1; i<(m-1) && tree != null; i++ ){
				ChessMove next = game.goToNext();
				if( next != null ){
					prev = tree;
					tree = tree.get(game.moveToString(next));
				} else {
			    	ChessGameInfo info = game.getGameInfo();
	    			info.setResult(new ChessResult(game.isBlackSide()?ChessResult.WHITE_WIN: ChessResult.BLACK_WIN));
					return null;
				}
			}
		}
		
		if( tree == null ){
	    	ChessGameInfo info = game.getGameInfo();
			info.setResult(new ChessResult(game.isBlackSide()?ChessResult.WHITE_WIN: ChessResult.BLACK_WIN));
			return null;
		}
		
		level = tree.keySet();
		
		if( level.size() == 0 ){
	    	ChessGameInfo info = game.getGameInfo();
			info.setResult(new ChessResult(game.isBlackSide()?ChessResult.WHITE_WIN: ChessResult.BLACK_WIN));
			return null;
		}

		String[] legal = new String[level.size()];
		level.toArray(legal);
		
		move = legal[random.nextInt(legal.length)];
		
		Map<String, Map> next = tree.get(move);

		logger.info("Generated move ["+game.sideColor()+"][m="+m+"][move="+move+"]\n[prev="+prev.keySet()+"]\n[avl="+level+"]\n[next="+next.keySet()+"]");
		
		ChessMove cmove = game.stringToMove(move);
		return cmove;
    }
	
}
