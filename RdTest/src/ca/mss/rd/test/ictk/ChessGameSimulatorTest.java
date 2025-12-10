/*
 *  ICTK - Internet ChessICTK ToolKit
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


import ictk.boardgame.chess.ChessGameInfo;
import ictk.boardgame.chess.ChessMove;

import java.io.File;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ca.mss.rd.chessictk.game.ChessICTK;

public class ChessGameSimulatorTest {

	String movestr;
	ChessICTK chess;

	private static Random random = new Random();

	long start;
	int total;
	long finish;
	long time;
	double rate;
	long exceptions;
	
	@Before
	public void setUp() throws Exception {
		start = System.currentTimeMillis();
		total = 0;
		exceptions = 0;
	}

	@After
	public void tearDown()throws Exception {
		finish = System.currentTimeMillis();
		time = (finish - start);
		rate = (time*1.0)/(total*1.0);
		
		System.out.println("[total="+total+"][time="+time+"][rate="+rate+"][exceptions="+exceptions+"]");
	}


	// ////////////////////////////////////////////////////////////////////

	// [total=141072][time=16515][rate=0.11706788023137121]
	// [total=139773][time=16171][rate=0.11569473360377183]
	// [total=141357][time=16514][rate=0.11682477698309952]
	// Bug fixed
	// [total=142298][time=16718][rate=0.11748583957610086][exceptions=0]
	@Test
	public void testSimulator1(){

		for(int i=0; i<1000; i++ ){
			chess = new ChessICTK();
		   	ChessGameInfo info = new ChessGameInfo();
			info.setAuxilleryProperties(new Properties());
			chess.setGameInfo(info);
			//chess.goToEnd();
	
			while ( chess.getCapturedPiecesAmount() < 28 && !chess.isCheckmate() && !chess.isStalemate()) {
				List<ChessMove> legalMoves = chess.getLegalMoves();
				int moveCount = legalMoves.size();
				
				if (moveCount > 0) {
	
					int moveIndex = random.nextInt(moveCount);
					ChessMove move = legalMoves.get(moveIndex);
	
					chess.addMove2History(move);
	
				} else
					break;
			}
	
			//System.out.println("[scores="+getScores()+"][captured="+chess.getCapturedPiecesAmount()+"][moves="+chess.getCurrentMoveNumber()+"] \n"+chess.getBoard().toString());
			total += chess.getCurrentMoveNumber();
		}
	}

	
	// 4 times slower with SAN conversion (prev() or removeLastMove() does not matter)
	// [total=88746][time=41029][rate=0.46231942848128366][exceptions=0]
	// [total=86928][time=40217][rate=0.4626472482974416][exceptions=0]
	// Bug fixed
	// [total=141722][time=65107][rate=0.4593993875333399][exceptions=0]
	// [total=141871][time=64685][rate=0.45594237018136197][exceptions=0]
	@Test
	public void testSimulator3(){

		for(int i=0; i<1000; i++ ){
			chess = new ChessICTK();
		   	ChessGameInfo info = new ChessGameInfo();
			info.setAuxilleryProperties(new Properties());
			chess.setGameInfo(info);
			//chess.goToEnd();
	
			try {
				while ( chess.getCapturedPiecesAmount() < 28 && !chess.isCheckmate() && !chess.isStalemate()) {
					List<ChessMove> legalMoves = chess.getLegalMoves();
					int moveCount = legalMoves.size();
					
					if (moveCount > 0) {
		
						int moveIndex = random.nextInt(moveCount);
						ChessMove move = legalMoves.get(moveIndex);
		
						chess.addMove2History(move);
						String pgn = chess.moveToString(move);
						chess.removeMove();
						//chess.retractMove();

						ChessMove move2 = ((ChessMove) chess.stringToMove(pgn));
						chess.addMove2History(move2);
					} else
						break;
				}
			} catch(Exception e){
				exceptions++;
			}
			//System.out.println("[scores="+getScores()+"][captured="+chess.getCapturedPiecesAmount()+"][moves="+chess.getCurrentMoveNumber()+"] \n"+chess.getBoard().toString());
			total += chess.getCurrentMoveNumber();
		}
	}

	// 2 times slower without SAN conversion
	// [total=141044][time=35155][rate=0.24924846147301552][exceptions=0]
	// [total=138594][time=34467][rate=0.2486904194986796][exceptions=4]
	// [total=138483][time=34483][rate=0.24900529306846328][exceptions=1]
	// Bug fixed !!!
	// [total=143612][time=35998][rate=0.25066150460964265][exceptions=0]
	@Test
	public void testSimulator4(){

		for(int i=0; i<1000; i++ ){
			chess = new ChessICTK();
		   	ChessGameInfo info = new ChessGameInfo();
			info.setAuxilleryProperties(new Properties());
			chess.setGameInfo(info);
			//chess.goToEnd();
	
			try {
				while ( chess.getCapturedPiecesAmount() < 28 && !chess.isCheckmate() && !chess.isStalemate()) {
					List<ChessMove> legalMoves = chess.getLegalMoves();
					int moveCount = legalMoves.size();
					
					if (moveCount > 0) {
		
						int moveIndex = random.nextInt(moveCount);
						ChessMove move = legalMoves.get(moveIndex);
		
						chess.addMove2History(move);
						chess.retractMove();
						chess.addMove2History(move);
						
					} else
						break;
				}
			} catch(Exception e){
				exceptions++;
			}
			//System.out.println("[scores="+getScores()+"][captured="+chess.getCapturedPiecesAmount()+"][moves="+chess.getCurrentMoveNumber()+"] \n"+chess.getBoard().toString());
			total += chess.getCurrentMoveNumber();
		}
	}
	
	// 400 times slower with save/read cycle 
	// [total=220][time=8952][rate=40.69090909090909][exceptions=0]
	// [total=377][time=16593][rate=44.0132625994695][exceptions=0]
	// [total=362][time=11687][rate=32.28453038674033][exceptions=0]
	// [total=295][time=7094][rate=24.047457627118643][exceptions=0]
	// [total=707][time=20483][rate=28.97171145685997][exceptions=0]
	// [total=1213][time=54342][rate=44.79967023907667][exceptions=0]
	@Test
	public void testSimulator5(){

		for(int i=0; i<100; i++ ){
			chess = new ChessICTK();
		   	ChessGameInfo info = new ChessGameInfo();
			info.setAuxilleryProperties(new Properties());
			chess.setGameInfo(info);
			//chess.goToEnd();
	
			chess.serializeToPgn();
			
			try {
				while ( chess.getCapturedPiecesAmount() < 28 && !chess.isCheckmate() && !chess.isStalemate()) {
					
					chess.deserializeFromPGN();
					
					List<ChessMove> legalMoves = chess.getLegalMoves();
					int moveCount = legalMoves.size();
					
					if (moveCount > 0) {
		
						int moveIndex = random.nextInt(moveCount);
						ChessMove move = legalMoves.get(moveIndex);
						//System.out.println("move = "+move);
		
						chess.addMove2History(move);
						String pgn = chess.moveToString(move);
						chess.removeMove();
		
						ChessMove move2 = ((ChessMove) chess.stringToMove(pgn));
						chess.addMove2History(move2);
						
						chess.serializeToPgn();
						chess.goToEnd();
					} else
						break;
				}
			} catch(Exception e){
				exceptions++;
			}
			//System.out.println("[scores="+getScores()+"][captured="+chess.getCapturedPiecesAmount()+"][moves="+chess.getCurrentMoveNumber()+"] \n"+chess.getBoard().toString());
			total += chess.getCurrentMoveNumber();
		}
	}
	
	// 800 times slower with save/read cycle
	// [total=106][time=9796][rate=92.41509433962264][exceptions=0]
	@Test
	public void testSimulator6(){

		for(int i=0; i<10; i++ ){
			chess = new ChessICTK();
		   	ChessGameInfo info = new ChessGameInfo();
			info.setAuxilleryProperties(new Properties());
			chess.setGameInfo(info);
			//chess.goToEnd();
	
			chess.serializeToPgn();
			
			try {
				while ( chess.getCapturedPiecesAmount() < 28 && !chess.isCheckmate() && !chess.isStalemate()) {
					
					List<ChessMove> legalMoves = chess.getLegalMoves();
					int moveCount = legalMoves.size();
					
					if (moveCount > 0) {
		
						int moveIndex = random.nextInt(moveCount);
						ChessMove move = legalMoves.get(moveIndex);
		
						chess.addMove2History(move);
						
						chess.serializeToPgn();
						chess.deserializeFromPGN();
						chess.goToEnd();

					} else
						break;
				}
			} catch(Exception e){
				exceptions++;
			}
			//System.out.println("[scores="+getScores()+"][captured="+chess.getCapturedPiecesAmount()+"][moves="+chess.getCurrentMoveNumber()+"] \n"+chess.getBoard().toString());
			total += chess.getCurrentMoveNumber();
		}
	}	
	
	
	@SuppressWarnings("unused")
	@Test
	public void testSimulatorCatchATruble() throws Exception {

		ChessMove move = null;
		
		int i=0;
		for(i=0; i<1000; i++ ){
			chess = new ChessICTK();
	
			try {
				while ( chess.getCapturedPiecesAmount() < 28 && !chess.isCheckmate() && !chess.isStalemate()) {
					List<ChessMove> legalMoves = chess.getLegalMoves();
					int moveCount = legalMoves.size();
					
					if (moveCount > 0) {
		
						int moveIndex = random.nextInt(moveCount);

						move = legalMoves.get(moveIndex);

						chess.addMove2History(move);
						movestr = chess.moveToString(move);

						try {
							if( false ){
								chess.retractMove();
								chess.addMove2History(move);
							} else {
								chess.removeMove();
								chess.addMove2History(((ChessMove) chess.stringToMove(movestr)));
							}
						} catch(Exception e){
							throw new RuntimeException("Second add: "+movestr, e);
						}
					} else
						break;
				}
			} catch(RuntimeException e){
				e.printStackTrace();
				break;
			} catch(Exception e){
				e.printStackTrace();
				break;
			}
			total += chess.getCurrentMoveNumber();
		}

		chess.serializeToPgn();
		System.out.println("\nGame #"+i);
		System.out.println(chess.getPgnGame());
		FileUtils.writeStringToFile(new File("chess_game.pgn"), chess.getPgnGame());
		
	}
	
	
	@SuppressWarnings("unused")
	@Test
	public void testSimulatorRepeatATruble()throws Exception {

		ChessICTK chess2 = new ChessICTK();
		chess2.setPgnGame(FileUtils.readFileToString(new File("chess_game.pgn")));
		chess2.deserializeFromPGN();

		chess2.goToEnd();
		int max=chess2.getCurrentMoveNumber()-1;
		
		for(int c=10; c>0; c--){
			chess = new ChessICTK();
			
			try {
				chess2.retractAll();
				for(int i=1; i<=max; i++ ) {
					ChessMove move2 = (ChessMove )chess2.goToNext();
					{
						String move2str = chess.moveToString(move2);

						//RdChessMove move = ((RdChessMove) chess.stringToMove(chess.getBoard(), move2str));
						ChessMove move = null;

						List<ChessMove> legalMoves = chess.getLegalMoves();
						for(int j=0; j<legalMoves.size(); j++){
							move = legalMoves.get(j);
							
							chess.addMove2History(move);
							String move3str = chess.moveToString(move);
							chess.retractMove();
							
							if( move2str.equals(move3str) )
								break;

						}
						if( move == null )
							throw new RuntimeException("Can not find move ["+move2str+"] in the lilst "+legalMoves);
						
						chess.addMove2History(move);
						movestr = chess.moveToString(move);

						if( false ){
							System.out.println("Move ["+i+"/"+max+"]: "+chess.getIdent(move.getChessPiece())+chess.getLocation(move.getOrigin())+" "+chess.moveToString(move));
							System.out.println(chess.toString());
							System.out.println("\n");
						}
						
						try {
							if( false ){
								chess.retractMove();
								chess.addMove2History(move);
							} else {
								chess.removeMove();
								chess.addMove2History(((ChessMove) chess.stringToMove(movestr)));
							}
						} catch(Exception e){
							throw new RuntimeException("Second add: "+movestr, e);
						}
					}
				}
			} catch(Exception e){
				System.out.println("java.lang.RuntimeException: Second add: hxg8=R");
				e.printStackTrace();
				break;
			}
			total += chess.getCurrentMoveNumber();
		}
	}
	
	
}














