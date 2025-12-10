package ca.rd.game.generic;

import java.util.List;

public interface Game {

	public List<Piece> createGame();

	public List<Piece> getPiecesByFace(Face face);
	public List<Piece> getPiecesByFace(Face one, Face two);
	public List<Piece> getPiecesByColor(Color color);
	public List<Piece> getPiecesByRank(int rank);
	
	public List<Piece> getGame();

	public int getRank(Face face);
	
	public Piece getPiece(int id);
	public int getGameSize();
}
