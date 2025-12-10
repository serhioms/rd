package ca.rd.game.chess.advance;

import ca.rd.game.chess.simple.ChessGameSimple.ChessColor;
import ca.rd.game.chess.simple.ChessGameSimple.ChessFace;

public class ChessPiece extends ChessPieceIterator {

	final static public int FACE_PAWN_8_ID = 15;
	final static public int FACE_PAWN_7_ID = 14;
	final static public int FACE_PAWN_6_ID = 13;
	final static public int FACE_PAWN_5_ID = 12;
	final static public int FACE_PAWN_4_ID = 11;
	final static public int FACE_PAWN_3_ID = 10;
	final static public int FACE_PAWN_2_ID = 9;
	final static public int FACE_PAWN_1_ID = 8;
	final static public int FACE_KNIGHT_1_ID = 7;
	final static public int FACE_KNIGHT_2_ID = 6;
	final static public int FACE_BISHOP_1_ID = 5;
	final static public int FACE_BISHOP_2_ID = 4;
	final static public int FACE_ROOK_1_ID = 3;
	final static public int FACE_ROOK_2_ID = 2;
	final static public int FACE_QUEEN_ID = 1;
	final static public int FACE_KING_ID = 0;

	final public int pieceId;
	final public Location startLocation;
	private boolean isCaptured = false;

	public String toRecord() {
		return super.toString()+getCurrent().moveTo;
	}
	
	final public boolean isCaptured(){
		return isCaptured;
	}
	
	public ChessPiece(ChessGame game, ChessFace face, ChessColor color, int faceId, int pieceId) {
		super(face, color);

		this.game = game;
		this.pieceId = pieceId;
		
		if( color == ChessColor.White )
			switch( faceId ){
			case FACE_ROOK_1_ID:
				startLocation = Location.a1;
				return;
			case FACE_KNIGHT_1_ID:
				startLocation = Location.b1;
				return;
			case FACE_BISHOP_1_ID:
				startLocation = Location.c1;
				return;
			case FACE_QUEEN_ID:
				startLocation = Location.d1;
				return;
			case FACE_KING_ID:
				startLocation = Location.e1;
				return;
			case FACE_BISHOP_2_ID:
				startLocation = Location.f1;
				return;
			case FACE_KNIGHT_2_ID:
				startLocation = Location.g1;
				return;
			case FACE_ROOK_2_ID:
				startLocation = Location.h1;
				return;
				
			case FACE_PAWN_1_ID:
				startLocation = Location.a2;
				return;
			case FACE_PAWN_2_ID:
				startLocation = Location.b2;
				return;
			case FACE_PAWN_3_ID:
				startLocation = Location.c2;
				return;
			case FACE_PAWN_4_ID:
				startLocation = Location.d2;
				return;
			case FACE_PAWN_5_ID:
				startLocation = Location.e2;
				return;
			case FACE_PAWN_6_ID:
				startLocation = Location.f2;
				return;
			case FACE_PAWN_7_ID:
				startLocation = Location.g2;
				return;
			case FACE_PAWN_8_ID:
				startLocation = Location.h2;
				return;
			}
		else if( color == ChessColor.Black )
			switch( faceId ){
			case FACE_ROOK_1_ID:
				startLocation = Location.a8;
				return;
			case FACE_KNIGHT_1_ID:
				startLocation = Location.b8;
				return;
			case FACE_BISHOP_1_ID:
				startLocation = Location.c8;
				return;
			case FACE_QUEEN_ID:
				startLocation = Location.d8;
				return;
			case FACE_KING_ID:
				startLocation = Location.e8;
				return;
			case FACE_BISHOP_2_ID:
				startLocation = Location.f8;
				return;
			case FACE_KNIGHT_2_ID:
				startLocation = Location.g8;
				return;
			case FACE_ROOK_2_ID:
				startLocation = Location.h8;
				return;
				
			case FACE_PAWN_1_ID:
				startLocation = Location.a7;
				return;
			case FACE_PAWN_2_ID:
				startLocation = Location.b7;
				return;
			case FACE_PAWN_3_ID:
				startLocation = Location.c7;
				return;
			case FACE_PAWN_4_ID:
				startLocation = Location.d7;
				return;
			case FACE_PAWN_5_ID:
				startLocation = Location.e7;
				return;
			case FACE_PAWN_6_ID:
				startLocation = Location.f7;
				return;
			case FACE_PAWN_7_ID:
				startLocation = Location.g7;
				return;
			case FACE_PAWN_8_ID:
				startLocation = Location.h7;
				return;
			}
		throw new RuntimeException("Can not set up initial pozition for piece [id="+faceId+"][color="+color+"][face="+face+"]");
	}

	/*
	 * Move traversals 
	 */
	public void beforeMove(){
		populateLegalMoves();
		game.populatePowerDistribution();
	}
	
	public void afterMove(){
		populateLegalMoves();
		game.populatePowerDistribution();
	}
	
