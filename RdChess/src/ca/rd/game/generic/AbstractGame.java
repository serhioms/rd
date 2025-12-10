package ca.rd.game.generic;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

abstract public class AbstractGame implements Game {

	final public List<Piece> game;
	
	public AbstractGame() {
		this.game = createGame();
	}

	@Override
	abstract public List<Piece> createGame();

	@Override
	public List<Piece> getGame(){
		return game;
	}

	@Override
	public int getRank(Face face) {
		return face.getRank();
	}

	@Override
	final public List<Piece> getPiecesByFace(Face face) {
		List<Piece> pieces = new ArrayList<Piece>();
		for(Iterator<Piece> iter=getGame().iterator(); iter.hasNext(); ){
			Piece piece = iter.next();
			if( piece.getFace() == face )
				pieces.add(piece);
		}
		return pieces;
	}

	@Override
	final public List<Piece> getPiecesByColor(Color color) {
		List<Piece> pieces = new ArrayList<Piece>();
		for(Iterator<Piece> iter=getGame().iterator(); iter.hasNext(); ){
			Piece piece = iter.next();
			if( piece.getColor() == color )
				pieces.add(piece);
		}
		return pieces;
	}

	@Override
	final public List<Piece> getPiecesByRank(int rank) {
		List<Piece> pieces = new ArrayList<Piece>();
		for(Iterator<Piece> iter=getGame().iterator(); iter.hasNext(); ){
			Piece piece = iter.next();
			if( piece.getRank() == rank )
				pieces.add(piece);
		}
		return pieces;
	}
	
	@Override
	public List<Piece> getPiecesByFace(Face one, Face two) {
		List<Piece> pieces = new ArrayList<Piece>();
		for(Iterator<Piece> iter=getGame().iterator(); iter.hasNext(); ){
			Piece piece = iter.next();
			if( piece.getFace() == one && piece.getSecondFace() == two )
				pieces.add(piece);
		}
		return pieces;
	}

	@Override
	public Piece getPiece(int id) {
		return game.get(id);
	}

	@Override
	public int getGameSize() {
		return game.size();
	}

	
	
	
}
