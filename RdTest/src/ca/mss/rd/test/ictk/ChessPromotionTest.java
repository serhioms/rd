/*
 *  ICTK - Internet Chess ToolKit
 *  More information is available at http://ictk.sourceforge.net
 *  Copyright (C) 2002 J. Varsoke <jvarsoke@ghostmanonfirst.com>
 *  All rights reserved.
 *
 *  $Id: ChessMoveTest.java,v 1.2 2003/07/15 01:19:23 jvarsoke Exp $
 *
 *  This file is part of ICTK.
 *
 *  ICTK is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *
 *  ICTK is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with ICTK; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package ca.mss.rd.test.ictk;

import ictk.boardgame.IllegalMoveException;
import ictk.boardgame.chess.ChessBoard;
import ictk.boardgame.chess.ChessGame;
import ictk.boardgame.chess.ChessMove;
import ictk.boardgame.chess.ChessResult;
import ictk.boardgame.chess.io.SAN;
import ictk.util.Log;
import junit.framework.TestCase;

public class ChessPromotionTest extends TestCase {
   ChessGame game;
   ChessBoard board, board2;
   ChessResult res;
   ChessMove   move;

	private static SAN STANDARD_ALGEBRAIC_NOTATION = new SAN();

   public ChessPromotionTest (String name) {
      super(name);
      //Log.addMask(ChessMove.DEBUG);
      //Log.addMask(ChessBoard.DEBUG);
   }

   public void setUp () {
      board = new ChessBoard();
	  game = new ChessGame();
   }

   public void tearDown () {
      board = null;
      board2 = null;
      move = null;
      game = null;
      Log.removeMask(ChessMove.DEBUG);
      Log.removeMask(ChessBoard.DEBUG);
   }

   //////////////////////////////////////////////////////////////////////
   //////////////////////////////////////////////////////////////////////
   public void testPromotion () throws IllegalMoveException {
	      //Log.addMask(ChessMove.DEBUG);
	      //Log.addMask(ChessBoard.DEBUG);
		      char[][] position1=
		      		  {{'R',' ','P',' ',' ',' ','P',' '},
	                  {'N','P',' ',' ',' ',' ','p','n'},
	                  {'B','P',' ',' ',' ',' ','p','b'},
	                  {'Q','P',' ',' ',' ',' ','p','q'},
	                  {'K','P',' ',' ',' ',' ','p','k'},
	                  {'B','P',' ',' ',' ',' ','p','b'},
	                  {'N','P',' ',' ',' ',' ','p','n'},
	                  {'R','P',' ',' ',' ',' ','p','r'}};
		      char[][] position2=
		      		  {{'R',' ','P',' ',' ',' ',' ','Q'},
	                  {'N','P',' ',' ',' ',' ','p','n'},
	                  {'B','P',' ',' ',' ',' ','p','b'},
	                  {'Q','P',' ',' ',' ',' ','p','q'},
	                  {'K','P',' ',' ',' ',' ','p','k'},
	                  {'B','P',' ',' ',' ',' ','p','b'},
	                  {'N','P',' ',' ',' ',' ','p','n'},
	                  {'R','P',' ',' ',' ',' ','p','r'}};


	      board = new ChessBoard(position1);
		  game.setBoard(board);
		  try {
			  game.getHistory().add(new ChessMove(board, 1, 7, 1, 8)); //a7-a8
			  game.getHistory().removeLastMove();
			  game.getHistory().add(new ChessMove(board, 1, 7, 1, 8)); //a7-a8
		  }catch( Exception e){
			  e.printStackTrace();
		      assertFalse(e.getMessage(), true);
		  }
	      board2 = new ChessBoard(position2);
	      board2.setBlackMove(true);
	      //System.err.println("b1: " + board.getEnPassantFile()
	      //   + " b2: " + board2.getEnPassantFile());
	      assertTrue(board.equals(board2));
	   }

   public void testPromotionWithStrike () throws IllegalMoveException {
	      //Log.addMask(ChessMove.DEBUG);
	      //Log.addMask(ChessBoard.DEBUG);
		      char[][] position1=
		      		  {{'R',' ','P',' ',' ',' ','P',' '},
	                  {'N','P',' ',' ',' ',' ','p','n'},
	                  {'B','P',' ',' ',' ',' ','p','b'},
	                  {'Q','P',' ',' ',' ',' ','p','q'},
	                  {'K','P',' ',' ',' ',' ','p','k'},
	                  {'B','P',' ',' ',' ',' ','p','b'},
	                  {'N','P',' ',' ',' ',' ','p','n'},
	                  {'R','P',' ',' ',' ',' ','p','r'}};
		      char[][] position2=
		      		  {{'R',' ','P',' ',' ',' ',' ',' '},
	                  {'N','P',' ',' ',' ',' ','p','Q'},
	                  {'B','P',' ',' ',' ',' ','p','b'},
	                  {'Q','P',' ',' ',' ',' ','p','q'},
	                  {'K','P',' ',' ',' ',' ','p','k'},
	                  {'B','P',' ',' ',' ',' ','p','b'},
	                  {'N','P',' ',' ',' ',' ','p','n'},
	                  {'R','P',' ',' ',' ',' ','p','r'}};


	      board = new ChessBoard(position1);
		  game.setBoard(board);
		  try {
			  game.getHistory().add(new ChessMove(board, 1, 7, 2, 8)); //a7xb8
		  }catch( Exception e){
			  e.printStackTrace();
		      assertFalse(e.getMessage(), true);
		  }
	      board2 = new ChessBoard(position2);
	      board2.setBlackMove(true);
	      //System.err.println("b1: " + board.getEnPassantFile()
	      //   + " b2: " + board2.getEnPassantFile());
	      assertTrue(board.equals(board2));
	      
	   }

   public void testPromotionWithStrikeReverse () throws IllegalMoveException {
	      //Log.addMask(ChessMove.DEBUG);
	      //Log.addMask(ChessBoard.DEBUG);
		      char[][] position1=
		      		  {{'R',' ','P',' ',' ',' ','P',' '},
	                  {'N','P',' ',' ',' ',' ','p','n'},
	                  {'B','P',' ',' ',' ',' ','p','b'},
	                  {'Q','P',' ',' ',' ',' ','p','q'},
	                  {'K','P',' ',' ',' ',' ','p','k'},
	                  {'B','P',' ',' ',' ',' ','p','b'},
	                  {'N','P',' ',' ',' ',' ','p','n'},
	                  {'R','P',' ',' ',' ',' ','p','r'}};


	      board = new ChessBoard(position1);
		  game.setBoard(board);
		  try {
			  game.getHistory().add(new ChessMove(board, 1, 7, 2, 8)); //a7xb8
			  game.getHistory().removeLastMove();
		  }catch( Exception e){
			  e.printStackTrace();
		      assertFalse(e.getMessage(), true);
		  }
	      board2 = new ChessBoard(position1);
	      board2.setBlackMove(false);
	      //System.err.println("b1: " + board.getEnPassantFile()
	      //   + " b2: " + board2.getEnPassantFile());
	      assertTrue(board.equals(board2));
	      
	      
	   }
   
   public void testPromotionWithStrikeReverse2 () throws IllegalMoveException {
	      //Log.addMask(ChessMove.DEBUG);
	      //Log.addMask(ChessBoard.DEBUG);
		      char[][] position1=
		      		  {{'R',' ','P',' ',' ',' ','P',' '},
	                  {'N','P',' ',' ',' ',' ','p','n'},
	                  {'B','P',' ',' ',' ',' ','p','b'},
	                  {'Q','P',' ',' ',' ',' ','p','q'},
	                  {'K','P',' ',' ',' ',' ','p','k'},
	                  {'B','P',' ',' ',' ',' ','p','b'},
	                  {'N','P',' ',' ',' ',' ','p','n'},
	                  {'R','P',' ',' ',' ',' ','p','r'}};
		      char[][] position2=
		      		  {{'R',' ','P',' ',' ',' ',' ',' '},
	                  {'N','P',' ',' ',' ',' ','p','Q'},
	                  {'B','P',' ',' ',' ',' ','p','b'},
	                  {'Q','P',' ',' ',' ',' ','p','q'},
	                  {'K','P',' ',' ',' ',' ','p','k'},
	                  {'B','P',' ',' ',' ',' ','p','b'},
	                  {'N','P',' ',' ',' ',' ','p','n'},
	                  {'R','P',' ',' ',' ',' ','p','r'}};


	      board = new ChessBoard(position1);
		  game.setBoard(board);
		  try {
			  game.getHistory().add(new ChessMove(board, 1, 7, 2, 8)); //a7xb8
			  game.getHistory().removeLastMove();
			  game.getHistory().add(new ChessMove(board, 1, 7, 2, 8)); //a7xb8
			  game.getHistory().removeLastMove();
			  game.getHistory().add(new ChessMove(board, 1, 7, 2, 8)); //a7xb8
			  game.getHistory().removeLastMove();
			  game.getHistory().add(new ChessMove(board, 1, 7, 2, 8)); //a7xb8
			  game.getHistory().removeLastMove();
			  game.getHistory().add(new ChessMove(board, 1, 7, 2, 8)); //a7xb8
			  game.getHistory().removeLastMove();
			  game.getHistory().add(new ChessMove(board, 1, 7, 2, 8)); //a7xb8
			  game.getHistory().removeLastMove();
			  game.getHistory().add(new ChessMove(board, 1, 7, 2, 8)); //a7xb8
			  game.getHistory().removeLastMove();
			  game.getHistory().add(new ChessMove(board, 1, 7, 2, 8)); //a7xb8
			  game.getHistory().removeLastMove();
			  game.getHistory().add(new ChessMove(board, 1, 7, 2, 8)); //a7xb8
			  game.getHistory().removeLastMove();
			  game.getHistory().add(new ChessMove(board, 1, 7, 2, 8)); //a7xb8
			  game.getHistory().removeLastMove();
			  game.getHistory().add(new ChessMove(board, 1, 7, 2, 8)); //a7xb8
		  }catch( Exception e){
			  e.printStackTrace();
		      assertFalse(e.getMessage(), true);
		  }
	      board2 = new ChessBoard(position2);
	      board2.setBlackMove(true);
	      //System.err.println("b1: " + board.getEnPassantFile()
	      //   + " b2: " + board2.getEnPassantFile());

	      //System.out.println("\n"+board.toString());
	      //System.out.println("\n"+board2.toString());

	      assertTrue(board.equals(board2));
	      
	      
	   }

   public void testPromotionWithStrikeReverse3 () throws IllegalMoveException {
	      //Log.addMask(ChessMove.DEBUG);
	      //Log.addMask(ChessBoard.DEBUG);
	   /*
	   8   #   # r # b #   
	   7     # N # p #   # 
	   6   p   #   # k p   
	   5   P # p #   #   # 
	   4   #   #   P   p   
	   3     #   N   #   # 
	   2   #   # K p P # R 
	   1     #   #   b   #
	       A B C D E F G H
	   */
	   			char[][] position1=
		      		  {{'R',' ','P',' ',' ',' ','P',' '},
	                  {'N','P',' ',' ',' ',' ','p','n'},
	                  {'B','P',' ',' ',' ',' ','p','b'},
	                  {'Q','P',' ',' ',' ',' ','p','q'},
	                  {'K','P',' ',' ',' ',' ','p','k'},
	                  {'B','P',' ',' ',' ',' ','p','b'},
	                  {'N','P',' ',' ',' ',' ','p','n'},
	                  {'R','P',' ',' ',' ',' ','p','r'}};
		      char[][] position2=
		      		  {{'R',' ','P',' ',' ',' ',' ',' '},
	                  {'N','P',' ',' ',' ',' ','p','B'},
	                  {'B','P',' ',' ',' ',' ','p','b'},
	                  {'Q','P',' ',' ',' ',' ','p','q'},
	                  {'K','P',' ',' ',' ',' ','p','k'},
	                  {'B','P',' ',' ',' ',' ','p','b'},
	                  {'N','P',' ',' ',' ',' ','p','n'},
	                  {'R','P',' ',' ',' ',' ','p','r'}};


	      board = new ChessBoard(position1);
		  game.setBoard(board);
		  try {
			  game.getHistory().add((ChessMove) STANDARD_ALGEBRAIC_NOTATION.stringToMove(board, "a7xb8=B")); //a7xb8
			  game.getHistory().removeLastMove();
			  game.getHistory().add((ChessMove) STANDARD_ALGEBRAIC_NOTATION.stringToMove(board, "a7xb8=B")); //a7xb8
			  game.getHistory().removeLastMove();
			  game.getHistory().add((ChessMove) STANDARD_ALGEBRAIC_NOTATION.stringToMove(board, "a7xb8=B")); //a7xb8
			  game.getHistory().removeLastMove();
			  game.getHistory().add((ChessMove) STANDARD_ALGEBRAIC_NOTATION.stringToMove(board, "a7xb8=B")); //a7xb8
			  game.getHistory().removeLastMove();
			  game.getHistory().add((ChessMove) STANDARD_ALGEBRAIC_NOTATION.stringToMove(board, "a7xb8=B")); //a7xb8
			  game.getHistory().removeLastMove();
			  game.getHistory().add((ChessMove) STANDARD_ALGEBRAIC_NOTATION.stringToMove(board, "a7xb8=B")); //a7xb8
			  game.getHistory().removeLastMove();
			  game.getHistory().add((ChessMove) STANDARD_ALGEBRAIC_NOTATION.stringToMove(board, "a7xb8=B")); //a7xb8
			  game.getHistory().removeLastMove();
			  game.getHistory().add((ChessMove) STANDARD_ALGEBRAIC_NOTATION.stringToMove(board, "a7xb8=B")); //a7xb8
			  game.getHistory().removeLastMove();
			  game.getHistory().add((ChessMove) STANDARD_ALGEBRAIC_NOTATION.stringToMove(board, "a7xb8=B")); //a7xb8
			  game.getHistory().removeLastMove();
			  game.getHistory().add((ChessMove) STANDARD_ALGEBRAIC_NOTATION.stringToMove(board, "a7xb8=B")); //a7xb8
			  game.getHistory().removeLastMove();
			  game.getHistory().add((ChessMove) STANDARD_ALGEBRAIC_NOTATION.stringToMove(board, "a7xb8=B")); //a7xb8
		  }catch( Exception e){
			  e.printStackTrace();
		      assertFalse(e.getMessage(), true);
		  }
	      board2 = new ChessBoard(position2);
	      board2.setBlackMove(true);
	      //System.err.println("b1: " + board.getEnPassantFile()
	      //   + " b2: " + board2.getEnPassantFile());

	      //System.out.println("\n"+board.toString());
	      //System.out.println("\n"+board2.toString());

	      assertTrue(board.equals(board2));
	      
	      
	   }
}
