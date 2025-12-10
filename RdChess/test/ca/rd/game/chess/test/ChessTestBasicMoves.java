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

public class ChessTestBasicMoves {

	ChessHistory history;
	ChessGame game;
	ChessPiece piece, rook, wrook, brook, bishop, queen, knight, king, wking, bking, bpawn, wpawn;
	Location a1,a8,h8,h1,e4,e1,e8,e2,e7;
	
	@Test
	public void testBPawnEnPassantMove() {
		history.setPiece(piece=bpawn, Location.f4);
		history.setPiece(piece=wpawn, Location.e2);
		// System.out.println( game.getWhiteBoard() );
		history.addMove(piece=wpawn, Location.e4);
		// System.out.println( game.getWhiteBoard() );
		// System.out.println( "Legal moves for ["+bpawn.toRecord()+"]: "+bpawn.getLegalMoveList() );
		history.addMove(piece=bpawn, Location.e3);
		// System.out.println( game.getWhiteBoard() );
		// System.out.println( "Legal moves for ["+bpawn.toRecord()+"]: "+bpawn.getLegalMoveList() );
		assertTrue("e2".equals(piece.getLegalMoveList()));
	}	

	@Test
	public void testBPawnEnPassantLegal() {
		history.setPiece(piece=bpawn, Location.f4);
		history.setPiece(piece=wpawn, Location.e2);
		// System.out.println( game.getWhiteBoard() );
		history.addMove(piece=wpawn, Location.e4);
		// System.out.println( game.getWhiteBoard() );
		// System.out.println( "Legal moves for ["+bpawn.toRecord()+"]: "+bpawn.getLegalMoveList(history) );
		assertTrue("f3,e3".equals(bpawn.getLegalMoveList()));
	}	

	@Test
	public void testWPawnEnPassantLegal() {
		history.setPiece(piece=wpawn, Location.f5);
		history.setPiece(piece=bpawn, Location.e7);
//		 System.out.println( game.getWhiteBoard() );
		history.addMove(piece=bpawn, Location.e5);
//		 System.out.println( game.getWhiteBoard() );
//		 System.out.println( "Legal moves for ["+wpawn.toRecord()+"]: "+wpawn.getLegalMoveList() );
		assertTrue("f6,e6".equals(wpawn.getLegalMoveList()));
	}	

	@Test
	public void testBPawnCapture2F() {
		history.setPiece(brook, Location.d6);
		history.setPiece(wpawn, Location.f6);
		assertTrue(dotest(bpawn, e7, "e6,e5,f6"));
	}	

	@Test
	public void testBPawnCapture2() {
		history.setPiece(wrook, Location.d6);
		history.setPiece(wpawn, Location.f6);
		assertTrue(dotest(bpawn, e7, "e6,e5,f6,d6"));
	}	

	@Test
	public void testBPawnCapture1() {
		history.setPiece(wrook, Location.d6);
		assertTrue(dotest(bpawn, e7, "e6,e5,d6"));
	}	

	@Test
	public void testWPawnCapture2F() {
		history.setPiece(wrook, Location.d5);
		history.setPiece(bpawn, Location.f5);
		assertTrue(dotest(wpawn, e4, "e5,f5"));
	}	

	@Test
	public void testWPawnCapture2() {
		history.setPiece(brook, Location.d5);
		history.setPiece(bpawn, Location.f5);
		assertTrue(dotest(wpawn, e4, "e5,d5,f5"));
	}	

	@Test
	public void testWPawnCapture1() {
		history.setPiece(brook, Location.d5);
		assertTrue(dotest(wpawn, e4, "e5,d5"));
	}	

	@Test
	public void testWPawnE4() {
		assertTrue(dotest(wpawn, e4, "e5"));
	}	

	@Test
	public void testBPawnE4() {
		assertTrue(dotest(bpawn, e4, "e3"));
	}	

	@Test
	public void testBPawnE7() {
		assertTrue(dotest(bpawn, e7, "e6,e5"));
	}	

	@Test
	public void testWPawnE2() {
		assertTrue(dotest(wpawn, e2, "e3,e4"));
	}	

	@Test
	public void testKingH1() {
		assertTrue(dotest(king, h1, "g2,h2,g1"));
	}	

	@Test
	public void testKingH8() {
		assertTrue(dotest(king, h8, "g7,h7,g8"));
	}	

	@Test
	public void testKingA8() {
		assertTrue(dotest(king, a8, "b7,a7,b8"));
	}	

	@Test
	public void testKingA1() {
		assertTrue(dotest(king, a1, "b2,a2,b1"));
	}	

	@Test
	public void testKingE4() {
		assertTrue(dotest(king, e4, "f3,f5,d3,d5,e3,e5,f4,d4"));
	}	

