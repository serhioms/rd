package ca.rd.game.chess.test;

import static org.junit.Assert.assertTrue;

import java.awt.Color;

import org.junit.Before;
import org.junit.Test;

import ca.rd.game.chess.advance.Location;
import ca.rd.game.chess.advance.ChessGame;
import ca.rd.game.chess.advance.ChessHistory;
import ca.rd.game.chess.advance.ChessPiece;
import ca.rd.game.chess.simple.ChessGameSimple.ChessFace;

public class ChessTestSpecialMoves {

	ChessHistory history;
	ChessGame game;
	ChessPiece piece, rook, wrook, brook, bishop, queen, knight, king, wking, bking, bpawn, wpawn;
	Location a1,a8,h8,h1,e4,e1,e8,e2,e7;
	
	@Test
	public void testKingLongCastle() {
		
		game.setInitialPosition();

		String beginBoard = game.getWhiteBoard();

		// System.out.println( game.getWhiteBoard() );
		// System.out.println();
		
		history.addMove(Location.d2, Location.d4);
		// System.out.println( game.getWhiteBoard() );
		// System.out.println();
		
		history.addMove(Location.e7, Location.e5);
		// System.out.println( game.getWhiteBoard() );
		// System.out.println();
		
		history.addMove(Location.c1, Location.g5);
		// System.out.println( game.getWhiteBoard() );
		// System.out.println();
		
		history.addMove(Location.g8, Location.f6);
		// System.out.println( game.getWhiteBoard() );
		// System.out.println();

		history.addMove(Location.d1, Location.d3);
		// System.out.println( game.getWhiteBoard() );
		// System.out.println();

		history.addMove(Location.b8, Location.c6);
		// System.out.println( game.getWhiteBoard() );
		// System.out.println();

		history.addMove(Location.b1, Location.c3);
		// System.out.println( game.getWhiteBoard() );
		// System.out.println();

		history.addMove(Location.f8, Location.b4);
		// System.out.println( game.getWhiteBoard() );
		// System.out.println();

		// System.out.println( "Legal moves for ["+(piece=game.getOcupant(BoardLocation.e1)).toRecord()+"]: "+piece.getLegalMoveList() );
		// System.out.println();
		assertTrue( "d2,d1,c1".equals(game.getOcupant(Location.e1).getLegalMoveList()) );

		history.addMove(Location.e1, Location.c1);
		// System.out.println( game.getWhiteBoard() );
		// System.out.println();

		//System.out.println( "Legal moves for ["+(piece=game.getOcupant(BoardLocation.c1)).toRecord()+"]: "+piece.getLegalMoveList() );
		// System.out.println();
		assertTrue( "d2,b1".equals(game.getOcupant(Location.c1).getLegalMoveList()) );

		history.addMove(Location.d7, Location.d6);
		history.addMove(Location.d8, Location.e7);
		history.addMove(Location.c8, Location.f5);
		
//		 System.out.println( "Legal moves for ["+(piece=game.getOcupant(BoardLocation.e8)).toRecord()+"]: "+piece.getLegalMoveList() );
//		 System.out.println();
//		 System.out.println( game.getWhiteBoard() );
//		 System.out.println();
		assertTrue( "d7,f8,g8,d8,c8".equals(game.getOcupant(Location.e8).getLegalMoveList()) );
		
		history.addMove(Location.e8, Location.c8);

//		 System.out.println( "Legal moves for ["+(piece=game.getOcupant(BoardLocation.c8)).toRecord()+"]: "+piece.getLegalMoveList() );
//		 System.out.println();
//		 System.out.println( game.getWhiteBoard() );
//		 System.out.println();
		assertTrue( "d7,b8".equals(game.getOcupant(Location.c8).getLegalMoveList()) );
		
		
		history.goStart();
		
		String endBoard = game.getWhiteBoard();

		// System.out.println( game.getWhiteBoard(true) );

		assertTrue( endBoard.equals(beginBoard) );
		

	}	
	
	
	@Test
	public void testKingShortCastle() {
		
		game.setInitialPosition();

		String beginBoard = game.getWhiteBoard();

		// System.out.println( game.getWhiteBoard() );
		// System.out.println();
		
		history.addMove(Location.e2, Location.e4);
		// System.out.println( game.getWhiteBoard() );
		// System.out.println();
		
		history.addMove(Location.e7, Location.e5);
		// System.out.println( game.getWhiteBoard() );
		// System.out.println();
		
		history.addMove(Location.f1, Location.b5);
		// System.out.println( game.getWhiteBoard() );
		// System.out.println();
		
		history.addMove(Location.g8, Location.f6);
		// System.out.println( game.getWhiteBoard() );
		// System.out.println();

		history.addMove(Location.g1, Location.f3);
		// System.out.println( game.getWhiteBoard() );
		// System.out.println();

		history.addMove(Location.b8, Location.c6);
		// System.out.println( game.getWhiteBoard() );
		// System.out.println();

//		 System.out.println( "Legal moves for ["+(piece=game.getOcupant(BoardLocation.e1)).toRecord()+"]: "+piece.getLegalMoveList() );
//		 System.out.println();
//		 System.out.println( game.getWhiteBoard() );
//		 System.out.println();
		assertTrue( "e2,f1,g1".equals(game.getOcupant(Location.e1).getLegalMoveList()) );

		history.addMove(Location.e1, Location.g1);

//		 System.out.println( "Legal moves for ["+(piece=game.getOcupant(BoardLocation.g1)).toRecord()+"]: "+piece.getLegalMoveList() );
//		 System.out.println();
//		 System.out.println( game.getWhiteBoard() );
//		 System.out.println();
		assertTrue( "h1".equals(game.getOcupant(Location.g1).getLegalMoveList()) );

		history.addMove(Location.f8, Location.e7);
		
//		 System.out.println( "Legal moves for ["+(piece=game.getOcupant(BoardLocation.e8)).toRecord()+"]: "+piece.getLegalMoveList() );
//		 System.out.println( game.getWhiteBoard() );
		assertTrue( "f8,g8".equals(game.getOcupant(Location.e8).getLegalMoveList()) );

		history.addMove(Location.e8, Location.g8);
		 
//		 System.out.println( "Legal moves for ["+(piece=game.getOcupant(BoardLocation.g8)).toRecord()+"]: "+piece.getLegalMoveList() );
//		 System.out.println( game.getWhiteBoard() );
		assertTrue( "h8".equals(game.getOcupant(Location.g8).getLegalMoveList()) );

		history.goStart();
		
		String endBoard = game.getWhiteBoard();

		//System.out.println( game.getWhiteBoard(true) );

		assertTrue( endBoard.equals(beginBoard) );
		

	}	
	
	
	@Test
	public void testBPawnEnPassantMove() {
		
		game.setInitialPosition();

		String beginBoard = game.getWhiteBoard();

		// System.out.println( game.getWhiteBoard() );
		// System.out.println();
		
		history.addMove(piece=game.getOcupant(Location.e2), Location.e4);
		// System.out.println( game.getWhiteBoard() );
		// System.out.println();
		
		history.addMove(piece=game.getOcupant(Location.c7), Location.c6);
		// System.out.println( game.getWhiteBoard() );
		// System.out.println();
		
		history.addMove(piece=game.getOcupant(Location.e4), Location.e5);
		// System.out.println( game.getWhiteBoard() );
		// System.out.println();

		history.addMove(piece=game.getOcupant(Location.f7), Location.f5);
		// System.out.println( game.getWhiteBoard() );

//		 System.out.println( "Legal moves for ["+(piece=game.getOcupant(BoardLocation.e5)).toRecord()+"]: "+piece.getLegalMoveList() );
//		 System.out.println();
//		 System.out.println( game.getWhiteBoard() );
//		 System.out.println();
		assertTrue( "e6,f6".equals(game.getOcupant(Location.e5).getLegalMoveList()) );

		history.addMove(piece=game.getOcupant(Location.e5), Location.f6);
		// System.out.println( game.getWhiteBoard() );
		// System.out.println();

		history.addMove(piece=game.getOcupant(Location.g7), Location.g6);
		// System.out.println( game.getWhiteBoard() );

//		 System.out.println( "Legal moves for ["+(piece=game.getOcupant(BoardLocation.f6)).toRecord()+"]: "+piece.getLegalMoveList() );
//		 System.out.println();
//		 System.out.println( game.getWhiteBoard() );
//		 System.out.println();
		assertTrue( "f7,e7".equals(game.getOcupant(Location.f6).getLegalMoveList()) );

		history.goStart();
		
		String endBoard = game.getWhiteBoard();

		// System.out.println( game.getWhiteBoard(false) );

		assertTrue( endBoard.equals(beginBoard) );
		

	}	
	
	
	
	
	@Before
	public void setUp() throws Exception {
		game = new ChessGame();
		history = new ChessHistory(game);
		rook = (ChessPiece )game.getPiecesByFace(ChessFace.Rook).get(0);
		wrook = (ChessPiece )(game.getPiecesByFace(ChessFace.Rook).get(0).getColor() == Color.white? game.getPiecesByFace(ChessFace.Rook).get(0): game.getPiecesByFace(ChessFace.Rook).get(3));
		brook = (ChessPiece )(game.getPiecesByFace(ChessFace.Rook).get(3).getColor() == Color.black? game.getPiecesByFace(ChessFace.Rook).get(3): game.getPiecesByFace(ChessFace.Rook).get(0));
		bishop = (ChessPiece )game.getPiecesByFace(ChessFace.Bishop).get(0);
		queen = (ChessPiece )game.getPiecesByFace(ChessFace.Queen).get(0);
		knight = (ChessPiece )game.getPiecesByFace(ChessFace.Knight).get(0);
		king = (ChessPiece )game.getPiecesByFace(ChessFace.King).get(0);
		wking = (ChessPiece )(game.getPiecesByFace(ChessFace.King).get(0).getColor() == Color.white? game.getPiecesByFace(ChessFace.King).get(0): game.getPiecesByFace(ChessFace.King).get(1));
		bking = (ChessPiece )(game.getPiecesByFace(ChessFace.King).get(1).getColor() == Color.black? game.getPiecesByFace(ChessFace.King).get(1): game.getPiecesByFace(ChessFace.King).get(0));
		wpawn = (ChessPiece )(game.getPiecesByFace(ChessFace.Pawn).get(0).getColor() == Color.white? game.getPiecesByFace(ChessFace.Pawn).get(0): game.getPiecesByFace(ChessFace.Pawn).get(8));
		bpawn = (ChessPiece )(game.getPiecesByFace(ChessFace.Pawn).get(8).getColor() == Color.black? game.getPiecesByFace(ChessFace.Pawn).get(8): game.getPiecesByFace(ChessFace.Pawn).get(0));
		a1 = Location.a1;
		a8 = Location.a8;
		h1 = Location.h1;
		h8 = Location.h8;
		e4 = Location.e4;
		e1 = Location.e1;
		e8 = Location.e8;
		e2 = Location.e2;
		e7 = Location.e7;
	}


	public boolean dotest(ChessPiece piece, Location moveTo, String result) {
		try {
			history.addMove(piece, moveTo);
			
			boolean doprint;
			
			if( !(doprint=result.equals(piece.getLegalMoveList())) ){
				System .out.println( game.getWhiteBoard() );
				System .out.println( "Legal moves for ["+piece.toRecord()+"]: "+piece.getLegalMoveList() );
			}
			
			return doprint;
			
		} catch( RuntimeException e){
			e.printStackTrace();
			return false;
		}
	}	
	
}