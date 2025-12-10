package ca.mss.rd.chessictk.game;

import ictk.boardgame.AmbiguousMoveException;
import ictk.boardgame.ContinuationList;
import ictk.boardgame.History;
import ictk.boardgame.IllegalMoveException;
import ictk.boardgame.chess.AmbiguousChessMoveException;
import ictk.boardgame.chess.ChessBoard;
import ictk.boardgame.chess.ChessGame;
import ictk.boardgame.chess.ChessGameInfo;
import ictk.boardgame.chess.ChessMove;
import ictk.boardgame.chess.ChessPiece;
import ictk.boardgame.chess.ChessResult;
import ictk.boardgame.chess.Square;
import ictk.boardgame.chess.io.PGNReader;
import ictk.boardgame.chess.io.PGNWriter;
import ictk.boardgame.chess.io.SAN;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;


public class ChessICTK {

	static final private SAN san = new SAN();

	private ChessGame ictkGame;
	private ChessBoard ictkBoard;
	private String pgnGame;

	/**
	 * 
	 */
	public ChessICTK() {
	}

	/**
	 * @param pgnGame
	 */
	public ChessICTK(String pgnGame) {
		setPgnGame(pgnGame);
	}

	/**
	 * @return the game
	 */
	final protected ChessGame getGame() {
		if (ictkGame == null) {
			if (pgnGame == null)
				setGame(new ChessGame());
			else
				deserializeFromPGN();
		}
		return ictkGame;
	}

	/**
	 * @param game
	 *            the game to set
	 */
	final public void setGame(ChessGame game) {
		this.ictkGame = game;
	}

	/**
	 * @return the pgnGame
	 */
	final public String getPgnGame() {
		return pgnGame;
	}

	/**
	 * @param pgnGame
	 *            the pgnGame to set
	 */
	final public void setPgnGame(String pgnGame) {
		this.pgnGame = pgnGame;
	}

	final public void deserializeFromPGN() {
		try {
			PGNReader reader = new PGNReader(new StringReader(getPgnGame()));
			setGame((ChessGame) reader.readGame());
			reader.close();
			// Go to the end of the game for continue ...
			getHistory().goToEnd();
		} catch (IOException e) {
			throw new RuntimeException("Can not read game from PGN", e);
		} catch (IllegalMoveException e) {
			throw new RuntimeException("Illegal move exception while reading PGN", e);
		} catch (AmbiguousMoveException e) {
			throw new RuntimeException("Ambiguous move exception while reading PGN", e);
		}
	}

	final public ChessICTK serializeToPgn() {
		try {
			StringWriter sw = new StringWriter(1024);

			// establish the writer object
			PGNWriter writer = new PGNWriter(sw);
			writer.setExportVariations(false);

			writer.setColumnWidth(10000);

			// write the game
			writer.writeGame(getGame());

			// gently left the file
			writer.flush();
			writer.close();

			setPgnGame(sw.getBuffer().toString());
			return this;
		} catch (Exception e) {
			throw new RuntimeException("Can not serialize game to PGN string due to " + e.toString(), e.fillInStackTrace());
		}
	}

	final public void addMove2History(ChessMove move) {
		try {
			getHistory().add(move);
		} catch (IllegalMoveException e) {
			throw new RuntimeException("Illegal [move " + move + "]\n" + this.toString(), e);
		} catch (AmbiguousMoveException e) {
			throw new RuntimeException("Ambiguous [move " + move + "]\n" + this.toString(), e);
		}
	}

	final public void addVariation(ChessMove move) {
		try {
			getHistory().addVariation(move);
		} catch (IllegalMoveException e) {
			throw new RuntimeException("Illegal [move " + move + "]\n" + this.toString(), e);
		} catch (AmbiguousMoveException e) {
			throw new RuntimeException("Ambiguous [move " + move + "]\n" + this.toString(), e);
		}
    }
 
	final public List<ChessMove> getLegalMoves(){
		return getBoard().getLegalMoves();
	}

	final public ChessMove removeMove() {
		return (ChessMove) getHistory().removeLastMove();
	}

	final public Iterator<ChessPiece> getTeamIterator(boolean isBlackTeam) {
		return getBoard().getTeamIterator(isBlackTeam);
	}

    final public void addMove(ChessMove move) {
    	addMove2History(move);
    }

	final public ChessMove nextMove(ChessMove move) {
		return (ChessMove )getHistory().next(move);
	}
	
	final public ChessMove nextMove() {
		return (ChessMove )getHistory().next();
	}
	
    final public void addMove(String move) {
		addMove(stringToMove(move));
    }
	
