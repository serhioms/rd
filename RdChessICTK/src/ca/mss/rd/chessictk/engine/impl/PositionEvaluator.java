 package ca.mss.rd.chessictk.engine.impl;

import ictk.boardgame.chess.ChessMove;
import ictk.boardgame.chess.ChessPiece;

import java.util.Iterator;
import java.util.List;

import ca.mss.rd.chessictk.game.ChessGame;
import ca.mss.rd.tools.chess.com_wikispaces_chessprogramming_treesearch.iface.NodeEvaluator;

import ca.mss.rd.util.Debug;
import ca.mss.rd.util.UtilString;

public class PositionEvaluator {

	public static final String module = PositionEvaluator.class.getName();
	static final long serialVersionUID = module.hashCode();
	private static org.apache.log4j.Logger logger = ca.mss.rd.util.Debug.getLogger(module);
	
	private ChessGame game;

	// which side move should be generated
	private boolean isBlackSide; 

	public PositionEvaluator(ChessGame game) {
		this.game = game;
	}
	
	public final void populateSide() {
		this.isBlackSide = game.isBlackSide();
	}

	final static private long MATERIAL_FACTOR = 11; // 11
	final static private long PRECEISION_FACTOR = 10000000; 

	public long evaluate(int depth, int maxdepth) {
		long total = 0;

		if( depth != maxdepth ){
			if( game.isCheckmate() ){
				if( isBlackSide == game.isBlackSide() )
					total = -NodeEvaluator.MAXIMUM/2;
				else
					total = NodeEvaluator.MAXIMUM/2;
			} else {
				// TODO: treat draw as a checkmate for the beginning 
				if( isBlackSide == game.isBlackSide() )
					total = -NodeEvaluator.MAXIMUM/2;
				else
					total = NodeEvaluator.MAXIMUM/2;
			}

			if( Debug.isVerboseEnabled) logger.debug("Eval      "+UtilString.space(depth*2)+"[depth="+depth+"]["+game.sideColor()+"]["+(game.isCheckmate()?"CHECKMATE": "DRAW")+"][total="+total+"]");

			return total;
			
		} else {
			int materialCost = materialCost();
			int mobility = mobility();
			total = materialCost*MATERIAL_FACTOR + mobility;

			
			ChessMove move = game.retractMove();
				int materialCost2 = materialCost();
				int mobility2 = mobility();
				long total2 = materialCost2*MATERIAL_FACTOR + mobility2;
			game.nextMove(move);
			
			total = total*PRECEISION_FACTOR/total2;
	
			if( Debug.isVerboseEnabled) logger.debug("Eval      "+UtilString.space(depth*2)+"[depth="+depth+"]["+game.sideColor()+"]["+total+"={"+materialCost+","+mobility+"}]");
			
		}
			
		
		return total;
	}


	final private int mobility(){
		int mobility = game.getLegalMoves().size(); // Simple calculation

		List<ChessMove> list = game.getLegalMoves();
		for(int i=0, max=list.size(); i<max; i++){
			ChessMove move = list.get(i);
			
			game.addMove(move);
			
			int quiescence = game.getQuiescence(move.getChessPiece());
			
			mobility += quiescence>=0? 1: 0;
			
        	game.removeMove();
		}
		
		return mobility;
	}

	final private int materialCost() {
		int cost = 0;
		Iterator<ChessPiece> iter = game.getTeamIterator(game.isBlackSide());
		while (iter.hasNext()) {
			ChessPiece piece = iter.next();
			if (!piece.isCaptured()) {
				cost += game.getCost(piece);
			}
		}
		return cost;
	}

}
