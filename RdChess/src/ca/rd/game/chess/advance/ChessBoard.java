package ca.rd.game.chess.advance;

import java.util.HashSet;

import ca.rd.game.chess.simple.ChessGameSimple.ChessColor;
import ca.rd.game.chess.simple.ChessGameSimple.ChessFace;


public class ChessBoard {

	final public static int MOVE = 2;
	final public static int POWER = 1;
	final public static int BOTH = 0;
	
	final public Location[] squares = Location.values();
	final public ChessPiece[] ocupant = new ChessPiece[squares.length];

	final public ChessPieceSet[] powerWhite = new ChessPieceSet[squares.length];
	final public ChessPieceSet[] powerBlack = new ChessPieceSet[squares.length];
	
	public ChessBoard() {
		for(int i=0; i<squares.length; i++){
			powerWhite[i] = new ChessPieceSet();
			powerBlack[i] = new ChessPieceSet();
		}
	}

	public void setPower(Location to, ChessPiece piece){
		if( piece.color == ChessColor.White )
			powerWhite[to.ordinal()].add(piece);
		else
			powerBlack[to.ordinal()].add(piece);
	}

	public void cleanPower(){
		for(int i=0; i<squares.length; i++){
			powerWhite[i].clear();
			powerBlack[i].clear();
		}
	}

	public class ChessPieceSet extends HashSet<ChessPiece>{
		
	}
	
	final public Location getSquare(int index){
		return squares[index];
	}

	final static protected int[][][] getLegalTemplate(ChessFace face, ChessColor color){
		switch( face ){
		case Bishop:
			return BISHOP_MOVES;
		case King: 
			return KING_MOVES;
		case Knight: 
			return KNIGHT_MOVES;
		case Pawn: 
			return color == ChessColor.Black? PAWN_BLACK_MOVES: PAWN_WHITE_MOVES;
		case Queen: 
			return QUEEN_MOVES;
		case Rook: 
			return ROOK_MOVES;
		}
		throw new RuntimeException("Unexpected face ["+face+"] for legal move invocation");
	}
	
	final static public int[][][] BISHOP_MOVES = new int[][][]{
		new int[][]{{+1,+1,BOTH},{+2,+2,BOTH},{+3,+3,BOTH},{+4,+4,BOTH},{+5,+5,BOTH},{+6,+6,BOTH},{+7,+7,BOTH}},
		new int[][]{{+1,-1,BOTH},{+2,-2,BOTH},{+3,-3,BOTH},{+4,-4,BOTH},{+5,-5,BOTH},{+6,-6,BOTH},{+7,-7,BOTH}},
		new int[][]{{-1,+1,BOTH},{-2,+2,BOTH},{-3,+3,BOTH},{-4,+4,BOTH},{-5,+5,BOTH},{-6,+6,BOTH},{-7,+7,BOTH}},
		new int[][]{{-1,-1,BOTH},{-2,-2,BOTH},{-3,-3,BOTH},{-4,-4,BOTH},{-5,-5,BOTH},{-6,-6,BOTH},{-7,-7,BOTH}}
	}; 
	
	final static public int[][][] ROOK_MOVES = new int[][][]{
		new int[][]{{0,+1,BOTH},{0,+2,BOTH},{0,+3,BOTH},{0,+4,BOTH},{0,+5,BOTH},{0,+6,BOTH},{0,+7,BOTH}},
		new int[][]{{0,-1,BOTH},{0,-2,BOTH},{0,-3,BOTH},{0,-4,BOTH},{0,-5,BOTH},{0,-6,BOTH},{0,-7,BOTH}},
		new int[][]{{+1,0,BOTH},{+2,0,BOTH},{+3,0,BOTH},{+4,0,BOTH},{+5,0,BOTH},{+6,0,BOTH},{+7,0,BOTH}},
		new int[][]{{-1,0,BOTH},{-2,0,BOTH},{-3,0,BOTH},{-4,0,BOTH},{-5,0,BOTH},{-6,0,BOTH},{-7,0,BOTH}}
	};

