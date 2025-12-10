package ca.mss.rd.chessictk.game;

import ictk.boardgame.chess.ChessBoard;
import ictk.boardgame.chess.ChessMove;
import ictk.boardgame.chess.ChessPiece;
import ictk.boardgame.chess.Square;
import ictk.boardgame.chess.io.SAN;

import java.util.Iterator;
import java.util.List;

import ca.mss.rd.util.UtilValidate;


public class ChessLaszlo extends ChessGame {
	
	static final private SAN san = new SAN();

	/**
	 * 
	 */
	public ChessLaszlo() {
		super();
	}

	public ChessLaszlo(String pgn) {
		super(pgn);
	}


    
    public String getEvent() {
		if( isStartTheGame() )
    		return "ongamestart";
		else
			return getEvent(getCurrentMove());
    }

    public String getEvent(ChessMove move) {
    	if( move.isCheckmate() )
			return "onmate";
		else if( move.isStalemate() )
			return "onstalemate";
		else if( move.isEndOfGame() )
			return "ondraw";
		else  if( move.isCastleKingside() )
			return "oncastle";
		else if( move.isCastleQueenside() )
			return "oncastle";
		else if( move.isDoubleCheck() )
			return "ondoublecheck";
		else if( move.isCheck() )
			return "oncheck";
		return getEvent(getPiece(move));
    }

    public String getEvent(ChessPiece piece) {
		if( piece == null )
			return "ondroptoboard";
		else if( piece.isCaptured() )
			return "ondroptobox";
		else 
			return "ondroptoboard";
    }

