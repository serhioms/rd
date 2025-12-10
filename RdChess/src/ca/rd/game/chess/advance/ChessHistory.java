package ca.rd.game.chess.advance;

import java.util.ArrayList;
import java.util.List;

public class ChessHistory {

	final public static int INITIAL_HISTORY_SIZE = 100;
	
	final public ChessGame game;
	private boolean isBlackMove;


	public ChessHistory(ChessGame game) {
		this.game = game;
		game.setHistory(this);
	}

	/*
	 * Proper player order
	 */
	public final boolean isBlackMove() {
		return isBlackMove;
	}

	/*
	 * Legal move implementation
	 */
	private List<ChessPiece> history = new ArrayList<ChessPiece>(INITIAL_HISTORY_SIZE);
	private int size;
	
	final public int getSize(){
		return size;
	}
	
	final public void setPiece(ChessPiece piece, Location to){
		
		piece.setTo(to);
		
		if( size < history.size() )
			history.set(size, piece);
		else
			history.add(piece);
		
		size++;
	}
	
	final public void addMove(Location moveFr, Location moveTo){
		addMove(game.getOcupant(moveFr), moveTo);
	}
	
	final public void addMove(ChessPiece piece, Location moveTo){
		
		piece.beforeMove();
		
		piece.move(moveTo);
		
		if( size < history.size() )
			history.set(size, piece);
		else
			history.add(piece);
		
		size++;
		isBlackMove = !isBlackMove;
		
		piece.afterMove();
	}
	
	final public void goBack(int n){
		while( n-- > 0 ){
			ChessPiece piece = history.get(size-1);
			piece.beforeBack();
			size--;
			piece.back();
			isBlackMove = !isBlackMove;
			piece.afterBack();
		}
	}
	
	final public void goStart(){
		goBack(history.size());
	}
	
	final public ChessPiece getLastPiece(){
		return history.get(size-1);
	}
	
	final public String getRecord(){
		ChessPiece piece = getLastPiece();
		ChessPieceRecord current = piece.getCurrent();
		if( current.captured != null )
			return getSize()+") "+piece+" "+piece.getPrevious().moveTo+" x "+current.captured+" "+current.moveTo;
		else
			return getSize()+") "+piece+" "+piece.getPrevious().moveTo+" - "+current.moveTo;
	}

}
