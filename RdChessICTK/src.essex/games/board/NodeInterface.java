
package games.board;

import java.io.Serializable;

public interface NodeInterface extends Serializable, Cloneable {

  public static final long serialVersionUID = 12343;
  public static final int EMPTY = 0;

  public MoveEnumeration getMoves(int player);


  // assumes a board may be represented as a 2d array of int
  // - good enough for most popular board games
  public int[][] getBoard();

  // should really be done by a separate evaluator
  public int score();

  public Move translateMove(String response);

  public void applyMove(Move m, int player);

  public void retractMove(Move m);

  public boolean isLegal(Move m, int player);

  public boolean couldStartWith(Move m, int player);

  /* this should not be defined in NodeInterface - it only
     has relevance to minimax-based players... */
  public boolean maximizing(int player);

  /* again - this should not be part of NodeInterface  */
  public int getPlayer(boolean maximizing);

  public int nextPlayer(int player);

  // for some games this may depend on which player is due to move
  public boolean gameOver();

  public String resultString();

  /**
    returns 0 for a draw, 1 for player 1 win, -1 for player 2 win
    throws an exception if the game is not yet over
  */
  public int getResult() throws Exception;

  public int startingPlayer();

  public int stateOf(int i, int j);

  public int width();

  public int height();

  public int toPlay();

  public NodeInterface copy();

  public void copyFrom(NodeInterface node);

  public void drawPiece(java.awt.Graphics g, int val, int x, int y, int size);

  /** this is used for remote-play purposes - rather than
    pass the remote player a copy of the entire board, we
    can just pass the most recent move (null if no moves
    have been made yet) and let it maintain its own board
    state */
  public games.board.Move getLastMove();

}