    public String toXml() {
		StringBuffer xml = new StringBuffer();

    	String tableId = getProperty("tableId");
    	
    	xml.append("<position"); // position
			xml.append(" tId=\"");
			xml.append(tableId);
			xml.append("\"");
			
			xml.append(" bId=\"");
			xml.append(0);
			xml.append("\"");
			
			xml.append(" pId=\"");
			xml.append(getCurrentMoveNumber());
			xml.append("\"");
			
			xml.append(" event=\"");
			xml.append(getEvent());
			xml.append("\"");
			
			xml.append(" isWM=\"");
			xml.append(!isBlackSide());
			xml.append("\"");
			
			xml.append(" nop=\"");
			xml.append(getBoardPiecesAmount());
			xml.append("\"");
			
			if( !isStartTheGame() ){ // set last move data for history record
				ChessMove move = (ChessMove )getHistory().getCurrentMove();
				Square dest = move.getDestination();
				ChessPiece piece = (ChessPiece )dest.getPiece();
				ChessPiece casuality = move.getCasualty();
				
    			xml.append(" pid=\"");
				xml.append(piece.getPieceId());
				xml.append("\"");
				
    			xml.append(" f=\"");
				xml.append(dest.getFileAsChar());
				xml.append("\"");
				
				xml.append(" r=\"");
				xml.append(dest.getRankAsChar());
				xml.append("\"");

				xml.append(" isW=\"");
				xml.append(!piece.isBlack());
				xml.append("\"");
	
				xml.append(" isC=\"");
				xml.append(casuality != null? casuality.isCaptured(): false);
				xml.append("\"");
			}
		xml.append(">");
		
		if( !isStartTheGame() ){ // we do not need to send initial position. It is predefined on the client.
			xml.append("<location>"); // locations
				boolean isWhiteTeam = false;
				do {
					isWhiteTeam = !isWhiteTeam; 
					
					Iterator<ChessPiece> iter = getTeamIterator(isWhiteTeam);
					while( iter.hasNext() ){

						ChessPiece piece=iter.next();
						
						if( piece.moveDetector != piece.moveCount ){ // only for pieces which get moved recently... 
	    					if( piece.isCaptured() ){
	    						if( piece.moveDetector > 0 ){
    	    				    	xml.append("<");
    	    				    	xml.append(piece.getPieceId());
    				    			xml.append(" f=\"0\" r=\"0\"/>");
	    						}
	    					} else {
								Square square = piece.getSquare();
								
	    				    	xml.append("<");
	    				    	xml.append(piece.getPieceId());
	    			    			xml.append(" f=\"");
	    							xml.append(square.getFileAsChar());
	    							xml.append("\"");
	    							
	    							xml.append(" r=\"");
	    							xml.append(square.getRankAsChar());
	    							xml.append("\"");

	    							xml.append(" event=\"");
	    							xml.append(getEvent(piece));
	    							xml.append("\"");
	    						xml.append("/>");
	    					}
						}
			        }
				} while( isWhiteTeam );
			xml.append("</location>"); // end of locations
	
			xml.append("<valid>"); // valid moves
		        List<ChessMove> legals = getLegalMoves();
				for(int i=0, max=legals.size(); i<max; i++){
					ChessMove move = legals.get(i);
					try {
						this.clearMoveCounts().addMove(move);
						isWhiteTeam = false;
						do {
							isWhiteTeam = !isWhiteTeam; 
							
							Iterator<ChessPiece> iter = getTeamIterator(isWhiteTeam);
							while( iter.hasNext() ){

								ChessPiece piece=iter.next();
								
								if( piece.moveDetector != piece.moveCount ){ // only for pieces which get moved recently... 
			    					if( piece.isCaptured() ){
			    				    	xml.append("<");
			    				    	xml.append(piece.getPieceId());
						    			xml.append(" f=\"0\" r=\"0\"");
		    							xml.append(" event=\"");
		    							xml.append(getEvent(piece));
		    							xml.append("\"/>");
			    					} else {
										Square dest = piece.getSquare();
										
										xml.append("<");
								    	xml.append(piece.getPieceId());
							    			xml.append(" f=\"");
											xml.append(dest.getFileAsChar());
											xml.append("\"");
											
											xml.append(" r=\"");
											xml.append(dest.getRankAsChar());
											xml.append("\"");

											xml.append(" isW=\"");
											xml.append(!piece.isBlack());
											xml.append("\"");

											xml.append(" isC=\"");
											xml.append(piece.isCaptured());
											xml.append("\"");

											xml.append(" event=\"");
											xml.append(getEvent(piece));
											xml.append("\"");
										xml.append("/>");
			    					}
								}
					        }
						} while( isWhiteTeam );
						this.retractMove();
					} catch(Exception e){
						ChessPiece piece =  (ChessPiece )move.getOrigin().getPiece();
						Square dest = move.getDestination();

						xml.append("<");
				    	xml.append(piece.getPieceId());
			    			xml.append(" f=\"");
							xml.append(dest.getFileAsChar());
							xml.append("\"");
							
							xml.append(" r=\"");
							xml.append(dest.getRankAsChar());
							xml.append("\"");

							xml.append(" isW=\"");
							xml.append(!piece.isBlack());
							xml.append("\"");

							xml.append(" isC=\"");
							xml.append(piece.isCaptured());
							xml.append("\"");

							xml.append(" event=\"");
							xml.append(getEvent(move));
							xml.append("\"");
						xml.append("/>");
					}
				}
		    xml.append("</valid>"); // end of valid moves
		}
		xml.append("</position>"); // end of position

    	return xml.toString();
    }

	public void populateIds() {
		
    	ChessBoard board = getBoard();

        for(int r=1; r <= board.MAX_RANK; r++) {
            if( r > 2 && r < 7 ){
            	continue; // just skip mid field for startup position
            }
            for(int f=1; f <= board.MAX_FILE; f++) {
            	
            	Square square = board.getSquare(f, r);
            	if( square.isOccupied()) {
            		
            		ChessPiece piece = (ChessPiece )square.getPiece();
            		String pieceId = piece.getPieceId();
            		
        			if( UtilValidate.isEmpty(pieceId) ){
        				pieceId = String.valueOf(san.pieceToChar(piece)).toUpperCase()+(piece.isBlack()?"B":"W")+String.valueOf(square.getFile());
        				piece.setPieceId(pieceId);
        			}
            	}
            }
        }
    }
    
}
