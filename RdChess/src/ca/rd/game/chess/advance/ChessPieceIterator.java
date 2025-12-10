package ca.rd.game.chess.advance;

import java.util.Iterator;

import ca.rd.game.chess.simple.ChessGameSimple.ChessColor;
import ca.rd.game.chess.simple.ChessGameSimple.ChessFace;

public class ChessPieceIterator extends ChessPieceLegal {

	private MoveIterator all, move, power; 
	
	public ChessPieceIterator(ChessFace face, ChessColor color) {
		super(face, color);
	}

	final public Iterator<Location> iteratorAll(){
		if( legalMovesValid4size != game.getHistory().getSize() )
			populateLegalMoves();

		if( all == null ){
			all = new MoveIterator(ChessBoard.BOTH);
		}
		return all.start();
	}
	
	final public Iterator<Location> iteratorMove(){
		if( legalMovesValid4size != game.getHistory().getSize() )
			populateLegalMoves();

		if( move == null ){
			move = new MoveIterator(ChessBoard.MOVE);
		}
		return move.start();
	}
	
	final public Iterator<Location> iteratorPower(){
		if( legalMovesValid4size != game.getHistory().getSize() )
			populateLegalMoves();

		if( power == null ){
			power = new MoveIterator(ChessBoard.POWER);
		}
		return power.start();
	}
	
	public class MoveIterator implements Iterator<Location> {

		/*
		 * Array iterator implementation
		 */
		private int i,j;
		private int what;
		private Location next;
		
		public MoveIterator(int what) {
			this.what = what;
		}

		public MoveIterator start() {
			i = j = 0;
			switch( what ){
			case ChessBoard.BOTH:
				findNextAll();
				break;
			case ChessBoard.POWER:
				findNextPower();
				break;
			case ChessBoard.MOVE:
				findNextMove();
				break;
			}
			return this;
		}

		@Override
		public boolean hasNext() {
			return next != null;
		}
	
		@Override
		public Location next() {
			Location current = next;
			if( current != null )
				switch( what ){
				case ChessBoard.BOTH:
					findNextAll();
					break;
				case ChessBoard.POWER:
					findNextPower();
					break;
				case ChessBoard.MOVE:
					findNextMove();
					break;
				}
			return current;
		}
	
		@Override
		public void remove() { 
			throw new RuntimeException("Remove method is not implemented for ChessPieceIterator.");
		}
		
		final private void findNextMove() {
			for(next = null; (next == null) && (i >=0 && i < legalMove.length && legalMove[i] != null )&&( j >=0 && j < legalMove[i].length); ){
				next = legalMove[i][j];
				if( next != null ){
					if( ++j == legalMove[i].length ){
						j = 0;
						i++;
					}
				} else {
					j = 0;
					i++;
				}
			}
		}

		final private void findNextPower() {
			for(next = null; (next == null) && (i >=0 && i < legalPower.length && legalPower[i] != null )&&( j >=0 && j < legalPower[i].length); ){
				next = legalPower[i][j];
				if( next != null ){
					if( ++j == legalPower[i].length ){
						j = 0;
						i++;
					}
				} else {
					j = 0;
					i++;
				}
			}
		}

		final private void findNextAll() {
			for(next = null; (next == null) && (i >=0 && i < legalLocation.length && legalLocation[i] != null )&&( j >=0 && j < legalLocation[i].length); ){
				next = legalLocation[i][j];
				if( next != null ){
					if( ++j == legalLocation[i].length ){
						j = 0;
						i++;
					}
				} else {
					j = 0;
					i++;
				}
			}
		}
	}

}
