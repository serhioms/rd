package ca.rd.game.chess.simple;

import ca.rd.game.chess.simple.ChessGameSimple.ChessColor;
import ca.rd.game.chess.simple.ChessGameSimple.ChessFace;
import ca.rd.game.generic.AbstractPiece;
import ca.rd.game.generic.Color;
import ca.rd.game.generic.Face;

public class ChessPieceSimple extends AbstractPiece {
	
	final public ChessFace face;
	final public ChessColor color;

	public ChessPieceSimple(ChessFace face, ChessColor color) {
		this.face = face;
		this.color = color;
	}

	@Override
	public Face getFace() {
		return face;
	}

	@Override
	public Color getColor() {
		return color;
	}

	@Override
	public int getRank() {
		return face.getRank();
	}

	@Override
	public String toString() {
		return color == ChessColor.White? face.toString().toUpperCase(): face.toString().toLowerCase();
	}
	
	
}

