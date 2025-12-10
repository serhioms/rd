package ca.rd.game.chess.simple;

import java.util.ArrayList;
import java.util.List;

import ca.rd.game.chess.advance.ChessPiece;
import ca.rd.game.generic.AbstractGame;
import ca.rd.game.generic.Color;
import ca.rd.game.generic.Face;
import ca.rd.game.generic.Piece;

public class ChessGameSimple extends AbstractGame {

	public ChessGameSimple() {
		super();
	}

	final static public int[] STANDARD_CHESS_RANKS = new int[]{0,8,4,3,2,1};
	
	public enum ChessFace implements Face {
		King("K", getRanks()[0]),
		Queen("Q", getRanks()[1]),
		Rook("R", getRanks()[2]),
		Bishop("B", getRanks()[3]),
		Knight("N", getRanks()[4]),
		Pawn("P", getRanks()[5]);

		final public String name;
		private int rank;
		
		private ChessFace(String name, int rank) {
			this.rank = rank;
			this.name = name;
		}

		@Override
		public int getRank() {
			return rank;
		}

		@Override
		public void setRank(int rank) {
			this.rank = rank;
		}
		
		@Override
		public String toString() {
			return name;
		}

	}
	
	public enum ChessColor implements Color {
		Black,
		White
	}
	
	/* Let ansesstors use more complexiv pieces then min package require */
	protected Piece createPiece(ChessFace face, ChessColor color, int faceId, int pieceId){
		return new ChessPieceSimple(face, color);
	}
	
	@Override
	final public List<Piece> createGame() {
		final List<Piece> game = new ArrayList<Piece>();
		for(ChessColor color: ChessColor.values()) {
			for(ChessFace face: ChessFace.values()) {
				switch(face){
				case Pawn:
					for(int i=ChessPiece.FACE_PAWN_1_ID; i<=ChessPiece.FACE_PAWN_8_ID; i++)
						game.add(createPiece(face, color, i, game.size())); 
					break;
				case King:
					game.add(createPiece(face, color, ChessPiece.FACE_KING_ID, game.size())); 
					break;
				case Queen:
					game.add(createPiece(face, color, ChessPiece.FACE_QUEEN_ID, game.size())); 
					break;
				case Bishop:
					game.add(createPiece(face, color, ChessPiece.FACE_BISHOP_1_ID, game.size())); 
					game.add(createPiece(face, color, ChessPiece.FACE_BISHOP_2_ID, game.size())); 
					break;
				case Knight:
					game.add(createPiece(face, color, ChessPiece.FACE_KNIGHT_1_ID, game.size())); 
					game.add(createPiece(face, color, ChessPiece.FACE_KNIGHT_2_ID, game.size())); 
					break;
				case Rook:
					game.add(createPiece(face, color, ChessPiece.FACE_ROOK_1_ID, game.size())); 
					game.add(createPiece(face, color, ChessPiece.FACE_ROOK_2_ID, game.size())); 
					break;
				}
			}
		}
		return game;
	}

	static public int[] getRanks() {
		return STANDARD_CHESS_RANKS;
	}

}


