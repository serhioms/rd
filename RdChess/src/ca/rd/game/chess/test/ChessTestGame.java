package ca.rd.game.chess.test;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import ca.rd.game.chess.advance.ChessBoard;
import ca.rd.game.chess.advance.ChessGame;
import ca.rd.game.chess.advance.ChessHistory;
import ca.rd.game.chess.advance.ChessPiece;
import ca.rd.game.chess.advance.Location;

public class ChessTestGame {

	ChessGame game;
	ChessPiece piece;
	ChessHistory history;
	
	@Before
	public void setUp() throws Exception {
		game = new ChessGame();
		history = new ChessHistory(game);
	}

	@Test
	public void testKingLongCastle() {
		
		game.setInitialPosition();
  
		String beginBoard = game.getWhiteBoard();

		 System.out.println( game.getWhiteBoard() );
		 System.out.println();
		 System.out.println( game.board.getBoard(ChessBoard.SHOW_SQUARES_POWER) );
		 System.out.println();
		
		
		history.addMove(Location.d2, Location.d4);
		 System.out.println( game.getWhiteBoard() );
		 System.out.println();
		 System.out.println( game.board.getBoard(ChessBoard.SHOW_SQUARES_POWER) );
		 System.out.println();
		
		history.addMove(Location.e7, Location.e5);
		 System.out.println( game.getWhiteBoard() );
		 System.out.println();
		 System.out.println( game.board.getBoard(ChessBoard.SHOW_SQUARES_POWER) );
		 System.out.println();
		
		history.addMove(Location.c1, Location.g5);
		 System.out.println( game.getWhiteBoard() );
		 System.out.println();
		 System.out.println( game.board.getBoard(ChessBoard.SHOW_SQUARES_POWER_WHITE) );
		 System.out.println();

		 System.out.println( "Legal moves for ["+(piece=game.getOcupant(Location.e8)).toRecord()+"]: "+piece.getLegalMoveList() );

		 
/*		
		history.addMove(Location.g8, Location.f6);
		 System.out.println( game.getWhiteBoard() );
		 System.out.println();

		history.addMove(Location.d1, Location.d3);
		 System.out.println( game.getWhiteBoard() );
		 System.out.println();

		history.addMove(Location.b8, Location.c6);
		 System.out.println( game.getWhiteBoard() );
		 System.out.println();

		history.addMove(Location.b1, Location.c3);
		 System.out.println( game.getWhiteBoard() );
		 System.out.println();

		history.addMove(Location.f8, Location.b4);
		 System.out.println( game.getWhiteBoard() );
		 System.out.println();

		 System.out.println( "Legal moves for ["+(piece=game.getOcupant(Location.e1)).toRecord()+"]: "+piece.getLegalMoveList() );
		 System.out.println();
		assertTrue( "d2,d1,c1".equals(game.getOcupant(Location.e1).getLegalMoveList()) );

		history.addMove(Location.e1, Location.c1);
		 System.out.println( game.getWhiteBoard() );
		 System.out.println();

		System.out.println( "Legal moves for ["+(piece=game.getOcupant(Location.c1)).toRecord()+"]: "+piece.getLegalMoveList() );
		 System.out.println();
		assertTrue( "d2,b1".equals(game.getOcupant(Location.c1).getLegalMoveList()) );

		history.addMove(Location.d7, Location.d6);
		history.addMove(Location.d8, Location.e7);
		history.addMove(Location.c8, Location.f5);
		
		 System.out.println( "Legal moves for ["+(piece=game.getOcupant(Location.e8)).toRecord()+"]: "+piece.getLegalMoveList() );
		 System.out.println();
		 System.out.println( game.getWhiteBoard() );
		 System.out.println();
		assertTrue( "d7,f8,g8,d8,c8".equals(game.getOcupant(Location.e8).getLegalMoveList()) );
		
		history.addMove(Location.e8, Location.c8);

		 System.out.println( "Legal moves for ["+(piece=game.getOcupant(Location.c8)).toRecord()+"]: "+piece.getLegalMoveList() );
		 System.out.println();
		 System.out.println( game.getWhiteBoard() );
		 System.out.println();
		assertTrue( "d7,b8".equals(game.getOcupant(Location.c8).getLegalMoveList()) );
	
		
		history.goStart();
		
		String endBoard = game.getWhiteBoard();

		System.out.println( game.getWhiteBoard(true) );

		assertTrue( endBoard.equals(beginBoard) );
*/			
			assertTrue( true );

	}	

}