	public void beforeBack(){
	}
	
	public void afterBack(){
		cleanLegalMoves();
	}

	/*
	 * Implement piece Operations
	 */
	
	final public void setStart(){
		historySize = 0;
		setTo(startLocation);
	}
	
	final public void setTo(Location setTo){
		if( historySize != 0 )
			throw new RuntimeException("Piece ["+this+"] allready on the table");

		ChessPiece ocupant = game.board.ocupant[setTo.ordinal()];
		if( ocupant != null ){
			throw new RuntimeException("Can not set piece ["+this+"] to the place ["+setTo+"] occupied by ["+ocupant+"]");
		}

		ChessPieceRecord pieceHistory = instantiatePieceHistory();
		
		pieceHistory.moveType = MoveType.Initial;
		pieceHistory.moveTo = setTo;
		
		game.board.ocupant[setTo.ordinal()] = this;

		historySize++;
	}

	final public void move(Location moveTo){
		
		ChessPieceRecord pieceHistory = instantiatePieceHistory();

		// history changes
		pieceHistory.moveType = MoveType.Move;
		pieceHistory.moveTo = moveTo;
		pieceHistory.captured = game.board.ocupant[moveTo.ordinal()];
		
		if( pieceHistory.captured != null ){
			pieceHistory.moveType = MoveType.Capture;
			pieceHistory.captured.isCaptured = true;
		} else if( enpassantPiece != null && canPromoEnpassant(moveTo) ){
			pieceHistory.moveType = MoveType.Enpassant;
			pieceHistory.captured = enpassantPiece; 
			pieceHistory.captured.isCaptured = true;
		} else if( moveTo == shortCastle ){
			pieceHistory.moveType = MoveType.CastleShort;
		} else if( moveTo == longCastle ){
			pieceHistory.moveType = MoveType.CastleLong;
		}
		
		// Board Changes
		game.board.ocupant[getCurrent().moveTo.ordinal()] = null;
		game.board.ocupant[moveTo.ordinal()] = this;
		
		switch( pieceHistory.moveType ){
		case Enpassant:
			game.board.ocupant[pieceHistory.captured.getCurrent().moveTo.ordinal()] = null;
			break;
		case CastleShort:
			if( color == ChessColor.White ){
				game.board.ocupant[Location.f1.ordinal()] = game.board.ocupant[Location.h1.ordinal()];
				game.board.ocupant[Location.h1.ordinal()] = null;
			} else {
				game.board.ocupant[Location.f8.ordinal()] = game.board.ocupant[Location.h8.ordinal()];
				game.board.ocupant[Location.h8.ordinal()] = null;
			}
			break;
		case CastleLong:
			if( color == ChessColor.White ){
				game.board.ocupant[Location.d1.ordinal()] = game.board.ocupant[Location.a1.ordinal()];
				game.board.ocupant[Location.a1.ordinal()] = null;
			} else {
				game.board.ocupant[Location.d8.ordinal()] = game.board.ocupant[Location.a8.ordinal()];
				game.board.ocupant[Location.a8.ordinal()] = null;
			}
			break;
		default:
		}
		
		// move history pointer forward
		historySize++;
	}

	
	final public void back(){

		// return piece to previous square
		game.board.ocupant[getPrevious().moveTo.ordinal()] = this;

		ChessPieceRecord pieceHistory = getCurrent();
		
		// back move board & minor history changes 
		switch( pieceHistory.moveType ){
		case Initial:
		case Move:
			game.board.ocupant[pieceHistory.moveTo.ordinal()] = null;
			break;
		case Capture:
			pieceHistory.captured.isCaptured = false;
			game.board.ocupant[pieceHistory.moveTo.ordinal()] = pieceHistory.captured;
			break;
		case Enpassant:
			pieceHistory.captured.isCaptured = false;
			game.board.ocupant[pieceHistory.moveTo.ordinal()] = null;
			break;
		case CastleLong:
			if( color == ChessColor.White ){
				game.board.ocupant[Location.a1.ordinal()] = game.board.ocupant[Location.d1.ordinal()];
				game.board.ocupant[Location.d1.ordinal()] = null;
			} else {
				game.board.ocupant[Location.a8.ordinal()] = game.board.ocupant[Location.d8.ordinal()];
				game.board.ocupant[Location.d8.ordinal()] = null;
			}
			break;
		case CastleShort:
			if( color == ChessColor.White ){
				game.board.ocupant[Location.h1.ordinal()] = game.board.ocupant[Location.f1.ordinal()];
				game.board.ocupant[Location.f1.ordinal()] = null;
			} else {
				game.board.ocupant[Location.h8.ordinal()] = game.board.ocupant[Location.f8.ordinal()];
				game.board.ocupant[Location.f8.ordinal()] = null;
			}
			break;
		case Promotion:
			break;
		default:
		}

		// move history pointer back
		historySize--;
	}

}
