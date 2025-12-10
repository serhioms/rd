package ca.rd.game.chess.test;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import ca.rd.game.chess.advance.Location;
import ca.rd.game.chess.advance.ChessBoard;
import ca.rd.game.chess.advance.ChessGame;
import ca.rd.game.chess.advance.ChessHistory;
import ca.rd.game.chess.advance.ChessPiece;
import ca.rd.game.chess.simple.ChessGameSimple;

public class ChessTestSimple {

	ChessGameSimple game;
	ChessGame game2;
	ChessPiece piece;
	
	@Before
	public void setUp() throws Exception {
		game = new ChessGameSimple();
		game2 = new ChessGame();
	}


	@Test
	public void testGame() {
		assertTrue(game.getGame().size() == 32);
	}

	@Test
	public void testColors() {
		for(ChessGameSimple.ChessColor color: ChessGameSimple.ChessColor.values()) {
			assertTrue(game.getPiecesByColor(color).size() == 16);
		}
	}


	@Test
	public void testFaces() {
		for(ChessGameSimple.ChessFace face: ChessGameSimple.ChessFace.values()) {
			switch( face) {
				case Bishop:
				case Knight:
				case Rook:
					assertTrue(game.getPiecesByFace(face).size() == 4);
					break;
				case Pawn:
					assertTrue(game.getPiecesByFace(face).size() == 16);
					break;
				case King:
				case Queen:
					assertTrue(game.getPiecesByFace(face).size() == 2);
					break;
			}
		}
	}

	@Test
	public void testRanks() {
		int[] ranks = ChessGameSimple.getRanks();
		for(int i=0; i<ranks.length ; i++) {
			switch(ranks[i] ){
			case 0:
				assertTrue( game.getPiecesByRank(ranks[i]).size() == 2 );					
				break;
			case 8:
				assertTrue( game.getPiecesByRank(ranks[i]).size() == 2 );					
				break;
			case 4:
				assertTrue( game.getPiecesByRank(ranks[i]).size() == 4 );					
				break;
			case 3:
				assertTrue( game.getPiecesByRank(ranks[i]).size() == 4 );					
				break;
			case 2:
				assertTrue( game.getPiecesByRank(ranks[i]).size() == 4 );					
				break;
			case 1: 
				assertTrue( game.getPiecesByRank(ranks[i]).size() == 16 );					
				break;
			default:
				assertTrue( false );					
			}
		}
	}
	
	
	@Test
	public void testMovesWhithoutCaptures() {
		
		try {
			Random rnd = new Random();
			int pieceLimit = game2.getGameSize();
			
			ChessHistory history = new ChessHistory(game2);
			
			game2.setInitialPosition();
			
			String beginBoard = game2.getWhiteBoard();
			// System.out.println( beginBoard );
			// System.out.println();
			
			for(int i=0; i<100; i++){
				piece = (ChessPiece )game2.getPiece(rnd.nextInt(pieceLimit));
				
				// no drops 
				Location moveTo = game2.board.squares[rnd.nextInt(game2.board.squares.length)];
				while( game2.getOcupant(moveTo) != null ){
					moveTo =  game2.board.squares[rnd.nextInt(game2.board.squares.length)];
				}
				
				history.addMove(piece, moveTo);
				
				// System.out.println( history.getRecord() );
				// System.out.println( game2.getWhiteBoard());
			}

			// System.out.println( game2.getWhiteBoard());
		
			history.goStart();
			
			String endBoard = game2.getWhiteBoard();
			
			// System.out.println( endBoard);

			assertTrue( endBoard.equals(beginBoard) );
		} catch( RuntimeException e){
			assertTrue(false);
		}
	}
	
	@Test
	public void testMovesWithCaptures() {
		
		try {
			Random rnd = new Random();
			int pieceLimit = game2.getGameSize();
			
			ChessHistory history = new ChessHistory(game2);
			
			game2.setInitialPosition();
			
			String beginBoard = game2.getWhiteBoard();
			// System.out.println( beginBoard );
			// System.out.println();
			
			for(int i=0; i<100; i++){
				
				piece = (ChessPiece )game2.getPiece(rnd.nextInt(pieceLimit));
				while( piece.isCaptured() ){
					piece = (ChessPiece )game2.getPiece(rnd.nextInt(pieceLimit));
				}
				
				final Location location = game2.getLocation(rnd.nextInt(game2.board.squares.length));
				
				history.addMove(piece, location);

				// System.out.println( history.getRecord() );
				// System.out.println( game2.getWhiteBoard());
			}

			// System.out.println( game2.getWhiteBoard());
		
			history.goStart();
			
			String endBoard = game2.getWhiteBoard();
			
			// System.out.println( endBoard);

			assertTrue( endBoard.equals(beginBoard) );
		} catch( RuntimeException e){
			assertTrue(false);
		}
	}	
	