	@Test
	public void testWKingE1() {
		assertTrue(dotest(wking, e1, "f2,d2,e2,f1,d1"));
	}	

	@Test
	public void testBKingE8() {
		assertTrue(dotest(bking, e8, "f7,d7,e7,f8,d8"));
	}	

	@Test
	public void testWKingRokirovka() {
		history.setPiece(wrook, Location.h1);
		assertTrue(dotest(wking, e1, "f2,d2,e2,f1,g1,d1"));
	}	

	@Test
	public void testBKingRokirovka() {
		history.setPiece(brook, Location.h8);
		assertTrue(dotest(bking, e8, "f7,d7,e7,f8,g8,d8"));
	}	



	
	
	@Test
	public void testKnightH1() {
		assertTrue(dotest(knight, h1, "f2,g3"));
	}	

	@Test
	public void testKnightH8() {
		assertTrue(dotest(knight, h8, "f7,g6"));
	}	

	@Test
	public void testKnightA8() {
		assertTrue(dotest(knight, a8, "b6,c7"));
	}	

	@Test
	public void testKnightA1() {
		assertTrue(dotest(knight, a1, "c2,b3"));
	}	

	@Test
	public void testKnightE4() {
		assertTrue(dotest(knight, e4, "c5,c3,d2,f2,g3,g5,f6,d6"));
	}	

	
	
	@Test
	public void testQueenH1() {
		assertTrue(dotest(queen, h1, "g2,f3,e4,d5,c6,b7,a8,h2,h3,h4,h5,h6,h7,h8,g1,f1,e1,d1,c1,b1,a1"));
	}	

	@Test
	public void testQueenH8() {
		assertTrue(dotest(queen, h8, "g7,f6,e5,d4,c3,b2,a1,h7,h6,h5,h4,h3,h2,h1,g8,f8,e8,d8,c8,b8,a8"));
	}	

	@Test
	public void testQueenA8() {
		assertTrue(dotest(queen, a8, "b7,c6,d5,e4,f3,g2,h1,a7,a6,a5,a4,a3,a2,a1,b8,c8,d8,e8,f8,g8,h8"));
	}	

	@Test
	public void testQueenA1() {
		assertTrue(dotest(queen, a1, "b2,c3,d4,e5,f6,g7,h8,a2,a3,a4,a5,a6,a7,a8,b1,c1,d1,e1,f1,g1,h1"));
	}	

	@Test
	public void testQueenE4() {
		assertTrue(dotest(queen, e4, "f3,g2,h1,f5,g6,h7,d3,c2,b1,d5,c6,b7,a8,e3,e2,e1,e5,e6,e7,e8,f4,g4,h4,d4,c4,b4,a4"));
	}	

	
	
	
	@Test
	public void testBishopH1() {
		assertTrue(dotest(bishop, h1, "g2,f3,e4,d5,c6,b7,a8"));
	}	

	@Test
	public void testBishopH8() {
		assertTrue(dotest(bishop, h8, "g7,f6,e5,d4,c3,b2,a1"));
	}	

	@Test
	public void testBishopA8() {
		assertTrue(dotest(bishop, a8, "b7,c6,d5,e4,f3,g2,h1"));
	}	

	@Test
	public void testBishopA1() {
		assertTrue(dotest(bishop, a1, "b2,c3,d4,e5,f6,g7,h8"));
	}	

	@Test
	public void testBishopE4() {
		assertTrue(dotest(bishop, e4, "f3,g2,h1,f5,g6,h7,d3,c2,b1,d5,c6,b7,a8"));
	}	

	
	
	
	@Test
	public void testRookE4() {
		assertTrue(dotest(rook, e4, "e3,e2,e1,e5,e6,e7,e8,f4,g4,h4,d4,c4,b4,a4"));
	}	
	
	@Test
	public void testRookH1() {
		assertTrue(dotest(rook, h1, "h2,h3,h4,h5,h6,h7,h8,g1,f1,e1,d1,c1,b1,a1"));
	}	
	
	@Test
	public void testRookH8() {
		assertTrue(dotest(rook, h8, "h7,h6,h5,h4,h3,h2,h1,g8,f8,e8,d8,c8,b8,a8"));
	}	
	
	@Test
	public void testRookA8() {
		assertTrue(dotest(rook, a8, "a7,a6,a5,a4,a3,a2,a1,b8,c8,d8,e8,f8,g8,h8"));
	}	
	
	@Test
	public void testRookA1() {
		assertTrue(dotest(rook, a1, "a2,a3,a4,a5,a6,a7,a8,b1,c1,d1,e1,f1,g1,h1"));
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
			history.setPiece(piece, moveTo);
			
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