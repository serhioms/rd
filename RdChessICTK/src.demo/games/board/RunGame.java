
package games.board;

import games.board.*;

import java.io.*;
import java.util.*;

/**

 This class simply has a main method
 that plays a game of the specified type.

 It uses a BoardComponent to display the game,
 and reads the game from simple plays a game of the specified

*/

public class RunGame {

  // the default depth for the Minimax player...
  static int defaultDepth = 3;

  // Player[] players;
  // Board board;

  static String defaultGame = "games.checkers.Board";

  public static void main(String[] args) throws Exception {
    if (args.length == 0)
      new RunGame( defaultGame );
    else
      new RunGame( args[0] );
  }

  public RunGame(String boardClass) throws Exception {
    NodeInterface board =
      ( NodeInterface ) Class.forName(boardClass).newInstance();

    // games.board.Player p1 = new games.board.MinimaxPlayer(defaultDepth);
    games.board.Player p1 = new games.board.RandomPlayer();
    // games.board.Player p2 = new games.board.MinimaxPlayer(defaultDepth);
    games.board.Player p2 = new games.board.RandomPlayer();

    // let player 2 be a human player inputting moves
    // via the mouse
    // games.board.Player p2 = new games.board.SequenceMoveGenerator();

    // create a component to display the board
    BoardComponent bc = new BoardComponent( board );

    // set the square listener if appropriate
    if (p2 instanceof SquareListener)
      bc.setListener( (SquareListener) p2);

    // create a frame to hold the component
    utilities.CloseableFrame cf =
      new utilities.CloseableFrame(bc, boardClass, false);

    // create a Game to run it
    Game g = new Game(board, bc);

    // play!
    g.play(p1, p2);
  }
}
