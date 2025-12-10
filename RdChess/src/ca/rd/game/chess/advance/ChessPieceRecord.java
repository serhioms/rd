package ca.rd.game.chess.advance;

public class ChessPieceRecord {
	
	public Location moveTo;
	public ChessPiece movePiece;
	public MoveType moveType;
	public ChessPiece captured;
	public ChessPiece affected;
	public MoveType affectedType;
	
	final public ChessPieceRecord clean(){
		moveTo = null;
		movePiece = null;
		moveType = null;
		captured = null;
		affected = null;
		affectedType = null;
		return this;
	}
	
}

