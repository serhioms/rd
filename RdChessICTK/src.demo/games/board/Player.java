
package games.board;

public interface Player {

  /** a player maintains its own board state, which is
      set up initially with setBoard */
  // public void setBoard(NodeInterface board);

  /** calls to getMove are now expected to act on the
      current state of the board */
  // public Move getMove(Move previousMove, int toPlay, boolean maximize);

  /** in the old interface a player maintained no
      state, but was given a copy of the current board
      for each move request each point in time */

  public Move getMove(NodeInterface board, int toPlay, boolean maximize);

}