	final static public int[][][] QUEEN_MOVES = new int[][][]{
		new int[][]{{+1,+1,BOTH},{+2,+2,BOTH},{+3,+3,BOTH},{+4,+4,BOTH},{+5,+5,BOTH},{+6,+6,BOTH},{+7,+7,BOTH}},
		new int[][]{{+1,-1,BOTH},{+2,-2,BOTH},{+3,-3,BOTH},{+4,-4,BOTH},{+5,-5,BOTH},{+6,-6,BOTH},{+7,-7,BOTH}},
		new int[][]{{-1,+1,BOTH},{-2,+2,BOTH},{-3,+3,BOTH},{-4,+4,BOTH},{-5,+5,BOTH},{-6,+6,BOTH},{-7,+7,BOTH}},
		new int[][]{{-1,-1,BOTH},{-2,-2,BOTH},{-3,-3,BOTH},{-4,-4,BOTH},{-5,-5,BOTH},{-6,-6,BOTH},{-7,-7,BOTH}},
		new int[][]{{0,+1,BOTH},{0,+2,BOTH},{0,+3,BOTH},{0,+4,BOTH},{0,+5,BOTH},{0,+6,BOTH},{0,+7,BOTH}},
		new int[][]{{0,-1,BOTH},{0,-2,BOTH},{0,-3,BOTH},{0,-4,BOTH},{0,-5,BOTH},{0,-6,BOTH},{0,-7,BOTH}},
		new int[][]{{+1,0,BOTH},{+2,0,BOTH},{+3,0,BOTH},{+4,0,BOTH},{+5,0,BOTH},{+6,0,BOTH},{+7,0,BOTH}},
		new int[][]{{-1,0,BOTH},{-2,0,BOTH},{-3,0,BOTH},{-4,0,BOTH},{-5,0,BOTH},{-6,0,BOTH},{-7,0,BOTH}}
	};

	final static public int[][][] KING_MOVES = new int[][][]{
		new int[][]{{+1,+1,BOTH}},
		new int[][]{{+1,-1,BOTH}},
		new int[][]{{-1,+1,BOTH}},
		new int[][]{{-1,-1,BOTH}},
		new int[][]{{ 0,+1,BOTH}},
		new int[][]{{ 0,-1,BOTH}},
		new int[][]{{+1,0,MOVE},{+2,0,MOVE}}, // Short Castle
		new int[][]{{-1,0,MOVE},{-2,0,MOVE}}  // Long Castle
	};

	final static public int[][][] PAWN_WHITE_MOVES = new int[][][]{
		new int[][]{{0,-1,MOVE},{0,-2,MOVE}},
		new int[][]{{-1,-1,POWER}},
		new int[][]{{+1,-1,POWER}},
	};

	final static public int[][][] PAWN_BLACK_MOVES = new int[][][]{
		new int[][]{{0,+1,MOVE},{0,+2,MOVE}},
		new int[][]{{+1,+1,POWER}},
		new int[][]{{-1,+1,POWER}},
	};
	
	final static public int[][][] KNIGHT_MOVES = new int[][][]{
		new int[][]{{-2,-1,BOTH}},
		new int[][]{{-2,+1,BOTH}},
		new int[][]{{-1,+2,BOTH}},
		new int[][]{{+1,+2,BOTH}},
		new int[][]{{+2,+1,BOTH}},
		new int[][]{{+2,-1,BOTH}},
		new int[][]{{+1,-2,BOTH}},
		new int[][]{{-1,-2,BOTH}}
	};
	
	/*

8  a8 b8 c8 d8 e8 f8 g8 h8 
7  a7 b7 c7 d7 e7 f7 g7 h7 
6  a6 b6 c6 d6 e6 f6 g6 h6 
5  a5 b5 c5 d5 e5 f5 g5 h5 
4  a4 b4 c4 d4 e4 f4 g4 h4 
3  a3 b3 c3 d3 e3 f3 g3 h3 
2  a2 b2 c2 d2 e2 f2 g2 h2 
1  a1 b1 c1 d1 e1 f1 g1 h1 
   a  b  c  d  e  f  g  h
   
8   0  1  2  3  4  5  6  7 
7   8  9 10 11 12 13 14 15 
6  16 17 18 19 20 21 22 23 
5  24 25 26 27 28 29 30 31 
4  32 33 34 35 36 37 38 39 
3  40 41 42 43 44 45 46 47 
2  48 49 50 51 52 53 54 55 
1  56 57 58 59 60 61 62 63 
   a  b  c  d  e  f  g  h
	
	*/
	
