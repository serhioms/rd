package ca.rd.game.chess.advance;

import java.util.Iterator;

import ca.rd.game.chess.simple.ChessGameSimple.ChessColor;
import ca.rd.game.chess.simple.ChessGameSimple.ChessFace;

abstract public class ChessPieceLegal extends ChessPieceHistory  {

	protected ChessGame game;

	final protected Location[][] legalMove;
	final protected Location[][] legalPower;
	final protected Location[][] legalLocation;

	final protected int[][][] legalTemplate;

	private String legalMoves;
	protected int legalMovesValid4size = -1;

	protected ChessPiece enpassantPiece;
	protected Location longCastle,shortCastle;
	

	abstract public Iterator<Location> iteratorAll();
	
	public ChessPieceLegal(ChessFace face, ChessColor color) {
		super(face, color);

		this.legalTemplate = ChessBoard.getLegalTemplate(face, color);
		
		this.legalMove = new Location[legalTemplate.length][];
		this.legalPower = new Location[legalTemplate.length][];
		this.legalLocation = new Location[legalTemplate.length][];
		
		for(int i=0,p=0,m=0; i<legalTemplate.length; i++){
			
			legalLocation[i] = new Location[legalTemplate[i].length];
			
			switch( legalTemplate[i][0][2] ){
			case ChessBoard.BOTH:
				legalMove[m++] = new Location[legalTemplate[i].length];
				legalPower[p++] = new Location[legalTemplate[i].length];
				break;
			case ChessBoard.POWER:
				legalPower[p++] = new Location[legalTemplate[i].length];
				break;
			case ChessBoard.MOVE:
				legalMove[m++] = new Location[legalTemplate[i].length];
				break;
			}
		}
	}
	
	final public String getLegalMoveList(){
		
		if( legalMovesValid4size != game.getHistory().getSize() )
			populateLegalMoves();
		
		if( legalMoves == null ){
			for(Iterator<Location> iter=iteratorAll(); iter.hasNext(); )
				if( legalMoves == null )
					legalMoves = iter.next().toString();
				else
					legalMoves += ","+iter.next();
		}
		return legalMoves;
			
	}
	
	final protected boolean canPromoEnpassant(Location to){
		Location toloc = enpassantPiece.getCurrent().moveTo;
		Location frloc = enpassantPiece.getPrevious().moveTo;
		if( toloc.h != to.h )
			return false;
		else if( color == ChessColor.Black)
			return to.v < frloc.v && to.v > toloc.v;
		else 
			return to.v > frloc.v && to.v < toloc.v;
	}
	
	final protected void cleanLegalMoves(){
		legalMoves = null;
	}

	final protected void populateLegalMoves(){

		legalMovesValid4size = game.getHistory().getSize();
		cleanLegalMoves();
		
		final Location moveTo = getCurrent().moveTo;
		final int H = moveTo.h, V = moveTo.v;
		int toH, toV;

		enpassantPiece = null;
		shortCastle = null;
		longCastle = null;
		
		Location nextMove;
		Location nextPower;
		
		for(int i=0,p=0,m=0; i<legalTemplate.length; i++){
			for(int j=0; j<legalTemplate[i].length; j++){

				nextMove = nextPower = null;

				switch(0){
				default:
					// check if new location is out of the board
					if( (toH=H+legalTemplate[i][j][0]) <0 )
						break;
					
					if( toH>=Location.MAX_H)
						break;
					
					if( (toV=V+legalTemplate[i][j][1]) <0 )
						break;
					
					if( toV>=Location.MAX_V)
						break;
					
					int to = Location.getIndex(toH, toV);
					
					// check king castling case
					if( face == ChessFace.King ){
						if( legalTemplate[i][j][0] == +2 ){
							if( historySize != 1)
								break;
		
							if( moveTo != (color == ChessColor.White? Location.e1: Location.e8) )
								break;
							
							ChessPiece rook = (color == ChessColor.White)? game.board.ocupant[Location.h1.ordinal()]: game.board.ocupant[Location.h8.ordinal()];
							if( rook == null )
								break;
							else if( rook.historySize != 1 )
								break;
							else if( rook.color != color )
								break;
							
							shortCastle = game.board.getSquare(to);
						} else if( legalTemplate[i][j][0] == -2 ){
							if( historySize != 1)
								break;
		
							if( moveTo != (color == ChessColor.White? Location.e1: Location.e8) )
								break;
							
							if( null != (color == ChessColor.White? game.board.ocupant[Location.b1.ordinal()]: game.board.ocupant[Location.b8.ordinal()]) )
								break;
							
							ChessPiece rook = (color == ChessColor.White)? game.board.ocupant[Location.a1.ordinal()]: game.board.ocupant[Location.a8.ordinal()];
							if( rook == null )
								break;
							else if( rook.historySize != 1 )
								break;
							else if( rook.color != color )
								break;
							
							longCastle = game.board.getSquare(to);
						}
					} else if( face == ChessFace.Pawn ){
						// Enpassant
						if( legalTemplate[i][j][0] != 0 ){
							// pawn power
							nextPower =  game.board.getSquare(to);
							
							// pawn captures
							ChessPiece piece = game.board.ocupant[to];
							if( piece == null ){
								if( game.getHistory().getSize() <= 0 )
									break;
								
								// Check En Passant
								ChessPiece prevPiece = game.getHistory().getLastPiece();
								if( prevPiece.face == ChessFace.Pawn && prevPiece.color != this.color ){
									Location toloc = prevPiece.getCurrent().moveTo;
									Location frloc = prevPiece.getPrevious().moveTo;
									if( toloc.h != toH )
										break;
									else if( color == ChessColor.Black && toV < frloc.v && toV > toloc.v ){
										enpassantPiece = prevPiece;
										//System.out.println("Enpassant "+toRecord()+" "+BoardLocation.getLocation(to));
									} else if( color == ChessColor.White && toV > frloc.v && toV < toloc.v ){
										enpassantPiece = prevPiece;
										//System.out.println("Enpassant "+toRecord()+" "+BoardLocation.getLocation(to));
									} else
										break;
								} else
									break;
							} else if( piece.color == color )
								break;
						} else if( legalTemplate[i][j][1] == -2 || legalTemplate[i][j][1] == +2 ){
							// pawn first long move
							if( historySize != 1 )
								break;
	
							if( moveTo.v != (color == ChessColor.White? Location.PAWN_WHITE_VERTICAL: Location.PAWN_BLACK_VERTICAL) )
								break;
							
						}
					}
					
					// standard piece move
					nextPower = nextMove = game.board.getSquare(to);
	
					// standard piece capture
					if( game.board.ocupant[to] != null ){
						if( game.board.ocupant[to].color == this.color ){
							nextMove = null;
							if( game.board.ocupant[to].face == ChessFace.King )
								nextPower = null;
						}
						break;
					}
				}
				
				legalLocation[i][j] = nextMove;

				switch( legalTemplate[i][j][2] ){
				case ChessBoard.BOTH:
					legalMove[m][j] = nextMove;
					legalPower[p][j] = nextPower;
					break;
				case ChessBoard.POWER:
					legalPower[p][j] = nextPower;
					break;
				case ChessBoard.MOVE:
					legalMove[m][j] = nextMove;
					break;
				}
				
				if( nextMove == null )
					break;
			}
			
			// according to initialization in constructor
			switch( legalTemplate[i][0][2] ){
			case ChessBoard.BOTH:
				m++;
				p++;
				break;
			case ChessBoard.POWER:
				p++;
				break;
			case ChessBoard.MOVE:
				m++;
				break;
			}
		}
	}	
	
}
