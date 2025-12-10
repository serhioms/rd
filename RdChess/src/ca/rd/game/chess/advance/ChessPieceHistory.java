package ca.rd.game.chess.advance;

import java.util.ArrayList;
import java.util.List;

import ca.rd.game.chess.simple.ChessGameSimple.ChessColor;
import ca.rd.game.chess.simple.ChessGameSimple.ChessFace;
import ca.rd.game.chess.simple.ChessPieceSimple;

public class ChessPieceHistory extends ChessPieceSimple {

	final public List<ChessPieceRecord> history = new ArrayList<ChessPieceRecord>(ChessHistory.INITIAL_HISTORY_SIZE/10);
	protected int historySize;
	
	final protected ChessPieceRecord instantiatePieceHistory(){
		ChessPieceRecord pieceHistory;
		
		if( historySize < history.size() )
			pieceHistory = history.get(historySize).clean();
		else
			history.add((pieceHistory = new ChessPieceRecord()));
		
		return pieceHistory;
	}

	final public ChessPieceRecord getCurrent(){
		return history.get(historySize-1);
	}

	final public ChessPieceRecord getPrevious(){
		return history.get(historySize-2);
	}

	public ChessPieceHistory(ChessFace face, ChessColor color) {
		super(face, color);
	}
}