	@Test
	public void testMovesLegal() {
		
		try {
			Random rnd = new Random();
			
			ChessHistory history = new ChessHistory(game2);
			
			game2.setInitialPosition();
			
			String beginBoard = game2.getWhiteBoard();
//			 System.out.println( beginBoard );
//			 System.out.println();
			
			List<ChessPiece> pieces = new ArrayList<ChessPiece>();
			List<Location> locations = new ArrayList<Location>();

			for(int i=0; i<1000; i++){
				
				pieces.clear();
				for(Iterator<ChessPiece> iter=game2.iterator(history.isBlackMove()); iter.hasNext(); ){
					pieces.add(iter.next());
				}

				piece = null;
				
				while( true ){
					if( pieces.size() == 0 ){
						break;
					}
	
					piece = pieces.get(rnd.nextInt(pieces.size()));
					
					locations.clear();
					for(Iterator<Location> iter=piece.iteratorAll(); iter.hasNext(); ){
						locations.add(iter.next());
					}
	
					if( locations.size() == 0 ){
						pieces.remove(piece);
						piece = null;
						continue;
					}

					break;
				}

				if( piece == null ){
//					 System.out.println( "No piece to move..." );
					break;
				}
				
				Location location = locations.get(rnd.nextInt(locations.size()));

				history.addMove(piece, location);

//				 System.out.println( history.getRecord() );
//				 System.out.println( game2.getWhiteBoard());
//				 System.out.println();
			}

//			 System.out.println( game2.getWhiteBoard());
		
			history.goStart();
			
			String endBoard = game2.getWhiteBoard();
			
			// System.out.println( endBoard);

			assertTrue( endBoard.equals(beginBoard) );
		} catch( RuntimeException e){
			e.printStackTrace();
			assertTrue(false);
		}
	}	
	
	@Test
	public void testRookLeft() {
		
		try {
			ChessHistory history = new ChessHistory(game2);
			
			game2.setInitialPosition();
			
			history.addMove(game2.getOcupant(Location.e2), Location.e4);
			history.addMove(game2.getOcupant(Location.a7), Location.a5);
			history.addMove(game2.getOcupant(Location.d2), Location.d4);
			history.addMove(game2.getOcupant(Location.a8), Location.a6);

			// System.out.println( game2.getWhiteBoard() );
			// System.out.println( "Legal moves for ["+game2.getOcupant(Location.a6).toRecord()+"]: "+game2.getOcupant(Location.a6).getLegalMoveList(history) );
			assertTrue( "a7,a8,b6,c6,d6,e6,f6,g6,h6".equals(game2.getOcupant(Location.a6).getLegalMoveList()) );
			
			// System.out.println( game2.getWhiteBoard() );
			// System.out.println( "Legal moves for ["+game2.getOcupant(Location.h8).toRecord()+"]: "+game2.getOcupant(Location.h8).getLegalMoveList(history) );
			assertTrue( null == game2.getOcupant(Location.h8).getLegalMoveList() );
			
		} catch( RuntimeException e){
			e.printStackTrace();
			assertTrue(false);
		}
	}	
	
	@Test
	public void testRookRight() {
		
		try {
			ChessHistory history = new ChessHistory(game2);
			
			game2.setInitialPosition();
			
			history.addMove(game2.getOcupant(Location.e2), Location.e4);
			history.addMove(game2.getOcupant(Location.h7), Location.h5);
			history.addMove(game2.getOcupant(Location.d2), Location.d4);
			history.addMove(game2.getOcupant(Location.h8), Location.h6);

//			 System.out.println( ChessBoard.getWhiteBoard(false) );
//			 System.out.println( game2.getWhiteBoard() );
//			 System.out.println( "Legal moves for ["+(piece=game2.getOcupant(Location.h6)).toRecord()+"]: "+piece.getLegalMoveList() );
			assertTrue( "h7,h8,g6,f6,e6,d6,c6,b6,a6".equals(game2.getOcupant(Location.h6).getLegalMoveList()) );
			
			// System.out.println( game2.getWhiteBoard() );
			// System.out.println( "Legal moves for ["+game2.getOcupant(Location.a8).toRecord()+"]: "+game2.getOcupant(Location.a8).getLegalMoveList() );
			assertTrue( null == game2.getOcupant(Location.a8).getLegalMoveList() );
			
		} catch( RuntimeException e){
			e.printStackTrace();
			assertTrue(false);
		}
	}	

}