	final static public int SHOW_SQUARES_ID = 0;
	final static public int SHOW_SQUARES_ORDINALS = 2;
	final static public int SHOW_SQUARES_POWER = 4;
	final static public int SHOW_SQUARES_POWER_WHITE = 6;
	final static public int SHOW_SQUARES_POWER_BLACK = 8;
	
	final static public int SHOW_WHITE_SIDE = 0;
	final static public int SHOW_BLACK_SIDE = 1;
	
	public String getBoard(int what) {
		switch( what ){
		case SHOW_SQUARES_ID:
		case SHOW_SQUARES_ORDINALS:
		case SHOW_SQUARES_POWER:
		case SHOW_SQUARES_POWER_WHITE:
		case SHOW_SQUARES_POWER_BLACK:
			return getWhiteBoard(what);
		default:
			return getBlackBoard(what-1);
		}
	}
	
	public String getWhiteBoard(int what) {
		String board = "";
		
		int v=0, size;
		for(int i=0; i< squares.length; i++ ){
			if( i % 8 == 0 ){
				if( i > 0 )
					board += "\n";
				board += Location.VERTICAL[v++];
				board += "  ";
			}
			
			switch( what ){
			case SHOW_SQUARES_ID:
				board += squares[i];
				break;
			case SHOW_SQUARES_ORDINALS:
				String ordinal = "  "+squares[i].ordinal();
				board += ordinal.substring(ordinal.length()-2, ordinal.length());
				break;
			case SHOW_SQUARES_POWER:
				size = powerWhite[squares[i].ordinal()].size()-powerBlack[squares[i].ordinal()].size();
				board += (size == 0? "  ": (size>0?"+"+Integer.toString(size):Integer.toString(size)));
				break;
			case SHOW_SQUARES_POWER_WHITE:
				size = powerWhite[squares[i].ordinal()].size();
				board += (size == 0? "  ": (size>0?"+"+Integer.toString(size):Integer.toString(size)));
				break;
			case SHOW_SQUARES_POWER_BLACK:
				size = -powerBlack[squares[i].ordinal()].size();
				board += (size == 0? "  ": (size>0?"+"+Integer.toString(size):Integer.toString(size)));
				break;
			}
			
			board += " ";
		}
		board += "\n";
		
		board += " ";
		for(int h=0; h< 8; h++ ){
			board += "  ";
			board += Location.HORIZONTAL[h].toLowerCase();
		}
		board += "\n\n";

		return board;
	}


	public String getBlackBoard(int what) {
		String board = "";
		
		int v=0;
		for(int i=0; i< squares.length; i++ ){
			if( i % 8 == 0 ){
				if( i > 0 )
					board += "\n";
				board += Location.VERTICAL[v++];
				board += "  ";
			}
			
			switch( what ){
			case SHOW_SQUARES_ID:
				board += squares[i];
				break;
			case SHOW_SQUARES_ORDINALS:
				String ordinal = "  "+squares[i].ordinal();
				board += ordinal.substring(ordinal.length()-2, ordinal.length());
				break;
			case SHOW_SQUARES_POWER:
				int size = powerWhite[squares[i].ordinal()].size()-powerBlack[squares[i].ordinal()].size();
				board += (size == 0? "  ": (size>0?"+"+Integer.toString(size):Integer.toString(size)));
				break;
			}
			
			board += " ";
		}
		board += "\n";
		
		board += " ";
		for(int h=0; h< 8; h++ ){
			board += "  ";
			board += Location.HORIZONTAL[h].toLowerCase();
		}
		board += "\n\n";

		return board;
	}


	public static void main(String[] args) {
		ChessBoard cb = new ChessBoard(); 
		
		System.out.println( cb.getWhiteBoard(0) );
	}
	
}