	final public void retractAll() {
		getHistory().rewind();
	}
	
	final public ChessMove retractMove() {
		return retractMove(false);
	}

	final public ChessMove retractMove(boolean isPromote) {
		ChessMove move = (ChessMove) getHistory().prev();
		if (isPromote) {
			ContinuationList clist = getHistory().getContinuationList();
			clist.promote(move, 0);
		}
		return move;
	}

	final public int getCapturedPiecesAmount() {
		ChessPiece[] whites = getBoard().getCapturedPieces(false);
		ChessPiece[] blacks = getBoard().getCapturedPieces(true);
		return (whites != null ? whites.length : 0) + (blacks != null ? blacks.length : 0);
	}

	final public ChessICTK goToEnd() {
		getHistory().goToEnd();
		return this;
	}

	final public ChessMove goToBegin() {
		return (ChessMove) getHistory().goTo(getHistory().getFirst());
	}

	final public List<ChessMove> getFirstAll() {
		return (List<ChessMove>) getHistory().getFirstAll();
	}

	final public ChessMove goToNext() {
		return (ChessMove) getHistory().next();
	}

	final public int getTotalMoveNumbers() {
		int moves = getTotalMoveNumbers2();
		moves = moves / 2 + moves % 2;
		return moves;
	}

	final public int getTotalMoveNumbers2() {
		return getHistory().getCurrentMoveNumber()-1;
	}

	final public int getCurrentMoveNumber() {
		int moves = getCurrentMoveNumber2();
		moves = moves / 2 + moves % 2;
		return moves;
	}

	final public int getCurrentMoveNumber2() {
		return getHistory().getCurrentMoveNumber();
	}

	final public ChessMove getCurrentMove() {
		return (ChessMove) getHistory().getCurrentMove();
	}

	final protected ChessBoard getBoard() {
		if (ictkBoard == null) {
			ictkBoard = (ChessBoard) getGame().getBoard();
		}
		return ictkBoard;
	}

	final protected History getHistory() {
		return getGame().getHistory();
	}

	final public List<ChessMove> getContinuationList() {
		return (List<ChessMove>) getHistory().getContinuationList();
	}

	final public boolean isBlackSide() {
		return getBoard().isBlackMove();
	}
 
	final public ChessGameInfo getGameInfo() {
		ChessGameInfo info = (ChessGameInfo) getGame().getGameInfo();
		if (info == null) {
			info = new ChessGameInfo();
			info.setAuxilleryProperties(new Properties());
			setGameInfo(info);
			populateResult(); // initialize 
		}
		return info;
	}

	final public void setGameInfo(ChessGameInfo gi) {
		getGame().setGameInfo(gi);
	}

	final public ChessResult getResult() {
		return (ChessResult) getGameInfo().getResult();
	}

	final public void populateResult() {
		if( isCheckmate() ){
			if( isBlackSide() )
				getGameInfo().setResult(new ChessResult(ChessResult.WHITE_WIN));
			else
				getGameInfo().setResult(new ChessResult(ChessResult.BLACK_WIN));
		} else if( isStalemate() ){
			getGameInfo().setResult(new ChessResult(ChessResult.DRAW));
		} else {
			getGameInfo().setResult(new ChessResult(ChessResult.UNDECIDED));
		}
	}

	final public boolean isEndOfGame() {
		return isCheckmate() || isStalemate() || isDraw();
	}

	final public boolean isCheckmate() {
		return getBoard().isCheckmate();
	}

	final public boolean isStalemate() {
		return getBoard().isStalemate();
	}

	final public boolean isDraw() {
		return false; //getCapturedPiecesAmount() > 26;
	}

	/*
	 * SAN methods
	 */
	final public String getLocation(Square square) {
		return san.fileToChar(square.getFile()) + "" + san.rankToChar(square.getRank());
	}

	final public String getIdent(ChessPiece piece) {
		return "" + san.pieceToChar(piece);
	}

	final public ChessMove stringToMove(String s) {
		try {
			return (ChessMove) san.stringToMove(getBoard(), s);
		} catch (AmbiguousChessMoveException e) {
			throw new RuntimeException(e.getMessage(), e.fillInStackTrace());
		} catch (IllegalMoveException e) {
			throw new RuntimeException(e.getMessage(), e.fillInStackTrace());
		}
	}

	final public String moveToString(ChessMove m) {
		return san.moveToString(m);
	}

	final public ChessPiece getPiece(ChessMove move) {
		return (ChessPiece) move.getDestination().getPiece();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	final public String toString() {
		return getBoard().toString();
	}
}
