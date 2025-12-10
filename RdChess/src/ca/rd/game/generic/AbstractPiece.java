package ca.rd.game.generic;

abstract public class AbstractPiece implements Piece {

	@Override
	abstract public Face getFace();

	@Override
	public Face getSecondFace() {
		return getFace();
	}

}
